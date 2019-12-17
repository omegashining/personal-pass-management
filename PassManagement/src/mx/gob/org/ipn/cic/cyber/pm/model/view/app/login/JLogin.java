package mx.gob.org.ipn.cic.cyber.pm.model.view.app.login;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.panel.JImageGridBagPanel;
import mx.gob.org.ipn.cic.cyber.mu.security.enums.AlgorithmE;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.mu.security.util.SecurityUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.service.UsersS;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.JApplication;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Users;

/**
 *
 * @author Gabriel
 */
public class JLogin extends JDialog implements InterfaceWindow, ActionListener {
    
    private final JApplication application;

    private JImageGridBagPanel imagePanel;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton bAccept;
    private JButton bCancel;

    public JLogin(JApplication application) {
        super(application, "Iniciar Sesión", true);

        this.application = application;

        this.initComponents();
        this.initGUI();
        this.initListeners();
        this.initWindow();
    }

    @Override
    public void initComponents() {
        this.imagePanel = new JImageGridBagPanel(ImageUtil.imageIcon("images/desktopography_login.jpg"), 120, GridBagConstraints.CENTER);
        this.tfUsername = new JTextField("", 12);
        this.pfPassword = new JPasswordField("", 12);
        this.bAccept = new JButton("Aceptar");
        this.bCancel = new JButton("Cancelar");
    }

    @Override
    public void initGUI() {
        JLabel lTitle = new JLabel("DATOS DE LA CUENTA");
        lTitle.setFont(new Font(lTitle.getName(), Font.BOLD, 20));
        
        JLabel lUsername = new JLabel("Usuario :");
        lUsername.setFont(new Font(lUsername.getName(), Font.BOLD, 14));
        
        JLabel lPassword = new JLabel("Contraseña :");
        lPassword.setFont(new Font(lPassword.getName(), Font.BOLD, 14));
        
        this.imagePanel.addRows(11);
        this.imagePanel.addComponent(0, 1, 120, -1, lTitle);
        
        this.imagePanel.changeConstraints(GridBagConstraints.BOTH);
        
        this.imagePanel.addComponent(5, 5, 35, -1, lUsername);
        this.imagePanel.addComponent(40, 5, 75, -1, this.tfUsername);
        
        this.imagePanel.addComponent(5, 7, 35, -1, lPassword);
        this.imagePanel.addComponent(40, 7, 75, -1, this.pfPassword);
        
        this.imagePanel.addComponent(10, 9, 45, -1, this.bAccept);
        this.imagePanel.addComponent(65, 9, 45, -1, this.bCancel);
    }

    @Override
    public void initListeners() {
        this.tfUsername.addKeyListener(new EnterListener());
        this.pfPassword.addKeyListener(new EnterListener());

        this.bAccept.addActionListener(this);
        this.bCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Aceptar":
                this.check();
                break;
            case "Cancelar":
                this.dispose();
                break;
        }
    }

    @Override
    public void initWindow() {
        this.add(this.imagePanel);
        this.setSize(400, 250);
        this.setResizable(false);
        this.setLocationRelativeTo(this.application);
        this.setUndecorated(true);
        this.setVisible(true);
    }

    private void check() {
        UsersS userS = new UsersS();
        Users user = userS.getByUsername(this.tfUsername.getText());

        if (user != null) {
            String password = new String(this.pfPassword.getPassword());
            String cypherPassword = SecurityUtil.encryptTextDigest(password, AlgorithmE.MD5);

            if (!user.isTrash() && user.isEnable() && user.getPassword().equals(cypherPassword)) {
                this.application.setEneable(user, true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos.");
        }
    }

    class EnterListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\n') {
                check();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}

    }

}
