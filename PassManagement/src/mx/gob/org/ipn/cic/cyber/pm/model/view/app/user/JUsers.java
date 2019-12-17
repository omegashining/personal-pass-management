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

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.panel.JGridBagPanel;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.JApplication;

/**
 *
 * @author Gabriel
 */
public class JUsers extends JFrame implements InterfaceWindow, ActionListener {
    
    private final JApplication application;
    
    private JGridBagPanel gbpPanel;
    private JUsersTable usersTable;
    private JButton bAdd;
    private JButton bClose;
    private Container container;

    public JUsers(String title, JApplication application) {
        super(title);
        
        this.application = application;
        
        this.initComponents();
        this.initGUI();
        this.initListeners();
        this.initWindow();
    }

    @Override
    public void initComponents() {
        this.gbpPanel = new JGridBagPanel(160, GridBagConstraints.BOTH);
        this.usersTable = new JUsersTable(this);
        this.bAdd = new JButton("Agregar");
        this.bClose = new JButton("Cerrar");
        this.container = this.getContentPane();
    }

    @Override
    public void initGUI() {
        this.bAdd.setIcon(ImageUtil.imageIcon("images/user_add.png"));
        this.bClose.setIcon(ImageUtil.imageIcon("images/close.png"));
        
        this.gbpPanel.addComponent(10, 0, 60, -1, this.bAdd);
        this.gbpPanel.addComponent(90, 0, 60, -1, this.bClose);

        this.container.add(this.usersTable, BorderLayout.CENTER);
        this.container.add(this.gbpPanel, BorderLayout.SOUTH);
    }

    @Override
    public void initListeners() {
        this.bAdd.addActionListener(this);
        this.bClose.addActionListener(this);
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }

    @Override
    public void initWindow() {
        this.setIconImage(ImageUtil.image("images/user_see.png"));
        this.setSize(900, 520);
        this.setLocationRelativeTo(this.application);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "Agregar":
                this.setEnabled(false);
                this.usersTable.create();
                break;
            case "Cerrar":
                this.close();
                break;
        }
    }
    
    public void close() {
        this.application.setEnabled(true);
        this.dispose();
    }

    public void reloadTable() {
        this.usersTable.fillTable();
    }
    
}
