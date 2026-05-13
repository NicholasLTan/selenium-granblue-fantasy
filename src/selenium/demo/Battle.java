package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Battle {
	public void battle(WebDriver driver, WebDriverWait wait) throws InterruptedException {
		By ok = By.className("btn-usual-ok");
		By popup = By.className("pop-usual");
		String raidStr = "https://game.granbluefantasy.jp/#raid";
		String resultStr = "https://game.granbluefantasy.jp/#result";
		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		AutoBattle autoBattle = new AutoBattle();
		autoBattle.autoBattle(driver, shortWait);
		
		wait.until(ExpectedConditions.or (
				ExpectedConditions.urlContains(raidStr),
				ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#result_multi/empty")
		));
		List<WebElement> purpleSkills = driver.findElements(By.cssSelector("div[class^='lis-ability-state'][type='5'][state='2']"));
		if ( !purpleSkills.isEmpty() ) {
		    for ( int i = 0 ; i < purpleSkills.size() ; i++ ) {
		        WebElement purple = purpleSkills.get(i);
		        System.out.println("Purple skill @ pos " + purple.findElement(By.xpath("./../..")).getAttribute("pos"));
		        if (!purple.findElement(By.xpath("./../..")).getAttribute("pos").isEmpty()) {
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
		while (driver.getCurrentUrl().startsWith(raidStr)) {
			wait.until(ExpectedConditions.or(
					ExpectedConditions.visibilityOfElementLocated(popup),
					ExpectedConditions.urlContains(resultStr),
					ExpectedConditions.elementToBeClickable(By.className("btn-result"))));
			List<WebElement> elementPopup = driver.findElements(popup);
			List<WebElement> elementResult = driver.findElements(By.className("btn-result"));
			if (driver.getCurrentUrl().contains(resultStr)) {
				return;
			} else if (!elementPopup.isEmpty() && elementPopup.get(0).isDisplayed() ) {
				System.out.println("Battle OK Click");
				elementPopup.get(0).findElement(ok).click();
				wait.until(ExpectedConditions.stalenessOf(elementPopup.get(0)));
				driver.findElement(By.className("btn-treasure-footer-reload")).click();
				return;
			} else if ( !elementResult.isEmpty() && elementResult.get(0).isDisplayed() ) {
				System.out.println("Battle Next");
				elementResult.get(0).click();
				wait.until(ExpectedConditions.urlContains(resultStr));
			}
		}
		if (!driver.getCurrentUrl().startsWith(raidStr)) {
			return;
		}
	}
}
