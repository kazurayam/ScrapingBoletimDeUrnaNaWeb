package my.common

import groovy.json.JsonOutput
import my.study1.ComboBoxesScraper1

public class ComboValuesFormatter {

	private ComboValuesFormatter() {}

	static String formatOutputTextAsJson(List<Map> comboValues) {
		return JsonOutput.prettyPrint(JsonOutput.toJson(comboValues))
	}

	static String formatOutputTextAsCSV(List<Map> comboValues) {
		StringBuilder sb = new StringBuilder()
		for (Map section in comboValues) {
			sb.append(ComboBoxesScraper1.toCSVLine(section))
			sb.append('\n')
		}
		return sb.toString()
	}
}
