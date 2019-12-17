package mx.gob.org.ipn.cic.cyber.pm.model.view.app.type;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import mx.gob.org.ipn.cic.cyber.du.view.dialog.JMessage;
import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.table.listener.PopupTableListener;
import mx.gob.org.ipn.cic.cyber.du.view.table.JCustomTable;
import mx.gob.org.ipn.cic.cyber.du.view.table.model.NotEditTableModel;
import mx.gob.org.ipn.cic.cyber.du.view.table.objects.Header;
import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.service.TypesS;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Types;

/**
 *
 * @author Gabriel
 */
public class JTypesTable extends JScrollPane implements InterfaceWindow, ActionListener {
    
    private final JTypes types;
    
    private JPopupMenu popupMenu;
    private JMenuItem miUpdate;
    private JMenuItem miDelete;
    private JCustomTable ctTypes;
    private NotEditTableModel model;
    private PopupTableListener popupListener;

    public JTypesTable(JTypes types) {
        this.types = types;
        
        this.initComponents();
        this.initGUI();
        this.initListeners();
        this.initWindow();
    }

    @Override
    public void initComponents() {
        this.popupMenu = new JPopupMenu();
        this.miUpdate = new JMenuItem("Actualizar Información");
        this.miDelete = new JMenuItem("Eliminar");
        this.ctTypes = new JCustomTable(new NotEditTableModel());
        this.model = (NotEditTableModel) this.ctTypes.getModel();
        this.popupListener = new PopupTableListener(this.ctTypes, this.popupMenu);
    }

    @Override
    public void initGUI() {
        Header[] headers = new Header[3];
        headers[0] = new Header("Id", 150);
        headers[1] = new Header("Tipo", 250);
        headers[2] = new Header("Estado");
        
        this.ctTypes.initializeHeaders(headers);
        this.ctTypes.setAutoCreateRowSorter(true);
        
        this.fillTable();
        
        this.miUpdate.setIcon(ImageUtil.imageIcon("images/type_edit.png"));
        this.miDelete.setIcon(ImageUtil.imageIcon("images/type_delete.png"));
        
        this.popupMenu.add(this.miUpdate);
        this.popupMenu.addSeparator();
        this.popupMenu.add(this.miDelete);
        
        this.add(this.popupMenu);
    }

    @Override
    public void initListeners() {
        this.ctTypes.addMouseListener(this.popupListener);
        
        this.miUpdate.addActionListener(this);
        this.miDelete.addActionListener(this);
    }

    @Override
    public void initWindow() {
        this.setViewportView(this.ctTypes);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "Actualizar Información":
                this.setEnabled(false);
                this.update();
                break;
            case "Eliminar":
                this.delete();
                break;
        }
    }
    
    public void fillTable() {
        Thread thread = new Thread(new FillTypeTable(this.model));
        SwingUtilities.invokeLater(thread);
    }
    
    private Integer getRowSelected() {
        return this.popupListener.getRowSelectedFromModel();
    }
    
    private Integer getIdType() {
        return (Integer) this.model.getValueAt(this.getRowSelected(), 0);
    }
    
    private String getTypeName() {
        return this.model.getValueAt(this.getRowSelected(), 1).toString();
    }
    
    public void create() {
        JCreateUpdateType createType = new JCreateUpdateType("Crear Tipo de Cuenta", false, null, this.types);
        createType.setLocationRelativeTo(this);
    }

    private void update() {
        TypesS typeS = new TypesS();
        Types type = typeS.getById(this.getIdType());

        if(type != null) {
            JCreateUpdateType updateType = new JCreateUpdateType("Actualizar Tipo de Cuenta", true, type, this.types);
            updateType.setLocationRelativeTo(this);
        }
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el tipo de cuenta: " + this.getTypeName() + "?" );

        if (JOptionPane.OK_OPTION == confirm) {
            TypesS typeS = new TypesS();
            Notification notification = typeS.deleteById(this.getIdType());

            if(notification.isSuccessful()) {
                this.model.removeRow(this.getRowSelected());

                JMessage.info(this, notification.getMessage());
            } else {
                JMessage.error(this, notification.getMessage());
            }
        }
    }
    
}
