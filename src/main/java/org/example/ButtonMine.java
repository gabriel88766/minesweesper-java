package org.example;

import javax.swing.*;
import java.awt.*;

public class ButtonMine extends JButton {
    boolean isMine, clicked;
    int pos_x, pos_y;
    int nearMines;
    public ButtonMine(int pos_x, int pos_y, int size){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        Dimension sz = new Dimension(size, size);
        this.setMaximumSize(sz);
        this.setMinimumSize(sz);
        this.setPreferredSize(sz);
        this.setBackground(new Color(0x485ac8));
        this.setLayout(null);
        this.setBounds(pos_x * size, pos_y * size, size, size);
        this.isMine = false;
        this.nearMines = 0;
        this.addActionListener(e -> onClick());
    }
    public void setMine(){
        isMine = true;
    }
    public void onClick(){
        if(Game.getInstance().finished) return;
        if(clicked) return;
        clicked = true;
        if(!Game.getInstance().isSet) {
            Game.getInstance().setGame(pos_y * 20 + pos_x);
        }
        System.out.println(nearMines);
        if(isMine){
            Icon icon = new ImageIcon("C:\\Users\\gabri\\IdeaProjects\\mineweesper-java\\src\\main\\java\\org\\example\\images\\mine.png");
            setIcon(icon);
            Game.getInstance().triggerMines();
        }else if(nearMines == 0){
            setBackground(Color.CYAN);
            Game.getInstance().openNear(this);
        }else{
            if(nearMines == 1){
                Icon icon = new ImageIcon("C:\\Users\\gabri\\IdeaProjects\\mineweesper-java\\src\\main\\java\\org\\example\\images\\1.png");
                setIcon(icon);
            }else if(nearMines == 2){
                Icon icon = new ImageIcon("C:\\Users\\gabri\\IdeaProjects\\mineweesper-java\\src\\main\\java\\org\\example\\images\\2.png");
                setIcon(icon);
            }else if(nearMines == 3){
                Icon icon = new ImageIcon("C:\\Users\\gabri\\IdeaProjects\\mineweesper-java\\src\\main\\java\\org\\example\\images\\3.png");
                setIcon(icon);
            }else{
                setBackground(Color.GREEN);
            }
        }
    }


}
