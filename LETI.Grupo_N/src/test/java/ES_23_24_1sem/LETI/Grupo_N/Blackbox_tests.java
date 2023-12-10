package ES_23_24_1sem.LETI.Grupo_N;

import static org.junit.Assert.*;

 

import java.util.List;

 

import org.junit.AfterClass;

import org.junit.BeforeClass;

import org.junit.Test;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

 

public class Blackbox_tests{
	
	static WebDriver driver;
	@BeforeClass
	     public static void setUpBeforeClass() throws Exception {
	// Defina o caminho para o GeckoDriver
	System.setProperty("webdriver.gecko.driver", "C:\\Users\\Pedro\\Downloads\\geckodriver-v0.33.0-win-aarch64\\geckodriver.exe");

    // Inicie o WebDriver do Firefox
    driver = new FirefoxDriver();
	}
	@Test
	
	     public void test() throws InterruptedException {
        // Abra a página
        driver.get("C:\\Users\\Pedro\\Desktop\\Schedule.html\\Schedule.html");

        // Encontre um elemento na página (por exemplo, usando ID)
        WebElement element = driver.findElement(By.id("filter-field"));

        // Faça alguma interação, como clicar no elemento
        element.click();
        element.sendKeys("LETI");

        // Aguarde um pouco para visualizar o resultado (opcional)
        Thread.sleep(3000);
 

        driver.quit();
    }

	
	
//
// 
//
//     // WebDriver instance
//
//     static WebDriver driver;
//
// 
//
//     @BeforeClass
//
//     public static void setUpBeforeClass() throws Exception {
//
//          // location of drivers
//
//    	 System.setProperty("webdriver.gecko.driver", "C:\\Users\\Pedro\\Downloads\\geckodriver-v0.33.0-win-aarch64\\geckodriver.exe");
//    	 driver = new FirefoxDriver();
//
//
//     }
//
// 
//
//     @AfterClass
//
//     public static void tearDownAfterClass() throws Exception {
//
//          driver.close();   // close the tab it has opened
//
//          driver.quit();    // close the browser
//
//     }
//
// 
//
//     @Test
//
//     public void test() {
//
//          // and now use this to visit Google. It should be https
//
//          driver.get("https://www.google.pt");
//
//          driver.get("C:\\Users\\Pedro\\Desktop\\Schedule.html");
//
//
//          // Encontrar a tabela na página
//          WebElement tabela = driver.findElement(By.id("example-table"));
//
//          WebElement primeiraCelula = tabela.findElement(By.xpath("//div[@class='tabulator-cell'][1]"));
//          System.out.println("Conteúdo da primeira célula: " + primeiraCelula.getText());
//
//
//          // submit the form. WebDriver will find the form for us from the element
//
//          driver.close();

}

 

