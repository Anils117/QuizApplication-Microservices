package com.anil.questionservice.controller;


import com.anil.questionservice.model.Question;
import com.anil.questionservice.model.QuestionWrapper;
import com.anil.questionservice.model.Response;
import com.anil.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @RequestMapping("/")
    public String home(){
        return "Hello World!!!";
    }

    @RequestMapping("getAllQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @RequestMapping("getByCategory/{category}")
    public ResponseEntity<List<Question>> getByCategory(@PathVariable String category){
        return questionService.getByCategory(category);
    }

    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.add(question);
    }


    @GetMapping("deleteQuestion/{id}")
    public ResponseEntity<String > deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestion(id);
    }


    @GetMapping("generate")
    public  ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,@RequestParam Integer numQ){
        return questionService.getQuestionsForQuiz(categoryName,numQ);
    }


    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
