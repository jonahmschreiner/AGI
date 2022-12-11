package ActivitySolverServiceMethods;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.RawEnvClosingPrepWrapper;

public class RawEnvClosingPrep {
	public static RawEnvClosingPrepWrapper exec(ExecuteActivityInputWrapper eaiw, ExecuteActivityPrepWrapper eapw) {
		RawEnvClosingPrepWrapper recpw = new RawEnvClosingPrepWrapper();
		try {
			Statement myState = eaiw.myConnection.createStatement();
			recpw.existingConEnvCpuUsage = 0;
			recpw.existingConEnvLDT = LocalDateTime.MAX;
			recpw.existingConEnvMouseX = -5;
			recpw.existingConEnvMouseY = -5;
			try {
				String sqlCommand = "SELECT CurrentDateTime, CpuUsage, MouseX, MouseY FROM ConditionEnv WHERE id=" + eapw.conditionEnvDBId + ";";
				ResultSet rs5 = myState.executeQuery(sqlCommand);
				rs5.next();
				recpw.existingConEnvCpuUsage = rs5.getDouble("CpuUsage");
				recpw.existingConEnvLDT = LocalDateTime.parse(rs5.getString("CurrentDateTime"));
				recpw.existingConEnvMouseX = rs5.getInt("MouseX");
				recpw.existingConEnvMouseY = rs5.getInt("MouseY");
			} catch (Exception f) {
				f.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return recpw;
	}
}
