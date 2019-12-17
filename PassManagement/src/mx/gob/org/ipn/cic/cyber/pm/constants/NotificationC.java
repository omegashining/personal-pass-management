package mx.gob.org.ipn.cic.cyber.pm.constants;

import java.util.ResourceBundle;

/**
 *
 * @author Gabriel
 */
public interface NotificationC {
    
    ResourceBundle resourceBundle = ResourceBundle.getBundle("notifications");
    
    // USER
    
    String USER_INSERT_SUCCESS = resourceBundle.getString("users.insert.success");
    String USER_INSERT_ERROR   = resourceBundle.getString("users.insert.error");
    
    String USER_UPDATE_SUCCESS = resourceBundle.getString("users.update.success");
    String USER_UPDATE_ERROR   = resourceBundle.getString("users.update.error");
    
    String USER_DELETE_SUCCESS = resourceBundle.getString("users.delete.success");
    String USER_DELETE_ERROR   = resourceBundle.getString("users.delete.error");
    
    // TYPE
    
    String TYPE_INSERT_SUCCESS = resourceBundle.getString("type.insert.success");
    String TYPE_INSERT_ERROR   = resourceBundle.getString("type.insert.error");
    
    String TYPE_UPDATE_SUCCESS = resourceBundle.getString("type.update.success");
    String TYPE_UPDATE_ERROR   = resourceBundle.getString("type.update.error");
    
    String TYPE_DELETE_SUCCESS = resourceBundle.getString("type.delete.success");
    String TYPE_DELETE_ERROR   = resourceBundle.getString("type.delete.error");
    
    // ROLE
    
    String ROLE_INSERT_SUCCESS = resourceBundle.getString("role.insert.success");
    String ROLE_INSERT_ERROR   = resourceBundle.getString("role.insert.error");
    
    String ROLE_UPDATE_SUCCESS = resourceBundle.getString("role.update.success");
    String ROLE_UPDATE_ERROR   = resourceBundle.getString("role.update.error");
    
    String ROLE_DELETE_SUCCESS = resourceBundle.getString("role.delete.success");
    String ROLE_DELETE_ERROR   = resourceBundle.getString("role.delete.error");
    
    // PERMISSION
    
    String PERMISSION_INSERT_SUCCESS = resourceBundle.getString("permission.insert.success");
    String PERMISSION_INSERT_ERROR   = resourceBundle.getString("permission.insert.error");
    
    String PERMISSION_UPDATE_SUCCESS = resourceBundle.getString("permission.update.success");
    String PERMISSION_UPDATE_ERROR   = resourceBundle.getString("permission.update.error");
    
    String PERMISSION_DELETE_SUCCESS = resourceBundle.getString("permission.delete.success");
    String PERMISSION_DELETE_ERROR   = resourceBundle.getString("permission.delete.error");
    
    // ACCOUNT
    
    String ACCOUNT_INSERT_SUCCESS = resourceBundle.getString("account.insert.success");
    String ACCOUNT_INSERT_ERROR   = resourceBundle.getString("account.insert.error");
    
    String ACCOUNT_UPDATE_SUCCESS = resourceBundle.getString("account.update.success");
    String ACCOUNT_UPDATE_ERROR   = resourceBundle.getString("account.update.error");
    
    String ACCOUNT_DELETE_SUCCESS = resourceBundle.getString("account.delete.success");
    String ACCOUNT_DELETE_ERROR   = resourceBundle.getString("account.delete.error");
    
}
