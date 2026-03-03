package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IsElementPresent {
	public boolean isElementPresent(WebDriver driver, By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.size() > 0;
    }
	
	public WebElement isElementClickable(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		WebElement clickable = null;
		try {
			clickable = wait.until(ExpectedConditions.elementToBeClickable(locator));
			return clickable;
		} catch (TimeoutException e) {
			return null;
		}
	}
}
