/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.jquest.model.service;

import br.cefetmg.jquest.model.dao.CommentaryDAO;
import br.cefetmg.jquest.model.dao.ForumDAOImpl;
import br.cefetmg.jquest.model.dao.QuestionDAOImpl;
import br.cefetmg.jquest.model.dao.UserDAOImpl;
import br.cefetmg.jquest.model.domain.Commentary;
import br.cefetmg.jquest.model.exception.BusinessException;
import br.cefetmg.jquest.model.exception.PersistenceException;
import java.util.List;

/**
 *
 * @author GABRIEL HADDAD
 */
public class CommentaryManagementImpl implements CommentaryManagement{
    
    private final CommentaryDAO commentaryDAO;
    private final ForumManagement forumManagement;
    private final QuestionManagement questionManagement;
    private final UserManagement userManagement;

    public CommentaryManagementImpl(CommentaryDAO commentaryDAO) {
        this.commentaryDAO = commentaryDAO;
        forumManagement =  new ForumManagementImpl(ForumDAOImpl.getInstance());
        questionManagement = new QuestionManagementImpl(QuestionDAOImpl.getInstance());
        userManagement = new UserManagementImpl(UserDAOImpl.getInstance());
    }
    
    
        
    @Override
    public Long commentaryInsert(Commentary commentary) throws BusinessException, PersistenceException {
        if (commentary == null)
            throw new BusinessException("Commentary cannot be null");
                    
        if (commentary.getDiscussionId()== null 
                || forumManagement.getForumById(commentary.getDiscussionId(), commentary.getQuestionId()) == null )
            throw new BusinessException("Commentary's discussionID doesn't exist.");
        
        if (commentary.getQuestionId()== null 
                || questionManagement.getQuestionById(commentary.getQuestionId()) == null)
            throw new BusinessException("Commentary's questionID doesn't exist.");
        
        if (commentary.getUserId()== null 
                || userManagement.getUserById(commentary.getUserId()) == null)
            throw new BusinessException("Commentary's userID doesn't exist.");

        if (commentary.getTextCommentary() == null ||commentary.getTextCommentary().isEmpty()  )
            throw new BusinessException("Commentary's text cannot be null or empty");        
        
        commentaryDAO.insert(commentary);
        return commentary.getCommentarySeq();
    }

    @Override
    public boolean commentaryUpdate(Commentary commentary) throws BusinessException, PersistenceException {
        if (commentary == null)
            throw new BusinessException("Commentary cannot be null");
        
        if (commentary.getCommentarySeq()== null)
            throw new BusinessException("Commentary's sequence cannot be null");
            
        if (commentary.getDiscussionId()== null)
            throw new BusinessException("Commentary's discussionID cannot be null");
        
        if (commentary.getQuestionId()== null)
            throw new BusinessException("Commentary's questionID cannot be null");
        
        if (commentary.getUserId()== null)
            throw new BusinessException("Commentary's userID cannot be null");
        
        if (commentary.getTextCommentary()== null
                || commentary.getTextCommentary().isEmpty())
            throw new BusinessException("Commentary's text cannot be null");  

        return commentaryDAO.update(commentary);
    }

    @Override
    public boolean commentaryRemove(Long COD_questao, Long COD_discussao, Long commentarySeq) throws PersistenceException {
        if (commentarySeq == null) {
            throw new PersistenceException("Commentary's sequence cannot be null");
        }
        return commentaryDAO.remove(COD_questao, COD_discussao, commentarySeq);
    }

    @Override
    public Commentary getcommentaryBySeq(Long COD_questao, Long COD_discussao, Long commentarySeq) throws PersistenceException {
        if (commentarySeq == null)
            throw new PersistenceException("Commentary's sequence cannot be null");
        
        return commentaryDAO.getCommentaryBySeq(COD_questao, COD_discussao, commentarySeq); //if the id isn't valid it throws an exception
    }

    @Override
    public List<Commentary> getCommentariesByForumId(Long forumId, Long questionId) throws PersistenceException {
        if (forumId == null || questionId == null) {
            throw new PersistenceException("Forum's id cannot be null");
        }

        return commentaryDAO.getCommentariesByForumId(forumId, questionId);
    }
}
