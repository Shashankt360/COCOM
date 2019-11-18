package ScriptHelper;

import java.awt.List;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.DocumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import Driver.DriverHelper;
import Driver.Log;
import Driver.PropertyReader;
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
		PropertyReader pr = new PropertyReader();

		String URL = null;

		URL = pr.readproperty("NH_URL");

		ExtentTestManager.getTest().log(LogStatus.PASS,
				" Step: Opening the Url : <font color='green'> " + URL + " </font>");

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
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Select the Reseller name: " + Inputdata[i][1].toString());

			Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][2].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country: " + Inputdata[i][2].toString());
			implicitwait(20);
			waitandForElementDisplayed(xml.getlocator("//locators/ServiceProfile"));
			Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][3].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Select the Service Profile: " + Inputdata[i][3].toString());
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
					GreenLog("Data are displaying with search creteria!!");
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
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action as Activate");
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

			case "Free": // Free to Reserve Scenario
			{

				Select(getwebelement(xml.getlocator("//locators/NumberStatus")), "Free");
				implicitwait(20);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Number Status as Free");

				// --------------------
				// for test

				if (Inputdata[i][24].toString().equals("Geographical Numbers")) {

					if ((Inputdata[i][2].toString().equals("DENMARK"))
							|| (Inputdata[i][2].toString().equals("PORTUGAL"))) {
						log("Don't need to click on geographical and nongeographical radio Button");
					} else {
						WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton"));
						Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton")));
						implicitwait(20);

					}
					Thread.sleep(2000);
					Select(getwebelement(xml.getlocator("//locators/SelectAreaName")), Inputdata[i][5].toString());
					implicitwait(20);
					Select(getwebelement(xml.getlocator("//locators/blockSize")), Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Select the Block Size: " + Inputdata[i][22].toString());
					implicitwait(5);
					waitandForElementDisplayed(xml.getlocator("//locators/Quantity"));
					Select(getwebelement(xml.getlocator("//locators/Quantity")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Select the Quantity Size: " + Inputdata[i][23].toString());

				}

				else if (Inputdata[i][24].toString().equals("Location Independent Numbers")) {
					WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton2"));
					Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton2")));
					Select(getwebelement(xml.getlocator("//locators/blockSize2")), Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Select the Block Size: " + Inputdata[i][22].toString());

					Select(getwebelement(xml.getlocator("//locators/Quantity2")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Select the Quantity Size: " + Inputdata[i][23].toString());
				}

				else if (Inputdata[i][24].toString().equals("UK WIDE (Any Services)")) {
					WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton3"));
					Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton3")));
					Select(getwebelement(xml.getlocator("//locators/blockSize2")), Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Select the Block Size: " + Inputdata[i][22].toString());

					Select(getwebelement(xml.getlocator("//locators/Quantity2")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Select the Quantity Size: " + Inputdata[i][23].toString());
				}

				else if (Inputdata[i][24].toString().equals("UK WIDE (Public Services)")) {
					WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton4"));
					Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton4")));
					Select(getwebelement(xml.getlocator("//locators/blockSize2")), Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Select the Block Size: " + Inputdata[i][22].toString());

					Select(getwebelement(xml.getlocator("//locators/Quantity2")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Select the Quantity Size: " + Inputdata[i][23].toString());
				}

				else if (Inputdata[i][24].toString().equals("Search by City")) {
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search by city");
					WaitforElementtobeclickable(xml.getlocator("//locators/NumberRadioButton6"));
					Clickon(getwebelement(xml.getlocator("//locators/NumberRadioButton6")));

					Select(getwebelement(xml.getlocator("//locators/Province")), Inputdata[i][25].toString());
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Select the Province: " + Inputdata[i][25].toString());
					Select(getwebelement(xml.getlocator("//locators/cityandtown")), Inputdata[i][26].toString());
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Select the City/Town: " + Inputdata[i][26].toString());
					Select(getwebelement(xml.getlocator("//locators/blockSize2")), Inputdata[i][22].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: Select the Block Size: " + Inputdata[i][22].toString());

					Select(getwebelement(xml.getlocator("//locators/Quantity2")), Inputdata[i][23].toString());
					implicitwait(5);
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Select the Quantity Size: " + Inputdata[i][23].toString());

				}

				else {
					RedLog("Geographical or Nongeographical button is missing");
				}
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Select the Geographical or NonGeographical radio button");

				WaitforElementtobeclickable(xml.getlocator("//locators/SearchButton"));
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				validate();
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Search button");

				if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
					GreenLog("Data are displaying with serach creteria!!");
					DownloadExcel("NumberEnquiryReport");
					if (Inputdata[i][19].toString().contains("Reserve")) {
						System.out.println("not shashank");
						if (isElementPresent(xml.getlocator("//locators/SelectNumber").replace("Number",
								Inputdata[i][35].toString()))) {

							Clickon(getwebelement(xml.getlocator("//locators/SelectNumberCheck").replace("Number",
									Inputdata[i][35].toString())));
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Click on the Number Range Checkbox");
							Select(getwebelement(xml.getlocator("//locators/Action")), "Reserve");
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Action as Reserve");
							Clickon(getwebelement(xml.getlocator("//locators/GoButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");

							Thread.sleep(4000);
							SendKeys(getwebelement(xml.getlocator("//locators/CustomerReference")),
									Inputdata[i][6].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Enter Customer Reference number: " + Inputdata[i][6].toString());
							WaitforElementtobeclickable(xml.getlocator("//locators/Submit"));

							Clickon(getwebelement(xml.getlocator("//locators/Submit")));

							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");

							String TransactionIdres = Gettext(
									getwebelement(xml.getlocator("//locators/TransactionId")));
							TransactionId.set(TransactionIdres);
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: The Transaction Id generated is : " + TransactionIdres);
							Thread.sleep(1000);

							// Status Query ************************************************************

							StatuQuerybyTransitionId(TransactionIdres);

							GreenLog("Numbwer Query status id update Successfully from Free to Reserve");
						} else {

							WaitforElementtobeclickable(xml.getlocator("//locators/NumberRangeCheckbox"));
							Clickon(getwebelement(xml.getlocator("//locators/NumberRangeCheckbox")));
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Click on the Number Range Checkbox");
							Select(getwebelement(xml.getlocator("//locators/Action")), "Reserve");
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Action as Reserve");
							Clickon(getwebelement(xml.getlocator("//locators/GoButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");

							Thread.sleep(4000);
							SendKeys(getwebelement(xml.getlocator("//locators/CustomerReference")),
									Inputdata[i][6].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Enter Customer Reference number: " + Inputdata[i][6].toString());
							WaitforElementtobeclickable(xml.getlocator("//locators/Submit"));

							Clickon(getwebelement(xml.getlocator("//locators/Submit")));

							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");

							String TransactionIdres = Gettext(
									getwebelement(xml.getlocator("//locators/TransactionId")));
							TransactionId.set(TransactionIdres);
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: The Transaction Id generated is : " + TransactionIdres);
							Thread.sleep(1000);

							// Status Query ************************************************************

							StatuQuerybyTransitionId(TransactionIdres);

							GreenLog("Numbwer Query status id update Successfully from Free to Reserve");
						}
					} else if (Inputdata[i][20].toString().contains("WithAddress")) {
						if (Inputdata[i][19].toString().contains("Activated")) {
							WaitforElementtobeclickable(xml.getlocator("//locators/NumberRangeCheckbox"));

							Clickon(getwebelement(xml.getlocator("//locators/NumberRangeCheckbox")));
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Click on the Number Range Checkbox");

							Select(getwebelement(xml.getlocator("//locators/Action")), "Activate");
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Action as Activate");
							Clickon(getwebelement(xml.getlocator("//locators/GoButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
							SendKeys(getwebelement(xml.getlocator("//locators/CustomerName")),
									Inputdata[i][7].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Fill the Customer name Field: " + Inputdata[i][7].toString());
							SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")),
									Inputdata[i][9].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Fill the building number Field: " + Inputdata[i][9].toString());
							SendKeys(getwebelement(xml.getlocator("//locators/city")), Inputdata[i][11].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Fill the City Field: " + Inputdata[i][11].toString());
							SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")),
									Inputdata[i][8].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Fill the Building name field: " + Inputdata[i][8].toString());
							SendKeys(getwebelement(xml.getlocator("//locators/Street")), Inputdata[i][10].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Fill the Building name field: " + Inputdata[i][8].toString());
							SendKeys(getwebelement(xml.getlocator("//locators/PostCode")), Inputdata[i][12].toString());
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Fill the Post Code: " + Inputdata[i][12].toString());
							WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
							Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
							implicitwait(10);

							if (Inputdata[i][21].toString().contains("Port-out")) {

								Clickon(getwebelement(xml.getlocator("//locators/TransactionId")));
								String winHandleBefore = driver.getWindowHandle();

								// Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
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
								boolean flag = false;
								String date3 = null;
								DateFormat dateFormat = new SimpleDateFormat("HH:mm");

								String date1 = dateFormat.format(new Date(System.currentTimeMillis()));
								Date dd = new Date(System.currentTimeMillis() + 20 * 60 * 1000);
								String date2 = dateFormat.format(dd);

								do {

									Thread.sleep(2000);
									// TransactionStatus1 =
									// Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
									Pagerefresh();
									Pagerefresh();
									flag = isElementPresent(xml.getlocator("//locators/CompletedTxStatus"));

									date3 = dateFormat.format(new Date(System.currentTimeMillis()));
									System.out.println(date3);
									System.out.println(date2);
									Thread.sleep(1000);
									Pagerefresh();
									if (date3.trim().equals(date2.trim())) {
										System.out.println(
												"System is waiting for 20 minutes to change status, But still status in not changed!");
										Assert.fail(
												"System is taking longer time to change Status from InProgress to Complted!");
										break;
									}
								}

								while (flag == false);
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Transaction Status :completed for Activation number");

								String mainNumber = Gettext(getwebelement(xml.getlocator("//locators/mainNumberone")));
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: get the main number from Activated number ");
								MainNumber.set(mainNumber);
								String rangestart = Gettext(getwebelement(xml.getlocator("//locators/RangeStartone")));
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: get the Range start number from Activated number ");
								StartRange.set(rangestart);
								String rangeEnd = Gettext(getwebelement(xml.getlocator("//locators/RangeEndone")));
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: get the Range end number from Activated number ");
								EndRange.set(rangeEnd);
								String postcode = Gettext(
										getwebelement(xml.getlocator("//locators/PostCodeforport-out")));
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: get the PostCode from Activated number ");
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

								for (int k = 0; k <= allData; k++) {

									if (k != 0 && k % 10 == 0) {
										Clickon(getwebelement(xml.getlocator("//locators/NEXT")));
										Thread.sleep(2000);
									}

									Thread.sleep(1000);
									String data = GetText(getwebelement(xml.getlocator("//locators/ServiceProfileone")
											.replace("index", String.valueOf(k + 1))));

									System.out.println(data);

									if (data.contains(Inputdata[i][3].toString().trim())
											|| data == Inputdata[i][3].toString().trim()) {

										Thread.sleep(2000);
										try {
											safeJavaScriptClick(getwebelement(xml.getlocator("//locators/ANH")));
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										ExtentTestManager.getTest().log(LogStatus.PASS,
												" Step: Click on Service profile: "
														+ Inputdata[i][3].toString().trim());

										Thread.sleep(2000);
										break;
									}
								}

								Select(getwebelement(xml.getlocator("//locators/LocalAreaCodeonePortout")),
										Inputdata[i][5].toString());
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: gSelect the Local Area code: " + Inputdata[i][5].toString());

								SendKeys(getwebelement(xml.getlocator("//locators/mainNumberportout")),
										MainNumber.get());
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Enter the main number for port-out number:  " + MainNumber.get());
								SendKeys(getwebelement(xml.getlocator("//locators/Rangestartport-out")),
										StartRange.get());
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Enter the Range Start number for port-out: " + StartRange.get());
								SendKeys(getwebelement(xml.getlocator("//locators/RangeEndport-out")), EndRange.get());
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Enter the Range End number for port-out: " + EndRange.get());

								implicitwait(20);
								Thread.sleep(2000);
								javaexecutotSendKeys(Inputdata[i][22].toString(),
										getwebelement(xml.getlocator("//locators/portoutdate")));
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Select the Port-out date from Calender: "
												+ Inputdata[i][22].toString());
								SendKeys(getwebelement(xml.getlocator("//locators/OperatorName-Portout")),
										Inputdata[i][23].toString());
								// ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Operator
								// Name "+Inputdata[i][23].toString());
								log(" Step: Enter the Operator Name:  " + Inputdata[i][23].toString());
								SendKeys(getwebelement(xml.getlocator("//locators/Postcode-portout")), postcode);
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Enter the PostCode for port-out: " + postcode);
								implicitwait(20);
								Thread.sleep(3000);
								Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step:Click on submit button");
								Thread.sleep(1000);
								waitandForElementDisplayed(xml.getlocator("//locators/TransactionResult"));
								String portoutTX = GetText(
										getwebelement(xml.getlocator("//locators/TransactionResult")));
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: getting the Transaction ID");
								log(portoutTX);
								TransactionIdForPortout.set(portoutTX);
//			
								WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequest")));

								Clickon(getwebelement(xml.getlocator("//locators/updateportinRequest")));
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Click on For update Portin Request");
								waitandForElementDisplayed(xml.getlocator("//locators/statusQueryTransactionID"));
								log(TransactionIdForPortout.get().toString().trim().toLowerCase());
								SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
										TransactionIdForPortout.get().toString().trim().toLowerCase());
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
								Select(getwebelement(xml.getlocator("//locators/transactionType-portout")),
										Inputdata[i][24].toString());
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: the Tranasction Type: " + Inputdata[i][24].toString());
								Thread.sleep(1000);
								waitandForElementDisplayed(xml.getlocator("//locators/TransactionID-forPortout"));
								log(TransactionIdForPortout.get().toString().trim());
								SendKeys(getwebelement(xml.getlocator("//locators/TransactionID-forPortout")),
										TransactionIdForPortout.get().toString().trim().toLowerCase());
								waitandForElementDisplayed(xml.getlocator("//locators/changeType-portout"));
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Enter the Transaction ID for port-out Request: "
												+ Inputdata[i][25].toString());
								Select(getwebelement(xml.getlocator("//locators/changeType-portout")),
										Inputdata[i][25].toString());

								Thread.sleep(1000);
								waitandForElementDisplayed(xml.getlocator("//locators/NewStatus-portout"));
								Select(getwebelement(xml.getlocator("//locators/NewStatus-portout")),
										Inputdata[i][26].toString());
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Select the Status from dropdown: " + Inputdata[i][26].toString());
								WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
								Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Submit button");
								implicitwait(20);

								Clickon(getwebelement(xml.getlocator("//locators/TransactionResult")));
								String winHandleBefore1 = driver.getWindowHandle();

								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Click on Transaction Id to validate the Trasaction status");

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
								boolean flag0 = false;
								do {

									Thread.sleep(2000);
									// TransactionStatus1 =
									// Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
									Pagerefresh();
									Pagerefresh();
									flag0 = isElementPresent(xml.getlocator("//locators/CompletedTxStatus"));
									ExtentTestManager.getTest().log(LogStatus.PASS,
											" Step: Checking the Transaction Status is Completed or not");

									Thread.sleep(1000);
									Pagerefresh();
								}

								while (flag0 == false);
								String TransactionStatus1 = Gettext(
										getwebelement(xml.getlocator("//locators/CompletedTxStatus")));
								TransactionPortoutstatus.set(TransactionStatus1);

								Assert.assertTrue(TransactionPortoutstatus.get().contains("Completed"));
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Assertion Status has been passed for port -out");
								ExtentTestManager.getTest().log(LogStatus.PASS,
										" Step: Transaction Status has been Completed for port-out");

							}

							else {

								ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Port-out has been completed");

							}
						}

						log("Free to Activated with Address completed.");
					}

				} else if (Inputdata[i][20].toString().contains("WithoutAddress")) {

					log("Start");
					Thread.sleep(2000);
					if (isElementPresent(xml.getlocator("//locators/Griddata"))) {
						GreenLog("Data are displaying with serach creteria!!");

						WaitforElementtobeclickable(xml.getlocator("//locators/NumberRangeCheckbox"));
						Clickon(getwebelement(xml.getlocator("//locators/NumberRangeCheckbox")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Range Checkbox");
						Select(getwebelement(xml.getlocator("//locators/Action")), "Activate");
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Action as Activate");
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

					} else if (Inputdata[i][19].toString() == null) {
						log("In Sheet, Perform Action column does not have data to perform action with Free Number Query!");
					} else {
						log("In Sheet, Perform Action column  having data to perform action but not correct spelled with Free Number Query!");
					}
				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "

							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString()
							+ " And Block size " + Inputdata[i][22].toString() + " And Quantity "
							+ Inputdata[i][23].toString());
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
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Select the User Action: " + Inputdata[i][16].toString());
						Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						Thread.sleep(1000);
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")), Inputdata[i][8].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the Building name field: " + Inputdata[i][8].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")),
								Inputdata[i][9].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the building number Field: " + Inputdata[i][9].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/NewStreet")), Inputdata[i][10].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the New Street Field: " + Inputdata[i][10].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/NewCity")), Inputdata[i][11].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the  New City Field: " + Inputdata[i][11].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/NewPostCode")), Inputdata[i][12].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the Post Code: " + Inputdata[i][12].toString());
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the submit button");
						Thread.sleep(9000);

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
						try {
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action as Deactivate");
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

							implicitwait(20);
						} catch (StaleElementReferenceException e) {
							ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action as Deactivate");
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
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action as Reactivate");
						WaitforElementtobeclickable(xml.getlocator("//locators/UserGo"));
						Clickon(getwebelement(xml.getlocator("//locators/UserGo")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						Thread.sleep(3000);
						// String Transactionresult=null;
						try {
							String Transactionresult = Gettext(
									getwebelement(xml.getlocator("//locators/TransactionResult")));
							TransactionId.set(Transactionresult);
							StatuQuerybyTransitionId(Transactionresult);
						}

						catch (StaleElementReferenceException e) {

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
					Thread.sleep(5000);
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
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Select the User Action: " + Inputdata[i][16].toString());
						Clickon(getwebelement(xml.getlocator("//locators/UserGo2")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						Thread.sleep(5000);
						if (Inputdata[i][2].toString().equals("AUSTRIA")) {
							SendKeys(getwebelement(xml.getlocator("//locators/CustomerName")),
									Inputdata[i][7].toString());
							log("country is Austria so customer name is required: " + Inputdata[i][7].toString());
						}
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")), Inputdata[i][8].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the Building name field: " + Inputdata[i][8].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")),
								Inputdata[i][9].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the building number Field: " + Inputdata[i][9].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/NewStreet")), Inputdata[i][10].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the New Street Field: " + Inputdata[i][10].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/NewCity")), Inputdata[i][11].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the  New City Field: " + Inputdata[i][11].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/NewPostCode")), Inputdata[i][12].toString());
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Fill the Post Code: " + Inputdata[i][12].toString());
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
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select user action as Deactivate");
						WaitforElementtobeclickable(xml.getlocator("//locators/UserGo2"));
						Clickon(getwebelement(xml.getlocator("//locators/UserGo2")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
						Thread.sleep(3000);
						String Transactionresult = null;
						;
						try {

							Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/TransactionResult")));
							TransactionId.set(Transactionresult);
							ExtentTestManager.getTest().log(LogStatus.PASS,
									" Step: Transaction Id is : " + TransactionId.get());
						} catch (StaleElementReferenceException e) {
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
					try {

						String Transactionresult = Gettext(
								getwebelement(xml.getlocator("//locators/TransactionIdLink")));
						TransactionId.set(Transactionresult);
						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Id is : " + TransactionId.get());

						implicitwait(20);
						// Status Query ************************************************************

						StatuQuerybyTransitionId(Transactionresult);
					} catch (StaleElementReferenceException e) {

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

				} else {
					RedLog("System does not have search result with Number Status with country!! Number status is "
							+ Inputdata[i][4].toString().trim() + " And Country is " + Inputdata[i][2].toString());
				}
				break;
			}

			// Deafault case replace("index"),transid
			default: {
				RedLog("In Excel data, It seems like Number Status data is misspelled or not correct!! Data is "
						+ Inputdata[i][4].toString());
			}
			}
			GreenLog(
					"/******************************* current Step  has been finished * ******************************************");
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

			Select(getwebelement(xml.getlocator("//locators/ResellerName")), Inputdata[i][1].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Select the Reseller name: " + Inputdata[i][1].toString());
			Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][2].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country: " + Inputdata[i][2].toString());
			implicitwait(20);
			Thread.sleep(1000);
			WaitforElementtobeclickable(xml.getlocator("//locators/ServiceProfile"));
			Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][3].toString().trim());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Select the Service Profile: " + Inputdata[i][3].toString());
			Thread.sleep(2000);

			Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Transaction ID");
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number Status");

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
				// --------------------

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
			GreenLog(
					"/******************************* current Step  has been finished * ******************************************");
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
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Select the Reseller name: " + Inputdata[i][1].toString());

			Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][2].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country: " + Inputdata[i][2].toString());
			Thread.sleep(2000);
			Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][3].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Select the Service Profile: " + Inputdata[i][3].toString());
			Thread.sleep(3000);

			Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Number Range");
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number Status");

			Select(getwebelement(xml.getlocator("//locators/LocalAreaCode")), Inputdata[i][5].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Select the Local Area code: " + Inputdata[i][5].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/mainNumber")), Inputdata[i][13].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Enter the main number: " + Inputdata[i][13].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/RangeStart")), Inputdata[i][14].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Enter the RangeStart: " + Inputdata[i][14].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/RangeEnd")), Inputdata[i][15].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Enter the RangeEnd: " + Inputdata[i][15].toString());
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
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Select the Reseller name: " + Inputdata[i][1].toString());

			Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][2].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country: " + Inputdata[i][2].toString());
			implicitwait(20);
			Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][3].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Select the Service Profile: " + Inputdata[i][3].toString());
			Thread.sleep(3000);

			Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Customer Reference");
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Customer Reference");
			// need here to paste customer reference
			SendKeys(getwebelement(xml.getlocator("//locators/CustomerReferenceNumber")),
					Inputdata[i][6].toString().trim());

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

				implicitwait(20);
				// Status Query ************************************************************

				StatuQuerybyTransitionId(Transactionresult);
			} else {
				RedLog("System does not have search result with Customer Reference  with country!! Customer Reference is "
						+ Inputdata[i][6].toString().trim() + " And Country is " + Inputdata[i][2].toString());
			}
			GreenLog(
					"/******************************* current Step  has been finished * ******************************************");
		}
	}

	public void ActivateStatus(Object[][] Inputdata, int i)
			throws InterruptedException, DocumentException, IOException {
		log("Start");

		Thread.sleep(2000);
		if (isElementPresent(
				xml.getlocator("locators/ReserveElement").replace("Transactionid", Inputdata[i][35].toString()))) {
			int size = getwebelementscount(xml.getlocator("locators/ReserveElementall"));

			java.util.List<WebElement> all = getwebelements(xml.getlocator("locators/ReserveElementall"));

			for (int j = 0; j < size; j++) {

				String transid = all.get(j).getText();
				System.out.println(transid);

				javascriptexecutor(getwebelement(xml.getlocator("locators/ReserveElement").replace("Transactionid",
						Inputdata[i][35].toString())));
				implicitwait(20);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
				Clickon(getwebelement(xml.getlocator("locators/ReserveElement").replace("Transactionid",
						Inputdata[i][35].toString())));

				Thread.sleep(2000);

				Select(getwebelement(xml.getlocator("//locators/SelectAction").replace("Transactionid",
						Inputdata[i][35].toString())), "Activate");
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
				// SendKeys1(getwebelement(xml.getlocator("//locators/ActivateReserve").replace("Activate",
				// Inputdata[i][16].toString())));
				implicitwait(20);
				WaitforElementtobeclickable(
						xml.getlocator("//locators/Gobtnmain").replace("Transactionid", Inputdata[i][35].toString()));
				Clickon(getwebelement(
						xml.getlocator("//locators/Gobtnmain").replace("Transactionid", Inputdata[i][35].toString())));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");

				Thread.sleep(5000);

				break;
			}
//		System.out.println("yes yes");
//		WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
//		Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
//		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
//		Select(getwebelement(xml.getlocator("//locators/UserAction")), "Activate");
//		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
//		WaitforElementtobeclickable(xml.getlocator("//locators/GoButon"));
//		Clickon(getwebelement(xml.getlocator("//locators/GoButon")));
			// ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go
			// button");
			SendKeys(getwebelement(xml.getlocator("//locators/CustomerName")), Inputdata[i][7].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the Customer name Field: " + Inputdata[i][7].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")), Inputdata[i][9].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the building number Field: " + Inputdata[i][9].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/city")), Inputdata[i][11].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the City Field: " + Inputdata[i][11].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")), Inputdata[i][8].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the Building name field: " + Inputdata[i][8].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/Street")), Inputdata[i][10].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the Building name field: " + Inputdata[i][8].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/PostCode")), Inputdata[i][12].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the Post Code: " + Inputdata[i][12].toString());
			WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
			Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
			Thread.sleep(1000);

			String Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/TransactionResult")));
			TransactionId.set(Transactionresult);
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : " + TransactionId.get());

			System.out.println(Transactionresult);
			StatuQuerybyTransitionId(Transactionresult);

		} else {

			WaitforElementtobeclickable(xml.getlocator("//locators/RadioButton"));
			Clickon(getwebelement(xml.getlocator("//locators/RadioButton")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Radio button");
			Select(getwebelement(xml.getlocator("//locators/UserAction")), "Activate");
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the User Action");
			Clickon(getwebelement(xml.getlocator("//locators/GoButon")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Go button");
			SendKeys(getwebelement(xml.getlocator("//locators/CustomerName")), Inputdata[i][7].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the Customer name Field: " + Inputdata[i][7].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber")), Inputdata[i][9].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the building number Field: " + Inputdata[i][9].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/city")), Inputdata[i][11].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the City Field: " + Inputdata[i][11].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/BuildingName")), Inputdata[i][8].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the Building name field: " + Inputdata[i][8].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/Street")), Inputdata[i][10].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the Building name field: " + Inputdata[i][8].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/PostCode")), Inputdata[i][12].toString());
			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Fill the Post Code: " + Inputdata[i][12].toString());
			WaitforElementtobeclickable(xml.getlocator("//locators/SubmitButton"));
			Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Submit button");
			Thread.sleep(1000);

			String Transactionresult = Gettext(getwebelement(xml.getlocator("//locators/TransactionResult")));
			TransactionId.set(Transactionresult);
			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : " + TransactionId.get());
			System.out.println(Transactionresult);
			StatuQuerybyTransitionId(Transactionresult);

		}
	}

	public void validate() throws InterruptedException, DocumentException {
		if (getwebelement(xml.getlocator("//locators/mainError")).isDisplayed()) {
			String errormsg = getwebelement(xml.getlocator("//locators/mainError")).getText();

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
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Enter the Transaction ID: " + TransactionID);
		// WaitforElementtobeclickable(xml.getlocator("//locators/StatusQuerySearchbutton"));
		Thread.sleep(10000);
		Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on submit button");

		Thread.sleep(5000);

//>>>>>>>>>>>>GRID DATA CONCEPT 
		if (isElementPresent(xml.getlocator("//locators/GriddataforTX"))) {

			String transType = Gettext(getwebelement(xml.getlocator("//locators/TransactionType")));

			ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Id is : " + transType);
			System.out.println(
					"*****************************************************************************" + transType);
			implicitwait(20);

			String transStatus = Gettext(getwebelement(xml.getlocator("//locators/StatusQueryTransactionStatus")));
			System.out.println(
					"*****************************************************************************" + transStatus);

			if (transStatus.contains("Firm order commitment")) {
				GreenLog("Status Query has been passed with Transaction Status Firm order commitment");

				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status is :Firm order commitment");
			} else if (transStatus.contains("Porting initiated")) {
				GreenLog("Status Query has been passed with Transaction Status Porting initiated");

				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status is :Porting initiated");
			} else {

				boolean flag3 = false;

				String date3 = null;
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");

				String date1 = dateFormat.format(new Date(System.currentTimeMillis()));
				Date dd = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
				String date2 = dateFormat.format(dd);

				do {

					Thread.sleep(2000);
					Pagerefresh();
					Thread.sleep(2000);
					flag3 = isElementPresent(xml.getlocator("//locators/QuesryForCompleted"));

					date3 = dateFormat.format(new Date(System.currentTimeMillis()));
					System.out.println(date3);
					System.out.println(date2);
					Thread.sleep(1000);
					Pagerefresh();

					if (date3.trim().equals(date2.trim()) && flag3 == false) {
						System.out.println(
								"System is waiting for 10 minutes to change status, But still status in not changed!");
						RedLog2("waited 10 minute to change Status from InProgress to Complted!");
						Assert.fail("waited 10 minute to change Status from InProgress to Complted!");

						break;
					}
				} while (flag3 == false);
				// waitandForElementDisplayed("//locators/StatusQueryTransactionStatus");
				try {
					String transStatus1 = Gettext(
							getwebelement(xml.getlocator("//locators/StatusQueryTransactionStatus")));
					Thread.sleep(5000);
					if (transStatus1.contains("Completed")) {

						GreenLog("Status Query has been passed with Transaction Status Completed");

						ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Transaction Status is :Completed");

					} else if (transStatus1.contains("In Progress")) {
						Assert.fail("System is taking longer time to change Status from InProgress to Complted!");
					} else if (transStatus1.contains("Firm order commitment")) {
						GreenLog("Status Query has been passed with Transaction Status Firm order commitment");

						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Status is :Firm order commitment");
					} else if (transStatus1.contains("Porting initiated")) {
						GreenLog("Status Query has been passed with Transaction Status Porting initiated");

						ExtentTestManager.getTest().log(LogStatus.PASS,
								" Step: Transaction Status is :Porting initiated");
					}

					else {
						RedLog("it seems like there is no any Transaction Status value which contained Completed or In Process or there is no any record found for this transaction ID");
						Assert.fail();
					}
				} catch (StaleElementReferenceException e) {

				}
			}
		}
	}
//		implicitwait(20);  
//		String Norecord = Gettext(getwebelement(xml.getlocator("//locators/NoRecordFound")));
////		
//		if (Norecord.contains("No records found!")) {
//			implicitwait(20);
//			Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
//			Clickon(getwebelement(xml.getlocator("//locators/StatusQuerySearchbutton")));
//		} 
//	
//		else{
//			RedLog("System does not have search result with Transaction ID - check your Transaction ID and field Details ");
//		}					
//	}

	public void DownloadExcel(String Filaname) throws InterruptedException, DocumentException {
		String downloadPath = null;
		try {
			downloadPath = GetExcelpath("Excel");
		} catch (Exception e) {

			e.printStackTrace();
		}
		// Assert.assertTrue(isFileDownloaded(downloadPath, Filaname), "Failed to
		// download Expected document");
		Clickon(getwebelement(xml.getlocator("//locators/DownloadExcel")));
		GreenLog("Step: Excel has been downloded : Successfully");

	}

	public void PortIn(Object[][] Inputdata) throws Exception {

		for (int i = 0; i < Inputdata.length; i++) {

			if (Inputdata[i][0].toString().trim().equals("Port-In")) {
				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));
				log("click on managing porting");
				Thread.sleep(1000);

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
				SendKeys(getwebelement(xml.getlocator("//locators/customernamepost")),
						Inputdata[i][7].toString().trim());
				implicitwait(5);

				SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumber2")),
						Inputdata[i][9].toString().trim());
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
				SendKeys(getwebelement(xml.getlocator("//locators/CurrentProvider")),
						Inputdata[i][33].toString().trim());
				implicitwait(5);

				javaexecutotSendKeys(Inputdata[i][34].toString(),
						getwebelement(xml.getlocator("//locators/portindate")));
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
				log("send the transaction ID: " + TransactionId.get());

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
					Assert.assertTrue(CurrentStatus.get().contains("Validation In Progress"),
							"Current status is not valid");
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
				log("get the child status: " + ChildCurrentStat.get());
				Assert.assertEquals(ChildCurrentStat.get(), "Completed", "Child status is " + ChildCurrentStat.get());

				String ParentCurrentStatus = Gettext(getwebelement(xml.getlocator("//locators/ParentStatus")));
				ParentCurrentStat.set(ParentCurrentStatus);

				log("ParentCurrentStatus shown");
//				//--------------------------------------------------------------------

				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));

				log("now again check the parent status");
				Thread.sleep(1000);

				// Thread.sleep(1000);

				WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequesthover")));
				Clickon(getwebelement(xml.getlocator("//locators/updateportinRequesthover")));

				log("click on the update port in request");
				// -----------
				SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
						ParentCurrentStat.get().toString().trim().toLowerCase());
				Thread.sleep(5000);
				log("send the transaction ID: " + ParentCurrentStat.get());
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
					Assert.assertTrue(CurrentStatus.get().contains("Validation In Progress"),
							"Current status is not valid");
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
				log("get the child status: " + ChildCurrentStat.get());
				Assert.assertEquals(ChildCurrentStat.get(), "Completed", "Child status is " + ChildCurrentStat.get());

				String ParentCurrentStatus1 = Gettext(getwebelement(xml.getlocator("//locators/ParentStatus")));
				ParentCurrentStat.set(ParentCurrentStatus1);

				log("ParentCurrentStatus shown: " + ParentCurrentStat.get());
//				//--------------------------------------------------------------------

				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));
				log("now again check the parent status");
				Thread.sleep(1000);

				// Thread.sleep(1000);

				WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequesthover")));
				Clickon(getwebelement(xml.getlocator("//locators/updateportinRequesthover")));
				log("click on the update port in request");

				SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
						ParentCurrentStat.get().toString().trim().toLowerCase());
				Thread.sleep(5000);
				log("send the transaction ID: " + ParentCurrentStat.get());

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
					Assert.assertTrue(CurrentStatus.get().contains("Validation In Progress"),
							"Current status is not valid");
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

				log("ParentCurrentStatus shown: " + ParentCurrentStat.get());

				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));
				log("now again check the parent status");
				Thread.sleep(1000);
				WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequesthover")));
				Clickon(getwebelement(xml.getlocator("//locators/updateportinRequesthover")));
				log("click on the update port in request");
				SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
						ParentCurrentStat.get().toString().trim().toLowerCase());
				log("send the transaction ID: " + ParentCurrentStat.get());

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
					Assert.assertTrue(CurrentStatus.get().contains("Validation In Progress"),
							"Current status is not valid");
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

				String date3 = null;
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");

				String date1 = dateFormat.format(new Date(System.currentTimeMillis()));
				Date dd = new Date(System.currentTimeMillis() + 20 * 60 * 1000);
				String date2 = dateFormat.format(dd);

				do {

					Thread.sleep(2000);
					// TransactionStatus1 =
					// Gettext(getwebelement(xml.getlocator("//locators/TransactionStatus")));
					Pagerefresh();
					Pagerefresh();
					flag3 = isElementPresent(xml.getlocator("//locators/ChildStatus"));
					ExtentTestManager.getTest().log(LogStatus.PASS,
							" Step: ChildCurrentStatus is : " + ChildCurrentStat.get());
					log("if child Current Status shown than jump to the next step otherwise click on the refresh button");
					WaitforElementtobeclickable((xml.getlocator("//locators/SubmitButton")));
					Clickon(getwebelement(xml.getlocator("//locators/SubmitButton")));
					date3 = dateFormat.format(new Date(System.currentTimeMillis()));
					System.out.println(date3);
					System.out.println(date2);
					Thread.sleep(1000);
					Pagerefresh();
					if (date3.trim().equals(date2.trim())) {
						System.out.println(
								"System is waiting for 20 minutes to change status, But still status in not changed!");
						Assert.fail("System is taking longer time to change Status from InProgress to Complted!");
						break;
					}
				}

				while (flag3 == false);

				Thread.sleep(5000);
				log("childcurrent status id successfully submited");
				Assert.assertEquals(ChildCurrentStat.get(), "Completed", "Child status is " + ChildCurrentStat.get());

				String ParentCurrentStatus13 = Gettext(getwebelement(xml.getlocator("//locators/ParentStatus")));
				ParentCurrentStat.set(ParentCurrentStatus13);
				log("ParentCurrentStatus shown: " + ParentCurrentStat.get());

				WaitforElementtobeclickable(xml.getlocator("//locators/ManagePorting"));
				Moveon(getwebelement(xml.getlocator("//locators/ManagePorting")));
				log("now again check the parent status");
				Thread.sleep(1000);

				WaitforElementtobeclickable((xml.getlocator("//locators/updateportinRequesthover")));
				Clickon(getwebelement(xml.getlocator("//locators/updateportinRequesthover")));
				log("click on the update port in request");
				SendKeys(getwebelement(xml.getlocator("//locators/statusQueryTransactionID")),
						ParentCurrentStat.get().toString().trim().toLowerCase());
				Thread.sleep(5000);
				log("send the transaction ID: " + ParentCurrentStat.get());
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
			} else {

			}
		}
		//
	}

	

//	public void MergerdNumber(Object[][] Inputdata) throws InterruptedException, Exception {
//		for (int i = 0; i < Inputdata.length; i++) {
//			Thread.sleep(5000);
//
//			switchtoframe("fmMenu");
//			WaitforElementtobeclickable((xml.getlocator("//locators/IECountryButton")));
//			Clickon(getwebelement(xml.getlocator("//locators/IECountryButton")));
//			log("Click on the change country name");
//			switchtodefault();
//			switchtoframe("fmMain");
//			log("Switch to the default frame");
//			waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
//			Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
//			log("Select the country name is: " + Inputdata[i][2]);
//			WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
//			Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
//
//			log("Merger Number starting");
//			Thread.sleep(5000);
//			switchtoframe("fmMenu");
//			WaitforElementtobeclickable((xml.getlocator("//locators/IEMergeButton")));
//			Clickon(getwebelement(xml.getlocator("//locators/IEMergeButton")));
//			log("click on the merge number range");
//			switchtodefault();
//			switchtoframe("fmMain");
//
//			Select(getwebelement(xml.getlocator("//locators/IEAvailability")), Inputdata[i][37].toString());
//
//			SendKeys(getwebelement(xml.getlocator("//locators/IEAreaCode")), Inputdata[i][5].toString());
//			SendKeys(getwebelement(xml.getlocator("//locators/IEAreaCodeEXtension")), Inputdata[i][36].toString());
//			SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeStart")), Inputdata[i][14].toString());
//			SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeEnd")), Inputdata[i][15].toString());
//			Select(getwebelement(xml.getlocator("//locators/IEGeoNumber")), Inputdata[i][38].toString());
//			if (Inputdata[i][37].toString().contains("Wholesale")) {
//				Select(getwebelement(xml.getlocator("//locators/IEDropBlockSize")), Inputdata[i][22].toString());
//			}
//			Select(getwebelement(xml.getlocator("//locators/IEPageSize")), Inputdata[i][39].toString());
//			WaitforElementtobeclickable((xml.getlocator("//locators/IESubmitButton")));
//			Clickon(getwebelement(xml.getlocator("//locators/IESubmitButton")));
//			if (Inputdata[i][40].toString().contains("All")) {
//				WaitforElementtobeclickable(xml.getlocator("//locators/IEAllCheckbox"));
//				Clickon(getwebelement(xml.getlocator("//locators/IEAllCheckbox")));
//			}
//
//			else {
//				int rangeStart = Integer.parseInt(Inputdata[i][40].toString());
//				int rangeEnd = Integer.parseInt(Inputdata[i][41].toString());
//				for (int k = rangeStart; k <= rangeEnd; k++) {
//
//					Clickon(getwebelement(xml.getlocator("//locators/IERandomCheckbox")
//							.replace("Rowindex", String.valueOf(k + 1)).replace("index", String.valueOf(k))));
//					log("row work complete");
//				}
//
//			}
//			WaitforElementtobeclickable((xml.getlocator("//locators/IEmergeButton")));
//			Clickon(getwebelement(xml.getlocator("//locators/IEmergeButton")));
//
//		}
//
//	}
//	
//	public void SplitNumber(Object[][] Inputdata) throws InterruptedException, Exception {
//		for (int i = 0; i < Inputdata.length; i++) {
//		switchtoframe("fmMenu");
//		WaitforElementtobeclickable((xml.getlocator("//locators/IESplitNumberButton")));
//		Clickon(getwebelement(xml.getlocator("//locators/IESplitNumberButton")));
//		log("Click on the Sblit Nubmer Tab");
//		SendKeys(getwebelement(xml.getlocator("//locators/IESplitNumber")), Inputdata[i][42].toString());
//		WaitforElementtobeclickable((xml.getlocator("//locators/IESplitSearchButton")));
//		Clickon(getwebelement(xml.getlocator("//locators/IESplitSearchButton")));
//		
//		}
//		
//		
//			
//			}
//	public void ReservationOrCancelReservation(Object[][] Inputdata) throws InterruptedException, Exception {
//		for (int i = 0; i < Inputdata.length; i++)
//		{
//      if(Inputdata[i][20].toString().equals("Reservation Number")|| Inputdata[i][20].toString().equals("Cancel Reservation"))
//      {
//    	  UserGroupforPhnNoMgn(Inputdata);
//			Thread.sleep(3000);
//			switchtodefault();
//			switchtoframe("fmMenu");
//			WaitforElementtobeclickable(xml.getlocator("//locators/DirectAllocation"));
//			Clickon(getwebelement(xml.getlocator("//locators/DirectAllocation"))); 
//			log("Click on Direct Allocation");
//			switchtodefault();
//			switchtoframe("fmMain");
//			if(isElementPresent(xml.getlocator("//locators/AreaCodePrefix")))
//			{
//			waitandForElementDisplayed(xml.getlocator("//locators/AreaCodePrefix"));
//			SendKeys(getwebelement(xml.getlocator("//locators/AreaCodePrefix")), Inputdata[i][5].toString());
//			log("Enter the Area Code Prefix");
//			waitandForElementDisplayed(xml.getlocator("//locators/AreaCodeExt"));
//			SendKeys(getwebelement(xml.getlocator("//locators/AreaCodeExt")), Inputdata[i][45].toString());
//			log("Enter the Area Code Extention");
//			waitandForElementDisplayed(xml.getlocator("//locators/RangeFrom"));
//			SendKeys(getwebelement(xml.getlocator("//locators/RangeFrom")), Inputdata[i][47].toString());
//			log("Enter the Range start number");
//			
//			waitandForElementDisplayed(xml.getlocator("//locators/retandtransferRangeto"));
//			SendKeys(getwebelement(xml.getlocator("//locators/retandtransferRangeto")), Inputdata[i][48].toString());
//			log("Enter the Range to number");
//			
//			WaitforElementtobeclickable(xml.getlocator("//locators/Submitbtn"));
//			Clickon(getwebelement(xml.getlocator("//locators/Submitbtn"))); 
//			log("Click on Submit button");
//			
//			}
//			if(isElementPresent(xml.getlocator("//locators/GriddataNMTS")))
//			{
//
//			Thread.sleep(3000);	
//			switchtodefault();
//			switchtoframe("fmMain");
//			Thread.sleep(5000);		
//				
//				GreenLog("Data are displaying with search creteria!!");			
//				WaitforElementtobeclickable(xml.getlocator("//locators/checkbox3"));
//				Clickon(getwebelement(xml.getlocator("//locators/checkbox3"))); 
//				log("Click on Allocation checkbox");	
//				waitandForElementDisplayed(xml.getlocator("//locators/Selectmark"));
//				Clickon(getwebelement(xml.getlocator("//locators/Selectmark"))); 
//				log("Click on Select mark");			
//				Thread.sleep(3000);
//				WaitforElementtobeclickable(xml.getlocator("//locators/AllocationCheckboxagain"));
//				Clickon(getwebelement(xml.getlocator("//locators/AllocationCheckboxagain"))); 
//				log("Click on Allocation checkbox again");
//				Thread.sleep(3000);
//				WaitforElementtobeclickable(xml.getlocator("//locators/submitSelectedRange"));
//				Clickon(getwebelement(xml.getlocator("//locators/submitSelectedRange"))); 
//				log("Click on submit Selected Range button");		
//				switchtodefault();
//				switchtoframe("fmMain");			
//				waitandForElementDisplayed(xml.getlocator("//locators/ocn"));
//				SendKeys(getwebelement(xml.getlocator("//locators/ocn")), Inputdata[i][51].toString());
//				log("Enter the OCN noumber");			
//				WaitforElementtobeclickable(xml.getlocator("//locators/Submitocn"));
//				Clickon(getwebelement(xml.getlocator("//locators/Submitocn"))); 
//				log("Click on submit Search button");
//			
//				Thread.sleep(10000);
//			}
//				else {
//
//					RedLog("please check the correct data in Excel sheet for - AreaCode, AreacodeExt and Rangefrom:(");
//					}
//			Thread.sleep(7000);
//			if(isElementPresent(xml.getlocator("//locators/GriddataforOCN")))
//			{
//				Thread.sleep(10000);
//				WaitforElementtobeclickable(xml.getlocator("//locators/doubleClickonOCN"));
//				DoubleClick(getwebelement(xml.getlocator("//locators/doubleClickonOCN")));
//				log("Double click on OCN number");
//		
//		}
//				else
//				{
//					RedLog("it seems like OCN no is not correct in Excel sheet please check it ");
//				}
//			Thread.sleep(10000);
//				switchtodefault();
//				Thread.sleep(10000);
//				switchtoframe("fmMain");
//				
//				waitandForElementDisplayed(xml.getlocator("//locators/selectDesc"));
//				Select(getwebelement(xml.getlocator("//locators/selectDesc")), Inputdata[i][52].toString());
//				log("Select the Service Destination");			
//				waitandForElementDisplayed(xml.getlocator("//locators/EmailNotification"));
//				Select(getwebelement(xml.getlocator("//locators/EmailNotification")), Inputdata[i][53].toString());
//				log("Select the Email Notification");				
//				waitandForElementDisplayed(xml.getlocator("//locators/ServicePro"));
//				SendKeys(getwebelement(xml.getlocator("//locators/ServicePro")), Inputdata[i][3].toString());
//				log("Enter the Service Profile");			
//				waitandForElementDisplayed(xml.getlocator("//locators/TransactionreferenceNo"));
//				SendKeys(getwebelement(xml.getlocator("//locators/TransactionreferenceNo")), Inputdata[i][55].toString());
//				log("Enter the Transaction reference Number");
//				Thread.sleep(5000);
//				//waitandForElementDisplayed(xml.getlocator("//locators/CheckboxforAllocation"));
//				Clickon(getwebelement(xml.getlocator("//locators/CheckboxforAllocation"))); 
//				log("Click mark Checkbox");
//				WaitforElementtobeclickable(xml.getlocator("//locators/SubmitOrderBtn"));
//				Clickon(getwebelement(xml.getlocator("//locators/SubmitOrderBtn"))); 
//				log("Click on Submit Order Button");
//				Thread.sleep(3000);
//				//verify the Reservation status as : Reservation 
//	//			GET THE RESERVATION STATUS**********************************************************************************************//
//				String CurrentNumberStatus = GetText(getwebelement(xml.getlocator("//locators/verifyStatus")));
//				log(CurrentNumberStatus);
//				Thread.sleep(3000);
//			
//				
//				if(Inputdata[i][20].toString().equals("Cancel Reservation"))
//						{
//					Thread.sleep(1000);
//					WaitforElementtobeclickable(xml.getlocator("//locators/CancelReserveCheckbox"));
//					Clickon(getwebelement(xml.getlocator("//locators/CancelReserveCheckbox"))); 
//					log("Click on Submit mark's checkbox");
//				
//					Thread.sleep(3000);
//					Clickon(getwebelement(xml.getlocator("//locators/SelectedAllLink"))); 
//					log("Click on select mark's checkbox");
//					Thread.sleep(4000);
//					WaitforElementtobeclickable(xml.getlocator("//locators/SelectedRangeCheckrange"));
//					Clickon(getwebelement(xml.getlocator("//locators/SelectedRangeCheckrange"))); 
//					
//					log("Click on Selected Range Checkrange mark's checkbox");
//					Thread.sleep(1000);
//					waitandForElementDisplayed(xml.getlocator("//locators/selectDecision"));
//					Select(getwebelement(xml.getlocator("//locators/selectDecision")), Inputdata[i][56].toString());
//					log("select the Decision as Cancel Reservation");
//					Thread.sleep(3000);
//					waitandForElementDisplayed(xml.getlocator("//locators/RejectReason"));
//					Select(getwebelement(xml.getlocator("//locators/RejectReason")), Inputdata[i][70].toString());
//					log("select the Reject Reason");
//					
//					Thread.sleep(4000);
//					waitandForElementDisplayed(xml.getlocator("//locators/MRN"));
//					SendKeys(getwebelement(xml.getlocator("//locators/MRN")), Inputdata[i][60].toString());
//					log("Enter the MRN number");
//					WaitforElementtobeclickable(xml.getlocator("//locators/SubmitDecision"));
//					Clickon(getwebelement(xml.getlocator("//locators/SubmitDecision"))); 
//					log("Click on Submit Decision button");
//					Thread.sleep(3000);
//					String CurrentNumberStatus1 = GetText(getwebelement(xml.getlocator("//locators/verifyStatus")));
//					log(CurrentNumberStatus1);
//						}
//				else {
//			    	  
//		           }
//      }
//      
//				else if(Inputdata[i][20].toString().equals("Free to ActivatewithAddress") || Inputdata[i][20].toString().equals("Reserve to ActivatewithAddress") )
//				{
//					Thread.sleep(3000);
//					WaitforElementtobeclickable(xml.getlocator("//locators/CancelReserveCheckbox"));
//					Clickon(getwebelement(xml.getlocator("//locators/CancelReserveCheckbox"))); 
//					log("Click on Submit mark's checkbox");
//					Thread.sleep(3000);
//					Clickon(getwebelement(xml.getlocator("//locators/SelectedAllLink"))); 
//					log("Click on select mark's checkbox");
//					Thread.sleep(4000);
//					WaitforElementtobeclickable(xml.getlocator("//locators/SelectedRangeCheckrange"));
//					Clickon(getwebelement(xml.getlocator("//locators/SelectedRangeCheckrange"))); 					
//					log("Click on Selected Range Checkrange mark's checkbox");
//					Thread.sleep(3000);
//					waitandForElementDisplayed(xml.getlocator("//locators/selectDecision"));
//					Select(getwebelement(xml.getlocator("//locators/selectDecision")), Inputdata[i][56].toString());
//					log("select the Processing Decision ");
//					Thread.sleep(3000);
//					SendKeys(getwebelement(xml.getlocator("//locators/ContactNo")), Inputdata[i][66].toString());
//					log("Enter the End Contact Number");
//					Thread.sleep(2000);
//					waitandForElementDisplayed(xml.getlocator("//locators/ContractSource"));
//					Select(getwebelement(xml.getlocator("//locators/ContractSource")), Inputdata[i][61].toString());
//					log("select the Contract Source ");
//					Thread.sleep(2000);
//					waitandForElementDisplayed(xml.getlocator("//locators/MRN"));
//					SendKeys(getwebelement(xml.getlocator("//locators/MRN")), Inputdata[i][60].toString());
//					log("Enter the MRN number");					
//					waitandForElementDisplayed(xml.getlocator("//locators/EndCustName"));
//					SendKeys(getwebelement(xml.getlocator("//locators/EndCustName")), Inputdata[i][7].toString());
//					log("Enter the End Customer Name");
//					Thread.sleep(3000);					
//					SendKeys(getwebelement(xml.getlocator("//locators/BuildingNameNMTS")), Inputdata[i][8].toString());
//					log("Enter the Building Name");
//					Thread.sleep(2000);
//					SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumberNMTS")), Inputdata[i][9].toString());
//					log("Enter the Building Number");
//					Thread.sleep(2000);				
//					SendKeys(getwebelement(xml.getlocator("//locators/StreetName")), Inputdata[i][10].toString());
//					log("Enter the Street Name");
//					Thread.sleep(2000);				
//					SendKeys(getwebelement(xml.getlocator("//locators/Town")), Inputdata[i][62].toString());
//					log("Enter the Town");
//					Thread.sleep(5000);			
//					SendKeys(getwebelement(xml.getlocator("//locators/PostCodeNMTS")), Inputdata[i][12].toString());
//					log("Enter the PostCode");					
//					WaitforElementtobeclickable(xml.getlocator("//locators/SubmitDecision"));
//					Clickon(getwebelement(xml.getlocator("//locators/SubmitDecision"))); 
//					log("Click on Submit Decision button");
//					Thread.sleep(3000);	
//					if(Inputdata[i][20].toString().equals("Reserve to ActivatewithAddress"))
//					{
//						// It will perform Allocated No to Activate 
//						FreeToActivateOnNMTS(Inputdata);
//					}
//					else {
//						AcceptJavaScriptMethod();
//					}
//				}			
//	
//				else {
//					RedLog("it seems like :( there is issue with your Excel data sheet . please correct it ..");
//				}
//		}}
//	public void FreeToActivateOnNMTS(Object[][] Inputdata) throws InterruptedException, Exception {
//		for (int i = 0; i < Inputdata.length; i++)
//		{
//			if(Inputdata[i][20].toString().equals("Free to ActivatewithAddress"))
//			{
//				ReservationOrCancelReservation(Inputdata);
//			}
//			else {
//		Thread.sleep(2000);
//		switchtodefault();
//		switchtoframe("fmMenu");
//		//click to activate 
//		waitandForElementDisplayed(xml.getlocator("//locators/ActivateNumbertab"));
//		Clickon(getwebelement(xml.getlocator("//locators/ActivateNumbertab"))); 
//		log("Click on Activate Number tab");
//	
//		switchtodefault();
//		switchtoframe("fmMain");
//		Thread.sleep(2000);
//			Select(getwebelement(xml.getlocator("//locators/madeByNMTS")), Inputdata[i][57].toString());
//						log("select the Made By Name:-" + Inputdata[i][57].toString());
//						Select(getwebelement(xml.getlocator("//locators/DeactivationAvail")), Inputdata[i][37].toString());
//						log("select the Availability:-" + Inputdata[i][37].toString());
//						Select(getwebelement(xml.getlocator("//locators/FilterBy")), Inputdata[i][58].toString());
//						log("select the Filter By:-" + Inputdata[i][58].toString());
//						Thread.sleep(1000); 
//			Select(getwebelement(xml.getlocator("//locators/matching")), Inputdata[i][59].toString());
//						log("select the Matcing:-" + Inputdata[i][59].toString());
//						SendKeys(getwebelement(xml.getlocator("//locators/FilterText")), Inputdata[i][42].toString());
//						log("Enter the Number" + Inputdata[i][42].toString());
//						WaitforElementtobeclickable((xml.getlocator("//locators/SubmitDeactivate")));
//						Clickon(getwebelement(xml.getlocator("//locators/SubmitDeactivate")));
//						log("click on the submit button");
//						Thread.sleep(10000);
//						if (isElementPresent(xml.getlocator("//locators/errormsgnorecord"))) 
//						{
//							RedLog("No record found please enter the valid data number");
//						} 
//						else {
//							Thread.sleep(5000);
//							DoubleClick(getwebelement(xml.getlocator("//locators/OrderId")));
//							log("Doubleclick on the order id button");
//							WaitforElementtobeclickable((xml.getlocator("//locators/check1")));
//							Clickon(getwebelement(xml.getlocator("//locators/check1")));
//							log("Click on the checkbox");
//							WaitforElementtobeclickable((xml.getlocator("//locators/SelectedAllLink")));
//							Clickon(getwebelement(xml.getlocator("//locators/SelectedAllLink")));
//							log("Click on the Selected All");
//							waitandForElementDisplayed(xml.getlocator("//locators/AllocationCheckboxagain"));
//							Clickon(getwebelement(xml.getlocator("//locators/AllocationCheckboxagain")));
//							log("Click on Allocation checkbox again");	
//							Thread.sleep(2000);
//							waitandForElementDisplayed(xml.getlocator("//locators/ActivateNumnerbtn"));
//							Clickon(getwebelement(xml.getlocator("//locators/ActivateNumnerbtn")));
//							log("Click on Activate Numner button");
//		
//						}
//						}}
//		}
//	public void ReservetoActivate(Object[][] Inputdata) throws InterruptedException, Exception {
//		for (int i = 0; i < Inputdata.length; i++) 
//		{
//			UserGroupforPhnNoMgn(Inputdata);
//			switchtodefault();
//			switchtoframe("fmMenu");		
//			WaitforElementtobeclickable((xml.getlocator("//locators/ProcessReservationtab")));
//			Clickon(getwebelement(xml.getlocator("//locators/ProcessReservationtab")));
//			log("Click on the Process Reservation tab");
//			switchtodefault();
//			switchtoframe("fmMain");
//			Select(getwebelement(xml.getlocator("//locators/madeByNMTS")), Inputdata[i][57].toString());
//			log("select the Made By Name:-" + Inputdata[i][57].toString());
//			Select(getwebelement(xml.getlocator("//locators/DeactivationAvail")), Inputdata[i][37].toString());
//			log("select the Availability:-" + Inputdata[i][37].toString());
//			Select(getwebelement(xml.getlocator("//locators/FilterBy")), Inputdata[i][58].toString());
//			log("select the Filter By:-" + Inputdata[i][58].toString());
//			Thread.sleep(1000); 
//       Select(getwebelement(xml.getlocator("//locators/matching")), Inputdata[i][59].toString());
//			log("select the Matcing:-" + Inputdata[i][59].toString());
//			SendKeys(getwebelement(xml.getlocator("//locators/FilterText")), Inputdata[i][42].toString());
//			log("Enter the Number" + Inputdata[i][42].toString());
//			WaitforElementtobeclickable((xml.getlocator("//locators/SubmitDeactivate")));
//			Clickon(getwebelement(xml.getlocator("//locators/SubmitDeactivate")));
//			log("click on the submit button");
//			Thread.sleep(10000);
//			if (isElementPresent(xml.getlocator("//locators/errormsgnorecord"))) 
//			{
//				RedLog("No record found please enter the valid data number");
//			} 
//			else {
//				Thread.sleep(10000);
//				if(isElementPresent(xml.getlocator("//locators/CheckReservestatus")))
//				{
//					//.
//					waitandForElementDisplayed(xml.getlocator("//locators/OrderId"));
//					DoubleClick(getwebelement(xml.getlocator("//locators/OrderId")));
//					log("Doubleclick on the order id button");
//					ReservationOrCancelReservation(Inputdata);
//					
//					
//				}
//				else {
//					RedLog("Please currect the Number from the Excel sheet . it seems like number iss not in Reserve status");
//				}
//				
//		
//		}
//		}}
//	
//	public void PortInNMTS(Object[][] Inputdata) throws InterruptedException, Exception {
//		for (int i = 0; i < Inputdata.length; i++) 
//		{
//			UserGroupforPhnNoMgn(Inputdata);
//			Thread.sleep(3000);
//			switchtodefault();
//			switchtoframe("fmMenu");
//			WaitforElementtobeclickable((xml.getlocator("//locators/PortIntab")));
//			Clickon(getwebelement(xml.getlocator("//locators/PortIntab")));
//			log("Click on the PortIn Numbers tab");
//			switchtodefault();
//			switchtoframe("fmMain");
//		Select(getwebelement(xml.getlocator("//locators/Switch")), Inputdata[i][44].toString());
//		log("select the Switch in Adding Number:-" + Inputdata[i][44].toString());
//		if (Inputdata[i][2].toString().contains("BE"))
//		{
//			log("country is BE so select the areacode from the table");
//			WaitforElementtobeclickable((xml.getlocator("//locators/AreacodePrefix")));
//			Clickon(getwebelement(xml.getlocator("//locators/AreacodePrefix")));
//			log("Click on the AreacodePrefix button");
//			Thread.sleep(3000);
//			switchtofram(getwebelement(xml.getlocator("//locators/iframe")));
//			Clickon(getwebelement(
//					xml.getlocator("//locators/AreaInformationWin").replace("index", Inputdata[i][5].toString())));
//			log("Area the area code prefix:-" + Inputdata[i][5].toString());
//			switchtodefault();
//			switchtoframe("fmMain");
//		} else {
//			SendKeys(getwebelement(xml.getlocator("//locators/Areacodeother")), Inputdata[i][5].toString());
//			log("Enter the area code/prfix:-" + Inputdata[i][5].toString());
//		}
//		SendKeys(getwebelement(xml.getlocator("//locators/IEAreaCode")), Inputdata[i][36].toString());
//		log("Enter the area code extension:-" + Inputdata[i][36].toString());
//		SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeStart")), Inputdata[i][14].toString());
//		log("Enter the range start value:-" + Inputdata[i][14].toString());
//		SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeEnd")), Inputdata[i][15].toString());
//		log("Enter the range end value:-" + Inputdata[i][15].toString());
//		Select(getwebelement(xml.getlocator("//locators/AddnumGeo")), Inputdata[i][38].toString());
//		log("Select the Geo or non-Geo:-" + Inputdata[i][38].toString());
//		Select(getwebelement(xml.getlocator("//locators/AddnumCtg")), Inputdata[i][46].toString());
//		log("Select the Category:-" + Inputdata[i][46].toString());
//		Select(getwebelement(xml.getlocator("//locators/AddingAvailability")), Inputdata[i][37].toString());
//		log("Select the Availability:-" + Inputdata[i][37].toString());	
//		SendKeys(getwebelement(xml.getlocator("//locators/NRN")), Inputdata[i][60].toString());
//		log("Enter the NRN VALUE:-" + Inputdata[i][60].toString());	
//		Thread.sleep(3000);
//		Select(getwebelement(xml.getlocator("//locators/SelectRangeHolder")), Inputdata[i][63].toString());
//		log("Select the SelectRangeHolder:-" + Inputdata[i][63].toString());		
//		WaitforElementtobeclickable((xml.getlocator("//locators/AddingSubmit")));
//		Clickon(getwebelement(xml.getlocator("//locators/AddingSubmit")));
//		log("Click on the submit button");
//		
//		//>>>>>>>>>>>>>>>>>GetNMTSStatus	
//	
//	//checking the ocn for Port- IN
//		Thread.sleep(5000);
//		switchtodefault();
//		switchtoframe("fmMain");			
//		waitandForElementDisplayed(xml.getlocator("//locators/ocn"));
//		SendKeys(getwebelement(xml.getlocator("//locators/ocn")), Inputdata[i][51].toString());
//		log("Enter the OCN noumber");			
//		WaitforElementtobeclickable(xml.getlocator("//locators/Submitocn"));
//		Clickon(getwebelement(xml.getlocator("//locators/Submitocn"))); 
//		log("Click on submit Search button");
//		Thread.sleep(10000);
//	Thread.sleep(7000);
//	if(isElementPresent(xml.getlocator("//locators/GriddataforOCN")))
//	{
//		Thread.sleep(10000);
//		WaitforElementtobeclickable(xml.getlocator("//locators/doubleClickonOCN"));
//		DoubleClick(getwebelement(xml.getlocator("//locators/doubleClickonOCN")));
//		log("Double click on OCN number");
//
//}
//		else
//		{
//			RedLog("it seems like OCN no is not correct in Excel sheet please check it ");
//		}
//	Thread.sleep(10000);
//		switchtodefault();
//		Thread.sleep(10000);
//		switchtoframe("fmMain");	
//		waitandForElementDisplayed(xml.getlocator("//locators/selectDesc"));
//		Select(getwebelement(xml.getlocator("//locators/selectDesc")), Inputdata[i][52].toString());
//		log("Select the Service Destination");			
//		waitandForElementDisplayed(xml.getlocator("//locators/EmailNotification"));
//		Select(getwebelement(xml.getlocator("//locators/EmailNotification")), Inputdata[i][53].toString());
//		log("Select the Email Notification");				
//		waitandForElementDisplayed(xml.getlocator("//locators/ServicePro"));
//		SendKeys(getwebelement(xml.getlocator("//locators/ServicePro")), Inputdata[i][3].toString());
//		log("Enter the Service Profile");			
//		waitandForElementDisplayed(xml.getlocator("//locators/TransactionreferenceNo"));
//		SendKeys(getwebelement(xml.getlocator("//locators/TransactionreferenceNo")), Inputdata[i][55].toString());
//		log("Enter the Transaction reference Number");
//		Thread.sleep(5000);
//		//waitandForElementDisplayed(xml.getlocator("//locators/CheckboxforAllocation"));
//		Clickon(getwebelement(xml.getlocator("//locators/CheckboxforAllocation"))); 
//		log("Click mark Checkbox");
//		WaitforElementtobeclickable(xml.getlocator("//locators/SubmitOrderBtn"));
//		Clickon(getwebelement(xml.getlocator("//locators/SubmitOrderBtn"))); 
//		log("Click on Submit Order Button");
////		try
////		{
////		Thread.sleep(4000);
////		String CurrentNumberStatus4 = GetText(getwebelement(xml.getlocator("//locators/verifyStatusforPortInone")));
////		Thread.sleep(4000);
////		String CurrentNumberStatus5 = GetText(getwebelement(xml.getlocator("//locators/verifyStatusforPortIntwo")));
////		log("Current NMTS Number Status is "+CurrentNumberStatus4+CurrentNumberStatus5);
////		}
////		catch(StaleElementReferenceException w)
////		{
////			
////		}
//
//			Thread.sleep(3000);
//			waitandForElementDisplayed(xml.getlocator("//locators/selectDecision"));
//			Select(getwebelement(xml.getlocator("//locators/selectDecision")), Inputdata[i][19].toString());
//			log("select the Processing Decision : Allocate for Port-in Activate");
//			Thread.sleep(3000);
//			//waitandForElementDisplayed(xml.getlocator("//locators/ContactNo"));
//			SendKeys(getwebelement(xml.getlocator("//locators/ContactNo")), Inputdata[i][66].toString());
//			log("Enter the End Contact Number");
//			Thread.sleep(2000);
//			waitandForElementDisplayed(xml.getlocator("//locators/ContractSource"));
//			Select(getwebelement(xml.getlocator("//locators/ContractSource")), Inputdata[i][61].toString());
//			log("select the Contract Source ");
//			Thread.sleep(2000);
//			waitandForElementDisplayed(xml.getlocator("//locators/EndCustName"));
//			SendKeys(getwebelement(xml.getlocator("//locators/EndCustName")), Inputdata[i][7].toString());
//			log("Enter the End Customer Name");
//			Thread.sleep(3000);		
//			SendKeys(getwebelement(xml.getlocator("//locators/BuildingNameNMTSForAllocate")), Inputdata[i][8].toString());
//			log("Enter the Building Name");
//			Thread.sleep(2000);
//			SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumberNMTSForAllocate")), Inputdata[i][9].toString());
//			log("Enter the Building Number");
//			Thread.sleep(2000);	
//			SendKeys(getwebelement(xml.getlocator("//locators/StreetName")), Inputdata[i][10].toString());
//			log("Enter the Street Name");
//			Thread.sleep(2000);	
//			SendKeys(getwebelement(xml.getlocator("//locators/Town")), Inputdata[i][62].toString());
//			log("Enter the Town");
//			Thread.sleep(5000);
//			SendKeys(getwebelement(xml.getlocator("//locators/PostCodeNMTSForAllocate")), Inputdata[i][12].toString());
//			log("Enter the PostCode");	
//			WaitforElementtobeclickable(xml.getlocator("//locators/SubmitDecision"));
//			Clickon(getwebelement(xml.getlocator("//locators/SubmitDecision"))); 
//			log("Click on Submit Decision button");
//			Thread.sleep(2000);	
//			AcceptJavaScriptMethod();
//	//After port-in Allocated go to view order tab to check the status and also trying to Port-in acativate
//
//			switchtodefault();
//			switchtoframe("fmMenu");
//			//click to activate 
//			waitandForElementDisplayed(xml.getlocator("//locators/ActivateNumbertab"));
//			Clickon(getwebelement(xml.getlocator("//locators/ActivateNumbertab"))); 
//			log("Click on Activate Number tab");
//			switchtodefault();
//			switchtoframe("fmMain");
//			Thread.sleep(2000);
//			Select(getwebelement(xml.getlocator("//locators/madeByNMTS")), Inputdata[i][57].toString());
//			log("select the Made By Name:-" + Inputdata[i][57].toString());
//			Select(getwebelement(xml.getlocator("//locators/DeactivationAvail")), Inputdata[i][37].toString());
//			log("select the Availability:-" + Inputdata[i][37].toString());
//			Select(getwebelement(xml.getlocator("//locators/FilterBy")), Inputdata[i][58].toString());
//			log("select the Filter By:-" + Inputdata[i][58].toString());
//			Thread.sleep(1000); 
//          Select(getwebelement(xml.getlocator("//locators/matching")), Inputdata[i][59].toString());
//			log("select the Matcing:-" + Inputdata[i][59].toString());
//			SendKeys(getwebelement(xml.getlocator("//locators/FilterText")), Inputdata[i][42].toString());
//			log("Enter the Number" + Inputdata[i][42].toString());
//			WaitforElementtobeclickable((xml.getlocator("//locators/SubmitDeactivate")));
//			Clickon(getwebelement(xml.getlocator("//locators/SubmitDeactivate")));
//			log("click on the submit button");
//			Thread.sleep(10000);
//			if (isElementPresent(xml.getlocator("//locators/errormsgnorecord"))) 
//			{
//				RedLog("No record found please enter the valid data number");
//			} else {
//				Thread.sleep(2000);
//				waitandForElementDisplayed(xml.getlocator("//locators/OrderId"));
//				DoubleClick(getwebelement(xml.getlocator("//locators/OrderId")));
//				log("Doubleclick on the order id button");
//				Thread.sleep(2000);
//				WaitforElementtobeclickable((xml.getlocator("//locators/ActivateNumnerbtn")));
//				Clickon(getwebelement(xml.getlocator("//locators/ActivateNumnerbtn")));
//				log("click on the Activate Numner button");
//			   AcceptJavaScriptMethod();
//			
////			String CurrentNumberStatus2 = GetText(getwebelement(xml.getlocator("//locators/verifyStatus")));
////			log(CurrentNumberStatus2);
//		}	}
//
//		}
//		
//	
//		public void UserGroupforSuperUser(Object[][] Inputdata) throws InterruptedException, Exception {
//			for (int i = 0; i < Inputdata.length; i++) {
//				Thread.sleep(3000);
//				switchtoframe("fmMenu");
//				String check = Gettext(getwebelement(xml.getlocator("//locators/UserName")));
//				if (check.equals("[SuperUser]")) {
//					log("User is Super User");
//					Thread.sleep(3000);
//					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryButton")));
//					Clickon(getwebelement(xml.getlocator("//locators/IECountryButton")));
//					log("Click on the change country name");
//					switchtodefault();
//					switchtoframe("fmMain");
//					waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
//					Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
//					log("Select the country name is: " + Inputdata[i][2]);
//					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
//					Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
//					log("click on the select button");
//				} else {
//					log("User is Phone Number Manager");
//					WaitforElementtobeclickable((xml.getlocator("//locators/Changegroup2")));
//					Clickon(getwebelement(xml.getlocator("//locators/Changegroup2")));
//					log("Click on the change Group");
//					switchtodefault();
//					switchtoframe("fmMain");
//					Thread.sleep(10000);
//					waitandForElementDisplayed(xml.getlocator("//locators/selectPhoneNOmanager"));
//					Select(getwebelement(xml.getlocator("//locators/selectPhoneNOmanager")), "SuperUser");
//					log("Select the USerGroup is: " + "SuperUser");
//					WaitforElementtobeclickable((xml.getlocator("//locators/ChangeGroupbtn")));
//					Clickon(getwebelement(xml.getlocator("//locators/ChangeGroupbtn")));
//					log("click on the Change Group Button button");
//					Thread.sleep(3000);
//					switchtoframe("fmMenu");
//					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryButton")));
//					Clickon(getwebelement(xml.getlocator("//locators/IECountryButton")));
//					log("Click on the change country name");
//					switchtodefault();
//					switchtoframe("fmMain");
//					waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
//					Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
//					log("Select the country name is: " + Inputdata[i][2]);
//					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
//					Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
//					log("click on the select button");
//				}
//
//			}
//		}
//
//		public void UserGroupforPhnNoMgn(Object[][] Inputdata) throws InterruptedException, Exception {
//			for (int i = 0; i < Inputdata.length; i++) {
//				Thread.sleep(3000);
//				switchtoframe("fmMenu");
//				String check = Gettext(getwebelement(xml.getlocator("//locators/UserName")));
//				if (check.equals("[SuperUser]")) {
//					WaitforElementtobeclickable((xml.getlocator("//locators/Changegroup")));
//					Clickon(getwebelement(xml.getlocator("//locators/Changegroup")));
//					log("Click on the change Group");
//					switchtodefault();
//					switchtoframe("fmMain");
//					waitandForElementDisplayed(xml.getlocator("//locators/selectPhoneNOmanager"));
//					Select(getwebelement(xml.getlocator("//locators/selectPhoneNOmanager")), "Phone No. Manager");
//					log("Select the User Group is: " + "Phone No. Manager");
//					WaitforElementtobeclickable((xml.getlocator("//locators/ChangeGroupbtn")));
//					Clickon(getwebelement(xml.getlocator("//locators/ChangeGroupbtn")));
//					log("click on the Change Group Button button");
//					Thread.sleep(3000);
//					switchtoframe("fmMenu");
//					WaitforElementtobeclickable((xml.getlocator("//locators/PhnMgnCountry")));
//					Clickon(getwebelement(xml.getlocator("//locators/PhnMgnCountry")));
//					log("Click on the change country name");
//					switchtodefault();
//					switchtoframe("fmMain");
//					Thread.sleep(3000);
//					waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
//					Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
//					log("Select the country name is: " + Inputdata[i][2]);
//					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
//					Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
//					log("click on the select button");
//
//				} else {
//					log("user group is phone number manager.");
//					Thread.sleep(3000);
//					log("Country");
//					WaitforElementtobeclickable((xml.getlocator("//locators/PhnMgnCountry")));
//					Clickon(getwebelement(xml.getlocator("//locators/PhnMgnCountry")));
//					log("Click on the change country name");
//					switchtodefault();
//					switchtoframe("fmMain");
//					waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
//					Thread.sleep(3000);
//					Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
//					log("Select the country name is: " + Inputdata[i][2]);
//					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
//					Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
//					log("click on the select button");
//					Thread.sleep(3000);
//				}
//				
//			}
//			
//		}
//		public void GetNMTSStatus(String status) throws InterruptedException, DocumentException
//		{
//			String CurrentNumberStatus = GetText(getwebelement(xml.getlocator("//locators/verifyStatus")));
//			log("Current NMTS Number status is + CurrentNumberStatus");
//			
//		}
//
	}
			

		
		
	


	