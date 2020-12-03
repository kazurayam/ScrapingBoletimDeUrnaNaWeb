package my

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable


public class ComboBoxesScraper {

	FileAppender appender_

	ComboBoxesScraper(FileAppender appender) {
		this.appender_ = appender
	}

	/**
	 * 
	 * @param testObjects
	 * @return
	 */
	List process(Map<String, TestObject> testObjects) {

		// data buffer
		List<Map<String, String>> comboValues = []
		int recCount = 0

		// iterate over the Combo boxes while changing selections, collect values
		def tMax = WebUI.getNumberOfTotalOption(testObjects['Turno'])
		for (int t = 0; t <= tMax; t++) {
			WebUI.selectOptionByIndex(testObjects['Turno'], t)
			//WebUI.delay(1)
			Thread.sleep(100)   // 1000 millisecond is too long
			WebElement tOpt = new Select(WebUI.findWebElement(testObjects['Turno'])).getOptions()[t]
			def tVal = getValue(tOpt)
			def tTxt = getText(tOpt)
			def uMax = WebUI.getNumberOfTotalOption(testObjects['UF'])
			for (int u = 1; u < uMax; u++) {
				WebUI.selectOptionByIndex(testObjects['UF'], u)
				//WebUI.delay(1)
				Thread.sleep(100)
				WebElement uOpt = new Select(WebUI.findWebElement(testObjects['UF'])).getOptions()[u]
				def uVal = getValue(uOpt)
				def uTxt = getText(uOpt)
				def mMax = WebUI.getNumberOfTotalOption(testObjects['Municipio'])
				for (int m = 1; m < mMax; m++) {
					WebUI.selectOptionByIndex(testObjects['Municipio'], m)
					//WebUI.delay(1)
					Thread.sleep(100)
					WebElement mOpt = new Select(WebUI.findWebElement(testObjects['Municipio'])).getOptions()[m]
					def mVal = getValue(mOpt)
					def mTxt = getText(mOpt)
					def zMax = WebUI.getNumberOfTotalOption(testObjects['Zona'])
					for (int z = 1; z < zMax; z++) {
						WebUI.selectOptionByIndex(testObjects['Zona'], z)
						//WebUI.delay(1)
						Thread.sleep(100)
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
							Map section = [
								'Turno':     ['x': t, 'v': tVal, 't': tTxt],
								'UF':        ['x': u, 'v': uVal, 't': uTxt],
								'Municipio': ['x': m, 'v': mVal, 't': mTxt],
								'Zona':      ['x': z, 'v': zVal, 't': zTxt],
								'Seção':     ['x': s, 'v': sVal, 't': sTxt],
							]
							comboValues.add(section)
							++recCount
							String line = toCSVLine(section)
							if (recCount % 100 == 1) {
								// display progress
								WebUI.comment("${recCount} ${line}")
							}
							if (appender_ != null) {
								appender_.append(line)
							}
							// if DEBUG_MODE, quit shortly
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

	static String toCSVLine(Map section) {
		StringBuilder sb = new StringBuilder()
		sb.append("Turno,${section['Turno']['x']},${section['Turno']['v']},${section['Turno']['t']},")
		sb.append("UF,${section['UF']['x']},${section['UF']['v']},${section['UF']['t']},")
		sb.append("Municipio,${section['Municipio']['x']},${section['Municipio']['v']},${section['Municipio']['t']},")
		sb.append("Zona,${section['Zona']['x']},${section['Zona']['v']},${section['Zona']['t']},")
		sb.append("Seção,${section['Seção']['x']},${section['Seção']['v']},${section['Seção']['t']},")
		return sb.toString()
	}
}

