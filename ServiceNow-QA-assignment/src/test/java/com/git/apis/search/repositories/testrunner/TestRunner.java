package com.git.apis.search.repositories.testrunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/com/git/apis/search/repositories/features",glue= {"com/git/apis/search/repositories/stepdefinations"},plugin = { "pretty" ,"json:target/jsonReports/cucumber-report.json"},
monochrome = true)

public class TestRunner {
}
