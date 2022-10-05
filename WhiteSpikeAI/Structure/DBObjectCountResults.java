package Structure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import MainLLF.Constants;

public class DBObjectCountResults {
	public int senseCount = -1;
	public int orCount = -1;
	public int senseDefCount = -1;
	public int envCount = -1;
	public int activityCount = -1;
	public int orChangeCount = -1;
	
	public DBObjectCountResults () {
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT COUNT(*) as total FROM Sense";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			try {
				this.senseCount = rs.getInt("total");
				sqlCommand = "SELECT COUNT(*) as total FROM Orientation";
				ResultSet rs2 = myState.executeQuery(sqlCommand);
				rs2.next();
				try {
					this.orCount = rs2.getInt("total");
					sqlCommand = "SELECT COUNT(*) as total FROM SenseDefinition";
					ResultSet rs3 = myState.executeQuery(sqlCommand);
					rs3.next();
					try {
						this.senseDefCount = rs3.getInt("total");
						sqlCommand = "SELECT COUNT(*) as total FROM Env";
						ResultSet rs4 = myState.executeQuery(sqlCommand);
						rs4.next();
						try {
							this.envCount = rs4.getInt("total");
							sqlCommand = "SELECT COUNT(*) as total FROM Activity";
							ResultSet rs5 = myState.executeQuery(sqlCommand);
							rs5.next();
							try {
								this.activityCount = rs5.getInt("total");
								sqlCommand = "SELECT COUNT(*) as total FROM OrientationChange";
								ResultSet rs6 = myState.executeQuery(sqlCommand);
								rs6.next();
								try {
									this.orChangeCount = rs6.getInt("total");
								} catch (Exception e) {
									
								}
							} catch (Exception e) {
								
							}
						} catch (Exception e) {
							
						}
					} catch (Exception e) {
						
					}
				} catch (Exception e) {
					
				}
			} catch (Exception e) {
				
			}
		} catch (Exception e) {
			
		}
	}
}
