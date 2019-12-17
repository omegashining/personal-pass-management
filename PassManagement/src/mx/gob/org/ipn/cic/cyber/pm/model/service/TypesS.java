package mx.gob.org.ipn.cic.cyber.pm.model.service;

import java.util.List;

import javax.annotation.Resource;

import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.mu.model.service.ServiceS;
import mx.gob.org.ipn.cic.cyber.pm.model.dao.TypeDAOImpl;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mx.gob.org.ipn.cic.cyber.pm.constants.ErrorC.*;
import static mx.gob.org.ipn.cic.cyber.pm.constants.NotificationC.*;

/**
 *
 * @author Gabriel
 */
public class TypesS implements ServiceS<Types> {
    
    private static final Logger logger = LoggerFactory.getLogger(TypesS.class);
    
    @Resource
    private final TypeDAOImpl typeDAO;

    public TypesS() {
        this.typeDAO = new TypeDAOImpl();
    }
    
    public Notification changeStatusById(Integer id, Boolean status) {
        Notification notification;
        
        try {
            String query = "update Types set enable = " + status.toString() + " where idType = " + id;
            
            notification = this.typeDAO.query(query);
            notification.setMessage(TYPE_UPDATE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_UPDATEBYID);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, TYPE_UPDATE_ERROR);
        }
        
        return notification;
    }

    @Override
    public Types getById(Integer id) {
        try {
            return (Types) this.typeDAO.getById(id);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYID);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public List<Types> getAll(FilterSQL filterSQL) {
        try {
            return (List<Types>) this.typeDAO.findMany("from Types " + filterSQL.getPrefix() + filterSQL);
        } catch(Exception ex) {
            ex.printStackTrace();
            logger.error(ERROR_GETALL);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public List<Types> getByPage(Integer page, Integer page_size, FilterSQL filterSQL) {
        try {
            return (List<Types>) this.typeDAO.findByPage("from Types " + filterSQL.getPrefix() + filterSQL, page, page_size);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYPAGE);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public Notification create(Types object) {
        Notification notification;
        
        try {
            notification = this.typeDAO.create(object);
            notification.setMessage(TYPE_INSERT_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_CREATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, TYPE_INSERT_ERROR);
        }
        
        return notification;
    }

    @Override
    public Notification update(Types object) {
        Notification notification;
        
        try {
            notification = this.typeDAO.update(object);
            notification.setMessage(TYPE_UPDATE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_UPDATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, TYPE_UPDATE_ERROR);
        }
        
        return notification;
    }

    @Override
    public Notification deleteById(Integer id) {
        Notification notification;
        
        try {
            String query = "update Types set trash = true where idType = " + id;
            
            notification = this.typeDAO.query(query);
            notification.setMessage(TYPE_DELETE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_DELETEBYID);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, TYPE_DELETE_ERROR);
        }
        
        return notification;
    }

    @Override
    public long count(FilterSQL filterSQL) {
        try {
            return this.typeDAO.count("select count(*) from Types " + filterSQL.getPrefix() + filterSQL.getConditions());
        } catch(Exception ex) {
            logger.error(ERROR_COUNT);
            logger.error(ex.getMessage());
            
            return 0;
        }
    }
    
}
