package com.anil.quizservice.controller;


import com.anil.quizservice.model.QuestionWrapper;
import com.anil.quizservice.model.Response;
import com.anil.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;
    @PostMapping("createQuiz")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getTitle(),quizDto.getCategoryName(),quizDto.getNumQuestions());

    }

    @GetMapping("getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer id){
        return quizService.getQuiz(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> getResult(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);
    }
}
