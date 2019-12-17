package mx.gob.org.ipn.cic.cyber.pm.model.view.app.type;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import mx.gob.org.ipn.cic.cyber.mu.model.enums.DataTypeE;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.ParamSQL;
import mx.gob.org.ipn.cic.cyber.pm.model.service.TypesS;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Types;

/**
 *
 * @author Gabriel
 */
public class FillTypeTable implements Runnable {
    
    private final DefaultTableModel model;

    public FillTypeTable(DefaultTableModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        while (this.model.getRowCount() > 0) {
            this.model.removeRow(0);
        }
        
        FilterSQL filterSQL = new FilterSQL("t");
        filterSQL.addParam(new ParamSQL("trash", "false", DataTypeE.BOOLEAN));
        
        TypesS typeS = new TypesS();
        List<Types> types = typeS.getAll(filterSQL);
        
        if( types != null ) {
            Object[] object;
            
            for(Types type: types) {
                object = new Object[3];
                object[0] = type.getIdType();
                object[1] = type.getTypeName();
                object[2] = type.isEnable() ? "Habilitado" : "Deshabilitado";

                this.model.addRow(object);
            }
        }
    }
    
}
