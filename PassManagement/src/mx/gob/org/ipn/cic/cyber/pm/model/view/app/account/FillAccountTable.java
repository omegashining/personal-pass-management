package mx.gob.org.ipn.cic.cyber.pm.model.view.app.account;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import mx.gob.org.ipn.cic.cyber.mu.model.enums.ComparationE;
import mx.gob.org.ipn.cic.cyber.mu.model.enums.DataTypeE;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.ParamSQL;
import mx.gob.org.ipn.cic.cyber.pm.model.service.AccountS;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Account;

/**
 *
 * @author Gabriel
 */
public class FillAccountTable implements Runnable {
    
    private final int idUser;
    private final String typeName;
    private final DefaultTableModel model;

    public FillAccountTable(int idUser, String typeName, DefaultTableModel model) {
        this.idUser = idUser;
        this.typeName = typeName;
        this.model = model;
    }

    @Override
    public void run() {
        while (this.model.getRowCount() > 0) {
            this.model.removeRow(0);
        }
        
        FilterSQL filterSQL = new FilterSQL("a");
        filterSQL.addParam(new ParamSQL("user.idUser", this.idUser + "", DataTypeE.NUMBER, ComparationE.EQUAL));
        filterSQL.addParam(new ParamSQL("type.typeName", this.typeName, DataTypeE.STRING));
        filterSQL.addParam(new ParamSQL("trash", "false", DataTypeE.BOOLEAN));
        
        AccountS accountS = new AccountS();
        List<Account> accounts = accountS.getAll(filterSQL);
        
        if(accounts != null) {
            Object[] object;
            
            for(Account account: accounts) {
                object = new Object[4];
                object[0] = account.getIdAccount();
                object[1] = account.getAccountName();
                object[2] = account.getUsername();
                object[3] = account.getPassword();

                this.model.addRow(object);
            }
        }
    }
    
}
