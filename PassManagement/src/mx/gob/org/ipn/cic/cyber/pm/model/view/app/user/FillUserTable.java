package mx.gob.org.ipn.cic.cyber.pm.model.view.app.user;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import mx.gob.org.ipn.cic.cyber.mu.model.enums.DataTypeE;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.ParamSQL;
import mx.gob.org.ipn.cic.cyber.pm.model.service.UsersS;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Permission;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Users;

/**
 *
 * @author Gabriel
 */
public class FillUserTable implements Runnable {
    
    private final DefaultTableModel model;

    public FillUserTable(DefaultTableModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        while (this.model.getRowCount() > 0) {
            this.model.removeRow(0);
        }
        
        FilterSQL filterSQL = new FilterSQL("u");
        filterSQL.addParam(new ParamSQL("trash", "false", DataTypeE.BOOLEAN));
        
        UsersS usersS = new UsersS();
        List<Users> users = usersS.getAll(filterSQL);
        
        if(users != null) {
            Object[] object;
            String permissions;
            
            for(Users user: users) {
                if (!user.isEliminable()) {
                    continue;
                }
                
                object = new Object[6];
                object[0] = user.getIdUser();
                object[1] = user.getUsername();
                object[2] = user.getName() + " " + user.getPaternal() + " " + user.getMaternal();
                object[3] = user.getRole().getRoleName();
                
                permissions = "";
                for (Permission permission : user.getRole().getPermissions()) {
                    permissions = permissions.concat(permission.getPermissionName() + " ");
                }
                
                object[4] = permissions;
                object[5] = user.isEnable() ? "Habilitado" : "Deshabilitado";
                
                this.model.addRow(object);
            }
        }
    }
    
}
