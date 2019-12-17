package mx.gob.org.ipn.cic.cyber.pm.model.service;

import java.util.List;

import javax.annotation.Resource;

import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.mu.model.service.ServiceS;
import mx.gob.org.ipn.cic.cyber.pm.model.dao.PermissionDAOImpl;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mx.gob.org.ipn.cic.cyber.pm.constants.ErrorC.*;
import static mx.gob.org.ipn.cic.cyber.pm.constants.NotificationC.*;

/**
 *
 * @author Gabriel
 */
public class PermissionS implements ServiceS<Permission> {
    
    private static final Logger logger = LoggerFactory.getLogger(PermissionS.class);
    
    @Resource
    private final PermissionDAOImpl permissionDAO;

    public PermissionS() {
        this.permissionDAO = new PermissionDAOImpl();
    }
    
    public Notification changeStatusById(Integer id, Boolean status) {
        Notification notification;
        
        try {
            String query = "update Permission set enable = " + status.toString() + " where idPermission = " + id;
            
            notification = this.permissionDAO.query(query);
            notification.setMessage(PERMISSION_UPDATE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_UPDATEBYID);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, PERMISSION_UPDATE_ERROR);
        }
        
        return notification;
    }

    @Override
    public Permission getById(Integer id) {
        try {
            return (Permission) this.permissionDAO.getById(id);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYID);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public List<Permission> getAll(FilterSQL filterSQL) {
        try {
            return (List<Permission>) this.permissionDAO.findMany("from Permission " + filterSQL.getPrefix() + filterSQL);
        } catch(Exception ex) {
            logger.error(ERROR_GETALL);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public List<Permission> getByPage(Integer page, Integer page_size, FilterSQL filterSQL) {
        try {
            return (List<Permission>) this.permissionDAO.findByPage("from Permission " + filterSQL.getPrefix() + filterSQL, page, page_size);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYPAGE);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public Notification create(Permission object) {
        Notification notification;
        
        try {
            notification = this.permissionDAO.create(object);
            notification.setMessage(PERMISSION_INSERT_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_CREATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, PERMISSION_INSERT_ERROR);
        }
        
        return notification;
    }

    @Override
    public Notification update(Permission object) {
        Notification notification;
        
        try {
            notification = this.permissionDAO.update(object);
            notification.setMessage(PERMISSION_UPDATE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_UPDATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, PERMISSION_UPDATE_ERROR);
        }
        
        return notification;
    }

    @Override
    public Notification deleteById(Integer id) {
        Notification notification;
        
        try {
            String query = "update Permission set trash = true where idPermission = " + id;
            
            notification = this.permissionDAO.query(query);
            notification.setMessage(PERMISSION_DELETE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_DELETEBYID);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, PERMISSION_DELETE_ERROR);
        }
        
        return notification;
    }

    @Override
    public long count(FilterSQL filterSQL) {
        try {
            return this.permissionDAO.count("select count(*) from Permission " + filterSQL.getPrefix() + filterSQL.getConditions());
        } catch(Exception ex) {
            logger.error(ERROR_COUNT);
            logger.error(ex.getMessage());
            
            return 0;
        }
    }
    
}
