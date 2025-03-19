package com.quiz.controller;

import com.quiz.model.QuestionWrapper;
import com.quiz.model.Quiz;
import com.quiz.model.Response;
import com.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody Map<String, Object> requestData) {
        String category = (String) requestData.get("category");
        int numQ = (int) requestData.get("numQ");
        String title = (String) requestData.get("title");

        String response = quizService.createQuiz(category, numQ, title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get-quiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id) {
        return new ResponseEntity<>(quizService.getQuizQuestions(id), HttpStatus.OK);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return new ResponseEntity<>(quizService.getAllQuizzes(), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable int id) {
        boolean deleted = quizService.deleteQuiz(id);
        return deleted ? new ResponseEntity<>("Quiz deleted", HttpStatus.OK)
                : new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
    }
}
