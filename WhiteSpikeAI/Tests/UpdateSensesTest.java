package Tests;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import EnvAndDatabaseServiceMethods.BlobToSense;
import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv;
import EnvAndDatabaseServiceMethods.ChangedPixelsToBlobs;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.RemoveOldSensesFromEnv;
import EnvAndDatabaseServiceMethods.SenseCombiner;
import EnvAndDatabaseServiceMethods.UpdateEnv;
import EnvAndDatabaseServiceMethods.UpdateSenses;
import EnvAndDatabaseServiceMethods.UploadOrientationChangesToDB;
import EnvAndDatabaseServiceMethods.VisualOutputOfSensesFromSensesAndImage;
import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv.junctionList;
import MainLLF.Constants;
import Structure.Blob;
import Structure.Env;
import Structure.Pixel;
import Structure.Sense;

public class UpdateSensesTest {
	public static void main(String[] args) {
		//Reset DB
		DatabaseHandler.main(null);
		try {
			Thread.sleep(1000);
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Env env = new Env();
			DatabaseHandler.uploadEnvToDatabase(env, myConnection);
			VisualOutputOfSensesFromSensesAndImage.execute(env.abstractEnv.senses, env.rawEnv.currentDisplay, "before");
			Thread.sleep(3000);
			env = UpdateEnv.update(env, myConnection);
			VisualOutputOfSensesFromSensesAndImage.execute(env.abstractEnv.senses, env.rawEnv.currentDisplay, "after");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
