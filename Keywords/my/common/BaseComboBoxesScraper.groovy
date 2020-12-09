package my.common

import org.openqa.selenium.WebElement

abstract class BaseComboBoxesScraper {

	String getValue(WebElement el) {
		return (el != null) ? el.getAttribute('value') : null
	}

	String getText(WebElement el) {
		return (el != null) ? el.getText() : null
	}
}
