package ru.matprojects.sqlexcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class SqlExcel {
    private static final String DATA_FOLDER = "./resdata/";
    public static void main(String[] args) {
        PostgreConnection psConn = new PostgreConnection();

        List<String> sqlExecutes = psConn.getExecuteSqls();

        Connection connection = psConn.getConnection();

        int sqlCnt = 0;
        for (String sqlExecute : sqlExecutes) {
            sqlCnt++;
            Workbook book = new XSSFWorkbook();
            Sheet sheet = book.createSheet("sqlResult");

            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sqlExecute);
                ResultSetMetaData rsMeta = rs.getMetaData();
                int rowCnt = 0;
                Row row = sheet.createRow(rowCnt);
                for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(rsMeta.getColumnLabel(i));
                }

                rowCnt++;
                while (rs.next()) {
                    row = sheet.createRow(rowCnt);
                    rowCnt++;
                    for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellValue(rs.getString(i));
                    }
                }

                new File(DATA_FOLDER).mkdirs();
                String fileName = "outputFile_" + sqlCnt + ".xlsx";
                book.write(new FileOutputStream(DATA_FOLDER + fileName));
            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        psConn.closeConnection();
    }
}
