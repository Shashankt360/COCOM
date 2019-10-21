package ScriptHelper;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.DocumentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import Driver.DriverHelper;
import Driver.xmlreader;
import Reporter.ExtentTestManager;

public class NumberHostingHelper extends DriverHelper {

	WebElement el;
	xmlreader xml = new xmlreader("src\\Locators\\NumberHosting.xml");

	public NumberHostingHelper(WebDriver dr) {
		super(dr);

	}

	public void OpenApplication() throws Exception {
		openurl("NH");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Opening the Url");

	}

	public String NumberQueryFamily(Object[][] Inputdata) {
		String SearchCreteria = null;
		for (int i = 0; i < Inputdata.length; i++) {
			SearchCreteria = Inputdata[i][0].toString();
		}
		return SearchCreteria;
	}

	public void Search(Object[][] Inputdata) throws InterruptedException, DocumentException, IOException {

		switch (NumberQueryFamily(Inputdata)) {
		case "Transaction ID": {
			NumberQuiryUsingTransactionID(Inputdata);
			break;
		}
		case "Number Range": {
			NumberQuiryUsingNumberRange(Inputdata);
			break;
		}
		case "Customer Reference": {
			NumberQuiryUsingCustomerReferance(Inputdata);
			break;

		}
		case "Number Status": {
			NumberQuiryUsingNumberStatus(Inputdata);
			break;
		}

		default: {
			log("It seems like, Selected Creteria is not valid in Sheet!!! Please update it in sheet!! ");
		}
		}

	}

	public void NumberQuiryUsingNumberStatus(Object[][] Inputdata)
			throws InterruptedException, IOException, DocumentException {

		for (int i = 0; i < Inputdata.length; i++) {
			Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
			Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
			Select(getwebelement(xml.getlocator("//locators/ResellerName")), Inputdata[i][1].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");

			Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][2].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
			implicitwait(20);
			waitandForElementDisplayed(xml.getlocator("//locators/ServiceProfile"));
			Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][3].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
			Thread.sleep(3000);
			Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Number Status");
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number Status");

			switch (Inputdata[i][4].toString()) {

			case "Reserved": // Reserved to Active Scenario
			{

				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), Inputdata[i][4].toString());
				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
				
				Thread.sleep(3000);
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					DownloadExcel("NumberEnquiryReport");
					Thread.sleep(2000);
					if (Inputdata[i][20].toString().contains("WithAddress")) {
						if (Inputdata[i][19].toString().contains("Activated")) {
							ActivateStatus(Inputdata, i);

						} else {
							log("No action performed!!");
							log("In Sheet, Perform Action column does not have data to perform action with Activated!");
						}
					} else if (Inputdata[i][20].toString().contains("WithoutAddress")) {

						log("Start");
						WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
						Select(getwebelement(xml.getlocator("//locators/UserAction")), "Activate");
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
						Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						log("Address is not mandatory for this transaction ID");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						Thread.sleep(4000);
						waitandForElementDisplayed(xml.getlocator("//locators/TransactionResult"));
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionResult")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						Thread.sleep(2000);
						implicitwait(20);
						// Status Query ************************************************************

						StatuQuerybyTransitionId(Transactionresult);
						log("Successfully Updated status");

					}

				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}

				break;
			}

//						Thread.sleep(10000);

			case "Free": // Free to Reserve Scenario
			{

				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Free");
				implicitwait(20);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as Free");

				// --------------------
				//for test
				
				

				if (Inputdata[i][24].toString().equals("Geographical Numbers"))
				{
					
					if((Inputdata[i][2].toString().equals("DENMARK"))||(Inputdata[i][2].toString().equals("PORTUGAL")))
					{
						log("Don't need to click on geographical and nongeographical radio Button");
					}
					else {
						WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton"));
						Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton")));
						implicitwait(20);
					
					}
					Thread.sleep(2000);
					Select(getwebelement(xml.getlocator("//locators/SelectAreaName")), Inputdata[i][5].toString());
					implicitwait(20);
					Select(getwebelement(xml.getlocator("//locators/blockSize")),Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Block Size");
					implicitwait(5);
                    waitandForElementDisplayed(xml.getlocator("//locators/Quantity"));
					Select(getwebelement(xml.getlocator("//locators/Quantity")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Select the Quantity Size");

				}
				
				else if (Inputdata[i][24].toString().equals("Location Independent Numbers")) {
					WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton2"));
					Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton2")));
					Select(getwebelement(xml.getlocator("//locators/blockSize2")),Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Block Size");

					Select(getwebelement(xml.getlocator("//locators/Quantity2")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Select the Quantity Size");
				}
				
				else if (Inputdata[i][24].toString().equals("UK WIDE (Any Services)")) {
					WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton3"));
					Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton3")));
					Select(getwebelement(xml.getlocator("//locators/blockSize2")),Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Block Size");

					Select(getwebelement(xml.getlocator("//locators/Quantity2")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Select the Quantity Size");
				}
				
				else if (Inputdata[i][24].toString().equals("UK WIDE (Public Services)")) {
					WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton4"));
					Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton4")));
					Select(getwebelement(xml.getlocator("//locators/blockSize2")),Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Block Size");

					Select(getwebelement(xml.getlocator("//locators/Quantity2")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Select the Quantity Size");
				} 
				
				else if(Inputdata[i][24].toString().equals("Search by City")) {
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search by city");
					WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton6"));
					Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton6")));
					
					Select(getwebelement(xml.getlocator("//locators/Province")),Inputdata[i][25].toString());
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Province");
					Select(getwebelement(xml.getlocator("//locators/cityandtown")),Inputdata[i][26].toString());
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the City/Town");
					Select(getwebelement(xml.getlocator("//locators/blockSize2")),Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Block Size");

					Select(getwebelement(xml.getlocator("//locators/Quantity2")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Select the Quantity Size");
					
				}
				
				else {
					RedLog("Geographical or Nongeographical button is missing");
				}
				ExtentTestManager.getTest().log(LogStatus.PASS," Step: Select the Geographical or NonGeographical radio button");
				
				
				

				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				validate();
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
				
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					DownloadExcel("NumberEnquiryReport");
					if (Inputdata[i][19].toString().contains("Reserve")) {
						WaitforElementtobeclickable(xml.getlocator("//locators/NumberRangeCheckbox"));
						Clickon(getwebelement(xml.getlocator("//locators/NumberRangeCheckbox")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Range Checkbox");
						Select(getwebelement(xml.getlocator("//locators/Action")), "Reserve");
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Action");
						Clickon(getwebelement(xml.getlocator("//locators/GoButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");

						SendKeys(getwebelement(xml.getlocator("//locators/CustomerReference")),
								Inputdata[i][6].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter Customer Reference number");
						WaitforElementtobeclickable(xml.getlocator("//locators/Submit"));

						Clickon(getwebelement(xml.getlocator("//locators/Submit")));

						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");

						String TransactionIdres = Gettext(getwebelement(xml.getlocator("//locators/TransactionId")));
						TransactionId.set(TransactionIdres);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: The Transaction Id generated is : " + TransactionId);
						Thread.sleep(1000);

						// Status Query ************************************************************

						StatuQuerybyTransitionId(TransactionIdres);


						GreenLog("Numbwer Query status id update Successfully from Free to Reserve");

					} else if (Inputdata[i][20].toString().contains("WithAddress")) {
						if (Inputdata[i][19].toString().contains("Activated")) {
							WaitforElementtobeclickable(xml.getlocator("//locators/NumberRangeCheckbox"));
							
							
							
							Clickon(getwebelement(xml.getlocator("//locators/NumberRangeCheckbox")));
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Click on the Number Range Checkbox");
							
							
							
							Select(getwebelement(xml.getlocator("//locators/Action")), "Activate");
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Action");
							Clickon(getwebelement(xml.getlocator("//locators/GoButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
							SendKeys(getwebelement(xml.getlocator("//locators/CustomerName")),
									Inputdata[i][7].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Customer name Field");
							SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")),
									Inputdata[i][9].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the building number Field");
							SendKeys(getwebelement(xml.getlocator("//locators/city")), Inputdata[i][11].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the City Field");
							SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")),
									Inputdata[i][8].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Building name field");
							SendKeys(getwebelement(xml.getlocator("//locators/Street")), Inputdata[i][10].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Street Field");
							SendKeys(getwebelement(xml.getlocator("//locators/PostCode")), Inputdata[i][12].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Post Code");
							WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
							Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
							implicitwait(10);
							
							
							if(Inputdata[i][21].toString().contains("Port-out"))
							{
					
								Clickon(getwebelement(xml.getlocator("//locators/TransactionId")));
								String winHandleBefore = driver.getWindowHandle();
								
												//		Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
														ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction ID");
							
													
														
														Thread.sleep(5000);
														
														Set<String> handles = driver.getWindowHandles();
														Iterator<String> iterator = handles.iterator();
														String parent = iterator.next();
														String curent = iterator.next();
														System.out.println("Window handel" + curent);
														driver.switchTo().window(curent);
														// Perform the actions on new window
														String LocalAreaCode = null;
														implicitwait(20);
														boolean flag =false;
														do {
															
															Thread.sleep(2000);
														//	TransactionStatus1 = Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
															Pagerefresh();
															Pagerefresh();
													     flag = 	isElementPresent(xml.getlocator("//locators/CompletedTxStatus"));
													    
													    
//															
															Thread.sleep(1000);
															Pagerefresh();
														}
														
														while (flag==false );
														 ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status :completed for Activation number");
										
								String mainNumber= Gettext(
										getwebelement(xml.getlocator("//locators/mainNumberone")));
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: get the main number from Activated number ");
								MainNumber.set(mainNumber);
								String rangestart= Gettext(
										getwebelement(xml.getlocator("//locators/RangeStartone")));
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: get the Range start number from Activated number ");
								StartRange.set(rangestart);
								String rangeEnd= Gettext(
										getwebelement(xml.getlocator("//locators/RangeEndone")));
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: get the Range end number from Activated number ");
								EndRange.set(rangeEnd);
								String postcode= Gettext(
										getwebelement(xml.getlocator("//locators/PostCodeforport-out")));
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: get the PostCode from Activated number ");
								PostCode.set(postcode);
								

							
							driver.switchTo().window(parent);
							
							 Thread.sleep(2000);
							Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Move on to manage porting ");
							Thread.sleep(2000);
							Clickon(getwebelement(xml.getlocator("//locators/RequestPort-out")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Request Port-out");
							
							
							int allData = getwebelementscount(xml.getlocator("//locators/CommonANH"));//
							 
						      System.out.println(allData);
						      
							   for(int k=0; k<=allData; k++)
							     {
								
								   if( k!=0 && k%10==0)
								   {
									   Clickon(getwebelement(xml.getlocator("//locators/NEXT")));
									   Thread.sleep(2000);
								   }
								  
								   Thread.sleep(1000);
								   String data= GetText(getwebelement(xml.getlocator("//locators/ServiceProfileone").replace("index", String.valueOf(k+1))));
								  
								   System.out.println(data);
								   
								    
								   if(data.contains(Inputdata[i][3].toString().trim()) || data==Inputdata[i][3].toString().trim())
								   {
			
									   Thread.sleep(2000);
										try {
											safeJavaScriptClick(getwebelement(xml.getlocator("//locators/ANH")));
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										  ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Service profile "+Inputdata[i][3].toString().trim());
									
								   Thread.sleep(2000);
									   break;
								   }
							     }   
							   
			
							Select(getwebelement(xml.getlocator("//locators/LocalAreaCodeonePortout")), Inputdata[i][5].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: gSelect the Local Area code");
							
							SendKeys(getwebelement(xml.getlocator("//locators/mainNumberportout")), MainNumber.get());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the main number for port-out number ");
							SendKeys(getwebelement(xml.getlocator("//locators/Rangestartport-out")), StartRange.get());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Range Start number for port-out");
							SendKeys(getwebelement(xml.getlocator("//locators/RangeEndport-out")), EndRange.get());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Range End number for port-out");
						
							implicitwait(20);
							Thread.sleep(2000);
							javaexecutotSendKeys(Inputdata[i][22].toString(), getwebelement(xml.getlocator("//locators/portoutdate")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Port-out date from Calender ");
							SendKeys(getwebelement(xml.getlocator("//locators/OperatorName-Portout")), Inputdata[i][23].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Operator Name ");
							SendKeys(getwebelement(xml.getlocator("//locators/Postcode-portout")), postcode);
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the PostCode for port-out");
							implicitwait(20);
							Thread.sleep(3000);
							Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step:Click on submit button");	
							Thread.sleep(1000);
							waitandForElementDisplayed(xml.getlocator("//locators/TransactionResult"));							
						    String portoutTX = GetText(getwebelement(xml.getlocator("//locators/TransactionResult")));
						    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: getting the Transaction ID");
						    log(portoutTX);
							TransactionIdForPortout.set(portoutTX);
//			
							WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequest")));
							
							Clickon(getwebelement(xml.getlocator("//locators/updateportinRequest")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on For update Portin Request");
							waitandForElementDisplayed(xml.getlocator("//locators/statusQueryTransactionID"));
							log(TransactionIdForPortout.get().toString().trim().toLowerCase());
							SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),TransactionIdForPortout.get().toString().trim().toLowerCase() );
				                Thread.sleep(5000);
				                System.out.println(TransactionIdForPortout.get());
							WaitforElementtobeclickable((xml.getlocator("//locators/StatusQuerySearchbutton")));
							Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Search button ");
							
							Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
							
							Thread.sleep(2000);
							WaitforElementtobeclickable((xml.getlocator("//locators/portout-Go")));
							Clickon(getwebelement(xml.getlocator("//locators/portout-Go")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Go button ");
							waitandForElementDisplayed(xml.getlocator("//locators/transactionType-portout"));
							Select(getwebelement(xml.getlocator("//locators/transactionType-portout")), Inputdata[i][24].toString());
						    ExtentTestManager.getTest().log(LogStatus.PASS, " Step: the Tranasction Type ");
						    Thread.sleep(1000);
							waitandForElementDisplayed(xml.getlocator("//locators/TransactionID-forPortout"));
							log(TransactionIdForPortout.get().toString().trim());
							SendKeys(getwebelement(xml.getlocator("//locators/TransactionID-forPortout")),TransactionIdForPortout.get().toString().trim().toLowerCase());
							waitandForElementDisplayed(xml.getlocator("//locators/changeType-portout"));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Transaction ID for port-out Request");
							Select(getwebelement(xml.getlocator("//locators/changeType-portout")), Inputdata[i][25].toString());
					
							Thread.sleep(1000);
							waitandForElementDisplayed(xml.getlocator("//locators/NewStatus-portout"));
							Select(getwebelement(xml.getlocator("//locators/NewStatus-portout")), Inputdata[i][26].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Status from dropdown");
							WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
							Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Submit button");
							implicitwait(20);
							
							
							Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
							String winHandleBefore1 = driver.getWindowHandle();
			
									ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Transaction Id to validate the Trasaction status");
									
									Thread.sleep(5000);
									
									Set<String> handles0 = driver.getWindowHandles();
									Iterator<String> iterator0 = handles0.iterator();
									String parent0 = iterator0.next();
									String curent0 = iterator0.next();
									System.out.println("Window handel" + curent0);
									driver.switchTo().window(curent0);
									// Perform the actions on new window
									String LocalAreaCode0 = null;
									implicitwait(20);
									boolean flag0 =false;
									do {
										
										Thread.sleep(2000);
									//	TransactionStatus1 = Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
										Pagerefresh();
										Pagerefresh();
								     flag0 = 	isElementPresent(xml.getlocator("//locators/CompletedTxStatus"));
								     ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Checking the Transaction Status is Completed or not");
									
										Thread.sleep(1000);
										Pagerefresh();
									}
									
									while (flag0==false );
									String TransactionStatus1 = Gettext(getwebelement(xml.getlocator("//locators/CompletedTxStatus")));	
									TransactionPortoutstatus.set(TransactionStatus1);
									
									Assert.assertTrue(TransactionPortoutstatus.get().contains("Completed"));	
									ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Assertion Status has been passed for port -out");
									ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status has been Completed for port-out");
						
							}
							
							else {
								
							
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Port-out has been completed");
						
							}
					}

						log("Free to Activated with Address completed.");
					} 
//							String Transactionresult = Gettext(
//									getwebelement(xml.getlocator("//locators/TransactionId")));
//							TransactionId.set(Transactionresult);
//							ExtentTestManager.getTest().log(LogStatus.PASS,
//									" Step: Transaction Id is : " + TransactionId.get());
//							implicitwait(10);
//							// Store the current window handle
//							StatuQuerybyTransitionId(Transactionresult);
////							Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
////							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction ID");
////							System.out.println(Transactionresult);
//						}{{{{{{{{{>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
//						log("Free to Activated with Address completed.");
					} else if (Inputdata[i][20].toString().contains("WithoutAddress")) {

						log("Start");
						Thread.sleep(2000);
						if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
							GreenLog("Data are displaying with serach creteria!!");
						
			
						WaitforElementtobeclickable(xml.getlocator("//locators/NumberRangeCheckbox"));
						Clickon(getwebelement(xml.getlocator("//locators/NumberRangeCheckbox")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Range Checkbox");
						Select(getwebelement(xml.getlocator("//locators/Action")), "Activate");
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Action");
						Clickon(getwebelement(xml.getlocator("//locators/GoButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						Thread.sleep(5000);
						// editing
						waitandForElementDisplayed(xml.getlocator("//locators/TransactionIdwithoutAddress"));
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionIdwithoutAddress")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						Thread.sleep(3000);
						implicitwait(20);
						// Status Query ************************************************************

						StatuQuerybyTransitionId(Transactionresult);
						log("Free to Activated without Reserve Scenario competed.");
					
					}
					else if (Inputdata[i][19].toString() == null)
					{
						log("In Sheet, Perform Action column does not have data to perform action with Free Number Query!");
					} 
					else 
					{
						log("In Sheet, Perform Action column  having data to perform action but not correct spelled with Free Number Query!");
					}	
					}
				else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
				
	                  + Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString() + 
	                    " And Block size "+Inputdata[i][22].toString() +" And Quantity " +Inputdata[i][23].toString());	
				}
			}
				break;
			
			case "Activated": {
				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Activated");
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as Activated");
				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
				
				Thread.sleep(5000);

				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {

					GreenLog("Data are displaying with serach creteria!!");
					DownloadExcel("NumberEnquiryReport");
					if (Inputdata[i][19].toString().contains("UpdateAddress")) {
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
						Thread.sleep(2000);
						Select(getwebelement(xml.getlocator("//locators/UserAction")), Inputdata[i][16].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
						Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						Thread.sleep(1000);
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")), Inputdata[i][8].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Building name field");
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")),
								Inputdata[i][9].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the building number Field");
						SendKeys(getwebelement(xml.getlocator("//locators/NewStreet")), Inputdata[i][10].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the New Street Field");
						SendKeys(getwebelement(xml.getlocator("//locators/NewCity")), Inputdata[i][11].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the  New City Field");
						SendKeys(getwebelement(xml.getlocator("//locators/NewPostCode")), Inputdata[i][12].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Post Code");
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the submit button");
						Thread.sleep(4000);
						
			
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionResult")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						
						// Thread.sleep(3000);
						implicitwait(20);
						// Status Query ************************************************************

						StatuQuerybyTransitionId(Transactionresult);

					} else if (Inputdata[i][19].toString().contains("Deactivate")) {
						WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Radio button");
						Select(getwebelement(xml.getlocator("//locators/UserAction")), "Deactivate");
						try
						{
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action");
						WaitforElementtobeclickable(xml.getlocator("//locators/UserGo"));
						Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						Thread.sleep(3000);
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionResult")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						// Thread.sleep(3000);
						implicitwait(20);
						}
						catch(StaleElementReferenceException e)
						{
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action");
							WaitforElementtobeclickable(xml.getlocator("//locators/UserGo"));
							Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
							
							WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
							Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
							Thread.sleep(3000);
							String Transactionresult = Gettext(
									getwebelement(xml.getlocator("//locators/TransactionResult")));
							TransactionId.set(Transactionresult);
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Transaction Id is : " + TransactionId.get());
						}
						// Status Query ************************************************************

						StatuQuerybyTransitionId(TransactionId.get());

					} else {
						log("Search Scenario with activate scenario has been successfull performed");

					}

				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}

				break;
			}
			case "Quarantined": {

				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Quarantined");
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as Quarantined");
				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
				
				Thread.sleep(5000);
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					DownloadExcel("NumberEnquiryReport");
					if (Inputdata[i][19].toString().contains("Reactivate")) {
						WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Radio button");
						Select(getwebelement(xml.getlocator("//locators/UserAction")), "Reactivate");
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action");
						WaitforElementtobeclickable(xml.getlocator("//locators/UserGo"));
						Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						Thread.sleep(3000);
						//String Transactionresult=null;
						try {
							String	 Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionResult")));
						TransactionId.set(Transactionresult);
						StatuQuerybyTransitionId(Transactionresult);
						}
						
						catch(StaleElementReferenceException e)
						{
							
						}
					
						
						implicitwait(20);
						// Status Query ************************************************************

						
					}

				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}

				break;
			}
			case "Port In(Allocated)": {
				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Port In(Allocated)");
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Select the Number Status as Port In(Allocated)");
				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
				Thread.sleep(2000);
				
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					DownloadExcel("NumberEnquiryReport");
					Thread.sleep(5000l);
					String Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/TransactionIdLink")));
					TransactionId.set(Transactionresult);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Transaction Id is : " + TransactionId.get());
					Thread.sleep(1000);
					implicitwait(20);
					// Status Query ************************************************************

					StatuQuerybyTransitionId(Transactionresult);

				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}
				break;
			}
			case "Port In(Activated)": {

				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Port In(Activated)");
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Select the Number Status as Port In(Activated)");
				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
				
				Thread.sleep(5000);

				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					DownloadExcel("NumberEnquiryReport");
					if (Inputdata[i][19].toString().contains("UpdateAddress")) {
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
						Select(getwebelement(xml.getlocator("//locators/UserAction2")), Inputdata[i][16].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
						Clickon(getwebelement(xml.getlocator("//locators/UserGo2")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						Thread.sleep(5000);
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")), Inputdata[i][8].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Building name field");
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")),
								Inputdata[i][9].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the building number Field");
						SendKeys(getwebelement(xml.getlocator("//locators/NewStreet")), Inputdata[i][10].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the New Street Field");
						SendKeys(getwebelement(xml.getlocator("//locators/NewCity")), Inputdata[i][11].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the  New City Field");
						SendKeys(getwebelement(xml.getlocator("//locators/NewPostCode")), Inputdata[i][12].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Post Code");
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the submit button");
						Thread.sleep(1000);
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionResult")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						Thread.sleep(1000);



						StatuQuerybyTransitionId(Transactionresult);
					}
//					}
					else if (Inputdata[i][19].toString().contains("Deactivate")) {
						WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Radio button");
						Select(getwebelement(xml.getlocator("//locators/UserAction2")), "Deactivate");
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action");
						WaitforElementtobeclickable(xml.getlocator("//locators/UserGo2"));
						Clickon(getwebelement(xml.getlocator("//locators/UserGo2")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						Thread.sleep(3000);
						String Transactionresult=null;;
						try
						{
							
						
						 Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionResult")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						}
						catch(StaleElementReferenceException e)
						{
						implicitwait(20);
						// Status Query ************************************************************
						}
						StatuQuerybyTransitionId(Transactionresult);
					} else {
						log("Search Scenario with port-in activated has been successfully performed");
					}

				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}
				break;
			}
			case "Port Out": {
				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Port Out");
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as Port Out");
				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
				
				Thread.sleep(2000);
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					DownloadExcel("NumberEnquiryReport");
//					Thread.sleep(5000);
//					String Transactionresult = Gettext(
//							getwebelement(xml.getlocator("//locators/TransactionIdLink")));
//					TransactionId.set(Transactionresult);
//					ExtentTestManager.getTest().log(LogStatus.PASS,
//							" Step: Transaction Id is : " + TransactionId.get());
//					//Thread.sleep(3000);
//					implicitwait(20);
//					// Status Query	************************************************************
//					
//					StatuQuerybyTransitionId(Transactionresult);
				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}
				break;
			}
			case "Returned": {

				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Returned");
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as Returned");
				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
				
				Thread.sleep(1000);
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					DownloadExcel("NumberEnquiryReport");
					try{

					String Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/TransactionResult")));
					TransactionId.set(Transactionresult);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Transaction Id is : " + TransactionId.get());
					// Thread.sleep(3000);
					implicitwait(20);
					// Status Query ************************************************************

					StatuQuerybyTransitionId(Transactionresult);
					}
					catch(StaleElementReferenceException e)
					{
						
					}

				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}
				break;
			}
			case "All": {
				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "All");
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as All");
				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
				
				Thread.sleep(1000);
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					DownloadExcel("NumberEnquiryReport");
					
//					//-------------------------------
//					//ArrayList<String>  ar = new ArrayList<String>();
//					int size = getwebelementscount(xml.getlocator("locators/ElementAll"));
//					//System.out.println(size);
//					java.util.List<WebElement> all = getwebelements(xml.getlocator("locators/ElementAll"));
//					
//					for (int j = 0; j < size; j++) {
//					
//					String transid =	all.get(j).getText();
//					//ar.add(transid)	;
////				
//					//------------------------------- 35
//					
//				if(isElementPresent(xml.getlocator("locators/Getcheckbox").replace("Transactionid", Inputdata[i][35].toString())))
//				{
//					
//					javascriptexecutor(getwebelement(xml.getlocator("locators/Getcheckbox").replace("Transactionid", Inputdata[i][35].toString())));
//				     implicitwait(20);
//					Clickon(getwebelement(xml.getlocator("locators/Getcheckbox").replace("Transactionid", Inputdata[i][35].toString())));
//						Thread.sleep(5000);
//						System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//						break;
//				}
//					
//					
//		
//				}
					
						 
					
				} 
				else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}
				break;
			}

			// Deafault case  replace("index"),transid
			default: {
				RedLog("In Excel data, It seems like Number Status data is misspelled or not correct!! Data is "
						+ Inputdata[i][4].toString());
			}
			}
System.out.println("//////////////////////////// * Step first has been finished *  ////////////////////////////////////");
		}

	}

	
	public void NumberQuiryUsingTransactionID(Object[][] Inputdata)
			throws InterruptedException, DocumentException, IOException {
		for (int i = 0; i < Inputdata.length; i++) {
			Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
			Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
			Thread.sleep(2000);
//		System.out.println(Inputdata[i][0].toString());
//     WaitforElementtobeclickable(xml.getlocator("//locators/ResellerName"));
			Select(getwebelement(xml.getlocator("//locators/ResellerName")), Inputdata[i][1].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
			Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][2].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
			implicitwait(20);
			Thread.sleep(1000);
			WaitforElementtobeclickable(xml.getlocator("//locators/ServiceProfile"));
			Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][3].toString().trim());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
			Thread.sleep(2000);
			// System.out.println(Inputdata[i][18].toString());
			Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Transaction ID");
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number Status");
			// System.out.println(Inputdata[i][18].toString());
			Thread.sleep(1000);
			SendKeys(getwebelement(xml.getlocator("locators/InputTransactionId")), Inputdata[i][18].toString());
			implicitwait(20);
			Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
			validate();
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step:click the Search button");
			 
			Thread.sleep(1500);
			if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
				
				
				GreenLog("Data are displaying with serach creteria!!");
				 DownloadExcel("NumberEnquiryReport");
			//--------------------
			
			
				 
				 
				 
				 
				 
				 
				 
				Thread.sleep(1000);
				String Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/TransactionIdLink")));
				TransactionId.set(Transactionresult);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : " + TransactionId.get());
				// Thread.sleep(3000);
				implicitwait(20);
				// Status Query ************************************************************

				StatuQuerybyTransitionId(Transactionresult);
			} else {
				RedLog("System does not have search result with Search Transaction Id with country!! Transection id is "
						+ Inputdata[i][18].toString() + " And Country is " + Inputdata[i][2].toString());
			}
			System.out.println("//////////////////////////// * Step first has been finished *  ////////////////////////////////////");
		}
	}

	public void NumberQuiryUsingNumberRange(Object[][] Inputdata)
			throws InterruptedException, DocumentException, IOException {
		for (int i = 0; i < Inputdata.length; i++) {
			Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
			Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
			Select(getwebelement(xml.getlocator("//locators/ResellerName")), Inputdata[i][1].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");

			Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][2].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
			Thread.sleep(2000l);
			Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][3].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
			Thread.sleep(3000);

			Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Number Range");
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number Status");

			Select(getwebelement(xml.getlocator("//locators/LocalAreaCode")), Inputdata[i][5].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Area Name");
			SendKeys(getwebelement(xml.getlocator("//locators/mainNumber")), Inputdata[i][13].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the main number");
			SendKeys(getwebelement(xml.getlocator("//locators/RangeStart")), Inputdata[i][14].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the RangeStart");
			SendKeys(getwebelement(xml.getlocator("//locators/RangeEnd")), Inputdata[i][15].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the RangeEnd");
			Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step:click the Search button");
			DownloadExcel("NumberEnquiryReport");
			if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
				GreenLog("Data are displaying with serach creteria!!");
				Thread.sleep(1000);
				String Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/TransactionIdLink")));
				TransactionId.set(Transactionresult);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : " + TransactionId.get());
				// Thread.sleep(3000);
				implicitwait(20);
				// Status Query ************************************************************

				StatuQuerybyTransitionId(Transactionresult);

			} else {
				RedLog("System does not have search result with SearchNumber Range with country!! Number Range is "
						+ Inputdata[i][14].toString() + " to " + Inputdata[i][15].toString() + " And Country is "
						+ Inputdata[i][2].toString());
			}

		}
	}

	public void NumberQuiryUsingCustomerReferance(Object[][] Inputdata)
			throws InterruptedException, DocumentException, IOException {
		for (int i = 0; i < Inputdata.length; i++) {
			Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
			Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
			Select(getwebelement(xml.getlocator("//locators/ResellerName")), Inputdata[i][1].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");

			Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][2].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
			implicitwait(20);
			Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][3].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
			Thread.sleep(3000);

			Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Customer Reference");
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Customer Reference");
			// need here to paste customer reference
			SendKeys(getwebelement(xml.getlocator("//locators/CustomerReferenceNumber")),
					Inputdata[i][6].toString().trim());
			// Select(getwebelement(xml.getlocator("//locators/CustomerReferenceNumber")),
			// Inputdata[i][6].toString().trim());
			implicitwait(10);
			Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step:click the Search button");
			
			Thread.sleep(1500);
			if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
				GreenLog("Data are displaying with serach creteria!!");
				DownloadExcel("NumberEnquiryReport");
				String Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/TransactionIdLink")));
				TransactionId.set(Transactionresult);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : " + TransactionId.get());
				// Thread.sleep(3000);
				implicitwait(20);
				// Status Query ************************************************************

				StatuQuerybyTransitionId(Transactionresult);
			} else {
				RedLog("System does not have search result with Customer Reference  with country!! Customer Reference is "
						+ Inputdata[i][6].toString().trim() + " And Country is " + Inputdata[i][2].toString());
			}
			System.out.println("//////////////////////////// * Step first has been finished *  ////////////////////////////////////");
		}
	}

	public void ActivateStatus(Object[][] Inputdata, int i)
			throws InterruptedException, DocumentException, IOException {
		log("Start");
		
		
		Thread.sleep(2000);
		int size = getwebelementscount(xml.getlocator("locators/ReserveElementall"));
		//System.out.println(size);
		java.util.List<WebElement> all = getwebelements(xml.getlocator("locators/ReserveElementall"));
		
		for (int j = 0; j < size; j++) {
		
		String transid =	all.get(j).getText();
		System.out.println(transid);
		
//	
		//------------------------------- 35
		
	if(isElementPresent(xml.getlocator("locators/ReserveElement").replace("Transactionid", Inputdata[i][35].toString())))
	{
		WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
		javascriptexecutor(getwebelement(xml.getlocator("locators/ReserveElement").replace("Transactionid", Inputdata[i][35].toString())));
	     implicitwait(20);
	     ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
		Clickon(getwebelement(xml.getlocator("locators/ReserveElement").replace("Transactionid", Inputdata[i][35].toString())));
		implicitwait(20);
		Select(getwebelement(xml.getlocator("//locators/SelectAction").replace("Transactionid", Inputdata[i][35].toString())), "Activate");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
		//SendKeys1(getwebelement(xml.getlocator("//locators/ActivateReserve").replace("Activate", Inputdata[i][16].toString())));
		implicitwait(20);	
		WaitforElementtobeclickable(xml.getlocator("//locators/Gobtnmain").replace("Transactionid", Inputdata[i][35].toString()));
		Clickon(getwebelement(xml.getlocator("//locators/Gobtnmain").replace("Transactionid", Inputdata[i][35].toString())));	
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
//		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
		
			Thread.sleep(5000);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			break;
	}
		
		

	}

//		WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
//		Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
//		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
		//Select(getwebelement(xml.getlocator("//locators/UserAction")), "Activate");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
		Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
		SendKeys(getwebelement(xml.getlocator("//locators/CustomerName")), Inputdata[i][7].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Customer name Field");
		SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")), Inputdata[i][9].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the building number Field");
		SendKeys(getwebelement(xml.getlocator("//locators/city")), Inputdata[i][11].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the City Field");
		SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")), Inputdata[i][8].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Building name field");
		SendKeys(getwebelement(xml.getlocator("//locators/Street")), Inputdata[i][10].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Street Field");
		SendKeys(getwebelement(xml.getlocator("//locators/PostCode")), Inputdata[i][12].toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Fill the Post Code");
		WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
		Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
		Thread.sleep(1000);

		String Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/TransactionResult")));
		TransactionId.set(Transactionresult);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : " + TransactionId.get());
		// Thread.sleep(10000);
		// Store the current window handle

//		Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
//		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction ID");
//		System.out.println(Transactionresult);
		System.out.println(Transactionresult);
		StatuQuerybyTransitionId(Transactionresult);

	}
	public void validate() throws InterruptedException, DocumentException
	{
		if(getwebelement(xml.getlocator("//locators/mainError")).isDisplayed())
		{
			String errormsg=getwebelement(xml.getlocator("//locators/mainError")).getText();
			//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: errormsg is : " + errormsg);
		}
	}
	
	
	
	
	
	
	

	public void StatuQuerybyTransitionId(String TransactionID)
			throws InterruptedException, DocumentException, IOException {

		Moveon(getwebelement(xml.getlocator("//locators/statusQuery")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Move to Status Query ");
		implicitwait(20);
		WaitforElementtobeclickable(xml.getlocator("//locators/NumberHostingTransaction"));
		Clickon(getwebelement(xml.getlocator("//locators/NumberHostingTransaction")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Number Hosting Transaction");
		waitandForElementDisplayed(xml.getlocator("//locators/statusQueryTransactionID"));
		SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")), TransactionID);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Transaction ID");
		//WaitforElementtobeclickable(xml.getlocator("//locators/StatusQuerySearchbutton"));
		Thread.sleep(10000);
		Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on submit button");
//		 WaitforElementtobeclickable(xml.getlocator("//locators/StatusQuerySearchbutton"));
//		 Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));

		
		Thread.sleep(5000);
	
//>>>>>>>>>>>>GRID DATA CONCEPT 
		if (isElementPresent(xml.getlocator("//locators/GriddataforTX")))
		{
		
		String transType = Gettext(getwebelement(xml.getlocator("//locators/TransactionType")));

		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : " + transType);
		System.out.println("*****************************************************************************" + transType);
		implicitwait(20);

		String transStatus = Gettext(getwebelement(xml.getlocator("//locators/StatusQueryTransactionStatus")));
		System.out.println("*****************************************************************************" + transStatus);
				

		if (transStatus.contains("Completed"))
		{
			GreenLog("Status Query has been passed with Transaction Status Completed");

			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status is :Completed");

		} 
		else if (transStatus.contains("In Progress")) 
		{
			GreenLog("Status Query has been passed with Transaction Status : In Progress");
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status is : In Progress");
		} 
		else if (transStatus.contains("Failed with Error")) 
		{

			GreenLog("it seems like there is Transaction Status is: Failed with Error");
		}
		else if (transStatus.contains("Firm order commitment")) 
		{

			GreenLog("it seems like there is Transaction Status Error: Failed with Error");
		}
		

		else {
			RedLog("it seems like there is no any Transaction Status value which contained Completed or In Process or Firm order commitment");

		}
		
		implicitwait(20);
		String Norecord = Gettext(getwebelement(xml.getlocator("//locators/NoRecordFound")));
		
		if (Norecord.contains("No records found!")) {
			implicitwait(20);
			Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
			Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
		} else if(Norecord.contains("No records found!"))
		{
			WaitforElementtobeclickable(xml.getlocator("//locators/StatusQuerySearchbutton"));
			Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
		}
		else if(Norecord.contains("No records found!"))
		{
			WaitforElementtobeclickable(xml.getlocator("//locators/StatusQuerySearchbutton"));
			Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Sorry No record found for this Transaction ID");
		}
		}
		else{
			RedLog("System does not have search result with Transaction ID - check your Transaction ID and field Details ");
		}					
		

	}

	               
		
	 
	 public void DownloadExcel(String Filaname) throws InterruptedException, DocumentException
	 {
		 String downloadPath=null;
		try {
			downloadPath = GetExcelpath("Excel");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		   Assert.assertTrue(isFileDownloaded(downloadPath, Filaname), "Failed to download Expected document");
			 Clickon(getwebelement(xml.getlocator("//locators/DownloadExcel")));
			 GreenLog("Step: Excel has been downloded : Successfully");
			 //ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Excel has been downloded : Successfully");
		 
	 }
	 public void PortIn(Object[][] Inputdata) throws Exception {
		 
		 
			for (int i = 0; i < Inputdata.length; i++) {

				if(Inputdata[i][0].toString().trim().equals("Port-In"))
				 {
				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));
				log("click on managing porting");
				Thread.sleep(1000);
				// safeJavaScriptClick(getwebelement(xml.getlocator("//locators/RequestPortin")));
				Clickon(getwebelement(xml.getlocator("//locators/RequestPortin")));
				implicitwait(10);
				log("click on request portin");
				int allData = getwebelementscount(xml.getlocator("//locators/CommonANH"));//

				System.out.println(allData);
				for (int k = 0; k <= allData; k++) {
					if (k != 0 && k % 10 == 0) {
						Clickon(getwebelement(xml.getlocator("//locators/NEXT")));
						log("click on ANH and next button");
					}
					Thread.sleep(1000);
					String data = GetText(getwebelement(
							xml.getlocator("//locators/ServiceProfileone").replace("index", String.valueOf(k + 1))));
					System.out.println(data);
					Thread.sleep(1000);
					if (data.contains(Inputdata[i][3].toString().trim()) || data == Inputdata[i][3].toString().trim()) {
						Thread.sleep(2000);
						safeJavaScriptClick(getwebelement(xml.getlocator("//locators/ANH")));
						log("click on ANH");
						// Clickon(getwebelement(xml.getlocator("//locators/ANH")));
						Thread.sleep(2000);
						break;
					}
				}
				SendKeys(getwebelement(xml.getlocator("//locators/customernamepost")), Inputdata[i][7].toString().trim());
				implicitwait(5);

				SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber2")), Inputdata[i][9].toString().trim());
				implicitwait(5);

				SendKeys(getwebelement(xml.getlocator("//locators/Street2")), Inputdata[i][10].toString().trim());
				implicitwait(5);

				SendKeys(getwebelement(xml.getlocator("//locators/city2")), Inputdata[i][11].toString().trim());
				implicitwait(5);

				SendKeys(getwebelement(xml.getlocator("//locators/postcode2")), Inputdata[i][12].toString());
				implicitwait(5);
				SendKeys(getwebelement(xml.getlocator("//locators/MainBillingNumber")), Inputdata[i][32].toString());
				implicitwait(5);
				SendKeys(getwebelement(xml.getlocator("//locators/localareacode2")), Inputdata[i][5].toString());
				implicitwait(5);
				SendKeys(getwebelement(xml.getlocator("//locators/MainNumber")), Inputdata[i][13].toString());
				implicitwait(5);
				SendKeys(getwebelement(xml.getlocator("//locators/RangeStart2")), Inputdata[i][14].toString().trim());
				implicitwait(5);
				SendKeys(getwebelement(xml.getlocator("//locators/RangeEnd2")), Inputdata[i][15].toString().trim());
				implicitwait(5);
				SendKeys(getwebelement(xml.getlocator("//locators/CurrentProvider")), Inputdata[i][33].toString().trim());
				implicitwait(5);
				// SendKeys(getwebelement(xml.getlocator("//locators/portindate")),Inputdata[i][29].toString());
				javaexecutotSendKeys(Inputdata[i][34].toString(), getwebelement(xml.getlocator("//locators/portindate")));
				implicitwait(5);
				Clickon(getwebelement(xml.getlocator("//locators/searchportin")));
				Thread.sleep(2000);
				implicitwait(5);
				log("Fill all the filled and click on the search button");
				Thread.sleep(2000);
				String Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/portinTransactionlink")));
				TransactionId.set(Transactionresult);
				log("Transaction id received");
				WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequest")));
				Clickon(getwebelement(xml.getlocator("//locators/updateportinRequest")));

				// --------------------------------

				log("click on the update part in request");
				waitandForElementDisplayed(xml.getlocator("//locators/statusQueryTransactionID"));
				log(TransactionId.get().toString().trim().toLowerCase());
				SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
						TransactionId.get().toString().trim().toLowerCase());
				Thread.sleep(3000);
				System.out.println(TransactionId.get());
				log("send the transaction ID");

				// -----------------
				Thread.sleep(10000);
				WaitforElementtobeclickable((xml.getlocator("//locators/StatusQuerySearchbutton")));
				Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
				Thread.sleep(3000);
				log("click on the search Button");
				String CurrentStatus1 = Gettext(getwebelement(xml.getlocator("//locators/currentStatusPortin")));
				CurrentStatus.set(CurrentStatus1);

				log("CurrentStatus received");
				log("check the current status");
				if (CurrentStatus.get().contains("Validation In Progress")) {
					WaitforElementtobeclickable((xml.getlocator("//locators/checkbox")));
					Clickon(getwebelement(xml.getlocator("//locators/checkbox")));
					log("click on the checkbox");
					implicitwait(5);
					SendKeys(getwebelement(xml.getlocator("//locators/ActionPortin")), "Submitted to operator");
					log("select the valid action portin");
					Thread.sleep(5000);

					WaitforElementtobeclickable((xml.getlocator("//locators/GoButon")));
					Clickon(getwebelement(xml.getlocator("//locators/GoButon")));
					log("click on the go button");
				} else {
					RedLog("CurrentStatus is incorrect");
					Assert.assertTrue(CurrentStatus.get().contains("Validation In Progress"),"Current status is not valid");
				}

				WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
				Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
				log("click on the submit button");
				Thread.sleep(2000);
				AcceptJavaScriptMethod();
				log("submitted succesfully");

				Thread.sleep(10000);
				boolean flag = false;

				do {

					// String ChildCurrentStatus =
					// Gettext(getwebelement(xml.getlocator("//locators/ChildStatus")));
					flag = isElementPresent(xml.getlocator("//locators/ChildStatus"));
					// ChildCurrentStat.set(ChildCurrentStatus);

					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: ChildCurrentStatus is : " + ChildCurrentStat.get());
					log("if child Current Status shown than go to the next step button otherwise click on the refresh button");
					WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
					Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
					
				} while (flag == false);
				log("childcurrent status id successfully submited");
				Thread.sleep(1000);
				String ChildCurrentStatus = Gettext(getwebelement(xml.getlocator("//locators/ChildStatus")));
				ChildCurrentStat.set(ChildCurrentStatus);
				log("get the child status");
				Assert.assertEquals(ChildCurrentStat.get(), "Completed", "Child status is " + ChildCurrentStat.get());

				String ParentCurrentStatus = Gettext(getwebelement(xml.getlocator("//locators/ParentStatus")));
				ParentCurrentStat.set(ParentCurrentStatus);

				log("ParentCurrentStatus shown");
//				//--------------------------------------------------------------------

				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));

				log("now again check the parent status");
				Thread.sleep(1000);
				// Clickon(getwebelement(xml.getlocator("//locators/ManagePorting")));

				// Thread.sleep(1000);

				WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequesthover")));
				Clickon(getwebelement(xml.getlocator("//locators/updateportinRequesthover")));

				log("click on the update port in request");
				// -----------
				SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
						ParentCurrentStat.get().toString().trim().toLowerCase());
				Thread.sleep(5000);
				log("send the transaction ID");
				// -------------

				WaitforElementtobeclickable((xml.getlocator("//locators/StatusQuerySearchbutton")));
				Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
				log("click on the search button");
				Thread.sleep(3000);
				String CurrentStatus2 = Gettext(getwebelement(xml.getlocator("//locators/currentStatusPortin")));
				CurrentStatus.set(CurrentStatus2);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: CurrentStatus is : " + CurrentStatus.get());

				log("CurrentStatus received");
				log("check the current status");
				if (CurrentStatus.get().contains("Submitted to operator")) {
					WaitforElementtobeclickable((xml.getlocator("//locators/checkbox")));
					Clickon(getwebelement(xml.getlocator("//locators/checkbox")));
					log("click on the checkbox");
					implicitwait(5);

					Select(getwebelement(xml.getlocator("//locators/ActionPortin")), "Firm order commitment");
					// SendKeys(getwebelement(xml.getlocator("//locators/ActionPortin")),Inputdata[i][30].toString().trim());
					Thread.sleep(15000);
					log("select the valid action portin");

					WaitforElementtobeclickable((xml.getlocator("//locators/GoButon")));
					Clickon(getwebelement(xml.getlocator("//locators/GoButon")));
					log("click onthe go button");
				} else {
					RedLog("CurrentStatus is incorrect");
					Assert.assertTrue(CurrentStatus.get().contains("Validation In Progress"),"Current status is not valid");
				}

				// ----------------------------------

				WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
				Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));

				log("click on the submit button");
				Thread.sleep(2000);
				AcceptJavaScriptMethod();

				log("submitted succesfully");

				Thread.sleep(10000);
				boolean flag1 = false;

				do {

					// String ChildCurrentStatus =
					// Gettext(getwebelement(xml.getlocator("//locators/ChildStatus")));
					flag1 = isElementPresent(xml.getlocator("//locators/ChildStatus"));
					// ChildCurrentStat.set(ChildCurrentStatus);

					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: ChildCurrentStatus is : " + ChildCurrentStat.get());
					log("if child Current Status shown than jump on the next step otherwise click on the refresh button");
					WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
					Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
					
				} while (flag1 == false);
				log("childcurrent status id successfully submited");
				Thread.sleep(1000);

				// =========================================================================
				log("get the child status");
				Assert.assertEquals(ChildCurrentStat.get(), "Completed", "Child status is " + ChildCurrentStat.get());

				String ParentCurrentStatus1 = Gettext(getwebelement(xml.getlocator("//locators/ParentStatus")));
				ParentCurrentStat.set(ParentCurrentStatus1);

				log("ParentCurrentStatus shown");
//				//--------------------------------------------------------------------

				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));
				log("now again check the parent status");
				Thread.sleep(1000);
				// Clickon(getwebelement(xml.getlocator("//locators/ManagePorting")));

				// Thread.sleep(1000);

				WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequesthover")));
				Clickon(getwebelement(xml.getlocator("//locators/updateportinRequesthover")));
				log("click on the update port in request");

				SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
						ParentCurrentStat.get().toString().trim().toLowerCase());
				Thread.sleep(5000);
				log("send the transaction ID");

				WaitforElementtobeclickable((xml.getlocator("//locators/StatusQuerySearchbutton")));
				Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
				log("click on the search button");
				Thread.sleep(3000);
				String CurrentStatus3 = Gettext(getwebelement(xml.getlocator("//locators/currentStatusPortin")));
				CurrentStatus.set(CurrentStatus3);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: CurrentStatus is : " + CurrentStatus.get());
				log("CurrentStatus received");

				log("check the current status");
				if (CurrentStatus.get().contains("Firm order commitment")) {
					WaitforElementtobeclickable((xml.getlocator("//locators/checkbox")));
					Clickon(getwebelement(xml.getlocator("//locators/checkbox")));
					log("click on the checkbox");

					implicitwait(5);
					Select(getwebelement(xml.getlocator("//locators/ActionPortin")), "Activate Port");
					// SendKeys(getwebelement(xml.getlocator("//locators/ActionPortin")),Inputdata[i][30].toString().trim());
					Thread.sleep(10000);
					log("select the valid action portin");

					WaitforElementtobeclickable((xml.getlocator("//locators/GoButon")));
					Clickon(getwebelement(xml.getlocator("//locators/GoButon")));
					log("click onthe go button");
				} else {
					RedLog("CurrentStatus is incorrect");
					Assert.assertTrue(CurrentStatus.get().contains("Validation In Progress"),"Current status is not valid");
				}

				// ----------------------------------

				WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
				Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
				log("click on the submit button");
				Thread.sleep(2000);
				AcceptJavaScriptMethod();
				log("submitted succesfully");

				Thread.sleep(10000);
				boolean flag2 = false;

				do {

					// String ChildCurrentStatus =
					// Gettext(getwebelement(xml.getlocator("//locators/ChildStatus")));
					flag2 = isElementPresent(xml.getlocator("//locators/ChildStatusmain"));
					// ChildCurrentStat.set(ChildCurrentStatus);

					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: ChildCurrentStatus is : " + ChildCurrentStat.get());
					log("if child Current Status shown than jump to the next step otherwise click on the refresh button");
					WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
					Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
					
				} while (flag2 == false);
				Thread.sleep(5000);
				log("childcurrent status id successfully submited");
				Assert.assertEquals(ChildCurrentStat.get(), "Completed", "Child status is " + ChildCurrentStat.get());

				String ParentCurrentStatus12 = Gettext(getwebelement(xml.getlocator("//locators/ParentStatus")));
				ParentCurrentStat.set(ParentCurrentStatus12);

				log("ParentCurrentStatus shown");
//				//--------------------------------------------------------------------
				// ========================================================================

				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));
				log("now again check the parent status");
				Thread.sleep(1000);
				// Clickon(getwebelement(xml.getlocator("//locators/ManagePorting")));

				// Thread.sleep(1000);

				WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequesthover")));
				Clickon(getwebelement(xml.getlocator("//locators/updateportinRequesthover")));
				log("click on the update port in request");
				SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
						ParentCurrentStat.get().toString().trim().toLowerCase());
				log("send the transaction ID");

				Thread.sleep(5000);
				WaitforElementtobeclickable((xml.getlocator("//locators/StatusQuerySearchbutton")));
				Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
				log("click on the search button");

				Thread.sleep(3000);
				String CurrentStatus4 = Gettext(getwebelement(xml.getlocator("//locators/currentStatusPortin")));
				CurrentStatus.set(CurrentStatus4);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: CurrentStatus is : " + CurrentStatus.get());
				log("CurrentStatus received");
				log("check the current status");
				if (CurrentStatus.get().contains("Porting initiated")) {
					WaitforElementtobeclickable((xml.getlocator("//locators/checkbox")));
					Clickon(getwebelement(xml.getlocator("//locators/checkbox")));
					log("Click on the checkbox button");
					implicitwait(5);
					Select(getwebelement(xml.getlocator("//locators/ActionPortin")), "Test successful");
					// SendKeys(getwebelement(xml.getlocator("//locators/ActionPortin")),Inputdata[i][30].toString().trim());
					Thread.sleep(10000);
					log("test successful");

					WaitforElementtobeclickable((xml.getlocator("//locators/GoButon")));
					Clickon(getwebelement(xml.getlocator("//locators/GoButon")));
					log("click on the go button");
				} else {
					RedLog("CurrentStatus is incorrect");
					Assert.assertTrue(CurrentStatus.get().contains("Validation In Progress"),"Current status is not valid");
				}

				// ----------------------------------

				WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
				Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
				log("click on the submit button");
				Thread.sleep(2000);
				AcceptJavaScriptMethod();
				log("submitted succesfully");

				Thread.sleep(10000);
				boolean flag3 = false;

				do {

					// String ChildCurrentStatus =
					// Gettext(getwebelement(xml.getlocator("//locators/ChildStatus")));
					flag3 = isElementPresent(xml.getlocator("//locators/ChildStatus"));
					// ChildCurrentStat.set(ChildCurrentStatus);

					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: ChildCurrentStatus is : " + ChildCurrentStat.get());
					log("if child Current Status shown than jump to the next step otherwise click on the refresh button");
					WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
					Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
					
				} while (flag3 == false);
				Thread.sleep(5000);
				log("childcurrent status id successfully submited");
				Assert.assertEquals(ChildCurrentStat.get(), "Completed", "Child status is " + ChildCurrentStat.get());

				String ParentCurrentStatus13 = Gettext(getwebelement(xml.getlocator("//locators/ParentStatus")));
				ParentCurrentStat.set(ParentCurrentStatus13);
				log("ParentCurrentStatus shown");

				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));
				log("now again check the parent status");
				Thread.sleep(1000);
				// Clickon(getwebelement(xml.getlocator("//locators/ManagePorting")));

				// Thread.sleep(1000);

				WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequesthover")));
				Clickon(getwebelement(xml.getlocator("//locators/updateportinRequesthover")));
				log("click on the update port in request");
				SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
						ParentCurrentStat.get().toString().trim().toLowerCase());
				Thread.sleep(5000);
				log("send the transaction ID");
				WaitforElementtobeclickable((xml.getlocator("//locators/StatusQuerySearchbutton")));
				Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
				log("click on the search button");
				Thread.sleep(3000);
				String CurrentStatus5 = Gettext(getwebelement(xml.getlocator("//locators/currentStatusPortin")));
				CurrentStatus.set(CurrentStatus5);

				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: CurrentStatus is : " + CurrentStatus.get());
				log("CurrentStatus received");

				Assert.assertTrue(CurrentStatus.get().contains("Completed"), "last step unresponsivesss");
				log("Port In scenerion is completed successfully!!");
			}
				else {
					
				 }
		 }
		 

		}

}
