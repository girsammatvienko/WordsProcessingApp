package com.project.wordsprocessingapp.controllers.gui;

import com.project.wordsprocessingapp.entities.Request;
import com.project.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.project.wordsprocessingapp.entities.exceptions.InputFormatException;
import com.project.wordsprocessingapp.services.WordProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gui")
public class MainGuiController {
    private WordProcessingService service;

    @Autowired
    public MainGuiController(WordProcessingService service) {
        this.service = service;
    }

    @RequestMapping("/getAll")
    public String getAllStatistics(Model model) {
        model.addAttribute("statisticsMap", service.getCurrentStat());
        model.addAttribute("request", new Request());
        return "stats/stats-list";
    }

    @RequestMapping("/proceed")
    public String evaluateSentence(@RequestBody Request request, Model model) throws EmptyPayloadException,
            InputFormatException {
        service.proceed(request);
        return "redirect:/gui/getAll";
    }

    @RequestMapping("/history")
    public String getRequestHistory(Model model) {
        model.addAttribute("requestHistory", service.getRequestHistory());
        return "requests/request-history-list";
    }


}
