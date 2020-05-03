package com.git.apis.search.repositories.stepdefinations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.git.apis.global.exception.CustomException;
import com.git.apis.search.utils.ConfigUtil;

import cucumber.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	static Logger log=LogManager.getLogger();
	@Before
	public void beforeExecution() {

		log.info("Initiating test suit to test GitHub search repositories API");
		try {
			ConfigUtil.loadProperties();
			ConfigUtil.specifyMaximumResultCount();
		} catch (CustomException e1) {
			// TODO Auto-generated catch block
			log.error(e1.toString());
		}
		
		
		log.info("Executing testcases");
		
	
	}
	@After
	public void afterExecution(Scenario scenario) {
		//Logger log=LogManager.getLogger();
		log.info("Execution completed");
		log.info("Execution report is present at location :"+System.getProperty("user.dir")+"\\target\\cucumber-html-reports\\overview-features.html");
		log.traceExit();
	}

}
