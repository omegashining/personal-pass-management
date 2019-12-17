package mx.gob.org.ipn.cic.cyber.pm.model.view.app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import mx.gob.org.ipn.cic.cyber.du.view.dialog.JLoading;

/**
 *
 * @author Gabriel
 */
public class Progress implements Runnable {
    
    private final JLoading loading;
    private final JApplication appliction;
    
    private int progress;
    
    public Progress(JLoading loading, JApplication application) {
        this.loading = loading;
        this.appliction = application;
        this.progress = this.loading.getProgress();
    }

    @Override
    public void run() {
        try {
            this.appliction.initComponents();
            iterate(30);
            
            this.appliction.initGUI();
            iterate(40);
            
            this.appliction.initListeners();
            iterate(10);
            
            this.appliction.initWindow();
            iterate(20);

            this.loading.dispose();
            
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    appliction.setVisible(true);
                }
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    public void iterate(int cantity) {
        for(int i = 0; i < cantity; i++) {
            try {
                Thread.sleep(10);
        
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        loading.setProgress(++progress);
                    }
                });
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
