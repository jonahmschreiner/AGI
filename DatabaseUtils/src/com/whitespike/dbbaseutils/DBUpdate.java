package com.whitespike.dbbaseutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whitespike.dbcontrol.Constants;

public class DBUpdate {
	public static <T, S> void execAll(List<String> objects, Map<String, List<String>> columns, Map<String, List<T>> values, List<List<String>> conditionColumns, List<List<S>> conditionValues, List<List<String>> conditionConditions, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			for (int i = 0; i < objects.size(); i++) {
				String currObj = objects.get(i);
				List<String> currColumns = columns.get(currObj);
				List<?> currValues = values.get(currObj);
				String sqlCommand = "UPDATE " + currObj + " SET ";
				for (int j = 0; j < currColumns.size(); j++) {
					String currColumn = currColumns.get(j);
					sqlCommand = sqlCommand + currColumn + "=";
					try {
						String currValue = (String) currValues.get(j);
						sqlCommand = sqlCommand + "\"" + currValue + "\"";
					} catch (Exception e) {
						String currValue = String.valueOf(currValues.get(j));
						sqlCommand = sqlCommand + currValue;
					}
					if (j < currColumns.size() - 1) {
						sqlCommand = sqlCommand + ", ";
					}
				}
				if (conditionColumns.get(i).size() > 0) {
					sqlCommand = sqlCommand + "WHERE ";
					List<String> currConColumns = conditionColumns.get(i);
					List<S> currConValues = conditionValues.get(i);
					List<String> currConConditions = conditionConditions.get(i);
					for (int j = 0; j < currConColumns.size(); j++) {
						String currValue;
						try {
							currValue = "\"" + (String) currConValues.get(j) + "\"";
						} catch (Exception e) {
							currValue = String.valueOf(currConValues.get(j));
						}
						sqlCommand = sqlCommand + currConColumns.get(j) + "=" + currValue;
						if (j < currConColumns.size() - 1) {
							sqlCommand = sqlCommand + " " + currConConditions.get(j) + " ";
						}
					}
				}
				sqlCommand = sqlCommand + ";";
				myState.execute(sqlCommand);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static <T, S> void exec(String object, List<String> columns, List<T> values, List<String> conditionColumns, List<S> conditionValues, List<String> conditionConditions, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
				String sqlCommand = "UPDATE " + object + " SET ";
				for (int j = 0; j < columns.size(); j++) {
					String currColumn = columns.get(j);
					sqlCommand = sqlCommand + currColumn + "=";
					try {
						String currValue = (String) values.get(j);
						sqlCommand = sqlCommand + "\"" + currValue + "\"";
					} catch (Exception e) {
						String currValue = String.valueOf(values.get(j));
						sqlCommand = sqlCommand + currValue;
					}
					if (j < columns.size() - 1) {
						sqlCommand = sqlCommand + ", ";
					}
				}
				if (conditionColumns.size() > 0) {
					sqlCommand = sqlCommand + "WHERE ";
					for (int j = 0; j < conditionColumns.size(); j++) {
						String currValue;
						try {
							currValue = "\"" + (String) conditionValues.get(j) + "\"";
						} catch (Exception e) {
							currValue = String.valueOf(conditionValues.get(j));
						}
						sqlCommand = sqlCommand + conditionColumns.get(j) + "=" + currValue;
						if (j < conditionColumns.size() - 1) {
							sqlCommand = sqlCommand + " " + conditionConditions.get(j) + " ";
						}
					}
				}
				sqlCommand = sqlCommand + ";";
				myState.execute(sqlCommand);
		} catch (Exception e){
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
			List<String> conCols = new ArrayList<String>();
			List<Integer> conVals = new ArrayList<Integer>();
			List<String> conCons = new ArrayList<String>();
			exec("CountObj", columns, values, conCols, conVals, conCons, myConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
