package ru.netology.card;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;

class ChromeTest {

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");

    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestPositive() {
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Тимофей Чернасов");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79101105214");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("[type='button']")).click();
        String actual = driver.findElement(cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeFIO() {
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Micael");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79104405214");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("[type='button']")).click();
        String actual = driver.findElement(cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeFIOEmpty() {
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79101107114");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("[type='button']")).click();
        String actual = driver.findElement(cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativePhone() {
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Дмитрий");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+7910110521499");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("[type='button']")).click();
        String actual = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativePhoneEmpty() {
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Ярослав");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("[type='button']")).click();
        String actual = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeCheckboxEmpty() {
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Эдуард Васильев");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+70123456789");
        driver.findElement(cssSelector("[type='button']")).click();
        String actual = driver.findElement(cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getCssValue("color");
        String expected = "rgba(255, 92, 92, 1)";
        assertEquals(expected, actual);
    }
}

