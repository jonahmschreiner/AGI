package EnvAndDatabaseServiceMethods;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Structure.CreateEnvInJavaFromConditionEnvInDBWrapper;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.PixelOverallChange;
import Structure.Sense;

public class CreateEnvInJavaFromConditionEnvInDB {
	public static CreateEnvInJavaFromConditionEnvInDBWrapper exec(ExecuteActivityInputWrapper eaiw, ExecuteActivityPrepWrapper eapw) throws Exception {
			Statement myState = eaiw.myConnection.createStatement();
			eapw.conditionEnvDBId = eapw.activityRS.getInt("ConditionEnv");
			String sqlCommand = "SELECT Senses FROM ConditionEnv WHERE id=" + eapw.conditionEnvDBId + ";";
			ResultSet rs2 = myState.executeQuery(sqlCommand);
			rs2.next();
			String senseIdString = rs2.getString("Senses");
			String[] senseIdStringArray = senseIdString.split(" ");
			String inString = "(";
			for (int j = 0; j < senseIdStringArray.length; j++) {
				inString = inString + senseIdStringArray[j];
				if (j < senseIdStringArray.length - 1) {
					inString = inString + ", ";
				}
			}
			inString = inString + ")";
			sqlCommand = "SELECT ConditionSenseDefinition.Definition, ConditionOrientation.Height, ConditionOrientation.Width, ConditionOrientation.Rotation, ConditionOrientation.x, ConditionOrientation.y, ConditionOrientation.color FROM ConditionSense INNER JOIN ConditionOrientation ON ConditionSense.ConditionOrientation=ConditionOrientation.id INNER JOIN ConditionSenseDefinition ON ConditionSense.ConditionSenseDefinition=ConditionSenseDefinition.id WHERE ConditionSense.id IN" + inString + ";";
			eaiw.fw.append("SQL IDK2: " + sqlCommand);
			eaiw.fw.flush();
			ResultSet rs3 = myState.executeQuery(sqlCommand);
			List<Sense> conditionEnvSenses = new ArrayList<Sense>();
			while (true) {
				try {
					rs3.next();
					Sense newSense = new Sense();
					newSense.orientation.height = rs3.getInt("ConditionOrientation.Height");
					newSense.orientation.width = rs3.getInt("ConditionOrientation.Width");
					newSense.orientation.rotation = rs3.getInt("ConditionOrientation.Rotation");
					newSense.orientation.position.x = rs3.getInt("ConditionOrientation.x");
					newSense.orientation.position.y = rs3.getInt("ConditionOrientation.y");
					Color color = CreateColorFromRange.exec(rs3.getString("ConditionOrientation.color"));
					newSense.orientation.color = color;
					List<PixelOverallChange> pocs = new ArrayList<PixelOverallChange>();
					String[] changeDefValues = rs3.getString("ConditionSenseDefinition.Definition").split(";");
					for (int z = 0; z < changeDefValues.length; z++) {
						pocs.add(new PixelOverallChange(changeDefValues[z]));
					}
					newSense.definition.overallChangeDefString = pocs;
					
					conditionEnvSenses.add(newSense);
				} catch (Exception g) {
					break;
				}
			}
			eaiw.fw.append(" EXECACT: Re-creating condition env from db finished\n");
			eaiw.fw.flush();
			CreateEnvInJavaFromConditionEnvInDBWrapper wrapper = new CreateEnvInJavaFromConditionEnvInDBWrapper();
			wrapper.conditionEnvSenses = conditionEnvSenses;
			wrapper.eaiw = eaiw;
			wrapper.eapw = eapw;
		return wrapper;
	}
}
