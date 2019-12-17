package mx.gob.org.ipn.cic.cyber.pm.model.view.app.user;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.dialog.JMessage;
import mx.gob.org.ipn.cic.cyber.du.view.panel.JGridBagPanel;
import mx.gob.org.ipn.cic.cyber.mu.security.enums.AlgorithmE;
import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.mu.security.util.SecurityUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.service.UsersS;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.JApplication;

/**
 *
 * @author Gabriel
 */
public class JChangePassword extends JFrame implements InterfaceWindow, ActionListener {
    
    private final JApplication application;
    
    private JGridBagPanel gbpPanel;
    private JPasswordField pfPassword;
    private JPasswordField pfNew;
    private JPasswordField pfConfirm;
    private JButton bAccept;
    private JButton bCancel;
    private Container container;

    public JChangePassword(String title, JApplication application) {
        super(title);
        
        this.application = application;
        
        this.initComponents();
        this.initGUI();
        this.initListeners();
        this.initWindow();
    }

    @Override
    public void initComponents() {
        this.gbpPanel = new JGridBagPanel(120, GridBagConstraints.BOTH);
        this.pfPassword = new JPasswordField();
        this.pfNew = new JPasswordField();
        this.pfConfirm = new JPasswordField();
        this.bCancel = new JButton("Cancelar");
        this.bAccept = new JButton("Aceptar");
        this.container = this.getContentPane();
    }

    @Override
    public void initGUI() {
        this.gbpPanel.addRows(9);
        
        this.gbpPanel.addComponent(0, 1, 50, -1, new JLabel("Contraseña Actual :"));
        this.gbpPanel.addComponent(50, 1, 70, -1, this.pfPassword);
        
        this.gbpPanel.addComponent(0, 2, 120, -1, new JSeparator(JSeparator.HORIZONTAL));
        
        this.gbpPanel.addComponent(0, 3, 50, -1, new JLabel("Nueva Contraseña :"));
        this.gbpPanel.addComponent(50, 3, 70, -1, this.pfNew);
        
        this.gbpPanel.addComponent(0, 5, 50, -1, new JLabel("Confirmar Contraseña :"));
        this.gbpPanel.addComponent(50, 5, 70, -1, this.pfConfirm);
        
        this.gbpPanel.addComponent(0, 7, 55, -1, this.bAccept);
        this.gbpPanel.addComponent(65, 7, 55, -1, this.bCancel);
        
        this.container.add(this.gbpPanel, BorderLayout.CENTER);
    }

    @Override
    public void initListeners() {
        this.bAccept.addActionListener(this);
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
        this.setIconImage(ImageUtil.image("images/daffy_duck.png"));
        this.setSize(450, 240);
        this.setLocationRelativeTo(this.application);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Aceptar":
                this.changePassword();
                break;
            case "Cancelar":
                this.close();
                break;
        }
    }
    
    private boolean isEmpty(JPasswordField passwordField) {
        return passwordField != null && passwordField.getPassword().length > 0;
    }
    
    private String cypherPassword(JPasswordField passwordField) {
        String password = new String(passwordField.getPassword());
        
        return SecurityUtil.encryptTextDigest(password, AlgorithmE.MD5);
    }
    
    private boolean areEmpty() {
        return this.isEmpty(this.pfPassword) && this.isEmpty(this.pfNew) && this.isEmpty(this.pfConfirm);
    }
    
    private boolean isCorrectPassword() {
        String cypherPassword = this.cypherPassword(this.pfPassword);
        
        return this.application.getUser().getPassword().equals(cypherPassword);
    }
    
    private boolean areEqualPasswords() {
        return new String(this.pfNew.getPassword()).equals(new String(this.pfConfirm.getPassword()));
    }
    
    public void changePassword() {
        if (this.areEmpty()) {
            if (this.isCorrectPassword()) {
                if (this.areEqualPasswords()) {
                    String cypherPassword = this.cypherPassword(this.pfNew);
                    this.application.getUser().setPassword(cypherPassword);
                    
                    UsersS userS = new UsersS();
                    Notification notification = userS.update(this.application.getUser());
                    
                    if(notification.isSuccessful()) {
                        JMessage.info(this, notification.getMessage());
                        
                        this.close();
                    } else {
                        JMessage.error(this, notification.getMessage());
                    }
                } else {
                    JMessage.warn(this, "La nueva contraseña y su confirmación no son iguales.");
                }
            } else {
                JMessage.warn(this, "La contraseña actual que escribió es incorrecta.");
            }
        } else {
            JMessage.warn(this, "Debe llenar todos los campos vacíos.");
        }
    }
    
    public void close() {
        this.application.setEnabled(true);
        this.dispose();
    }
    
}
