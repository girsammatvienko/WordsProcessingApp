package com.example.wordsprocessingapp.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"payload\"]")
    private WebElement inputWordField;

    @FindBy(xpath = "//*[@id=\"prc\"]")
    private WebElement proceedButton;

    @FindBy(xpath = "//*[@id=\"key\"]")
    private List<WebElement> words;

    @FindBy(xpath = "//*[@id=\"value\"]")
    private List<WebElement> entries;

    public void inputWord(String word) {
        inputWordField.sendKeys(word);
    }

    public void clickProceedButton() {proceedButton.click(); }

    public List<WebElement> getWords() { return words; }

    public List<WebElement> getEntries() { return entries; }

}
