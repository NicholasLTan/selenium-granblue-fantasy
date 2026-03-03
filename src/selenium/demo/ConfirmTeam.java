package selenium.demo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ConfirmTeam {
	public void confirmTeam(WebDriverWait wait) {
		WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"wrapper\"]/div[3]/div[3]/div[3]/div[2]")));	    			    
		System.out.println("Confirm team/support");
		confirm.click(); // Confirm team/support
		return;
	}
}
