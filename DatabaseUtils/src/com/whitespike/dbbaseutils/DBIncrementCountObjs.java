package com.whitespike.dbbaseutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import com.whitespike.dbcontrol.Constants;

public class DBIncrementCountObjs {
	public static void execAll(List<String> columns, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			for (int i = 0; i < columns.size(); i++) {
				String currColumn = columns.get(i);
				currColumn = currColumn + "Count";
				if (currColumn.startsWith("Condition")) {
					String sqlCommand = "UPDATE ConditionCountObj SET " + currColumn + "=" + currColumn + " + 1;";
					myState.execute(sqlCommand);
				} else {
					String sqlCommand = "UPDATE CountObj SET " + currColumn + "=" + currColumn + " + 1;";
					myState.execute(sqlCommand);
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void exec(String column, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			column = column + "Count";
			if (column.startsWith("Condition")) {
				String sqlCommand = "UPDATE ConditionCountObj SET " + column + "=" + column + " + 1;";
				myState.execute(sqlCommand);
			} else {
				String sqlCommand = "UPDATE CountObj SET " + column + "=" + column + " + 1;";
				myState.execute(sqlCommand);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			exec("ConditionEnv", myConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
