/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.jquest.model.service;

import br.cefetmg.jquest.model.domain.UseLog;
import br.cefetmg.jquest.model.exception.BusinessException;
import br.cefetmg.jquest.model.exception.PersistenceException;
import java.util.List;

/**
 *
 * @author Haddad
 */
public interface UseLogManagement {
    public Long useLogInsert(UseLog useLog) throws BusinessException, PersistenceException;
    public boolean useLogUpdate(UseLog useLog) throws BusinessException, PersistenceException;
    public boolean useLogRemove(Long userId, Long useLogSeq) throws PersistenceException;
    public UseLog getUseLogBySeq(Long userId, Long useLogSeq) throws PersistenceException;
    public List<UseLog> getAllLogsByUserId(Long userId) throws PersistenceException;  
}
