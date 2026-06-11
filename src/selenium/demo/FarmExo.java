package selenium.demo;

import java.time.Duration;
import java.util.Objects;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmExo {
	@Test
	public void farmExo() throws InterruptedException {
		int maxAttempts = 40; // To prevent infinite loops; 0 for infinite
		String level = "120"; // Crucible level for farming
		String itemID = "10596"; // Ardens Lapis for Ifrit, item must be visible in 
		int maxItem = 10;
		
		Login login = new Login();
		WebDriver driver = Objects.requireNonNull(login.login(), "WebDriver must not be null");
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(60)));
		WebDriverWait longWait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(1000)));
		
		driver.get("https://game.granbluefantasy.jp/#event/godslayer"); //Exo event URL
		By bossQuestBy = By.className("img-boss-quest");
		wait.until(ExpectedConditions.elementToBeClickable(bossQuestBy));
		System.out.println(driver.findElement(By.className("prt-head-current")).getText());
		driver.findElement(bossQuestBy).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("prt-popup-header")));
		System.out.println(driver.findElement(By.className("prt-popup-header")).getText());
		
		int currItem = 0;
		String itemCSS = "figure[data-item-id='" + itemID + "']";
		IsElementPresent ePresent = new IsElementPresent();
		boolean	elementExists = ePresent.isElementPresent(driver, By.cssSelector(itemCSS));
		if (elementExists) {
			WebElement item = driver.findElement(By.cssSelector(itemCSS));
			String itemCountString = item.findElement(By.xpath("./figcaption")).getText();
			System.out.println(itemCountString);
			String currItemCountString = itemCountString.substring(0,itemCountString.indexOf("/"));
			maxItem = Integer.valueOf(itemCountString.substring(itemCountString.indexOf("/")+1,itemCountString.length()));
			currItem = Integer.valueOf(currItemCountString);
			System.out.println("currItem = " + currItem + "; maxItem = " + maxItem);
		}
		
		int attempts = 0;
		if (attempts < maxAttempts || maxAttempts == 0) {
	        WebElement selectElement = driver.findElement(By.className("btn-select-level"));
	        Select select = new Select(selectElement);
	        select.selectByVisibleText(level);
			driver.findElement(By.className("btn-set-quest")).click(); //Play
			wait.until(ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#quest/supporter"));
		}		
		while (attempts < maxAttempts || maxAttempts == 0 || (elementExists && currItem >= maxItem)) {
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);
			ePresent = new IsElementPresent();				
			Results results = new Results();
			results.results(driver, longWait, true);
			String itemCountString = driver.findElement(By.cssSelector(itemCSS)).findElement(By.xpath("./figcaption")).getText();
			currItem = Integer.valueOf(itemCountString.substring(0,itemCountString.indexOf("/")));
			attempts++;
			System.out.println(itemCountString);
			System.out.println(attempts + "/" + maxAttempts + " attempts");
		}
	}
}
