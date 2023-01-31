package testrunner.AlayaCareTestRunner;


import utility.ReportingTools.PrettyCucumberReportingTool;
import io.cucumber.testng.*;
import org.testng.annotations.*;
import utility.ReportingTools.ReportUtils;


@CucumberOptions(

		features = "src/test/resources/AlayaCareFeatures/",

		glue= {"StepDefinations", "Hooks"},

//		tags = "@VisitWithEmployee",
//		tags = "@AAC2",
		tags = "@VisitUsingEmp",
//		tags = "@demoTest",
//		tags = "@VisitUsingClient",
//		tags = "@AllEntity",


		plugin = {"pretty:target/cucumber-report/Smoke/pretty.txt",
				"html:target/cucumber-html-report",
				"json:target/cucumber-report/Smoke/cucumber.json",
				"junit:target/cucumber-report/Smoke/cucumber-junitreport.xml",
				"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
				"json:target/cucumber.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		monochrome=true
)
public class PriceLineTestRunner {

	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());

	}

	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runScenario(pickle.getPickle());
	}

	@DataProvider
	public Object[][] scenarios() {
		return testNGCucumberRunner.provideScenarios();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		testNGCucumberRunner.finish();
	}

	@AfterSuite(alwaysRun=true)
	public void generateReport(){
		PrettyCucumberReportingTool.generateReport();
		ReportUtils.generateAllureReport();
	}

}