package com.astm.commonFunctions;

import base.utils.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.qameta.allure.Attachment;
import java.io.File;
import java.nio.file.Files;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PublicCommon {

	private WebDriver driver;
	static Logger log = Logger.getLogger(PublicAdminCommons.class.getName());
	public PublicCommon(WebDriver driver) {
		this.driver = driver;
	}

	static ExcelDataConfig edc;
	public static String productMatrixFilePath = ConfigReader.getValue("ProductMatrixSheet");
	public static String webdataMatrixFilePath = ConfigReader.getValue("WebDataExcelPath");
	public static String micFilePath = ConfigReader.getValue("MICWebDataExcelPath");

	public static int columnForProductKeyWordLookUp =Integer.parseInt(ConfigReader.getValue("columnForProductKeyWordLookUp"));
	public static int columnForCredentialKeyWordLookUp = 0;
	//Integer.parseInt(ConfigReader.getValue("columnForProductKeyWordLookUp"));
	private static FileInputStream fs = null;
	private static Sheet st = null;
	private static Workbook wb = null;
	private static String excelextensionxlsx = ".xlsx";
	private static String excelextensionxls = ".xls";

	public static final String STDProducts = "STDProducts";
	public static final String TrainProducts = "TrainProducts";
	public static final String PTPProducts = "PTPProducts";
	public static final String TPTProducts = "TPTProducts";
	public static final String MEMProducts = "MembershipProducts";
	public static final String Journals = "Journals";
	public static final String MarketingPages = "MarketingPages";
	public static final String WhiteLabelStore="WhiteLabelStore";
	public static final String credentials = "Credentials";
	public static final String CredentialsMap = "CredentialsMap";
	public static final String PubBOSProduct = "PUB_BOS_Products";

	public static final String ActiveMembershipAccounts = "ActiveMembershipAccounts";
	public static final String MigratedAccounts = "MigratedAccounts";
	public static final String CommonUrls = "CommonUrls";
	public static final String Admin = "AdminUrl";
	public static final String micQueries = "CommonDBQueries";
	public static final String INTQueries = "CommonINTDBQueries";
	public static final String adjunctProducts = "AdjunctProducts";
	public static final String royaltyProducts = "RoyaltyProducts";
	public static final String compilationProducts = "CompilationProducts";
	public static final String PubChildProduct = "PubChildProduct";
	public static final String productUrl = "ProductUrl";
	public static final String MiscProducts= "MiscProducts";
	public static final String PublicationChildProduct="PublicationChildProduct";
	public static final String PCChildProducts ="3PCChildProducts";
	public static final String Mem_Product="MEM_Product";
	public static final String MembershipProducts="MembershipProducts";
	public static String environmentSelector() {
		String envConfig = ConfigReader.getValue("BASE_URL_PUB_ASTM");
		if(envConfig.contains("stage"))
			return "stage";
		else if(envConfig.contains("dev"))
			return "dev";
		else if(envConfig.contains("www"))
			return "www";
		else
			return "qa";
	}

	public static String configRead(String filePath, String key) {
		File file = new File(filePath); //src\\test\\resources\\keycodes.properties

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		//props.put("CPU", "Snapdragon855");

		try {
			prop.load(fileInput);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prop.getProperty(key);

	}

	public static String configWrite(String filePath, String key, String value) {
		//File file = new File(filePath); //src\\test\\resources\\keycodes.properties
		FileOutputStream outputStream;

		//FileInputStream fileInput = null;
		try {
			outputStream = new FileOutputStream(filePath);

			Properties prop = new Properties();



			prop.put(key, value);
			prop.store(outputStream, "Added to Property File");

			return prop.getProperty(key);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}


	}

	@Step("Evaluating Text Content on page for {validationOnPage}- Expected: {expectedText}, Actual: {actualText}")
	public static boolean verifyElementTextOnPage(String validationOnPage, String actualText, String expectedText) {

		return actualText.equals(expectedText);

	}

	@Step("Evaluating Content on page for {validationOnPage}- Expected: {expected}, Actual: {actual}")
	public static boolean verifyContentOnPage(String validationOnPage, String actual, String expected) {
		return actual.contains(expected);
	}
	@Step("Evaluating Content on page for {validationOnPage}- Expected: {expected}, Actual: {actual}")
	public static boolean verifyContentOnPageRegExp(String validationOnPage, String actual, String expected) {
		return actual.matches(expected);
	}

	@Step("Validating element: {0} is Visible")
	public static boolean verifyElementIsVisible(String elementName,WebElement element, WebDriver driver) {

		WaitStatementUtils.explicitWaitForVisibility(driver, element);
		Boolean isDisplayed = element.isDisplayed();

		return isDisplayed;

	}

	@Step("Verify title of page opened is '{expectedTitle}'")
	public static boolean compareTitle(String expectedTitle, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.titleContains(expectedTitle));

			return driver.getTitle().contains(expectedTitle);
		}
		catch (Exception e) {
			return false;
		}
	}

	public static boolean compareUrl(String expectedUrl, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.urlContains(expectedUrl));
			return driver.getCurrentUrl().contains(expectedUrl);
		}
		catch (Exception e) {
			return false;
		}
	}

	@Step("Exception Encountered in Step: {exceptionString}")
	public static void reportExceptionToResult(String exceptionString) {
		System.out.println("Catch Block Triggered: " + exceptionString);
		allureStepFail();
		// assertTrue(false);

	}

	public static void allureStepFail() {
		Allure.getLifecycle().updateStep(stepResult -> stepResult.setStatus(Status.FAILED));
		Allure.getLifecycle().stopStep();
	}

	public static void exceptionHandlingBlock(Exception e, Logger log, String message) {
		try {
			e.printStackTrace();
			log.info(e.toString());
			reportFailAssert(message, e);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	@Step("Exception Occured in Test: Message: {message}  ")
	public static void reportFailAssert(String message, Exception realCause) {
		try {
			Assert.fail(message, realCause);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/*	*//*public static boolean isFileDownloaded(String downloadPath, String fileName, WebDriver driver) {
		Boolean flag = false;
		try {
			Thread.sleep(10000);
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path folder = Path.of(downloadPath);
			folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

			*//**//*WatchKey key = watchService.take();
			if (key != null) {
				key.pollEvents();
				key.reset();
			}*//**//*
			File dir = new File(downloadPath);
			File[] dirContents = dir.listFiles();
			WaitStatementUtils.implicitWaitForSeconds(driver, 10);

			String actual;
			String expected = fileName;
			int fileCount = dirContents.length;
			for (int i = 0; i < fileCount; i++) {
				actual = dirContents[i].getName().trim();
				System.out.println(actual);
				System.out.println(expected);
				if (expected.contains(actual)) {
					// File has been found, it can now be deleted:
					Allure.addAttachment("Downloaded File found: ", fileName);
					dirContents[i].delete();
					flag = true;
				} else {
					Allure.addAttachment("Downloaded File not found: ", fileName);
					flag = false;
				}
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return flag;
	}*/
	@Step("Exception Occured in Test: Message: {message}  ")
	public static void reportFailAssertError(String message, AssertionError realCause){
		try {
			Assert.fail(message, realCause);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method converts the list of Webelements into List of String It uses the
	 * getText() to get the text from weblement if exists
	 */
	public static List<String> getListOfTextFromListOfWebelements(List<WebElement> elementsList, WebDriver driver) {

		List<String> textList = new ArrayList<String>();
		for (WebElement item : elementsList) {
			if(item.getText().isEmpty()==false)
				textList.add(item.getText().trim());
		}
		return textList;
	}

	public static List<String> getListOfTextFromListOfWebelementsAndRemoveBrackets(List<WebElement> elementsList, WebDriver driver) {

		List<String> textList = new ArrayList<String>();

		for (WebElement item : elementsList) {
			if(item.getText().isEmpty()==false)
				textList.add(item.getText().trim());
			System.out.println(item.getText().replaceAll("()","").trim());
		}
		return textList;
	}


	/*
	 * This method converts the list of Webelements into List of String It uses the
	 * getText() to get the text from weblement if exists
	 */
	public static List<String> getListOfTextFromListOfWebelementsForMobile(List<WebElement> elementsList,
																		   WebDriver driver) {

		List<String> dropdownValue = new ArrayList<String>();
		for (WebElement item : elementsList) {
			System.out.println(item.getAttribute("textContent"));
			dropdownValue.add(item.getAttribute("textContent"));
		}
		return dropdownValue;
	}


	/*
	 * This method requires sheetName, filePath and Column Name. returns the Column
	 * number.
	 */
	public static int getKeyCellNumber(String sheetName, String filePath, String key, ExcelDataConfig edc) {

		int sheetNO = edc.getSheetIndex(sheetName);
		int cellNum = 0;
		while (!edc.readExcelData(sheetNO, 0, cellNum).equalsIgnoreCase(key)) {
			if (cellNum > edc.getCellCount(sheetNO, 1))
				return -1;
			else
				cellNum++;
		}
		return cellNum;
	}

	/*
	 * This method requires sheetName, filepath, listSize and columnName returns
	 * List of string present in the column.
	 */
	public static List<String> getListFromExcel(String sheetName, String filePath, int listSize, String key) {

		edc = new ExcelDataConfig(filePath);
		int sheetNO = edc.getSheetIndex(sheetName);

		List<String> list = new ArrayList<String>();

		int colNum = getKeyCellNumber(sheetName, filePath, key, edc);
		System.out.println("Column Number::::" + colNum);
		for (int i = 1; i <= listSize; i++) {
			list.add(edc.readExcelData(sheetNO, i, colNum));
		}
		System.out.println("fetched list::::::" + list);
		return list;

	}

	public static List<HashMap<String, String>> getAllData(String filepath, String sheetName){
		int mapKeyRow = 0;
		int mapValueRow = 0;
		edc = new ExcelDataConfig(filepath);
		int sheetNO = edc.getSheetIndex(sheetName);
		int rowcount = edc.getRowCount(sheetName);
		//Object[][] obj = new Object[rowcount - 1][1];
		List<HashMap<String, String>> dataList = new ArrayList<>();

		for (int i = 1; i < rowcount; i++) {
			int cellCount = edc.getCellCount(sheetNO, i);
			HashMap<String, String> datamap = new HashMap<>();
			for (int j = 0; j < cellCount; j++) {
				datamap.put(edc.readExcelData(sheetNO, mapKeyRow, j), edc.readExcelData(sheetNO, mapValueRow + i, j));
			}
			dataList.add(datamap);
		}

		//System.out.println("My Data: "+datamap);
		return dataList;
	}

	/*
	 * Create dynamic xpath and return By operator
	 *
	 * @parametres: xpathString: generic xpath string with dynamic portion
	 * replaceThis: part of string to be replaced in xpath string replaceWith: value
	 * to replace the existing string portion to create dynamic xpath at runtime
	 *
	 * @returns: By Locator
	 *
	 * @author: Swapnil
	 */
	public static By createByOperatorXpath(String xpathString, String replaceThis, String replaceWith) {

		try {
			String newXpath = xpathString.replace(replaceThis, replaceWith);
			System.out.println("New Dynamic xpath: " + newXpath);
			return By.xpath(newXpath);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static WebElement createDynamicWebElement(WebDriver driver, String xpathString, String replaceThis, String replaceWith) {

		try {
			String newXpath = xpathString.replace(replaceThis, replaceWith);
			System.out.println("New Dynamic xpath: " + newXpath);

			By locator = By.xpath(newXpath);
			WebElement element = driver.findElement(locator);;
			return element;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static boolean waitForNumberOfElementsToBe(final WebDriver driver, String xpath,
													  int numberOfElementsExpected, int timeout) {
		try {
			new WebDriverWait(driver, timeout).pollingEvery(Duration.ofMillis(2000))
					.until(ExpectedConditions.numberOfElementsToBe(By.xpath(xpath), numberOfElementsExpected));
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/*
	 * This method takes grand totol price on page and calculates the tax and
	 * returns it based on the tax rate.
	 *
	 * @Parametres: totalPrice: total price on which tax is to be calculated,
	 * taxRate: rate of tax
	 *
	 * @returns: calculated tax amount
	 *
	 * @Author: Swapnil
	 */
	public double calculateTax(double totalPrice, double taxRate) {
		try {
			return decimalFormat(((totalPrice * taxRate) / (double) 100));
		} catch (Exception ex) {
			ex.printStackTrace();
			// log.info(ex.toString());
			return 0;
		}
	}

	/*
	 * This method takes a double value number as parametre and returns it after
	 * round it off to two decimal places.
	 *
	 * @Parametres: number- double value to round off (Please not that the precision
	 * of the input number is defined upto 3 decimal places. For calculations
	 * requiring higher initial precision the precision can be changed in df.
	 *
	 * @Author: Swapnil
	 */
	public double decimalFormat(double number) {
		DecimalFormat df = new DecimalFormat("#.###");
		return Math.round((Double.valueOf(df.format(number))) * 100) / 100;
	}

	public static HashMap<String, String> getMapperData(String filepath, String sheetName) {
		int mapKeyRow = 0;
		int mapValueRow = 0;
		edc = new ExcelDataConfig(filepath);
		int sheetNO = edc.getSheetIndex(sheetName);
		int rowcount = edc.getRowCount(sheetName);
		// Object[][] obj = new Object[rowcount - 1][1];
		HashMap<String, String> datamap = new HashMap<String, String>();

		for (int i = 1; i < rowcount; i++) {
			int cellCount = edc.getCellCount(sheetNO, i);
			for (int j = 0; j < cellCount; j++) {
				String dataInLoop = edc.readExcelData(sheetNO, mapValueRow + i, j);
				if (!dataInLoop.trim().equals("")) {
					datamap.put(edc.readExcelData(sheetNO, mapKeyRow, j),
							edc.readExcelData(sheetNO, mapValueRow + i, j));
				}
			}

		}

		// System.out.println("My Data: "+datamap);
		return datamap;
	}

	public static void waitForSec(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * This methods helps in getting the text from Textbox
	 *
	 */
	public static String JSExecutorGetText(WebElement elementForText,WebDriver driver) {
		String text;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String myExression = "return arguments[0].value;";
			text = (String) js.executeScript(myExression, elementForText);

			System.out.println("JS Retrieved Text: " + text);
			return text;
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
	}

	/*
	 * This methods helps in getting the text from Textbox
	 *
	 */
	public String JSExecutorGetText(WebDriver driver, WebElement elementForText) {
		String text;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String myExression = "return arguments[0].value;";
			text = (String) js.executeScript(myExression, elementForText);

			System.out.println("JS Retrieved Text: " + text);
			return text;
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
	}

	/*
	 * Javascript Executor Click
	 *
	 * @Parametres: Webelement to click
	 *
	 * @Author: Swapnil
	 */
	public static void JSClick(WebElement element, WebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Selects the value in the dropdown by Visible Text
	 *
	 * @Parameters: Webelement i.e dropdown and the option's text to select
	 */
	public static boolean selectValueInDropdown(WebElement element, String value) {
		try {
			Select select = new Select(element);
			select.selectByVisibleText(value);
			element.click();
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			e.getStackTrace();
			return false;
		}
	}

	public static boolean selectValueInDropdown_Contains(WebElement element, WebElement element1,WebDriver driver) {
		try {
			element.click();
			PublicAdminCommons.scrollToElement(driver,element1);
			JSClick(element1,driver);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			e.getStackTrace();
			return false;
		}
	}
	/*
	 * Selects the value in the dropdown by Index Text
	 *
	 * @Parameters: Webelement i.e dropdown and the option's text to select
	 */
	public static boolean selectValueInDropdownbyIndex(WebElement element, int value) {
		try {
			Select select = new Select(element);
			select.selectByIndex(value);
			//element.click();
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			e.getStackTrace();
			return false;
		}
	}

	public static boolean selectValueInDropdownbyValue(WebElement element, int value) {
		try {
			Select select = new Select(element);
			select.selectByValue(String.valueOf(value));
			element.click();
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			e.getStackTrace();
			return false;
		}
	}


	/*
	 * Get all Selected values in the dropdown
	 *
	 * @Parameters: Webelement i.e dropdown
	 */
	public static List<String> getAllSelectedValueInDropdown(WebElement element,WebDriver driver) {
		try {
			Select select = new Select(element);
			return getListOfTextFromListOfWebelements(select.getAllSelectedOptions(), driver);

		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
	}

	public String getText(WebElement element) {
		String text=element.getText();
		return text;
	}

	/**
	 * Get all values in the dropdown
	 * @param element
	 * @return List of Options
	 */
	public static List<String> getAllValuesInDropdown(WebElement element,WebDriver driver) {
		try {
			Select select = new Select(element);
			return getListOfTextFromListOfWebelements(select.getOptions(), driver);
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
	}
	/*
	 * Get First Selected value in the dropdown
	 *
	 * @Parameters: Webelement i.e dropdown
	 */
	public static WebElement getFirstSelectedValueInDropdown(WebElement element) {
		try {
			Select select = new Select(element);
			return select.getFirstSelectedOption();

		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
	}

	/**
	 * This Method is to clear and Send keys to Any text field
	 *
	 * @param keys
	 */
	public static boolean clearTextFieldsendKeys(WebElement element, String keys) {
		try {
			element.click();
			element.clear();
			element.sendKeys(keys);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static boolean clearTextFieldsendKeysWithScroll(WebElement element, String keys, WebDriver driver) {
		try {
			ReusableMethods.scrollIntoView(element, driver);
			element.clear();
			element.sendKeys(keys);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static boolean waitForElementAttributeToBe(final WebDriver driver, By locator, String attribute,
													  String value, int timeout) {
		try {
			new WebDriverWait(driver, timeout).pollingEvery(Duration.ofMillis(500))
					.until(ExpectedConditions.attributeToBe(locator, attribute, value));
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/*
	 * Excel Util for Public Product Matrix filePath =
	 * PublicCommon.productMatrixFilePath sheetName = Product Category Sheet Name:
	 * STDProducts, PTPProducts, TPTProducts
	 */

	public static HashMap<String, String> getProductData(String filePath, String sheetName, String productName) {
		boolean found = false;
		boolean isfirstRow = false;
		Row firstrow = null;
		// Initialize class
		// Get Path and Sheet Name from Property File
		final HashMap<String, String> currentRowData = new HashMap<String, String>();
		st = init(filePath, sheetName);
		Iterator<Row> rowIterator = st.iterator();
		try {
			// int iCellCounter = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (!isfirstRow) {
					firstrow = row;
					isfirstRow = true;
				}
				if (row.getCell(columnForProductKeyWordLookUp).getStringCellValue().equalsIgnoreCase(productName)) {
					found = true;
					for (int i = 0; i < row.getLastCellNum(); i++) {
						String cellValue = row.getCell(i).getStringCellValue();
						if (cellValue == null) {
							cellValue = "";
						}
						cellValue = ExcelDataConfig.getUniqueString(cellValue);
						currentRowData.put(firstrow.getCell(i).getStringCellValue(), cellValue);
					}
					break;
				}

			}

			fs.close();

		} catch (Exception e) {
			// LogUtil.errorLog(ExcelDataUtil.class, "caught exception", e);
			e.printStackTrace();
			System.out.println("Caught Exception in Excel Util");
		}
		if (!found)
			// LogUtil.infoLog(ExcelDataUtil.class, "No data found with given key-> " +
			// testCaseID);

			System.out.println("No Data Found with Given KEY.");

		return currentRowData;

	}

	public static HashMap<String, String> getCredentialData(String filePath, String sheetName, String productName) {
		boolean found = false;
		boolean isfirstRow = false;
		Row firstrow = null;
		// Initialize class
		// Get Path and Sheet Name from Property File
		final HashMap<String, String> currentRowData = new HashMap<String, String>();
		st = init(filePath, sheetName);
		Iterator<Row> rowIterator = st.iterator();
		try {
			// int iCellCounter = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (!isfirstRow) {
					firstrow = row;
					isfirstRow = true;
				}
				if (row.getCell(columnForCredentialKeyWordLookUp).getStringCellValue().equalsIgnoreCase(productName)) {
					found = true;
					for (int i = 0; i < row.getLastCellNum(); i++) {
						String cellValue = row.getCell(i).getStringCellValue();
						if (cellValue == null) {
							cellValue = "";
						}
						cellValue = ExcelDataConfig.getUniqueString(cellValue);
						currentRowData.put(firstrow.getCell(i).getStringCellValue(), cellValue);
					}
					break;
				}

			}

			fs.close();

		} catch (Exception e) {
			// LogUtil.errorLog(ExcelDataUtil.class, "caught exception", e);
			e.printStackTrace();
			System.out.println("Caught Exception in Excel Util");
		}
		if (!found)
			// LogUtil.infoLog(ExcelDataUtil.class, "No data found with given key-> " +
			// testCaseID);

			System.out.println("No Data Found with Given KEY.");

		return currentRowData;

	}

	public static Sheet init(String filePath, String sheetName) {
		String fileExtensionName = filePath.substring(filePath.indexOf('.'));
		try {
			fs = new FileInputStream(filePath);
			if (fileExtensionName.equals(excelextensionxlsx)) {
				// If it is xlsx file then create object of XSSFWorkbook class
				wb = new XSSFWorkbook(fs);
			}
			// Check condition if the file is xls file
			else if (fileExtensionName.equals(excelextensionxls)) {
				// If it is xls file then create object of XSSFWorkbook class
				wb = new HSSFWorkbook(fs);
			}
			st = wb.getSheet(sheetName);
		} catch (Exception e) {
			// LogUtil.errorLog(ExcelDataUtil.class, EXCEPTIONCAUGHT, e);
			System.out.println("Exception in initBlock");
		}

		return st;

	}

	/*
	 * This method splits the String with Regex "~~" and convert into List of String
	 */
	public static List<String> splitStringAndConvertIntoList(String string) {

		return Arrays.asList(string.split("~~"));

	}

	/**
	 * Verify element is visible in the Viewport
	 * @param element
	 * @return true/false
	 */
	public static Boolean isVisibleInViewport(WebElement element,WebDriver driver) {

		return (Boolean)((JavascriptExecutor)driver).executeScript(
				"var elem = arguments[0],                 " +
						"  box = elem.getBoundingClientRect(),    " +
						"  cx = box.left + box.width / 2,         " +
						"  cy = box.top + box.height / 2,         " +
						"  e = document.elementFromPoint(cx, cy); " +
						"for (; e; e = e.parentElement) {         " +
						"  if (e === elem)                        " +
						"    return true;                         " +
						"}                                        " +
						"return false;                            "
				, element);
	}

	/**
	 * Click on WebElement
	 * @param driver
	 * @param elm
	 * @return true/false
	 */
	public static boolean clickonWebElement(WebDriver driver,WebElement elm) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.elementToBeClickable(elm));
			elm.click();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Check Visibility of Element
	 * @param driver
	 * @param locator
	 * @return True/False
	 */
	public static boolean isWebElementDisplayed(WebDriver driver,By locator) {
		try {
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(locator));
			WebElement elm = driver.findElement(locator);
			if(elm.isDisplayed())
				ReusableMethods.scrollIntoView(driver.findElement(locator), driver);
			return elm.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Check Visibility of Element
	 * @param driver
	 * @param locator
	 * @return True/False
	 */
	public static boolean isWebElementInvisible(WebDriver driver,By locator) {
		try {
			new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(locator));

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean invisibilityOfElement(WebDriver driver,WebElement webElement) {
		try {
			new WebDriverWait(driver, 20).pollingEvery(Duration.ofMillis(500))
					.until(ExpectedConditions.invisibilityOfElementLocated((By) webElement));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean ifFileExist(String fileName, WebElement element,WebDriver driver) {
		try {
			String downloadFolderPath = System.getProperty("user.dir") + "\\src\\main\\resources\\testResource\\downloads";
			String expectedFileName = "\\" + fileName;
			File file = new File(downloadFolderPath + expectedFileName);
			if (file.exists())
				file.delete();
			ReusableMethods.scrollIntoView(element, driver);
			element.click();
			boolean flag =  new WebDriverWait(driver, 20).pollingEvery(Duration.ofMillis(500)).until((ExpectedCondition<Boolean>) checkingFileExist -> file.exists());
			if (flag == true)
				Thread.sleep(3000);
			file.delete();
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isWebElementDisplayed(WebDriver driver,By locator, int timeOut) {
		try {
			new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOfElementLocated(locator));
			WebElement elm = driver.findElement(locator);
			if(elm.isDisplayed())
				ReusableMethods.scrollIntoView(driver.findElement(locator), driver);
			return elm.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}
	/**
	 *
	 * @param elementName
	 * @param locator
	 */
	public static void verifyVisibility(String elementName, By locator,WebDriver driver) {
		try {
			assertTrue(isWebElementDisplayed(driver, locator), elementName+" is not visible.");
			ScreenshotUtil.takeScreenshotForAllure(driver);
		} catch (Exception e) {
			e.printStackTrace();
			PublicCommon.reportFailAssert("Failed to verify visibility of element: "+elementName, e);
		}
	}

	/**
	 *
	 * @param elementName
	 * @param actualList
	 * @param expectedList
	 * @param assertionMessage
	 */
	public static void compareList(String elementName, List<String> actualList,List<String> expectedList, String assertionMessage) {
		try {
			assertEquals(actualList, expectedList,assertionMessage);
		} catch (Exception e) {
			e.printStackTrace();
			PublicCommon.reportFailAssert("Failed to compare lists.", e);
		}
	}

	/**
	 *
	 * @param elementName
	 * @param actualList
	 * @param expectedList
	 */
	public static void compareList(String elementName, List<String> actualList,List<String> expectedList) {
		try {
			assertEquals(actualList, expectedList, "Mismatch in List Comparison for element/item on page - "+elementName+" Actual: "+actualList+", Expected: "+expectedList);
		} catch (Exception e) {
			e.printStackTrace();
			PublicCommon.reportFailAssert("Failed to compare lists.", e);
		}
	}

	/**
	 * Adds String values to a list of String
	 * @param valueList
	 * @param values
	 */
	public static List <String> addValuesToList(List <String> valueList, String [] values) {
		try {

			valueList.clear();
			for(int i=0; i<values.length; i++) {
				valueList.add(values[i]);
			}
			System.out.println("Current Value List :: "+valueList);
			return valueList;
		}catch(Exception exp) {
			exp.printStackTrace();
			return null;
		}
	}

	/**
	 *
	 * @param elementName
	 * @param element
	 * @param expectedText
	 * @param assertionMessage
	 */
	public void verifyText(String elementName, WebElement element,String expectedText, String assertionMessage) {
		try {
			ReusableMethods.scrollIntoView(element, driver);
			verifyContentOnPage(elementName, element.getText(), expectedText);
			ScreenshotUtil.takeScreenshotForAllure(driver);
		} catch (Exception e) {
			e.printStackTrace();
			reportFailAssert("Failed to verify visibility of element: "+elementName, e);
		}
	}

	/**
	 *
	 * @param elementName
	 * @param expectedUrl
	 * @param testID
	 */
	private void verifyUrl(String elementName,String expectedUrl,String testID)
	{
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.urlContains(expectedUrl));
			verifyContentOnPage(elementName, driver.getCurrentUrl(), expectedUrl);

		} catch (Exception e) {
			reportFailAssert("Failed to verify url of : "+elementName, e);
		}
	}

	/**
	 *
	 * @param list of WebElements
	 * @return List of attribute values
	 */
	public static List<String> getAttributeList(List<WebElement> list, String attributeName) {

		List<String> atrList = new ArrayList<String>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			atrList.add(list.get(i).getAttribute(attributeName));
		}
		return atrList;
	}

	public static boolean switchToIframe(WebDriver driver, String id) {
		try {
			driver.switchTo().frame(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean openNewTabAndSwitch(WebDriver driver) {
		try {
			((JavascriptExecutor)driver).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1)); // switches to new tab
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean openNewTabAndSwitchToReqTab(WebDriver driver,int Tab) {
		try {
			((JavascriptExecutor)driver).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(Tab)); // switches to new tab
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean openNewTabAndSwitchThirdTab(WebDriver driver) {
		try {
			((JavascriptExecutor)driver).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(2)); // switches to new tab
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean switchTabs(WebDriver driver,int tab) {
		try {
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(tab));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean closeNewTabAndSwitchToOriginalTab(WebDriver driver) {
		try {
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.close();
			driver.switchTo().window(tabs.get(0)); // switches to older tab
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			return false;
		}
	}

	public static boolean closeNewTabAndSwitchToRequiredTab(WebDriver driver,int Tab) {
		try {
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.close();
			driver.switchTo().window(tabs.get(Tab)); // switches to older tab
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			return false;
		}
	}


	public static boolean openUrl(WebDriver driver, String url) {
		try {
			driver.get(url);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getText(WebDriver driver, By locator) {
		String text=driver.findElement(locator).getText();
		return text;
	}

	public static List<String> getTextOfWebElements(WebDriver driver, By Locater) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
		List<WebElement> elms = driver.findElements(Locater);
		List<String> getTexts = new ArrayList<String>();
		for (WebElement element : elms) {
			getTexts.add(element.getText());
		}
		return getTexts;
	}

	public static String updateUrl(String url) {

		if (ConfigReader.getValue("BASE_URL_PUB_ASTM").contains("https://stage.astm.org/")) {
			return url.replace("environment", "stage");
		} else if (ConfigReader.getValue("BASE_URL_PUB_ASTM").contains("https://dev.astm.org/")) {
			return url.replace("environment", "dev");
		} else {
			return url.replace("environment", "qa");
		}
	}

	public static List<String> getAllFieldValues(List<WebElement> elements, WebDriver driver) {
		try {
			int fieldValue = elements.size();
			List<String> list = new ArrayList<String>();

			for (int i = 0; i < fieldValue; i++) {

				list.add(JSExecutorGetText(elements.get(i),driver));
			}
			return list;

		} catch (Exception e) {
			e.getStackTrace();
			return null;// TODO: handle exception
		}
	}

	public static String getTextOfElement(WebDriver driver, By locator) {
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
			WebElement elm = driver.findElement(locator);
			return elm.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void selectCheckbox(WebDriver driver, By locator) {
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
			driver.findElement(locator).click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static String getAttributeValue(WebDriver driver,By locator) {
		try {
			String value=driver.findElement(locator).getAttribute("value");
			return value;
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@Step("Click on Ctrl and {ElamentName} link")
	public static void ctrlClick(WebDriver driver, String ElamentName, By element){
		Actions action = new Actions(driver);
		action.keyDown(Keys.LEFT_CONTROL)
				.click(driver.findElement(element))
				.keyUp(Keys.LEFT_CONTROL)
				.build()
				.perform();
	}


	/*public static boolean isFileDownloaded(String downloadPath, String fileName) {
		Boolean flag = false;
		try {
			Thread.sleep(10000);
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path folder = Path.of(downloadPath);
			folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);


			File dir = new File(downloadPath);
			File[] dirContents = dir.listFiles();
			//WaitStatementUtils.implicitWaitForSeconds(driver,10);
			for (int i = 0; i < dirContents.length; i++) {
				String actual = dirContents[i].getName();
				String expected = fileName;
				if (expected.equals(actual)) {
					// File has been found, it can now be deleted:
					Allure.addAttachment("Downloaded File found: ", fileName);
					dirContents[i].delete();
					flag = true;
				} else {
					Allure.addAttachment("Downloaded File not found: ", fileName);
					flag = false;
				}
			}
		}catch (Exception e){

		}
		return flag;
	}*/

	@Step("Is the downloaded file found in folder")
	public static boolean isFileDownloadedFound(String downloadPath, String fileName,String email) throws IOException {
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();

		for (int i = 0; i < dirContents.length; i++) {
			String actual=dirContents[i].getName().replaceAll(" ","");
			String expected=fileName;
			System.out.println(actual);
			System.out.println(expected);
			if (actual.equals(expected)) {
				String line = "";
				BufferedReader br = new BufferedReader(new FileReader(downloadPath+"\\"+expected));
				while ((line = br.readLine()) != null)   //returns a Boolean value
				{
					line.contains(email) ;
				}
				Allure.addAttachment(expected, "file found");
				dirContents[i].delete();
				return true;
			}
		}
		return false;
	}

	/*public static void main(String[] args)
	{
		String line = "";
		String splitBy = ",";
		try
		{
			//parsing a CSV file into BufferedReader class constructor
			BufferedReader br = new BufferedReader(new FileReader("CSVDemo.csv"));
			while ((line = br.readLine()) != null)   //returns a Boolean value
			{
				String[] employee = line.split(splitBy);    // use comma as separator
				System.out.println("Employee [First Name=" + employee[0] + ", Last Name=" + employee[1] + ", Designation=" + employee[2] + ", Contact=" + employee[3] + ", Salary= " + employee[4] + ", City= " + employee[5] +"]");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}*/

	@Attachment(value = "Attachment", type = "application/octet-stream")
	public static byte[] attachFile(String fileNameWithType) {
		try {
			String filePath = "path/to/" + fileNameWithType;

			// Read the file as bytes
			byte[] fileBytes = Files.readAllBytes(new File(filePath).toPath());

			// Attach the file with the specified MIME type
			Allure.addAttachment("Attachment", "application/pdf", String.valueOf(fileBytes));

			return fileBytes;
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0]; // Return an empty byte array in case of an error
		}
	}

	public static String getTimestamp(String DateFormat) {
		String timeStamp = new SimpleDateFormat(DateFormat).format(new java.util.Date());
		return timeStamp;
	}

	public static String addDays(int Days, String DateFormat){
		SimpleDateFormat date = new SimpleDateFormat(DateFormat);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, Days);
		return date.format(calendar.getTime());
	}
	@Step("Verifying the: {value}")
	public static void validateText(String value, String actualText, String expectedText, String assertionMessage) {
		try {
			String validation = value;
			boolean result = IntegrationCommon.verifyElementTextOnPage(validation, actualText, expectedText);
			assertTrue(result,assertionMessage);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}


}
