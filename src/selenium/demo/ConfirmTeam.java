package selenium.demo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmTeam {
	public void confirmTeam(WebDriver driver, WebDriverWait wait) {
		WebElement confirm = null;
		System.out.println("Start New ConfirmTeam");
		System.out.println(driver.getCurrentUrl());
		if (driver.getCurrentUrl().contains("supporter")) {
			confirm = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='btn-usual-ok se-quest-start']")));
		}
		if ( confirm != null ) {
			System.out.println("Confirm team/support");
			confirm.click(); // Confirm team/support
		}
		wait.until ( ExpectedConditions.not ( ExpectedConditions.urlContains ("supporter")));
		return;
	}
	
	public void confirmTeam(WebDriverWait wait) {
		WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"wrapper\"]/div[3]/div[3]/div[3]/div[2]")));	    			    
		System.out.println("Confirm team/support");
		confirm.click(); // Confirm team/support
		return;
	}
}
