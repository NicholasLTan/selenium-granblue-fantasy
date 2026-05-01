package selenium.demo;

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
		AutoBattle autoBattle = new AutoBattle();
		autoBattle.autoBattle(driver, wait);
		//activate purple skills
		  
		List<WebElement> purpleSkills = driver.findElements(By.cssSelector("div[class^='lis-ability-state'][type='5'][state='2']"));
		if ( !purpleSkills.isEmpty() ) {
		    for ( int i = 0 ; i < purpleSkills.size() ; i++ ) {
		        WebElement purple = purpleSkills.get(i);
		        System.out.println("pos = " + purple.findElement(By.xpath("./../..")).getAttribute("pos"));
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
		/*
		if ( purpleSkills.size() > 0 ) {
		    for ( int i = 0 ; i < purpleSkills.size() ; i++ ) {		        
		        WebElement purple = purpleSkills.get(i);
		        if ( !purple.getAttribute("pos").isEmpty() ) { 
		        	purple.click();
		        }
		    } 
		}
		  */
		  
		  
		  //<div class="lis-ability-state ability2" state="1" type="3"></div>
		 
		
		while (driver.getCurrentUrl().startsWith("https://game.granbluefantasy.jp/#raid")) {
			wait.until(ExpectedConditions.or(
					ExpectedConditions.visibilityOfElementLocated(popup),
					ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#result")));
			List<WebElement> elementPopup = driver.findElements(popup);
			if (driver.getCurrentUrl().contains("https://game.granbluefantasy.jp/#result")) {
				return;
			} else if (!elementPopup.isEmpty() && elementPopup.get(0).isDisplayed() ) {
				System.out.println("Battle OK Click");
				elementPopup.get(0).findElement(ok).click();
				wait.until(ExpectedConditions.stalenessOf(elementPopup.get(0)));
				driver.findElement(By.className("btn-treasure-footer-reload")).click();
				return;
			}
		}
		if (!driver.getCurrentUrl().startsWith("https://game.granbluefantasy.jp/#raid")) {
			return;
		}
	}
}
