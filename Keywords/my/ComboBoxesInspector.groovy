package my

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class ComboBoxesInspector {
	
	private ComboBoxesInspector() {}
	
	/**
	 * 
	 * @param testObjects
	 * @return
	 */
	static List inspect(Map<String, TestObject> testObjects) {
		// data buffer
		List<Map<String, String>> comboValues = []
		int recCount = 0
	
		// iterate over the Combo boxes while changing selections, collect values
		for (int t = 0; t < WebUI.getNumberOfTotalOption(testObjects['Turno']); t++) {
			WebUI.selectOptionByIndex(testObjects['Turno'], t)
			WebUI.delay(1)
			for (int u = 1; u < WebUI.getNumberOfTotalOption(testObjects['UF']); u++) {
				WebUI.selectOptionByIndex(testObjects['UF'], u)
				WebUI.delay(1)
				for (int m = 1; m < WebUI.getNumberOfTotalOption(testObjects['Municipio']); m++) {
					WebUI.selectOptionByIndex(testObjects['Municipio'], m)
					WebUI.delay(1)
					for (int z = 1; z < WebUI.getNumberOfTotalOption(testObjects['Zona']); z++) {
						WebUI.selectOptionByIndex(testObjects['Zona'], z)
						WebUI.delay(1)
						for (int s = 1; s < WebUI.getNumberOfTotalOption(testObjects['Seção']); s++) {
							WebUI.selectOptionByIndex(testObjects['Seção'], s)
							Map record = [
								'Turno': t,
								'UF': u,
								'Municipio': m,
								'Zona': z,
								'Seção': s,
								]
							comboValues.add(record)
							++recCount
							if (recCount % 50 == 1) {
								WebUI.comment("${recCount} ${record.toString()}")
							}
						}
					}
				}
			}
		}
		return comboValues
	}
}
