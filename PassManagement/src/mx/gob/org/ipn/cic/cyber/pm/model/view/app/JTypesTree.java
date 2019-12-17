package mx.gob.org.ipn.cic.cyber.pm.model.view.app;

import javax.swing.tree.DefaultMutableTreeNode;

import mx.gob.org.ipn.cic.cyber.du.view.tree.MyTree;
import mx.gob.org.ipn.cic.cyber.mu.model.enums.DataTypeE;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.ParamSQL;
import mx.gob.org.ipn.cic.cyber.pm.model.service.TypesS;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Types;

/**
 *
 * @author Gabriel
 */
public class JTypesTree extends MyTree {

    public JTypesTree(String rootName) {
        super(rootName);
    }
    
    @Override
    protected void fillTree() {
        TypesS typeS = new TypesS();
        
        FilterSQL filterSQL = new FilterSQL("t");
        filterSQL.addParam(new ParamSQL("enable", "true", DataTypeE.BOOLEAN));
        
        for (Types type : typeS.getAll(filterSQL)) {
            this.rootNode.add(new DefaultMutableTreeNode(type.getTypeName()));
        }
        
        this.updateRoot();
    }
    
}
