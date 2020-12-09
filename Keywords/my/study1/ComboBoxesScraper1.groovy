package my.study1

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import my.common.FileAppender

import my.common.BaseComboBoxesScraper

public class ComboBoxesScraper1 extends BaseComboBoxesScraper {

	FileAppender appender_

	ComboBoxesScraper1(FileAppender appender) {
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
		List<WebElement> tOptions = new Select(WebUI.findWebElement(testObjects['Turno'])).getOptions()
		for (int t = 0; t <= tOptions.size(); t++) {
			WebUI.selectOptionByIndex(testObjects['Turno'], t)
			WebElement tOpt = tOptions[t]
			def tVal = getValue(tOpt)
			def tTxt = getText(tOpt)
			List<WebElement> uOptions = new Select(WebUI.findWebElement(testObjects['UF'])).getOptions()
			for (int u = 1; u < uOptions.size(); u++) {
				WebUI.selectOptionByIndex(testObjects['UF'], u)
				WebElement uOpt = uOptions[u]
				def uVal = getValue(uOpt)
				def uTxt = getText(uOpt)
				List<WebElement> mOptions = new Select(WebUI.findWebElement(testObjects['Municipio'])).getOptions()
				for (int m = 1; m < mOptions.size(); m++) {
					WebUI.selectOptionByIndex(testObjects['Municipio'], m)
					WebElement mOpt = mOptions[m]
					def mVal = getValue(mOpt)
					def mTxt = getText(mOpt)
					List<WebElement> zOptions = new Select(WebUI.findWebElement(testObjects['Zona'])).getOptions()
					for (int z = 1; z < zOptions.size(); z++) {
						WebUI.selectOptionByIndex(testObjects['Zona'], z)   // this method call blocks
						WebElement zOpt = zOptions[z]
						def zVal = getValue(zOpt)
						def zTxt = getText(zOpt)
						// cache values which are frequently referred to for better performance
						List<WebElement> sOptions = new Select(WebUI.findWebElement(testObjects['Seção'])).getOptions()
						for (int s = 1; s < sOptions.size(); s++) {
							// skip selecting the option, for better performance
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

