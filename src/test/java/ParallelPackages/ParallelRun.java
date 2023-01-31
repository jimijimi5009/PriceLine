package ParallelPackages;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
		plugin = {"pretty:target/cucumber-report/Smoke/pretty.txt",
				"html:target/cucumber-html-report",
				"json:target/cucumber-report/Smoke/cucumber.json",
				"junit:target/cucumber-report/Smoke/cucumber-junitreport.xml",
				"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
				"json:target/cucumber.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		tags = "@VisitUsingEmp",
		monochrome = true,
		glue = { "ParallelPackages" },
		features = { "src/test/resources/ParallelFeatures/AlayaCareTestParallel.feature" }

)

public class ParallelRun extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)


	public Object[][] scenarios() {
		return super.scenarios();
	}
}