package game2;

import game2.main.createGame.CreateGame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Start extends JFrame {

    public Start() {
        setTitle("Game Panel");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton jButton = new JButton("Play");
        add(jButton);
        jButton.setSize(100, 40);
        jButton.setLocation(this.getWidth() / 2  - jButton.getWidth() / 2, this.getHeight() / 2 - jButton.getHeight() );

        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createGame();
            }
        });
        repaint();
    }

    public void createGame() {
        CreateGame.getGame();
    }

    public static void main(String[] args) {
        Start start = new Start();
    }
}
