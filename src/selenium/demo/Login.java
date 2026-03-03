package selenium.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Login {

	public WebDriver login() throws InterruptedException {

		boolean journeyDrops = true;

		// Optional. If not specified, WebDriver searches the PATH for chromedriver.
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick\\libs\\selenium-jars\\chromedriver-win64\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//println File("C:\\Users\\Nick\\AppData\\Local\\Google\\Chrome\\User Data\\Default").getAbsolutePath();
		options.setExperimentalOption("debuggerAddress","localhost:9222");


		WebDriver driver = new ChromeDriver(options);
		driver.get("https://game.granbluefantasy.jp/#mypage"); 
		Thread.sleep(5000);  // Let the user actually see something!
		// Click on the element
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

