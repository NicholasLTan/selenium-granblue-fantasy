package selenium.demo;

import java.time.Duration;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AngelHalo {

	private void AngelVH(WebDriver driver) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-key='97'")));
		driver.findElement(By.cssSelector("div[data-key='97'")).click();  //Angel Halo
		//Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-chapter-id='51003'")));
		driver.findElement(By.cssSelector("div[data-chapter-id='51003'")).click(); //Very Hard
		//Thread.sleep(2000);
	}
	
	@Test
	public void angelHalo() throws InterruptedException {
		int maxRelics = 13; // Number of Silver Relics to farm
		
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
		SemiAuto semiAuto = new SemiAuto();
		AutoBattleNew autoBattleNew = new AutoBattleNew();
		AutoBattle autoBattle = new AutoBattle();
		Battle battle = new Battle();

		driver.get("https://game.granbluefantasy.jp/#quest/extra");
		int relics = 0;
		while (relics < maxRelics) {
			int haloCount = 0;
			this.AngelVH(driver); //Angel Halo VH quest
			while (haloCount < 10) {
				confirmTeam.confirmTeam(wait);
				//semiAuto.semiAuto(driver, wait);
				battle.battle(driver, wait);
				//autoBattleNew.autoBattleNew(driver, wait);
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
					//autoBattle.autoBattle(driver, wait);
					battle.battle(driver, wait);
					results.results(driver, wait, false);								
				}
			}
		}
		System.out.println("Angel Halo farming complete");
	}
}
