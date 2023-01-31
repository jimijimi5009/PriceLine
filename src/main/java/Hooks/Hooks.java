package Hooks;

import Environment.Config;
import domain.Helper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utility.FileRead;
import utility.ReadPropFile;

import java.io.IOException;


public class Hooks extends Config {
    Helper helper;
    public Hooks(Helper helper) {
        this.helper=helper;
    }

    @Before
    public void beforeAll(Scenario scenario) throws IOException {
        scenarios = scenario;;
        ReadPropFile.config = FileRead.readProperties();
    }

    @After()
    public void afterTestExecute(Scenario scenario){

        boolean x = scenario.isFailed();
        helper.closeReourceForCompleted(scenario);
    }



}
