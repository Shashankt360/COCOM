package Testscript;

import org.testng.annotations.Test;

import Driver.DataReader;
import Driver.DriverTestcase;

public class NumberHosting extends DriverTestcase {
	
	
	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
	public void NumberInquiry(Object[][] Data) throws Exception 
	{
		
		
		numberHostingHelper.get().OpenApplication();
		Login.get().Login("NH");
		numberHostingHelper.get().Search(Data);
		numberHostingHelper.get().PortIn(Data);
		
		
//		numberHostingHelper.get().OpenApplication2();
//		Login.get().LoginNMTS("NMTS");
	//	numberHostingHelper.get().MergerdNumber(Data);
		
//		numberHostingHelper.get().ReservationOrCancelReservation(Data); // running well
//		
//		numberHostingHelper.get().PortInNMTS(Data);         //running well
//		numberHostingHelper.get().ReservetoActivate(Data);	//running well
//		
//		numberHostingHelper.get().FreeToActivateOnNMTS(Data);    // running well
		
		
		
		
		
		
		

		
		
		
		
		
		
		
		
//		numberHostingHelper.get().Reserve(Data);
//		numberHostingHelper.get().Activation(Data);
//		numberHostingHelper.get().DeActivation(Data);
//		numberHostingHelper.get().Addressupdate(Data);
//		numberHostingHelper.get().ReActivation(Data);
		
//		Login.get().Login("NH");
//		
//		for(int i=0; i<Data.length;i++)
//		{
//		System.out.println(readTesCase(Data, i));
//		if(readTesCase(Data, i).contains("Transaction ID") )
//		{
//		numberHostingHelper.get().Scenario(Data);
//	    }
//		if(readTesCase(Data, i).contains("Search"))
//		{
//		System.out.println("In progress");
//		}
		
		
		
		
		
	
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
//	public void NumberStatus(Object[][] Data) throws Exception 
//	{
//		
//		numberHostingHelper.get().OpenApplication();
//		Login.get().Login("NH");
//		numberHostingHelper.get().Scenario0(Data);
//	}
//	
//	
//	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
//	public void FreetoReserve(Object[][] Data) throws Exception 
//	{
//		
//		numberHostingHelper.get().OpenApplication();
//		Login.get().Login("NH");
//		numberHostingHelper.get().Scenario1(Data);
//	}
//	
//	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
//	public void ActivateReserverNumber(Object[][] Data) throws Exception 
//	{
//		
//		numberHostingHelper.get().OpenApplication();
//		Login.get().Login("NH");
//		numberHostingHelper.get().Scenario2(Data);
//	}
//	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
//	public void AddressUpdateReserveNumber(Object[][] Data) throws Exception 
//	{
//		
//		numberHostingHelper.get().OpenApplication();
//		Login.get().Login("NH");
//		numberHostingHelper.get().Scenario3(Data);
//	}
//	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
//	public void DeactivateReserveNumber(Object[][] Data) throws Exception 
//	{
//		
//		numberHostingHelper.get().OpenApplication();
//		Login.get().Login("NH");
//		numberHostingHelper.get().Scenario4(Data);
//	}

}
