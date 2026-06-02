package selenium.demo;

import java.time.Duration;
import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RecordsTen {
	@Test
	public void recordsTen() throws InterruptedException {
		int maxAttempts = 5; // Optional: To prevent infinite loops
		int questLv = 150; //75=VH, 80=EX, 95=IMP, 100=NM, 150=NM
		boolean reload = true; //Set to false if unable to OTK

		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(600));
		PlayAgain playAgain = new PlayAgain();
		By questBy = By.cssSelector("div[id='btn-quest-start'][data-quest-name^='Lvl " + questLv + "']");				

		driver.get("https://game.granbluefantasy.jp/#event/terra");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-progress-map")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("prt-head-current")));		
		System.out.println(driver.findElement(By.className("prt-head-current")).getText());
		WebElement questBtn;
		if (!driver.findElements(By.className("prt-popup-header")).isEmpty()) {
			WebElement closeBtn = driver.findElement(By.className("btn-usual-close"));
			closeBtn.click();
			wait.until(ExpectedConditions.stalenessOf(closeBtn));
		}		
		if (questLv >= 100) {
			driver.findElement(By.className("btn-quest-start-hell")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pop-usual")));
			questBtn=driver.findElement(questBy);
			maxAttempts=Integer.valueOf(questBtn.findElement(By.xpath("../div[@class='txt-remain-count']")).getText().replaceAll("[^0-9]", ""));
			reload = false;			
		} else { questBtn = driver.findElement(questBy); }
		wait.until(ExpectedConditions.elementToBeClickable(questBy));
		System.out.println(driver.findElement(questBy).getAttribute("data-quest-name"));
		questBtn.click();
		playAgain.playAgain(driver, longWait, maxAttempts, reload);
	}



}
