package com.example.wordsprocessingapp.selenium;

import com.example.wordsprocessingapp.repositories.StatsRepository;
import com.example.wordsprocessingapp.selenium.config.ConfProperties;
import com.example.wordsprocessingapp.selenium.pages.HomePage;
import com.google.common.collect.Ordering;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,properties = { "server.port=8080" })
public class HomePageTest {

    private WebDriver driver;

    private HomePage homePage;

    @Autowired
    private StatsRepository statsRepository;

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
    public void evaluateValidSentence() throws InterruptedException {
        clearRepository();
        getPage();
        String validWord = "world hello earth sun";
        sendRequest(validWord);
        synchronized (this) {
            wait(1500);
        }
        Integer elementsAmountAfterSendingRequest = homePage.getWords().size();
        driver.close();
        Assertions.assertThat(elementsAmountAfterSendingRequest).isEqualTo(5); //4 + Unique words field
    }

    @Test
    public void addInvalidWordConsistingOnlyOfNumbersTest() throws InterruptedException {
        getPage();
        String invalidWord = "123456";
        sendRequest(invalidWord);
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
        String invalidPayload= "";
        sendRequest(invalidPayload);
        synchronized (this) {
            wait(1500);
        }
        String alertMessage = driver.switchTo().alert().getText();
        acceptAlertIfPresent(driver.switchTo().alert());
        driver.close();
        Assertions.assertThat(alertMessage).isEqualTo("Input cannot be empty!");
    }

    @Test
    public void wordsSortedInDescendingOrderTest() throws InterruptedException {
        getPage();
        sendRequest("Hello Hello Hello World World World Hello Earth Hello Earth");
        List<Integer> entries = new ArrayList<>();
        homePage.getEntries().
                stream()
                .forEach((e)->entries.add(Integer.parseInt(e.getText())));
        entries.remove(entries.size()-1);
        boolean sorted = Ordering.natural().reverse().isOrdered(entries);
        driver.close();
        assertTrue(sorted);
    }

    private void sendRequest(String payload) throws InterruptedException {
       homePage.inputText(payload);
       homePage.clickProceedButton();
       synchronized (this) {
           wait(1200);
       }
    }

    private void clearRepository() { statsRepository.deleteAll(); }

    private void getPage() {
        driver.get(homePageUrl);
    }

    private void acceptAlertIfPresent(Alert alert) {
        if(alert != null) alert.accept();
    }

}




