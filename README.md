# WebAccessibilityUsingAXE
Demonstrating accessibility scanning with Quantum and AXE for mobile and desktop web

This project demonstrates how AXE (provided by Deque see [here](https://axe-core.org/docs/)) can be used to automate accessibility testing for any browser, desktop or mobile, with Quantum framework

## Quick getting started
1. Head over to resources/application.properties and enter your cloud name (remote.server), your username and either password or security token (you can get the latter [this way](https://developers.perfectomobile.com/display/PD/Security+Token))
2. ensure your project is configured as Maven (right click on POM and select the maven option). ensure all imports are complete
3. right click src/main/java/resource/config/testng_desktop.xml and click "run"

In the report you will find a new step called **"Then axe scans the web page"** , this is what causes the scan

## Reading the Perfecto report
Under the step "Then axe scans the web page", you will find a number of "Execute Script" and then the first comment.
This comment will say **"Accessibility scan found 3 violations in 39 objects"**

Then, there will be a loop detailing each object and a follow up of a screenshot highlighting each object:

This is following the convention defined in the [AXE spec](https://axe-core.org/docs/#results-object)

**Impact**: critical**
**Rule ID: aria-required-children**
**Summary: Fix any of the following: Required ARIA children role not present: listbox textbox**
**Selector:	#lst-ib**
**HTML:		<input class="gsfi" id="lst-ib" maxlength="2048" name="q" autocomplete="off" title="Search" type="text" value="quantum perfecto" aria-label="Search" aria-haspopup="false" role="combobox" aria-autocomplete="list" dir="ltr" spellcheck="false" style="border: none; padding: 0px; margin: 0px; height: auto; width: 100%; background: url(&quot;data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw%3D%3D&quot;) transparent; position: absolute; z-index: 6; left: 0px; outline: none;">**
**Help: Certain ARIA roles must contain particular children**
**HelpURL: https://dequeuniversity.com/rules/axe/2.3/aria-required-children?application=axeAPI**
**Tags: [cat.aria, wcag2a, wcag131]**

## Customizations
In application.properties one can set the flag **env.failScriptOnAccessibilityErrors** to true such that once a violation is found, their script will assert. (see AXEStepsDefs.java, line 119).

