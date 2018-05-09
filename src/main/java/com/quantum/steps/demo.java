/**
 * 
 */
package com.quantum.steps;

import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import cucumber.api.java.en.Then;

/**
 * @author chirag.jayswal
 *
 */
@QAFTestStepProvider
public class demo {


	@Then("I go to services menu")
	public static void goToContact() {
		QAFExtendedWebDriver d = new WebDriverTestBase().getDriver();
		try {
			d.findElement("menu").click();

		}catch (Exception e)
		{
			//do noting !! just open menu
		}

		d.findElement("contact").click();

	}



}
