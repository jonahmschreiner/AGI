package com.whitespike.dbbaseutils;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class DBDelete {
	public static <T> void execAll(List<String> objects, List<List<String>> conditionColumns, List<List<T>> conditionValues, List<List<String>> conditionConditions, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			for (int i = 0; i < objects.size(); i++) {
				String sqlCommand = "DELETE FROM " + objects.get(i);
				List<String> currConCols = conditionColumns.get(i);
				List<String> currConCons = conditionConditions.get(i);
				List<T> currConVals = conditionValues.get(i);
				if (currConCols.size() > 0) {
					sqlCommand = sqlCommand + " WHERE ";
					for (int j = 0; j < currConCols.size(); j++) {
						sqlCommand = sqlCommand + currConCols.get(j) + "=" + currConVals.get(j);
						if (j < currConCols.size() - 1) {
							sqlCommand = sqlCommand + " " + currConCons.get(j) + " ";
						}
					}
				}
				sqlCommand = sqlCommand + ";";
				myState.execute(sqlCommand);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
