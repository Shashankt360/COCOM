package Driver;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class UpdateCSV {
	
public static void updateCSV() throws IOException
{
	String[] coulumn = null;
	String InputCsvFilename = "src\\Data\\Deal_Pricing_CSV_upload.csv";
	String OutputCsv = "src\\Data\\output1.csv";
	String updateddata = "";
	List<String[]> data = new ArrayList<String[]>();
	CSVReader csvReader = new CSVReader(new FileReader(InputCsvFilename));
	
 
	CSVWriter writer = new CSVWriter(new FileWriter(OutputCsv));
	List content = csvReader.readAll();
	System.out.println("Total Number of Rows"+content.size());
	for (Object object : content) 
	{
		coulumn = (String[]) object;
		data.add(new String[] {coulumn[0], coulumn[1]});
		updateddata=updateddata+coulumn[0]+ "," + coulumn[1]+"\n";
		//updateddata.add(new String[] {coulumn[0]+ "," + coulumn[1]+"\n"});
		System.out.println(coulumn[0]+ "," + coulumn[1]);
	}
	
	 
	
	System.out.println("___________________________________________");
	System.out.println(data.size());
	System.out.print(updateddata);
	writer.writeAll(data);
//	bw.write(updateddata);
	csvReader.close();
//	bw.close();
	writer.close();
	
	

//	List<String[]> data = new ArrayList<String[]>();
//	data.add(new String[] {"India", "New Delhi"});
//	data.add(new String[] {"United States", "Washington D.C"});
//	data.add(new String[] {"Germany", "Berlin"});

//	writer.writeAll(data);

	
	
	
}


//public static void WriteCSV() throws IOException {
//String csv = "src\\Data\\output2.csv";
//CSVWriter writer = new CSVWriter(new FileWriter(csv));
//
//List<String[]> data = new ArrayList<String[]>();
//data.add(new String[] {"India", "New Delhi"});
//data.add(new String[] {"United States", "Washington D.C"});
//data.add(new String[] {"Germany", "Berlin"});
//
//writer.writeAll(data);
//
//writer.close();
//}
	public static void main(String args[]) throws IOException 
	{
		updateCSV();
		//WriteCSV();
	}
}
