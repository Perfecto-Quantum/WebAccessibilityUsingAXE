/**
 * 
 */
package com.quantum.steps;

import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.quantum.axe.AccessibilityException;
import com.quantum.axe.AxeHelper;
import com.quantum.axe.AxeTestResult;
import com.quantum.utils.ConfigurationUtils;
import cucumber.api.java.en.Then;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chirag.jayswal
 *
 */
@QAFTestStepProvider
public class AXEStepsDefs {

	public static QAFExtendedWebDriver getQAFDriver() {
		return new WebDriverTestBase().getDriver();
	}


	@Then("^axe scans the web page$")
	public void axe_scan(){

		getQAFDriver().getScreenshotAs(OutputType.BASE64);
		AxeHelper axe = new AxeHelper(getQAFDriver());
		axe.runAxe();

		axe.startHighlighter("violations");
		List<AxeTestResult> violations = null;
		try {
			violations = axe.axeEverything().violations;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (null != violations && 0 < violations.size()){
			int objects = 0;
			for (AxeTestResult t:violations) { objects = objects + t.nodes.size();}
			 String s = "Accessibility scan found "+ violations.size() + " violations in " + objects + " objects";
			System.out.println(s);
			perfectoComment(s);
		}
		final StringBuilder errors = new StringBuilder();
		int violationsCount = 0;
		int errorCount = 0;
		while (true) {
			final Map<String, ?> violation = axe.nextHighlight();
			if (violation == null) {
				break;
			}
			errorCount++;
			final String ruleId = (String) violation.get("issue");
			if (0 != ruleId.compareToIgnoreCase(violations.get(violationsCount).id))
				violationsCount++;

			final Map<String, String> node = (Map<String, String>) violation.get("node");

			final String impact = node.get("impact");
			final String summary = node.get("failureSummary");
			final String html = node.get("html");
			final String selector = (String) violation.get("target");

			final String message = String.format("%s - %s%n %s%n Selector:\t%s%n HTML:\t\t%s%n%n",
					impact, ruleId, summary, selector, html);
			final String perfectoReportMessage = String.format("Impact: %s; Rule ID: %s%n; Summary: %s%n; Selector:\t%s%n HTML:\t\t%s%n%n",
					impact, ruleId, summary, selector, html);
			try {
				final String perfectoReportMessageComplete = String.format("%s; help: %s; helpURL: %s; Tags: %s",
						perfectoReportMessage,
						violations.get(violationsCount).help,
						violations.get(violationsCount).helpUrl,
						violations.get(violationsCount).tags.toString());

				perfectoComment(perfectoReportMessageComplete);
				System.out.println("Error "+ errorCount +System.lineSeparator()+perfectoReportMessageComplete);
			} catch (Exception e) {
				e.printStackTrace();
				perfectoComment(perfectoReportMessage);
			}

			getQAFDriver().getScreenshotAs(OutputType.BASE64);
			errors.append(message);

		}

		if (errorCount > 0) {
			final Capabilities capabilities = getQAFDriver().getCapabilities();
			final String platform = String.valueOf(capabilities.getCapability("platformName"));
			final String version = String.valueOf(capabilities.getCapability("platformVersion"));
			final String browserName = String.valueOf(capabilities.getCapability("browserName"));
			final String browserVersion = String.valueOf(capabilities.getCapability("browserVersion"));

			String browserVersionFormatted;
			if ("null".equals(browserName)) {
				browserVersionFormatted = "default browser";
			} else {
				browserVersionFormatted = browserName + "-" + browserVersion;
			}


			String message = String.format("%n%s-%s %s : %d violations on %s%n",
					platform, version, browserVersionFormatted, errorCount, getQAFDriver().getCurrentUrl());

			message = String.format("%s%n%s%n", message, errors);

			String failScriptOnAccessibilityErrors = ConfigurationUtils.getBaseBundle().getPropertyValue("env.failScriptOnAccessibilityErrors");
			if (0 ==  failScriptOnAccessibilityErrors.compareToIgnoreCase("true"))
				throw new AccessibilityException(message);

		}

	}


	@Then("^I grab a screenshot$")
	public void grabScreenshot() throws Throwable {
		byte[] bytestss = getQAFDriver().getScreenshotAs(OutputType.BYTES);
	}

	private void perfectoComment(String comment){

		Map<String, Object> params = new HashMap<>();
		params.put("text", comment);
		getQAFDriver().executeScript("mobile:comment", params);
	}

}
