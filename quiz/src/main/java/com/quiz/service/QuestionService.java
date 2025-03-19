package com.quiz.service;

import com.quiz.model.Question;
import com.quiz.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    public List<Question> getQuestions() {
        return questionRepo.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionRepo.findByCategory(category);
    }

    public Question addQuestion(Question question) {
        return questionRepo.save(question);
    }

    public Question updateQuestionById(int id, Question updatedQuestion) {
        return questionRepo.findById(id).map(existingQuestion -> {
            existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
            existingQuestion.setOption1(updatedQuestion.getOption1());
            existingQuestion.setOption2(updatedQuestion.getOption2());
            existingQuestion.setOption3(updatedQuestion.getOption3());
            existingQuestion.setOption4(updatedQuestion.getOption4());
            existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
            existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
            existingQuestion.setCategory(updatedQuestion.getCategory());
            return questionRepo.save(existingQuestion);
        }).orElse(null);
    }

    public void deleteQuestionById(int id) {
        questionRepo.deleteById(id);
    }
}
