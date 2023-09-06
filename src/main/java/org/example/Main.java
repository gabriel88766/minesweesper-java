package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static JFrame frame;
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        SwingUtilities.invokeLater(() -> {
            Main.frame = new JFrame("Minesweeper");
            JMenuBar menu = new JMenuBar();
            //menu.set
            //menu.setSize(800,30);
            JMenu newGame = new JMenu("New Game");
            newGame.add(new NewGameItem("easy"));
            newGame.add(new NewGameItem("medium"));
            newGame.add(new NewGameItem("hard"));
            menu.add(newGame);
            frame.setJMenuBar(menu);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Game.getInstance().newGame("medium");
            frame.setContentPane(Game.getInstance().drawGame());

            //Display the window.
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

class NewGameItem extends JMenuItem implements ActionListener {
    public NewGameItem(String difficulty){
        super(difficulty);
        addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
         Game.getInstance().newGame(this.getText());
         Main.frame.setVisible(false);
         Main.frame.setContentPane(Game.getInstance().drawGame());
         Main.frame.setSize(Game.getInstance().paneWidth,Game.getInstance().paneHeight);
         Main.frame.setVisible(true);
    }
}