package ScriptHelper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.DocumentException;
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
					Thread.sleep(2000);
					if (Inputdata[i][20].toString().contains("WithAddress")) {
						if (Inputdata[i][19].toString().contains("Activated")) {
							ActivateStatus(Inputdata, i);
//							String winHandleBefore = driver.getWindowHandle();
//							Thread.sleep(5000);
//							Set<String> handles = driver.getWindowHandles();
//							Iterator<String> iterator = handles.iterator();
//							iterator.next();
//							String curent = iterator.next();
//							System.out.println("Window handel" + curent);
//							driver.switchTo().window(curent);
//							// Perform the actions on new window
//
//							String TransactionStatus = null;
//							do {
//								TransactionStatus = Gettext(
//										getwebelement(xml.getlocator("//locators/TransactionStatus")));
//								System.out.println(TransactionStatus);
//								Thread.sleep(5000);
//								Pagerefresh();
//							} while (!TransactionStatus.equals("Completed"));

							// String TransactionStatus =
							// Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
							// System.out.println(TransactionStatus);
//							Assert.assertTrue(TransactionStatus.contains("Completed"));
//							ExtentTestManager.getTest().log(LogStatus.PASS,
//									" Step: Transaction Status is : " + TransactionStatus);
//							Thread.sleep(5000);
//							driver.close();
//							driver.switchTo().window(winHandleBefore);

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
						// Status Query	************************************************************
						
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


					 case "Free" :   // Free to Reserve Scenario
						{
							
						  Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Free");
						  implicitwait(20);
						 
						  //--------------------
						  Select(getwebelement(xml.getlocator("//locators/SelectAreaName")),Inputdata[i][5].toString());
						  implicitwait(20);
						  ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as Free");
						  WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
						  Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
							if(isElementPresent(xml.getlocator("//locators/Griddata")))
							{
								GreenLog("Data are displaying with serach creteria!!");
						
						  if(Inputdata[i][19].toString().contains("Reserve"))
						  {
							    WaitforElementtobeclickable(xml.getlocator("//locators/NumberRangeCheckbox"));
							    Clickon(getwebelement(xml.getlocator("//locators/NumberRangeCheckbox")));
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Range Checkbox");
								Select(getwebelement(xml.getlocator("//locators/Action")),"Reserve");
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Action");
								Clickon(getwebelement(xml.getlocator("//locators/GoButton")));
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
								
								SendKeys(getwebelement(xml.getlocator("//locators/CustomerReference")),Inputdata[i][6].toString());
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter Customer Reference number");
								 WaitforElementtobeclickable(xml.getlocator("//locators/Submit"));
								 
								Clickon(getwebelement(xml.getlocator("//locators/Submit")));
								
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
								
								String TransactionIdres =Gettext(getwebelement(xml.getlocator("//locators/TransactionId")));
							    TransactionId.set(TransactionIdres);
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: The Transaction Id generated is : "+TransactionId);
								Thread.sleep(1000);
								
								
							// Status Query	************************************************************
								
								StatuQuerybyTransitionId(TransactionIdres);
						
						
						// Store the current window handle
//						String winHandleBefore = driver.getWindowHandle();
//
//						WaitforElementtobeclickable(xml.getlocator("//locators/TransactionId"));
//						Clickon(getwebelement(xml.getlocator("//locators/TransactionId")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction Id");
//						System.out.println(TransactionId);
//						Thread.sleep(5000);
//
//						Set<String> handles = driver.getWindowHandles();
//						Iterator<String> iterator = handles.iterator();
//						iterator.next();
//						String curent = iterator.next();
//						System.out.println("Window handel" + curent);
//						driver.switchTo().window(curent);
//						// Perform the actions on new window
//						String TransactionStatus1 = Gettext(
//								getwebelement(xml.getlocator("//locators/TransactionStatus")));
//						ExtentTestManager.getTest().log(LogStatus.PASS,
//								" Step: The Transaction Status is : " + TransactionStatus);
//						TransactionStatus.set(TransactionStatus1);
//						Thread.sleep(1000);
//						VerifyText(TransactionStatus.get());
//						System.out.println(TransactionStatus);
//						Thread.sleep(5000);
//						driver.close();
//						driver.switchTo().window(winHandleBefore);
						/// need to made a method to verify status
						// Assert.assertTrue(Status.contains("Reserved"), "Numbwer Query status is not
						/// update from Free to Reserve!!");
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
							String Transactionresult = Gettext(
									getwebelement(xml.getlocator("//locators/TransactionId")));
							TransactionId.set(Transactionresult);
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Transaction Id is : " + TransactionId.get());
							implicitwait(10);
							// Store the current window handle
							StatuQuerybyTransitionId(Transactionresult);
//							Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
//							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction ID");
//							System.out.println(Transactionresult);
						}

						log("Free to Activated with Address completed.");
					} else if (Inputdata[i][20].toString().contains("WithoutAddress")) {

						log("Start");
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
						//editing
						waitandForElementDisplayed(xml.getlocator("//locators/TransactionIdwithoutAddress"));
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionIdwithoutAddress")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						Thread.sleep(3000);
						implicitwait(20);
						// Status Query	************************************************************
						
						StatuQuerybyTransitionId(Transactionresult);
						log("Free to Activated without Reserve Scenario competed.");

					}

					else if (Inputdata[i][19].toString() == null) {
						log("In Sheet, Perform Action column does not have data to perform action with Free Number Query!");
					} else {
						log("In Sheet, Perform Action column  having data to perform action but not correct spelled with Free Number Query!");
					}
				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}
				break;
			}
			case "Activated": {
				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Activated");
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as Activated");
				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");

				Thread.sleep(5000);

				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {

					GreenLog("Data are displaying with serach creteria!!");
					if (Inputdata[i][19].toString().contains("UpdateAddress")) 
					{
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
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
						Thread.sleep(1000);
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionIdLink")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						//Thread.sleep(3000);
						implicitwait(20);
						// Status Query	************************************************************
						
						StatuQuerybyTransitionId(Transactionresult);
						// Store the current window handle
//						String winHandleBefore = driver.getWindowHandle();
//
//						Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction ID");
//						System.out.println(Transactionresult);
//						Thread.sleep(5000);
//
//						Set<String> handles = driver.getWindowHandles();
//						Iterator<String> iterator = handles.iterator();
//						iterator.next();
//						String curent = iterator.next();
//						System.out.println("Window handel" + curent);
//						driver.switchTo().window(curent);
//						// Perform the actions on new window
//						String TransactionStatus1 = null;
//						do {
//							TransactionStatus1 = Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
//							TransactionStatus.set(TransactionStatus1);
//							System.out.println(TransactionStatus.get());
//							Thread.sleep(5000);
//							Pagerefresh();
//						} while (!TransactionStatus.equals("Completed"));
//
//						Assert.assertTrue(TransactionStatus.get().contains("Completed"));
//						ExtentTestManager.getTest().log(LogStatus.PASS,
//								" Step: Transaction Id is : " + TransactionStatus);
//						Thread.sleep(5000);
//						driver.close();
//						driver.switchTo().window(winHandleBefore);
//						Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
//						Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
//						Select(getwebelement(xml.getlocator("//locators/ResellerName")), Inputdata[0][0].toString());
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
//						Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][1].toString());
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
//						Thread.sleep(3000);
//						Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][2].toString());
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
//						Thread.sleep(3000);
//						Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Transaction ID");
//						ExtentTestManager.getTest().log(LogStatus.PASS,
//								" Step: Select the Search Criteria as Transaction Id");
//						SendKeys(getwebelement(xml.getlocator("//locators/InputTransactionId")), Transactionresult);
//						ExtentTestManager.getTest().log(LogStatus.PASS,
//								" Step: Enter the Transaction Id: " + Transactionresult);
//						Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
//						Thread.sleep(5000);
//						// Store the current window handle
//						// String WinHandleBefore = driver.getWindowHandle();
//						Clickon(getwebelement(xml.getlocator("//locators/TransactionIdLink")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction Id");
//						Thread.sleep(5000);
//
//						Set<String> handle = driver.getWindowHandles();
//						Iterator<String> iterators = handle.iterator();
//						iterators.next();
//						String current = iterators.next();
//						System.out.println("Window handel" + current);
//						driver.switchTo().window(current);
//						// Perform the actions on new window
//						String BuildingNumber = Gettext(
//								getwebelement(xml.getlocator("//locators/VerifyBuldingNumber")));
//						String BuildingName = Gettext(getwebelement(xml.getlocator("//locators/VerifyBuildingName")));
//						String StreetName = Gettext(getwebelement(xml.getlocator("//locators/VerifyStreetName")));
//						String City = Gettext(getwebelement(xml.getlocator("//locators/VerifyCity")));
//						String PostCode = Gettext(getwebelement(xml.getlocator("//locators/VerifyPostCode")));
//						Assert.assertTrue(BuildingNumber.contains(Inputdata[0][9].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS,
//								" Step: Verify Building number: " + BuildingNumber);
//
//						Assert.assertTrue(BuildingName.contains(Inputdata[0][8].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Building name: " + BuildingName);
//						Assert.assertTrue(StreetName.contains(Inputdata[0][10].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Street name: " + StreetName);
//						Assert.assertTrue(City.contains(Inputdata[0][11].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify City : " + City);
//						Assert.assertTrue(PostCode.contains(Inputdata[0][12].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Post Code: " + PostCode);
//						Thread.sleep(5000);
//						driver.close();
//						driver.switchTo().window(winHandleBefore);
					} 
					else if (Inputdata[i][19].toString().contains("Deactivate")) {
						WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Radio button");
						Select(getwebelement(xml.getlocator("//locators/UserAction")),"Deactivate");
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action");
						WaitforElementtobeclickable(xml.getlocator("//locators/UserGo"));
						Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						Thread.sleep(1000);
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionIdLink")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						//Thread.sleep(3000);
						implicitwait(20);
						// Status Query	************************************************************
						
						StatuQuerybyTransitionId(Transactionresult);
						

					}
					else {
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
					if (Inputdata[i][19].toString().contains("Reactivate")) {
						WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Radio button");
						Select(getwebelement(xml.getlocator("//locators/UserAction")),"Reactivate");
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action");
						WaitforElementtobeclickable(xml.getlocator("//locators/UserGo"));
						Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionIdLink")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						//Thread.sleep(3000);
						implicitwait(20);
						// Status Query	************************************************************
						
						StatuQuerybyTransitionId(Transactionresult);
					}
					

				} 
				else {
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
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					Thread.sleep(5000l);
					String Transactionresult = Gettext(
							getwebelement(xml.getlocator("//locators/TransactionIdLink")));
					TransactionId.set(Transactionresult);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Transaction Id is : " + TransactionId.get());
					Thread.sleep(1000);
					implicitwait(20);
					// Status Query	************************************************************
					
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
						// Store the current window handle
//						String winHandleBefore = driver.getWindowHandle();
//
//						Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction ID");
//						System.out.println(Transactionresult);
//						Thread.sleep(5000);
//
//						Set<String> handles = driver.getWindowHandles();
//						Iterator<String> iterator = handles.iterator();
//						iterator.next();
//						String curent = iterator.next();
//						System.out.println("Window handel" + curent);
//						driver.switchTo().window(curent);
//						// Perform the actions on new window
//						String TransactionStatus1 = null;
//						do {
//							TransactionStatus1 = Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
//							TransactionStatus.set(TransactionStatus1);
//							System.out.println(TransactionStatus.get());
//							Thread.sleep(5000);
//							Pagerefresh();
//						} while (!TransactionStatus.equals("Completed"));
//
//						Assert.assertTrue(TransactionStatus.get().contains("Completed"));
//						ExtentTestManager.getTest().log(LogStatus.PASS,
//								" Step: Transaction Id is : " + TransactionStatus);
//						Thread.sleep(5000);
//						driver.close();
//						driver.switchTo().window(winHandleBefore);
//						Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
//						Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
//						Select(getwebelement(xml.getlocator("//locators/ResellerName")), Inputdata[0][0].toString());
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Reseller name");
//						Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][1].toString());
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country");
//						Thread.sleep(3000);
//						Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][2].toString());
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Service Profile");
//						Thread.sleep(3000);
//						Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Transaction ID");
//						ExtentTestManager.getTest().log(LogStatus.PASS,
//								" Step: Select the Search Criteria as Transaction Id");
//						SendKeys(getwebelement(xml.getlocator("//locators/InputTransactionId")), Transactionresult);
//						ExtentTestManager.getTest().log(LogStatus.PASS,
//								" Step: Enter the Transaction Id: " + Transactionresult);
//						Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");
//						Thread.sleep(5000);
//						// Store the current window handle
//						// String WinHandleBefore = driver.getWindowHandle();
//						Clickon(getwebelement(xml.getlocator("//locators/TransactionIdLink")));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction Id");
//						Thread.sleep(5000);
//
//						Set<String> handle = driver.getWindowHandles();
//						Iterator<String> iterators = handle.iterator();
//						iterators.next();
//						String current = iterators.next();
//						System.out.println("Window handel" + current);
//						driver.switchTo().window(current);
//						// Perform the actions on new window
//						String BuildingNumber = Gettext(
//								getwebelement(xml.getlocator("//locators/VerifyBuldingNumber")));
//						String BuildingName = Gettext(getwebelement(xml.getlocator("//locators/VerifyBuildingName")));
//						String StreetName = Gettext(getwebelement(xml.getlocator("//locators/VerifyStreetName")));
//						String City = Gettext(getwebelement(xml.getlocator("//locators/VerifyCity")));
//						String PostCode = Gettext(getwebelement(xml.getlocator("//locators/VerifyPostCode")));
//						Assert.assertTrue(BuildingNumber.contains(Inputdata[0][9].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS,
//								" Step: Verify Building number: " + BuildingNumber);
//
//						Assert.assertTrue(BuildingName.contains(Inputdata[0][8].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Building name: " + BuildingName);
//						Assert.assertTrue(StreetName.contains(Inputdata[0][10].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Street name: " + StreetName);
//						Assert.assertTrue(City.contains(Inputdata[0][11].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify City : " + City);
//						Assert.assertTrue(PostCode.contains(Inputdata[0][12].toString()));
//						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Verify Post Code: " + PostCode);
//						Thread.sleep(5000);
//						driver.close();
//						driver.switchTo().window(winHandleBefore);
						
						
						StatuQuerybyTransitionId(Transactionresult);
					}
//					}
					else if (Inputdata[i][19].toString().contains("Deactivate")) {
						WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
						Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Radio button");
						Select(getwebelement(xml.getlocator("//locators/UserAction2")),"Deactivate");
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action");
						WaitforElementtobeclickable(xml.getlocator("//locators/UserGo2"));
						Clickon(getwebelement(xml.getlocator("//locators/UserGo2")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionIdLink")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());
						//Thread.sleep(3000);
						implicitwait(20);
						// Status Query	************************************************************
						
						StatuQuerybyTransitionId(Transactionresult);
					}
					else {
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
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
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
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					String Transactionresult = Gettext(
							getwebelement(xml.getlocator("//locators/TransactionIdLink")));
					TransactionId.set(Transactionresult);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Transaction Id is : " + TransactionId.get());
					//Thread.sleep(3000);
					implicitwait(20);
					// Status Query	************************************************************
					
					StatuQuerybyTransitionId(Transactionresult);

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
				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					String Transactionresult = Gettext(
							getwebelement(xml.getlocator("//locators/TransactionIdLink")));
					TransactionId.set(Transactionresult);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Transaction Id is : " + TransactionId.get());
					//Thread.sleep(3000);
					implicitwait(20);
					// Status Query	************************************************************
					
					StatuQuerybyTransitionId(Transactionresult);

				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}
				break;
			}

			// Deafault case
			default: {
				RedLog("In Excel data, It seems like Number Status data is misspelled or not correct!! Data is "
						+ Inputdata[i][4].toString());
			}
			}

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
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step:click the Search button");
			Thread.sleep(1500);
			if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
				GreenLog("Data are displaying with serach creteria!!");

				Thread.sleep(1000);
				String Transactionresult = Gettext(
						getwebelement(xml.getlocator("//locators/TransactionIdLink")));
				TransactionId.set(Transactionresult);
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Transaction Id is : " + TransactionId.get());
				//Thread.sleep(3000);
				implicitwait(20);
				// Status Query	************************************************************
				
				StatuQuerybyTransitionId(Transactionresult);
			} else {
				RedLog("System does not have search result with Search Transaction Id with country!! Transection id is "
						+ Inputdata[i][18].toString() + " And Country is " + Inputdata[i][2].toString());
			}
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
			
			
			
			if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
				GreenLog("Data are displaying with serach creteria!!");
				Thread.sleep(1000);
				String Transactionresult = Gettext(
						getwebelement(xml.getlocator("//locators/TransactionIdLink")));
				TransactionId.set(Transactionresult);
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Transaction Id is : " + TransactionId.get());
				//Thread.sleep(3000);
				implicitwait(20);
				// Status Query	************************************************************
				
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
				String Transactionresult = Gettext(
						getwebelement(xml.getlocator("//locators/TransactionIdLink")));
				TransactionId.set(Transactionresult);
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Transaction Id is : " + TransactionId.get());
				//Thread.sleep(3000);
				implicitwait(20);
				// Status Query	************************************************************
				
				StatuQuerybyTransitionId(Transactionresult);
			} else {
				RedLog("System does not have search result with Customer Reference  with country!! Customer Reference is "
						+ Inputdata[i][6].toString().trim() + " And Country is " + Inputdata[i][2].toString());
			}
		}
	}

	public void ActivateStatus(Object[][] Inputdata, int i)
			throws InterruptedException, DocumentException, IOException {
		log("Start");
		WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
		Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
		Select(getwebelement(xml.getlocator("//locators/UserAction")), "Activate");
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
		//Thread.sleep(10000);
		// Store the current window handle

//		Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
//		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Transaction ID");
//		System.out.println(Transactionresult);
		System.out.println(Transactionresult);
		StatuQuerybyTransitionId(Transactionresult);

	}
	 public void StatuQuerybyTransitionId(String TransactionID) throws InterruptedException, DocumentException, IOException
	 {
		 
		 Moveon(getwebelement(xml.getlocator("//locators/statusQuery")));
		 implicitwait(20);
		 Clickon(getwebelement(xml.getlocator("//locators/NumberHostingTransaction")));
		 
		 SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),TransactionID);
		
		
//		 WaitforElementtobeclickable(xml.getlocator("//locators/StatusQuerySearchbutton"));
//		 Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
		 
		 implicitwait(20);
		 String Norecord = Gettext(getwebelement(xml.getlocator("//locators/NoRecordFound")));
		 //EnterText2(Keys.ENTER);
		 if(Norecord.contains("No records found!"))
		 {
			 implicitwait(20);
			 Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
		 }
		 else 
		 {
			 WaitforElementtobeclickable(xml.getlocator("//locators/StatusQuerySearchbutton"));
			 Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
		}
			 
			 
			 
			 Thread.sleep(1000);
		 implicitwait(20);
		
		 String transType = Gettext(getwebelement(xml.getlocator("//locators/TransactionType")));
		 
		 ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : "+transType);
		 System.out.println("*****************************************************************************"+transType);
		 implicitwait(20);

		 String transStatus = Gettext(getwebelement(xml.getlocator("//locators/StatusQueryTransactionStatus")));
		System.out.println("*****************************************************************************"+transStatus);
		 
		 if(transStatus.contains("Completed") )
		 {
			 GreenLog("Status Query has been passed with Transaction Status Completedz");
			 
			 ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status is :Completed");
			
		 } 
		 else if(transStatus.contains("In Progress"))
			 { 
			 GreenLog("Status Query has been passed with Transaction Status : In Progress");
				 ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status is : In Progress");
			 }
		 else if(transStatus.contains("Failed with Error"))
		 { 
		 
		 RedLog("it seems like there is Transaction Status Error: Failed with Error");
		 }
		 
		 else {
			 RedLog("it seems like there is no any Transaction Status value which cantains  Completed or In Process");
			
			 
		 }
		 
		 //shashank Tiwari
		 
	 
	 }

}
