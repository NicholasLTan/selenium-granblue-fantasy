package selenium.demo;

import java.time.Duration;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Testing {
	@Test
	public void testing () throws InterruptedException{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick\\libs\\selenium-jars\\chromedriver-win64\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("debuggerAddress","localhost:9222");
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement attackButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-retry")));  
		attackButton.click(); //Attack
		System.out.println("SP Quest");
		
		
	}
}
