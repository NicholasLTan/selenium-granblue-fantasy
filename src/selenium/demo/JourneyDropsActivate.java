package selenium.demo;
import java.time.Duration;
import java.util.Objects;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JourneyDropsActivate {
	@Test
	public void journeyDropsActivate() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		String hours = "2";
		String[] dropList = {"1","2","6"};
		By ok = By.className("btn-usual-ok");
		By cancel = By.className("btn-usual-cancel");		
		driver.get("https://game.granbluefantasy.jp/#shop/exchange/trajectory");
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(10)));
		//System.out.println("Before trangect");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("txt-trangect-num")));
		int jdNum = Integer.valueOf(driver.findElement(By.className("txt-trangect-num")).getText());
		//System.out.println("After trangect");
		for (String dropId : dropList) {
			By jdByCSS = By.cssSelector("div[data-support-id='" + dropId + "']");
			if (Integer.valueOf(dropId) > 5) {
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.PAGE_DOWN).perform();
				//wait.until(ExpectedConditions.visibilityOfElementLocated(jdByCSS));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("atx-lead-link")));
				Thread.sleep(500);  //Necessary sleep for PGDN to process
			}
			String jdTarget = String.valueOf(jdNum - (Integer.valueOf(hours) * 10));			 
			WebElement button = driver.findElement(jdByCSS);
			String buttonString = Objects.requireNonNull(button.getDomAttribute("class"));
			if (buttonString.equals("btn-use-support")  ) {				
				driver.findElement(jdByCSS).click();	// Activate		
				wait.until(ExpectedConditions.elementToBeClickable(cancel));
				driver.findElement(By.cssSelector("div[data-list-key='4']")).click(); // Lv4
			} else if (buttonString.equals("btn-edit-support")) {
				driver.findElement(jdByCSS).click();	// Extend
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pop-usual")));
				driver.findElement(By.className("btn-extend-support")).click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(ok));
			if (!hours.equals("1")) {
		        WebElement selectElement = driver.findElement(By.className("num-time"));
		        Select select = new Select(selectElement);
		        select.selectByValue(hours);
			}			
			driver.findElement(ok).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pop-support-complete")));
			Thread.sleep(500); //Necessary delay
			driver.findElement(ok).click();
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("txt-trangect-num"), Objects.requireNonNull(jdTarget)));
			jdNum = Integer.valueOf(driver.findElement(By.className("txt-trangect-num")).getText());
		}
		return;
	}	  
}