package mx.gob.org.ipn.cic.cyber.pm.model.dao;

import mx.gob.org.ipn.cic.cyber.mu.model.dao.HibernateAbstractDAO;
import mx.gob.org.ipn.cic.cyber.pm.model.HibernateUtil;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Roles;

/**
 *
 * @author Gabriel
 */
public class RoleDAOImpl extends HibernateAbstractDAO {
    
    public RoleDAOImpl() {
        super(Roles.class, HibernateUtil.getSessionFactory());
    }
    
}
