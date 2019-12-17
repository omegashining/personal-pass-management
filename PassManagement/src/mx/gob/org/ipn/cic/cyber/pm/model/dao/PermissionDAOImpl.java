package mx.gob.org.ipn.cic.cyber.pm.model.dao;

import mx.gob.org.ipn.cic.cyber.mu.model.dao.HibernateAbstractDAO;
import mx.gob.org.ipn.cic.cyber.pm.model.HibernateUtil;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Permission;

/**
 *
 * @author Gabriel
 */
public class PermissionDAOImpl extends HibernateAbstractDAO {
    
    public PermissionDAOImpl() {
        super(Permission.class, HibernateUtil.getSessionFactory());
    }
    
}
