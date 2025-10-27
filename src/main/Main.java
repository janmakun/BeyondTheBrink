package main;
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
