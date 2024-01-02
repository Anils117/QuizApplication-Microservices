package com.anil.questionservice.service;


import com.anil.questionservice.dao.QuestionDao;
import com.anil.questionservice.model.Question;
import com.anil.questionservice.model.QuestionWrapper;
import com.anil.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> add(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong!!",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Success!!",HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong!!",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQ) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName,numQ);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<Question> questions = new ArrayList<>();
        List<QuestionWrapper> questionWrappers = new ArrayList<>();

        for(Integer id  :questionIds){
            questions.add(questionDao.findById(id).get());
        }
        for(Question question : questions){
            QuestionWrapper questionWrapper = new QuestionWrapper();
            questionWrapper.setId(question.getId());
            questionWrapper.setQuestionTitle(question.getQuestionTitle());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());
            questionWrappers.add(questionWrapper);
        }
        return new ResponseEntity<>(questionWrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response response : responses){
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
