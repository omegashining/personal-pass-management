package mx.gob.org.ipn.cic.cyber.pm.constants;

import java.util.ResourceBundle;

/**
 *
 * @author Gabriel
 */
public interface ErrorC {
    
    ResourceBundle resourceBundle = ResourceBundle.getBundle("errors");
    
    String ERROR_GETBYUSERNAME = resourceBundle.getString("service.getbyname");
    String ERROR_GETBYID       = resourceBundle.getString("service.getbyid");
    String ERROR_GETALL        = resourceBundle.getString("service.getall");
    String ERROR_GETBYPAGE     = resourceBundle.getString("service.getbypage");
    String ERROR_CREATE        = resourceBundle.getString("service.create");
    String ERROR_UPDATE        = resourceBundle.getString("service.update");
    String ERROR_DELETEBYID    = resourceBundle.getString("service.deletebyid");
    String ERROR_UPDATEBYID    = resourceBundle.getString("service.updatebyid");
    String ERROR_COUNT         = resourceBundle.getString("service.count");
    
}
