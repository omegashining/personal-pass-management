package mx.gob.org.ipn.cic.cyber.pm.model.view.app.type;

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
import javax.swing.JTextField;

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.cyber.du.view.textfield.plaindocument.PlainDocumentFieldLimit;
import mx.gob.org.ipn.cic.cyber.du.view.dialog.JMessage;
import mx.gob.org.ipn.cic.cyber.du.view.panel.JGridBagPanel;
import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.mu.image.util.ImageUtil;
import mx.gob.org.ipn.cic.cyber.pm.model.service.TypesS;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Types;

/**
 *
 * @author Gabriel
 */
public class JCreateUpdateType extends JFrame implements InterfaceWindow, ActionListener {
    
    private final boolean isUpdate;
    private final Types cuType;
    private final JTypes types;
    
    private JGridBagPanel gbpPanel;
    private JTextField tfTypeName;
    private JCheckBox cbEnable;
    private JButton bSave;
    private JButton bCancel;
    private Container container;

    public JCreateUpdateType(String title, boolean isUpdate, Types cuType, JTypes types) {
        super(title);
        
        this.isUpdate = isUpdate;
        this.cuType = cuType;
        
        this.types = types;
        
        this.initComponents();
        this.initGUI();
        this.initListeners();
        this.initWindow();
    }

    @Override
    public void initComponents() {
        this.gbpPanel = new JGridBagPanel(155, GridBagConstraints.CENTER);
        this.tfTypeName = new JTextField();
        this.cbEnable = new JCheckBox();
        this.bSave = new JButton("Guardar Cambios");
        this.bCancel = new JButton("Cancelar");
        this.container = this.getContentPane();
    }

    @Override
    public void initGUI() {
        this.tfTypeName.setDocument(new PlainDocumentFieldLimit(45));
        this.tfTypeName.setText(this.cuType != null ? this.cuType.getTypeName() : "");
        
        this.cbEnable.setSelected(this.cuType != null ? this.cuType.isEnable() : true);
        
        this.bSave.setIcon(ImageUtil.imageIcon("images/save.png"));
        this.bCancel.setIcon(ImageUtil.imageIcon("images/close.png"));
        
        this.gbpPanel.addRows(9);
        this.gbpPanel.addComponent(0, 1, 155, -1, new JLabel("DATOS DEL TIPO"));
        
        this.gbpPanel.changeConstraints(GridBagConstraints.BOTH);
        
        this.gbpPanel.addComponent(10, 3, 35, -1, new JLabel("Nombre :"));
        this.gbpPanel.addComponent(45, 3, 100, -1, this.tfTypeName);
        
        this.gbpPanel.addComponent(10, 5, 35, -1, new JLabel("Habilitado :"));
        this.gbpPanel.addComponent(45, 5, 100, -1, this.cbEnable);
        
        this.gbpPanel.addComponent(10, 7, 60, -1, this.bSave);
        this.gbpPanel.addComponent(85, 7, 60, -1, this.bCancel);
        
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
        this.setSize(550, 210);
        this.setVisible(true);
        
        if (this.isUpdate) {
            this.setIconImage(ImageUtil.image("images/type_edit.png"));
        } else {
            this.setIconImage(ImageUtil.image("images/type_add.png"));
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
        this.types.setEnabled(true);
        this.dispose();
    }
    
    private Types getTypeFromFields() {
        Types type = new Types();
        type.setTypeName(this.tfTypeName.getText());
        type.setEnable(this.cbEnable.isSelected());
        
        if (this.isUpdate) {
            type.setIdType(this.cuType.getIdType());
        }
        
        return type;
    }
    
    private void save() {
        Types type = getTypeFromFields();
        
        if(!type.areEmptyFields()) {
            TypesS typeS = new TypesS();
            Notification notification = this.isUpdate ? typeS.update(type) : typeS.create(type);

            if(notification.isSuccessful()) {
                this.types.reloadTable();
                
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
