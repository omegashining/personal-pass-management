package mx.gob.org.ipn.cic.cyber.pm.model.view.app.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.table.listener.PopupTableListener;
import mx.gob.org.ipn.cic.cyber.du.view.dialog.JMessage;
import mx.gob.org.ipn.cic.cyber.du.view.table.JCustomTable;
import mx.gob.org.ipn.cic.cyber.du.view.table.model.NotEditTableModel;
import mx.gob.org.ipn.cic.cyber.du.view.table.objects.Header;
import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.general.util.ClipboardUtil;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.service.AccountS;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.JApplication;
import mx.gob.org.ipn.cic.cyber.pm.util.SecurityU;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Account;

/**
 *
 * @author Gabriel
 */
public class JAccountsTable extends JScrollPane implements InterfaceWindow, ActionListener {
    
    private final JApplication application;
    private final JEditorPane editorPane;
    
    private JPopupMenu popupMenu;
    private JMenuItem miCopy;
    private JMenuItem miUpdate;
    private JMenuItem miDelete;
    private JCustomTable ctAccounts;
    private NotEditTableModel model;
    private PopupTableListener popupListener;

    public JAccountsTable(JApplication application, JEditorPane editorPane) {
        this.application = application;
        this.editorPane = editorPane;
        
        this.initComponents();
        this.initGUI();
        this.initListeners();
        this.initWindow();
    }

    public NotEditTableModel getTableModel() {
        return model;
    }

    @Override
    public void initComponents() {
        this.popupMenu = new JPopupMenu();
        this.miCopy = new JMenuItem("Copiar Datos de Celda");
        this.miUpdate = new JMenuItem("Actualizar Información");
        this.miDelete = new JMenuItem("Eliminar");
        this.ctAccounts = new JCustomTable(new NotEditTableModel());
        this.model = (NotEditTableModel) this.ctAccounts.getModel();
        this.popupListener = new PopupTableListener(this.ctAccounts, this.popupMenu);
    }

    @Override
    public void initGUI() {
        Header[] headers = new Header[4];
        headers[0] = new Header("Id", 70);
        headers[1] = new Header("Nombre", 200);
        headers[2] = new Header("Usuario", 200);
        headers[3] = new Header("Contraseña");
        
        this.ctAccounts.initializeHeaders(headers);
        this.ctAccounts.setAutoCreateRowSorter(true);
        
        this.miCopy.setIcon(ImageUtil.imageIcon("images/copy.png"));
        this.miUpdate.setIcon(ImageUtil.imageIcon("images/account_edit.png"));
        this.miDelete.setIcon(ImageUtil.imageIcon("images/account_delete.png"));
        
        this.popupMenu.add(this.miCopy);
        this.popupMenu.addSeparator();
        this.popupMenu.add(this.miUpdate);
        this.popupMenu.addSeparator();
        this.popupMenu.add(this.miDelete);
        
        this.add(this.popupMenu);
    }

    @Override
    public void initListeners() {
        this.miCopy.addActionListener(this);
        this.miUpdate.addActionListener(this);
        this.miDelete.addActionListener(this);
        
        this.ctAccounts.addMouseListener(this.popupListener);
        
        this.ctAccounts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rowSelected = ctAccounts.getSelectedRow();
                
                if (rowSelected >= 0 && !e.getValueIsAdjusting()) {
                    int rowSelectedFromModel = ctAccounts.convertRowIndexToModel(rowSelected);
                    int idAccount = (Integer) model.getValueAt(rowSelectedFromModel, 0);

                    AccountS accountS = new AccountS();
                    Account account = accountS.getById(idAccount);
                    
                    StringBuilder sb = new StringBuilder("");
                    sb.append("URL :\n");
                    sb.append(account.getUrl());
                    sb.append("\n\n");
                    sb.append("Observaciones :\n");
                    sb.append(account.getObservations());
                    
                    editorPane.setText(sb.toString());
                }
            }
        });
    }

    @Override
    public void initWindow() {
        this.setViewportView(this.ctAccounts);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "Copiar Datos de Celda":
                ClipboardUtil.setText(this.getData());
                break;
            case "Actualizar Información":
                this.setEnabled(false);
                this.update();
                break;
            case "Eliminar":
                this.delete();
                break;
        }
    }
    
    private Integer getRowSelected() {
        return this.popupListener.getRowSelectedFromModel();
    }
    
    private Integer getColumnSelected() {
        return this.popupListener.getColumnSelectedFromModel();
    }
    
    private Integer getIdAccount() {
        return (Integer) this.model.getValueAt(this.getRowSelected(), 0);
    }
    
    private String getAccountName() {
        return this.model.getValueAt(this.getRowSelected(), 1).toString();
    }
    
    private String getData() {
        String data = this.model.getValueAt(this.getRowSelected(), this.getColumnSelected()).toString();
        
        if (this.popupListener.getHeaderName().equals("Contraseña")) {
            return SecurityU.decrypt(data);
        }
        
        return data;
    }
    
    public void create() {
        JCreateUpdateAccount createAccount = new JCreateUpdateAccount("Crear Cuenta", false, null, this.application);
        createAccount.setLocationRelativeTo(this);
    }

    private void update() {
        AccountS accountsS = new AccountS();
        Account account = accountsS.getById(this.getIdAccount());

        if(account != null) {
            JCreateUpdateAccount updateAccount = new JCreateUpdateAccount("Actualizar Cuenta", true, account, this.application);
            updateAccount.setLocationRelativeTo(this);
        }
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar la cuenta: " + this.getAccountName() + "?" );

        if (JOptionPane.OK_OPTION == confirm) {
            AccountS accountsS = new AccountS();
            Notification notification = accountsS.deleteById(this.getIdAccount());

            if(notification.isSuccessful()) {
                this.model.removeRow(this.popupListener.getRowSelectedFromModel());

                JMessage.info(this, notification.getMessage());
            } else {
                JMessage.error(this, notification.getMessage());
            }
        }
    }
    
}
