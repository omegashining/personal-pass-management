package mx.gob.org.ipn.cic.cyber.pm.model.service;

import java.util.List;

import javax.annotation.Resource;

import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.mu.model.service.ServiceS;
import mx.gob.org.ipn.cic.cyber.pm.model.dao.RoleDAOImpl;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mx.gob.org.ipn.cic.cyber.pm.constants.ErrorC.*;
import static mx.gob.org.ipn.cic.cyber.pm.constants.NotificationC.*;

/**
 *
 * @author Gabriel
 */
public class RolesS implements ServiceS<Roles> {
    
    private static final Logger logger = LoggerFactory.getLogger(RolesS.class);
    
    @Resource
    private final RoleDAOImpl roleDAO;

    public RolesS() {
        this.roleDAO = new RoleDAOImpl();
    }
    
    public Notification changeStatusById(Integer id, Boolean status) {
        Notification notification;
        
        try {
            String query = "update Roles set enable = " + status.toString() + " where idRole = " + id;
            
            notification = this.roleDAO.query(query);
            notification.setMessage(ROLE_UPDATE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_UPDATEBYID);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, ROLE_UPDATE_ERROR);
        }
        
        return notification;
    }

    @Override
    public Roles getById(Integer id) {
        try {
            return (Roles) this.roleDAO.getById(id);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYID);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public List<Roles> getAll(FilterSQL filterSQL) {
        try {
            return (List<Roles>) this.roleDAO.findMany("from Roles " + filterSQL.getPrefix() + filterSQL);
        } catch(Exception ex) {
            logger.error(ERROR_GETALL);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public List<Roles> getByPage(Integer page, Integer page_size, FilterSQL filterSQL) {
        try {
            return (List<Roles>) this.roleDAO.findByPage("from Roles " + filterSQL.getPrefix() + filterSQL, page, page_size);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYPAGE);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public Notification create(Roles object) {
        Notification notification;
        
        try {
            notification = this.roleDAO.create(object);
            notification.setMessage(ROLE_INSERT_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_CREATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, ROLE_INSERT_ERROR);
        }
        
        return notification;
    }

    @Override
    public Notification update(Roles object) {
        Notification notification;
        
        try {
            notification = this.roleDAO.update(object);
            notification.setMessage(ROLE_UPDATE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_UPDATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, ROLE_UPDATE_ERROR);
        }
        
        return notification;
    }

    @Override
    public Notification deleteById(Integer id) {
        Notification notification;
        
        try {
            String query = "update Roles set trash = true where idRole = " + id;
            
            notification = this.roleDAO.query(query);
            notification.setMessage(ROLE_DELETE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_DELETEBYID);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, ROLE_DELETE_ERROR);
        }
        
        return notification;
    }

    @Override
    public long count(FilterSQL filterSQL) {
        try {
            return this.roleDAO.count("select count(*) from Roles " + filterSQL.getPrefix() + filterSQL.getConditions());
        } catch(Exception ex) {
            logger.error(ERROR_COUNT);
            logger.error(ex.getMessage());
            
            return 0;
        }
    }
    
}
