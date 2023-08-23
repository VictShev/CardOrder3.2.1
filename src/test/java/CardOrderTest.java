
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardOrderTest {
    private WebDriver driver;


    @BeforeEach
    void createBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void orderingCardTest() {
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");//---
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79101234567");//---

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.strip());
    }


    @Test
    void nameEnglishTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79101234567");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.strip());
    }


    @Test
    void nameNumberTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("555");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79101234567");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

//        String actual = driver.findElement(By.className("input__sub")).getText();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.strip());
    }

    @Test
    void nameSpecialCharactersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("&");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79101234567");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.strip());
    }

    @Test
    void nameEmptyTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79101234567");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.strip());
    }


    @Test
    void phoneRusCharactersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void phoneEngCharactersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("Ivan");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void phoneSpecialCharactersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("%");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void phoneOnlyNumbersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("79012345678");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void phoneManyNumbersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+790123456785555555555555555555555");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void phoneFewNumbersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79012345");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void phoneEmptyTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("");
        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.strip());
    }

    @Test
    void phoneCheckNoClickTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79012345678");
        driver.findElement(By.className("button")).click();

        assertTrue(driver.findElement(By.className("input_invalid")).isEnabled());
    }

}