package mx.gob.org.ipn.cic.cyber.pm.model.dao;

import mx.gob.org.ipn.cic.cyber.mu.model.dao.HibernateAbstractDAO;
import mx.gob.org.ipn.cic.cyber.pm.model.HibernateUtil;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Users;

/**
 *
 * @author Gabriel
 */
public class UsersDAOImpl extends HibernateAbstractDAO {
    
    public UsersDAOImpl() {
        super(Users.class, HibernateUtil.getSessionFactory());
    }
    
}
