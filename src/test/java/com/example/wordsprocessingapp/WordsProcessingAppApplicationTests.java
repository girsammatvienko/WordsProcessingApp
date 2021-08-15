package com.example.wordsprocessingapp;

import com.example.wordsprocessingapp.controllers.api.MainApiController;
import com.example.wordsprocessingapp.controllers.gui.MainGuiController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class WordsProcessingAppApplicationTests {

    @Autowired
    private MainApiController mainApiController;

    @Autowired
    private MainGuiController mainGuiController;

    @Test
    public void contextsLoads() {
        Assertions.assertThat(mainApiController).isNotNull();
        Assertions.assertThat(mainGuiController).isNotNull();
    }

}
