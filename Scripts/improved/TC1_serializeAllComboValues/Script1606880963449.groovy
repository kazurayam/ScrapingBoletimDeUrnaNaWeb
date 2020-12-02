import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.apache.commons.io.FileUtils

// display parameter values
WebUI.comment("TARGET_URL: ${GlobalVariable.TARGET_URL}")
WebUI.comment("DIRNAME_PARAMETERS: ${GlobalVariable.DIRNAME_PARAMETERS}")
WebUI.comment("DIRNAME_PAGESOURCES: ${GlobalVariable.DIRNAME_PAGESOURCES}")
WebUI.comment("FILENAME_ALLCOMBOVALUES: ${GlobalVariable.FILENAME_ALLCOMBOVALUES}")
WebUI.comment("FILENAME_SPLITTEDCOMBOVALUES: ${GlobalVariable.FILENAME_SPLITTEDCOMBOVALUES}")
WebUI.comment("SIZE_PARALLEL_PROCESSING: ${GlobalVariable.SIZE_PARALLEL_PROCESSING}")

// initialize output direcgtories, 
File parametersDir = new File(GlobalVariable.DIRNAME_PARAMETERS)
initializeDir(parametersDir)
File pageSourcesDir = new File(GlobalVariable.DIRNAME_PAGESOURCES)
initializeDir(pageSourcesDir)

WebUI.openBrowser('')
WebUI.navigateToUrl(GlobalVariable.TARGET_URL)
// wait for the page loaded
WebUI.verifyElementPresent(findTestObject('Page_Boletim de Urna na WEB/h2_region_title'), 10)

// iterate over values of Combo boxes, collect the information

// serialize the collected info into a JSON file

WebUI.closeBrowser()


/**
 * create a new empty directory,
 * or create a direcgtory after deleting it recursively if already exists
 * 
 * @param dir
 * @return
 * @throws IOException
 */
def initializeDir(File dir) throws IOException {
	if (dir.exists()) {
		FileUtils.deleteDirectory(dir)
	}
	dir.mkdirs()
}