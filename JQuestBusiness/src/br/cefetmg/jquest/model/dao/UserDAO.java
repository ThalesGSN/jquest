/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.jquest.model.dao;

import br.cefetmg.jquest.model.domain.User;
import br.cefetmg.jquest.model.exception.PersistenceException;
import java.util.List;

/**
 *
 * @author Jota Renan
 */
public interface UserDAO {
    public void insert(User user) throws PersistenceException;
    public void update(User user) throws PersistenceException;
    public User remove(Long userId) throws PersistenceException;
    public User getUserById(Long userId) throws PersistenceException;
    public List<User> listAll() throws PersistenceException;
}
