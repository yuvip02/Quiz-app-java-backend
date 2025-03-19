package com.quiz.service;

import com.quiz.model.Question;
import com.quiz.model.QuestionWrapper;
import com.quiz.model.Quiz;
import com.quiz.model.Response;
import com.quiz.repository.QuestionRepo;
import com.quiz.repository.QuizRepo;
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
    QuizRepo quizRepo;

    @Autowired
    QuestionRepo questionRepo;

    public String createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);

        return "SUCCESS"; // ✅ Just return a String
    }


    public List<QuestionWrapper> getQuizQuestions(int id) {
        Optional<Quiz> quiz=quizRepo.findById(id);//what optional does it, return either null or the value
        List<Question> questionsFromDb=quiz.get().getQuestions();//when using optional we have to use get()
        List<QuestionWrapper> questions=new ArrayList<>();
        for(Question q:questionsFromDb)
        {
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questions.add(qw);
        }

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        Optional<Quiz> quiz=quizRepo.findById(id);
        if(quiz.isPresent()) {
            List<Question> questionsFromDb=quiz.get().getQuestions();

            int i=0;
            int right=0;

            for(Response response:responses){
                if(response.getResponse().equals(questionsFromDb.get(i).getRightAnswer()))
                {
                    right++;
                }
                i++;
            }
            return new ResponseEntity<>(right,HttpStatus.OK);
        }
        return new ResponseEntity<>(0,HttpStatus.NOT_FOUND);

    }

    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll(); // ✅ Returns List<Quiz>
    }

    public boolean deleteQuiz(int id) {
        if (quizRepo.existsById(id)) {
            quizRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
