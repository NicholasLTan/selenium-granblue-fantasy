package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Battle {
	public void battle(WebDriver driver, WebDriverWait wait, boolean OneTurn) throws InterruptedException {
		By ok = By.className("btn-usual-ok");
		By popup = By.className("pop-usual");
		String raidStr = "https://game.granbluefantasy.jp/#raid";
		String resultStr = "https://game.granbluefantasy.jp/#result";
		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		AutoBattle autoBattle = new AutoBattle();		
		Reload reload = new Reload();

		wait.until(ExpectedConditions.or (
				ExpectedConditions.urlContains(raidStr),
				ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#result_multi/empty")
				));
		List<WebElement> purpleSkills = driver.findElements(By.cssSelector("div[class^='lis-ability-state'][type='5'][state='2']"));
		if ( !purpleSkills.isEmpty() && !driver.findElement(By.className("name")).getText().contains("Perfected Alchemist")) {
			for ( int i = 0 ; i < purpleSkills.size() ; i++ ) {
				WebElement purple = purpleSkills.get(i);
				System.out.println("Purple skill @ pos " + purple.findElement(By.xpath("./../..")).getAttribute("pos"));
				if (!purple.findElement(By.xpath("./../..")).getAttribute("pos").isEmpty()) {
					wait.until(ExpectedConditions.elementToBeClickable(purple));
					purple.click();
					Thread.sleep(2500);
					//wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='btn-ability-skip']")));
					/*if ( driver.findElement(By.className("prt-ability-skip")).getAttribute("active").equals("1") ) {
		        		driver.findElement(By.className("btn-ability-skip")).click();
		        	}*/

					purpleSkills = driver.findElements(By.cssSelector("div[icon-type='5']"));
					if ( !purpleSkills.isEmpty() ) {
						purpleSkills.get(0).click();
					}
					driver.findElement(By.className("btn-command-back")).click();
				}
			} 
		}
		autoBattle.autoBattle(driver, shortWait);
		//try {
			while (driver.getCurrentUrl().startsWith(raidStr)) {
				wait.until(ExpectedConditions.or(
						ExpectedConditions.attributeContains(By.cssSelector("div[class^='btn-attack-start']"), "class", "display-off"),
						ExpectedConditions.visibilityOfElementLocated(popup),
						ExpectedConditions.urlContains(resultStr),
						ExpectedConditions.elementToBeClickable(By.className("btn-result"))));
				List<WebElement> elementPopup = driver.findElements(popup);
				List<WebElement> elementResult = driver.findElements(By.className("btn-result"));
				List<WebElement> elementStart = driver.findElements(By.cssSelector("div[class^='btn-attack-start']"));
				List<WebElement> elementWave = driver.findElements(By.className("txt-info-num"));
				if (!elementStart.isEmpty()) {}
				if (driver.getCurrentUrl().contains(resultStr)) {
					System.out.println("Battle GoTo Result");
					return;
				} else if (driver.getCurrentUrl().contains("raid_multi") && !elementPopup.isEmpty() && elementPopup.get(0).isDisplayed() ) { //going stale here
					System.out.println("Battle Popup OK Click");
					elementPopup.get(0).findElement(ok).click();
					wait.until(ExpectedConditions.stalenessOf(elementPopup.get(0)));
					driver.findElement(By.className("btn-treasure-footer-reload")).click();
					return;
				} else if (driver.getCurrentUrl().contains("raid_multi") && !elementResult.isEmpty() && elementResult.get(0).isDisplayed() ) {
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
						(elementWave.isEmpty() || elementWave.get(0).findElement(By.cssSelector("> div:nth-child(1)")).getAttribute("class").equals(elementWave.get(0).findElement(By.cssSelector("> div:nth-child(2)")).getAttribute("class"))) &&
						!elementStart.isEmpty() && elementStart.get(0).getAttribute("class").endsWith("display-off") ) {
					System.out.println("Start invisible, reloading");
					reload.reload(driver, wait);
				}
			}
			if (!driver.getCurrentUrl().startsWith(raidStr)) {
				return;
			}/*
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
		}*/
	}
}
