 package main;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        //Window
        JFrame frame = new JFrame("BBGAME");

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
