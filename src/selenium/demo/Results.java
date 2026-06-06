package selenium.demo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Objects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Results {
	public static final String retryXpath = "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[2]";
	public static final String expeditionXpath = "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[5]";
	public void results(WebDriver driver, WebDriverWait wait, boolean retry) throws InterruptedException {
		By ok = By.className("btn-usual-ok");
		By ctrl = By.className("btn-control"); //Backup Requests //Special Quests
		By next = By.className("btn-usual-next"); //Play Next, for DHalo
		By popup = By.className("pop-usual");
		By popupHeaderBy = By.className("prt-popup-header");
		By rankup = By.id("cjs-lp-rankup");
		By close = By.className("btn-usual-close");
		By playAgain = By.cssSelector("div[data-chapter-id][class='btn-retry cnt-quest']");
		int logLevel = 1;		
		String resultsURL = "https://game.granbluefantasy.jp/#result";
				
		if ( logLevel >= 1 ) {System.out.println("Results wait");}
		//Thread.sleep(1000);
		wait.until(ExpectedConditions.urlContains(resultsURL));
		while (Objects.requireNonNull(driver.getCurrentUrl()).startsWith(resultsURL)) {
			if ( logLevel >= 2 ) {System.out.println("In Results Wait Loop");}
			wait.until(ExpectedConditions.or(
					ExpectedConditions.visibilityOfElementLocated(popup),
					ExpectedConditions.elementToBeClickable(rankup),
					ExpectedConditions.elementToBeClickable(ok),
					ExpectedConditions.elementToBeClickable(ctrl),
					ExpectedConditions.elementToBeClickable(close),
					ExpectedConditions.elementToBeClickable(playAgain))); 
			String url = Objects.requireNonNull(driver.getCurrentUrl());
			List<WebElement> elementPopup = driver.findElements(popup);
			List<WebElement> elementRankup = driver.findElements(rankup);
			List<WebElement> elementOk = driver.findElements(ok);
			List<WebElement> elementCtrl = driver.findElements(ctrl);
			List<WebElement> elementClose = driver.findElements(close);
			List<WebElement> elementPlayAgain = driver.findElements(playAgain);
			if (url.startsWith("https://game.granbluefantasy.jp/#result_multi/empty")) {
				if ( logLevel >= 1 ) {System.out.println("Empty result");}
				wait.until(ExpectedConditions.elementToBeClickable(ctrl));
				WebElement ctrlElement = elementCtrl.get(0);
				ctrlElement.click();
				if ( logLevel >= 2 ) {System.out.println("CTRL clicked");}
				wait.until(ExpectedConditions.stalenessOf(ctrlElement));
			} else if (!elementRankup.isEmpty() && elementRankup.get(0).isDisplayed()) {
				if ( logLevel >= 1 ) {System.out.println("Rankup");}
				WebElement rankupElement = Objects.requireNonNull(elementRankup.get(0));
				wait.until(ExpectedConditions.elementToBeClickable(rankupElement));
				Thread.sleep(2500); //Necessary sleep for canvas anim to play
				rankupElement.click();
				wait.until(ExpectedConditions.stalenessOf(rankupElement));
				if ( logLevel >= 2 ) {System.out.println("Rankup clicked");}
			} else if (!elementClose.isEmpty() && elementClose.get(0).isDisplayed()) {
				if ( logLevel >= 1 ) {System.out.println("Close");}
				WebElement closeElement = Objects.requireNonNull(elementClose.get(0));
				closeElement.click();
				wait.until(ExpectedConditions.stalenessOf(closeElement));
			} else if (!elementPopup.isEmpty() && elementPopup.get(0).isDisplayed()) {
				String popupHeader = elementPopup.get(0).findElement(popupHeaderBy).getText();
				if ( logLevel >= 1 ) {
					System.out.println("Popup Ok");
					System.out.println(popupHeader); 
				}
				elementOk.get(0).click();
				wait.until(ExpectedConditions.or(
						ExpectedConditions.not(ExpectedConditions.textToBe(popupHeaderBy, popupHeader)),
						ExpectedConditions.invisibilityOf(Objects.requireNonNull(elementPopup.get(0))))); 	
			/*} else if (!elementOk.isEmpty() && elementOk.get(0).isDisplayed()) {
				System.out.println("Ok");
				elementOk.get(0).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(ok)); */			
			} else if (retry  && !elementPlayAgain.isEmpty() && elementPlayAgain.get(0).isDisplayed()) {
				if ( logLevel >= 1 ) {System.out.println("PlayAgain");}
				elementPlayAgain.get(0).click();
				break;
			} else if (!elementCtrl.isEmpty() && elementCtrl.get(0).isDisplayed()) {
				if ( logLevel >= 1 ) {System.out.println("Ctrl");}
				elementCtrl.get(0).click();
				//wait.until(ExpectedConditions.invisibilityOfElementLocated(ctrl));
				if ( logLevel >= 1 ) {System.out.println("Ctrl complete");}
				break;
			}
		}
		//Thread.sleep(1000); //Necessary sleep for potential popup to manifest
		
		while ( Objects.requireNonNull(driver.getCurrentUrl()).startsWith(resultsURL)) {
			if ( logLevel >= 2 ) {System.out.println("In Results Wait Loop 2");}
			Thread.sleep(500);
			List<WebElement> elementPopup = driver.findElements(popup);
			while (!elementPopup.isEmpty()) {
				wait.until(ExpectedConditions.or(
						ExpectedConditions.visibilityOfElementLocated(popup),
						ExpectedConditions.elementToBeClickable(ok),
						ExpectedConditions.elementToBeClickable(ctrl),
						ExpectedConditions.elementToBeClickable(close)));				
				elementPopup = driver.findElements(popup);
				if (!elementPopup.isEmpty() && elementPopup.get(0).isDisplayed()) {
					if ( logLevel >= 1 ) {System.out.println("Popup Ok");
					System.out.println(elementPopup.get(0).findElement(By.className("prt-popup-header")).getText());}
					List<WebElement> elementNext = driver.findElements(next);
					List<WebElement> elementOk = driver.findElements(ok);
					List<WebElement> elementClose = driver.findElements(close);
					List<WebElement> elementCtrl = driver.findElements(ctrl);
					if (!elementNext.isEmpty() && elementNext.get(0).isDisplayed() && retry && !elementPopup.get(0).findElement(By.className("prt-popup-header")).getText().equals("Unparalleled Foe")) {
						if ( logLevel >= 1 ) {System.out.println("PlayAgain Next");}
						WebElement nextElement = elementNext.get(0);
						nextElement.click();
						wait.until(ExpectedConditions.stalenessOf(nextElement));
						break;
					} else if (!elementOk.isEmpty() && elementOk.get(0).isDisplayed()) {
						if ( logLevel >= 1 ) {System.out.println("PlayAgain Ok");}
						elementOk.get(0).click();
						wait.until(ExpectedConditions.stalenessOf(Objects.requireNonNull(elementOk.get(0))));
						break;
					} else if (!elementClose.isEmpty() && elementClose.get(0).isDisplayed()) {
						if ( logLevel >= 1 ) {System.out.println("PlayAgain Close");}
						elementClose.get(0).click();
						wait.until(ExpectedConditions.stalenessOf(Objects.requireNonNull(elementClose.get(0))));
						break;
					} else if (!elementCtrl.isEmpty() && elementCtrl.get(0).isDisplayed()) {
						wait.until(ExpectedConditions.elementToBeClickable(Objects.requireNonNull(elementCtrl.get(0))));
						System.out.println(Objects.requireNonNull(elementCtrl.get(0).getAttribute("data-buton-name")));
						elementCtrl.get(0).click();
						if ( logLevel >= 1 ) {System.out.println("CTRL clicked");}
						wait.until(ExpectedConditions.stalenessOf(Objects.requireNonNull(elementCtrl.get(0))));
					}
				}
			}
		}
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl != null && currentUrl.startsWith("https://game.granbluefantasy.jp/#quest/supporter")) {
			System.out.println("Results return to supporter");
			return;
		} else if (currentUrl != null && currentUrl.startsWith("https://game.granbluefantasy.jp/#quest/assist")) {
			System.out.println("Results return to Backup Requests");
			return;
		}
		if ( logLevel >= 1 ) {System.out.println("Results return");}
		return;
	}
}
