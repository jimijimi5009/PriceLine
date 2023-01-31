package Pages;


import CommonPages.PageElements;
import CommonPages.SeleniumElements;
import io.cucumber.datatable.DataTable;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import utility.*;
import utility.ReportingTools.TestAllureListener;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

@Listeners({TestAllureListener.class})
public class DBValidationPage {

    private final WebDriver driver;
    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public DBValidationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }



    SqlServerConnection connection;
    List<Map<String, String>> testData;
    String excelPath = Const.getExcelPath("bayada_payroll_export_28.xlsx");
    String sheetName = "Sheet1";


    @Step("Connecting to Database")
    public void connectToDatabase() {
        try {
            connection = new SqlServerConnection("ODSDB");
            TestAllureListener.saveTextLog("Successfully Connected to Database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("Validating Post date Column in DB ")
    public boolean validatePostColumData() {
        boolean flag = false;
        try {
            if (!ExcelReader.isFileClosed(excelPath)) {
                System.out.println("Excel is open.");
                OsUtill.killAllProcesses("excel.exe");
            }
            testData = ExcelReader.getData(excelPath, sheetName);
            int dataCount = ExcelReader.getRowCount(excelPath, sheetName, 1);
            List<String> failedPostData = new ArrayList<>();
            List<String> dataBaseEmpty = new ArrayList<>();
            for (int i = 0; i < dataCount; i++) {

                String getAdjustmentIndicator = testData.get(i).get("adjustment_indicator");
                String getAlayacareVisitID = testData.get(i).get("alayacare_visit_id");
                if (getAdjustmentIndicator.contains("0") && StringUtils.isNotBlank(getAlayacareVisitID)) {
                    getAlayacareVisitID = "AAC-V" + getAlayacareVisitID;

                } else if (getAdjustmentIndicator.contains("0") && StringUtils.isBlank(getAlayacareVisitID)) {
                    getAlayacareVisitID = "AAC-C" + getAlayacareVisitID;

                } else if (getAdjustmentIndicator.contains("1") && StringUtils.isBlank(getAlayacareVisitID)) {
                    getAlayacareVisitID = "AAC-A" + getAlayacareVisitID;

                }

                LogUtil.setLog("Checking visit id... : " + getAlayacareVisitID);

                String sqlQuery = Const.getOdsQuery(getAlayacareVisitID);
                ResultSet resultSet = connection.executeQuery(sqlQuery);
                List<String> getDBDataFromRow = new ArrayList<>();

                if (!connection.isMyResultSetEmpty(resultSet)) {
                    while (resultSet.next()) {
                        getDBDataFromRow.add(resultSet.getString("PostDate"));
                    }
                    for (String s : getDBDataFromRow) {
                        if (s == null) {
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        LogUtil.setLog("Validating Actual DB Data should be null but getting data is :- " + getDBDataFromRow);
                    } else {
                        failedPostData.add("Failed to match.. " + getAlayacareVisitID + " with " + getDBDataFromRow);
                        LogUtil.setLog(":- " + failedPostData);
                    }
                } else {
                    dataBaseEmpty.add("Failed!! visit id " + getAlayacareVisitID + " Database is Null...");
                }
            }

            Assert.assertEquals(0, failedPostData.size(), failedPostData.toString());
            Assert.assertEquals(0, dataBaseEmpty.size(), dataBaseEmpty.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }



}

