package Testscript;

import org.testng.annotations.Test;

import Driver.DataReader;
import Driver.DriverTestcase;

public class NMTS extends DriverTestcase{
	
	
	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
	public void NumberInquiryForNMTS(Object[][] Data) throws Exception 
	{
		nmtsHelper.get().OpenApplication2();
		Login.get().LoginNMTS("NMTS");
		

		nmtsHelper.get().SearchofNMTSScenario(Data); // running well
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//	nmtsHelper.get().SplitNumbermain(Data);
//		nmtsHelper.get().PortInNMTS(Data);         //running well
//		nmtsHelper.get().ReservetoActivate(Data);	//running well
//		
//		nmtsHelper.get().FreeToActivateOnNMTS(Data);    // running well
		
	}

}
