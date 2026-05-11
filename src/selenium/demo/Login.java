package selenium.demo;

import org.openqa.selenium.WebElement;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

	public WebDriver login() throws InterruptedException {

		//boolean journeyDrops = false; //deprecated, move Journey Drops to Suite

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick\\libs\\selenium-jars\\chromedriver-win64\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("debuggerAddress","localhost:9222");
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		//driver.get("https://game.granbluefantasy.jp/#mypage"); 
		//Thread.sleep(1000);  
		IsElementPresent ePresent = new IsElementPresent();
		boolean elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-close"));
		if (elementExists) {
			WebElement element = driver.findElement(By.className("btn-usual-close"));
			element.click();
			
			System.out.println("Main Quest Close");
			wait.until(ExpectedConditions.stalenessOf(element));
		}
		/*
		if ( journeyDrops == true ) {
			JourneyDropsActivate JDA = new JourneyDropsActivate();
			JDA.journeyDropsActivate(driver);
		}
		*/
		return driver;
	}	  
}

