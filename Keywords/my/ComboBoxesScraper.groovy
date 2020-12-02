package my

import org.openqa.selenium.support.ui.Select

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement

public class ComboBoxesScraper {

	private ComboBoxesScraper() {}

	/**
	 * 
	 * @param testObjects
	 * @return
	 */
	static List process(Map<String, TestObject> testObjects) {
		
		// data buffer
		List<Map<String, String>> comboValues = []
		int recCount = 0

		// iterate over the Combo boxes while changing selections, collect values
		for (int t = 0; t < WebUI.getNumberOfTotalOption(testObjects['Turno']); t++) {
			WebUI.selectOptionByIndex(testObjects['Turno'], t)
			WebUI.delay(1)
			WebElement tOpt = new Select(WebUI.findWebElement(testObjects['Turno'])).getOptions()[t]
			def tVal = tOpt.getAttribute('value')
			def tTxt = tOpt.getText()
			for (int u = 1; u < WebUI.getNumberOfTotalOption(testObjects['UF']); u++) {
				WebUI.selectOptionByIndex(testObjects['UF'], u)
				WebUI.delay(1)
				WebElement uOpt = new Select(WebUI.findWebElement(testObjects['UF'])).getOptions()[u]
				def uVal = uOpt.getAttribute('value')
				def uTxt = uOpt.getText()
				for (int m = 1; m < WebUI.getNumberOfTotalOption(testObjects['Municipio']); m++) {
					WebUI.selectOptionByIndex(testObjects['Municipio'], m)
					WebUI.delay(1)
					WebElement mOpt = new Select(WebUI.findWebElement(testObjects['Municipio'])).getOptions()[m]
					def mVal = mOpt.getAttribute('value')
					def mTxt = mOpt.getText()
					for (int z = 1; z < WebUI.getNumberOfTotalOption(testObjects['Zona']); z++) {
						WebUI.selectOptionByIndex(testObjects['Zona'], z)
						WebUI.delay(1)
						WebElement zOpt = new Select(WebUI.findWebElement(testObjects['Zona'])).getOptions()[z]
						def zVal = zOpt.getAttribute('value')
						def zTxt = zOpt.getText()
						for (int s = 1; s < WebUI.getNumberOfTotalOption(testObjects['Seção']); s++) {
							//WebUI.selectOptionByIndex(testObjects['Seção'], s)
							WebElement sOpt = new Select(WebUI.findWebElement(testObjects['Seção'])).getOptions()[s]
							def sVal = sOpt.getAttribute('value')
							def sTxt = sOpt.getText()
							println "sOpt ${sOpt}"
							println "sVal ${sVal}"
							println "sTxt ${sTxt}"
							
							//
							Map record = [
								'Turno':     ['x': t, 'v': tVal, 't': tTxt],
								'UF':        ['x': u, 'v': uVal, 't': uTxt],
								'Municipio': ['x': m, 'v': mVal, 't': mTxt],
								'Zona':      ['x': z, 'v': zVal, 't': zTxt],
								'Seção':     ['x': s, 'v': sVal, 't': sTxt],
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
