package ScriptHelper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.dom4j.DocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import Driver.DriverHelper;
import Driver.DriverTestcase;
import Driver.PropertyReader;
import Driver.xmlreader;
import Reporter.ExtentTestManager;

public class NmtsHelper extends DriverHelper{
	
	
		WebElement el;
		xmlreader xml = new xmlreader("src\\Locators\\NumberHosting.xml");

		public NmtsHelper(WebDriver dr) {
			super(dr);
		}

		public void OpenApplication2() throws Exception {
			openurl("NMTS");
			PropertyReader pr = new PropertyReader();

			String URL = null;

			URL = pr.readproperty("NMTS_URL");

			ExtentTestManager.getTest().log(LogStatus.PASS,
					" Step: Opening the Url : <font color='green'> " + URL + " </font>");

		}
		
		public String NumberQueryFamilyofNMTS(Object[][] Inputdata) {
			String SearchCreteria = null;
			for (int i = 0; i < Inputdata.length; i++) {
				SearchCreteria = Inputdata[i][0].toString();
			}
			return SearchCreteria;
		}

		public void SearchofNMTSScenario(Object[][] Inputdata) throws Exception {

			switch (NumberQueryFamilyofNMTS(Inputdata)) {
			case "Merge Number": {
				MergerdNumber(Inputdata);
				break;
			}
			case "Split Number": {
				SplitNumbermain(Inputdata);
				break;
			}
			case "Free to Reserve": {
				FreeToReserveScenario(Inputdata);
				break;

			}
			case "Adding Number": {
				AddNumber(Inputdata);
				break;
			}
			case "Free to Activate": {
				FreeToActivateScenario(Inputdata);
				break;
			}
			case "Cancel Reservation": {
				CancelReservation(Inputdata);
				break;
			}
			case "Return and Transfer Number": {
				ReturnandTransfer(Inputdata);
				break;
			}
			case "Deactivation Scenario": {
				DeactivationNMTS(Inputdata);
				break;
			}
			
			case "PortIn Number": {
				PortInNMTS(Inputdata);
				break;
			}
			case "Reserve To Activate": {
				ReservetoActivateScenario(Inputdata);
				break;
			}
			case "Reservation Scenario": {
				FreeToReserveScenario(Inputdata);
				break;
			}
			case "Port-out Scenario NMTS": {
				PortOutinMNTS(Inputdata);
				break;
			}
			default: {
				log("It seems like, Selected Creteria is not valid in Sheet!!! Please update it in sheet!! ");
			}
			}

		}
		
		public void MergerdNumber(Object[][] Inputdata) throws InterruptedException, Exception {
			for (int i = 0; i < Inputdata.length; i++) {
				UserGroupforSuperUser(Inputdata);
				
				log("Merger Number starting");
				Thread.sleep(3000);
				switchtodefault();
				switchtoframe("fmMenu");
				if(Inputdata[i][2].toString().equals("CH"))
				{
					WaitforElementtobeclickable((xml.getlocator("//locators/mergebtnCH")));
					Clickon(getwebelement(xml.getlocator("//locators/mergebtnCH")));
					log("click on the merge number range button for CH country");
				}
				else {
				WaitforElementtobeclickable((xml.getlocator("//locators/IEMergeButtonforsuper")));
				Clickon(getwebelement(xml.getlocator("//locators/IEMergeButtonforsuper")));
				log("click on the merge number range Button");
				}
				switchtodefault();
				switchtoframe("fmMain");
				if (Inputdata[i][24].toString().equals("Merge Number")) {
					WaitforElementtobeclickable((xml.getlocator("//locators/IEMergeRadioBtn")));
					Clickon(getwebelement(xml.getlocator("//locators/IEMergeRadioBtn")));
					log("Select the radio button:-"+Inputdata[i][24].toString());
				} else
				{
					WaitforElementtobeclickable((xml.getlocator("//locators/IEpoolandstatus")));
					Clickon(getwebelement(xml.getlocator("//locators/IEpoolandstatus")));
					log("Select the radio button:-"+Inputdata[i][24].toString());
					// Inputdata[i][24].toString().equals("Change pool/Status")
				}
				// IEMergeRadioBtn
				Select(getwebelement(xml.getlocator("//locators/IEStatus")), Inputdata[i][4].toString());
				log("select the Status:-" + Inputdata[i][4].toString());
			
				Select(getwebelement(xml.getlocator("//locators/IEAvailability")), Inputdata[i][37].toString());
				log("select the availability:-" + Inputdata[i][37].toString());
				
				SendKeys(getwebelement(xml.getlocator("//locators/IEAreaCode")), Inputdata[i][5].toString());
				log("enter the area code:-" + Inputdata[i][5].toString());
				
				SendKeys(getwebelement(xml.getlocator("//locators/IEAreaCodeEXtension")), Inputdata[i][36].toString());
				log("enter the area code extension:-" + Inputdata[i][36].toString());
				
				SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeStart")), Inputdata[i][14].toString());
				log("enter the rangestart value:-" + Inputdata[i][14].toString());
				
				SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeEnd")), Inputdata[i][15].toString());
				log("enter the rangeend value:-" + Inputdata[i][15].toString());
				
				Select(getwebelement(xml.getlocator("//locators/IEGeoNumber")), Inputdata[i][38].toString());
				log("select the Geographical or Non Geographical number" + Inputdata[i][38].toString());
				

				if (Inputdata[i][24].toString().equals("Merge Number")) {
					if (Inputdata[i][37].toString().contains("Wholesale")) {
						log("if the availability is wholesale please select the block size");
						Select(getwebelement(xml.getlocator("//locators/IEDropBlockSize")), Inputdata[i][22].toString());
						log("Selected Block size for wholesale is:-"+Inputdata[i][22].toString());

					} else {
						log("Current Availability is Colt so block size is not required.");
					}
				} else if (Inputdata[i][24].toString().equals("Change pool/Status")) {
					log("For Changepool/Status");
					log("if the availability is wholesale please select the block size");
					Select(getwebelement(xml.getlocator("//locators/IEDropBlockSize")), Inputdata[i][22].toString());
					log("Selected Block size for change pool/status is:-"+Inputdata[i][22].toString());
				}

				Select(getwebelement(xml.getlocator("//locators/IEPageSize")), Inputdata[i][39].toString());
				log("select the page size:-" + Inputdata[i][39].toString());
				WaitforElementtobeclickable((xml.getlocator("//locators/IESubmitButton")));
				Clickon(getwebelement(xml.getlocator("//locators/IESubmitButton")));
				log("click on the submit button");
				Thread.sleep(5000);
				if (isElementPresent(xml.getlocator("//locators/IEnorecord"))) 
				{
					RedLog("No Record found please put the current value in the excel file");
				}
				else {

					if (Inputdata[i][40].toString().contains("All")) {
						WaitforElementtobeclickable(xml.getlocator("//locators/IEAllCheckbox"));
						Clickon(getwebelement(xml.getlocator("//locators/IEAllCheckbox")));
						log("select all checkbox");

					}

					else {

						log("enter the value of range start:-" + Inputdata[i][40].toString());
						int rangeStart = Integer.parseInt(Inputdata[i][40].toString());
						log("enter the value of range end:-" + Inputdata[i][41].toString());
						int rangeEnd = Integer.parseInt(Inputdata[i][41].toString());
						if (isElementPresent(xml.getlocator("//locators/IEmorerecord"))) {
							for (int k = rangeStart; k <= rangeEnd; k++) {
								if (isElementPresent(xml.getlocator("//locators/IERandomCheckbox")
										.replace("Rowindex", String.valueOf(k + 1)).replace("index", String.valueOf(k)))) {
									Clickon(getwebelement(xml.getlocator("//locators/IERandomCheckbox")
											.replace("Rowindex", String.valueOf(k + 1))
											.replace("index", String.valueOf(k))));
									log("check the selected checkbox");
								} else {
									RedLog("Invalid range or more than one checkbox required.");
								}
								log("row work complete");
							}
						} else {
							RedLog("For merge more than one row needed so please enter the valid data");
						}

					}
				}
				Thread.sleep(2000);
				if (Inputdata[i][24].toString().equals("Merge Number")) {
					WaitforElementtobeclickable((xml.getlocator("//locators/IEmergeButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IEmergeButton")));
					log("click ont the merge Button");
				} else {
					Select(getwebelement(xml.getlocator("//locators/targetStatus")), Inputdata[i][49].toString());
					log("Target status is:-" + Inputdata[i][49].toString());
					Thread.sleep(2000);
					Select(getwebelement(xml.getlocator("//locators/tgtavail")), Inputdata[i][50].toString());
					log("Target availability is:-" + Inputdata[i][50].toString());
					Thread.sleep(1000);
					WaitforElementtobeclickable((xml.getlocator("//locators/checkboxformerge")));
					Clickon(getwebelement(xml.getlocator("//locators/checkboxformerge")));
					log("click ont the Switch task completed");
					//checkboxformerge
					WaitforElementtobeclickable((xml.getlocator("//locators/poolandstatusbtn")));
					Clickon(getwebelement(xml.getlocator("//locators/poolandstatusbtn")));
					log("click ont the IEpoolandstatus button");

				}
				
				// Select more than one number to merge
//				if (isElementPresent(xml.getlocator("//locators/IEErrorinmerge"))) {
//					RedLog("Plese select more than one checkbox");
//				} else {
				GreenLog("Selected number successfully merged");
			}
				// }
			}
			public void AddNumber(Object[][] Inputdata) throws InterruptedException, Exception 
			{
				for (int i = 0; i < Inputdata.length; i++)
				{
					UserGroupforSuperUser(Inputdata);
					Thread.sleep(2000);
					switchtodefault();
					switchtoframe("fmMenu");
					WaitforElementtobeclickable((xml.getlocator("//locators/IEAddButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IEAddButton")));

					log("Click on the Add number button");
					switchtodefault();
					switchtoframe("fmMain");
					Thread.sleep(3000);
					Select(getwebelement(xml.getlocator("//locators/Switch")), Inputdata[i][44].toString());
					log("select the Switch in Adding Number:-" + Inputdata[i][44].toString());
					if (Inputdata[i][2].toString().contains("BE")) {
						log("country is BE so select the areacode from the table");
						WaitforElementtobeclickable((xml.getlocator("//locators/AreacodePrefix")));
						Clickon(getwebelement(xml.getlocator("//locators/AreacodePrefix")));
						log("Click on the AreacodePrefix button");
						Thread.sleep(3000);
						// CloseProposalwindow();
						// Clickon(getwebelement(xml.getlocator("//locators/IERandomCheckbox").replace("Rowindex",
						// String.valueOf(k + 1)).replace("index", String.valueOf(k))));
						switchtofram(getwebelement(xml.getlocator("//locators/iframe")));

						Clickon(getwebelement(
								xml.getlocator("//locators/AreaInformationWin").replace("index", Inputdata[i][5].toString())));
						log("Area the area code prefix:-" + Inputdata[i][5].toString());
						switchtodefault();
						switchtoframe("fmMain");

					} else {
						SendKeys(getwebelement(xml.getlocator("//locators/Areacodeother")), Inputdata[i][5].toString());
						log("Enter the area code/prfix:-" + Inputdata[i][5].toString());
					}
					SendKeys(getwebelement(xml.getlocator("//locators/IEAreaCode")), Inputdata[i][36].toString());
					log("Enter the area code extension:-" + Inputdata[i][36].toString());
					SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeStart")), Inputdata[i][14].toString());
					log("Enter the range start value:-" + Inputdata[i][14].toString());
					SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeEnd")), Inputdata[i][15].toString());
					log("Enter the range end value:-" + Inputdata[i][15].toString());
					Select(getwebelement(xml.getlocator("//locators/AddnumStatus")), Inputdata[i][4].toString());
					log("select the Status:-" + Inputdata[i][4].toString());
					Select(getwebelement(xml.getlocator("//locators/AddnumGeo")), Inputdata[i][38].toString());
					log("Select the Geo or non-Geo:-" + Inputdata[i][38].toString());
					Select(getwebelement(xml.getlocator("//locators/AddnumCtg")), Inputdata[i][46].toString());
					log("Select the Category:-" + Inputdata[i][46].toString());
					Select(getwebelement(xml.getlocator("//locators/AddingAvailability")), Inputdata[i][37].toString());
					log("Select the Availability:-" + Inputdata[i][37].toString());
					WaitforElementtobeclickable((xml.getlocator("//locators/AddingSubmit")));
					Clickon(getwebelement(xml.getlocator("//locators/AddingSubmit")));
					log("Click on the submit button");

					// for success

					//Thread.sleep(10000);
					
					if (isElementPresent(xml.getlocator("//locators/errorforerror"))) {
						String msg = GetText(getwebelement(xml.getlocator("//locators/errorforerror")));
						RedLog("Please Enter the valid Input in the Excel file" + msg);
						
					}

					else {
						String msg2 = GetText(getwebelement(xml.getlocator("//locators/successmsg")));
						GreenLog("message is" + msg2);
					}

				}
			}
   public void ReturnandTransfer(Object[][] Inputdata) throws InterruptedException, Exception {
				for (int i = 0; i < Inputdata.length; i++) {

					UserGroupforPhnNoMgn(Inputdata);
					Thread.sleep(2000);
					switchtodefault();
					switchtoframe("fmMenu");
					Thread.sleep(5000);
					if(Inputdata[i][2].toString().equals("CH")) {
						WaitforElementtobeclickable((xml.getlocator("//locators/portoutforCH")));
						Clickon(getwebelement(xml.getlocator("//locators/portoutforCH")));
						log("Click on the PORT-OUT button");
					}
					else {
					WaitforElementtobeclickable((xml.getlocator("//locators/retandtransfer")));
					Clickon(getwebelement(xml.getlocator("//locators/retandtransfer")));
					log("Click on the return and transfer button");
					}
					switchtodefault();
					switchtoframe("fmMain");
					if(Inputdata[i][2].toString().equals("CH")) {
						waitandForElementDisplayed(xml.getlocator("//locators/wholesaleradiobtn"));
						Clickon(getwebelement(xml.getlocator("//locators/wholesaleradiobtn")));
						log("Click on the radio button button for wholesale");
						Thread.sleep(2000);
						waitandForElementDisplayed(xml.getlocator("//locators/portoutradiobtn"));
						Clickon(getwebelement(xml.getlocator("//locators/portoutradiobtn")));
						log("Click on the radio button button for port out");
						Thread.sleep(3000);
						//portoutradiobtn
					}
					Select(getwebelement(xml.getlocator("//locators/ReturnAvailability")), Inputdata[i][37].toString());
					log("select the Availability:-" + Inputdata[i][37].toString());
					Thread.sleep(2000);
					SendKeys(getwebelement(xml.getlocator("//locators/AreaCodePrefix")), Inputdata[i][5].toString());
					log("Enter the Area Code Prefix" + Inputdata[i][5].toString());
					waitandForElementDisplayed(xml.getlocator("//locators/AreaCodeExt"));
					SendKeys(getwebelement(xml.getlocator("//locators/AreaCodeExt")), Inputdata[i][36].toString());
					log("Enter the Area Code Extention" + Inputdata[i][36].toString());
					Select(getwebelement(xml.getlocator("//locators/retandtransferStatus")), Inputdata[i][4].toString());
					log("select the Status:-" + Inputdata[i][4].toString());
					waitandForElementDisplayed(xml.getlocator("//locators/RangeFrom"));
					SendKeys(getwebelement(xml.getlocator("//locators/RangeFrom")), Inputdata[i][14].toString());
					log("Enter the Range start number" + Inputdata[i][14].toString());
					SendKeys(getwebelement(xml.getlocator("//locators/retandtransferRangeto")), Inputdata[i][15].toString());
					log("Enter the Range END number" + Inputdata[i][15].toString());
					waitandForElementDisplayed(xml.getlocator("//locators/Submitbtn"));
					Clickon(getwebelement(xml.getlocator("//locators/Submitbtn")));
					log("Click on Submit button");

					Thread.sleep(3000);
					switchtodefault();
					switchtoframe("fmMain");
					Thread.sleep(5000);
					if (isElementPresent(xml.getlocator("//locators/GriddataNMTS"))) {
						GreenLog("Data are displaying with search creteria!!");
						waitandForElementDisplayed(xml.getlocator("//locators/checkbox3"));
						Clickon(getwebelement(xml.getlocator("//locators/checkbox3")));
						log("Click on Allocation checkbox");
						waitandForElementDisplayed(xml.getlocator("//locators/Selectmark"));
						Clickon(getwebelement(xml.getlocator("//locators/Selectmark")));
						log("Click on Select mark");
						Thread.sleep(3000);

						String page = GetText(getwebelement(xml.getlocator("//locators/totalpage")));
						int len = Integer.parseInt(page);
						log("page number is" + page);

						if (isElementPresent(xml.getlocator("//locators/checkforreturn").replace("RangeFrom",
								Inputdata[i][14].toString()))) {
							waitandForElementDisplayed(xml.getlocator("//locators/checkforreturn").replace("RangeFrom",
									Inputdata[i][14].toString()));
							Clickon(getwebelement(xml.getlocator("//locators/checkforreturn").replace("RangeFrom",
									Inputdata[i][14].toString())));
							log("Click on the Checkbox again");
						} else {
							if (len > 1) {
								waitandForElementDisplayed(xml.getlocator("//locators/lastbtn"));
								Clickon(getwebelement(xml.getlocator("//locators/lastbtn")));
								log("Click on the last page for checkbox");
							}

							Thread.sleep(2000);

							waitandForElementDisplayed(xml.getlocator("//locators/checkforreturn").replace("RangeFrom",
									Inputdata[i][14].toString()));
							Clickon(getwebelement(xml.getlocator("//locators/checkforreturn").replace("RangeFrom",
									Inputdata[i][14].toString())));
							log("Click on the Checkbox again");
							// totalpage
							// pagenumberInput
							// checkboxforreturn
							Thread.sleep(2000);
						}

						Select(getwebelement(xml.getlocator("//locators/ProcessingDecession")), Inputdata[i][56].toString());
						log("select the Processing Decession:-" + Inputdata[i][56].toString());
						if (Inputdata[i][56].toString().equals("Transferred")) {
							Thread.sleep(5000);
							Select(getwebelement(xml.getlocator("//locators/operator")), Inputdata[i][69].toString());
							log("select the operator:-" + Inputdata[i][69].toString());
						}
						Thread.sleep(5000);
						
						
	
						
						if(isElementPresent(xml.getlocator("//locators/YesBtn"))) 
						{
							waitandForElementDisplayed(xml.getlocator("//locators/YesBtn"));
							Clickon(getwebelement(xml.getlocator("//locators/YesBtn")));
							log("Click on the yes button on popup window");
						GreenLog("Number is successfully:- "+Inputdata[i][56].toString());	
						}
						else 
						{
							RedLog("please enter the valid input");
						}
						
						
						AcceptJavaScriptMethod();
						
						
						

					}
					else 
					{
						RedLog("No Record Found please enter the valid data");
					}
				}
			}
   
   
   
   public void DeactivationNMTS(Object[][] Inputdata) throws InterruptedException, Exception {
				for (int i = 0; i < Inputdata.length; i++) {
					UserGroupforPhnNoMgn(Inputdata);
					switchtodefault();
					switchtoframe("fmMenu");
					
					if(Inputdata[i][2].toString().equals("FR")) 
					{
						WaitforElementtobeclickable((xml.getlocator("//locators/CeaseBtnforFR")));
						Clickon(getwebelement(xml.getlocator("//locators/CeaseBtnforFR")));
						log("Click on the Cease Number button for France");
					}
					else
					{
					WaitforElementtobeclickable((xml.getlocator("//locators/IECeaseButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IECeaseButton")));
					log("Click on the Cease Number button");
					}
					switchtodefault();
					switchtoframe("fmMain");
					Select(getwebelement(xml.getlocator("//locators/madeByNMTS")), Inputdata[i][57].toString());
					log("select the Made By Name:-" + Inputdata[i][57].toString());
					if (Inputdata[i][2].equals("FR") || Inputdata[i][2].equals("IT")) {
						Select(getwebelement(xml.getlocator("//locators/Coltandhostednumber")), Inputdata[i][67].toString());
						log("select the Colt and hosted number:-" + Inputdata[i][67].toString());
					}
					Select(getwebelement(xml.getlocator("//locators/DeactivationAvail")), Inputdata[i][37].toString());
					log("select the Availability:-" + Inputdata[i][37].toString());
					Select(getwebelement(xml.getlocator("//locators/FilterBy")), Inputdata[i][58].toString());
					log("select the Filter By:-" + Inputdata[i][58].toString());
					Thread.sleep(1000);
					Select(getwebelement(xml.getlocator("//locators/matching")), Inputdata[i][59].toString());
					log("select the Matcing:-" + Inputdata[i][59].toString());
					SendKeys(getwebelement(xml.getlocator("//locators/FilterText")), Inputdata[i][42].toString());
					log("Enter the Number" + Inputdata[i][42].toString());
					WaitforElementtobeclickable((xml.getlocator("//locators/SubmitDeactivate")));
					Clickon(getwebelement(xml.getlocator("//locators/SubmitDeactivate")));
					log("click on the submit button");

					Thread.sleep(17000);
					if (isElementPresent(xml.getlocator("//locators/errormsgnorecord"))) {
						RedLog("No record found please enter the valid data number");
					} else {
						DoubleClick(getwebelement(xml.getlocator("//locators/OrderId")));
						log("Doubleclick on the order id button");
						WaitforElementtobeclickable((xml.getlocator("//locators/check1")));
						Clickon(getwebelement(xml.getlocator("//locators/check1")));
						log("Click on the checkbox");
						WaitforElementtobeclickable((xml.getlocator("//locators/SelectedAllLink")));
						Clickon(getwebelement(xml.getlocator("//locators/SelectedAllLink")));
						log("Click on the Selected All");
						waitandForElementDisplayed(xml.getlocator("//locators/AllocationCheckboxagain"));
						Clickon(getwebelement(xml.getlocator("//locators/AllocationCheckboxagain")));
						log("Click on Allocation checkbox again");
						waitandForElementDisplayed(xml.getlocator("//locators/CancelQuarntined"));
						Clickon(getwebelement(xml.getlocator("//locators/CancelQuarntined")));
						log("Click on the Cancel Quarntined Button");
						switchtoframe("popupFrame");
						log("Entering ");

						waitandForElementDisplayed(xml.getlocator("//locators/yespopup"));
						Clickon(getwebelement(xml.getlocator("//locators/yespopup")));
						log("Click on the yes button on popup window");
						Thread.sleep(5000);
						log("Action to ok");
						Thread.sleep(10000);
						//AcceptJavaScriptMethod();
						GreenLog("number successfully deactivated");

//
						Thread.sleep(5000);
						String CurrentNumberStatus = GetText(getwebelement(xml.getlocator("//locators/verifyStatus")));
						TransactionStatusNMTS.set(CurrentNumberStatus);
						
						log("current  number status is:-"+CurrentNumberStatus);
						
						Thread.sleep(3000);
				
						if(Inputdata[i][78].toString().equalsIgnoreCase("Yes"))
						{
							Thread.sleep(3000);
					
							DriverTestcase tc=new DriverTestcase();
							tc.setupforChrome();
							Thread.sleep(3000);
							
							DriverTestcase dtc = new DriverTestcase();
							NmtsHelper nmt = new NmtsHelper(dtc.getwebdriver());
							String COCOMstatus = nmt.NumberQuiryUsingNumberRange(Inputdata);
							Thread.sleep(3000);
						  
							if (TransactionStatusNMTS.get().contains("Ceased") && COCOMstatus.contains("Quarantined")) 
							{
				
								GreenLog("Status Under NMTS and COCOM have same and verified!!!");
							} 
							else
							{
							   RedLog("Integration Deactivation scenario failed ");
							}
					
						}}}
					
					}
				
			
			public void PortOutinMNTS(Object[][] Inputdata) throws InterruptedException, Exception {
				for (int i = 0; i < Inputdata.length; i++) {
					UserGroupforPhnNoMgn(Inputdata);
					switchtodefault();
					switchtoframe("fmMenu");
					if(Inputdata[i][2].toString().equals("CH")) {
						WaitforElementtobeclickable((xml.getlocator("//locators/portoutforCH")));
						Clickon(getwebelement(xml.getlocator("//locators/portoutforCH")));
						log("Click on the PORT-OUT button for CH extension country");
					}
					else {
					WaitforElementtobeclickable((xml.getlocator("//locators/portoutNMTS")));
					Clickon(getwebelement(xml.getlocator("//locators/portoutNMTS")));
					log("Click on the PORT-OUT button");
					}
					switchtodefault();
					switchtoframe("fmMain");
					if(Inputdata[i][2].toString().equals("CH")) {
						waitandForElementDisplayed(xml.getlocator("//locators/wholesaleradiobtn"));
						Clickon(getwebelement(xml.getlocator("//locators/wholesaleradiobtn")));
						log("Click on the radio button button for wholesale");
						Thread.sleep(2000);
						waitandForElementDisplayed(xml.getlocator("//locators/portoutradiobtn"));
						Clickon(getwebelement(xml.getlocator("//locators/portoutradiobtn")));
						log("Click on the radio button button for port-out");
						Thread.sleep(3000);
						//portoutradiobtn
					}
					if (isElementPresent(xml.getlocator("//locators/AreaCodePrefix"))) {
						waitandForElementDisplayed(xml.getlocator("//locators/AreaCodePrefix"));
						SendKeys(getwebelement(xml.getlocator("//locators/AreaCodePrefix")), Inputdata[i][5].toString());
						log("Enter the Area Code Prefix:-"+Inputdata[i][5].toString());
						waitandForElementDisplayed(xml.getlocator("//locators/AreaCodeExt"));
						SendKeys(getwebelement(xml.getlocator("//locators/AreaCodeExt")), Inputdata[i][36].toString());
						log("Enter the Area Code Extention:-"+Inputdata[i][36].toString());
						waitandForElementDisplayed(xml.getlocator("//locators/RangeFrom"));
						SendKeys(getwebelement(xml.getlocator("//locators/RangeFrom")), Inputdata[i][14].toString());
						log("Enter the Range start number:-"+Inputdata[i][14].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/retandtransferRangeto")),
								Inputdata[i][15].toString());
						log("Enter the Range END number:-"+Inputdata[i][15].toString());
						waitandForElementDisplayed(xml.getlocator("//locators/Submitbtn"));
						Clickon(getwebelement(xml.getlocator("//locators/Submitbtn")));
						log("Click on Submit button");
					} else {

						RedLog("please check the correct data in Excel sheet for - AreaCode, AreacodeExt and Rangefrom:(");
					}
					Thread.sleep(3000);
					switchtodefault();
					switchtoframe("fmMain");
					Thread.sleep(5000);
					if (isElementPresent(xml.getlocator("//locators/GriddataNMTS"))) {
						GreenLog("Data are displaying with search creteria!!");
						waitandForElementDisplayed(xml.getlocator("//locators/checkbox3"));
						Clickon(getwebelement(xml.getlocator("//locators/checkbox3")));
						log("Click on Allocation checkbox");
						waitandForElementDisplayed(xml.getlocator("//locators/Selectmark"));
						Clickon(getwebelement(xml.getlocator("//locators/Selectmark")));
						log("Click on Select mark");
						Thread.sleep(3000);
						waitandForElementDisplayed(xml.getlocator("//locators/AllocationCheckboxagain"));
						Clickon(getwebelement(xml.getlocator("//locators/AllocationCheckboxagain")));
						log("Click on Allocation checkbox again");
						Thread.sleep(3000);
						Select(getwebelement(xml.getlocator("//locators/RangeHolder")), Inputdata[i][63].toString());
						log("select the New Number Range Holder:-" + Inputdata[i][63].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/ContractNumber")), Inputdata[i][64].toString());
						log("Input the Contruct Number:-" + Inputdata[i][64].toString());
						Select(getwebelement(xml.getlocator("//locators/ContractSrc")), Inputdata[i][61].toString());
						log("select the Contract Source:-" + Inputdata[i][61].toString());
						SendKeys(getwebelement(xml.getlocator("//locators/portoutdateNMTS")), Inputdata[i][65].toString());
						log("Input the Port OUt Date:-" + Inputdata[i][65].toString());
						waitandForElementDisplayed(xml.getlocator("//locators/Portoutselectedrangebtn"));
						Clickon(getwebelement(xml.getlocator("//locators/Portoutselectedrangebtn")));
						log("Click on Port out selected range button");
						Thread.sleep(2000);
						waitandForElementDisplayed(xml.getlocator("//locators/YesBtn"));
						Clickon(getwebelement(xml.getlocator("//locators/YesBtn")));
						log("Click on yes button");
						Thread.sleep(3000);
						AcceptJavaScriptMethod();
						Thread.sleep(3000);
						GreenLog("Number is Port-out successfully");
Thread.sleep(5000);
//waitandForElementDisplayed(xml.getlocator("//locators/Port-outStatus"));
//							 String CurrentNumberStatus1 = GetText(getwebelement(xml.getlocator("//locators/Port-outStatus")));
//							log("Second Current number status is:-"+CurrentNumberStatus1);
//							String CurrentNumberStatus = CurrentNumberStatus1.substring(9, 12);
					String Portoutstatus="Port";
					
						
						TransactionStatusNMTS.set(Portoutstatus);
						log("current  number status is:-"+"Port-out");
						Thread.sleep(3000);
				
						
						if(Inputdata[i][78].toString().equalsIgnoreCase("Yes"))
						{
							Thread.sleep(3000);
					
							DriverTestcase tc=new DriverTestcase();
							tc.setupforChrome();
							Thread.sleep(3000);
							
							DriverTestcase dtc = new DriverTestcase();
							NmtsHelper nmt = new NmtsHelper(dtc.getwebdriver());
							String COCOMstatus = nmt.NumberQuiryUsingNumberRange(Inputdata);
							Thread.sleep(3000);
						   String firstFourChars = "";
							if (COCOMstatus.length() > 3) 
							{
							    firstFourChars = COCOMstatus.toLowerCase().substring(0, 3);
							} 
							else
							{
							    firstFourChars = COCOMstatus.toLowerCase();
							}
							Assert.assertTrue(TransactionStatusNMTS.get().toLowerCase().toString().contains(firstFourChars),"Status under NMTS and COCOM are not Same!");
							log("Status Under NMTS and COCOM have same and verified!!!");
							
						
					
					}
					}}
					}

				

		public void SplitNumber(Object[][] Inputdata) throws InterruptedException, Exception {
			for (int i = 0; i < Inputdata.length; i++) {
			switchtoframe("fmMenu");
			WaitforElementtobeclickable((xml.getlocator("//locators/IESplitNumberButton")));
			Clickon(getwebelement(xml.getlocator("//locators/IESplitNumberButton")));
			log("Click on the Split Nubmer Tab");
			SendKeys(getwebelement(xml.getlocator("//locators/IESplitNumber")), Inputdata[i][42].toString());
			log("enter the number:-"+Inputdata[i][42].toString());
			WaitforElementtobeclickable((xml.getlocator("//locators/IESplitSearchButton")));
			Clickon(getwebelement(xml.getlocator("//locators/IESplitSearchButton")));
			log("Click on the submit button");
			}
			
			
				
				}
		public void FreeToReserveScenario(Object[][] Inputdata) throws InterruptedException, Exception 
		{//it will perfom first >>> free To reserve 
			for (int i = 0; i < Inputdata.length; i++)
			{

	    	  UserGroupforPhnNoMgn(Inputdata);
				Thread.sleep(3000);
				switchtodefault();
				switchtoframe("fmMenu");
				WaitforElementtobeclickable(xml.getlocator("//locators/DirectAllocation"));
				Clickon(getwebelement(xml.getlocator("//locators/DirectAllocation"))); 
				log("Click on Direct Allocation");
				switchtodefault();
				switchtoframe("fmMain");
				waitandForElementDisplayed(xml.getlocator("//locators/AreaCodePrefix"));
				SendKeys(getwebelement(xml.getlocator("//locators/AreaCodePrefix")), Inputdata[i][5].toString());
				log("Enter the Area Code Prefix:-"+Inputdata[i][5].toString());
				waitandForElementDisplayed(xml.getlocator("//locators/AreaCodeExt"));
				SendKeys(getwebelement(xml.getlocator("//locators/AreaCodeExt")), Inputdata[i][45].toString());
				log("Enter the Area Code Extention:-"+Inputdata[i][45].toString());
				waitandForElementDisplayed(xml.getlocator("//locators/RangeFrom"));
				SendKeys(getwebelement(xml.getlocator("//locators/RangeFrom")), Inputdata[i][47].toString());
				log("Enter the Range start number:-"+Inputdata[i][47].toString());
				
				waitandForElementDisplayed(xml.getlocator("//locators/retandtransferRangeto"));
				SendKeys(getwebelement(xml.getlocator("//locators/retandtransferRangeto")), Inputdata[i][48].toString());
				log("Enter the Range to number:-"+Inputdata[i][48].toString());
				
				WaitforElementtobeclickable(xml.getlocator("//locators/Submitbtn"));
				Clickon(getwebelement(xml.getlocator("//locators/Submitbtn"))); 
				log("Click on Submit button");
				
				if(isElementPresent(xml.getlocator("//locators/GriddataNMTS")))
				{

				Thread.sleep(3000);	
				switchtodefault();
				switchtoframe("fmMain");
				Thread.sleep(5000);		
					
					GreenLog("Data are displaying with search creteria!!");			
					WaitforElementtobeclickable(xml.getlocator("//locators/checkbox3"));
					Clickon(getwebelement(xml.getlocator("//locators/checkbox3"))); 
					log("Click on Allocation checkbox");	
					waitandForElementDisplayed(xml.getlocator("//locators/Selectmark"));
					Clickon(getwebelement(xml.getlocator("//locators/Selectmark"))); 
					log("Click on Select mark");			
					Thread.sleep(3000);
					WaitforElementtobeclickable(xml.getlocator("//locators/AllocationCheckboxagain"));
					Clickon(getwebelement(xml.getlocator("//locators/AllocationCheckboxagain"))); 
					log("Click on Allocation checkbox again");
					Thread.sleep(3000);
					WaitforElementtobeclickable(xml.getlocator("//locators/submitSelectedRange"));
					Clickon(getwebelement(xml.getlocator("//locators/submitSelectedRange"))); 
					log("Click on submit Selected Range button");		
					switchtodefault();
					switchtoframe("fmMain");			
					waitandForElementDisplayed(xml.getlocator("//locators/ocn"));
					SendKeys(getwebelement(xml.getlocator("//locators/ocn")), Inputdata[i][51].toString());
					log("Enter the OCN noumber:-"+Inputdata[i][51].toString());			
					WaitforElementtobeclickable(xml.getlocator("//locators/Submitocn"));
					Clickon(getwebelement(xml.getlocator("//locators/Submitocn"))); 
					log("Click on submit Search button");				
					Thread.sleep(10000);
				}
					else {

						RedLog("please check the correct data in Excel sheet for - AreaCode, AreacodeExt and Rangefrom:(");
						}
				Thread.sleep(7000);
				if(isElementPresent(xml.getlocator("//locators/GriddataforOCN")))
				{
					Thread.sleep(10000);
					WaitforElementtobeclickable(xml.getlocator("//locators/doubleClickonOCN"));
					DoubleClick(getwebelement(xml.getlocator("//locators/doubleClickonOCN")));
					log("Double click on OCN number");
			
			     }
					else
					{
						RedLog("it seems like OCN no is not correct in Excel sheet please check it ");
					}
				Thread.sleep(10000);
					switchtodefault();
					Thread.sleep(10000);
					switchtoframe("fmMain");		
					waitandForElementDisplayed(xml.getlocator("//locators/selectDesc"));
					Select(getwebelement(xml.getlocator("//locators/selectDesc")), Inputdata[i][52].toString());
					log("Select the Service Destination:-"+Inputdata[i][52].toString());			
					waitandForElementDisplayed(xml.getlocator("//locators/EmailNotification"));
					Select(getwebelement(xml.getlocator("//locators/EmailNotification")), Inputdata[i][53].toString());
					log("Select the Email Notification:-"+Inputdata[i][53].toString());				
					waitandForElementDisplayed(xml.getlocator("//locators/ServicePro"));
					SendKeys(getwebelement(xml.getlocator("//locators/ServicePro")), Inputdata[i][3].toString());
					log("Enter the Service Profile:-"+Inputdata[i][3].toString());			
					waitandForElementDisplayed(xml.getlocator("//locators/TransactionreferenceNo"));
					SendKeys(getwebelement(xml.getlocator("//locators/TransactionreferenceNo")), Inputdata[i][55].toString());
					log("Enter the Transaction reference Number:-"+Inputdata[i][55].toString());
					Thread.sleep(5000);
					//waitandForElementDisplayed(xml.getlocator("//locators/CheckboxforAllocation"));
					Clickon(getwebelement(xml.getlocator("//locators/CheckboxforAllocation"))); 
					log("Click mark Checkbox");
					WaitforElementtobeclickable(xml.getlocator("//locators/SubmitOrderBtn"));
					Clickon(getwebelement(xml.getlocator("//locators/SubmitOrderBtn"))); 
					log("Click on Submit Order Button");
					Thread.sleep(3000);
					//verify the Reservation status as : Reservation 
		//			GET THE RESERVATION STATUS**********************************************************************************************//
					String CurrentNumberStatus = GetText(getwebelement(xml.getlocator("//locators/verifyStatus")));
					TransactionStatusNMTS.set(CurrentNumberStatus);
					log("current  number status is:-"+CurrentNumberStatus);
					Thread.sleep(3000);
			
					if(Inputdata[i][78].toString().equalsIgnoreCase("Yes"))
					{
						Thread.sleep(3000);
				
						DriverTestcase tc=new DriverTestcase();
						tc.setupforChrome();
						Thread.sleep(3000);
						
						DriverTestcase dtc = new DriverTestcase();
						NmtsHelper nmt = new NmtsHelper(dtc.getwebdriver());
						String COCOMstatus = nmt.NumberQuiryUsingNumberRange(Inputdata);
						Thread.sleep(3000);
					   String firstFourChars = "";
						if (COCOMstatus.length() > 5) 
						{
						    firstFourChars = COCOMstatus.toLowerCase().substring(0, 4);
						} 
						else
						{
						    firstFourChars = COCOMstatus.toLowerCase();
						}
						Assert.assertTrue(TransactionStatusNMTS.get().toLowerCase().toString().contains(firstFourChars),"Status under NMTS and COCOM are not Same!");
						log("Status Under NMTS and COCOM have same and verified!!!");
						
					
				
					}}}



public String NumberQuiryUsingNumberRange(Object[][] Inputdata)
				throws InterruptedException, DocumentException, IOException {
	String status = null;
			for (int i = 0; i < Inputdata.length; i++) {
				Clickon(getwebelement(xml.getlocator("//locators/ManageNumber")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Manage Number");
				Clickon(getwebelement(xml.getlocator("//locators/NumberEnquiry")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on the Number Enquiry");
				
				Thread.sleep(6000);
				Select(getwebelement(xml.getlocator("//locators/ResellerName")), Inputdata[i][1].toString());
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Select the Reseller name: " + Inputdata[i][1].toString());

				Select(getwebelement(xml.getlocator("//locators/Country")), Inputdata[i][79].toString());
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Country: " + Inputdata[i][2].toString());
				Thread.sleep(3000);
				Select(getwebelement(xml.getlocator("//locators/ServiceProfile")), Inputdata[i][3].toString());
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Select the Service Profile: " + Inputdata[i][3].toString());
				Thread.sleep(7000);
                // System.out.println("1");
				Select(getwebelement(xml.getlocator("//locators/SearchCriteria")), "Number Range");
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Search Criteria as Number Status");
//System.out.println("2");
				Select(getwebelement(xml.getlocator("//locators/LocalAreaCode")), Inputdata[i][80].toString());
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Select the Local Area code: " + Inputdata[i][5].toString());
				SendKeys(getwebelement(xml.getlocator("//locators/mainNumber")), Inputdata[i][36].toString());
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Enter the main number: " + Inputdata[i][36].toString());
				SendKeys(getwebelement(xml.getlocator("//locators/RangeStart")), Inputdata[i][14].toString());
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Enter the RangeStart: " + Inputdata[i][14].toString());
				SendKeys(getwebelement(xml.getlocator("//locators/RangeEnd")), Inputdata[i][15].toString());
				ExtentTestManager.getTest().log(LogStatus.PASS,
						" Step: Enter the RangeEnd: " + Inputdata[i][15].toString());
				Clickon(getwebelement(xml.getlocator("//locators/SearchButton")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step:click the Search button");
				Thread.sleep(5000);
				//getting status of number
				status = Gettext(getwebelement(xml.getlocator("//locators/SEarchStatus1")));
				System.out.println(status);
			}
			
			return status;
}
				
			
			public void CancelReservation(Object[][] Inputdata) throws InterruptedException, Exception 
			{//cancel reservation
				for (int i = 0; i < Inputdata.length; i++)
				{
//					if(Inputdata[i][20].toString().equals("Cancel Reservation"))
//							{
					
					Thread.sleep(2000);
					switchtodefault();
					switchtoframe("fmMenu");
					//click to activate 
					waitandForElementDisplayed(xml.getlocator("//locators/processreservation"));
					Clickon(getwebelement(xml.getlocator("//locators/processreservation"))); 
					log("Click on Activate Number tab");
				
					switchtodefault();
					switchtoframe("fmMain");
					Thread.sleep(4000);
						Select(getwebelement(xml.getlocator("//locators/madeByNMTS")), Inputdata[i][57].toString());
									log("select the Made By Name:-" + Inputdata[i][57].toString());
									Select(getwebelement(xml.getlocator("//locators/DeactivationAvail")), Inputdata[i][37].toString());
									log("select the Availability:-" + Inputdata[i][37].toString());
									Select(getwebelement(xml.getlocator("//locators/FilterBy")), Inputdata[i][58].toString());
									log("select the Filter By:-" + Inputdata[i][58].toString());
									Thread.sleep(1000); 
						Select(getwebelement(xml.getlocator("//locators/matching")), Inputdata[i][59].toString());
									log("select the Matcing:-" + Inputdata[i][59].toString());
									SendKeys(getwebelement(xml.getlocator("//locators/FilterText")), Inputdata[i][42].toString());
									log("Enter the Number" + Inputdata[i][42].toString());
									WaitforElementtobeclickable((xml.getlocator("//locators/SubmitDeactivate")));
									Clickon(getwebelement(xml.getlocator("//locators/SubmitDeactivate")));
									log("click on the submit button");
									Thread.sleep(10000);
									if (isElementPresent(xml.getlocator("//locators/errormsgnorecord"))) 
									{
										RedLog("No record found please enter the valid data number");
									} 
									else {
										Thread.sleep(5000);
										DoubleClick(getwebelement(xml.getlocator("//locators/OrderId")));
										log("Doubleclick on the order id button");
					//.i.
						Thread.sleep(1000);
						WaitforElementtobeclickable(xml.getlocator("//locators/CancelReserveCheckbox"));
						Clickon(getwebelement(xml.getlocator("//locators/CancelReserveCheckbox"))); 
						log("Click on Submit mark's checkbox");
					
						Thread.sleep(3000);
						Clickon(getwebelement(xml.getlocator("//locators/SelectedAllLink"))); 
						log("Click on select mark's checkbox");
						Thread.sleep(4000);
						WaitforElementtobeclickable(xml.getlocator("//locators/SelectedRangeCheckrange"));
						Clickon(getwebelement(xml.getlocator("//locators/SelectedRangeCheckrange"))); 
						
						log("Click on Selected Range Checkrange mark's checkbox");
						Thread.sleep(1000);
						waitandForElementDisplayed(xml.getlocator("//locators/selectDecision"));
						Select(getwebelement(xml.getlocator("//locators/selectDecision")), Inputdata[i][56].toString());
						log("select the Decision as Cancel Reservation:-"+Inputdata[i][56].toString());
						Thread.sleep(3000);
						waitandForElementDisplayed(xml.getlocator("//locators/RejectReason"));
						Select(getwebelement(xml.getlocator("//locators/RejectReason")), Inputdata[i][70].toString());
						log("select the Reject Reason:-"+Inputdata[i][70].toString());
						
						Thread.sleep(4000);
						waitandForElementDisplayed(xml.getlocator("//locators/MRN"));
						SendKeys(getwebelement(xml.getlocator("//locators/MRN")), Inputdata[i][60].toString());
						log("Enter the MRN number:-"+Inputdata[i][60].toString());
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitDecision"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitDecision"))); 
						log("Click on Submit Decision button");
						Thread.sleep(3000);
						String CurrentNumberStatus1 = GetText(getwebelement(xml.getlocator("//locators/verifyStatus")));
						log("Second Current number status is:-"+CurrentNumberStatus1);
						
						TransactionStatusNMTS.set(CurrentNumberStatus1);
						log("current  number status is:-"+CurrentNumberStatus1);
						Thread.sleep(3000);
				
						
						if(Inputdata[i][78].toString().equalsIgnoreCase("Yes"))
						{
							Thread.sleep(3000);
					
							DriverTestcase tc=new DriverTestcase();
							tc.setupforChrome();
							Thread.sleep(3000);
							
							DriverTestcase dtc = new DriverTestcase();
							NmtsHelper nmt = new NmtsHelper(dtc.getwebdriver());
							String COCOMstatus = nmt.NumberQuiryUsingNumberRange(Inputdata);
							Thread.sleep(3000);
						   String firstFourChars = "";
							if (COCOMstatus.length() > 5) 
							{
							    firstFourChars = COCOMstatus.toLowerCase().substring(0, 4);
							} 
							else
							{
							    firstFourChars = COCOMstatus.toLowerCase();
							}
							Assert.assertTrue(TransactionStatusNMTS.get().toLowerCase().toString().contains(firstFourChars),"Status under NMTS and COCOM are not Same!");
							log("Status Under NMTS and COCOM have same and verified!!!");
							
						
					
						}}}

							}
	      
			
			public void AllocationPerform(Object[][] Inputdata) throws InterruptedException, Exception 
			{//free To reserve
				for (int i = 0; i < Inputdata.length; i++)
				{
					
//					else if(Inputdata[i][20].toString().equals("Free to ActivatewithAddress") || Inputdata[i][20].toString().equals("Reserve to ActivatewithAddress") )
//					{
						Thread.sleep(3000);
						WaitforElementtobeclickable(xml.getlocator("//locators/CancelReserveCheckbox"));
						Clickon(getwebelement(xml.getlocator("//locators/CancelReserveCheckbox"))); 
						log("Click on Submit mark's checkbox");
						Thread.sleep(3000);
						Clickon(getwebelement(xml.getlocator("//locators/SelectedAllLink"))); 
						log("Click on select mark's checkbox");
						Thread.sleep(5000);
						WaitforElementtobeclickable(xml.getlocator("//locators/SelectedRangeCheckrange"));
						Clickon(getwebelement(xml.getlocator("//locators/SelectedRangeCheckrange"))); 					
						log("Click on Selected Range Checkrange mark's checkbox");
					//	Thread.sleep(3000);
						waitandForElementDisplayed(xml.getlocator("//locators/selectDecision"));
						Select(getwebelement(xml.getlocator("//locators/selectDecision")), Inputdata[i][56].toString());
						log("select the Processing Decision:- "+Inputdata[i][56].toString());
					//	Thread.sleep(3000);
						SendKeys(getwebelement(xml.getlocator("//locators/ContactNo")), Inputdata[i][66].toString());
						log("Enter the End Contact Number:-"+Inputdata[i][66].toString());
					//	Thread.sleep(2000);
						waitandForElementDisplayed(xml.getlocator("//locators/ContractSource"));
						Select(getwebelement(xml.getlocator("//locators/ContractSource")), Inputdata[i][61].toString());
						log("select the Contract Source:- "+Inputdata[i][61].toString());
						//Thread.sleep(2000);
						waitandForElementDisplayed(xml.getlocator("//locators/MRN"));
						SendKeys(getwebelement(xml.getlocator("//locators/MRN")), Inputdata[i][60].toString());
						log("Enter the MRN number:-"+Inputdata[i][60].toString());					
						waitandForElementDisplayed(xml.getlocator("//locators/EndCustName"));
						SendKeys(getwebelement(xml.getlocator("//locators/EndCustName")), Inputdata[i][7].toString());
						log("Enter the End Customer Name:-"+Inputdata[i][7].toString());
						Thread.sleep(3000);					
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingNameNMTS")), Inputdata[i][8].toString());
						log("Enter the Building Name:-"+Inputdata[i][8].toString());
						Thread.sleep(2000);
						SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumberNMTS")), Inputdata[i][9].toString());
						log("Enter the Building Number:-"+Inputdata[i][9].toString());
						Thread.sleep(2000);				
						SendKeys(getwebelement(xml.getlocator("//locators/StreetName")), Inputdata[i][10].toString());
						log("Enter the Street Name:-"+Inputdata[i][10].toString());
						Thread.sleep(2000);				
						SendKeys(getwebelement(xml.getlocator("//locators/Town")), Inputdata[i][62].toString());
						log("Enter the Town:-"+Inputdata[i][62].toString());
						Thread.sleep(5000);			
						SendKeys(getwebelement(xml.getlocator("//locators/PostCodeNMTS")), Inputdata[i][12].toString());
						log("Enter the PostCode:-"+Inputdata[i][12].toString());					
						WaitforElementtobeclickable(xml.getlocator("//locators/SubmitDecision"));
						Clickon(getwebelement(xml.getlocator("//locators/SubmitDecision"))); 
						log("Click on Submit Decision button");
						Thread.sleep(3000);	
							AcceptJavaScriptMethod();
							}
			
//					else {
//						RedLog("it seems like :( there is issue with your Excel data sheet . please correct it ..");
//					}
            }
			
		public void ActivatePerform(Object[][] Inputdata) throws InterruptedException, Exception {
			for (int i = 0; i < Inputdata.length; i++)
			{
	      
			Thread.sleep(2000);
			switchtodefault();
			switchtoframe("fmMenu");
			//click to activate 
			waitandForElementDisplayed(xml.getlocator("//locators/ActivateNumbertab"));
			Clickon(getwebelement(xml.getlocator("//locators/ActivateNumbertab"))); 
			log("Click on Activate Number tab");
		
			switchtodefault();
			switchtoframe("fmMain");
			Thread.sleep(2000);
				Select(getwebelement(xml.getlocator("//locators/madeByNMTS")), Inputdata[i][57].toString());
							log("select the Made By Name:-" + Inputdata[i][57].toString());
							Select(getwebelement(xml.getlocator("//locators/DeactivationAvail")), Inputdata[i][37].toString());
							log("select the Availability:-" + Inputdata[i][37].toString());
							Select(getwebelement(xml.getlocator("//locators/FilterBy")), Inputdata[i][58].toString());
							log("select the Filter By:-" + Inputdata[i][58].toString());
							Thread.sleep(1000); 
				Select(getwebelement(xml.getlocator("//locators/matching")), Inputdata[i][59].toString());
							log("select the Matcing:-" + Inputdata[i][59].toString());
							SendKeys(getwebelement(xml.getlocator("//locators/FilterText")), Inputdata[i][42].toString());
							log("Enter the Number" + Inputdata[i][42].toString());
							WaitforElementtobeclickable((xml.getlocator("//locators/SubmitDeactivate")));
							Clickon(getwebelement(xml.getlocator("//locators/SubmitDeactivate")));
							log("click on the submit button");
							Thread.sleep(10000);
							if (isElementPresent(xml.getlocator("//locators/errormsgnorecord"))) 
							{
								RedLog("No record found please enter the valid data number");
							} 
							else {
								Thread.sleep(5000);
								DoubleClick(getwebelement(xml.getlocator("//locators/OrderId")));
								log("Doubleclick on the order id button");
								WaitforElementtobeclickable((xml.getlocator("//locators/check1")));
								Clickon(getwebelement(xml.getlocator("//locators/check1")));
								log("Click on the checkbox");
								WaitforElementtobeclickable((xml.getlocator("//locators/SelectedAllLink")));
								Clickon(getwebelement(xml.getlocator("//locators/SelectedAllLink")));
								log("Click on the Selected All");
								waitandForElementDisplayed(xml.getlocator("//locators/AllocationCheckboxagain"));
								Clickon(getwebelement(xml.getlocator("//locators/AllocationCheckboxagain")));
								log("Click on Allocation checkbox again");	
								Thread.sleep(2000);
								waitandForElementDisplayed(xml.getlocator("//locators/ActivateNumnerbtn"));
								Clickon(getwebelement(xml.getlocator("//locators/ActivateNumnerbtn")));
								log("Click on Activate Number button");
								
								Thread.sleep(8000);
								//String ActivatedStatus="Activated";
							log("Current NMTS Number Status :"+ "Activated Status");
								String CurrentNumberStatus1="";
							
									 CurrentNumberStatus1 = GetText(getwebelement(xml.getlocator("//locators/verifyActivaate")));
									log("Second Current number status is:-"+CurrentNumberStatus1);
								
								TransactionStatusNMTS.set(CurrentNumberStatus1);
								log("current  number status is:-"+CurrentNumberStatus1);
								Thread.sleep(3000);
						
								
								if(Inputdata[i][78].toString().equalsIgnoreCase("Yes"))
								{
									Thread.sleep(3000);
							
									DriverTestcase tc=new DriverTestcase();
									tc.setupforChrome();
									Thread.sleep(3000);
									
									DriverTestcase dtc = new DriverTestcase();
									NmtsHelper nmt = new NmtsHelper(dtc.getwebdriver());
									String COCOMstatus = nmt.NumberQuiryUsingNumberRange(Inputdata);
									Thread.sleep(3000);
								   String firstFourChars = "";
									if (COCOMstatus.length() > 5) 
									{
									    firstFourChars = COCOMstatus.toLowerCase().substring(0, 4);
									} 
									else
									{
									    firstFourChars = COCOMstatus.toLowerCase();
									}
									Assert.assertTrue(TransactionStatusNMTS.get().toLowerCase().toString().contains(firstFourChars),"Status under NMTS and COCOM are not Same!");
									log("Status Under NMTS and COCOM have same and verified!!!");
									
								
							
							}
							}}
			}
		public void ReservetoActivateScenario(Object[][] Inputdata) throws InterruptedException, Exception {
			for (int i = 0; i < Inputdata.length; i++) 
			{
				UserGroupforPhnNoMgn(Inputdata);
				switchtodefault();
				switchtoframe("fmMenu");		
				Thread.sleep(5000);
				WaitforElementtobeclickable((xml.getlocator("//locators/ProcessReservationtab")));
				Clickon(getwebelement(xml.getlocator("//locators/ProcessReservationtab")));
				log("Click on the Process Reservation tab");
				switchtodefault();
				switchtoframe("fmMain");
				Select(getwebelement(xml.getlocator("//locators/madeByNMTS")), Inputdata[i][57].toString());
				log("select the Made By Name:-" + Inputdata[i][57].toString());
				Select(getwebelement(xml.getlocator("//locators/DeactivationAvail")), Inputdata[i][37].toString());
				log("select the Availability:-" + Inputdata[i][37].toString());
				Select(getwebelement(xml.getlocator("//locators/FilterBy")), Inputdata[i][58].toString());
				log("select the Filter By:-" + Inputdata[i][58].toString());
				Thread.sleep(5000); 
	       Select(getwebelement(xml.getlocator("//locators/matching")), Inputdata[i][59].toString());
				log("select the Matcing:-" + Inputdata[i][59].toString());
				SendKeys(getwebelement(xml.getlocator("//locators/FilterText")), Inputdata[i][42].toString());
				log("Enter the Number" + Inputdata[i][42].toString());
				WaitforElementtobeclickable((xml.getlocator("//locators/SubmitDeactivate")));
				Clickon(getwebelement(xml.getlocator("//locators/SubmitDeactivate")));
				log("click on the submit button");
				Thread.sleep(10000);
				if (isElementPresent(xml.getlocator("//locators/errormsgnorecord"))) 
				{
					RedLog("No record found please enter the valid data number");
				} 
				else {
					Thread.sleep(10000);
					if(isElementPresent(xml.getlocator("//locators/CheckReservestatus")))
					{
						//.
						waitandForElementDisplayed(xml.getlocator("//locators/OrderId"));
						DoubleClick(getwebelement(xml.getlocator("//locators/OrderId")));
						log("Doubleclick on the order id button");
			//it will perform Allocate first then Activate
						AllocationPerform(Inputdata);
						Thread.sleep(3000);
						ActivatePerform(Inputdata);
						
						
					}
					else {
						RedLog("Please currect the Number from the Excel sheet . it seems like number iss not in Reserve status");
					}
					
				}
			}
			}
		
		public void FreeToActivateScenario(Object[][] Inputdata) throws InterruptedException, Exception {
			for (int i = 0; i < Inputdata.length; i++) 
			{
				FreeToReserveScenario(Inputdata);
				log("first jouney completed");
				AllocationPerform(Inputdata);
				log("Second journey completed");
				ActivatePerform(Inputdata);
				log("final journey completed");
			}
			
			}
		public void PortInNMTS(Object[][] Inputdata) throws InterruptedException, Exception {
			for (int i = 0; i < Inputdata.length; i++) 
			{
				UserGroupforPhnNoMgn(Inputdata);
				Thread.sleep(3000);
				switchtodefault();
				switchtoframe("fmMenu");
				WaitforElementtobeclickable((xml.getlocator("//locators/PortIntab")));
				Clickon(getwebelement(xml.getlocator("//locators/PortIntab")));
				log("Click on the PortIn Numbers tab");
				switchtodefault();
				switchtoframe("fmMain");
			Select(getwebelement(xml.getlocator("//locators/Switch")), Inputdata[i][44].toString());
			log("select the Switch in Adding Number:-" + Inputdata[i][44].toString());
			if (Inputdata[i][2].toString().contains("BE"))
			{
				log("country is BE so select the areacode from the table");
				WaitforElementtobeclickable((xml.getlocator("//locators/AreacodePrefix")));
				Clickon(getwebelement(xml.getlocator("//locators/AreacodePrefix")));
				log("Click on the AreacodePrefix button for BE extension country");
				Thread.sleep(3000);
				switchtofram(getwebelement(xml.getlocator("//locators/iframe")));
				Clickon(getwebelement(
						xml.getlocator("//locators/AreaInformationWin").replace("index", Inputdata[i][5].toString())));
				log("Area the area code prefix:-" + Inputdata[i][5].toString());
				switchtodefault();
				switchtoframe("fmMain");
			} else {
				SendKeys(getwebelement(xml.getlocator("//locators/Areacodeother")), Inputdata[i][5].toString());
				log("Enter the area code/prfix:-" + Inputdata[i][5].toString());
			}
			SendKeys(getwebelement(xml.getlocator("//locators/IEAreaCode")), Inputdata[i][36].toString());
			log("Enter the area code extension:-" + Inputdata[i][36].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeStart")), Inputdata[i][14].toString());
			log("Enter the range start value:-" + Inputdata[i][14].toString());
			SendKeys(getwebelement(xml.getlocator("//locators/IEMergeRangeEnd")), Inputdata[i][15].toString());
			log("Enter the range end value:-" + Inputdata[i][15].toString());
			Select(getwebelement(xml.getlocator("//locators/AddnumGeo")), Inputdata[i][38].toString());
			log("Select the Geo or non-Geo:-" + Inputdata[i][38].toString());
			Select(getwebelement(xml.getlocator("//locators/AddnumCtg")), Inputdata[i][46].toString());
			log("Select the Category:-" + Inputdata[i][46].toString());
			Select(getwebelement(xml.getlocator("//locators/AddingAvailability")), Inputdata[i][37].toString());
			log("Select the Availability:-" + Inputdata[i][37].toString());	
			SendKeys(getwebelement(xml.getlocator("//locators/NRN")), Inputdata[i][60].toString());
			log("Enter the NRN VALUE:-" + Inputdata[i][60].toString());	
			Thread.sleep(3000);
			Select(getwebelement(xml.getlocator("//locators/SelectRangeHolder")), Inputdata[i][63].toString());
			log("Select the SelectRangeHolder:-" + Inputdata[i][63].toString());		
			WaitforElementtobeclickable((xml.getlocator("//locators/AddingSubmit")));
			Clickon(getwebelement(xml.getlocator("//locators/AddingSubmit")));
			log("Click on the submit button");
			
			//>>>>>>>>>>>>>>>>>GetNMTSStatus	
		
		//checking the ocn for Port- IN
			Thread.sleep(5000);
			switchtodefault();
			switchtoframe("fmMain");			
			waitandForElementDisplayed(xml.getlocator("//locators/ocn"));
			SendKeys(getwebelement(xml.getlocator("//locators/ocn")), Inputdata[i][51].toString());
			log("Enter the OCN noumber:-"+Inputdata[i][51].toString());			
			WaitforElementtobeclickable(xml.getlocator("//locators/Submitocn"));
			Clickon(getwebelement(xml.getlocator("//locators/Submitocn"))); 
			log("Click on submit Search button");
			Thread.sleep(10000);
		Thread.sleep(7000);
		if(isElementPresent(xml.getlocator("//locators/GriddataforOCN")))
		{
			Thread.sleep(10000);
			WaitforElementtobeclickable(xml.getlocator("//locators/doubleClickonOCN"));
			DoubleClick(getwebelement(xml.getlocator("//locators/doubleClickonOCN")));
			log("Double click on OCN number");

	}
			else
			{
				RedLog("it seems like OCN no is not correct in Excel sheet please check it ");
			}
		Thread.sleep(10000);
			switchtodefault();
			Thread.sleep(10000);
			switchtoframe("fmMain");	
			waitandForElementDisplayed(xml.getlocator("//locators/selectDesc"));
			Select(getwebelement(xml.getlocator("//locators/selectDesc")), Inputdata[i][52].toString());
			log("Select the Service Destination:-"+Inputdata[i][52].toString());			
			waitandForElementDisplayed(xml.getlocator("//locators/EmailNotification"));
			Select(getwebelement(xml.getlocator("//locators/EmailNotification")), Inputdata[i][53].toString());
			log("Select the Email Notification:-"+Inputdata[i][53].toString());				
			waitandForElementDisplayed(xml.getlocator("//locators/ServicePro"));
			SendKeys(getwebelement(xml.getlocator("//locators/ServicePro")), Inputdata[i][3].toString());
			log("Enter the Service Profile:-"+Inputdata[i][3].toString());			
			waitandForElementDisplayed(xml.getlocator("//locators/TransactionreferenceNo"));
			SendKeys(getwebelement(xml.getlocator("//locators/TransactionreferenceNo")), Inputdata[i][55].toString());
			log("Enter the Transaction reference Number:-"+Inputdata[i][55].toString());
			Thread.sleep(5000);
			//waitandForElementDisplayed(xml.getlocator("//locators/CheckboxforAllocation"));
			Clickon(getwebelement(xml.getlocator("//locators/CheckboxforAllocation"))); 
			log("Click mark Checkbox");
			WaitforElementtobeclickable(xml.getlocator("//locators/SubmitOrderBtn"));
			Clickon(getwebelement(xml.getlocator("//locators/SubmitOrderBtn"))); 
			log("Click on Submit Order Button");
//			try
//			{
//			Thread.sleep(4000);
//			String CurrentNumberStatus4 = GetText(getwebelement(xml.getlocator("//locators/verifyStatusforPortInone")));
//			Thread.sleep(4000);
//			String CurrentNumberStatus5 = GetText(getwebelement(xml.getlocator("//locators/verifyStatusforPortIntwo")));
//			log("Current NMTS Number Status is "+CurrentNumberStatus4+CurrentNumberStatus5);
//			}
//			catch(StaleElementReferenceException w)
//			{
//				
//			}

				Thread.sleep(3000);
				waitandForElementDisplayed(xml.getlocator("//locators/selectDecision"));
				Select(getwebelement(xml.getlocator("//locators/selectDecision")), Inputdata[i][19].toString());
				log("select the Processing Decision : Allocate for Port-in Activate:-"+Inputdata[i][19].toString());
				Thread.sleep(3000);
				//waitandForElementDisplayed(xml.getlocator("//locators/ContactNo"));
				SendKeys(getwebelement(xml.getlocator("//locators/ContactNo")), Inputdata[i][66].toString());
				log("Enter the End Contact Number:-"+Inputdata[i][66].toString());
				Thread.sleep(2000);
				waitandForElementDisplayed(xml.getlocator("//locators/ContractSource"));
				Select(getwebelement(xml.getlocator("//locators/ContractSource")), Inputdata[i][61].toString());
				log("select the Contract Source:- "+Inputdata[i][61].toString());
				Thread.sleep(2000);
				waitandForElementDisplayed(xml.getlocator("//locators/EndCustName"));
				SendKeys(getwebelement(xml.getlocator("//locators/EndCustName")), Inputdata[i][7].toString());
				log("Enter the End Customer Name:-"+Inputdata[i][7].toString());
				Thread.sleep(3000);		
				SendKeys(getwebelement(xml.getlocator("//locators/BuildingNameNMTSForAllocate")), Inputdata[i][8].toString());
				log("Enter the Building Name:-"+Inputdata[i][8].toString());
				Thread.sleep(2000);
				SendKeys(getwebelement(xml.getlocator("//locators/BuildingNumberNMTSForAllocate")), Inputdata[i][9].toString());
				log("Enter the Building Number:-"+Inputdata[i][9].toString());
				Thread.sleep(2000);	
				SendKeys(getwebelement(xml.getlocator("//locators/StreetName")), Inputdata[i][10].toString());
				log("Enter the Street Name:-"+ Inputdata[i][10].toString());
				Thread.sleep(2000);	
				SendKeys(getwebelement(xml.getlocator("//locators/Town")), Inputdata[i][62].toString());
				log("Enter the Town:-"+Inputdata[i][62].toString());
				Thread.sleep(5000);
				SendKeys(getwebelement(xml.getlocator("//locators/PostCodeNMTSForAllocate")), Inputdata[i][12].toString());
				log("Enter the PostCode:-"+Inputdata[i][12].toString());	
				WaitforElementtobeclickable(xml.getlocator("//locators/SubmitDecision"));
				Clickon(getwebelement(xml.getlocator("//locators/SubmitDecision"))); 
				log("Click on Submit Decision button");
				Thread.sleep(2000);	
				AcceptJavaScriptMethod();
		//After port-in Allocated go to view order tab to check the status and also trying to Port-in acativate

				switchtodefault();
				switchtoframe("fmMenu");
				//click to activate 
				waitandForElementDisplayed(xml.getlocator("//locators/ActivateNumbertab"));
				Clickon(getwebelement(xml.getlocator("//locators/ActivateNumbertab"))); 
				log("Click on Activate Number tab");
				switchtodefault();
				switchtoframe("fmMain");
				Thread.sleep(2000);
				Select(getwebelement(xml.getlocator("//locators/madeByNMTS")), Inputdata[i][57].toString());
				log("select the Made By Name:-" + Inputdata[i][57].toString());
				Select(getwebelement(xml.getlocator("//locators/DeactivationAvail")), Inputdata[i][37].toString());
				log("select the Availability:-" + Inputdata[i][37].toString());
				Select(getwebelement(xml.getlocator("//locators/FilterBy")), Inputdata[i][58].toString());
				log("select the Filter By:-" + Inputdata[i][58].toString());
				Thread.sleep(1000); 
	          Select(getwebelement(xml.getlocator("//locators/matching")), Inputdata[i][59].toString());
				log("select the Matcing:-" + Inputdata[i][59].toString());
				SendKeys(getwebelement(xml.getlocator("//locators/FilterText")), Inputdata[i][42].toString());
				log("Enter the Number" + Inputdata[i][42].toString());
				WaitforElementtobeclickable((xml.getlocator("//locators/SubmitDeactivate")));
				Clickon(getwebelement(xml.getlocator("//locators/SubmitDeactivate")));
				log("click on the submit button");
				Thread.sleep(10000);
				if (isElementPresent(xml.getlocator("//locators/errormsgnorecord"))) 
				{
					RedLog("No record found please enter the valid data number");
				} else {
					Thread.sleep(2000);
					waitandForElementDisplayed(xml.getlocator("//locators/OrderId"));
					DoubleClick(getwebelement(xml.getlocator("//locators/OrderId")));
					log("Doubleclick on the order id button");
					Thread.sleep(2000);
					WaitforElementtobeclickable((xml.getlocator("//locators/ActivateNumnerbtn")));
					Clickon(getwebelement(xml.getlocator("//locators/ActivateNumnerbtn")));
					log("click on the Activate Number button");
					//GreenLog("Success");
					
					String ActivateStatus="PortIn(Activated)";
	//
					//String CurrentNumberStatus = GetText(getwebelement(xml.getlocator("//locators/verifyStatus")));
					TransactionStatusNMTS.set(ActivateStatus);
					log("current  number status is:-"+ActivateStatus);
					Thread.sleep(3000);
			
					if(Inputdata[i][78].toString().equalsIgnoreCase("Yes"))
					{
						Thread.sleep(3000);
				
						DriverTestcase tc=new DriverTestcase();
						tc.setupforChrome();
						Thread.sleep(3000);
						
						DriverTestcase dtc = new DriverTestcase();
						NmtsHelper nmt = new NmtsHelper(dtc.getwebdriver());
						String COCOMstatus = nmt.NumberQuiryUsingNumberRange(Inputdata);
						Thread.sleep(3000);
					   String firstFourChars = "";
						if (COCOMstatus.length() > 5) 
						{
						    firstFourChars = COCOMstatus.toLowerCase().substring(0, 4);
						} 
						else
						{
						    firstFourChars = COCOMstatus.toLowerCase();
						}
						Assert.assertTrue(TransactionStatusNMTS.get().toLowerCase().toString().contains(firstFourChars),"Status under NMTS and COCOM are not Same!");
						log("Status Under NMTS and COCOM have same and verified!!!");
						
					
				
					}}}}
		public void UserGroupforSuperUser(Object[][] Inputdata) throws InterruptedException, Exception {
			for (int i = 0; i < Inputdata.length; i++) {
				Thread.sleep(3000);
				switchtoframe("fmMenu");
				String check = Gettext(getwebelement(xml.getlocator("//locators/UserName")));
				if (check.equals("[SuperUser]")) {
					log("User is Super User");
					Thread.sleep(7000);
					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IECountryButton")));
					log("Click on the change country name");
					switchtodefault();
					switchtoframe("fmMain");
					waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
					Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
					log("Select the country name is: " + Inputdata[i][2]);
					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
					log("click on the select button");
				} else {
					log("User is Phone Number Manager");
					if(Inputdata[i][2].toString().equals("CH"))
					{
						WaitforElementtobeclickable((xml.getlocator("//locators/ChangeGroupforCH")));
						Clickon(getwebelement(xml.getlocator("//locators/ChangeGroupforCH")));
						log("Click on the change Group for CH country");
					}
					else if(Inputdata[i][2].toString().equals("IT")) {
						WaitforElementtobeclickable((xml.getlocator("//locators/ChangeGroupforIT")));
						Clickon(getwebelement(xml.getlocator("//locators/ChangeGroupforIT")));
						log("Click on the change Group for IT country");
						//ChangeGroupforIT
					}
					else {
					WaitforElementtobeclickable((xml.getlocator("//locators/Changegroup")));
					Clickon(getwebelement(xml.getlocator("//locators/Changegroup")));
					log("Click on the change Group");
					//ChangeGroupforCH
					}
					switchtodefault();
					switchtoframe("fmMain");
					Thread.sleep(10000);
					waitandForElementDisplayed(xml.getlocator("//locators/selectPhoneNOmanager"));
					Select(getwebelement(xml.getlocator("//locators/selectPhoneNOmanager")), "SuperUser");
					log("Select the USerGroup is: " + "SuperUser");
					WaitforElementtobeclickable((xml.getlocator("//locators/ChangeGroupbtn")));
					Clickon(getwebelement(xml.getlocator("//locators/ChangeGroupbtn")));
					log("click on the submit Button button");
					Thread.sleep(3000);
					switchtodefault();
					switchtoframe("fmMenu");
					
					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IECountryButton")));
					log("Click on the change country name");
					Thread.sleep(3000);
					switchtodefault();
					switchtoframe("fmMain");
					waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
					Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
					log("Select the country name is: " + Inputdata[i][2]);
					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
					log("click on the select button");
				}

			}
		}

		public void UserGroupforPhnNoMgn(Object[][] Inputdata) throws InterruptedException, Exception {
			for (int i = 0; i < Inputdata.length; i++) {
				Thread.sleep(3000);
				switchtoframe("fmMenu");
				String check = Gettext(getwebelement(xml.getlocator("//locators/UserName")));
				if (check.equals("[SuperUser]")) {
					if(Inputdata[i][2].toString().equals("CH"))
					{
						WaitforElementtobeclickable((xml.getlocator("//locators/ChangeGroupforCH")));
						Clickon(getwebelement(xml.getlocator("//locators/ChangeGroupforCH")));
						log("Click on the change Group for CH country");
					}
					else if(Inputdata[i][2].toString().equals("IT")) {
						WaitforElementtobeclickable((xml.getlocator("//locators/ChangeGroupforIT")));
						Clickon(getwebelement(xml.getlocator("//locators/ChangeGroupforIT")));
						log("Click on the change Group for IT country");
						//ChangeGroupforIT
					}
					else {
					WaitforElementtobeclickable((xml.getlocator("//locators/Changegroup2")));
					Clickon(getwebelement(xml.getlocator("//locators/Changegroup2")));
					log("Click on the change Group");
					//ChangeGroupforCH
					}
					Thread.sleep(2000);
					switchtodefault();
					switchtoframe("fmMain");
					waitandForElementDisplayed(xml.getlocator("//locators/selectPhoneNOmanager"));
					Select(getwebelement(xml.getlocator("//locators/selectPhoneNOmanager")), "Phone No. Manager");
					log("Select the User Group is: " + "Phone No. Manager");
					WaitforElementtobeclickable((xml.getlocator("//locators/ChangeGroupbtn")));
					Clickon(getwebelement(xml.getlocator("//locators/ChangeGroupbtn")));
					log("click on the Change Group Button button");
					Thread.sleep(3000);
					switchtoframe("fmMenu");
					if(Inputdata[i][2].toString().equals("CH"))
					{
						WaitforElementtobeclickable((xml.getlocator("//locators/PhnmgncountryforCH")));
						Clickon(getwebelement(xml.getlocator("//locators/PhnmgncountryforCH")));
						log("Click on the change country name for CH extension country");
					}
					else if(Inputdata[i][2].toString().equals("IT")) {
						WaitforElementtobeclickable((xml.getlocator("//locators/PhnmgncountryforIT")));
						Clickon(getwebelement(xml.getlocator("//locators/PhnmgncountryforIT")));
						log("Click on the change country name for IT extension country");
					}
					else {
					WaitforElementtobeclickable((xml.getlocator("//locators/PhnMgnCountry")));
					Clickon(getwebelement(xml.getlocator("//locators/PhnMgnCountry")));
					log("Click on the change country name");
					}
					switchtodefault();
					switchtoframe("fmMain");
					waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
					Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
					log("Select the country name is: " + Inputdata[i][2]);
					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
					log("click on the select button");

				} else {
					log("user group is phone number manager.");
					Thread.sleep(3000);
					log("Country");
					if(Inputdata[i][2].toString().equals("CH"))
					{
						WaitforElementtobeclickable((xml.getlocator("//locators/PhnmgncountryforCH")));
						Clickon(getwebelement(xml.getlocator("//locators/PhnmgncountryforCH")));
						log("Click on the change country name for CH extension country");
					}
					else if(Inputdata[i][2].toString().equals("IT")) {
						WaitforElementtobeclickable((xml.getlocator("//locators/PhnmgncountryforIT")));
						Clickon(getwebelement(xml.getlocator("//locators/PhnmgncountryforIT")));
						log("Click on the change country name for IT extension country");
					}
					else {
					WaitforElementtobeclickable((xml.getlocator("//locators/PhnMgnCountry")));
					Clickon(getwebelement(xml.getlocator("//locators/PhnMgnCountry")));
					log("Click on the change country name");
					}
					switchtodefault();
					switchtoframe("fmMain");
					waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
					Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
					log("Select the country name is: " + Inputdata[i][2]);
					WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
					log("click on the select button");
					Thread.sleep(3000);
				}

			}
		}
		public void Country(Object[][] Inputdata) throws InterruptedException, Exception {
			for (int i = 0; i < Inputdata.length; i++) {
				Thread.sleep(3000);

				switchtoframe("fmMenu");
				WaitforElementtobeclickable((xml.getlocator("//locators/IECountryButton")));
				Clickon(getwebelement(xml.getlocator("//locators/IECountryButton")));
				log("Click on the change country name");
				switchtodefault();
				switchtoframe("fmMain");
				waitandForElementDisplayed(xml.getlocator("//locators/IECountryName"));
				Select(getwebelement(xml.getlocator("//locators/IECountryName")), Inputdata[i][2].toString());
				log("Select the country name is: " + Inputdata[i][2]);
				WaitforElementtobeclickable((xml.getlocator("//locators/IECountryNameButton")));
				Clickon(getwebelement(xml.getlocator("//locators/IECountryNameButton")));
				log("click on the select button");

			}
		}
			public void SplitNumbermain(Object[][] Inputdata) throws InterruptedException, Exception {
				for (int i = 0; i < Inputdata.length; i++) {
					UserGroupforSuperUser(Inputdata);
					Thread.sleep(4000);
					switchtodefault();
					switchtoframe("fmMenu");
					WaitforElementtobeclickable((xml.getlocator("//locators/IESplitNumberButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IESplitNumberButton")));
					log("click on the Split Free Range");
					switchtodefault();
					switchtoframe("fmMain");
					Thread.sleep(5000);
					SendKeys(getwebelement(xml.getlocator("//locators/IESplitNumber")), Inputdata[i][42].toString());
					log("Input the number:-" + Inputdata[i][42].toString());
					WaitforElementtobeclickable((xml.getlocator("//locators/IESplitSearchButton")));
					Clickon(getwebelement(xml.getlocator("//locators/IESplitSearchButton")));
					log("Click on the search button");
					Thread.sleep(3000);
					
					if (isElementPresent(xml.getlocator("//locators/Norecord")))
					{
							RedLog("No Record found please enter the valid Number");	
					}	
					else
                    {
						
						int rangefrom = Integer.parseInt(Gettext(getwebelement(xml.getlocator("//locators/Rangefromspit"))));
						int rangeto = Integer.parseInt(Gettext(getwebelement(xml.getlocator("//locators/Rangetospit"))));
						int Actual=rangeto-rangefrom;
						
						int ActualRange = Actual+1;
						System.out.println(ActualRange);
						log("Total range is:-"+ActualRange);
						
					// calculating the data from the Excel 	
						int firstsize = Integer.parseInt((Inputdata[i][22].toString()));
						log("First size is:-"+firstsize);
						int firstquantity= 0;
						if(Inputdata[i][23].toString()=="")
						{
							firstquantity=0;
						}
						else
						{
							 firstquantity = Integer.parseInt((Inputdata[i][23].toString()));

						}
						log("First quantity is:-"+firstquantity);
						int firstAll=firstsize*firstquantity;
						System.out.println(firstAll);

						int secondsize = Integer.parseInt((Inputdata[i][72].toString()));
						log("Second size is:-"+secondsize);
						int secondquantity= 0;
						if(Inputdata[i][73].toString()=="")
						{
							secondquantity=0;
						}
						else
						{
							secondquantity = Integer.parseInt((Inputdata[i][73].toString()));

						}
						log("Second Quantity is:-"+secondquantity);
						int secondAll=secondsize*secondquantity;
						System.out.println(secondAll);
						int thirdsize = Integer.parseInt((Inputdata[i][74].toString()));
						log("third size is:-"+thirdsize);
						int thirdquantity= 0;
						if(Inputdata[i][75].toString()=="")
						{
							thirdquantity=0;
						}
						else
						{
							thirdquantity = Integer.parseInt((Inputdata[i][75].toString()));

						}
						log("Third quantity is:-"+thirdquantity);
						int thirdAll=thirdsize*thirdquantity;
						System.out.println(thirdAll);
						int fourthsize = Integer.parseInt((Inputdata[i][76].toString()));
						log("Fourth size is:-"+fourthsize);
						int fourthquantity= 0;
						if(Inputdata[i][77].toString()=="")
						{
							fourthquantity=0;
						}
						else
						{
							fourthquantity = Integer.parseInt((Inputdata[i][77].toString()));

						}
						log("Fourth quantity is:-"+fourthquantity);
						
						int fourthAll=fourthsize*fourthquantity;
						System.out.println(fourthAll);
						 
						
						
							
						if(firstAll+secondAll+thirdAll+fourthAll == ActualRange)
						{
							if(firstquantity == 0  )//we need to calculate the null value as well here
							{
				log("Since you hav't filled any data Quantity to create for block size 1 so we are checking with another quantity");
							}
							else 
							{
								Thread.sleep(2000);
								SendKeys(getwebelement(xml.getlocator("//locators/IESplitblockSizesplit")), Inputdata[i][22].toString());
								log("first block size is:-" + Inputdata[i][22].toString());
								Thread.sleep(3000);
								log("entering again the block size - 1");
								SendKeys(getwebelement(xml.getlocator("//locators/IESplitQuantitysizesplit")), Inputdata[i][23].toString());
								log("first quantity is:-" + Inputdata[i][23].toString());
								Thread.sleep(3000);
								log("please enter the quantity for block size number");
								SendKeys(getwebelement(xml.getlocator("//locators/IESplitQuantitysizesplit")), Inputdata[i][23].toString());
								log("first quantity size is:-" + Inputdata[i][23].toString());
								Thread.sleep(3000);
								WaitforElementtobeclickable((xml.getlocator("//locators/SplitAddButn")));				
								Clickon(getwebelement(xml.getlocator("//locators/SplitAddButn")));
								Clickon(getwebelement(xml.getlocator("//locators/SplitAddButn")));
							}
							if(secondquantity == 0 )//we need to calculate the null value as well here
							{
				log("Since you hav't filled any data Quantity to create for block size 10 so we are checking with another quantity");
							}
							else 
							{
								Thread.sleep(2000);
								
										Thread.sleep(3000);
										log("entering again the block size - 10");
										SendKeys(getwebelement(xml.getlocator("//locators/Splitblocksize10")), Inputdata[i][72].toString());
										log("please enter the quantity for one block size number:-"+Inputdata[i][72].toString());
										Thread.sleep(3000);
										SendKeys(getwebelement(xml.getlocator("//locators/SplitQuantitysize10")),
												Inputdata[i][73].toString());
										log("please enter the size for 10 block :-"+Inputdata[i][73].toString());
										Thread.sleep(3000);
										SendKeys(getwebelement(xml.getlocator("//locators/SplitQuantitysize10")),
												Inputdata[i][73].toString());
										log("please again enter the size for 10 block :-"+Inputdata[i][73].toString());
										Thread.sleep(3000);
										WaitforElementtobeclickable((xml.getlocator("//locators/SplitAddButn")));
										Clickon(getwebelement(xml.getlocator("//locators/SplitAddButn")));
										Clickon(getwebelement(xml.getlocator("//locators/SplitAddButn")));
										log("Click on the split add button");
							}
							if(thirdquantity == 0 )//we need to calculate the null value as well here
							{
				log("Since you hav't filled any data Quantity to create for block size 100 so we are checking with another quantity");
							}
							else 
							{
								Thread.sleep(3000);
								log("entering again the block size - 100");
								SendKeys(getwebelement(xml.getlocator("//locators/Splitblocksize100")), Inputdata[i][74].toString());
								log("enter the size for 100 block:-"+Inputdata[i][74].toString());
								//log("please enter the quantity for one block size number");
								Thread.sleep(3000);
								SendKeys(getwebelement(xml.getlocator("//locators/SplitQuantitysize100")),
										Inputdata[i][75].toString());
								log("enter the quantity for 100 block size is:-"+Inputdata[i][75].toString());
								Thread.sleep(3000);
								SendKeys(getwebelement(xml.getlocator("//locators/SplitQuantitysize100")),
										Inputdata[i][75].toString());
								log("again enter the quantity for 100 block size is:-"+Inputdata[i][75].toString());
								Thread.sleep(5000);
								//WaitforElementtobeclickable((xml.getlocator("//locators/splitaddbtn3")));
								Clickon(getwebelement(xml.getlocator("//locators/SplitAddButn")));
								Clickon(getwebelement(xml.getlocator("//locators/SplitAddButn")));
								log("Click on the split add button");
							}
							if(fourthquantity == 0 )//we need to calculate the null value as well here
							{
				log("Since you hav't filled any data Quantity to create for block size 1000 so it will not be created");
				Thread.sleep(20000);
							}
							else 
							{
								Thread.sleep(3000);
								log("entering again the block size - 1000");
								SendKeys(getwebelement(xml.getlocator("//locators/Splitblocksize1000")), Inputdata[i][76].toString());
								log("please enter the block size for 1000 block size number:-"+Inputdata[i][76].toString());
								Thread.sleep(3000);
								SendKeys(getwebelement(xml.getlocator("//locators/SplitQuantitysize1000")),
										Inputdata[i][77].toString());
								log(" enter the quantity for 1000 block size is:-"+Inputdata[i][77].toString());
								Thread.sleep(3000);
								SendKeys(getwebelement(xml.getlocator("//locators/SplitQuantitysize1000")),
										Inputdata[i][77].toString());
								log("again enter the quantity for 1000 block size is:-"+Inputdata[i][77].toString());
								Thread.sleep(3000);
								WaitforElementtobeclickable((xml.getlocator("//locators/splitaddabtn4")));
								Clickon(getwebelement(xml.getlocator("//locators/SplitAddButn")));
								Clickon(getwebelement(xml.getlocator("//locators/SplitAddButn")));
								log("Click on the split add button");
							}
							Thread.sleep(3000);
							WaitforElementtobeclickable((xml.getlocator("//locators/splitFreenumbtn")));
							Clickon(getwebelement(xml.getlocator("//locators/splitFreenumbtn")));
							log("Click on Save free number button ");
							
						}	
							else {
								RedLog("it seems like you have'nt calculated the correct Block size and Quantity in your Excel sheet :( please check qand correct it ");
							}
						
						}
	
				}
			//shashank	
				}}
