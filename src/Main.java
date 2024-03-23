import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main {
    public static void main(String[] args) {
        try {
            // Set Nimbus Look and Feel
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Converter GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Converter converter = new Converter();
            }
        });
    }
}
