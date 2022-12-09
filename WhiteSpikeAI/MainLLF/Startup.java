package MainLLF;

import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import EnvAndDatabaseServiceMethods.DatabaseHandler;
import Structure.Env;
import Structure.StartupWrapper;

public class Startup {
	public static StartupWrapper exec() {
		StartupWrapper sw = new StartupWrapper();
		try {
			Frame frame = new Frame();
			JOptionPane.showMessageDialog(frame, "White Spike was initialized");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			File logFile = new File("/home/agi/Downloads/log.txt");
			FileWriter fw = new FileWriter(logFile);
			sw.fw = fw;
			LocalDateTime ldt = LocalDateTime.now();
			fw.append(ldt.toString() + "\n-------\n");
			DatabaseHandler.doSetupIfNecessary();
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			sw.myConnection = myConnection;
			Statement myStatement = myConnection.createStatement();
			sw.myStatement = myStatement;
			System.out.println("db setup done");
			fw.append(" db setup done\n");
			fw.flush();
			Env env = new Env();
			sw.env = env;
			System.out.println("initial env created");
			fw.append(" initial env created (Num of Senses: " + env.abstractEnv.senses.size() + ")\n");
			fw.flush();
			DatabaseHandler.uploadEnvToDatabase(env, myConnection);
			System.out.println("env uploaded to db");
			fw.append(" env uploaded to db\n");
			fw.flush();
		} catch (Exception e) {
			System.out.println("A startup exception occurred");
			e.printStackTrace();
		}
		return sw;
	}
}
