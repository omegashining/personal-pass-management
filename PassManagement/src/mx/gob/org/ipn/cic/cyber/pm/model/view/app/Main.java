package mx.gob.org.ipn.cic.cyber.pm.model.view.app;

import javax.swing.JFrame;

import mx.gob.org.ipn.cic.cyber.du.view.dialog.JLoading;

/**
 *
 * @author Gabriel
 */
public class Main {
    
    public static void main(String[] args) {
        JLoading loading = new JLoading("images/desktopography_loading.jpg", 1280, 800);
        JFrame.setDefaultLookAndFeelDecorated(true);
        JApplication application = new JApplication();
        
        Progress progress = new Progress(loading, application);
        
        Thread thread = new Thread(progress);
        thread.start();
    }
    
}
