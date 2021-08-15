package com.example.wordsprocessingapp.selenium;

import com.example.wordsprocessingapp.controllers.api.MainApiController;
import com.example.wordsprocessingapp.controllers.gui.MainGuiController;
import com.example.wordsprocessingapp.entities.Request;
import com.example.wordsprocessingapp.selenium.config.ConfProperties;
import com.example.wordsprocessingapp.selenium.pages.HomePage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,properties = { "server.port=8080" })
public class HomePageTest {

    private WebDriver driver;

    private HomePage homePage;

    private String homePageUrl;

    @Before
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        homePageUrl = ConfProperties.getProperty("homepage");
        synchronized (this) {
            wait(1000);
        }
    }

    @Test
    public void addValidWordTest() throws InterruptedException {
        getPage();
        String validWord = "Word" + new Random().nextInt(10000);
        Integer elementsAmountBeforeAdding = homePage.getWordsStats().size();
        homePage.inputWord(validWord);
        homePage.clickProceedButton();
        synchronized (this) {
            wait(1000);
        }
        Integer elementsAmountAfterAdding = homePage.getWordsStats().size();
        driver.close();
        Assertions.assertThat(elementsAmountAfterAdding).isEqualTo(elementsAmountBeforeAdding + 1);
    }

    @Test
    public void addInvalidWordConsistingOnlyOfNumbersTest() throws InterruptedException {
        getPage();
        String invalidWord = "1234";
        homePage.inputWord(invalidWord);
        homePage.clickProceedButton();
        synchronized (this) {
            wait(1500);
        }
        String alertMessage = driver.switchTo().alert().getText();
        acceptAlertIfPresent(driver.switchTo().alert());
        driver.close();
        Assertions.assertThat(alertMessage).isEqualTo("Input word cannot consist only of numbers!");
    }

    @Test
    public void addEmptyWordTest() throws InterruptedException {
        getPage();
        String invalidWord = "";
        homePage.inputWord(invalidWord);
        homePage.clickProceedButton();
        synchronized (this) {
            wait(1500);
        }
        String alertMessage = driver.switchTo().alert().getText();
        acceptAlertIfPresent(driver.switchTo().alert());
        driver.close();
        Assertions.assertThat(alertMessage).isEqualTo("Input cannot be empty!");
    }

    private void getPage() {
        driver.get(homePageUrl);
    }

    private void acceptAlertIfPresent(Alert alert) {
        if(alert != null) alert.accept();
    }

}




