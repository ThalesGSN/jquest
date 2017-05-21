/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.jquest.model.dao;

import br.cefetmg.jquest.model.domain.Question;
import br.cefetmg.jquest.model.exception.PersistenceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Aluno
 */
public class QuestionDAOImpl implements QuestionDAO {

    private static QuestionDAOImpl questionDAO = null;
    private static HashMap<Long, Question> questionDB = new HashMap<Long, Question>();
    private static long questionCount = 0;

    private QuestionDAOImpl() {
    }
    
    public static QuestionDAOImpl getInstance() {
        if (questionDAO == null) {
            questionDAO = new QuestionDAOImpl();
        }
        return questionDAO;
    }
    
    @Override
    synchronized public void insert(Question question) throws PersistenceException{
        if (question == null) {
             throw new PersistenceException("Question cannot be null");           
        }
        Long questionId = question.getId();
        
        if (questionId != null && questionDB.containsKey(questionId)) {
            throw new PersistenceException("Duplicate key");
        }
        questionId = ++questionCount;
        question.setId(questionId);
        questionDB.put(questionId, question);
    }

    @Override
    synchronized public void update(Question question) throws PersistenceException {
        if (question == null) {
            throw new PersistenceException("Question cannot be null");
        }
        Long questionId = question.getId();
        if (questionId == null) {
            throw new PersistenceException("Entity Id cannot be null");
        }
        if (questionDB.containsKey(questionId)) {
            throw new PersistenceException("Question with id " + question.getId() + " is not persisted");
        }
        questionDB.replace(questionId, question);
    }

    @Override
    synchronized public Question remove(Long questionId) throws PersistenceException{
        if (questionId == null) {
            throw new PersistenceException("Question ID cannot be null");
        }
        if (!questionDB.containsKey(questionId)) {
            throw new PersistenceException("Question with id " + questionId + " is not persisted");
        }
        return questionDB.remove(questionId);
    }

    @Override
    synchronized public Question getQuestionById(Long questionId) throws PersistenceException{
        if (questionId == null) {
            throw new PersistenceException("Question ID cannot be null");
        }
        if (!questionDB.containsKey(questionId)) {
            throw new PersistenceException("Question with id " + questionId + " is not persisted");
        }
        return questionDB.get(questionId);
    }

    @Override
    public List<Question> listAll() {
        List<Question> questionList = new ArrayList<>();
        Iterator<Question> it = questionDB.values().iterator();
        while (it.hasNext()) {
            questionList.add(it.next());
        }
        return questionList;
    }

    
}
