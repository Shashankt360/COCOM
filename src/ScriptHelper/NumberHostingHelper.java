package ScriptHelper;

import java.util.Iterator;
import java.util.Set;

import org.dom4j.DocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import Driver.DriverHelper;
import Driver.xmlreader;
import Reporter.ExtentTestManager;

public class NumberHostingHelper extends DriverHelper {

	WebElement el;
	xmlreader xml=new xmlreader("src\\Locators\\NumberHosting.xml");
	
	public NumberHostingHelper(WebDriver dr) {
		super(dr);
		
	}
	
	public void OpenApplication() throws Exception
	{
		openurl("NH");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Opening the Url");
		
	}
	public void Scenario1(Object[][]Inputdata) throws Exception, Exception
	{
		Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
		Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
		Select(getwebelement(xml.getlocator("//locators/ResellerName")),Inputdata[0][0].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
		Select(getwebelement(xml.getlocator("//locators/Country")),Inputdata[0][1].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/ServiceProfile")),Inputdata[0][2].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/SearchCriteria")),"Number Status");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number status");
		Select(getwebelement(xml.getlocator("//locators/NumberStatus")),"Free");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status");
		Select(getwebelement(xml.getlocator("//locators/AreaName")),Inputdata[0][5].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Area Name");
		Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
		Clickon(getwebelement(xml.getlocator("//locators/NumberRangeCheckbox")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Range Checkbox");
		Select(getwebelement(xml.getlocator("//locators/Action")),"Reserve");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Action");
		Clickon(getwebelement(xml.getlocator("//locators/GoButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
		SendKeys(getwebelement(xml.getlocator("//locators/CustomerReference")),Inputdata[0][6].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter Customer Reference number");
		Clickon(getwebelement(xml.getlocator("//locators/Submit")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
		String TransactionId =Gettext(getwebelement(xml.getlocator("//locators/TransactionId")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: The Transaction Id generated is : "+TransactionId);
		Thread.sleep(10000);
		// Store the current window handle
		String winHandleBefore = driver.getWindowHandle();
		
		Clickon(getwebelement(xml.getlocator("//locators/TransactionId")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction Id");
		System.out.println(TransactionId);
		Thread.sleep(10000);
		
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> iterator = handles.iterator();
		iterator.next();
		String curent=iterator.next();
	    System.out.println("Window handel"+curent);
	    driver.switchTo().window(curent);
	 // Perform the actions on new window
	    String TransactionStatus = Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
	    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: The Transaction Status is : "+TransactionStatus);
	    VerifyText(TransactionStatus);
	    System.out.println(TransactionStatus);
	    Thread.sleep(5000);
	    driver.close();
	    driver.switchTo().window(winHandleBefore);
		Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
		Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
		Select(getwebelement(xml.getlocator("//locators/ResellerName")),Inputdata[0][0].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
		Select(getwebelement(xml.getlocator("//locators/Country")),Inputdata[0][1].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/ServiceProfile")),Inputdata[0][2].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/SearchCriteria")),"Transaction ID");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Transaction Id");
		SendKeys(getwebelement(xml.getlocator("//locators/InputTransactionId")),TransactionId);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Transaction Id: "+TransactionId);
		Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
		Thread.sleep(5000);
		String Status = Gettext(getwebelement(xml.getlocator("//locators/Status")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: The Status of the number is : "+Status);
		System.out.println(Status);
		Assert.assertTrue(Status.contains("Reserved"));
	//	System.out.println(Status);
	}
	
	public void Scenario2(Object[][] Inputdata) throws Exception, DocumentException 
	{
		Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
		Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
		Select(getwebelement(xml.getlocator("//locators/ResellerName")),Inputdata[0][0].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
		Select(getwebelement(xml.getlocator("//locators/Country")),Inputdata[0][1].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/ServiceProfile")),Inputdata[0][2].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/SearchCriteria")),"Number Status");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number status");
		Select(getwebelement(xml.getlocator("//locators/NumberStatus")),"Reserved");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as Reserved");
		Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
		Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
		Select(getwebelement(xml.getlocator("//locators/UserAction")),"Activate");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
		Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
		SendKeys(getwebelement(xml.getlocator("//locators/CustomerName")),Inputdata[0][7].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Customer name Field");
		SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")),Inputdata[0][9].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the building number Field");
		SendKeys(getwebelement(xml.getlocator("//locators/city")),Inputdata[0][11].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the City Field");
		SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")),Inputdata[0][8].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Building name field");
		SendKeys(getwebelement(xml.getlocator("//locators/Street")),Inputdata[0][10].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Street Field");
		SendKeys(getwebelement(xml.getlocator("//locators/PostCode")),Inputdata[0][12].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Post Code");
		Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
		Thread.sleep(10000);
		String Transactionresult =Gettext(getwebelement(xml.getlocator("//locators/TransactionResult")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : "+Transactionresult);
		Thread.sleep(20000);
		// Store the current window handle
		String winHandleBefore = driver.getWindowHandle();
		
		Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction ID");
		System.out.println(Transactionresult);
		Thread.sleep(10000);
		
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> iterator = handles.iterator();
		iterator.next();
		String curent=iterator.next();
	    System.out.println("Window handel"+curent);
	    driver.switchTo().window(curent);
	 // Perform the actions on new window
	    
	    String TransactionStatus=null;
	    do {
	        TransactionStatus = Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
		    System.out.println(TransactionStatus);
	    	Thread.sleep(5000);
	    	Pagerefresh();
	    }
	    while(!TransactionStatus.equals("Completed"));
	    
	 //   String TransactionStatus = Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
	 //   System.out.println(TransactionStatus);
	    Assert.assertTrue(TransactionStatus.contains("Completed"));
	    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status is : "+TransactionStatus);
	    Thread.sleep(5000);
	    driver.close();
	    driver.switchTo().window(winHandleBefore);
	}
	public void Scenario3(Object[][]Inputdata) throws Exception
	{
		Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
		Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
		Select(getwebelement(xml.getlocator("//locators/ResellerName")),Inputdata[0][0].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
		Select(getwebelement(xml.getlocator("//locators/Country")),Inputdata[0][1].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/ServiceProfile")),Inputdata[0][2].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/SearchCriteria")),"Number Status");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number status");
		Select(getwebelement(xml.getlocator("//locators/NumberStatus")),"Activated");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number status as Activated");
		Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
		Thread.sleep(10000);
		Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
		Select(getwebelement(xml.getlocator("//locators/UserAction")),"Address Update");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
		Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
		Thread.sleep(5000);
		SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")),Inputdata[0][8].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Building name field");
		SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")),Inputdata[0][9].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the building number Field");
		SendKeys(getwebelement(xml.getlocator("//locators/NewStreet")),Inputdata[0][10].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the New Street Field");
		SendKeys(getwebelement(xml.getlocator("//locators/NewCity")),Inputdata[0][11].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the  New City Field");
		SendKeys(getwebelement(xml.getlocator("//locators/NewPostCode")),Inputdata[0][12].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Post Code");
		Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the submit button");
		Thread.sleep(10000);
		String Transactionresult =Gettext(getwebelement(xml.getlocator("//locators/TransactionResult")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : "+Transactionresult);
		Thread.sleep(10000);
		// Store the current window handle
		String winHandleBefore = driver.getWindowHandle();
		
		Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction ID");
		System.out.println(Transactionresult);
		Thread.sleep(5000);
		
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> iterator = handles.iterator();
		iterator.next();
		String curent=iterator.next();
	    System.out.println("Window handel"+curent);
	    driver.switchTo().window(curent);
	 // Perform the actions on new window
	    String TransactionStatus=null;
	    do {
	        TransactionStatus = Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
		    System.out.println(TransactionStatus);
	    	Thread.sleep(5000);
	    	Pagerefresh();
	    }
	    while(!TransactionStatus.equals("Completed"));
	    
	    
	    Assert.assertTrue(TransactionStatus.contains("Completed"));
	    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : "+TransactionStatus);
	    Thread.sleep(5000);
	    driver.close();
	    driver.switchTo().window(winHandleBefore);
		Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
		Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
		Select(getwebelement(xml.getlocator("//locators/ResellerName")),Inputdata[0][0].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
		Select(getwebelement(xml.getlocator("//locators/Country")),Inputdata[0][1].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/ServiceProfile")),Inputdata[0][2].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/SearchCriteria")),"Transaction ID");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Transaction Id");
		SendKeys(getwebelement(xml.getlocator("//locators/InputTransactionId")),Transactionresult);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Transaction Id: "+Transactionresult);
		Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
		Thread.sleep(5000);
		// Store the current window handle
	//	String WinHandleBefore = driver.getWindowHandle();
		Clickon(getwebelement(xml.getlocator("//locators/TransactionIdLink")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction Id");
		Thread.sleep(5000);
		
		Set<String> handle = driver.getWindowHandles();
		Iterator<String> iterators = handle.iterator();
		iterators.next();
		String current=iterators.next();
	    System.out.println("Window handel"+current);
	    driver.switchTo().window(current);
	 // Perform the actions on new window
	    String BuildingNumber = Gettext(getwebelement(xml.getlocator("//locators/VerifyBuldingNumber")));
	    String BuildingName = Gettext(getwebelement(xml.getlocator("//locators/VerifyBuildingName")));
	    String StreetName = Gettext(getwebelement(xml.getlocator("//locators/VerifyStreetName")));
	    String City = Gettext(getwebelement(xml.getlocator("//locators/VerifyCity")));
	    String PostCode = Gettext(getwebelement(xml.getlocator("//locators/VerifyPostCode")));
	    Assert.assertTrue(BuildingNumber.contains(Inputdata[0][9].toString()));
	    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Building number: "+BuildingNumber);
	    Assert.assertTrue(BuildingName.contains(Inputdata[0][8].toString()));
	    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Building name: "+BuildingName);
	    Assert.assertTrue(StreetName.contains(Inputdata[0][10].toString()));
	    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Street name: "+StreetName);
	    Assert.assertTrue(City.contains(Inputdata[0][11].toString()));
	    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify City : "+City);
	    Assert.assertTrue(PostCode.contains(Inputdata[0][12].toString()));
	    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Post Code: "+PostCode);
	    Thread.sleep(5000);
	    driver.close();
	    driver.switchTo().window(winHandleBefore);
	    
	}
	public void Scenario4(Object[][]Inputdata) throws Exception
	{
		Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
		Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
		Select(getwebelement(xml.getlocator("//locators/ResellerName")),Inputdata[0][0].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
		Select(getwebelement(xml.getlocator("//locators/Country")),Inputdata[0][1].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/ServiceProfile")),Inputdata[0][2].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/SearchCriteria")),"Number Status");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number status");
		Select(getwebelement(xml.getlocator("//locators/NumberStatus")),"Activated");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number status as Activated");
		Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
		Thread.sleep(10000);
		Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Radio button");
		Select(getwebelement(xml.getlocator("//locators/UserAction")),"Deactivate");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action");
		Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
		Thread.sleep(5000);
		Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
		Thread.sleep(10000);
		String Transactionresult =Gettext(getwebelement(xml.getlocator("//locators/TransactionResult")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : "+Transactionresult);
		Thread.sleep(10000);
		// Store the current window handle
		String winHandleBefore = driver.getWindowHandle();
		
		Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction Id");
		System.out.println(Transactionresult);
		Thread.sleep(5000);
		
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> iterator = handles.iterator();
		iterator.next();
		String curent=iterator.next();
	    System.out.println("Window handel"+curent);
	    driver.switchTo().window(curent);
	 // Perform the actions on new window
	    String TransactionStatus=null;
	    do {
	        TransactionStatus = Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
		    System.out.println(TransactionStatus);
	    	Thread.sleep(5000);
	    	Pagerefresh();
	    }
	    while(!TransactionStatus.equals("Completed"));
	
	    Assert.assertTrue(TransactionStatus.contains("Completed"));
	//    VerifyText(TransactionStatus);
	    
	    Thread.sleep(5000);
	    driver.close();
	    driver.switchTo().window(winHandleBefore);
	    Thread.sleep(5000);
	    Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
		Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
		Select(getwebelement(xml.getlocator("//locators/ResellerName")),Inputdata[0][0].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
		Select(getwebelement(xml.getlocator("//locators/Country")),Inputdata[0][1].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/ServiceProfile")),Inputdata[0][2].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
		Thread.sleep(3000);
		Select(getwebelement(xml.getlocator("//locators/SearchCriteria")),"Transaction ID");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Transaction Id");
		SendKeys(getwebelement(xml.getlocator("//locators/InputTransactionId")),Transactionresult);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Transaction Id: "+Transactionresult);
		Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
		Thread.sleep(10000);
		String Status = Gettext(getwebelement(xml.getlocator("//locators/Status")));
		System.out.println(Status);
		Assert.assertTrue(Status.contains("Quarantined"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify the Ststus :"+Status);
		
	}

}
