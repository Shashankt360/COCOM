package Testscript;

import java.io.IOException;

import org.testng.annotations.Test;

public class SoupUITest {
	
	
	@Test
	public void SoupUItesting()
	{

String path="cmd /c start C:\\Users\\STIWARI1\\Desktop\\man.bat";

Runtime rn=Runtime.getRuntime();
try {
rn.exec(path);
} catch (IOException e) {

e.printStackTrace();
}
}
	}
	

