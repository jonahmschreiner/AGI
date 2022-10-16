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
	public int conditionSenseCount = -1;
	public int conditionSenseDefCount = -1;
	public int conditionOrientationChangeCount = -1;
	public int conditionOrientationCount = -1;
	public int conditionEnvCount = -1;
	
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
									sqlCommand = "SELECT COUNT(*) as total FROM ConditionOrientationChange";
									ResultSet rs7 = myState.executeQuery(sqlCommand);
									rs7.next();
									try {
										this.conditionOrientationChangeCount = rs7.getInt("total");
										sqlCommand = "SELECT COUNT(*) as total FROM ConditionOrientation";
										ResultSet rs8 = myState.executeQuery(sqlCommand);
										rs8.next();
										try {
											this.conditionOrientationCount = rs8.getInt("total");
											sqlCommand = "SELECT COUNT(*) as total FROM ConditionSenseDefinition";
											ResultSet rs9 = myState.executeQuery(sqlCommand);
											rs9.next();
											try {
												this.conditionSenseDefCount = rs9.getInt("total");
												sqlCommand = "SELECT COUNT(*) as total FROM ConditionSense";
												ResultSet rs10 = myState.executeQuery(sqlCommand);
												rs10.next();
												try {
													this.conditionSenseCount = rs10.getInt("total");
													sqlCommand = "SELECT COUNT(*) as total FROM ConditionEnv";
													ResultSet rs11 = myState.executeQuery(sqlCommand);
													rs11.next();
													try {
														this.conditionEnvCount = rs11.getInt("total");
													} catch (Exception e) {
														e.printStackTrace();
													}
												} catch (Exception e) {
													e.printStackTrace();
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
