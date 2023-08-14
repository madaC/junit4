import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationStrategies {

    WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
    @Before
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("lang=en-GB");
        driver = new ChromeDriver(options);
    }

    @Test
    public void testByTagName() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        String title = driver.getTitle();
        System.out.println(title);

        //explicit wait -  to wait for the textarea button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("textarea")));

        assertThat(textarea.getDomAttribute("rows")).isEqualTo("3");

    }

    @Test
    public void testByHtmlAttributes() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        //By name
        WebElement textByName = driver.findElement(By.name("my-text"));
        assertThat(textByName.isEnabled()).isTrue();

        //By id
        WebElement textById = driver.findElement(By.id("my-text-id"));
        assertThat(textById.getAttribute("type")).isEqualTo("text");
        assertThat(textById.getDomAttribute("type")).isEqualTo("text");
        assertThat(textById.getDomProperty("type")).isEqualTo("text");

        assertThat(textById.getAttribute("myprop")).isEqualTo("myvalue");

        //By  class name
        //we locate a list of elements by class
        List<WebElement> byClassName = driver.findElements(By.className("form-control"));
        //we verify the list has more than one element
        assertThat(byClassName.size()).isPositive();
        //we check that the first element found by class is the same as the input text located before
        assertThat(byClassName.get(0).getAttribute("name")).isEqualTo("my-text");

    }

    @Test
    public void testByLinkTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement indexLink = driver.findElement(By.linkText("Return to index"));
        assertThat(indexLink.getTagName()).isEqualTo("a");
        assertThat(indexLink.getCssValue("cursor")).isEqualTo("pointer");

        WebElement partialIndexLink = driver.findElement(By.partialLinkText("index"));
        assertThat(partialIndexLink.getLocation()).isEqualTo(indexLink.getLocation());
        assertThat(partialIndexLink.getRect()).isEqualTo(indexLink.getRect());

    }
    @Test
    @Ignore
    public void testByXPathBasic() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement hidden = driver.findElement(By.xpath("//input[@name='my-disabled']"));
        assertThat(hidden.isDisplayed()).isTrue();
    }

    @After
    public void teardown() {
        driver.quit();
    }


}
