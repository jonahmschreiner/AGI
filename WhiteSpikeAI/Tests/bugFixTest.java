package Tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import EnvAndDatabaseServiceMethods.ExecuteCoreAction;

public class bugFixTest {
	public static void main (String[] args) throws IOException, InterruptedException {
		File file = new File ("/home/agi/Desktop/test.txt");
		FileWriter fw = new FileWriter(file);
		Thread.sleep(2000);
		for (int i = 0; i < 430; i++) {
			fw.append(i + "\n");
			fw.flush();
			Thread.sleep(200);
			ExecuteCoreAction.exec(i, fw);
		}
	}
}
