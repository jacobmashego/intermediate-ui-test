import org.openqa.selenium.By;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class JavaScriptAlerts {
    private WebDriver driver;

    @BeforeTest
    public void webDriverManagement()
    {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\JMashego\\Documents\\Selenium Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.manage().window().maximize();
    }

    public void handleAlert(boolean acceptAlert, String text)
    {
        Alert alert = driver.switchTo().alert();
        //if text is not null, send keys to alert textbox
        if (text != null)
        {
            alert.sendKeys(text);
        }
        //accept alert if true, dismiss alert if false
        if (acceptAlert)
        {
            alert.accept();
        } else
        {
            alert.dismiss();
        }
    }

    public void verifyResults(String expectedResults, String errorMessage)
    {
        WebElement actualResults = driver.findElement(By.xpath("//p[@id='result']"));
        Assert.assertEquals(actualResults.getText(),expectedResults, errorMessage);
    }

    @Test(priority = 1)
    public void AcceptAlert()
    {
        //step 1: click on js alert button
        WebElement jsAlertButton = driver.findElement(By.xpath("//*[@id='content']/div/ul/li[1]/button"));
        jsAlertButton.click();

        //step 2: click OK on alert pop up
        handleAlert(true, null);

        //step 3: verify that the results text is correct
        String expectedResults = "You successfully clicked an alert";
        verifyResults(expectedResults,"Test failed - Alert not accepted");
    }

    @Test(priority = 2)
    public void DismissAlert()
    {
        //step 1: click on JS Confirm Button
        WebElement jsConfirmButton = driver.findElement(By.xpath("//*[@id='content']/div/ul/li[2]/button"));
        jsConfirmButton.click();

        //step 2: click cancel on alert pop up to dismiss the alert
        handleAlert(false, null);

        //step 3: verify that the results text is correct
        String expectedResults = "You clicked: Cancel";
        verifyResults(expectedResults,"Test failed - Alert not dismissed");
    }

    @Test(priority = 3)
    public void EnterTextInPrompt()
    {
        //step 1: click on js prompt button
        WebElement jsPromptButton = driver.findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[3]/button"));
        jsPromptButton.click();

        //step 2: enter a text on alert pop up
        String enterText = "Entering Text in Prompt";
        handleAlert(true, enterText);

        //step 3: verify that the results text is correct
        String expectedResults = "You entered: " + enterText;
        verifyResults(expectedResults,"Test failed - Text Not Entered");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
