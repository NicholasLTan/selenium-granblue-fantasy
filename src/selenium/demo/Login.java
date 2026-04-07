package selenium.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Login {

	public WebDriver login() throws InterruptedException {

		boolean journeyDrops = true;

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick\\libs\\selenium-jars\\chromedriver-win64\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("debuggerAddress","localhost:9222");
		WebDriver driver = new ChromeDriver(options);
		//driver.get("https://game.granbluefantasy.jp/#mypage"); 
		//Thread.sleep(1000);  
		IsElementPresent ePresent = new IsElementPresent();
		boolean elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-close"));
		if (elementExists) {
			driver.findElement(By.className("btn-usual-close")).click();
			System.out.println("Main Quest Close");
			Thread.sleep(2000);
		}
		if ( journeyDrops == true ) {
			JourneyDropsActivate JDA = new JourneyDropsActivate();
			JDA.journeyDropsActivate(driver);
		}
		return driver;
	}	  
}

