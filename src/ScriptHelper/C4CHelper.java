package ScriptHelper;

import org.dom4j.DocumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;
import Driver.DriverHelper;
import Driver.xmlreader;
import Reporter.ExtentTestManager;

public class C4CHelper extends DriverHelper{
	
	
	
	WebElement el;
	xmlreader xml=new xmlreader("src\\Locators\\C4C.xml");
	
	public C4CHelper(WebDriver parentdriver)
	{
		super(parentdriver);
	}
	public void NavigatetoC4C() throws Exception
	{
		Geturl(Getkeyvalue("C4C_URL"));
	
	}
	// Code For Account Navigation
	public void Movetoaccount(Object[][] Inputdata) throws Exception
	{
		
		//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Customer Section");
		//Thread.sleep(10000);
		WaitforElementtobeclickable(xml.getlocator("//locators/CustomerSection"));
		Clickon(getwebelement(xml.getlocator("//locators/CustomerSection")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Customer Section in Left Navigation");
		WaitforElementtobeclickable(xml.getlocator("//locators/Accountlink"));
		//WaitforC4Cloader(xml.getlocator("//locators/C4Cloader"),1);
		Clickon(getwebelement(xml.getlocator("//locators/Accountlink")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Account link in navidation");
		WaitforElementtobeclickable(xml.getlocator("//locators/OCN").replace("Accountnumber", Inputdata[0][0].toString()));
		//WaitforC4Cloader(xml.getlocator("//locators/C4Cloader"),1);
		Clickon(getwebelement(xml.getlocator("//locators/OCN").replace("Accountnumber", Inputdata[0][0].toString())));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Account Name");
	}

	public void  MovetoOpportunuity(Object[][] Inputdata) throws Exception{
		WaitforElementtobeclickable(xml.getlocator("//locators/OpportunuityTab"));
		//WaitforC4Cloader(xml.getlocator("//locators/C4Cloader"),1);
		Clickon(getwebelement(xml.getlocator("//locators/OpportunuityTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Opportunuity Tab");
		WaitforElementtobeclickable(xml.getlocator("//locators/Opportunuitynumber").replace("Opportunuitynumber", Inputdata[0][1].toString()));
		//WaitforC4Cloader(xml.getlocator("//locators/C4Cloader"),1);
		Clickon(getwebelement(xml.getlocator("//locators/Opportunuitynumber").replace("Opportunuitynumber", Inputdata[0][1].toString())));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Opportunuity Name ");
		Thread.sleep(10000);
		//WaitforC4Cloader(xml.getlocator("//locators/C4Cloader"),1);
		WaitforElementtobeclickable(xml.getlocator("//locators/QuoteTab"));
		Clickon(getwebelement(xml.getlocator("//locators/QuoteTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Quote Tab in Opportunuity Detail page ");
		Thread.sleep(5000);
	}
	public void AddQuote() throws Exception {
		WaitforElementtobeclickable(xml.getlocator("//locators/AddQuote"));
		Clickon(getwebelement(xml.getlocator("//locators/AddQuote")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Add to Quote Button");
		//WaitforC4Cloader(xml.getlocator("//locators/C4Cloader"),1);
	}
	public void EditQuote() throws Exception {
		Thread.sleep(5000);
		//WaitforC4Cloader(xml.getlocator("//locators/C4Cloader"),1);
		WaitforElementtobeclickable(xml.getlocator("//locators/Quotenumber").replace("Quotenumber", QuoteID.get().toString()));
		Clickon(getwebelement(xml.getlocator("//locators/Quotenumber").replace("Quotenumber", QuoteID.get().toString())));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Add to Quote Button");
		//WaitforC4Cloader(xml.getlocator("//locators/C4Cloader"),1);
		Thread.sleep(5000);
		WaitforElementtobeclickable(xml.getlocator("//locators/QuoteActions"));
		Clickon(getwebelement(xml.getlocator("//locators/QuoteActions")));
		WaitforC4Cloader(xml.getlocator("//locators/C4Cloader"),1);
		
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Add to Quote Button");
		WaitforElementtobeclickable(xml.getlocator("//locators/QuoteEdit"));
		Clickon(getwebelement(xml.getlocator("//locators/QuoteEdit")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Add to Quote Button");
		WaitforElementtobeclickable(xml.getlocator("//locators/customersignatureTab"));
		
	}
	public void CheckdocumentSigned() throws Exception
	{
		WaitforElementtobeclickable(xml.getlocator("//locators/customersignatureTab"));
		Clickon(getwebelement(xml.getlocator("//locators/customersignatureTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Add to Quote Button");
		WaitforElementtobeclickable(xml.getlocator("//locators/Status"));
	if(!(Gettext(getwebelement(xml.getlocator("//locators/Status"))).equals("Signed")))
	{
		WaitforElementtobeclickable(xml.getlocator("//locators/Refresh"));
		Clickon(getwebelement(xml.getlocator("//locators/Refresh")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Add to Quote Button");
	}
	}
}
