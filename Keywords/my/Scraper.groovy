package my

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.commons.io.FileUtils
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.Select

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class Scraper {

	private final String diretorioPath = './tmp/TSE/2018/TSE2018'
	private final Path diretorio

	/**
	 *
	 */
	Scraper() {
		diretorio = Paths.get(diretorioPath)
		if (Files.exists(diretorio)) {
			FileUtils.deleteDirectory(diretorio.toFile())
			Files.createDirectories(diretorio)
		}
	}

	/**
	 *
	 * @param to
	 * @param S
	 */
	void scrape(Map<String, TestObject> to, int S) {

		WebUI.selectOptionByIndex(to['ComboSeção'], S)
		//WebUI.click(to['BotãoPesquisar']);
		//WebUI.waitForPageLoad(5);
		//Gravando o arquivo a partir do valor selecionado nas combos
		Select selectturno = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_TURNO']")))
		Select selectUF = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_UF']")))
		Select selectMUN = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_MUN']")))
		Select selectZONA = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_ZONA']")))
		Select selectSECAO = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_SECAO']")))

		StringBuilder sb = new StringBuilder()
		sb.append('_')
		sb.append('Turno-')
		sb.append(selectturno.getFirstSelectedOption().getText())
		sb.append('_UF-')
		sb.append(selectUF.getFirstSelectedOption().getText())
		sb.append('_Municipio-')
		sb.append(selectMUN.getFirstSelectedOption().getText())
		sb.append('_Zona-')
		sb.append(selectZONA.getFirstSelectedOption().getText())
		sb.append('_Seção-')
		sb.append(selectSECAO.getFirstSelectedOption().getText())
		sb.append('.html')
		String nomeDoArquivo = sb.toString()

		Path outfile = diretorio.resolve(nomeDoArquivo);
		if (Files.exists(outfile)) {
			println("Overwriting ${outfile}")
		}
		WebUI.click(to['BotãoPesquisar']);

		WebUI.waitForPageLoad(5);

		def pageSrc = DriverFactory.getWebDriver().getPageSource()

		outfile.toFile().write(pageSrc)


	}

}
