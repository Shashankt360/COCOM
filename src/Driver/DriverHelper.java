package Driver;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

public class DriverHelper {
	
	WebDriver driver;
	Wait<WebDriver> wait;
	WebElement el;
	List<WebElement> ellist;
	public static ThreadLocal<String> Ordernumber= new ThreadLocal<>();
	public static ThreadLocal<String> OrderscreenURL= new ThreadLocal<>();
	public static ThreadLocal<String> SchedulerURL= new ThreadLocal<>();
	public static ThreadLocal<Integer> workitemcounter= new ThreadLocal<>();
	public static ThreadLocal<String> NCServiceId= new ThreadLocal<>();
	public static ThreadLocal<String> HubNCServiceId= new ThreadLocal<>();
	public static ThreadLocal<String> HubOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> HubOrderscreenURL= new ThreadLocal<>();
	public static ThreadLocal<String> ProductId= new ThreadLocal<>();
	public static ThreadLocal<String> SpokeOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> SpokeOrderscreenURL= new ThreadLocal<>();
	public static ThreadLocal<String> SpokeNCServiceId= new ThreadLocal<>();
	public static ThreadLocal<String> SpokeProductId= new ThreadLocal<>();
	public static ThreadLocal<String> ProductInstancenumber= new ThreadLocal<>();
	public static ThreadLocal<String> ModifiedOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> ModifyOrderscreenURL= new ThreadLocal<>();
	public static ThreadLocal<String> EndCheck= new ThreadLocal<>();
	public static ThreadLocal<String> CeaseOrderscreenURL= new ThreadLocal<>();
	public static ThreadLocal<String> CeaseOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> CompOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> EthernetProductOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> SiebelCeaseOrdernumber= new ThreadLocal<>();									 
	public static ThreadLocal<String> CircuitReference= new ThreadLocal<>();						   
	public static ThreadLocal<String> ANTCheck= new ThreadLocal<>();
	public static ThreadLocal<String> Errors= new ThreadLocal<>();
	public static ThreadLocal<String> SiebelOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> HubSiebelOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> SpokeSiebelOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> ServiceOrder= new ThreadLocal<>();
	public static ThreadLocal<String> CircuitRefnumber= new ThreadLocal<>();
	public static ThreadLocal<String> CircuitRefnumberHub= new ThreadLocal<>();
    public static ThreadLocal<String> HubNetworkReference= new ThreadLocal<>();
    public static ThreadLocal<String> TechnicalOrderStatus= new ThreadLocal<>();
	public static ThreadLocal<String> SpokeCircuitRefnumber= new ThreadLocal<>();
	public static ThreadLocal<String> ModifiedSiebelOrdernumber= new ThreadLocal<>();
	public static ThreadLocal<String> AendBuildingId= new ThreadLocal<>();
	
	
	public static ThreadLocal<String>  ModifiedCircuitRefnumber=new ThreadLocal<>();
	
	public DriverHelper(WebDriver dr)
	{
		driver=dr;
		wait = new FluentWait<WebDriver>(dr)       
				.withTimeout(180, TimeUnit.SECONDS)    
				.pollingEvery(5, TimeUnit.SECONDS)    
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		//workitemcounter.set(1);
	}
	 
	public void javascriptexecutor(WebElement el) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView();", el);
		js.executeScript("window.scrollTo(0, 0)");
		//window.scrollTo(0, 0);
	}
	
	public void WaitforElementtobeclickable(final String locator) throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator))); 
		//getwebelement(xml.getlocator("//locators/StandrdQuote"));
		System.out.println("Code for Loading");
		Thread.sleep(2000);
		
	}
	public void Getloadingcomplete(final String locator) throws InterruptedException
	{
		wait.until(ExpectedConditions.attributeToBe(By.xpath(locator), "style", "display: none;")); 
		//getwebelement(xml.getlocator("//locators/StandrdQuote"));
		System.out.println("Code for Loading");
		Thread.sleep(2000);
		
	}
	public void Getmaploaded(final String framlocator, final String messagelocator) throws InterruptedException
	{
		
		System.out.println("Code for Map Loading");
		Thread.sleep(1000);
		String[] finalval=framlocator.split("=");
		driver.switchTo().frame(driver.findElement(By.id(finalval[1])));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(messagelocator))); 
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		
	}
	public WebElement getwebelement(final String locator) throws InterruptedException
	{   //Log.info("Indriverhelper"+driver);
	 //WebElement el;
	final String[] finalval;
		if(locator.startsWith("name"))
		{
			finalval=locator.split("=");
			//Log.info(finalval[1]);
			//Log.info("Indriverhelper"+driver);
			//wait.until();
			
			wait.until(new Function<WebDriver, WebElement>() {       
				public WebElement apply(WebDriver driver) { 
					el=driver.findElement(By.name(finalval[1]));
					wait.until(ExpectedConditions.elementToBeClickable(el)).isEnabled();
					return el;     
				 }  
				}); 
			//wait.until(ExpectedConditions.stalenessOf(element))
		}
		else if(locator.startsWith("id"))
		{
			finalval=locator.split("=");
			//Log.info(finalval[1]);
			//Log.info("Indriverhelper"+driver);
			wait.until(new Function<WebDriver, WebElement>() {       
				public WebElement apply(WebDriver driver) { 
					el=driver.findElement(By.id(finalval[1]));
					wait.until(ExpectedConditions.elementToBeClickable(el)).isEnabled();
					return el;   
				 }  
				});
			 //el= driver.findElement(By.id(finalval[1]));
		}
		else if (locator.startsWith("//")|| locator.startsWith("(//")||locator.startsWith("("))
		{
			wait.until(new Function<WebDriver, WebElement>() {       
				public WebElement apply(WebDriver driver) { 
					el=driver.findElement(By.xpath(locator)); 
					wait.until(ExpectedConditions.elementToBeClickable(el)).isEnabled();
					return el;   
				 }  
				});
			
		}
		//Thread.sleep(1000);
		return el;
	}
	
	public WebElement getwebelement2(final String locator) throws InterruptedException
	{   
		if (locator.startsWith("//")|| locator.startsWith("(//"))
		{	
					el=driver.findElement(By.xpath(locator)); 
					return driver.findElement(By.xpath(locator));     	
		}
		Thread.sleep(1000);
		return el;
	}
	public void openurl(String environment) throws Exception {
		String URL=null;
		PropertyReader pr=new PropertyReader();
		Log.info(environment+"_URL");
		URL=pr.readproperty(environment+"_URL");
		
		driver.get(URL);
		
	}
	public void Geturl(String URL) throws Exception {
	
		driver.get(URL);
		
	}
	public void Clickon(WebElement el) throws InterruptedException {
		//Thread.sleep(3000);
		
		el.click();
		//Thread.sleep(3000);
	}
	public void safeJavaScriptClick(WebElement element) throws Exception {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				Log.info("Clicking on element with using java script click");

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			} else {
				Log.info("Unable to click on element");
			}
		} catch (StaleElementReferenceException e) {
			Log.info("Element is not attached to the page document "+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			Log.info("Element was not found in DOM "+ e.getStackTrace());
		} catch (Exception e) {
			Log.info("Unable to click on element "+ e.getStackTrace());
		}
	}
	public void switchtofram(WebElement el){
		driver.switchTo().frame(0);
		
	}
	public void switchtodefault(){
		driver.switchTo().defaultContent();
		
	}
			public String Getattribute(WebElement el,String attributename) {
			Log.info(el.getAttribute(attributename));
			return el.getAttribute(attributename);
	  }
public void Moveon(WebElement el) {
			
		Actions action = new Actions(driver);
		 
	    action.moveToElement(el).build().perform();
	}
	public boolean isElementPresent(String locator) {
	    try {
	        driver.findElement(By.xpath(locator));
	        Log.info("Element Found: True");
	        return true;
	    } catch (NoSuchElementException e) {
	    	 Log.info("Element Found: False");
	        return false;
	    }
	}
	
	public void waitandclickForworkitemsPresent(String locator, int timeout) throws InterruptedException
	{
			for(int i=0;i<=timeout*60/20;i++){
				try {
		            if (isElementPresent(locator)){
		                break;
		            }
		            else{
		            	//Goto Error Tab
		            	// Clickon(getwebelement(xml.getlocator("//locators/Tasks/Errors")));
		            	//if any Error displayed
		            	//if(isElementPresent("Locator for first error"))
		            	//{
		            	//Assert.fail("An Error Occuured on Error Tab");
		            	//break;
		            	//}
		            	//else
		            	//{
		            	//Clickon(getwebelement(xml.getlocator("//locators/Tasks/Workitems")));
		            	//Log.info("Refreshing the Pages");
			        	//driver.navigate().refresh();
			        	//Log.info("Waiting For 20 Sec");
			        	//Thread.sleep(20000);
		            	//}
		            	//Assert False and Break
		            	//else navigate to WorkItems and do the page refresh and weight
		            	Log.info("Refreshing the Pages");
			        	driver.navigate().refresh();
			        	Log.info("Waiting For 20 Sec");
			        	Thread.sleep(20000);
		            }
		            }
		        catch (Exception e) {
		        	Log.info(e.getMessage());
		        }
			}
	}
	
	public void waitandclickForOrderCompleted(String locator, int timeout) throws InterruptedException
	{
			for(int i=0;i<=timeout*60/20;i++){
				try {
		            if (isElementPresent(locator)){
		                break;
		            }
		            else{
		            	Log.info("Refreshing the Pages");
			        	driver.navigate().refresh();
			        	Log.info("Waiting For 20 Sec");
			        	Thread.sleep(20000);
		            }
		            }
		        catch (Exception e) {
		        	Log.info(e.getMessage());
		        }
			}
	}
	
	public void waitandclickForOrderStarted(String locator, int timeout) throws InterruptedException
	{
			for(int i=0;i<=timeout*60/20;i++){
				try {
		            if (isElementPresent(locator)){
		                break;
		            }
		            else{
		            	Log.info("Refreshing the Pages");
			        	driver.navigate().refresh();
			        	Log.info("Waiting For 20 Sec");
			        	Thread.sleep(20000);
		            }
		            }
		        catch (Exception e) {
		        	Log.info(e.getMessage());
		        }
			}
	}
	
	public void waitandForElementDisplay(String locator, int timeout) throws InterruptedException
	{
			for(int i=0;i<=timeout*60/20;i++){
				try {
		            if (isElementPresent(locator)){
		                break;
		            }
		            else{
		            	Log.info("Refreshing the Pages");
			        	driver.navigate().refresh();
			        	Log.info("Waiting For 20 Sec");
			        	Thread.sleep(20000);
		            }
		            }
		        catch (Exception e) {
		        	Log.info(e.getMessage());
		        }
			}
	}
	public void waitandForElementDisplay2(String locator, int timeout) throws InterruptedException
	{
			for(int i=0;i<=timeout*60/20;i++){
				try {
		            if (isElementPresent(locator)){
		                break;
		            }
		            else{
		            	//Log.info("Refreshing the Pages");
			        	//driver.navigate().refresh();
			        	Log.info("Waiting For 20 Sec");
			        	Thread.sleep(20000);
		            }
		            }
		        catch (Exception e) {
		        	Log.info(e.getMessage());
		        }
			}
	}
	public void Pagerefresh() throws InterruptedException
	{
			driver.navigate().refresh();
			        	
	}
	
	public int getwebelementscount(final String locator) throws InterruptedException
	{ 
		ellist=driver.findElements(By.xpath(locator));
		return ellist.size();
	}
	
	public void SendKeys(WebElement el,String value) throws InterruptedException {
		//Thread.sleep(3000);
		el.sendKeys(value);
		//Thread.sleep(3000);
	}

	public void SendkeaboardKeys(WebElement el,Keys k) throws InterruptedException {
		//Thread.sleep(3000);
		el.sendKeys(k);
		//Thread.sleep(3000);
	}
	
	public String GetText(WebElement el) {
			String actual=el.getText().toUpperCase().toString();
	//		String actual1=el.getText().toUpperCase().toString();
			return actual;
		}
	
	public String GetInputValue(WebElement el) {
		String actual=el.getAttribute("value");
		return actual;
		}

	public String Getkeyvalue(String Key) throws IOException{ 
			PropertyReader pr=new PropertyReader();
		    String Keyvalue;
			Keyvalue=pr.readproperty(Key);
			return Keyvalue;
		}
	
	public void VerifyTextpresent(String text) throws IOException
		{ 
			Log.info(text);
			Assert.assertFalse(driver.findElement(By.xpath("//*[text()='"+text+"']")).isDisplayed());
		}
	
	public void VerifyText(String text) throws IOException
		{ 
			Log.info(text);
			Assert.assertTrue(driver.findElement(By.xpath("//*[text()='"+text+"']")).isDisplayed());
		}
	
	public String Gettext(WebElement el) throws IOException
		{ 
			String text=el.getText().toString();
			return text;
		}
	
	public String[] GetText2(WebElement el) throws IOException
		{ 
			String text=el.getText().toString();
			String[] text2=text.split(" \\[");
			Log.info("New Task Name is:"+text2);
			return text2;
		}
	
	public String GetText3(WebElement el, String string) throws IOException
		{ 
			String text=el.getText().toString();
			return text;
		}
	
	public void Select(WebElement el, String value) throws IOException, InterruptedException
		{ //Thread.sleep(3000);
			Select s1=new Select(el);
			s1.selectByVisibleText(value);
			//Thread.sleep(3000);
		}
	
	public void Clear(WebElement el) throws IOException, InterruptedException
		{ //Thread.sleep(3000);
			el.clear();
			//Thread.sleep(3000);
		}
	
	public void AcceptJavaScriptMethod(){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			driver.switchTo().defaultContent();
		}
	
	public void savePage(){
			Actions keyAction = new Actions(driver);     
			keyAction.keyDown(Keys.CONTROL).sendKeys("s").keyUp(Keys.CONTROL).perform();
		}
	
	public void uploadafile(String  locator,String FileName)
	{
		String str = System.getProperty("user.dir")+"\\src\\Data\\"+FileName;
		String[]  finalval=locator.split("=");
		WebElement el=driver.findElement(By.id(finalval[1])); 
		el.sendKeys(str);
//		// + "\\Lib\\chromedriver.exe"
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Clipboard clipboard = toolkit.getSystemClipboard();
//		StringSelection strSel = new StringSelection(str);
//		clipboard.setContents(strSel, null);
//		Actions keyAction = new Actions(driver); 
//		keyAction.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
//		keyAction.sendKeys(Keys.ENTER);
		
	}
	}
