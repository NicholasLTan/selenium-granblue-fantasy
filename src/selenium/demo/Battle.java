package selenium.demo;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Battle {
	@SuppressWarnings("null")
	public void battle(@NonNull WebDriver driver, WebDriverWait wait, boolean OneTurn) throws InterruptedException {
		By ok = By.className("btn-usual-ok");
		By popup = By.className("pop-usual");
		String raidStr = "https://game.granbluefantasy.jp/#raid";
		String resultStr = "https://game.granbluefantasy.jp/#result";
		WebDriverWait shortWait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(30)));
		AutoBattle autoBattle = new AutoBattle();		
		Reload reload = new Reload();
		boolean usePurple = true;

		wait.until(ExpectedConditions.or (
				ExpectedConditions.urlContains(raidStr),
				ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#result_multi/empty")//,
				//ExpectedConditions.visibilityOfElementLocated(popup)
				));
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl == null || !currentUrl.startsWith(raidStr)) {
			return;
		}
		if (!driver.findElements(popup).isEmpty()) {
			if (driver.findElement(By.className("prt-popup-header")).getText().equals("Battle Concluded")) 
			{	driver.findElement(ok).click();
				reload.reload(driver, wait);
				return;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-attack-start")));
		try {
			autoBattle.autoBattle(driver, shortWait);
		} catch (ElementClickInterceptedException e) {
			reload.reload(driver, wait);
			return;
		}
		if (usePurple) {
			List<WebElement> purpleSkills = driver.findElements(By.cssSelector("div[class^='lis-ability-state'][state='2'][type='5']"));
			System.out.println(purpleSkills.size() + " purple skills");
			if ( !purpleSkills.isEmpty() && !driver.findElement(By.className("name")).getText().contains("Perfected Alchemist")) {
				for ( int i = 0 ; i < purpleSkills.size() ; i++ ) {
					WebElement purple = purpleSkills.get(i);
					System.out.println("Purple skill @ pos " + purple.findElement(By.xpath("./../..")).getAttribute("pos"));
					String posAttr = purple.findElement(By.xpath("./../..")).getAttribute("pos");
					if (posAttr != null && !posAttr.isEmpty()) {
						wait.until(ExpectedConditions.elementToBeClickable(purple));
						if (!driver.findElements(By.cssSelector("div[class^='prt-raid-log']")).isEmpty()) {
							WebElement raidLog = driver.findElement(By.cssSelector("div[class^='prt-raid-log']"));
							System.out.println("Battle purple style=" + raidLog.getAttribute("style"));
							wait.until(ExpectedConditions.attributeToBe(raidLog, "style", "display: none;"));
							System.out.println("Battle purple style=" + raidLog.getAttribute("style"));
						}
						wait.until(ExpectedConditions.attributeToBe(By.id("command-mask"), "style", "display: none;"));
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("prt-mask")));
						//<div class="active-mask" id="command-mask" style="display: block;"></div>
						purple.click();
						Thread.sleep(2500);
						//wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='btn-ability-skip']")));
						/*if ( driver.findElement(By.className("prt-ability-skip")).getAttribute("active").equals("1") ) {
		        		driver.findElement(By.className("btn-ability-skip")).click();
		        	}*/
					/*	<div class="prt-raid-log log-multipop log-battle" style="display: block;">
						<div class="txt-title">Battle Log</div>
						<div class="prt-line"></div>
						<div class="txt-body">Lvl 120 Qilin's special ability took effect!</div>
					</div> */
						purpleSkills = driver.findElements(By.cssSelector("div[icon-type='5']"));
						if ( !purpleSkills.isEmpty() ) {
							purpleSkills.get(0).click();
						}
						driver.findElement(By.className("btn-command-back")).click();
					}
				}
			}
		}		
		try {
			while (driver.getCurrentUrl().startsWith(raidStr)) {
				wait.until(ExpectedConditions.or(
						ExpectedConditions.and(
								ExpectedConditions.elementToBeClickable(By.className("btn-temporary")),
								ExpectedConditions.attributeContains(By.cssSelector("div[class^='btn-attack-start']"), "class", "display-off")),
						ExpectedConditions.visibilityOfElementLocated(popup),
						ExpectedConditions.urlContains(resultStr),
						ExpectedConditions.elementToBeClickable(By.className("btn-result"))));
				List<WebElement> elementPopup = driver.findElements(popup);
				List<WebElement> elementResult = driver.findElements(By.className("btn-result"));
				List<WebElement> elementStart = driver.findElements(By.cssSelector("div[class^='btn-attack-start']"));
				List<WebElement> elementWave = driver.findElements(By.cssSelector("div[id='prt-wave-num'] > div[class='txt-info-num'] > div"));
				if (!elementStart.isEmpty()) {}
				if (driver.getCurrentUrl().contains(resultStr)) {
					System.out.println("Battle GoTo Result");
					return;
				} else if (driver.getCurrentUrl().contains("raid_multi") && !elementPopup.isEmpty() && elementPopup.get(0).isDisplayed() ) { //going stale here
					System.out.println("Battle Popup OK Click");
					elementPopup.get(0).findElement(ok).click();  //no such element exception
					wait.until(ExpectedConditions.stalenessOf(elementPopup.get(0)));
					driver.findElement(By.className("btn-treasure-footer-reload")).click();
					return;
				} else if (driver.getCurrentUrl().contains("raid") && !elementResult.isEmpty() && elementResult.get(0).isDisplayed() ) {
					System.out.println("Battle Next found, size:" + elementResult.size());
					Thread.sleep(2500); //wait for auto to potentially proceed to results 
					//List<WebElement> elementAuto = driver.findElements(By.className("btn-auto"));
					//if ( elementAuto.isEmpty() || !elementAuto.get(0).isDisplayed() ) {
					if (driver.getCurrentUrl().contains(raidStr)) {
						System.out.println("Battle Next, completing stalled battle");
						elementResult.get(0).click();
						wait.until(ExpectedConditions.urlContains(resultStr));	
					}				
				} else if ( OneTurn && 
						(elementWave.isEmpty() || elementWave.get(0).getAttribute("class").equals(elementWave.get(1).getAttribute("class"))) &&
						!elementStart.isEmpty() && elementStart.get(0).getAttribute("class").endsWith("display-off") ) {
					System.out.println("Start invisible, reloading");
					reload.reload(driver, wait);
				}
			}
			if (!driver.getCurrentUrl().startsWith(raidStr)) {
				return;
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Battle StaleElementReferenceException GoTo Return");
			List<WebElement> elementResult = driver.findElements(By.className("btn-result"));
			if ( !driver.getCurrentUrl().startsWith(raidStr) ) {
				return;
			} else if ( !elementResult.isEmpty() && elementResult.get(0).isDisplayed() ) {
				Thread.sleep(2500); //wait for auto to potentially proceed to results 
				//List<WebElement> elementAuto = driver.findElements(By.className("btn-auto"));
				//if ( elementAuto.isEmpty() || !elementAuto.get(0).isDisplayed() ) {
				if (driver.getCurrentUrl().contains(raidStr)) {
					System.out.println("Battle Next, completing stalled battle");
					elementResult.get(0).click();
					wait.until(ExpectedConditions.urlContains(resultStr));	
				}
				return;
			}
		} catch (NoSuchElementException e) {
			System.out.println("Battle NoSuchElementException Wait Return");
			if (!driver.getCurrentUrl().contains(raidStr)) {
				System.out.println("Battle !RaidURL");
				wait.until(ExpectedConditions.urlContains(resultStr));
				System.out.println("Battle -> ResultURL");
			}
			return;
		} catch (TimeoutException e) {
			if (driver.getCurrentUrl().startsWith(raidStr)) {
				reload.reload(driver, wait);
				return;
			}
			
		}
	}
}
