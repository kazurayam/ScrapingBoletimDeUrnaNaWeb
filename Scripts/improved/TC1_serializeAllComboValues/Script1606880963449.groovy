import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.apache.commons.io.FileUtils

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonOutput
import internal.GlobalVariable as GlobalVariable
import my.ComboBoxesInspector

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
WebUI.setViewPortSize(414, 736)
WebUI.navigateToUrl(GlobalVariable.TARGET_URL)

// wait for the page is loaded
WebUI.verifyElementPresent(findTestObject('Page_Boletim de Urna na WEB/h2_region_title'), 10)

Map<String, TestObject> testObjects = [
	'Turno':		findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_12'),
	'UF':			findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_UFACALAMAPBACEDFESGOMAMGMSMTPAPBPEPI_a973a7'),
	'Municipio':	findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_Selecione um municpio  localidadeACR_454ffd'),
	'Zona':		findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_--0008'),
	'Seção':		findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_--0008000900640072007700830084008500_2e62b7'),
	'BotãoPesquisar':	findTestObject('Object Repository/Page_Boletim de Urna na WEB/span_Pesquisar'),
]
// make sure that all testObjects are defined correctly
for (tObj in testObjects.values()) {
	WebUI.verifyElementPresent(tObj, 1, FailureHandling.STOP_ON_FAILURE)
}

//Primeiros valores
// When a <select> is changed, the next <select> is updated by JavaScript,
// so we need to wait a while. How long? --- I do not know. Try 1 second and see.
WebUI.selectOptionByIndex(testObjects['Turno'], 0);     WebUI.delay(1); 
WebUI.selectOptionByIndex(testObjects['UF'], 1);        WebUI.delay(1);
WebUI.selectOptionByIndex(testObjects['Municipio'], 1); WebUI.delay(1);
WebUI.selectOptionByIndex(testObjects['Zona'], 1);      WebUI.delay(1);
WebUI.selectOptionByIndex(testObjects['Seção'], 1);     WebUI.delay(1);

//Primeira pesquisa
WebUI.click(testObjects['BotãoPesquisar'])
// wait for the page is loaded
WebUI.verifyElementPresent(findTestObject('Page_Boletim de Urna na WEB/h2_region_title'), 3)


// data buffer
List<Map> comboValues = ComboBoxesInspector.inspect(testObjects)

// serialize the collected info into a JSON file
String json = JsonOutput.toJson(comboValues)
print JsonOutput.prettyPrint(json)

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