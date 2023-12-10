package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.BeforeClass;

import org.junit.Test;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

public class Blackbox_tests{
	
	static WebDriver driver;
	@BeforeClass
	     public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Pedro\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("file:///C:\\Users\\Pedro\\Desktop\\Schedule.html");
	}
 
	 @Test
     public void test() throws InterruptedException {
    	 
    	 WebElement inputElement = driver.findElement(By.cssSelector("input[type='search'][placeholder='filter column...']"));
         inputElement.click(); 
         inputElement.clear(); 
         inputElement.sendKeys("LETI");
         Thread.sleep(2000);
         driver.findElement(By.cssSelector("button[data-page='5']")).click();
     }
    
}


 

