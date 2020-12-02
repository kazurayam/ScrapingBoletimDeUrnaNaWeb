import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.support.ui.Select

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.setViewPortSize(1024,720)
WebUI.navigateToUrl('http://inter04.tse.jus.br/ords/eletse/f?p=111:1::PESQUISAR:NO:::')
ComboTurno = 'Page_Boletim de Urna na WEB/select_12'
ComboUF = 'Object Repository/Page_Boletim de Urna na WEB/select_UFACALAMAPBACEDFESGOMAMGMSMTPAPBPEPI_a973a7'
ComboMunicipio = 'Object Repository/Page_Boletim de Urna na WEB/select_Selecione um municpio  localidadeACR_454ffd'
ComboZona = 'Object Repository/Page_Boletim de Urna na WEB/select_--0008'
ComboSeção = 'Object Repository/Page_Boletim de Urna na WEB/select_--0008000900640072007700830084008500_2e62b7'
BotãoPesquisar = 'Object Repository/Page_Boletim de Urna na WEB/span_Pesquisar'
//Primeiros valores
WebUI.selectOptionByIndex(findTestObject(ComboTurno), 0)
WebUI.selectOptionByIndex(findTestObject(ComboUF), 1)
WebUI.selectOptionByIndex(findTestObject(ComboMunicipio), 1)
WebUI.selectOptionByIndex(findTestObject(ComboZona), 1)
WebUI.selectOptionByIndex(findTestObject(ComboSeção), 1)
//Primeira pesquisa
WebUI.click(findTestObject(BotãoPesquisar))
WebUI.waitForPageLoad(10)
//alimenta quantidades em cada combo

//Ninho de for para percorrer todas as opções
int QtdTurno =     WebUI.getNumberOfTotalOption(findTestObject(ComboTurno))
println(QtdTurno)

ensureParentDirs(new File('./tmp'))

for (int T = 0; T <= QtdTurno; T++){
	WebUI.selectOptionByIndex(findTestObject(ComboTurno), T);
	WebUI.verifyElementPresent(findTestObject(ComboUF), 3)
	int QtdUF = WebUI.getNumberOfTotalOption(findTestObject(ComboUF))
	WebUI.comment("QtdUF=${QtdUF}")
	for (int U = 1; U <= QtdUF; U++){
		WebUI.selectOptionByIndex(findTestObject(ComboUF), U);
		WebUI.verifyElementPresent(findTestObject(ComboMunicipio), 3)
		int QtdMunicipio = WebUI.getNumberOfTotalOption(findTestObject(ComboMunicipio))
		WebUI.comment("QtdMunicipio=${QtdMunicipio}")
		for (int M = 1; M <= QtdMunicipio; M++) {
			WebUI.selectOptionByIndex(findTestObject(ComboMunicipio), M);
			WebUI.verifyElementPresent(findTestObject(ComboZona), 3)
			int QtdZona = WebUI.getNumberOfTotalOption(findTestObject(ComboZona))
			WebUI.comment("QtdZona=${QtdZona}")
			for (int Z = 1; Z <= QtdZona; Z++) {
				WebUI.selectOptionByIndex(findTestObject(ComboZona), Z);
				WebUI.verifyElementPresent(findTestObject(ComboSeção), 3)
				int QtdSeção = WebUI.getNumberOfTotalOption(findTestObject(ComboSeção))
				WebUI.comment("QtdSeção=${QtdSeção}")
				for (int S = 1; S <= QtdSeção; S++) {
					WebUI.selectOptionByIndex(findTestObject(ComboSeção), S)
					WebUI.verifyElementPresent(findTestObject(BotãoPesquisar), 3)
					WebUI.click(findTestObject(BotãoPesquisar));
					WebUI.waitForPageLoad(0);
					   //Gravando o arquivo a partir do valor selecionado nas combos
					Select selectTurno = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_TURNO']")))
					Turno = selectTurno.getFirstSelectedOption().getText()
					Select selectUF = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_UF']")))
					UF = selectUF.getFirstSelectedOption().getText()
					Select selectMUN = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_MUN']")))
					Municipio = selectMUN.getFirstSelectedOption().getText()
					Select selectZONA = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_ZONA']")))
					Zona = selectZONA.getFirstSelectedOption().getText()
					Select selectSECAO = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//select[@id='P1_SECAO']")))
					Seção = selectSECAO.getFirstSelectedOption().getText()

					String NomeDoArquivo = ('./tmp/TSE2018_'+
						'Turno-'+Turno+
						'_UF-'+UF+
						'_Municipio-'+Municipio+
						'_Zona-'+Zona+
						'_Seção-'+Seção+
						'.html')
					WebDriver driver = DriverFactory.getWebDriver()
					def src = driver.getPageSource()
					def Arquivo = new File(NomeDoArquivo)
					Arquivo.write(src)
				}
			}
		}
	}
}
WebUI.closeBrowser()


def ensureParentDirs(File file) throws IOException {
	File parent = file.getParentFile()
	if (!parent.exists()) {
		boolean b =  parent.mkdirs()
		if (!b) throw new IOException("failed to create ${parent}")
	}
}

