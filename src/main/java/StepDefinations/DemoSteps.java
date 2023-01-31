package StepDefinations;
import domain.Helper;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.IOException;
import java.util.logging.Logger;

public class DemoSteps {

    Helper helper;

    public DemoSteps(){
        helper = new Helper();
    }

    @Given("user is on demo machine")
    public void user_is_on_payroll_support_page( ) {
       throw new PendingException();
    }


}
