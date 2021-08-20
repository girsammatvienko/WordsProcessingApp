package com.project.wordsprocessingapp;

import com.project.wordsprocessingapp.controllers.api.MainApiController;
import com.project.wordsprocessingapp.controllers.gui.MainGuiController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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
