package mx.gob.org.ipn.cic.cyber.pm.model.view.app.user;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.textfield.plaindocument.PlainDocumentFieldLimit;
import mx.gob.org.ipn.cic.cyber.du.view.dialog.JMessage;
import mx.gob.org.ipn.cic.cyber.du.view.panel.JGridBagPanel;
import mx.gob.org.ipn.cic.cyber.mu.security.enums.AlgorithmE;
import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.mu.security.util.SecurityUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.service.UsersS;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Users;

/**
 *
 * @author Gabriel
 */
public class JCreateUpdateUser extends JFrame implements InterfaceWindow, ActionListener {
    
    private final boolean isUpdate;
    private final Users cuUser;
    private final JUsers users;
    
    private JGridBagPanel gbpPanel;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirm;
    private JTextField tfName;
    private JTextField tfPaternal;
    private JTextField tfMaternal;
    private JCheckBox cbEnable;
    private JButton bSave;
    private JButton bCancel;
    private Container container;

    public JCreateUpdateUser(String title, boolean isUpdate, Users cuUser, JUsers users) {
        super(title);
        
        this.isUpdate = isUpdate;
        this.cuUser = cuUser;
        this.users = users;
        
        this.initComponents();
        this.initGUI();
        this.initListeners();
        this.initWindow();
    }

    @Override
    public void initComponents() {
        this.gbpPanel = new JGridBagPanel(160, GridBagConstraints.CENTER);
        this.tfUsername = new JTextField();
        this.pfPassword = new JPasswordField();
        this.pfConfirm = new JPasswordField();
        this.tfName = new JTextField();
        this.tfPaternal = new JTextField();
        this.tfMaternal = new JTextField();
        this.cbEnable = new JCheckBox();
        this.bSave = new JButton("Guardar Cambios");
        this.bCancel = new JButton("Cancelar");
        this.container = this.getContentPane();
    }

    @Override
    public void initGUI() {
        if (this.isUpdate) {
            this.tfUsername.setEditable(false);
        }
        
        this.tfUsername.setDocument(new PlainDocumentFieldLimit(45));
        this.tfUsername.setText(this.cuUser != null ? this.cuUser.getUsername() : "");
        
        this.pfPassword.setDocument(new PlainDocumentFieldLimit(45));
        this.pfPassword.setText("");
        
        this.pfConfirm.setDocument(new PlainDocumentFieldLimit(45));
        this.pfConfirm.setText("");
        
        this.tfName.setDocument(new PlainDocumentFieldLimit(30));
        this.tfName.setText(this.cuUser != null ? this.cuUser.getName() : "");
        
        this.tfPaternal.setDocument(new PlainDocumentFieldLimit(20));
        this.tfPaternal.setText(this.cuUser != null ? this.cuUser.getPaternal() : "");
        
        this.tfMaternal.setDocument(new PlainDocumentFieldLimit(20));
        this.tfMaternal.setText(this.cuUser != null ? this.cuUser.getMaternal() : "");
        
        this.cbEnable.setSelected(this.cuUser != null ? this.cuUser.isEnable() : true);
        
        this.bSave.setIcon(ImageUtil.imageIcon("images/save.png"));
        this.bCancel.setIcon(ImageUtil.imageIcon("images/close.png"));
        
        int plus = !this.isUpdate ? 4 : 0;
        int rows = 15 + plus;
        
        this.gbpPanel.addRows(rows);
        this.gbpPanel.addComponent(0, 1, 160, -1, new JLabel("DATOS DEL TIPO"));
        
        this.gbpPanel.changeConstraints(GridBagConstraints.BOTH);
        
        this.gbpPanel.addComponent(10, 3, 50, -1, new JLabel("Usuario :"));
        this.gbpPanel.addComponent(50, 3, 100, -1, this.tfUsername);
        
        if (!this.isUpdate) {
            this.gbpPanel.addComponent(10, 5, 50, -1, new JLabel("Contraseña :"));
            this.gbpPanel.addComponent(50, 5, 100, -1, this.pfPassword);
        
            this.gbpPanel.addComponent(10, 7, 50, -1, new JLabel("Confirmar Contraseña :"));
            this.gbpPanel.addComponent(50, 7, 100, -1, this.pfConfirm);
        }
        
        this.gbpPanel.addComponent(10, 5 + plus, 50, -1, new JLabel("Nombre :"));
        this.gbpPanel.addComponent(50, 5 + plus, 100, -1, this.tfName);
        
        this.gbpPanel.addComponent(10, 7 + plus, 50, -1, new JLabel("Apellido Paterno :"));
        this.gbpPanel.addComponent(50, 7 + plus, 100, -1, this.tfPaternal);
        
        this.gbpPanel.addComponent(10, 9 + plus, 50, -1, new JLabel("Apellido Materno :"));
        this.gbpPanel.addComponent(50, 9 + plus, 100, -1, this.tfMaternal);
        
        this.gbpPanel.addComponent(10, 11 + plus, 50, -1, new JLabel("Habilitado :"));
        this.gbpPanel.addComponent(50, 11 + plus, 100, -1, this.cbEnable);
        
        this.gbpPanel.addComponent(10, 13 + plus, 60, -1, this.bSave);
        this.gbpPanel.addComponent(85, 13 + plus, 60, -1, this.bCancel);
        
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
        this.setSize(600, this.isUpdate ? 340 : 420);
        this.setVisible(true);
        
        if (this.isUpdate) {
            this.setIconImage(ImageUtil.image("images/user_edit.png"));
        } else {
            this.setIconImage(ImageUtil.image("images/user_add.png"));
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
        this.users.setEnabled(true);
        this.dispose();
    }
    
    private Users getUserFromFields() {
        Users user = new Users();
        user.setUsername(this.tfUsername.getText());
        user.setName(this.tfName.getText());
        user.setPaternal(this.tfPaternal.getText());
        user.setMaternal(this.tfMaternal.getText());
        user.setEnable(this.cbEnable.isSelected());
        
        if (this.isUpdate) {
            user.setIdUser(this.cuUser.getIdUser());
            user.setPassword(this.cuUser.getPassword());
            user.getRole().setIdRole(this.cuUser.getRole().getIdRole());
            user.setEliminable(this.cuUser.isEliminable());
        } else {
            user.setPassword(new String(this.pfPassword.getPassword()));
            user.getRole().setIdRole(2);
            user.setEliminable(true);
        }
        
        return user;
    }
    
    private boolean areEqualPasswords() {
        return new String(this.pfPassword.getPassword()).equals(new String(this.pfConfirm.getPassword()));
    }
    
    private void encryptUserPassword(Users user) {
        String cyphertext = SecurityUtil.encryptTextDigest(user.getPassword(), AlgorithmE.MD5);
        user.setPassword(cyphertext);
    }
    
    private void save() {
        Users user = this.getUserFromFields();
        
        if(!user.areEmptyFields()) {
            UsersS usersS = new UsersS();
            Users userTmp = usersS.getByUsername(user.getUsername());
            
            if (!this.isUpdate) {
                if (userTmp != null && !userTmp.isTrash()) {
                    JMessage.warn(this, "El usuario ya existe en el sistema.");
                    return;
                }

                if (!this.areEqualPasswords()) {
                    JMessage.warn(this, "Las contraseñas deben ser iguales.");
                    return;
                }
            }
            
            Notification notification;
            if (this.isUpdate) {
                notification = usersS.update(user);
            } else {
                this.encryptUserPassword(user);
                notification = usersS.create(user);
            }
            
            if(notification.isSuccessful()) {
                this.users.reloadTable();
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
