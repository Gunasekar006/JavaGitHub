package sample.ExcelConsolidation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByPartialLinkText;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Hello world!
 *
 */
public class App {

	String templateType = "//*[@id='apptype']";
	String tempName = "//*[@id='applicationname']";
	public WebDriver driver;

	public XSSFWorkbook book;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public int rowNum;
	public int columnNum;
	public int lastRow;
	public int lastColumn;
	public Properties prop;

	@BeforeSuite
	public void loadData() throws IOException {

		// Property declaration
		prop = new Properties();
		FileInputStream srcProp = new FileInputStream("Resource/config.properties");
		prop.load(srcProp);

	}

	@BeforeTest
	public void initializeDriver() throws IOException {

		String url = prop.getProperty("url");
		File src = new File("C:/Users/gkumarasamy/Desktop/valid_1.xlsx");
		FileInputStream fis = new FileInputStream(src);
		book = new XSSFWorkbook(fis);

		System.setProperty("webdriver.chrome.driver", "src/main/java/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		System.out.println("App instance: " + this.driver);

	}

	@Test
	public void enterData() throws InterruptedException {
		sheet = book.getSheetAt(0);
		lastRow = sheet.getLastRowNum();
		lastColumn = sheet.getRow(0).getLastCellNum();
		System.out.println("Last Column: " + lastColumn);
		System.out.println("Total entry:" + lastRow);

		Date d = new Date();
		SimpleDateFormat sm = new SimpleDateFormat("ddMMyyyyhhmmss");
		String name = sm.format(d);
		driver.findElement(By.xpath(tempName)).sendKeys("ztest" + name);

		click("//*[@id='example']/tbody/tr[1]/td[1]/input");
		System.out.println("Waiting");
		Thread.sleep(2000);
		int execelRow = 1;
		int appCol;

		System.out.println("*******************");
		for (rowNum = 1; rowNum <= 50; rowNum++) {

			for (columnNum = 0; columnNum < lastColumn; columnNum++) {
				System.out.println(rowNum);
				appCol = columnNum + 1;
				Thread.sleep(1000);
				sheet.getRow(execelRow).getCell(columnNum).setCellType(CellType.STRING);
				String val = sheet.getRow(execelRow).getCell(columnNum).getStringCellValue();
				if (!val.isEmpty()) {
					String tagName = driver
							.findElement(By.xpath("//*[@id='example']/tbody/tr[" + rowNum + "]/td[" + appCol + "]/*"))
							.getTagName();
					Thread.sleep(1000);
					if (tagName.contains("select")) {
						Select select = new Select(driver.findElement(
								By.xpath("//*[@id='example']/tbody/tr[" + rowNum + "]/td[" + appCol + "]/select")));
						select.selectByVisibleText(val);

					} else {
						String appRowEntry = "//*[@id='example']/tbody/tr[" + rowNum + "]/td[" + appCol + "]/"
								+ tagName;
						enterValue(appRowEntry, val);
					}

				} else {
					System.out.println("no entry added");
				}
			}
			execelRow++;
			if (rowNum >= 50) {
				driver.findElement(By.partialLinkText("Next")).click();
				System.out.println("90909090");
				rowNum = 0;
				appCol = 0;
				columnNum = 0;
				click("//*[@id='confirmOk']");
			}
			if (lastRow < execelRow) {
				System.out.println("All data entered");
				// driver.findElement(By.xpath()).click();
				click("//*[@id='btnsave']");
				click("//*[@id='confirmOk']");
				click("//*[@id='confirmOk']");
				break;
			}
		}

		adminConfig adminconfig = new adminConfig(driver);
		adminconfig.adminConfigscreen();

	}

	private void click(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(string))).click();

	}

	private void selectFromDropdown() {

	}

	private void enterValue(String appRow, String val) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appRow))).sendKeys(val);
		;

	}

	private void selectfromDorpdown(String fieldname, String fieldVal) {
		Select select;
		switch (fieldname.toLowerCase()) {
		case "applicationtype":
			select = new Select(driver.findElement(By.xpath("//*[@id='apptype']")));
			select.selectByVisibleText(fieldVal);
			break;
		case "dataType":
			select = new Select(driver.findElement(By.xpath("//*[@id='example']/tbody/tr[1]/td[2]")));
			select.selectByVisibleText(fieldVal);
			break;
		case "mandatory":
			select = new Select(driver.findElement(By.xpath("//*[@id='example']/tbody/tr[1]/td[4]/select")));
			select.selectByVisibleText(fieldVal);
			break;

		default:
			System.out.println("Pls check field name");

		}

	}

}
