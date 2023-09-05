package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Queue;


public class Game {
    private static Game instance = null;
    private int width, height, quantity, sizeButton;
    boolean isSet, finished;
    ButtonMine[][] buttons;
    private Game(){
        //nothing to do
    }
    public static Game getInstance(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }
    public void newGame(){
        //default 20x20 with 62 mines, size = 30, just to test
        width = 20;
        height = 20;
        quantity = 62;
        sizeButton = 30;
        isSet = false;
        finished = false;
        buttons = new ButtonMine[height][];
        for(int i = 0; i < height; i++){
            buttons[i] = new ButtonMine[width];
            for(int j = 0; j < width; j++){
                buttons[i][j] = new ButtonMine(j, i, sizeButton);
            }
        }
    }
    public void setGame(int forbidden){
        isSet = true;
        setMines(forbidden);
        setNearMines();
    }
    public void setMines(int forbidden){
        /* TODO: ignore first click, shouldn't be a mine */
        Random r = new Random();
        int[] mines = r.ints(quantity+1, 0, width*height).toArray();
        for(int i = 0; i < quantity; i++){
            if(mines[i] == forbidden) continue;
            int x = mines[i] % 20;
            int y = mines[i] / 20;
            buttons[y][x].setMine();
        }
    }
    public void setNearMines(){
        int[] dx = {0, 1, -1, 0, 1, -1, 1, -1};
        int[] dy = {-1, -1, -1, 1, 1, 1, 0, 0};
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                for(int k = 0; k < 8; k++){
                    int pos_y = i + dx[k];
                    int pos_x = j + dy[k];
                    if(pos_x < width && pos_x >= 0 && pos_y < height && pos_y >= 0){
                        if(buttons[pos_y][pos_x].isMine) {
                            buttons[i][j].nearMines++;
                        }
                    }
                }
            }
        }
    }
    public void openNear(ButtonMine btn){
        //DFS-like
        int[] dx = {0, 1, -1, 0, 1, -1, 1, -1};
        int[] dy = {-1, -1, -1, 1, 1, 1, 0, 0};
        for (int k = 0; k < 8; k++) {
            int pos_y = btn.pos_y + dx[k];
            int pos_x = btn.pos_x + dy[k];
            if (pos_x < width && pos_x >= 0 && pos_y < height && pos_y >= 0) {
                buttons[pos_y][pos_x].onClick();
            }
        }
    }
    public void triggerMines(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(buttons[i][j].isMine) buttons[i][j].onClick();
            }
        }
        finished = true;
    }
    public JPanel drawGame(){
        JPanel jp = new JPanel();
        jp.setLayout(null);
        Dimension size = new Dimension(800, 600);
        jp.setPreferredSize(size);
        jp.setMinimumSize(size);
        jp.setMaximumSize(size);
        jp.setBackground(Color.black);
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                jp.add(buttons[i][j]);
            }
        }
        return jp;
    }
}
