package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.List;


public class Game {
    private static Game instance = null;
    private static final int[] dx = {0, 1, -1, 0, 1, -1, 1, -1};
    private static final int[] dy = {-1, -1, -1, 1, 1, 1, 0, 0};
    private int width, height, quantity, sizeButton;
    protected int paneWidth, paneHeight;
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
    public void newGame(String difficulty){
        //default 20x20 with 62 mines, size = 30, just to test
        switch (difficulty) {
            case "easy" -> {
                width = 9; height = 9; quantity = 10; sizeButton = 50; paneWidth = 530; paneHeight = 560;
            }
            case "medium" -> {
                width = 16; height = 16; quantity = 40; sizeButton = 35; paneWidth = 640; paneHeight = 640;
            }
            case "hard" -> {
                width = 30; height = 16; quantity = 99; sizeButton = 30; paneWidth = 1080; paneHeight = 640;
            }
            default -> {
                System.out.println("Something wrong in the code");
                System.exit(666);
            }
        }
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
    public void setGame(int pos_y, int pos_x){
        isSet = true;
        setMines(pos_y, pos_x);
        setNearMines();
    }
    public void setMines(int pos_y, int pos_x){
        List<Integer> mines = new ArrayList<>();
        for(int i = 0; i < width*height; i++){
            int x = i % width;
            int y = i / width;
            if(x <= (pos_x + 1) && x >= (pos_x - 1) && y <= (pos_y + 1) && y >= (pos_y - 1)) continue;
            mines.add(i);
        }
        Collections.shuffle(mines);
        for(int i = 0; i < quantity; i++){
            int x = mines.get(i) % width;
            int y = mines.get(i) / width;
            buttons[y][x].setMine();
        }
    }
    public void setNearMines(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                for(int k = 0; k < 8; k++){
                    int pos_y = i + dx[k];
                    int pos_x = j + dy[k];
                    if(pos_x < width && pos_x >= 0 && pos_y < height && pos_y >= 0){
                        if(buttons[pos_y][pos_x].isMine()) {
                            buttons[i][j].nearMines++;
                        }
                    }
                }
            }
        }
    }
    public void openNear(ButtonMine btn){
        for (int k = 0; k < 8; k++) {
            int pos_y = btn.pos_y + dx[k];
            int pos_x = btn.pos_x + dy[k];
            if (pos_x < width && pos_x >= 0 && pos_y < height && pos_y >= 0) {

                buttons[pos_y][pos_x].showContent();
            }
        }
    }
    public void showNear(ButtonMine btn){
        int nearFlagged = 0;
        for (int k = 0; k < 8; k++) {
            int pos_y = btn.pos_y + dx[k];
            int pos_x = btn.pos_x + dy[k];
            if (pos_x < width && pos_x >= 0 && pos_y < height && pos_y >= 0) {
                if(buttons[pos_y][pos_x].flagged){
                    nearFlagged++;
                }
            }
        }
        if(nearFlagged == btn.nearMines){
            for(int k = 0; k < 8; k++){
                int pos_y = btn.pos_y + dx[k];
                int pos_x = btn.pos_x + dy[k];
                if (pos_x < width && pos_x >= 0 && pos_y < height && pos_y >= 0) {
                    if(!buttons[pos_y][pos_x].clicked && !buttons[pos_y][pos_x].flagged){
                        buttons[pos_y][pos_x].showContent();
                    }
                }
            }
            checkGame();
        }
    }
    public void triggerMines(){
        finished = true;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(buttons[i][j].isMine) buttons[i][j].showContent();
            }
        }
        showMessage("lose");
    }
    public void checkGame(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(!buttons[i][j].isMine && !buttons[i][j].clicked){
                    return; //not finished
                }
            }
        }
        finished = true;
        showMessage("win");
    }
    private void showMessage(String message){
        if(Objects.equals(message, "win")) JOptionPane.showMessageDialog(Main.frame, "Congratulations! You Win!!!");
        else JOptionPane.showMessageDialog(Main.frame, "You Lose!");

    }
    public JPanel drawGame(){
        JPanel jp = new JPanel();
        GridLayout layout = new GridLayout(height,width);
        jp.setLayout(layout);
        Dimension size = new Dimension(paneWidth, paneHeight);
        jp.setPreferredSize(size);
        jp.setMinimumSize(size);
        jp.setMaximumSize(size);
        jp.setBackground(new Color(0x3f3f7f));
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                jp.add(buttons[i][j]);
            }
        }
        return jp;
    }
}
