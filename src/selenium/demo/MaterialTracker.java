package selenium.demo;

import org.openqa.selenium.*;

public class MaterialTracker {
	public boolean materialTracker(WebDriver driver, String itemID) throws InterruptedException {
		WebElement fav = driver.findElement(By.cssSelector("figure[data-item-id='" + itemID + "']"));
		String favString = fav.findElement(By.className("txt-treasure-num")).getText();
		int slashIndex = favString.indexOf("/");
		int favCurr = Integer.valueOf(favString.substring(0, slashIndex));
		int favMax = Integer.valueOf(favString.substring(slashIndex+1));
		System.out.println(favString);
		if ( favCurr >= favMax ) {
			return false; //Count reached, exit
		} else {
			return true; //Continue
		}		
	}
}