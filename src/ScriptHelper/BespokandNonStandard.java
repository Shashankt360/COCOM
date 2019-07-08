package ScriptHelper;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import Driver.DriverHelper;
import Driver.PropertyReader;
import Driver.xmlreader;

public class BespokandNonStandard extends DriverHelper
{
	WebElement el;
	xmlreader xml=new xmlreader("src\\Locators\\BespokandNonStandard.xml");
	PropertyReader pr=new PropertyReader();
	public BespokandNonStandard(WebDriver parentdriver)
	{
		super(parentdriver);
	}

public void Bespoke(Object[][] Inputdata) throws Exception
	{	
	CurrentURL();
System.out.println("In Bespoke and Non Standrard");
if(Inputdata[0][15].equals("BeSpoke")){
//	openurl("CPQAdmin");
////	Switchtotab();
//	WaitforElementtobeclickable(xml.getlocator("//locators/InternalUser"));
//	Clickon(getwebelement(xml.getlocator("//locators/InternalUser")));
//	WaitforElementtobeclickable(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_SE_User")));
//	Clickon(getwebelement(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net",pr.readproperty("CPQ_SE_User"))));
	ProxyLogin("CPQ_SE_User", xml.getlocator("//locators/ProxyLink"));
	
	BespokeSE();
	BespokeSales();
}
else if(Inputdata[0][15].equals("Non Standard"))
{
//	openurl("CPQAdmin");
////	Switchtotab();
//	WaitforElementtobeclickable(xml.getlocator("//locators/InternalUser"));
//	Clickon(getwebelement(xml.getlocator("//locators/InternalUser")));
//	WaitforElementtobeclickable(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net", pr.readproperty("CPQ_SE_User")));
//	Clickon(getwebelement(xml.getlocator("//locators/UsernameProxy").replace("Ashwani.Singh31@colt.net",pr.readproperty("CPQ_SE_User"))));
	ProxyLogin("CPQ_SE_User", xml.getlocator("//locators/ProxyLink"));
	
	NonStandardSE();
NonStandardSales();
}
	}

	public void BespokeSE() throws Exception
	{
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/QuotetoOrderLink"));
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/QuotetoOrderLink")));
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/Quotelink").replace("QuoteId", QuoteID.get().trim()));
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/Quotelink").replace("QuoteId", QuoteID.get().trim())));
		
		//Code to reconfigure
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/PLtab"));
		System.out.println(xml.getlocator("//locators/ModelSelector").replace("index", String.valueOf(1)));
		WaitforElementtobeclickable(xml.getlocator("//locators/ModelSelector").replace("index", String.valueOf(1)));
		
		Clickon(getwebelement(xml.getlocator("//locators/ModelSelector").replace("index", String.valueOf(1))));
		Thread.sleep(3000);
		WaitforElementtobeclickable(xml.getlocator("//locators/Reconfigure"));
		
		Clickon(getwebelement(xml.getlocator("//locators/Reconfigure")));
		Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
		Getmaploaded(xml.getlocator("//locators/GoogleMapifram"), xml.getlocator("//locators/Messages"));
		
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/Featuretab"));
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/Featuretab")));
		Select feature=new Select(getwebelement(xml.getlocator("//locators/Bespoke/BespokeFeaturedropdown")));
		feature.selectByVisibleText("Fast Track-Bespoke");
		Clear(getwebelement(xml.getlocator("//locators/Bespoke/BespokeCapex")));
		
		SendKeys(getwebelement(xml.getlocator("//locators/Bespoke/BespokeCapex")),"500");
		Select capexincre=new Select(getwebelement(xml.getlocator("//locators/Bespoke/Capexincredec")));
		capexincre.selectByVisibleText("Incremental");
		Clear(getwebelement(xml.getlocator("//locators/Bespoke/BespokeOpex")));
		SendKeys(getwebelement(xml.getlocator("//locators/Bespoke/BespokeOpex")),"500");
		Select opexindre=new Select(getwebelement(xml.getlocator("//locators/Bespoke/Opexincredec")));
		opexindre.selectByVisibleText("Incremental");
		
		Select frequcny=new Select(getwebelement(xml.getlocator("//locators/Bespoke/Frequency")));
		frequcny.selectByVisibleText("Quarterly");
		Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
		
		SendKeys(getwebelement(xml.getlocator("//locators/Bespoke/Notes")),"Test");
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/Update")));
		Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/Save")));
		//Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
		
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/PLtab")));
		SendKeys(getwebelement(xml.getlocator("//locators/Bespoke/Tsolution")), "TS");
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/Save")));
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/Proxylogout")));
		
		}
	
	// Need to call Edit method before following method
	
	public void BespokeSales() throws Exception
	{
		openurl2(CurrentQuoteURL.get().toString());
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/PLtab"));		
		
		VerifyTextpresent("Deal Pricing Review Required");			
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/PLtab"));		
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/PLtab")));
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/Tsolution"));
		SendKeys(getwebelement(xml.getlocator("//locators/Bespoke/Tsolution")), "TS");
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/Dealbackground"));
		SendKeys(getwebelement(xml.getlocator("//locators/Bespoke/Dealbackground")), "DB");
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/Comparea"));
		SendKeys(getwebelement(xml.getlocator("//locators/Bespoke/Comparea")), "CM");
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/Approvaltab")));
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/SubmittoApproval")));
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/GeneralInfo"));
		Clickon(getwebelement(xml.getlocator("//locators/Bespoke/GeneralInfo")));
		VerifyTextpresent("Deal Pricing Review");
		
	}
	public void NonStandardSE() throws Exception
	{
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/QuotetoOrderLink"));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/QuotetoOrderLink")));
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/Quotelink").replace("QuoteId", QuoteID.get().trim()));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/Quotelink").replace("QuoteId", QuoteID.get().trim())));
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/PLtab"));
		System.out.println(xml.getlocator("//locators/ModelSelector").replace("index", String.valueOf(1)));
		WaitforElementtobeclickable(xml.getlocator("//locators/ModelSelector").replace("index", String.valueOf(1)));
		
		Clickon(getwebelement(xml.getlocator("//locators/ModelSelector").replace("index", String.valueOf(1))));
		Thread.sleep(3000);
		WaitforElementtobeclickable(xml.getlocator("//locators/Reconfigure"));
		
		Clickon(getwebelement(xml.getlocator("//locators/Reconfigure")));
		Getloadingcomplete(xml.getlocator("//locators/LoadingDailog"));
		Getmaploaded(xml.getlocator("//locators/GoogleMapifram"), xml.getlocator("//locators/Messages"));
		
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/SiteDetail"));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/SiteDetail")));
		//Enter Capex
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/Capex"));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/Capex")));
		
		//Select Capex interface
		Select capexinterface=new Select(getwebelement(xml.getlocator("//locators/NonStandard/Capexincre")));
		capexinterface.selectByVisibleText("Incremental");
		
		//Enter Opex
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/Opex"));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/Opex")));
		
		//Select Opex interface
		Select opexinterface=new Select(getwebelement(xml.getlocator("//locators/NonStandard/Capexincre")));
		opexinterface.selectByVisibleText("Incremental");
		
		//Select Frequency
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/Frequency"));
		Select freq=new Select(getwebelement(xml.getlocator("//locators/NonStandard/Frequency")));
		freq.selectByVisibleText("Replacement");
		//Select Notes
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/Notes"));
		SendKeys(getwebelement(xml.getlocator("//locators/NonStandard/Notes")), "Test");
		//Click on Save
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/Savequote"));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/Savequote")));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/PLtab")));
		SendKeys(getwebelement(xml.getlocator("//locators/NonStandard/Tsolution")), "TS");
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/Save")));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/Proxylogout")));
		
		}
	
	// Need to call Edit method before following method
	public void NonStandardSales() throws Exception
	{
		openurl2(CurrentQuoteURL.get().toString());
		WaitforElementtobeclickable(xml.getlocator("//locators/Bespoke/PLtab"));		
			
		VerifyTextpresent("Deal Pricing Review Required");			
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/PLtab"));		
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/PLtab")));
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/Tsolution"));
		SendKeys(getwebelement(xml.getlocator("//locators/NonStandard/Tsolution")), "TS");
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/Dealbackground"));
		SendKeys(getwebelement(xml.getlocator("//locators/NonStandard/Dealbackground")), "DB");
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/Comparea"));
		SendKeys(getwebelement(xml.getlocator("//locators/NonStandard/Comparea")), "CM");
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/Approvaltab")));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/SubmittoApproval")));
		WaitforElementtobeclickable(xml.getlocator("//locators/NonStandard/GeneralInfo"));
		Clickon(getwebelement(xml.getlocator("//locators/NonStandard/GeneralInfo")));
		VerifyTextpresent("Deal Pricing Review");
	}

}
