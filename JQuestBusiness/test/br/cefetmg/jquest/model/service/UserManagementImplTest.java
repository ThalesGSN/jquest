package br.cefetmg.jquest.model.service;

import br.cefetmg.jquest.model.dao.UserDAO;
import br.cefetmg.jquest.model.dao.UserDAOImpl;
import br.cefetmg.jquest.model.domain.User;
import br.cefetmg.jquest.model.exception.BusinessException;
import br.cefetmg.jquest.model.exception.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author João Pedro Renan
 */
public class UserManagementImplTest {
    
    private static UserManagementImpl userManager;
    private static UserDAO userDAO;
    private static List<Long> userList;
    private User user;   

    public UserManagementImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        userDAO = UserDAOImpl.getInstance();
        userManager = new UserManagementImpl(userDAO);
        userList = new ArrayList<>();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        this.user = new User();
        user.setEmail("a@a");
        user.setName("testUser");
        user.setId(0L);
        user.setSenha("opa");
    }

    @After
    public void tearDown() {
        
        for(Long id: userList)
            try {
                userDAO.remove(id);
            } catch (PersistenceException ex) {
                Logger.getLogger(UserManagementImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    public void testUpdateOfNullUser() throws Exception {
        try {
            this.user = null;
            this.userManager.userUpdate(user);
        }
        catch (PersistenceException | BusinessException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User cannot be null";
            // Exibe msg caso 2o param. seja falso
            assertTrue("Updating null user didn't throw an exception", msgErr.contains(msgEsperada));
            return;
        }
        fail("Updating null user didnt throw exception");
    }
    
    @Test
    public void testRemovalOfNullUserId() throws Exception {
        try {
            this.user.setId(null);
            this.userManager.userRemove(user.getId());
        } catch (PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User's Id cannot be null";
            // Exibe msg caso 2o param. seja falso
            assertTrue("Removal of null user", msgErr.contains(msgEsperada));
            return;
        }
    }
    
    @Test
    public void testGetUserByNullId() throws Exception {
        try {
            this.user.setId(null);
            this.userManager.getUserById(user.getId());
        } catch (PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User's Id cannot be null";
            // Exibe msg caso 2o param. seja falso
            assertTrue("Getting user by null ID", msgErr.contains(msgEsperada));
            return;
        }
    }
    
    @Test
    public void testInsertionOfEmptyNumberAsId() throws Exception {
        Long id = null;
        try {
            user.setId(null);
            id = userManager.userInsert(user);
        }
        catch (PersistenceException | BusinessException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User's ID is required";
            assertTrue("Was able to insert user with no ID", msgErr.contains(msgEsperada));
            return;
        }
        userList.add(id);
    }
    
    @Test
    public void testInsertionOfNullUser() throws Exception {
        try {
            user = null;
            userManager.userInsert(user);
        } catch (BusinessException | PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User cannot be null";
            // Exibe msg caso 2o param. seja falso
            assertTrue(msgErr.contains(msgEsperada));
            return;
        }
        fail("Insertion of null user");
    }
    
    @Test
    public void testUserWithNullEmailInsertion() throws Exception {
        try {
            user.setEmail(null);
            userManager.userInsert(user);
        } catch (BusinessException | PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User's email cannot be null";
            // Exibe msg caso 2o param. seja falso
            assertTrue(msgErr.contains(msgEsperada));
            return;
        }
        fail("Insertion of user with null email");
    }
    
    @Test
    public void testUserWithNullNameInsertion() throws Exception {
        try {
            user.setName(null);
            userManager.userInsert(user);
        } catch (BusinessException | PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User's name cannot be null";
            // Exibe msg caso 2o param. seja falso
            assertTrue(msgErr.contains(msgEsperada));
            return;
        }
        fail("Insertion of user with null name");
    }
    
    @Test
    public void testUserWithNullIdInsertion() throws Exception {
        try {
            user.setId(null);
            userManager.userInsert(user);
        } catch (BusinessException | PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User's ID is required";
            // Exibe msg caso 2o param. seja falso
            assertTrue(msgErr.contains(msgEsperada));
            return;
        }
        fail("Insertion of user with null Id");
    }  
    
    @Test
    public void testUserWithBlankNameInsertion() throws Exception {
        Long id = null;
        try {
            user.setName("");
            userManager.userInsert(user);
        } catch (BusinessException | PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User's name cannot be null";
            // Exibe msg caso 2o param. seja falso
            assertTrue(msgErr.contains(msgEsperada));
            return;
        }
        userList.add(id);
        fail("Insertion of user with blank name");
    }

    @Test
    public void testUserWithBlankEmailInsertion() throws Exception {
        try {
            user.setEmail("");
            userManager.userInsert(user);
        } catch (BusinessException | PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User's email cannot be null";
            // Exibe msg caso 2o param. seja falso
            assertTrue(msgErr.contains(msgEsperada));
            return;
        }
        fail("Insertion of user with blank email");
    }
    
    @Test
    public void testUserDuplicateKeyInsertion() throws Exception {
        Long id1 = null, id2 = null;
        try {
            User userDuplicate = new User(user.getName(), user.getEmail(), user.getId()+1, user.getSenha());
            id1 = userManager.userInsert(user);
            id2 = userManager.userInsert(userDuplicate);
        } catch (BusinessException | PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "Duplicate key";
            // Exibe msg caso 2o param. seja falso
            assertTrue(msgErr.contains(msgEsperada));
            return;
        }
        userList.add(id1);
        userList.add(id2);
        fail("Insertion of user with duplicate key");
    }
    
    @Test
    public void testRemovalOfUnexistentUser() throws Exception {
        try {
            userManager.userRemove(user.getId());
        } catch (PersistenceException ex) {
            String msgErr = ex.getMessage();
            String msgEsperada = "User with id " + user.getId() + " is not persisted";
            // Exibe msg caso 2o param. seja falso
            assertTrue(msgErr.contains(msgEsperada));
            return;
        }
        fail("Removal of unexistent user");
    }

    @Test
    public void testValidInsertion() throws Exception {
        Long id = null;
        try {
            id = userManager.userInsert(user);
        } catch (BusinessException | PersistenceException ex) {
            fail("Insertion of valid user");
        }
        userList.add(id);
    }
    
    @Test
    public void testValidRemoval() throws Exception {
        try {
            Long userId = userManager.userInsert(user);
            userManager.userRemove(userId);
        } catch (PersistenceException ex) {
            fail("Removal of valid user");
        }
    }


    
}