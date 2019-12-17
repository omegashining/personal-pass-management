package mx.gob.org.ipn.cic.cyber.pm.model.service;

import java.util.List;

import javax.annotation.Resource;

import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.mu.model.service.ServiceS;
import mx.gob.org.ipn.cic.cyber.pm.model.dao.UsersDAOImpl;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mx.gob.org.ipn.cic.cyber.pm.constants.ErrorC.*;
import static mx.gob.org.ipn.cic.cyber.pm.constants.NotificationC.*;

/**
 *
 * @author Gabriel
 */
public class UsersS implements ServiceS<Users> {
    
    private static final Logger logger = LoggerFactory.getLogger(UsersS.class);
    
    @Resource
    private final UsersDAOImpl usersDAO;
    
    public UsersS() {
        this.usersDAO = new UsersDAOImpl();
    }
    
    public Users getByUsername(String username) {
        try {
            return (Users) this.usersDAO.findOne("from Users as u where u.username='" + username + "'");
        } catch(Exception ex) {
            logger.error(ERROR_GETBYUSERNAME);
            logger.error(ex.getMessage());
            
            return null;
        }
    }
    
    public Notification changeStatusById(Integer id, Boolean status) {
        Notification notification;
        
        try {
            String query = "update Users set enable = " + status.toString() + " where idUser = " + id;
            
            notification = this.usersDAO.query(query);
            notification.setMessage(USER_UPDATE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_UPDATEBYID);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, USER_UPDATE_ERROR);
        }
        
        return notification;
    }
    
    @Override
    public Users getById(Integer id) {
        try {
            return (Users) this.usersDAO.getById(id);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYID);
            logger.error(ex.getMessage());
            
            return null;
        }
    }
    
    @Override
    public List<Users> getAll(FilterSQL filterSQL) {
        try {
            return (List<Users>) this.usersDAO.findMany("from Users " + filterSQL.getPrefix() + filterSQL);
        } catch(Exception ex) {
            logger.error(ERROR_GETALL);
            logger.error(ex.getMessage());
            
            return null;
        }
    }
    
    @Override
    public List<Users> getByPage(Integer page, Integer page_size, FilterSQL filterSQL) {
        try {
            return (List<Users>) this.usersDAO.findByPage("from Users " + filterSQL.getPrefix() + filterSQL, page, page_size);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYPAGE);
            logger.error(ex.getMessage());
            
            return null;
        }
    }
    
    @Override
    public Notification create(Users object) {
        Notification notification;
        
        try {
            notification = this.usersDAO.create(object);
            notification.setMessage(USER_INSERT_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_CREATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, USER_INSERT_ERROR);
        }
        
        return notification;
    }
    
    @Override
    public Notification update(Users object) {
        Notification notification;
        
        try {
            notification = this.usersDAO.update(object);
            notification.setMessage(USER_UPDATE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_UPDATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, USER_UPDATE_ERROR);
        }
        
        return notification;
    }
    
    @Override
    public Notification deleteById(Integer id) {
        Notification notification;
        
        try {
            String query = "update Users set trash = true where idUser = " + id;
            
            notification = this.usersDAO.query(query);
            notification.setMessage(USER_DELETE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_DELETEBYID);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, USER_DELETE_ERROR);
        }
        
        return notification;
    }
    
    @Override
    public long count(FilterSQL filterSQL) {
        try {
            return this.usersDAO.count("select count(*) from Users " + filterSQL.getPrefix() + filterSQL.getConditions());
        } catch(Exception ex) {
            logger.error(ERROR_COUNT);
            logger.error(ex.getMessage());
            
            return 0;
        }
    }
    
}
