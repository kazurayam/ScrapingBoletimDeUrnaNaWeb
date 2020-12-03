import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonOutput
import internal.GlobalVariable as GlobalVariable
import my.ComboBoxesScraper
import my.FileAppender

// display parameter values
WebUI.comment("TARGET_URL: ${GlobalVariable.TARGET_URL}")
WebUI.comment("DIRNAME_PARAMETERS: ${GlobalVariable.DIRNAME_PARAMETERS}")
WebUI.comment("DIRNAME_PAGESOURCES: ${GlobalVariable.DIRNAME_PAGESOURCES}")
WebUI.comment("FILENAME_ALLCOMBOVALUES: ${GlobalVariable.FILENAME_ALLCOMBOVALUES}")
WebUI.comment("FILENAME_SPLITTEDCOMBOVALUES: ${GlobalVariable.FILENAME_SPLITTEDCOMBOVALUES}")
WebUI.comment("SIZE_PARALLEL_PROCESSING: ${GlobalVariable.SIZE_PARALLEL_PROCESSING}")
WebUI.comment("DEBUG_MODE: ${GlobalVariable.DEBUG_MODE}")

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
	'Zona':		    findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_--0008'),
	'Seção':		findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_--0008000900640072007700830084008500_2e62b7'),
	'BotãoPesquisar':	findTestObject('Object Repository/Page_Boletim de Urna na WEB/span_Pesquisar'),
]
// make sure that all testObjects are defined correctly
for (tObj in testObjects.values()) {
	WebUI.verifyElementPresent(tObj, 1, FailureHandling.STOP_ON_FAILURE)
}

// data buffer
List<Map> comboValues

// AllComboValues file
File allComboValuesFile = parametersDir.toPath().resolve(GlobalVariable.FILENAME_ALLCOMBOVALUES).toFile()

FileAppender appender = new FileAppender(allComboValuesFile)
ComboBoxesScraper cbScraper = new ComboBoxesScraper(appender)
cbScraper.process(testObjects)
appender.close()

WebUI.closeBrowser()

/**
 * create a new empty directory,
 * or create a direcgtory after deleting it recursively if already exists
 * 
 * @param dir
 * @return
 * @throws IOException
 */
void initializeDir(File dir) throws IOException {
	if (dir.exists()) {
		FileUtils.deleteDirectory(dir)
	}
	dir.mkdirs()
}

String formatOutputTextAsJson(List<Map> comboValues) {
	return JsonOutput.prettyPrint(JsonOutput.toJson(comboValues))
}

String formatOutputTextAsCSV(List<Map> comboValues) {
	StringBuilder sb = new StringBuilder()
	for (Map section in comboValues) {
		sb.append(ComboBoxesScraper.toCSVLine(section))
		sb.append('\n')
	}
	return sb.toString()
}

void writeTextIntoFile(String text, File outfile) throws IOException {
	OutputStream os = new FileOutputStream(outfile)
	Writer wr = new OutputStreamWriter(os, StandardCharsets.UTF_8.name())
	wr.write(text)
	wr.flush()
	os.close()
}