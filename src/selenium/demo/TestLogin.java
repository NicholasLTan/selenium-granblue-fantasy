package selenium.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestLogin {
	
	public WebDriver testLogin() throws InterruptedException {

	  // Optional. If not specified, WebDriver searches the PATH for chromedriver.
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick\\libs\\selenium-jars\\chromedriver-win64\\chromedriver.exe");
	  ChromeOptions options = new ChromeOptions();
	  //println File("C:\\Users\\Nick\\AppData\\Local\\Google\\Chrome\\User Data\\Default").getAbsolutePath();
	  options.setExperimentalOption("debuggerAddress","localhost:9222");


	  WebDriver driver = new ChromeDriver(options);
	  
	  return driver;
	}	  
}

