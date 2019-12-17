package mx.gob.org.ipn.cic.cyber.pm.model.view.app.about;

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

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.panel.JGridBagPanel;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.view.app.JApplication;

/**
 *
 * @author Gabriel
 */
public class JAbout extends JFrame implements InterfaceWindow, ActionListener {
    
    private final JApplication application;
    
    private JGridBagPanel gbpPanel;
    private JButton bAccept;
    private Container container;

    public JAbout(String title, JApplication application) {
        super(title);
        
        this.application = application;
        
        this.initComponents();
        this.initGUI();
        this.initListeners();
        this.initWindow();
    }

    @Override
    public void initComponents() {
        this.gbpPanel = new JGridBagPanel(120, GridBagConstraints.CENTER);
        this.bAccept = new JButton("Aceptar");
        this.container = this.getContentPane();
    }

    @Override
    public void initGUI() {
        this.gbpPanel.addRows(11);
        
        this.gbpPanel.addComponent(0, 1, 120, -1, new JLabel("PassManagement"));
        this.gbpPanel.addComponent(0, 3, 120, -1, new JLabel("DESARROLLADO POR: Gabriel Baltazar Pérez"));
        this.gbpPanel.addComponent(0, 5, 120, -1, new JLabel("Versión 1.0.0"));
        this.gbpPanel.addComponent(0, 7, 120, -1, new JLabel("Copyright © 2015, Cybermagna, Todos los derechos reservados."));
        this.gbpPanel.addComponent(0, 9, 120, -1, this.bAccept);
        
        this.container.add(this.gbpPanel, BorderLayout.CENTER);
    }

    @Override
    public void initListeners() {
        this.bAccept.addActionListener(this);
        
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

        if(command.equals("Aceptar")) {
            this.close();
        }
    }
    
    public void close() {
        this.application.setEnabled(true);
        this.dispose();
    }
    
}
