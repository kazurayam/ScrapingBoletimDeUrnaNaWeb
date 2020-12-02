import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.support.ui.Select;
import my.Scraper

WebUI.openBrowser('')
WebUI.setViewPortSize(1024,720)
WebUI.navigateToUrl('http://inter04.tse.jus.br/ords/eletse/f?p=111:1::PESQUISAR:NO:::')

Map<String, TestObject> to = [
	'ComboTurno':		findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_12'),
	'ComboUF':			findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_UFACALAMAPBACEDFESGOMAMGMSMTPAPBPEPI_a973a7'),
	'ComboMunicipio':	findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_Selecione um municpio  localidadeACR_454ffd'),
	'ComboZona':		findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_--0008'),
	'ComboSeção':		findTestObject('Object Repository/Page_Boletim de Urna na WEB/select_--0008000900640072007700830084008500_2e62b7'),
	'BotãoPesquisar':	findTestObject('Object Repository/Page_Boletim de Urna na WEB/span_Pesquisar'),
]
//Primeiros valores
WebUI.selectOptionByIndex(to['ComboTurno'], 0);     WebUI.delay(1);
WebUI.selectOptionByIndex(to['ComboUF'], 1);        WebUI.delay(1);
WebUI.selectOptionByIndex(to['ComboMunicipio'], 1); WebUI.delay(1);
WebUI.selectOptionByIndex(to['ComboZona'], 1);      WebUI.delay(1);
WebUI.selectOptionByIndex(to['ComboSeção'], 1);     WebUI.delay(1);

//Primeira pesquisa
WebUI.click(to['BotãoPesquisar'])
WebUI.waitForPageLoad(10)

//alimenta quantidades em cada combo
int QtdTurno =     WebUI.getNumberOfTotalOption(to['ComboTurno'])-1
int QtdUF =        WebUI.getNumberOfTotalOption(to['ComboUF'])-1
int QtdMunicipio = WebUI.getNumberOfTotalOption(to['ComboMunicipio'])-1
int QtdZona =      WebUI.getNumberOfTotalOption(to['ComboZona'])-1
int QtdSeção =     WebUI.getNumberOfTotalOption(to['ComboSeção'])-1
println("QtdTurno:     ${QtdTurno}")
println("QtdUF:        ${QtdUF}")
println("QtdMunicipio: ${QtdMunicipio}")
println("QtdZona:      ${QtdZona}")
println("QtdSeção:     ${QtdSeção}")

//Ninho de for para percorrer todas as opções
Scraper scraper = new Scraper()
for (int t = 0; t <= QtdTurno; t++){
	//
	WebUI.selectOptionByIndex(to['ComboTurno'], t);
	QtdUF = WebUI.getNumberOfTotalOption(to['ComboUF']) - 1
	for (int u = 1; u <= QtdUF; u++){
		//
		WebUI.selectOptionByIndex(to['ComboUF'], u);
		QtdMunicipio = WebUI.getNumberOfTotalOption(to['ComboMunicipio']) - 1
		for (int m = 1; m <= QtdMunicipio; m++) {
			//
			WebUI.selectOptionByIndex(to['ComboMunicipio'], m);
			QtdZona = WebUI.getNumberOfTotalOption(to['ComboZona']) - 1
			for (int z = 1; z <= QtdZona; z++) {
				//
				WebUI.selectOptionByIndex(to['ComboZona'], z);
				QtdSeção = WebUI.getNumberOfTotalOption(to['ComboSeção']) - 1
				for (int s = 1; s <= QtdSeção; s++) {
					//
					scraper.scrape(to, s)
				}
			}
		}
	}
}
WebUI.closeBrowser()
