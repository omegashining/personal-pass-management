package mx.gob.org.ipn.cic.cyber.pm.model.dao;

import mx.gob.org.ipn.cic.cyber.mu.model.dao.HibernateAbstractDAO;
import mx.gob.org.ipn.cic.cyber.pm.model.HibernateUtil;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Account;

/**
 *
 * @author Gabriel
 */
public class AccountDAOImpl extends HibernateAbstractDAO {
    
    public AccountDAOImpl() {
        super(Account.class, HibernateUtil.getSessionFactory());
    }
    
}
