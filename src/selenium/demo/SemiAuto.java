package selenium.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SemiAuto {
	public void semiAuto(WebDriver driver, WebDriverWait wait) throws InterruptedException {
		By atk = By.className("btn-attack-start");
		By auto = By.className("btn-auto");
		try { 
			try { 
				while (true) { 

					wait.until(ExpectedConditions.or(
							ExpectedConditions.elementToBeClickable(atk),
							ExpectedConditions.elementToBeClickable(auto)
							));
					IsElementPresent ePresent = new IsElementPresent();
					 
					WebElement attackButton = ePresent.isElementClickable(driver, atk);
					if (attackButton!=null) {
						System.out.println("Attack");
						attackButton.click();
					}					
					WebElement semiButton = ePresent.isElementClickable(driver, auto);
					if (semiButton!=null) {
						System.out.println("Semi-auto");
						semiButton.click();
						break;
					}					
				}
			} catch (TimeoutException e) {
				return;
			}
		} finally {
		}
	}

}

