package br.cefetmg.jquest.model.service;

import br.cefetmg.jquest.model.dao.ModuleDAO;
import br.cefetmg.jquest.model.dao.ModuleDAOImpl;
import br.cefetmg.jquest.model.domain.Module;
import br.cefetmg.jquest.model.exception.BusinessException;
import br.cefetmg.jquest.model.exception.PersistenceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Paula Ribeiro
 */
public class ModuleManagementImplTest {
    private static ModuleManagement moduleManagement;
    private static ModuleDAO moduleDAO;
    private Module module;
    
    public ModuleManagementImplTest() {}
    
    @BeforeClass
    public static void setUpClass() {
        moduleDAO = ModuleDAOImpl.getInstance();
        moduleManagement = new ModuleManagementImpl(moduleDAO);
    }
    
    @Before
    public void setUp() {
        this.module = new Module();
        this.module.setName("Teste");
        this.module.setDescription("Teste");
        this.module.setDomainId(1L);
    }
    
    @Test
    public void testModuleInsertNull() {
        module = null;
        try {
            moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            return;
        }
        fail("Null module registered");
    }
    
    @Test
    public void testModuleInsertNullName() {
        module.setName(null);
        try {
            moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            return;
        }
        fail("Module with null name registered");
    }
    
    @Test
    public void testModuleInsertNullDescription() {
        module.setDescription(null);
        try {
            moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            return;
        }
        fail("Module with null description registered");
    }
    
    @Test
    public void testModuleInsertCorrect() {
        Long id = -1L;
        try {
            id = moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            fail("Module failed to be registered");
            return;
        }
        if (id == -1L) {
            fail("Module failed to be registered");
            return;
        }
        
        //removes registered module
        try {
            moduleManagement.moduleRemove(id, module.getDomainId());
        } catch (PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    @Test
    public void testModuleUpdateNull() {
        try {
            moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Module moduleUp = null;
        try {
            moduleManagement.moduleUpdate(moduleUp);
        } catch (BusinessException | PersistenceException ex) {
            //removes registered module
            try {
                moduleManagement.moduleRemove(module.getId(), module.getDomainId());
            } catch (PersistenceException e) {
                Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, e);
            } 
            return;
        }
        fail("Module updated to null");
    }
    
    @Test
    public void testModuleUpdateNullName() {
        try {
            moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        module.setName(null);
        try {
            moduleManagement.moduleUpdate(module);
        } catch (BusinessException | PersistenceException ex) {
            //removes registered module
            try {
                moduleManagement.moduleRemove(module.getId(), module.getDomainId());
            } catch (PersistenceException e) {
                Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, e);
            } 
            return;
        }
        fail("Module updated with null name");
    }
    
    @Test
    public void testModuleUpdateNullDescription() {
        try {
            moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        module.setDescription(null);
        try {
            moduleManagement.moduleUpdate(module);
        } catch (BusinessException | PersistenceException ex) {
            //removes registered module
            try {
                moduleManagement.moduleRemove(module.getId(), module.getDomainId());
            } catch (PersistenceException e) {
                Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, e);
            } 
            return;
        }
        fail("Module updated with null description");
    }
    
    @Test
    public void testModuleUpdateNullId() {
        Long id = null;
        try {
            id = moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        module.setId(null);
        try {
            moduleManagement.moduleUpdate(module);
        } catch (BusinessException | PersistenceException ex) {
            //removes registered module
            try {
                moduleManagement.moduleRemove(id, module.getDomainId());
            } catch (PersistenceException e) {
                Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, e);
            } 
            return;
        }
        fail("Tried to update a module with a null id");
    }
    
    @Test
    public void testModuleUpdateCorrect() {
        try {
            moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        module.setName("Update test");
        try {
            moduleManagement.moduleUpdate(module);
        } catch (BusinessException | PersistenceException ex) {
            fail("Failed to update module");
            return;
        }
        try {
            if (!moduleManagement.getModuleById(module.getId(), module.getDomainId()).getName().equals("Update test")) {
                fail("Failed to update module");
            }
        } catch (PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        //removes registered module
        try {
            moduleManagement.moduleRemove(module.getId(), module.getDomainId());
        } catch (PersistenceException e) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, e);
        } 
    }
    
    @Test
    public void testModuleRemove() {
        try {
            moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            moduleManagement.moduleRemove(module.getId(), module.getDomainId());
        } catch (PersistenceException ex) {
            fail("Failed to remove module");
        }
    }
    
    @Test
    public void testGetModuleById() {
        try {
            moduleManagement.moduleInsert(module);
        } catch (BusinessException | PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Module moduleTest;
        try {
            moduleTest = moduleManagement.getModuleById(module.getId(), module.getDomainId());
        } catch (PersistenceException ex) {
            fail("Failed to get module by id");
        }
        finally {
            //removes registered module
            try {
                moduleManagement.moduleRemove(module.getId(), module.getDomainId());
            } catch (PersistenceException e) {
                Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, e);
            } 
        }
    }
    
    @Test
    public void testGetAll() {
        Module module2 = module;
        Long id1 = null;
        Long id2 = null;
        List<Module> list;
        try {
            id1 = moduleManagement.moduleInsert(module);
            id2 = moduleManagement.moduleInsert(module2);
        } catch (BusinessException | PersistenceException ex) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            list = moduleManagement.getAll();
        } catch (PersistenceException ex) {
            fail("Failed to get all modules");
            return;
        }
        if(list.isEmpty()) {
            fail("Failed to get all modules correctly");
        }
        //removes registered module
        try {
            moduleManagement.moduleRemove(id1, module.getDomainId());
            moduleManagement.moduleRemove(id2, module2.getDomainId());
        } catch (PersistenceException e) {
            Logger.getLogger(ModuleManagementImplTest.class.getName()).log(Level.SEVERE, null, e);
        } 
    }
}