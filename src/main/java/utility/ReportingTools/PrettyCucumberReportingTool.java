package utility.ReportingTools;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrettyCucumberReportingTool {


    public static void generateReport(){

        File reportOutputDirectory = new File("target/MavenReport");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber.json");

        String buildNumber = "1";
        String projectName = "SQAautomation";

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);

        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);

        configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
        configuration.setBuildNumber(buildNumber);

        configuration.addClassifications("Platform", "Windows");
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Branch", "release/1.0");
        configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
        configuration.setQualifier("cucumber-report-1","First report");
        configuration.setQualifier("cucumber-report-2","Second report");

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        Reportable result = reportBuilder.generateReports();


    }




}
