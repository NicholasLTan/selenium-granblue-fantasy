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
	//public static final String questClass = "div[data-quest-id='812071'][class^='prt-quest-list btn-quest-list']"; //F Eyes of Sorrow
	//public static final String questClass = "div[data-quest-id='812011'][class^='prt-quest-list btn-quest-list']"; //F Trident Grandmaster
	//public static final String questClass = "div[data-quest-id='812051'][class^='prt-quest-list btn-quest-list']"; //F Faymian Fortress
	//public static final String questClass = "div[data-quest-id='813081'][class^='prt-quest-list btn-quest-list']"; //G Goliath Vanguard
	//public static final String questClass = "div[data-quest-id='813041'][class^='prt-quest-list btn-quest-list']"; //G Temptation's Guide
	//public static final String questClass = "div[data-quest-id='814091'][class^='prt-quest-list btn-quest-list']"; //H Harbinger Tyrant
	//public static final String questClass = "div[data-quest-id='814061'][class^='prt-quest-list btn-quest-list']"; //H Demanding Stormgod
	//public static final String questClass = "div[data-quest-id='814071'][class^='prt-quest-list btn-quest-list']"; //H Phantasmagoric Aberration
	//public static final String questClass = "div[data-quest-id='814081'][class^='prt-quest-list btn-quest-list']"; //H Dimensional Riftwalker
	//public static final String questClass = "div[data-quest-id='814101'][class^='prt-quest-list btn-quest-list']"; //H D Jadegleam Dragon
	//public static final String questClass = "div[data-quest-id='814051'][class^='prt-quest-list btn-quest-list']"; //H Vengeful Demigod
	//public static final String questClass = "div[data-quest-id='818061'][class^='prt-quest-list btn-quest-list']"; //L Simpering Beast
	public static final String questClass = "div[data-quest-id='818131'][class^='prt-quest-list btn-quest-list']"; //L Xeno Sagi Militis
	//public static final String questClass = "div[data-quest-id='819031'][class^='prt-quest-list btn-quest-list']"; //M High-Voltage Rock
	//public static final String questClass = "div[data-quest-id='819091'][class^='prt-quest-list btn-quest-list']"; //M Princess of Dragons
	//public static final String questClass = "div[data-quest-id='819071'][class^='prt-quest-list btn-quest-list']"; //M Parasite Steve
	@Test
	public void farmVeritas() throws InterruptedException {
		int maxAttempts = 100; // Optional: To prevent infinite loops
		boolean exitAtZero = true;
		
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		Battle battle = new Battle();
		Reload reload = new Reload();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
		Results results = new Results();
		WebElement element;
		By ok = By.className("btn-usual-ok"); 
		IsElementPresent ePresent = new IsElementPresent();
		
		//driver.findElement(By.className("btn-head-pop")).click();  //Menu
		//System.out.println("Menu");
		//Thread.sleep(1000);
		//driver.findElement(By.className("btn-sub-main-arcarum")).click();  //Arcarum
		driver.get("https://game.granbluefantasy.jp/#replicard");
		System.out.println("Arcarum");
		//Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-area-id='3']")));
		
		String areaStr = questClass.replaceAll("[^0-9]", "");
		//System.out.println(areaStr);
		String area = areaStr.substring(2,3);
		//System.out.println(area);
		int areaNum = Integer.valueOf(area) + 1;
		//System.out.println(areaNum);
		By areaBy = By.cssSelector("div[data-area-id='" + (areaNum) + "']");
		 
		driver.findElement(areaBy).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("prt-head-current")));
		System.out.println(driver.findElement(By.className("prt-head-current")).getText());
		//System.out.println("Zone Eletio");

		int attempts = 0;
		int cost = 19;
		int aap = 999;		
		boolean next = true;
		boolean turnReload = true;
		while ((attempts < maxAttempts) && next) {
			wait.until(ExpectedConditions.or(
					ExpectedConditions.urlMatches("https://game.granbluefantasy.jp/#replicard/supporter/"),
					ExpectedConditions.elementToBeClickable(By.cssSelector(questClass)),
					ExpectedConditions.elementToBeClickable(By.cssSelector(mimicClass))));
			System.out.println(driver.findElement(By.className("prt-head-current")).getText());
			boolean chest = ePresent.isElementPresent(driver, chestBy);			
			if (chest) {
				System.out.println("Chest");
				driver.findElement(chestBy).click();
				Thread.sleep(500);
				wait.until(ExpectedConditions.elementToBeClickable(ok));
				WebElement okButton = driver.findElement(ok);
				okButton.click();
				wait.until(ExpectedConditions.stalenessOf(okButton));
				Thread.sleep(500); //To prevent potentially detecting stale ok button
				wait.until(ExpectedConditions.or(
						ExpectedConditions.elementToBeClickable(By.cssSelector(mimicClass)),
						ExpectedConditions.elementToBeClickable(ok)));
						//ExpectedConditions.elementToBeClickable(By.cssSelector(questClass))));
				if (ePresent.isElementPresent(driver, By.cssSelector(mimicClass))) { //Mimic
					driver.findElement(By.cssSelector(mimicClass)).click();
					wait.until(ExpectedConditions.urlMatches("https://game.granbluefantasy.jp/#replicard/supporter/"));
					confirmTeam.confirmTeam(wait);
					battle.battle(driver, wait);
					results.results(driver, wait, false);					
				} else {
					driver.findElement(ok).click();
				}
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(questClass)));
			}
			
			if (ePresent.isElementPresent(driver, By.cssSelector("div[class='pop-open-chest']"))) {
				wait.until(ExpectedConditions.elementToBeClickable(ok));
				WebElement okButton = driver.findElement(ok);
				okButton.click();
				wait.until(ExpectedConditions.invisibilityOfAllElements(okButton));
			}
			boolean quest = ePresent.isElementPresent(driver, By.cssSelector(questClass));
			if (quest) {
				element = driver.findElement(By.cssSelector(questClass));
				element.click();
				String name = element.getAttribute("data-chapter-name");
				System.out.println("Class name: " + name);
				if (name.endsWith("Militis")) {
					wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-offer")));
					driver.findElement(By.className("btn-offer")).click();
				}
				
			}

			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("txt-stamina-after")));
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
			battle.battle(driver, wait);
			if ( cost == 20 && turnReload ) {
				wait = new WebDriverWait(driver, Duration.ofSeconds(120));
				//reload.reload(driver, wait); //Do not run for 30 AAP cost enemies
			}
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

