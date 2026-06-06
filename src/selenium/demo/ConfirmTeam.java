package selenium.demo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmTeam {
	public void confirmTeam(@NonNull WebDriver driver, WebDriverWait wait) {
		int logLevel = 0;
		WebElement confirm = null;
		if ( logLevel >= 1) { System.out.println("Start New ConfirmTeam"); }
		String currentUrl = driver.getCurrentUrl();
		if ( logLevel >= 1) { System.out.println(currentUrl); }
		if (currentUrl != null && currentUrl.contains("supporter")) {
			confirm = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-usual-ok']")));
		}
		if ( confirm != null ) {
			if ( logLevel >= 1) { System.out.println("Confirm team/support"); }
			confirm.click(); // Confirm team/support
		}
		try {
			wait.until ( ExpectedConditions.not ( ExpectedConditions.urlContains ("supporter")));
		} catch (TimeoutException e) {
			if ( !driver.findElements(By.className("pop-usual")).isEmpty() ) {
				WebElement popup = driver.findElement(By.className("pop-usual"));
				popup.findElement(By.className("btn-usual-ok")).click();
			}
			String url = driver.getCurrentUrl();
			if (url != null && url.equals("https://game.granbluefantasy.jp/#mypage")) {
				System.out.println("homepage");
				return;
			}
		}
		return;
	}
	
	public void confirmTeam(WebDriverWait wait) {
		WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"wrapper\"]/div[3]/div[3]/div[3]/div[2]")));	    			    
		System.out.println("Confirm team/support");
		confirm.click(); // Confirm team/support
		return;
	}
}
