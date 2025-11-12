/*package main;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        StartupScreen startup;
        System.out.println("Hello");

        //Window
        JFrame frame = new JFrame("BBGAME");
        StartupScreen startupScreen = new StartupScreen(frame);
        frame.add(startupScreen);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); // test change asdas

    }
}
 */

package main;
import javax.swing.JFrame;

public class Main {
    // Set this to true when debugging to skip startup/loading screens
    private static final boolean DEBUG_MODE = false; //set mu nyang true ini patse mag debug ka gege

    public static void main(String[] args) {
        System.out.println("Hello");

        JFrame frame = new JFrame("BBGAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        if (DEBUG_MODE) {
            // Debug mode: Load resources synchronously and start game directly
            System.out.println("🔧 DEBUG MODE: Skipping startup and loading screens");

            ResourceLoader loader = new ResourceLoader();
            System.out.println("Loading resources...");
            loader.loadAllResources();
            System.out.println("Resources loaded!");

            GamePanel gamePanel = new GamePanel(loader);
            frame.add(gamePanel);
            frame.pack();
            frame.setVisible(true);
            gamePanel.requestFocusInWindow();

        } else {
            // Normal mode: Show startup screen
            StartupScreen startupScreen = new StartupScreen(frame);
            frame.add(startupScreen);
            frame.pack();
            frame.setVisible(true);
        }
    }
}
