package my

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable


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
			def tVal = getValue(tOpt)
			def tTxt = getText(tOpt)
			for (int u = 1; u < WebUI.getNumberOfTotalOption(testObjects['UF']); u++) {
				WebUI.selectOptionByIndex(testObjects['UF'], u)
				WebUI.delay(1)
				WebElement uOpt = new Select(WebUI.findWebElement(testObjects['UF'])).getOptions()[u]
				def uVal = getValue(uOpt)
				def uTxt = getText(uOpt)
				for (int m = 1; m < WebUI.getNumberOfTotalOption(testObjects['Municipio']); m++) {
					WebUI.selectOptionByIndex(testObjects['Municipio'], m)
					WebUI.delay(1)
					WebElement mOpt = new Select(WebUI.findWebElement(testObjects['Municipio'])).getOptions()[m]
					def mVal = getValue(mOpt)
					def mTxt = getText(mOpt)
					for (int z = 1; z < WebUI.getNumberOfTotalOption(testObjects['Zona']); z++) {
						WebUI.selectOptionByIndex(testObjects['Zona'], z)
						WebUI.delay(1)
						WebElement zOpt = new Select(WebUI.findWebElement(testObjects['Zona'])).getOptions()[z]
						def zVal = getValue(zOpt)
						def zTxt = getText(zOpt)
						// cache values which are frequently referred to for better performance
						def sMax = WebUI.getNumberOfTotalOption(testObjects['Seção'])
						def sSelect = WebUI.findWebElement(testObjects['Seção'])
						List<WebElement> sOptions = new Select(sSelect).getOptions()
						//
						for (int s = 1; s < sMax; s++) {
							// won't do selecting, for better performance
							//WebUI.selectOptionByIndex(testObjects['Seção'], s)

							WebElement sOpt = sOptions[s]
							def sVal = getValue(sOpt)
							def sTxt = getText(sOpt)
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
							if (recCount % 100 == 1) {
								WebUI.comment("${recCount} ${record.toString()}")
							}
							// when DEBUG_MODE, quit shortly
							if (GlobalVariable.DEBUG_MODE && recCount > 100) {
								return comboValues
							}
						}
					}
				}
			}
		}
		return comboValues
	}

	static String getValue(WebElement el) {
		return (el != null) ? el.getAttribute('value') : null
	}

	static String getText(WebElement el) {
		return (el != null) ? el.getText() : null
	}

}

