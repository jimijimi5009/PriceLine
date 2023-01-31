package utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlServerConnection  {


    private Connection conn;
    private Statement statement;


    public SqlServerConnection(String databaseName)   {

        // Setup connection

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://GP2010SQC01-STG\\GP;databaseName=" + databaseName + ";integratedSecurity=true;encrypt=true;trustServerCertificate=true";
            this.conn = DriverManager.getConnection(dbUrl);
            this.statement = conn.createStatement();
        } catch (SQLException  e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
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

    public  boolean isMyResultSetEmpty(ResultSet rs) throws SQLException {
        return (!rs.isBeforeFirst() && rs.getRow() == 0);
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

    public Object getCellValue(String query) throws SQLException {
        System.out.println(getQueryResultList(query).get(0).get(0).toString());
        return getQueryResultList(query).get(0).get(0);
    }

    public List<Object> getRowList(String query) throws SQLException {
        System.out.println(getQueryResultList(query).get(0).toString());
        return getQueryResultList(query).get(0);
    }


    public List<List<Object>> getQueryResultList(String query) throws SQLException {
        ResultSet resultSet = this.statement.executeQuery(query);

        List<List<Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.add(resultSet.getObject(i));
                }
                rowList.add(row);
                System.out.println(rowList.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowList;
    }


    public Object getColumnData(String query, String column, int row) throws SQLException {
        ResultSet resultSet = this.statement.executeQuery(query);

        List<Object> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                rowList.add(resultSet.getObject(column));
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        System.out.println(rowList);
        if(rowList.size()>0){
            return rowList.get(row).toString();
        }else return rowList;

    }

    public List<String> getColumnNames(String query) throws SQLException {
        ResultSet resultSet = this.statement.executeQuery(query);

        List<String> columns = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(columns.toString());
        return columns;
    }

    public int getRowCount(String query) throws Exception {
        ResultSet resultSet = this.statement.executeQuery(query);
        resultSet.last();
        int rowCount = resultSet.getRow();
        System.out.println(rowCount);
        return rowCount;
    }

}

