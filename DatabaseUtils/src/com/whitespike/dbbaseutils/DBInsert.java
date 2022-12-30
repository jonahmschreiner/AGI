package com.whitespike.dbbaseutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whitespike.dbcontrol.Constants;

public class DBInsert {
	public static <T> void execAll(List<String> objects, Map<String, List<String>> columns, Map<String, List<T>> values, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			for (int i = 0; i < objects.size(); i++) {
				String currObj = objects.get(i);
				List<String> currColumns = columns.get(currObj);
				String sqlCommand = "INSERT INTO " + currObj + " (";
				for (int j = 0; j < currColumns.size(); j++) {
					String currColumn = currColumns.get(j);
					sqlCommand = sqlCommand + currColumn;
					if (j < currColumns.size() - 1) {
						sqlCommand = sqlCommand + ", ";
					}
				}
				sqlCommand = sqlCommand + ") VALUES (";
				List<?> currValues = values.get(currObj);
				for (int j = 0; j < currValues.size(); j++) {
					String currValue = (String) currValues.get(j);
					sqlCommand = sqlCommand + currValue;
					if (j < currValues.size() - 1) {
						sqlCommand = sqlCommand + ", ";
					}
				}
				sqlCommand = sqlCommand + ");";
				myState.execute(sqlCommand);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static <T> void exec(String object, List<String> columns, List<T> values, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "INSERT INTO " + object + " (";
			for (int j = 0; j < columns.size(); j++) {
				String currColumn = columns.get(j);
				sqlCommand = sqlCommand + currColumn;
				if (j < columns.size() - 1) {
					sqlCommand = sqlCommand + ", ";
				}
			}
			sqlCommand = sqlCommand + ") VALUES (";
			for (int j = 0; j < values.size(); j++) {
				try {
					String currValue = (String) values.get(j);
					sqlCommand = sqlCommand + currValue;
				} catch (Exception e) {
					String currValue = String.valueOf(values.get(j));
					sqlCommand = sqlCommand + currValue;
				}
				
				if (j < values.size() - 1) {
					sqlCommand = sqlCommand + ", ";
				}
			}
			sqlCommand = sqlCommand + ");";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			List<String> columns = new ArrayList<String>();
			columns.add("EnvCount");
			columns.add("SenseCount");
			List<Double> values = new ArrayList<Double>();
			values.add(5.0);
			values.add(5.0);
			exec("CountObj", columns, values, myConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
