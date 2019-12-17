package mx.gob.org.ipn.cic.cyber.pm.model.view.app.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.table.listener.PopupTableListener;
import mx.gob.org.ipn.cic.cyber.du.view.table.model.NotEditTableModel;
import mx.gob.org.ipn.cic.cyber.du.view.dialog.JMessage;
import mx.gob.org.ipn.cic.cyber.du.view.table.JCustomTable;
import mx.gob.org.ipn.cic.cyber.du.view.table.objects.Header;
import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.service.UsersS;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Users;

/**
 *
 * @author Gabriel
 */
public class JUsersTable extends JScrollPane implements InterfaceWindow, ActionListener {
    
    private final JUsers users;
    
    private JPopupMenu popupMenu;
    private JMenuItem miUpdate;
    private JMenuItem miDelete;
    private JCustomTable ctUsers;
    private NotEditTableModel model;
    private PopupTableListener popupListener;

    public JUsersTable(JUsers users) {
        this.users = users;
        
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
        this.ctUsers = new JCustomTable(new NotEditTableModel());
        this.model = (NotEditTableModel) this.ctUsers.getModel();
        this.popupListener = new PopupTableListener(this.ctUsers, this.popupMenu);
    }

    @Override
    public void initGUI() {
        Header[] headers = new Header[6];
        headers[0] = new Header("Id", 70);
        headers[1] = new Header("Usuario", 160);
        headers[2] = new Header("Nombre", 220);
        headers[3] = new Header("Rol", 120);
        headers[4] = new Header("Permisos", 180);
        headers[5] = new Header("Estado");
        
        this.ctUsers.initializeHeaders(headers);
        this.ctUsers.setAutoCreateRowSorter(true);
        
        this.fillTable();
        
        this.miUpdate.setIcon(ImageUtil.imageIcon("images/user_edit.png"));
        this.miDelete.setIcon(ImageUtil.imageIcon("images/user_delete.png"));
        
        this.popupMenu.add(this.miUpdate);
        this.popupMenu.addSeparator();
        this.popupMenu.add(this.miDelete);
        
        this.add(this.popupMenu);
    }

    @Override
    public void initListeners() {
        this.ctUsers.addMouseListener(this.popupListener);
        
        this.miUpdate.addActionListener(this);
        this.miDelete.addActionListener(this);
    }

    @Override
    public void initWindow() {
        this.setViewportView(this.ctUsers);
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
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el tipo de cuenta: " + this.getUserName() + "?" );
                if (JOptionPane.OK_OPTION == confirm) {
                    this.delete();
                }
                break;
        }
    }
    
    public void fillTable() {
        Thread thread = new Thread(new FillUserTable(this.model));
        SwingUtilities.invokeLater(thread);
    }
    
    private Integer getRowSelected() {
        return this.popupListener.getRowSelectedFromModel();
    }
    
    private Integer getIdUser() {
        return (Integer) this.model.getValueAt(this.getRowSelected(), 0);
    }
    
    private String getUserName() {
        return this.model.getValueAt(this.getRowSelected(), 1).toString();
    }
    
    public void create() {
        JCreateUpdateUser createUser = new JCreateUpdateUser("Crear Tipo de Cuenta", false, null, this.users);
        createUser.setLocationRelativeTo(this);
    }

    private void update() {
        UsersS userS = new UsersS();
        Users user = userS.getById(this.getIdUser());

        if(user != null) {
            JCreateUpdateUser updateUser = new JCreateUpdateUser("Actualizar Tipo de Cuenta", true, user, this.users);
            updateUser.setLocationRelativeTo(this);
        }
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Al eliminar el usario se borraran todas sus cuentas. ¿Esta de acuerdo?" );

        if (JOptionPane.OK_OPTION == confirm) {
            UsersS userS = new UsersS();
            Notification notification = userS.deleteById(this.getIdUser());

            if(notification.isSuccessful()) {
                this.model.removeRow(this.getRowSelected());

                JMessage.info(this, notification.getMessage());
            } else {
                JMessage.error(this, notification.getMessage());
            }
        }
    }
    
}
