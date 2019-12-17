package mx.gob.org.ipn.cic.cyber.pm.model.dao;

import mx.gob.org.ipn.cic.cyber.mu.model.dao.HibernateAbstractDAO;
import mx.gob.org.ipn.cic.cyber.pm.model.HibernateUtil;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Types;

/**
 *
 * @author Gabriel
 */
public class TypeDAOImpl extends HibernateAbstractDAO {
    
    public TypeDAOImpl() {
        super(Types.class, HibernateUtil.getSessionFactory());
    }
    
}
