package com.example.wordsprocessingapp.selenium.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfProperties {
    protected static Properties PROPERTIES;

    private ConfProperties() {
    }

    static {
        try(FileInputStream fileInputStream = new FileInputStream("src/test/resources/seleniumConfig.properties")){
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key) { return PROPERTIES.getProperty(key); }
}
