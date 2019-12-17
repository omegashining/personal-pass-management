package mx.gob.org.ipn.cic.cyber.pm.model.view.app.account;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mx.gob.org.ipn.cic.cyber.du.view.dialog.JMessage;
import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceComboInfo;
import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.textfield.plaindocument.PlainDocumentFieldLimit;
import mx.gob.org.ipn.cic.cyber.du.view.panel.JGridBagPanel;
import mx.gob.org.ipn.cic.cyber.du.view.combobox.renderer.DisplayNameRenderer;
import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.model.objects.FilterSQL;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.service.AccountS;
import mx.gob.org.ipn.cic.cyber.pm.model.service.TypesS;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.JApplication;
import mx.gob.org.ipn.cic.cyber.pm.util.SecurityU;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Account;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Types;

/**
 *
 * @author Gabriel
 */
public class JCreateUpdateAccount extends JFrame implements InterfaceWindow, ActionListener {
    
    private final boolean isUpdate;
    private final Account cuAccount;
    private final JApplication application;
    
    private JGridBagPanel gbpPanel;
    private JTextField tfAccountName;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextArea taUrl;
    private JTextArea taObservations;
    private JComboBox cbType;
    private JButton bSave;
    private JButton bCancel;
    private Container container;

    public JCreateUpdateAccount(String title, boolean isUpdate, Account cuAccount, JApplication application) {
        super(title);
        
        this.isUpdate = isUpdate;
        this.cuAccount = cuAccount;
        this.application = application;
        
        this.initComponents();
        this.initGUI();
        this.initListeners();
        this.initWindow();
    }

    @Override
    public void initComponents() {
        this.gbpPanel = new JGridBagPanel(200, GridBagConstraints.CENTER);
        this.tfAccountName = new JTextField();
        this.tfUsername = new JTextField();
        this.tfPassword = new JTextField();
        this.taUrl = new JTextArea(4, 65);
        this.taObservations = new JTextArea(4, 65);
        this.cbType = new JComboBox();
        this.bSave = new JButton("Guardar Cambios");
        this.bCancel = new JButton("Cancelar");
        this.container = this.getContentPane();
    }

    @Override
    public void initGUI() {
        this.tfAccountName.setDocument(new PlainDocumentFieldLimit(45));
        this.tfAccountName.setText(this.isUpdate ? this.cuAccount.getAccountName() : "");
        
        this.tfUsername.setDocument(new PlainDocumentFieldLimit(45));
        this.tfUsername.setText(this.isUpdate ? this.cuAccount.getUsername() : "");
        
        this.tfPassword.setDocument(new PlainDocumentFieldLimit(45));
        this.tfPassword.setText(this.isUpdate ? SecurityU.decrypt(this.cuAccount.getPassword()) : "");
        
        this.taUrl.setLineWrap(true);
        this.taUrl.setDocument(new PlainDocumentFieldLimit(255));
        this.taUrl.setText(this.isUpdate ? this.cuAccount.getUrl() : "");
        
        this.taObservations.setLineWrap(true);
        this.taObservations.setDocument(new PlainDocumentFieldLimit(255));
        this.taObservations.setText(this.isUpdate ? this.cuAccount.getObservations() : "");
        
        List<Types> types = new TypesS().getAll(new FilterSQL());
        
        if (!types.isEmpty()) {
            for (InterfaceComboInfo type : types) {
                this.cbType.addItem(type);

                if (this.isUpdate && type.getName().equals(this.cuAccount.getType().getTypeName())) {
                    this.cbType.setSelectedItem(type);
                }
            }
            
            this.cbType.setRenderer(new DisplayNameRenderer(this.cbType.getRenderer()));
        }
        
        this.bSave.setIcon(ImageUtil.imageIcon("images/save.png"));
        this.bCancel.setIcon(ImageUtil.imageIcon("images/close.png"));
        
        this.gbpPanel.addRows(23);
        this.gbpPanel.addComponent(0, 1, 200, -1, new JLabel("DATOS DEL TIPO"));
        
        this.gbpPanel.changeConstraints(GridBagConstraints.BOTH);
        
        this.gbpPanel.addComponent(10, 3, 35, -1, new JLabel("Nombre :"));
        this.gbpPanel.addComponent(45, 3, 100, -1, this.tfAccountName);
        
        this.gbpPanel.addComponent(10, 5, 35, -1, new JLabel("Usuario :"));
        this.gbpPanel.addComponent(45, 5, 100, -1, this.tfUsername);
        
        this.gbpPanel.addComponent(10, 7, 35, -1, new JLabel("Contraseña :"));
        this.gbpPanel.addComponent(45, 7, 100, -1, this.tfPassword);
        
        this.gbpPanel.addComponent(10, 9, 35, -1, new JLabel("URL :"));
        this.gbpPanel.addComponent(45, 9, 145, 4, this.taUrl);
        
        this.gbpPanel.addComponent(10, 14, 35, 1, new JLabel("Observaciones :"));
        this.gbpPanel.addComponent(45, 14, 145, 4, this.taObservations);
        
        this.gbpPanel.addComponent(10, 19, 35, 1, new JLabel("Tipo de Cuenta :"));
        this.gbpPanel.addComponent(45, 19, 100, -1, this.cbType);
        
        this.gbpPanel.addComponent(30, 21, 60, -1, this.bSave);
        this.gbpPanel.addComponent(110, 21, 60, -1, this.bCancel);
        
        this.container.add(this.gbpPanel, BorderLayout.CENTER);
    }

    @Override
    public void initListeners() {
        this.bSave.addActionListener(this);
        this.bCancel.addActionListener(this);
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }

    @Override
    public void initWindow() {
        this.setResizable(false);
        this.setSize(640, 480);
        this.setVisible(true);
        
        if (this.isUpdate) {
            this.setIconImage(ImageUtil.image("images/account_edit.png"));
        } else {
            this.setIconImage(ImageUtil.image("images/account_add.png"));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Guardar Cambios":
                this.save();
                break;
            case "Cancelar":
                this.close();
                break;
        }
    }
    
    public void close() {
        this.application.setEnabled(true);
        this.dispose();
    }
    
    private Account getAccountFromFields() {
        Account account = new Account();
        account.setAccountName(this.tfAccountName.getText());
        account.setUsername(this.tfUsername.getText());
        account.setPassword(SecurityU.encrypt(this.tfPassword.getText()));
        account.setUrl(this.taUrl.getText());
        account.setObservations(this.taObservations.getText());
        
        if (this.isUpdate) {
            account.setIdAccount(this.cuAccount.getIdAccount());
            account.getUser().setIdUser(this.cuAccount.getUser().getIdUser());
        } else {
            account.getUser().setIdUser(this.application.getUser().getIdUser());
        }
        
        return account;
    }
    
    private void save() {
        Account account = this.getAccountFromFields();
        InterfaceComboInfo type = (InterfaceComboInfo) this.cbType.getSelectedItem();
        
        if(!account.areEmptyFields() && type != null) {
            account.getType().setIdType(type.getId());
            
            AccountS accountS = new AccountS();
            Notification notification = this.isUpdate ? accountS.update(account) : accountS.create(account);

            if(notification.isSuccessful()) {
                this.application.reloadTable(type.getName());
                
                JMessage.info(this, notification.getMessage());
            } else {
                JMessage.error(this, notification.getMessage());
            }
            
            this.close();
        } else {
            JMessage.warn(this, "Debe llenar todos los campos vacíos.");
        }
    }
    
}
