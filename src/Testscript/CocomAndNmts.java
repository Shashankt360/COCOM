package Testscript;

import org.testng.annotations.Test;

import Driver.DataReader;
import Driver.DriverTestcase;

public class CocomAndNmts extends DriverTestcase{
	
	
	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
	public void FreetoReserve(Object[][] Data) throws Exception 
	{
//		numberHostingHelper.get().OpenApplication();
//		Login.get().Login("NH");
		
	}

}
