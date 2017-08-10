package common;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;


public class Driver {
	private WebDriver driver;
	final int DEFAULT_IMPLICIT_WAIT = 30;
	final int DEFAULT_WAIT_FOR_VISIBILITY = 10;
	final int DEFAULT_WAIT_FOR_CLICKABILITY = 10;
	
	private String parentWindowHandler;
	
	public Driver(){
		this("chrome");
	}
	
	public Driver(String driverImpl){
		if(driverImpl.equalsIgnoreCase("chrome")){
			ChromeDriverManager.getInstance().setup();
			driver = new ChromeDriver();
		}
		else if(driverImpl.equalsIgnoreCase("ff")){
			FirefoxDriverManager.getInstance().setup();
			driver = new FirefoxDriver();
		}
		else{
			throw new IllegalArgumentException("Wrong driver chosen when instantiating Driver. Choose between 'chrome' or 'ff'");
		}
		driver.manage().timeouts().implicitlyWait(DEFAULT_IMPLICIT_WAIT, TimeUnit.SECONDS);
	}
	
	public WebDriver getWebDriver(){
		return driver;
	}
	
	public void goTo(String url){
		driver.get(url);
	}
	
	public String getPageTitle(){
		return driver.getTitle();
	}
	
	public void click(String xpath){
		findElement(xpath).click();
	}

	public void clickWhenClickable(String xpath){
		waitForElementToBeClickable(xpath);
		click(xpath);
	}
	
	public void sendKeys(String xpath, String key){
		findElement(xpath).sendKeys(key);
	}
	
	public void scrollPage(int x, int y){
		String scrollScript = "window.scrollBy("+x+","+y+")";
		executeScript(scrollScript);
	}
	
	public void scrollPageToElement(String xpath){
		WebElement el = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",el);
	}
	
	public void executeScript(String script){
		((JavascriptExecutor) driver).executeScript(script);
	}
	
	public String getText(String xpath){
		return findElement(xpath).getText();
	}
	
	public void clear(String xpath){
		findElement(xpath).clear();
	}
	
	public WebElement findElement(String xpath){
		return driver.findElement(By.xpath(xpath));
	}
	
	public List<WebElement> findElements(String xpath){
		return driver.findElements(By.xpath(xpath));
	}
	
	public List<WebElement> getOptions(String xpath){
		Select select = new Select(findElement(xpath));
		return select.getOptions();
	}
	
	public void selectDropDownOption(String xpath, String optionText){
		Select select = new Select(findElement(xpath));
		select.selectByVisibleText(optionText);
	}
	
	public void selectDropDownByIndex(String xpath, int index) {
		Select select = new Select(findElement(xpath));
		select.selectByIndex(index);
	}
	
	public boolean isSelected(String xpath){
		return findElement(xpath).isSelected();
	}
	
	public void waitForElementToBeVisible(String xpath){
		waitForElementToBeVisible(xpath, DEFAULT_WAIT_FOR_VISIBILITY);
	}
	
	public void waitForElementToBeVisible(String xpath, int maxWait){
		WebDriverWait wait = new WebDriverWait(driver, maxWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
	
	public void waitForElementToBeClickable(String xpath){
		waitForElementToBeClickable(xpath, DEFAULT_WAIT_FOR_CLICKABILITY);
	}
	
	public void waitForElementToBeClickable(String xpath, int maxWait){
		WebDriverWait wait = new WebDriverWait(driver, maxWait);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	public void moveCursorOverElement(String xpath) {
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath(xpath)));
		Action action = builder.build();
		action.perform();
	}
	
	public boolean isElementAbsent(String xpath){
		boolean temp;
		
		removeImplicitWait();
		temp = findElements(xpath).size()==0;		
		resetImplicitWaitToDefault();
		
		return temp;
	}
	
	public boolean isElementPresent(String xpath){
		return findElements(xpath).size() == 1;
	}
	
	public boolean isElementPresent(String xpath, int maxWait){
		boolean temp;
		
		setImplicitWait(maxWait);;
		temp = isElementPresent(xpath);		
		resetImplicitWaitToDefault();
		
		return temp;
	}
	
	public boolean isElementDisplayed(String xpath){
		return findElement(xpath).isDisplayed();
	}
	
	private void resetImplicitWaitToDefault(){
		setImplicitWait(DEFAULT_IMPLICIT_WAIT);
	}
	
	private void removeImplicitWait(){
		setImplicitWait(0);
	}
	
	private void setImplicitWait(int seconds){
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public void switchToIframByXPath(String xpath) {
		WebElement iFrame = driver.findElement(By.xpath(xpath));
		driver.switchTo().frame(iFrame);
	}

	public void switchToIFrameByIndex(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToIFrameByName(String iFrameName) {
		driver.switchTo().frame(iFrameName);
	}
	
	public void switchBackToMain() {
		driver.switchTo().defaultContent();
	}
	
	public String executeJavascript(String script) {
		if (driver instanceof JavascriptExecutor)
			return (String) ((JavascriptExecutor) driver).executeScript(script);
		return null;
	}
	
	public void switchToPopupWindow(){
		saveParentWindowHandler();	
		switchToWindow(getPopUpWindowHandler());
	}
	
	public void switchBackToParentWindow(){
		switchToWindow(parentWindowHandler);
	}
	
	public void refresh(){
		driver.navigate().refresh();
	}
	
	public void close(){
		driver.close();
	}
	
	private void switchToWindow(String windowHandler){
		driver.switchTo().window(windowHandler);
	}
	
	private String getPopUpWindowHandler(){
		String popUpWindowHandler="";
		
		Set<String> handler = driver.getWindowHandles();
		Iterator<String> handlesIterator = handler.iterator();
		while(handlesIterator.hasNext()){
			popUpWindowHandler = handlesIterator.next();
		}
		return popUpWindowHandler;
	}
	
	private void saveParentWindowHandler(){
		parentWindowHandler = driver.getWindowHandle();
	}

	public void maximizeWindow() {
	      driver.manage().window().maximize();
	}
	
}
