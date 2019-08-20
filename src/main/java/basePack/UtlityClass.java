package basePack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UtlityClass {
	
	WebDriverWait wait;
	WebDriver driver;
	Select select;
	public UtlityClass(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		
		System.out.println("utlity instance: "+this.driver);
	}

	public void click(String ele){
		System.out.println("utlity loaded");
		
		wait=new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ele))).click();
	}
	
	public void selectFromDD() {
		WebElement ele=driver.findElement(By.xpath(""));
		wait.until(ExpectedConditions.elementToBeSelected(ele));
	select.selectByVisibleText("");	
		
	
	}

}
