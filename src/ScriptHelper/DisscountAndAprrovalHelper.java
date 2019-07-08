package ScriptHelper;

import java.text.DecimalFormat;

import org.dom4j.DocumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;
import Driver.DriverHelper;
import Driver.PropertyReader;
import Driver.xmlreader;
import Reporter.ExtentTestManager;

public class DisscountAndAprrovalHelper extends DriverHelper{
	
	
	
	WebElement el;
	xmlreader xml=new xmlreader("src\\Locators\\DisscountandAprroval.xml");
	xmlreader xml2=new xmlreader("src\\Locators\\C4C.xml");
	xmlreader xml3=new xmlreader("src\\Locators\\Configuration.xml");
	PropertyReader pr=new PropertyReader();
	public DisscountAndAprrovalHelper(WebDriver parentdriver)
	{
		super(parentdriver);
	}

	public void DisscountandApprove(Object[][] Inputdata,int i) throws Exception {
	
		//ApplyDisscount(Inputdata,i);
		ApproveQuote(Inputdata);
		
	}
	
public void ApplyDisscountQuotelevel(Object[][] Inputdata) throws Exception {
	
		System.out.println("Quote Level Disscount");
		
		
		if(Inputdata[0][21].toString().split(">")[1].trim().equals("Percentage Off")) {
			WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuoteTab"));
			javascriptexecutor(getwebelement(xml.getlocator("//locators/ApprovalTab")));
			Clickon(getwebelement(xml.getlocator("//locators/CPQQuoteTab")));
			Thread.sleep(5000);
		WaitforElementtobeclickable(xml.getlocator("//locators/Percentage/DiscountType"));
		//Select(getwebelement(xml.getlocator("//locators/Percentage/DiscountType")), Inputdata[0][21].toString().split(">")[1].trim());
		Clickon(getwebelement(xml.getlocator("//locators/Percentage/DiscountType")));
		Clickon(getwebelement(xml.getlocator("//locators/Percentage/OptionstoSelect")));
		WaitforElementtobeclickable(xml.getlocator("//locators/Percentage/NRCDiscount"));
		Clear(getwebelement(xml.getlocator("//locators/Percentage/NRCDiscount")));
		WaitforElementtobeclickable(xml.getlocator("//locators/Percentage/NRCDiscount"));
		SendKeys(getwebelement(xml.getlocator("//locators/Percentage/NRCDiscount")), Inputdata[0][22].toString());
		WaitforElementtobeclickable(xml.getlocator("//locators/Percentage/MRCDiscount"));
		Clear(getwebelement(xml.getlocator("//locators/Percentage/MRCDiscount")));
		WaitforElementtobeclickable(xml.getlocator("//locators/Percentage/MRCDiscount"));
		SendKeys(getwebelement(xml.getlocator("//locators/Percentage/MRCDiscount")), Inputdata[0][23].toString());
		Thread.sleep(2000);
//		WaitforElementtobeclickable(xml.getlocator("//locators/Percentage/CalculateDisscount"));
//		Clickon(getwebelement(xml.getlocator("//locators/Percentage/CalculateDisscount")));
		SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Percentage/MRCDiscount")),Keys.TAB);
		WaitforElementtobeclickable(xml.getlocator("//locators/Percentage/CalculateDisscount"));
		Clickon(getwebelement(xml.getlocator("//locators/Percentage/CalculateDisscount")));
		System.out.println("NRC Disscount"+Inputdata[0][22].toString());
		System.out.println("NRC Disscount"+Inputdata[0][23].toString());
		}
		else if(Inputdata[0][21].toString().split(">")[1].trim().equals("Amount Off")){
			WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuoteTab"));
			javascriptexecutor(getwebelement(xml.getlocator("//locators/ApprovalTab")));
			Clickon(getwebelement(xml.getlocator("//locators/CPQQuoteTab")));
			WaitforElementtobeclickable(xml.getlocator("//locators/Ammountoff/DiscountType"));
			//Select(getwebelement(xml.getlocator("//locators/Ammountoff/DiscountType")), Inputdata[0][21].toString().split(">")[1].trim());
			Clickon(getwebelement(xml.getlocator("//locators/Ammountoff/DiscountType")));
			Clickon(getwebelement(xml.getlocator("//locators/Ammountoff/OptionstoSelect")));
			WaitforElementtobeclickable(xml.getlocator("//locators/Ammountoff/NRCDiscount"));
			Clear(getwebelement(xml.getlocator("//locators/Ammountoff/NRCDiscount")));
			WaitforElementtobeclickable(xml.getlocator("//locators/Ammountoff/NRCDiscount"));
			SendKeys(getwebelement(xml.getlocator("//locators/Ammountoff/NRCDiscount")), Inputdata[0][22].toString());
			WaitforElementtobeclickable(xml.getlocator("//locators/Ammountoff/MRCDiscount"));
			Clear(getwebelement(xml.getlocator("//locators/Ammountoff/MRCDiscount")));
			WaitforElementtobeclickable(xml.getlocator("//locators/Ammountoff/MRCDiscount"));
			SendKeys(getwebelement(xml.getlocator("//locators/Ammountoff/MRCDiscount")), Inputdata[0][23].toString());
			Thread.sleep(2000);
			WaitforElementtobeclickable(xml.getlocator("//locators/Ammountoff/CalculateDisscount"));
			Clickon(getwebelement(xml.getlocator("//locators/Ammountoff/CalculateDisscount")));
			//WaitforElementtobeclickable(xml.getlocator("//locators/Ammountoff/CalculateDisscount"));
			//Clickon(getwebelement(xml.getlocator("//locators/Ammountoff/CalculateDisscount")));
			System.out.println("NRC Disscount"+Inputdata[0][22].toString());
			System.out.println("NRC Disscount"+Inputdata[0][23].toString());
		}
		else if(Inputdata[0][21].toString().split(">")[1].trim().equals("Target Price"))
		{
			WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuoteTab"));
			javascriptexecutor(getwebelement(xml.getlocator("//locators/ApprovalTab")));
			Clickon(getwebelement(xml.getlocator("//locators/CPQQuoteTab")));
			WaitforElementtobeclickable(xml.getlocator("//locators/TargetPrice/DiscountType"));
			//Select(getwebelement(xml.getlocator("//locators/TargetPrice/DiscountType")), Inputdata[0][21].toString().split(">")[1].trim());
			Clickon(getwebelement(xml.getlocator("//locators/TargetPrice/DiscountType")));
			Clickon(getwebelement(xml.getlocator("//locators/TargetPrice/OptionstoSelect")));
			WaitforElementtobeclickable(xml.getlocator("//locators/TargetPrice/NRCDiscount"));
			Clear(getwebelement(xml.getlocator("//locators/TargetPrice/NRCDiscount")));
			WaitforElementtobeclickable(xml.getlocator("//locators/TargetPrice/NRCDiscount"));
			SendKeys(getwebelement(xml.getlocator("//locators/TargetPrice/NRCDiscount")), Inputdata[0][22].toString());
			WaitforElementtobeclickable(xml.getlocator("//locators/TargetPrice/MRCDiscount"));
			Clear(getwebelement(xml.getlocator("//locators/TargetPrice/MRCDiscount")));
			WaitforElementtobeclickable(xml.getlocator("//locators/TargetPrice/MRCDiscount"));
			SendKeys(getwebelement(xml.getlocator("//locators/TargetPrice/MRCDiscount")), Inputdata[0][23].toString());
			Thread.sleep(2000);
			WaitforElementtobeclickable(xml.getlocator("//locators/TargetPrice/CalculateDisscount"));
			Clickon(getwebelement(xml.getlocator("//locators/TargetPrice/CalculateDisscount")));
			WaitforElementtobeclickable(xml.getlocator("//locators/TargetPrice/CalculateDisscount"));
			Clickon(getwebelement(xml.getlocator("//locators/TargetPrice/CalculateDisscount")));
			System.out.println("NRC Disscount"+Inputdata[0][22].toString());
			System.out.println("NRC Disscount"+Inputdata[0][23].toString());
		}
		else
		{
			System.out.println("No Disscount Required");
		}
	
}
public void ApplyDisscountlinelevel(Object[][] Inputdata) throws Exception {

	{
		 for(int i=0;i<Inputdata.length;i++) {
			 System.out.println("NRC Disscount"+Inputdata[i][22].toString());
				System.out.println("NRC Disscount"+Inputdata[i][23].toString());
			  }
		
		//System.out.println("Line Level Disscount");
	}
}
public void ApproveQuote(Object[][] Inputdata) throws Exception {
	
	waitandForElementtobenotDisplay(xml.getlocator("//locators/AjaxLoader1"),1);
	waitForpageload();
	//Thread.sleep(10000);
		//WaitforElementtobeclickable(xml.getlocator("//locators/Showdisscountcoulumn"));
	safeJavaScriptClick(getwebelement(xml.getlocator("//locators/Showdisscountcoulumn")));
	//Clickonoutofviewportwithstring(xml.getlocator("//locators/Showdisscountcoulumn"));
		WaitforElementtobeclickable(xml.getlocator("//locators/TcvDisscount"));
		//DecimalFormat df = new DecimalFormat("0.00");
		//df.setMaximumFractionDigits(2);
		//System.out.println(Gettext(getwebelement(xml.getlocator("//locators/TcvDisscount"))));
		//TotalTCVdisscount.set(Float.parseFloat(Gettext(getwebelement(xml.getlocator("//locators/TcvDisscount")))));
		
	//WaitforElementtobeclickable(xml.getlocator("//locators/TcvDisscount"));
	System.out.println(Gettext(getwebelement(xml.getlocator("//locators/TcvDisscount"))));
	TotalTCVdisscount.set(Float.parseFloat(Gettext(getwebelement(xml.getlocator("//locators/TcvDisscount")))));
	DefineApprover(Inputdata);
	SubmitforApproval(ApprovalCase.get(),Inputdata);
	
	ApproveQuote(ApprovalCase.get());
	//NavigatetoC4C();
	if(!ApprovalCase.get().equals("Self Approve")) {
	Movetoaccount(Inputdata);
	MovetoOpportunuity(Inputdata);
	EditQuote();
	
	
	
}
	//-----------------------------------------------
		if(isElementPresent(xml.getlocator("//locators/TechnicalApprovalTab")))
		{
			Clickon(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
			Thread.sleep(3000);
			//javascriptexecutor2(getwebelement(xml.getlocator("//locators/CheckboxTechnicalApproval")));
			ClickswithAction(xml.getlocator("//locators/CheckboxTechnicalApproval"));
			Thread.sleep(3000);
			Clickon(getwebelement(xml.getlocator("//locators/SubmitTechnicalApprovalButton")));
		//	Clickon(getwebelement(xml.getlocator("//locators/ReturnToC4C")));
			
			SEEngagement(Inputdata);
			//---------------------------------------------------	
			CSTEngagement(Inputdata);
			
		}
		
		Quotestatus.set(GetValueofInput(getwebelement(xml.getlocator("//locators/Quotestatus"))));
		System.out.println("Quite Stage on Screee"+GetValueofInput(getwebelement(xml.getlocator("//locators/Quotestatus"))));
		
	}

public void SEEngagement(Object[][] Inputdata) throws InterruptedException, Exception 
{
	//openurl("CPQAdmin");
//	Switchtotab();
	
	//WaitforElementtobeclickable(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_SE_User")));
	//Clickon(getwebelement(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_SE_User"))));
	ProxyLogin("CPQ_SE_User", xml.getlocator("//locators/ProxyLink"));
	
	WaitforElementtobeclickable(xml.getlocator("//locators/QuotetoOrderLink"));
	Clickon(getwebelement(xml.getlocator("//locators/QuotetoOrderLink")));
	WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim()));
	Clickon(getwebelement(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim())));
	waitForpageload();
	WaitforCPQloader();
	/// Reconfiguration Options...
	// No Re-configuration
	// REconfiguration - Bandwidth Change
	// REconfiguration - New Custome Feature
	// REconfiguration - Contract term update
	// REconfiguration - Address update
	UpgradeQuote(Inputdata);
	WaitforElementtobeclickable(xml.getlocator("//locators/TechnicalApprovalTab"));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
	javascriptexecutor(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Approval Tab");
	Clickon(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
	WaitforElementtobeclickable(xml.getlocator("//locators/SubmitToCSTApproval"));
	Clickon(getwebelement(xml.getlocator("//locators/SubmitToCSTApproval")));
	Thread.sleep(30000);
//	openurl("CPQAdmin");
//	WaitforElementtobeclickable(xml.getlocator("//locators/InternalUser"));
//	Clickon(getwebelement(xml.getlocator("//locators/InternalUser")));
//	Clickon(getwebelement(xml.getlocator("//locators/Proxylogout")));
//	Thread.sleep(30000);
//	openurl("CPQAdmin");
	
}

public void UpgradeQuote(Object[][] Data) throws InterruptedException, Exception 
{
	for(int i=0;i<Data.length;i++) {
		if(!Data[i][15].toString().equals("")&&!Data[i][15].toString().equals("BeSpoke")&&!Data[i][15].toString().equals("Non Standard")) {
		WaitforElementtobeclickable(xml3.getlocator("//locators/ModelSelector").replace("index", String.valueOf(i+1)));
		
		Clickon(getwebelement(xml3.getlocator("//locators/ModelSelector").replace("index", String.valueOf(i+1))));
		WaitforElementtobeclickable(xml3.getlocator("//locators/Reconfigure"));
		
		Clickon(getwebelement(xml3.getlocator("//locators/Reconfigure")));
		Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
		Getmaploaded(xml3.getlocator("//locators/GoogleMapifram"), xml.getlocator("//locators/Messages"));
	   switch(Data[i][15].toString())
	   {
	   case "Bandwidth":
	   {// Update the Bandwidth from Data[i][16].toString()
		   ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Site Detail Tab");
		   Clickon(getwebelement(xml3.getlocator("//locators/SiteDetailTab")));
		   ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
			Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select Badwidth");
			Select(getwebelement(xml3.getlocator("//locators/ServiceBandwidth")), Data[i][16].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
			Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
			WaitforElementtobeclickable(xml3.getlocator("//locators/updateQuote"));
			
			Clickon(getwebelement(xml3.getlocator("//locators/updateQuote")));
			Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
			Thread.sleep(3000);
			WaitforElementtobeclickable(xml3.getlocator("//locators/ReconfigsaveQuote"));
			
			Clickon(getwebelement(xml3.getlocator("//locators/ReconfigsaveQuote")));
			//Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
			// Need to check if New request need to be raised..
			// raised a request whenever required
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
			WaitforElementtobeclickable(xml3.getlocator("//locators/ApprovalTab"));
			Thread.sleep(10000);
			break;
	   }
	   case "Contract Term":
	   {// Update the Contract tearm Data[i][16].toString()
		   ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Site Detail Tab");
			Clickon(getwebelement(xml3.getlocator("//locators/SiteDetailTab")));
			 ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select Contract Term");
				Select(getwebelement(xml3.getlocator("//locators/Contractterm")), Data[i][16].toString());
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
				Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
				WaitforElementtobeclickable(xml3.getlocator("//locators/updateQuote"));
				
				Clickon(getwebelement(xml3.getlocator("//locators/updateQuote")));
				Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
				Thread.sleep(3000);
				WaitforElementtobeclickable(xml3.getlocator("//locators/ReconfigsaveQuote"));
				
				Clickon(getwebelement(xml3.getlocator("//locators/ReconfigsaveQuote")));
				//Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
				// Need to check if New request need to be raised..
				// raised a request whenever required
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
				WaitforElementtobeclickable(xml3.getlocator("//locators/ApprovalTab"));
				Thread.sleep(10000);
		   break;   
	   }
	   case "Feature":
	   {// Add a customer Feature Here Data[i][16].toString()
		   javascriptexecutor(getwebelement(xml.getlocator("//locators/FeaturesTab")));
		   ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click On Feature Tab");
		   Clickon(getwebelement(xml.getlocator("//locators/FeaturesTab")));
		   ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
		   Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
		   Select(getwebelement(xml.getlocator("//locators/BespokeFeature")),Data[i][16].toString());
		   Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
		   getwebelement(xml.getlocator("//locators/BespokeCapex")).clear();
		   SendKeys(getwebelement(xml.getlocator("//locators/BespokeCapex")),"500");
		   Select(getwebelement(xml.getlocator("//locators/IncrementalCapex")),"Incremental");
		   getwebelement(xml.getlocator("//locators/BespokeOpex")).clear();
		   SendKeys(getwebelement(xml.getlocator("//locators/BespokeOpex")),"500");
		   Select(getwebelement(xml.getlocator("//locators/IncrementalOpex")),"Incremental");
		   Select(getwebelement(xml.getlocator("//locators/Frequency")),"Monthly");
		   SendKeys(getwebelement(xml.getlocator("//locators/ProductDescription")),"Filled by Automation");
		   Clickon(getwebelement(xml3.getlocator("//locators/Clickupdate")));
		   Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
		   Thread.sleep(3000);
			WaitforElementtobeclickable(xml3.getlocator("//locators/ReconfigsaveQuote"));
			
			Clickon(getwebelement(xml3.getlocator("//locators/ReconfigsaveQuote")));
			//Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
			// Need to check if New request need to be raised..
			// raised a request whenever required
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
			WaitforElementtobeclickable(xml3.getlocator("//locators/ApprovalTab"));
			Thread.sleep(10000);
		   break; 
	   }
	   case "A Site Address":
	   { 
		   ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter A Site Address");
			SendKeys(getwebelement(xml3.getlocator("//locators/Asite")),Data[i][16].toString());
			Thread.sleep(2000);
			//SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Asite")),Keys.ARROW_DOWN);
			SendkeaboardKeys(getwebelement(xml3.getlocator("//locators/Asite")),Keys.ENTER);
			//getwebelement(xml.getlocator("//locators/LoadingDailog"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
			Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
			Getmaploaded(xml3.getlocator("//locators/GoogleMapifram"), xml.getlocator("//locators/Messages"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
			javascriptexecutor(getwebelement(xml3.getlocator("//locators/ConnectivitiCheck")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Next button to Run the Connectivity Check");
			Clickon(getwebelement(xml3.getlocator("//locators/ConnectivitiCheck")));
			//getwebelement(xml.getlocator("//locators/LoadingDailog"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
			Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Map to be loaded completly");
			Getmaploaded(xml3.getlocator("//locators/GoogleMapifram"), xml.getlocator("//locators/Messages"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Site Detail Tab");
			Clickon(getwebelement(xml3.getlocator("//locators/SiteDetailTab")));
			String exception=Gettext(getwebelement(xml3.getlocator("//locators/PPT/BasePrice")));
			if(exception.equalsIgnoreCase("Price Not Found"))
			{
				Clickon(getwebelement(xml3.getlocator("//locators/PPT/PartialSave")));
				Clickon(getwebelement(xml3.getlocator("//locators/PPT/Save")));
				
			}
			else
			{
				Clickon(getwebelement(xml3.getlocator("//locators/PPT/AddtoTransaction")));
			}
			ExceptionPPT();
			openurl("CPQAdmin");
//			Switchtotab();
			WaitforElementtobeclickable(xml3.getlocator("//locators/ModelSelector").replace("index", String.valueOf(i+1)));
			
			Clickon(getwebelement(xml3.getlocator("//locators/ModelSelector").replace("index", String.valueOf(i+1))));
			WaitforElementtobeclickable(xml3.getlocator("//locators/Reconfigure"));
			
			Clickon(getwebelement(xml3.getlocator("//locators/Reconfigure")));
			Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
			Getmaploaded(xml3.getlocator("//locators/GoogleMapifram"), xml.getlocator("//locators/Messages"));
			Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
			Clickon(getwebelement(xml3.getlocator("//locators/Clickupdate")));
			Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
			WaitforElementtobeclickable(xml3.getlocator("//locators/ReconfigsaveQuote"));
			
			Clickon(getwebelement(xml3.getlocator("//locators/ReconfigsaveQuote")));
			//Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
			// Need to check if New request need to be raised..
			// raised a request whenever required
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
			WaitforElementtobeclickable(xml3.getlocator("//locators/ApprovalTab"));
			Thread.sleep(10000);
			break; 
	   }
	   case "B Site Address":
	   {
		   ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter B Siter Address");
		 //Thread.sleep(2000);
		SendKeys(getwebelement(xml3.getlocator("//locators/Bsite")),Data[i][16].toString());
		Thread.sleep(2000);
		//SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Asite")),Keys.ARROW_DOWN);
		SendkeaboardKeys(getwebelement(xml3.getlocator("//locators/Bsite")),Keys.ENTER);
		//getwebelement(xml.getlocator("//locators/LoadingDailog"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
		Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
		//Thread.sleep(3000);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Map to be loaded completly");
		Getmaploaded(xml3.getlocator("//locators/GoogleMapifram"), xml.getlocator("//locators/Messages"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
		javascriptexecutor(getwebelement(xml3.getlocator("//locators/ConnectivitiCheck")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Next button to Run the Connectivity Check");
		Clickon(getwebelement(xml3.getlocator("//locators/ConnectivitiCheck")));
		//getwebelement(xml.getlocator("//locators/LoadingDailog"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
		Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Map to be loaded completly");
		Getmaploaded(xml3.getlocator("//locators/GoogleMapifram"), xml.getlocator("//locators/Messages"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Site Detail Tab");
		Clickon(getwebelement(xml3.getlocator("//locators/SiteDetailTab")));
		String exception=Gettext(getwebelement(xml3.getlocator("//locators/PPT/BasePrice")));
		if(exception.equalsIgnoreCase("Price Not Found"))
		{
			Clickon(getwebelement(xml3.getlocator("//locators/PPT/PartialSave")));
			Clickon(getwebelement(xml3.getlocator("//locators/PPT/Save")));
			
		}
		else
		{
			Clickon(getwebelement(xml3.getlocator("//locators/PPT/AddtoTransaction")));
		}
		
		ExceptionPPT();
		WaitforElementtobeclickable(xml3.getlocator("//locators/ModelSelector").replace("index", String.valueOf(i+1)));
		
		Clickon(getwebelement(xml3.getlocator("//locators/ModelSelector").replace("index", String.valueOf(i+1))));
		WaitforElementtobeclickable(xml3.getlocator("//locators/Reconfigure"));
		
		Clickon(getwebelement(xml3.getlocator("//locators/Reconfigure")));
		Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
		Getmaploaded(xml3.getlocator("//locators/GoogleMapifram"), xml.getlocator("//locators/Messages"));
		Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
		Clickon(getwebelement(xml3.getlocator("//locators/Clickupdate")));
		Getloadingcomplete(xml3.getlocator("//locators/LoadingDailog"));
		WaitforElementtobeclickable(xml3.getlocator("//locators/ReconfigsaveQuote"));
		
		Clickon(getwebelement(xml3.getlocator("//locators/ReconfigsaveQuote")));
		//Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
		// Need to check if New request need to be raised..
		// raised a request whenever required
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for Loading to be completed");
		WaitforElementtobeclickable(xml3.getlocator("//locators/ApprovalTab"));
		Thread.sleep(10000);
		break;
	   }
	   default:
	   {
		   System.out.println("No  SE chnages need to be updated");
			   break;
	   }
	   }
	}
	}
	
}

public void CSTEngagement(Object[][] Data) throws InterruptedException, Exception 
{
//	WaitforElementtobeclickable(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_CST_User")));
//	Clickon(getwebelement(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_CST_User"))));
	ProxyLogin("CPQ_CST_User", xml.getlocator("//locators/ProxyLink"));
	WaitforElementtobeclickable(xml.getlocator("//locators/QuotetoOrderLink"));
	Clickon(getwebelement(xml.getlocator("//locators/QuotetoOrderLink")));
	WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim()));
	Clickon(getwebelement(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim())));
	waitForpageload();
	WaitforCPQloader();
	AddLineitemGridLeadTime(Data);
	WaitforElementtobeclickable(xml.getlocator("//locators/TechnicalApprovalTab"));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
	javascriptexecutor(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Approval Tab");
	Clickon(getwebelement(xml.getlocator("//locators/TechnicalApprovalTab")));
	Clickon(getwebelement(xml.getlocator("//locators/CSTApproval")));
	Thread.sleep(5000);
	openurl("CPQAdmin");
	WaitforElementtobeclickable(xml.getlocator("//locators/InternalUser"));
	Clickon(getwebelement(xml.getlocator("//locators/InternalUser")));
	Clickon(getwebelement(xml.getlocator("//locators/Proxylogout")));
	openurl2(CurrentQuoteURL.get());
	WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
	// Open the Quote the As Sales Users
	
}
public void AddLineitemGridLeadTime(Object[][] data) throws InterruptedException, Exception{
	// Need to write the code
    for(int i=0;i<data.length;i++) {
    	Thread.sleep(5000);
    	Clickonoutofviewportwithstring(xml.getlocator("//locators/LeadTime").replace("index", String.valueOf(i+1)));
    	Thread.sleep(20000);
    	//WaitforElementtobeclickable(xml.getlocator("//locators/LeadTime").replace("index", String.valueOf(i+1)));	
	//Clickon(getwebelement(xml.getlocator("//locators/LeadTime").replace("index", String.valueOf(i+1))));
	//SendKeys(getwebelement(xml.getlocator("//locators/LeadTime")),"20");
    	//Thread.sleep(5000);
    	//ClickswithAction(xml.getlocator("//locators/LeadTime").replace("index", String.valueOf(i+1)));
    	//Clickon(getwebelement(xml.getlocator("//locators/LeadTime").replace("index", String.valueOf(i+1))));
    	
    	Thread.sleep(20000);
    	EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		Thread.sleep(2000);
		EnterText("20");
	
    }
}
public void SubmitforApproval(String Approvalcase,Object[][] Inputdata) throws Exception
{
	if(ApprovalCase.get().equals("Deal Pricing"))
	{
		System.out.println("Deal Pricing Jouney Need to follow");
	}
	else if(ApprovalCase.get().equals("VP1 Sales") ||Approvalcase.equals("VP2 Sales"))
	{
		WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
		javascriptexecutor(getwebelement(xml.getlocator("//locators/ApprovalTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Approval Tab");
		Clickon(getwebelement(xml.getlocator("//locators/ApprovalTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting For Loading to be Completed");
		WaitforElementtobeclickable(xml.getlocator("//locators/SubmitetoApproval"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Submite for Approval");
		Clickon(getwebelement(xml.getlocator("//locators/SubmitetoApproval")));
		WaitforElementtobeclickable(xml.getlocator("//locators/ApproverCommentFinal"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Submite for Approval");
		SendKeys(getwebelement(xml.getlocator("//locators/ApproverCommentFinal")),"Automation Comemnt");
		Thread.sleep(3000);
		WaitforElementtobeclickable(xml.getlocator("//locators/FinalSubmit"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Submite for Approval");
		Clickon(getwebelement(xml.getlocator("//locators/FinalSubmit")));
		Thread.sleep(30000);
		
	}
	else {
		WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
		javascriptexecutor(getwebelement(xml.getlocator("//locators/ApprovalTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Approval Tab");
		Clickon(getwebelement(xml.getlocator("//locators/ApprovalTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting For Loading to be Completed");
		WaitforElementtobeclickable(xml.getlocator("//locators/SubmitetoApproval"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Submite for Approval");
		Clickon(getwebelement(xml.getlocator("//locators/SubmitetoApproval")));
		
		
	}

}
public void NavigatetoC4C() throws Exception
{
	Geturl(Getkeyvalue("C4C_URL"));

}
public void Movetoaccount(Object[][] Inputdata) throws Exception
{
	
	//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Customer Section");
	//Thread.sleep(10000);

	Clickon(getwebelement(xml2.getlocator("//locators/CustomerSection")));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Customer Section in Left Navigation");
	Clickon(getwebelement(xml2.getlocator("//locators/Accountlink")));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Account link in navidation");
	Clickon(getwebelement(xml2.getlocator("//locators/OCN").replace("Accountnumber", Inputdata[0][0].toString())));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Account Name");
}
public void  MovetoOpportunuity(Object[][] Inputdata) throws Exception{
	
	Clickon(getwebelement(xml2.getlocator("//locators/OpportunuityTab")));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Opportunuity Tab");
	Clickon(getwebelement(xml2.getlocator("//locators/Opportunuitynumber").replace("Opportunuitynumber", Inputdata[0][1].toString())));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Opportunuity Name ");
	Thread.sleep(10000);
	Clickon(getwebelement(xml2.getlocator("//locators/QuoteTab")));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Quote Tab in Opportunuity Detail page ");
	Thread.sleep(10000);
}
public void EditQuote() throws Exception {
	Clickon(getwebelement(xml2.getlocator("//locators/Quotenumber").replace("Quotenumber", QuoteID.get().toString())));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Add to Quote Button");
	Thread.sleep(10000);
	Clickon(getwebelement(xml2.getlocator("//locators/QuoteActions")));
	Thread.sleep(10000);
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Add to Quote Button");
	Clickon(getwebelement(xml2.getlocator("//locators/QuoteEdit")));
	ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Add to Quote Button");
	Thread.sleep(10000);
	WaitforElementtobeclickable(xml2.getlocator("//locators/customersignatureTab"));
		
}
public ThreadLocal<String> DefineApprover(Object[][] Inputdata)
{  //String ApprovalCase = null;
System.out.println("Deal Class is"+DealClass.get());
System.out.println("Toatal TCV Disscount is"+TotalTCVdisscount.get());
	if(DealClass.get().equals("Bronze")&& !Inputdata[0][28].toString().equals("Yes")) 
	{
		if(TotalTCVdisscount.get()<5.00) {
			System.out.println("Self Approve");
			//ApprovalCase="Self Approve";
			ApprovalCase.set("Self Approve");
		}
		else if(TotalTCVdisscount.get()<10.00 && TotalTCVdisscount.get()>=5.00) {
			System.out.println("VP1 Sales");
			//ApprovalCase="VP1 Sales";
			ApprovalCase.set("VP1 Sales");
		}
		else if(TotalTCVdisscount.get()<15.00 && TotalTCVdisscount.get()>=10.00) {
			System.out.println("VP2 Sales");
			//ApprovalCase="VP2 Sales";
			ApprovalCase.set("VP2 Sales");
		}
		else if(TotalTCVdisscount.get()>15.00) {
			System.out.println("Deal Pricing");
			//ApprovalCase="Deal Pricing";
			ApprovalCase.set("Deal Pricing");
		}
		
	}
	else
	{
		System.out.println("Deal Pricing");
		//ApprovalCase="Deal Pricing";
		ApprovalCase.set("Deal Pricing");

		
	}
	
	return ApprovalCase;
}
public void ExceptionPPT() throws Exception
{
	Clickon(getwebelement(xml3.getlocator("//locators/PPT/EngagePortfolio")));		
	ProxyLogin("CPQ_PPT_User",xml.getlocator("//locators/ProxyLink"));
	WaitforElementtobeclickable(xml.getlocator("//locators/QuotetoOrderLink"));
	Clickon(getwebelement(xml.getlocator("//locators/QuotetoOrderLink")));
	WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim()));
	Clickon(getwebelement(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim())));
	//Clickon(getwebelement(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim())));
	waitForpageload();
	WaitforCPQloader();
	Thread.sleep(10000);
	WaitforElementtobeclickable(xml.getlocator("//locators/PPT/PLtab"));
	Clickon(getwebelement(xml.getlocator("//locators/PPT/PLtab")));
	WaitforElementtobeclickable(xml.getlocator("//locators/Listexpander"));
	Clickon(getwebelement(xml.getlocator("//locators/Listexpander")));
	WaitforElementtobeclickable(xml.getlocator("//locators/UserSelector").replace("Username", "Prashant Manu"));
	Clickon(getwebelement(xml.getlocator("//locators/UserSelector").replace("Username", "Prashant Manu")));
	//Select assignuser=new Select(getwebelement("//select[@name='portfolioTeamAssignment']"));
	//assignuser.selectByVisibleText("Prashant Manu");
	WaitforElementtobeclickable(xml.getlocator("//locators/PPT/Assignquote"));
	Clickon(getwebelement(xml.getlocator("//locators/PPT/Assignquote")));
	//String exception=xml.getlocator("//locators/PPT/Bpnotfound").toString();
	Thread.sleep(20000);
	if(GetText(getwebelement(xml.getlocator("//locators/PPT/Bpnotfound").toString())).equalsIgnoreCase("Base price not found"))
	{
		//SendKeys(getwebelement(xml.getlocator("//locators/PPT/Basepricenrr")),"555");
		Thread.sleep(2000);
		safeJavaScriptClick(getwebelement(xml.getlocator("//locators/PPT/Basepricenrr")));
		Thread.sleep(2000);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		
		EnterText("555");
		SendKeys(getwebelement(xml.getlocator("//locators/PPT/Basepricemrr")),"666");
		Thread.sleep(2000);
		safeJavaScriptClick(getwebelement(xml.getlocator("//locators/PPT/Basepricemrr")));
		Thread.sleep(2000);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		
		EnterText("666");
	}
	else if(GetText(getwebelement(xml.getlocator("//locators/PPT/Fastnotfound").toString())).equalsIgnoreCase("FastTrack price not found"))
	{
		//Clickon(getwebelement(xml.getlocator("//locators/PPT/Fastracknrr")));
		Thread.sleep(2000);
		safeJavaScriptClick(getwebelement(xml.getlocator("//locators/PPT/Fastracknrr")));
		Thread.sleep(2000);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		EnterText2(Keys.BACK_SPACE);
		
		EnterText("777");
		//SendKeys(getwebelement(xml.getlocator("//locators/PPT/Fastracknrr")),"777");
		
	}
	else if(GetText(getwebelement(xml.getlocator("//locators/PPT/Pricingcitynotfound").toString())).equalsIgnoreCase("A-END Pricing City ( Linz) Not Found"))
	{
		//Select a row
		Clickon(getwebelement(xml.getlocator("//locators/PPT/Pricincity")));
		//Click on reconfigure
		Clickon(getwebelement(xml.getlocator("//locators/PPT/Reconfigure")));
		//Go to site details tab
		Clickon(getwebelement(xml.getlocator("//locators/PPT/Sitedetails")));
		//Select pricing city
		Select(getwebelement(xml.getlocator("//locators/PPT/PricingCitydropdown")), "Berlin");
		//click on update
		Clickon(getwebelement(xml.getlocator("//locators/PPT/Clickupdate")));
		//click on save
		Clickon(getwebelement(xml.getlocator("//locators/PPT/Clicksave")));
	}
		//Return quote to sales
		Thread.sleep(10000);
		Clickon(getwebelement(xml.getlocator("//locators/PPT/Sendtosales")));
		Thread.sleep(30000);
		openurl("CPQAdmin");
		WaitforElementtobeclickable(xml.getlocator("//locators/InternalUser"));
		Clickon(getwebelement(xml.getlocator("//locators/InternalUser")));
		Clickon(getwebelement(xml.getlocator("//locators/Proxylogout")));
		Thread.sleep(30000);
		openurl2(CurrentQuoteURL.get());
		WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
	
	
}
public void ApproveQuote(String Approver) throws Exception
{
	switch(Approver)
	{
		case "Self Approve" :
		{
			System.out.println("No Further Approval Required");
			break;
		}
		case "VP2 Sales" :
		{
			// Click on Admin link
//			WaitforElementtobeclickable(xml.getlocator("//locators/Admin"));
//			javascriptexecutor(getwebelement(xml.getlocator("//locators/Admin")));
//			
//			Clickon(getwebelement(xml.getlocator("//locators/Admin")));
//			openurl("Admin");
//			Switchtotab();
//			WaitforElementtobeclickable(xml.getlocator("//locators/InternalUser"));
//			Clickon(getwebelement(xml.getlocator("//locators/InternalUser")));
//			WaitforElementtobeclickable(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_VP1_Sales")));
//			Clickon(getwebelement(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_VP1_Sales"))));
			ProxyLogin("CPQ_VP2_Sales", xml.getlocator("//locators/ProxyLink"));
			
			WaitforElementtobeclickable(xml.getlocator("//locators/QuotetoOrderLink"));
			Clickon(getwebelement(xml.getlocator("//locators/QuotetoOrderLink")));
			
		
			WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim()));
			Clickon(getwebelement(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim())));
			
			WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
			javascriptexecutor(getwebelement(xml.getlocator("//locators/ApprovalTab")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Approval Tab");
			Clickon(getwebelement(xml.getlocator("//locators/ApprovalTab")));
			WaitforElementtobeclickable(xml.getlocator("//locators/ApproverComment"));
			SendKeys(getwebelement(xml.getlocator("//locators/ApproverComment")),"Approve");
			WaitforElementtobeclickable(xml.getlocator("//locators/ApproveasApprover"));
			Clickon(getwebelement(xml.getlocator("//locators/ApproveasApprover")));
			
			WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
			javascriptexecutor(getwebelement(xml.getlocator("//locators/ApprovalTab")));
			openurl("CPQAdmin");
			WaitforElementtobeclickable(xml.getlocator("//locators/InternalUser"));
			Clickon(getwebelement(xml.getlocator("//locators/InternalUser")));
			Clickon(getwebelement(xml.getlocator("//locators/Proxylogout")));
			openurl2(CurrentQuoteURL.get());
			WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
			Thread.sleep(30000);
			break;
		}
		case "VP1 Sales" :
		{
		
//			WaitforElementtobeclickable(xml.getlocator("//locators/Admin"));
//			Clickon(getwebelement(xml.getlocator("//locators/Admin")));
//			Switchtotab();
//			WaitforElementtobeclickable(xml.getlocator("//locators/InternalUser"));
//			Clickon(getwebelement(xml.getlocator("//locators/InternalUser")));
//
//
//			WaitforElementtobeclickable(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_VP1_Sales")));
//			Clickon(getwebelement(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_VP1_Sales"))));
			ProxyLogin("CPQ_VP1_Sales", xml.getlocator("//locators/ProxyLink"));
			
			WaitforElementtobeclickable(xml.getlocator("//locators/QuotetoOrderLink"));
			Clickon(getwebelement(xml.getlocator("//locators/QuotetoOrderLink")));
			
			WaitforElementtobeclickable(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim()));
			Clickon(getwebelement(xml.getlocator("//locators/CPQQuotelink").replace("QuoteId", QuoteID.get().trim())));
			
			WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Scroll the Page to Top");
			javascriptexecutor(getwebelement(xml.getlocator("//locators/ApprovalTab")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Approval Tab");
			Clickon(getwebelement(xml.getlocator("//locators/ApprovalTab")));
			WaitforElementtobeclickable(xml.getlocator("//locators/ApproverComment"));
			SendKeys(getwebelement(xml.getlocator("//locators/ApproverComment")),"Approve");
			WaitforElementtobeclickable(xml.getlocator("//locators/ApproveasApprover"));
			Clickon(getwebelement(xml.getlocator("//locators/ApproveasApprover")));
			
			WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
			javascriptexecutor(getwebelement(xml.getlocator("//locators/ApprovalTab")));
			openurl("CPQAdmin");
			WaitforElementtobeclickable(xml.getlocator("//locators/InternalUser"));
			Clickon(getwebelement(xml.getlocator("//locators/InternalUser")));
			Clickon(getwebelement(xml.getlocator("//locators/Proxylogout")));
			openurl2(CurrentQuoteURL.get());
			WaitforElementtobeclickable(xml.getlocator("//locators/ApprovalTab"));
			Thread.sleep(30000);
		}
		default:
		{
			System.out.println("Nothing to Do here");
			break;
		}
	}
}
}
