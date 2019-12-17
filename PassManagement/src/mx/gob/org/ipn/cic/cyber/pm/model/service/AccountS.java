package mx.gob.org.ipn.cic.cyber.pm.model.service;

import java.util.List;

import javax.annotation.Resource;

import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.model.service.ServiceS;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.pm.model.dao.AccountDAOImpl;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mx.gob.org.ipn.cic.cyber.pm.constants.ErrorC.*;
import static mx.gob.org.ipn.cic.cyber.pm.constants.NotificationC.*;

/**
 *
 * @author Gabriel
 */
public class AccountS implements ServiceS<Account> {
    
    private static final Logger logger = LoggerFactory.getLogger(AccountS.class);
    
    @Resource
    private final AccountDAOImpl accountDAO;

    public AccountS() {
        this.accountDAO = new AccountDAOImpl();
    }

    @Override
    public Account getById(Integer id) {
        try {
            return (Account) this.accountDAO.getById(id);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYID);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public List<Account> getAll(FilterSQL filterSQL) {
        try {
            return (List<Account>) this.accountDAO.findMany("from Account " + filterSQL.getPrefix() + filterSQL);
        } catch(Exception ex) {
            logger.error(ERROR_GETALL);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public List<Account> getByPage(Integer page, Integer page_size, FilterSQL filterSQL) {
        try {
            return (List<Account>) this.accountDAO.findByPage("from Account " + filterSQL.getPrefix() + filterSQL, page, page_size);
        } catch(Exception ex) {
            logger.error(ERROR_GETBYPAGE);
            logger.error(ex.getMessage());
            
            return null;
        }
    }

    @Override
    public Notification create(Account object) {
        Notification notification;
        
        try {
            notification = this.accountDAO.create(object);
            notification.setMessage(ACCOUNT_INSERT_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_CREATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, ACCOUNT_INSERT_ERROR);
        }
        
        return notification;
    }

    @Override
    public Notification update(Account object) {
        Notification notification;
        
        try {
            notification = this.accountDAO.update(object);
            notification.setMessage(ACCOUNT_UPDATE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_UPDATE);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, ACCOUNT_UPDATE_ERROR);
        }
        
        return notification;
    }

    @Override
    public Notification deleteById(Integer id) {
        Notification notification;
        
        try {
            String query = "update Account set trash = true where idAccount = " + id;
            
            notification = this.accountDAO.query(query);
            notification.setMessage(ACCOUNT_DELETE_SUCCESS);
        } catch(Exception ex) {
            logger.error(ERROR_DELETEBYID);
            logger.error(ex.getMessage());
            
            notification = new Notification(false, ACCOUNT_DELETE_ERROR);
        }
        
        return notification;
    }

    @Override
    public long count(FilterSQL filterSQL) {
        try {
            return this.accountDAO.count("select count(*) from Account " + filterSQL.getPrefix() + filterSQL.getConditions());
        } catch(Exception ex) {
            logger.error(ERROR_COUNT);
            logger.error(ex.getMessage());
            
            return 0;
        }
    }
    
}
