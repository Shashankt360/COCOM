package Driver;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.Action;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.hdgf.chunks.ChunkFactory.CommandDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandCodec;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;


import Listeners.TestListener;

import ScriptHelper.LoginHelper;
import ScriptHelper.NumberHostingHelper;



public class DriverTestcase{
	
	
	
public static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new InheritableThreadLocal<>();
//public static final ThreadLocal<RemoteWebDriver> WEB_DRIVER_THREAD_LOCAL = new InheritableThreadLocal<>();
	
	public static final ThreadLocal<LoginHelper> Login= new InheritableThreadLocal<>();
	public static final ThreadLocal<NumberHostingHelper> numberHostingHelper= new InheritableThreadLocal<>();
	
//s	public static final ThreadLocal<CocomHelper> cocomHelper= new InheritableThreadLocal<>();
	public static ThreadLocal<String> QuoteID=new InheritableThreadLocal<>();
	public static TestListener Testlistener;
	//public static CarNorOrderHelper CarNorOrderhelper; 
	public ThreadLocal<String> TestName=new ThreadLocal(); 
	public static SessionId session_id;
	public static ChromeDriver driver;
	public static int  itr;
	@BeforeMethod
	   public void BeforeMethod(Method method,ITestContext ctx ,Object[] data) throws IOException, InterruptedException{
		System.out.println("Size of Data in Before method"+data.length);
		setup();
		//System.out.println("Driver at the time of initiation"+getwebdriver());
		//EventFiringWebDriver eventDriver = new EventFiringWebDriver(getwebdriver());
		//ActionListner handler = new ActionListner();
		// eventDriver.register(handler);
		// eventDriver.get("https://google.com");
		Object[][] st1 = null;
		try 
		
		{
	 	st1=(Object[][]) data[0];
	 	System.out.println("Lenghth of Complete Data provided by DP"+st1.length);
		}
		catch(Exception e)
		{
			st1=new Object[][] {{""}};
		}
		Object[] st = null;
		try 
		
		{
	 	st=(Object[]) st1[0];
	 	System.out.println("Length of First Data provided by DP"+st.length);
		}
		catch(Exception e)
		{
			st=new Object[][] {{""}};
			System.out.println(e.getMessage());
		}
	
	//Log.info("Index is:"+itr+"Length od data is:"+data.length);
	      if(method.getName().equals("FreetoReserve"))
	      {
	   		//DataReader dt=new DataReader();
	   		//Object[][] data=dt.datareader();
		    //Object[] st= (Object[]) data[itr][0];
		    Log.info(st[st.length-2].toString());
		    ctx.setAttribute("testName", st[st.length-2].toString());
	      }
	      if(method.getName().equals("ActivateReserverNumber"))
	      {
	   		//DataReader dt=new DataReader();
	   		//Object[][] data=dt.datareader();
		    //Object[] st= (Object[]) data[itr][0];
		    Log.info(st[st.length-2].toString());
		    ctx.setAttribute("testName", st[st.length-2].toString());
	      }
	      if(method.getName().equals("AddressUpdateReserveNumber"))
	      {
	   		//DataReader dt=new DataReader();
	   		//Object[][] data=dt.datareader();
		    //Object[] st= (Object[]) data[itr][0];
		    Log.info(st[st.length-2].toString());
		    ctx.setAttribute("testName", st[st.length-2].toString());
	      }
	      if(method.getName().equals("DeactivateReserveNumber"))
	      {
	   		//DataReader dt=new DataReader();
	   		//Object[][] data=dt.datareader();
		    //Object[] st= (Object[]) data[itr][0];
		    Log.info(st[st.length-2].toString());
		    ctx.setAttribute("testName", st[st.length-2].toString());
	      }
	      if(method.getName().equals("EndtoEndScenario2"))
	      {
	   		//DataReader dt=new DataReader();
	   		//Object[][] data=dt.datareader();
		    //Object[] st= (Object[]) data[itr][0];
		    Log.info(st[st.length-2].toString());
		    ctx.setAttribute("testName", st[st.length-2].toString());
	      }
	      if(method.getName().equals("EndtoEndScenario3"))
	      {
	   		//DataReader dt=new DataReader();
	   		//Object[][] data=dt.datareader();
		    //Object[] st= (Object[]) data[itr][0];
		    Log.info(st[st.length-2].toString());
		    ctx.setAttribute("testName", st[st.length-2].toString());
	      }
	      if(method.getName().equals("EndtoEndScenario4"))
	      {
	   		//DataReader dt=new DataReader();
	   		//Object[][] data=dt.datareader();
		    //Object[] st= (Object[]) data[itr][0];
		    Log.info(st[st.length-2].toString());
		    ctx.setAttribute("testName", st[st.length-2].toString());
	      }
	      if(method.getName().equals("NumberStatus"))
	      {
	    	  Log.info(st[st.length-2].toString());
			    ctx.setAttribute("testName", st[st.length-2].toString());
	      }
	      if(method.getName().equals("NumberInquiry"))
	      {
	    	  Log.info(st[st.length-2].toString());
			    ctx.setAttribute("testName", st[st.length-2].toString());
	      }
	      
	    
	}


	//@BeforeTest
	public void setup() throws IOException, InterruptedException
	{
		// Open Browser
		WebDriver dr = null;
		PropertyReader pr=new PropertyReader();
		String targatedbrowser=pr.readproperty("browser");
		String url=pr.readproperty("URL");
		Log.info("URL");
		if(!pr.readproperty("Mod").equals("Grid")) {
		if(targatedbrowser.equals("chrome"))
		{ 
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			Map<String, Object> prefs = new HashMap<String, Object>();
			 // Set the notification setting it will override the default setting
			prefs.put("profile.default_content_setting_values.notifications", 2);
			
			
            // Create object of ChromeOption class
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("--start-maximized");
			options.addArguments("disable-infobars");
			options.addArguments("--disable-popup-blocking");
			//options.setExperimentalOption("excludeSwitches", "disable-popup-blocking");
			capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
			//capabilities.setCapability(CapabilityType.l, "none");
			LoggingPreferences logs = new LoggingPreferences(); 
		    logs.enable(LogType.DRIVER, Level.ALL); 
			capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			System.setProperty("webdriver.chrome.driver",".\\lib\\chromedriver.exe");
			dr= new ChromeDriver(capabilities);
			//driver.manage().window().maximize();
			
			
//			dr.get("chrome://settings/content/popups");
//			//dr.findElement(By.xpath(h1[text()='Pop-ups and redirects']);
//			//dr.switchTo().frame("settings");
//			//Thread.sleep(2000);
//			//dr.findElement(By.xpath("//*[@id='popups']")).click();
//			Thread.sleep(6000);
//			Actions builder = new Actions(dr);
//			builder.sendKeys(Keys.TAB).build().perform();
//			builder.sendKeys(Keys.TAB).build().perform();
//			builder.sendKeys(Keys.TAB).build().perform();
//			builder.sendKeys(Keys.ENTER).build().perform();
			//dr.findElement(By.id("control")).click();
			//Thread.sleep(4000);
		}
		else if (targatedbrowser.equals("ie"))
		{
			Log.info("For IE inprogress");
		}
		
		else
		{
			Log.info("For FF inprogress");
		}
		
		//dr.manage().window().maximize();
		WEB_DRIVER_THREAD_LOCAL.set(dr);
		Thread.sleep(2000);
		
		}
		else
		{
			PropertyReader pr2=new PropertyReader();
			System.out.println("First Try");
			System.out.println(pr.readsessionproperty("SessionID").toString());
			System.out.println(pr.readsessionproperty("ExecutorUrl").toString());
			Thread.sleep(2000);
			session_id=new SessionId(pr.readsessionproperty("SessionID").toString());
			URL url2 = new URL(pr.readsessionproperty("ExecutorUrl").toString());
		    dr = createDriverFromSession(session_id, url2);
		   // dr.manage()dr.
		  try {
		    if(dr.getTitle().toString().contains("chrome not reachable"))
		    		{
		    	System.out.println("Chrome needs to initialized");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("--start-maximized");
			options.addArguments("disable-infobars");
			options.addArguments("--disable-popup-blocking");	
			
			capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
			
			System.setProperty("webdriver.chrome.driver",".\\lib\\chromedriver.exe");
			System.setProperty("driver.name","RemoteWebdriver");
			//capabilities.setCapability(CapabilityType.c, true);
			dr= new ChromeDriver(capabilities);
			System.out.println("browser launched");
		    HttpCommandExecutor executor = (HttpCommandExecutor) ((RemoteWebDriver) dr).getCommandExecutor();
		    url2 = executor.getAddressOfRemoteServer();
		    SessionId session_id = ((RemoteWebDriver) dr).getSessionId();
			pr2.updateproprty("SessionID",session_id.toString());
			pr2.updateproprty("ExecutorUrl",url2.toString());
			
		 System.out.println("Session ID is "+session_id);
		 System.out.println("URL is "+url2);
		 dr.get("chrome://settings/content/popups");
			//dr.findElement(By.xpath(h1[text()='Pop-ups and redirects']);
			//dr.switchTo().frame("settings");
			//Thread.sleep(2000);
			//dr.findElement(By.xpath("//*[@id='popups']")).click();
			Thread.sleep(6000);
			Actions builder = new Actions(dr);
			builder.sendKeys(Keys.TAB).build().perform();
			builder.sendKeys(Keys.TAB).build().perform();
			builder.sendKeys(Keys.TAB).build().perform();
			builder.sendKeys(Keys.ENTER).build().perform();
			//dr.findElement(By.id("control")).click();
			//Thread.sleep(4000);
		    		}
		    else {
		    	System.out.println("Nothing to Do here");
		    }
		  }
		  catch(Exception e)
		  {
			  System.out.println("Chrome needs to initialized");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--start-maximized");
				options.addArguments("disable-infobars");
				options.addArguments("--disable-popup-blocking");	
				
				capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
				System.setProperty("webdriver.chrome.driver",".\\lib\\chromedriver.exe");
				System.setProperty("driver.name","RemoteWebdriver");
				dr= new ChromeDriver(capabilities);
				System.out.println("browser launched");
			    HttpCommandExecutor executor = (HttpCommandExecutor) ((RemoteWebDriver) dr).getCommandExecutor();
			    url2 = executor.getAddressOfRemoteServer();
			    SessionId session_id = ((RemoteWebDriver) dr).getSessionId();
				pr2.updateproprty("SessionID",session_id.toString());
				pr2.updateproprty("ExecutorUrl",url2.toString());
				
			 System.out.println("Session ID is "+session_id);
			 System.out.println("URL is "+url2);
			 dr.get("chrome://settings/content/popups");
				//dr.findElement(By.xpath(h1[text()='Pop-ups and redirects']);
				//dr.switchTo().frame("settings");
				//Thread.sleep(2000);
				//dr.findElement(By.xpath("//*[@id='popups']")).click();
				Thread.sleep(6000);
				Actions builder = new Actions(dr);
				builder.sendKeys(Keys.TAB).build().perform();
				builder.sendKeys(Keys.TAB).build().perform();
				builder.sendKeys(Keys.TAB).build().perform();
				builder.sendKeys(Keys.ENTER).build().perform();
				//dr.findElement(By.id("control")).click();
				//Thread.sleep(4000);
		  }
//------------------------------------------------------------------
		    //dr.get("http://Google.com");
		   // dr.manage().window().maximize();
		 
			WEB_DRIVER_THREAD_LOCAL.set(dr);
			Thread.sleep(2000);
		}
		
		LoginHelper LN= new LoginHelper(getwebdriver());
		NumberHostingHelper NHH= new NumberHostingHelper(getwebdriver());
		Login.set(LN);
		
		numberHostingHelper.set(NHH);
	}

	@org.testng.annotations.BeforeSuite
	public void BeforeSuite(){
	itr=0;
	DOMConfigurator.configure("log4j.xml");
	}
	public  String readTesCase(Object[][] Inputdata, int i)
	{
		String Tcname = Inputdata[i][0].toString();
		return Tcname;
	}
	
	public static WebDriver createDriverFromSession(final SessionId sessionId, URL command_executor){
		WebDriver drsession = null;
		CommandExecutor executor = null;
		executor = new HttpCommandExecutor(command_executor) {
			   
		    @Override
		    public Response execute(Command command) throws IOException {
		        Response response = null;
		        System.out.println("In execute method"+command);
		       
		        if (command.getName() == "newSession") {
		            response = new Response();
		            response.setSessionId(sessionId.toString());
		            response.setStatus(0);
		            response.setValue(Collections.<String, String>emptyMap());
		            System.out.println("In new session condition");
		            try {
		            	 System.out.println("In the Try cases");
		                Field commandCodec = null;
		                commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
		                commandCodec.setAccessible(true);
		                commandCodec.set(this, new W3CHttpCommandCodec());
		                
		                Field responseCodec = null;
		                responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
		                responseCodec.setAccessible(true);
		               responseCodec.set(this, new W3CHttpResponseCodec());
		            } catch (NoSuchFieldException e) {
		                e.printStackTrace();
		                System.out.println(e.getMessage().toString());
		            } 
		            catch (IllegalAccessException e) {
		                e.printStackTrace();
		                System.out.println(e.getMessage().toString());
		            }

		        } else {
		        	
		           
		            try {
		            	Thread.sleep(3000);
		            	 System.out.println("In the Try cases for else");
		                Field commandCodec = null;
		                commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
		                commandCodec.setAccessible(true);
		                commandCodec.set(this, new W3CHttpCommandCodec());
		                System.out.println("Curent comand Codec"+commandCodec.getName().toString());

		                Field responseCodec = null;
		                responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
		                responseCodec.setAccessible(true);
		               responseCodec.set(this, new W3CHttpResponseCodec());
		            } catch (NoSuchFieldException | InterruptedException e) {
		                e.printStackTrace();
		                System.out.println(e.getMessage().toString());
		            } 
		            catch (IllegalAccessException e) {
		                e.printStackTrace();
		                System.out.println(e.getMessage().toString());
		            }
		            
		            
		            response = super.execute(command);
		            System.out.println("In Else and executing commad"+command.getName().toLowerCase());
		        }
		        System.out.println("Respoence is"+response.toString());
		        return response;
		    }
		    };
	    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--start-maximized");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-popup-blocking");	
		capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
		capabilities.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, "true");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		//capabilities.setCapability(CommandCodec<T>, true);
		System.setProperty("webdriver.chrome.driver",".\\lib\\chromedriver.exe");
	    drsession=(new RemoteWebDriver(executor, capabilities));
	    System.out.println(drsession.toString());
	    
	    
	    
	    
	    return drsession; 
	    
	}

	
	
	@AfterMethod
	public void Teardown2()
	{
		System.out.println("Cuurent Thread of diriver need to close-"+getwebdriver());
		getwebdriver().quit();
	}
	@AfterTest
	public void Teardown()
	{
		//System.out.println("Cuurent Thread of diriver need to close-"+getwebdriver());
		//getwebdriver().close();
	}
	public WebDriver getwebdriver() {
		WebDriver dr = WEB_DRIVER_THREAD_LOCAL.get();
		return dr;
	}
}
