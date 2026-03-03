package selenium.demo;
import java.time.Duration;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmVeritas {
	public static final By chestBy = By.className("btn-stage-chest"); //Mimic
	public static final String mimicClass = "div[data-chapter-name='Mimic'][class='prt-quest-list btn-quest-list']"; //Mimic
	//public static final String questClass = "div[data-quest-id='811021'][class^='prt-quest-list btn-quest-list']"; //E Living Lightning Rod
	//public static final String questClass = "div[data-quest-id='811081'][class^='prt-quest-list btn-quest-list']"; //E Hundred-Armed Hulk
	//public static final String questClass = "div[data-quest-id='813081'][class^='prt-quest-list btn-quest-list']"; //G Goliath Vanguard
	//public static final String questClass = "div[data-quest-id='813041'][class^='prt-quest-list btn-quest-list']"; //G Temptation's Guide
	//public static final String questClass = "div[data-quest-id='814091'][class^='prt-quest-list btn-quest-list']"; //H Harbinger Tyrant
	public static final String questClass = "div[data-quest-id='814061'][class^='prt-quest-list btn-quest-list']"; //H Demanding Stormgod
	//public static final String questClass = "div[data-quest-id='814071'][class^='prt-quest-list btn-quest-list']"; //H Phantasmagoric Aberration
	//public static final String questClass = "div[data-quest-id='814081'][class^='prt-quest-list btn-quest-list']"; //H Dimensional Riftwalker
	//public static final String questClass = "div[data-quest-id='814101'][class^='prt-quest-list btn-quest-list']"; //H D Jadegleam Dragon
	//public static final String questClass = "div[data-quest-id='818061'][class^='prt-quest-list btn-quest-list']"; //L Simpering Beast
	//public static final String questClass = "div[data-quest-id='819031'][class^='prt-quest-list btn-quest-list']"; //M High-Voltage Rock
	//public static final String questClass = "div[data-quest-id='819091'][class^='prt-quest-list btn-quest-list']"; //M Princess of Dragons
	@Test
	public void farmVeritas() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		Reload reload = new Reload();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
		Results results = new Results();
		WebElement element;
		IsElementPresent ePresent = new IsElementPresent();
		
		driver.findElement(By.className("btn-head-pop")).click();  //Menu
		System.out.println("Menu");
		Thread.sleep(1000);
		driver.findElement(By.className("btn-sub-main-arcarum")).click();  //Arcarum
		System.out.println("Arcarum");
		Thread.sleep(5000);
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[5]/div[1]/div[1]/div[2]/div[2]")).click();  //ZoneE
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[4]/div[1]/div[1]/div[2]/div[4]")).click();  //ZoneG
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[4]/div[1]/div[1]/div[2]/div[5]")).click();  //ZoneH		
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[5]/div[1]/div[1]/div[2]/div[9]")).click();  //ZoneL
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[4]/div[1]/div[1]/div[2]/div[1]")).click();  //ZoneM
		System.out.println("Zone Eletio");

		/*element = driver.findElement(By.cssSelector("div[data-quest-id='814101'][class='prt-quest-list btn-quest-list']")); //D Jadegleam Dragonkin
		element.click();
		System.out.println("Class name: " + element.getAttribute("data-chapter-name"));
		Thread.sleep(5000);*/

		int maxAttempts = 100; // Optional: To prevent infinite loops
		int attempts = 0;
		int cost = 19;
		int aap = 999;
		boolean exitAtZero = true;
		boolean next = true;
		boolean turnReload = true;
		while ((attempts < maxAttempts) && next) {
			Thread.sleep(5000);
			boolean chest = ePresent.isElementPresent(driver, chestBy);			
			if (chest) {
				System.out.println("Chest");
				driver.findElement(chestBy).click();
				Thread.sleep(500);
				wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-ok")));
				driver.findElement(By.className("btn-usual-ok")).click();
				Thread.sleep(5000);
				if (ePresent.isElementPresent(driver, By.cssSelector(mimicClass))) { //Mimic
					driver.findElement(By.cssSelector(mimicClass)).click();
					Thread.sleep(5000);
					confirmTeam.confirmTeam(wait);
					autoBattle.autoBattle(driver, wait);
					results.results(driver, wait, false);					
					//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[5]")).click();  //Expedition
					Thread.sleep(5000);
				} else {
					driver.findElement(By.className("btn-usual-ok")).click();
					Thread.sleep(5000);
				}
			}

			boolean quest = ePresent.isElementPresent(driver, By.cssSelector(questClass));
			if (quest) {
				element = driver.findElement(By.cssSelector(questClass));
				element.click();
				System.out.println("Class name: " + element.getAttribute("data-chapter-name"));
				Thread.sleep(5000);
			}
			Thread.sleep(2000);
			aap = Integer.valueOf(driver.findElement(By.className("txt-stamina-after")).getText());
			System.out.println(aap);
			if (cost == 19) {
				String costString = driver.findElement(By.className("txt-stamina")).getText();
				int preNum = costString.indexOf(':');
				int postNum = costString.indexOf(">>");
				String pre = costString.substring(preNum+2, postNum-1);
				String post = costString.substring(postNum+3);
				cost = Integer.valueOf(pre) - Integer.valueOf(post);
				System.out.println("cost = " + cost);
			}
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);
			//System.out.println(cost);
			if ( cost == 20 && turnReload ) {
				wait = new WebDriverWait(driver, Duration.ofSeconds(120));
				reload.reload(driver, wait); //Do not run for 30 AAP cost enemies
			}

			//if ( attempts + 1 == maxAttempts && aap < cost ) {
			if (( attempts++ == maxAttempts ) || ( exitAtZero == true && aap == 0 )) {
				results.results(driver, wait, false);
				next = false;
				System.out.println("next = " + next);
			} else {
				results.results(driver, wait, true);
			}
			System.out.println(attempts);
		}
		System.out.println("Farm Complete");
		
	}
}

