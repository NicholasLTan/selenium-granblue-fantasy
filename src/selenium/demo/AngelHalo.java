package selenium.demo;

import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AngelHalo {
	private void AngelVH(@NonNull WebDriver driver) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(10)));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-key='97'")));
		driver.findElement(By.cssSelector("div[data-key='97'")).click();  //Angel Halo
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-chapter-id='51003'")));
		driver.findElement(By.cssSelector("div[data-chapter-id='51003'")).click(); //Very Hard
	}
	
	@Test
	public void angelHalo() throws InterruptedException {
		int maxRelics = 13; // Number of Silver Relics to farm
		
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		@SuppressWarnings("null")
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
		Battle battle = new Battle();

		driver.get("https://game.granbluefantasy.jp/#quest/extra");
		int relics = 0;
		while (relics < maxRelics) {
			int haloCount = 0;
			this.AngelVH(driver); //Angel Halo VH quest
			while (haloCount < 10) {
				confirmTeam.confirmTeam(wait);
				battle.battle(driver, wait, false);
				haloCount++;
				System.out.println(relics + "." + haloCount );
				Results results = new Results();
				results.results(driver, wait, true);
				IsElementPresent ePresent = new IsElementPresent();
				boolean elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-next"));
				if (elementExists) {
					System.out.println(haloCount + " VHs");
					relics++;
					haloCount=10;
					driver.findElement(By.className("btn-usual-next")).click();
					System.out.println("DHalo start " + relics ); 
					confirmTeam.confirmTeam(wait);		  
					battle.battle(driver, wait, false);
					results.results(driver, wait, false);								
				}
			}
		}
		System.out.println("Angel Halo farming complete");
	}
}
