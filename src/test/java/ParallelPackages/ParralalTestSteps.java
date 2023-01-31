package ParallelPackages;

import domain.DriverFactory;
import domain.Helper;
import io.cucumber.java.en.Given;
import utility.ReadPropFile;

public class ParralalTestSteps {

    Helper helper;

    public ParralalTestSteps(){
        helper=new Helper();
    }

    @Given("User Navigate to alayacare {string}")
    public void userNavigateToAlayacare(String arg0) {

       String url = ReadPropFile.config.get(arg0.trim().toUpperCase()+"_"+"");
       DriverFactory.getDriver().get(url);

    }

}
