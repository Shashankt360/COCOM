package ScriptHelper;

import org.dom4j.DocumentException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import Driver.DriverHelper;
import Driver.xmlreader;
import Reporter.ExtentTestManager;

public class ContainerJourneyHelper extends DriverHelper {
	
	WebElement el;
	xmlreader xml=new xmlreader("src\\Locators\\Container.xml");
	
	public ContainerJourneyHelper(WebDriver dr) {
		super(dr);
		
	}

	
	
	
	public void AddContainerProduct(Object[][] Data) throws InterruptedException, Exception
	{
		Thread.sleep(30000);
		QuoteID.set(GetValueofInput(getwebelement(xml.getlocator("//locators/QuoteID"))));
		System.out.println(QuoteID.get());
		CurrentURL();
		for(int i=0; i<Data.length;i++)
		{
		Thread.sleep(10000);
		Clickon(getwebelement(xml.getlocator("//locators/EnterProductName")));
		SendKeys(getwebelement(xml.getlocator("//locators/EnterProductName")),Data[i][2].toString());
		Thread.sleep(2000);
		SendkeaboardKeys(getwebelement(xml.getlocator("//locators/EnterProductName")),Keys.ENTER);
		Clickon(getwebelement(xml.getlocator("//locators/AddContainerProduct")));
		getwebelement(xml.getlocator("//locators/EnterProductName")).clear();
		}
	}
	
	public void ContainerApproveQuote() throws Exception, DocumentException
	{
		WaitforElementtobeclickable(xml.getlocator("//locators/TechnicalApprovalTab"));
		javascriptexecutor(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
		safeJavaScriptClick(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
		Thread.sleep(20000);
		switchtofram(getwebelement("//iframe[@name='ucmIframeApp']"));
		int a = getwebelementscount(xml.getlocator("//locators/UploadXlsxAttachment").replace("[index]", "")); 
		System.out.println("count is for:"+xml.getlocator("//locators/UploadXlsxAttachment").replace("[index]", "")+"-----"+a);
		for(int i=0;i<a;i++)
		{
			
			Thread.sleep(20000);
			safeJavaScriptClick(getwebelement(xml.getlocator("//locators/UploadXlsxAttachment").replace("index", String.valueOf(i+1))));
			WaitforElementtobeclickable(xml.getlocator("//locators/SelectCategory").replace("value", "Internal"));
			Clickon(getwebelement(xml.getlocator("//locators/SelectCategory").replace("value", "Internal")));
			Thread.sleep(3000);
		//	WaitforElementtobeclickable(xml.getlocator("//locators/Selecttype"));
			Select3(getwebelement(xml.getlocator("//locators/Selecttype")),"EOF(xls)");
			Thread.sleep(3000);
			//WaitforElementtobeclickable(xml.getlocator("//locators/ChooseFile"));
			uploadafile(xml.getlocator("//locators/ChooseFile"),"IP Guardian_Order Form QtO' 1.0.xlsx");
			isElementPresent(xml.getlocator("//locators/Filename").replace("value", "IP Guardian_Order Form QtO' 1.0.xlsx"));
			Clickon(getwebelement(xml.getlocator("//locators/UploadFile")));
		}
		switchtodefault();
		Thread.sleep(20000);
		ClickswithAction(xml.getlocator("//locators/CheckboxTechnicalApproval"));
		Thread.sleep(3000);
		Clickon(getwebelement(xml.getlocator("//locators/SubmitTechnicalApprovalButton")));
	}
	
	public void ContainerSEApproval() throws Exception
	{
		ProxyLogin("CPQ_SE_User", xml.getlocator("//locators/ProxyLink"));
		
		WaitforElementtobeclickable(xml.getlocator("//locators/QuotetoOrderLink"));
		Clickon(getwebelement(xml.getlocator("//locators/QuotetoOrderLink")));
		WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim()));
		Clickon(getwebelement(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim())));
		waitForpageload();
		WaitforCPQloader();
		WaitforElementtobeclickable(xml.getlocator("//locators/TechnicalApprovalTab"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
		javascriptexecutor(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Approval Tab");
		Clickon(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
		Thread.sleep(5000);
		WaitforElementtobeclickable(xml.getlocator("//locators/SubmitToCSTApproval"));
		Clickon(getwebelement(xml.getlocator("//locators/SubmitToCSTApproval")));
		Thread.sleep(30000);
	}
	public void ContainerCSTApproval(Object[][] Data) throws DocumentException, Exception
	{
		ProxyLogin("CPQ_CST_User", xml.getlocator("//locators/ProxyLink"));
		WaitforElementtobeclickable(xml.getlocator("//locators/QuotetoOrderLink"));
		Clickon(getwebelement(xml.getlocator("//locators/QuotetoOrderLink")));
		WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim()));
		Clickon(getwebelement(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim())));
		waitForpageload();
		WaitforCPQloader();
		WaitforElementtobeclickable(xml.getlocator("//locators/TechnicalApprovalTab"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
		javascriptexecutor(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Approval Tab");
		safeJavaScriptClick(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
		Thread.sleep(20000);
		switchtofram(getwebelement("//iframe[@name='ucmIframeApp']"));
		int a = getwebelementscount(xml.getlocator("//locators/UploadXlsxAttachment").replace("[index]", ""));
		System.out.println("count is for:"+xml.getlocator("//locators/UploadXlsxAttachment").replace("[index]", "")+"-----"+a);
		for(int i=0;i<a;i++)
		{	
			Thread.sleep(20000);
			safeJavaScriptClick(getwebelement(xml.getlocator("//locators/UploadXlsxAttachment").replace("index", String.valueOf(i+1))));
			WaitforElementtobeclickable(xml.getlocator("//locators/SelectCategory").replace("value", "Customer Facing"));
			Clickon(getwebelement(xml.getlocator("//locators/SelectCategory").replace("value", "Customer Facing")));
			Thread.sleep(3000);
			Select3(getwebelement(xml.getlocator("//locators/Selecttype")),"EOF(pdf)");
			Thread.sleep(3000);
			uploadafile(xml.getlocator("//locators/ChooseFile"),"IP Guardian_Order Form QtO' 1.0.pdf");
			isElementPresent(xml.getlocator("//locators/Filename").replace("value", "IP Guardian_Order Form QtO' 1.0.pdf"));
			Clickon(getwebelement(xml.getlocator("//locators/UploadFile")));
		}
		switchtodefault();
		Thread.sleep(5000);
		Clickon(getwebelement(xml.getlocator("//locators/CSTApproval")));
		Thread.sleep(10000);
		//proxy logout
		WaitforElementtobeclickable(xml.getlocator("//locators/ProxyLogout"));
		Clickon(getwebelement(xml.getlocator("//locators/ProxyLogout")));
		Thread.sleep(5000);
		 openurl2(CurrentQuoteURL.get());
	}
	
	public void ContainerCreateOrder() throws InterruptedException, Exception
	{
		WaitforElementtobeclickable(xml.getlocator("//locators/CreateOrder"));
		Clickon(getwebelement(xml.getlocator("//locators/CreateOrder")));
		
	}
}

