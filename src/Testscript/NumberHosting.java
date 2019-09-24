package Testscript;

import org.testng.annotations.Test;

import Driver.DataReader;
import Driver.DriverTestcase;

public class NumberHosting extends DriverTestcase {
	
	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
	public void FreetoReserve(Object[][] Data) throws Exception 
	{
		
		numberHostingHelper.get().OpenApplication();
		Login.get().Login("NH");
		numberHostingHelper.get().Scenario1(Data);
	}
	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
	public void ActivateReserverNumber(Object[][] Data) throws Exception 
	{
		
		numberHostingHelper.get().OpenApplication();
		Login.get().Login("NH");
		numberHostingHelper.get().Scenario2(Data);
	}
	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
	public void AddressUpdateReserveNumber(Object[][] Data) throws Exception 
	{
		
		numberHostingHelper.get().OpenApplication();
		Login.get().Login("NH");
		numberHostingHelper.get().Scenario3(Data);
	}
	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
	public void DeactivateReserveNumber(Object[][] Data) throws Exception 
	{
		
		numberHostingHelper.get().OpenApplication();
		Login.get().Login("NH");
		numberHostingHelper.get().Scenario4(Data);
	}

}
