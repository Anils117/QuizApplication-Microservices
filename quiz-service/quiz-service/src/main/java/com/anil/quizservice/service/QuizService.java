package com.anil.quizservice.service;


import com.anil.quizservice.dao.QuizDao;
import com.anil.quizservice.feign.QuizInterface;
import com.anil.quizservice.model.QuestionWrapper;
import com.anil.quizservice.model.Quiz;
import com.anil.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizInterface quizInterface;
    @Autowired
    QuizDao quizDao;
    public ResponseEntity<String> createQuiz(String title, String category, int numQ) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id){
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questionWrappers = quizInterface.getQuestionsFromId(questionIds);
        return questionWrappers;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
