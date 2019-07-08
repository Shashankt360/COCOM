package ScriptHelper;

import org.dom4j.DocumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;
import Driver.DriverHelper;

import Driver.xmlreader;
import Reporter.ExtentTestManager;

//
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class LoginHelper extends DriverHelper{
	
	
	
	WebElement el;
	xmlreader xml=new xmlreader("src\\Locators\\Login.xml");
	
	public LoginHelper(WebDriver parentdriver)
	{
		super(parentdriver);
	}
	
	public void ProxyLogout() throws Exception
	{
		
	}
	public void Docusign(String url) throws Exception
	{
//		Gmail gm=new Gmail();
//		Message email = gm.getEmail("ashwanis@360logica.com", "ashwani@123", "Colt Proposal QT-20190513-031127-01");
//				System.out.println(email.getAllRecipients());
//				
//				System.out.println(email.getContent());
//				MimeMessage m = (MimeMessage)message;
//	            Object contentObject = m.getContent();
//	            if(contentObject instanceof Multipart)
//	            {
//	                BodyPart clearTextPart = null;
//	                BodyPart htmlTextPart = null;
//	                Multipart content = (Multipart)contentObject;
//	                int count = content.getCount();
//	                for(int i=0; i<count; i++)
//	                {
//	                    BodyPart part =  content.getBodyPart(i);
//	                    if(part.isMimeType("text/plain"))
//	                    {
//	                        clearTextPart = part;
//	                        break;
//	                    }
//	                    else if(part.isMimeType("text/html"))
//	                    {
//	                        htmlTextPart = part;
//	                    }
//	                }
//
//	                if(clearTextPart!=null)
//	                {
//	                    result = (String) clearTextPart.getContent();
//	                }
//	                else if (htmlTextPart!=null)
//	                {
//	                    String html = (String) htmlTextPart.getContent();
//	                    result = Jsoup.parse(html).text();
//	                }
//
//	            }
//	             else if (contentObject instanceof String) // a simple text message
//	            {
//	                result = (String) contentObject;
//	            }
//	            else // not a mime message
//	            {
//	                //logger.log(Level.WARNING,"notme part or multipart {0}",message.toString());
//	                result = null;
//	            }
//
//				//gm.getTextFromMimeMultipart(email.getContent());
//				System.out.println(email.getSubject());
//		
		Geturl("http://yopmail.com");
		
		SendKeys(getwebelement("//input[@id='login']"),"testingdata");
		Clickon(getwebelement("//input[@value='Check Inbox']"));
		Thread.sleep(4000);
		switchtofram(getwebelement("name=ifinbox"));
		System.out.println("Witched to iframe");
		Clickon(getwebelement("//span[contains(text(),'Colt Proposal QT-20190516-031202-01')]/parent::*"));
		switchtodefault();
		System.out.println("Witched to default content");
		Thread.sleep(4000);
		switchtofram(getwebelement("id=ifmail"));
		System.out.println("switched to iframe");
		Clickon(getwebelement("//span[contains(text(),'REVIEW DOCUMENT')]"));
		switchtodefault();
		Switchtotabandsignthequote();
		System.out.println("Witched to default content");
		
	}
//---------------------------------
	public void Login(String Application) throws Exception
	{
		openurl(Application);
		//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Navigated to "+Application+" Login Page");
		WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/Username"));  
		SendKeys(getwebelement(xml.getlocator("//locators/"+Application+"/Username")),Getkeyvalue(Application+"_Username"));
		//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter User Name");
		WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/Password"));
		SendKeys(getwebelement(xml.getlocator("//locators/"+Application+"/Password")),Getkeyvalue(Application+"_Password"));
		//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter Password");
		WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/Loginbutton"));
		
		Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/Loginbutton")));
		//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Login Button");
		
	}
	public void Logout(String Application) throws Exception
	{
		switch(Application)
		{
		case "C4C":
		{   Thread.sleep(2000);
			WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/UserProfile"));  
			Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/UserProfile")));
			 Thread.sleep(2000);
			WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/Logout"));  
			Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/Logout")));
			Thread.sleep(20000);
			break;
		}
		case "CPQ":
		{   Thread.sleep(2000);
			WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/Logout"));  
			Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/Logout")));
			Thread.sleep(20000);
			break;
		}
		case "Explore":
		{   Thread.sleep(2000);
			WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/UserProfile"));  
			Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/UserProfile")));
			 Thread.sleep(2000);
			WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/Logout"));  
			Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/Logout")));
			Thread.sleep(20000);
			break;
		}
		case "ExploreNearNet":
		{
			Thread.sleep(2000);
			WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/UserProfile"));  
			Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/UserProfile")));
			 Thread.sleep(2000);
			WaitforElementtobeclickable(xml.getlocator("//locators/"+Application+"/Logout"));  
			Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/Logout")));
			Thread.sleep(20000);
			break;
		}
		
		default:
		{
			System.out.println("No Action Required");
		}
		}
		
	}
	
	public void VerifySuccessLogin(String application) throws Exception
	{	
		Assert.assertEquals(Getkeyvalue(application+"_LoggedinUser").contains(Gettext(getwebelement(xml.getlocator("//locators/"+application+"/Userinfo")))),true);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Correct user has been logged in");
		
	}
	
//---------------------------------
//Code for Login	
	public void SiebelLogin(String Application) throws Exception
	{
		openurl(Application);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Navigated to "+Application+" Login Page");
		Thread.sleep(10000);
		SendKeys(getwebelement(xml.getlocator("//locators/"+Application+"/SiebelUsername")),Getkeyvalue(Application+"_Username"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter User Name");
		Thread.sleep(2000);
		SendKeys(getwebelement(xml.getlocator("//locators/"+Application+"/SiebelPassword")),Getkeyvalue(Application+"_Password"));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter Password");
		Thread.sleep(5000);
		Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/SiebelLoginbutton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Login Button");
		
	}
	//Code for Login	
		public void CPQLogin(String Application) throws Exception
		{
			openurl(Application);
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Navigated to "+Application+" Login Page");
			Thread.sleep(10000);
			SendKeys(getwebelement(xml.getlocator("//locators/"+Application+"/SiebelUsername")),Getkeyvalue(Application+"_Username"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter User Name");
			Thread.sleep(2000);
			SendKeys(getwebelement(xml.getlocator("//locators/"+Application+"/SiebelPassword")),Getkeyvalue(Application+"_Password"));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter Password");
			Thread.sleep(5000);
			Clickon(getwebelement(xml.getlocator("//locators/"+Application+"/SiebelLoginbutton")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Login Button");
			
		}


}
