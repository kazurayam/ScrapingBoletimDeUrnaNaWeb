import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable as GlobalVariable
import my.common.CustomFileUtils
import my.common.FileAppender
import my.study1.ComboBoxesScraper1

// display parameter values
WebUI.comment("TARGET_URL: ${GlobalVariable.TARGET_URL}")
WebUI.comment("DIRNAME_PARAMETERS: ${GlobalVariable.DIRNAME_PARAMETERS}")
WebUI.comment("FILENAME_ALLCOMBOVALUES: ${GlobalVariable.FILENAME_ALLCOMBOVALUES}")
WebUI.comment("DEBUG_MODE: ${GlobalVariable.DEBUG_MODE}")

if (GlobalVariable.TARGET_URL == null) {
	KeywordUtil.markFailedAndStop("GlobalVariable.TARGET_URL was null. Please specify appropriate Execution Profile.")	
}

// initialize output direcgtories, 
File parametersDir = new File((String)GlobalVariable.DIRNAME_PARAMETERS)
CustomFileUtils.initializeDir(parametersDir)

File pageSourcesDir = new File((String)GlobalVariable.DIRNAME_PAGESOURCES)
CustomFileUtils.initializeDir(pageSourcesDir)

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
ComboBoxesScraper1 cbScraper = new ComboBoxesScraper1(appender)
cbScraper.process(testObjects)
appender.close()

WebUI.closeBrowser()


