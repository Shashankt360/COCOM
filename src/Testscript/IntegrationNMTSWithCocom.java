package Testscript;

import org.testng.annotations.Test;

import Driver.DataReader;

public class IntegrationNMTSWithCocom 
{

	@Test(dataProviderClass=DataReader.class,dataProvider="NumberHosting")
	public void NumberInquiryForNMTS(Object[][] Data) throws Exception 
	{
//		nmtsHelper.get().OpenApplication2();
//		Login.get().LoginNMTS("NMTS");
//		
//
//		nmtsHelper.get().SearchofNMTSScenario(Data); // running well
}
}
