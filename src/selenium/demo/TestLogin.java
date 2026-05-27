package selenium.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestLogin {
	
	public WebDriver testLogin() throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("debuggerAddress","localhost:9222");
		WebDriver driver = new ChromeDriver(options);
	  // Optional. If not specified, WebDriver searches the PATH for chromedriver.
	  
	  //println File("C:\\Users\\Nick\\AppData\\Local\\Google\\Chrome\\User Data\\Default").getAbsolutePath();
	  


	  
	  
	  return driver;
	}	  
}

