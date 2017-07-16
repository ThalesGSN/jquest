/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.jquest.model.dao;

import br.cefetmg.jquest.model.domain.QuestionAlternative;
import br.cefetmg.jquest.model.exception.PersistenceException;
import java.util.List;

/**
 *
 * @author João Pedro Renan
 */
public interface QuestionAlternativeDAO {
    public Long insert(QuestionAlternative closedEndedAlt) throws PersistenceException;
    public boolean update(QuestionAlternative closedEndedAlt) throws PersistenceException;
    public boolean remove(Long closedEndedAltId, Long questionId) throws PersistenceException;
    public QuestionAlternative getQuestionAlternativeById(Long closedEndedAltId, Long questionId) throws PersistenceException;
    public List<QuestionAlternative> getAlternativesByQuestionId(Long questionId) throws PersistenceException;
    public List<QuestionAlternative> listAll() throws PersistenceException;
}
