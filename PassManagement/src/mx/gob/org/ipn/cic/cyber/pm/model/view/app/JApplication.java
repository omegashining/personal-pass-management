package mx.gob.org.ipn.cic.cyber.pm.model.view.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.util.SubstanceUtil;
import mx.gob.org.ipn.cic.cyber.du.view.table.model.thread.CleanTable;
import mx.gob.org.ipn.cic.cyber.mu.general.util.ClipboardUtil;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.account.FillAccountTable;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.about.JAbout;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.account.JAccountsTable;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.login.JLogin;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.type.JTypes;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.user.JChangePassword;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.user.JUsers;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Users;

/**
 *
 * @author Gabriel
 */
public final class JApplication extends JFrame implements InterfaceWindow, ActionListener, TreeSelectionListener {
    
    private Users user;
    
    private JMenuBar menuBar;
    private JMenu mFile;
    private JMenu mTools;
    private JMenu mSkins;
    private JMenu mHelp;
    private JMenuItem miInitSession;
    private JMenuItem miCloseSession;
    private JMenuItem miChangePassword;
    private JMenuItem miUsers;
    private JMenuItem miExit;
    private JMenuItem miTypes;
    private JMenuItem miAbout;
    private JToolBar toolBar;
    private JButton bAdd;
    private JTypesTree typesTree;
    private JEditorPane editorPane;
    private JAccountsTable accountsTable;
    private JLabel lStatus;
    private Container container;

    public Users getUser() {
        return user;
    }

    @Override
    public void initComponents() {
        this.menuBar = new JMenuBar();
        this.mFile = new JMenu("Archivo");
        this.mTools = new JMenu("Herramientas");
        this.mSkins = SubstanceUtil.skinsMenu();
        this.mHelp = new JMenu("Ayuda");
        this.miInitSession = new JMenuItem("Iniciar Sesión");
        this.miCloseSession = new JMenuItem("Cerrar Sesión");
        this.miChangePassword = new JMenuItem("Cambiar Contraseña");
        this.miUsers = new JMenuItem("Usuarios");
        this.miExit = new JMenuItem("Salir");
        this.miTypes = new JMenuItem("Tipos de Cuenta");
        this.miAbout = new JMenuItem("Acerca de...");
        this.toolBar = new JToolBar("Herramientas");
        this.bAdd = new JButton();
        this.typesTree = new JTypesTree("Secciones");
        this.editorPane = new JEditorPane();
        this.accountsTable = new JAccountsTable(this, this.editorPane);
        this.lStatus = new JLabel("Usuario: Ninguno");
        this.container = this.getContentPane();
    }
    
    @Override
    public void initGUI() {
        this.toolBar.add(this.bAdd);
        
        this.bAdd.setIcon(ImageUtil.imageIcon("images/account_add.png"));
        this.bAdd.setActionCommand("Agregar Cuenta");
        
        this.menuBar.add(this.mFile);
        this.menuBar.add(this.mTools);
        this.menuBar.add(this.mHelp);
        
        this.miInitSession.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        this.miUsers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
        this.miTypes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
        
        this.miInitSession.setIcon(ImageUtil.imageIcon("images/init_session.png"));
        this.miCloseSession.setIcon(ImageUtil.imageIcon("images/close_session.png"));
        this.miChangePassword.setIcon(ImageUtil.imageIcon("images/change_password.png"));
        this.miUsers.setIcon(ImageUtil.imageIcon("images/user_see.png"));
        this.miExit.setIcon(ImageUtil.imageIcon("images/exit.png"));
        this.miTypes.setIcon(ImageUtil.imageIcon("images/user_see.png"));
        this.miAbout.setIcon(ImageUtil.imageIcon("images/information.png"));
        
        this.mSkins.setIcon(ImageUtil.imageIcon("images/skins.png"));
        
        this.mFile.setMnemonic(KeyEvent.VK_A);
        this.mTools.setMnemonic(KeyEvent.VK_H);
        this.mHelp.setMnemonic(KeyEvent.VK_Y);
        
        this.mFile.add(this.miInitSession);
        this.mFile.add(this.miCloseSession);
        this.mFile.addSeparator();
        this.mFile.add(this.miChangePassword);
        this.mFile.add(this.miUsers);
        this.mFile.addSeparator();
        this.mFile.add(this.miExit);
        this.mTools.add(this.miTypes);
        this.mTools.addSeparator();
        this.mTools.add(this.mSkins);
        this.mHelp.add(this.miAbout);
        
        this.accountsTable.setMinimumSize(new Dimension(800, 300));
        
        this.editorPane.setEditable(false);
        this.editorPane.setMinimumSize(new Dimension(800, 150));
        this.editorPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lTitle = new JLabel("Información Adicional :");
        lTitle.setFont(new Font(lTitle.getName(), Font.BOLD, 15));
        lTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel pAditionalInfo = new JPanel();
        pAditionalInfo.setLayout(new BoxLayout(pAditionalInfo, BoxLayout.PAGE_AXIS));
        pAditionalInfo.add(lTitle);
        pAditionalInfo.add(Box.createVerticalStrut(10));
        pAditionalInfo.add(this.editorPane);
        
        JSplitPane spVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        spVertical.setTopComponent(this.accountsTable);
        spVertical.setBottomComponent(pAditionalInfo);
        
        JScrollPane spTree = new JScrollPane(this.typesTree);
        spTree.setMinimumSize(new Dimension(300, 500));
        
        JSplitPane spHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        spHorizontal.setLeftComponent(spTree);
        spHorizontal.setRightComponent(spVertical);
        
        this.container.add(this.toolBar, BorderLayout.PAGE_START);
        this.container.add(spHorizontal, BorderLayout.CENTER);
        this.container.add(this.lStatus, BorderLayout.SOUTH);
        
        this.setEneable(null, false);
    }
    
    @Override
    public void initListeners() {
        this.bAdd.addActionListener(this);
        
        this.miInitSession.addActionListener( this );
        this.miCloseSession.addActionListener(this);
        this.miChangePassword.addActionListener(this);
        this.miUsers.addActionListener(this);
        this.miTypes.addActionListener(this);
        this.miAbout.addActionListener(this);
        this.miExit.addActionListener(this);
        
        this.typesTree.addTreeSelectionListener(this);
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }
    
    @Override
    public void initWindow() {
        SubstanceUtil.loadSkin();
        
        this.setIconImage(ImageUtil.image("images/sylvester.png"));
        this.setJMenuBar(this.menuBar);
        this.setSize(new Dimension(1280, 800));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "Iniciar Sesión":
                JLogin login = new JLogin(this);
                break;
            case "Cerrar Sesión":
                this.setEneable(null, false);
                break;
            case "Cambiar Contraseña":
                this.setEnabled(false);
                JChangePassword changePassword = new JChangePassword("Cambiar Contraseña", this);
                break;
            case "Usuarios":
                this.setEnabled(false);
                JUsers users = new JUsers("Usuarios", this);
                break;
            case "Agregar Cuenta":
                this.accountsTable.create();
                break;
            case "Tipos de Cuenta":
                this.setEnabled(false);
                JTypes types = new JTypes("Tipos de Cuenta", this);
                break;
            case "Acerca de...":
                this.setEnabled(false);
                JAbout help = new JAbout("Acerca de...", this);
                break;
            case "Salir":
                this.close();
                break;
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.typesTree.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }
        
        this.fillAccountTable((String) node.getUserObject());
        
        this.editorPane.setText("");
    }
    
    private void close() {
        ClipboardUtil.clear();
        this.dispose();
    }

    public void setEneable(Users user, boolean isEneable) {
        this.miInitSession.setEnabled(!isEneable);
        this.miCloseSession.setEnabled(isEneable);
        
        this.user = isEneable ? user : null;
        
        this.miChangePassword.setEnabled(isEneable);
        
        if (isEneable) {
            if(user.getRole().getRoleName().equals("Administrador")) {
                this.miUsers.setEnabled(true);
                this.miTypes.setEnabled(true);
            } else {
                this.bAdd.setEnabled(true);
                this.typesTree.setEneable(true);
                this.accountsTable.setEnabled(true);
                this.editorPane.setEnabled(true);
            }
        } else {
            this.miUsers.setEnabled(false);
            this.miTypes.setEnabled(false);
            this.bAdd.setEnabled(false);
            this.typesTree.setEneable(false);
            this.accountsTable.setEnabled(false);
            this.editorPane.setEnabled(false);
            
            this.cleanAccountTable();
        }
        
        this.editorPane.setText("");
        this.lStatus.setText("Usuario : " + (isEneable ? user.getUsername() : "Ninguno"));
    }

    public void reloadTable(String typeName) {
        this.typesTree.selectNode(typeName);
        this.editorPane.setText("");
        
        this.fillAccountTable(typeName);
    }
    
    public void fillAccountTable(String typeName) {
        Thread thread = new Thread(new FillAccountTable(this.user.getIdUser(), typeName, this.accountsTable.getTableModel()));
        SwingUtilities.invokeLater(thread);
    }
    
    public void cleanAccountTable() {
        Thread thread = new Thread(new CleanTable(this.accountsTable.getTableModel()));
        SwingUtilities.invokeLater(thread);
    }
    
}

