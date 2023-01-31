/*
 * 
 */
package utility;

import java.sql.*;



public class OracleConnection  {

	private Connection conn;
	private Statement statement;

	public OracleConnection() {
		// Get user info
		String userid = ReadPropFile.config.get("OracleUserId");
		String password = ReadPropFile.config.get("OraclePassword");

		// Setup connection
		String dbUrl = "";
		try {
			this.conn = DriverManager.getConnection(dbUrl, userid, password);
			this.statement = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cleanup() {
		try {
			this.statement.close();
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sqlQuery) {
		ResultSet resultSet = null;
		try {
			resultSet = this.statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public ResultSet queryUntilTimeout(String sqlQuery, int timeoutSec, int waitSec) {
		int elapsedSec = 0;
		ResultSet resultSet = null;
		boolean rowsFound = false;

		try {
			// While loop until resultSet returns at least 1 row
			do {
				// Output current status
				System.out.println("Querying Server | Total Time Elapsed: " + elapsedSec + " Second(s) out of "
						+ timeoutSec + " Seconds | Checking every " + waitSec + " Second(s)");

				// Execute query
				resultSet = executeQuery(sqlQuery);

				// Get query results
				if (resultSet.next()) {
					rowsFound = true;
				}

				// Wait for next query if not found
				if (!rowsFound) {
					Thread.sleep(waitSec * 1000);
					elapsedSec += waitSec;
				}
				
			} while (!rowsFound && elapsedSec < timeoutSec);

			if (!rowsFound) {
				System.out.println("Query reached timeout of " + timeoutSec + " Seconds");
				resultSet = null;
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return resultSet;
	}

	public int getInt(ResultSet resultSet, String colName) {
		int result = -1;
		try {
			result = resultSet.getInt(colName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getString(ResultSet resultSet, String colName) {
		String result = null;
		try {
			result = resultSet.getString(colName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}
