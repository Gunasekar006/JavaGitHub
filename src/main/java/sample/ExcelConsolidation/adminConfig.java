package sample.ExcelConsolidation;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import basePack.UtlityClass;

public class adminConfig {
	WebDriver driver;
	public UtlityClass utlity;

	public adminConfig(WebDriver driver) {
		this.driver = driver;

	}

	public String btnAdminConfig = "//*[@id='btnadmin']";
	public String btnOk = "//*[@id='confirmOk']";

	public void adminConfigscreen() {
		 utlity = new UtlityClass(driver);
		System.out.println("Adminconfig instance: " + this.driver);
		System.out.println("admin config loaded");
		utlity.click(btnAdminConfig);
		utlity.click(btnOk);
		utlity.click(btnOk);
		Assert.assertEquals(driver.getTitle(), "Index", "Admin Config Loaded");
			
		
	}

	public void adminConfigReferenceMapping() {
		//*[contains(text(),'References')]
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
