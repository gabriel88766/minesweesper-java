package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonMine extends JButton {
    static final Icon[] ICONS = {
        new ImageIcon("src\\main\\java\\org\\example\\images\\mine.png"),
        new ImageIcon("src\\main\\java\\org\\example\\images\\1.png"),
        new ImageIcon("src\\main\\java\\org\\example\\images\\2.png"),
        new ImageIcon("src\\main\\java\\org\\example\\images\\3.png"),
        new ImageIcon("src\\main\\java\\org\\example\\images\\4.png"),
        new ImageIcon("src\\main\\java\\org\\example\\images\\5.png"),
        new ImageIcon("src\\main\\java\\org\\example\\images\\6.png"),
        new ImageIcon("src\\main\\java\\org\\example\\images\\7.png"),
        new ImageIcon("src\\main\\java\\org\\example\\images\\8.png"),
        new ImageIcon("src\\main\\java\\org\\example\\images\\flag.png")
    };
    boolean isMine, clicked, flagged, mouseInside;
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
        this.flagged = false;
        this.clicked = false;
        this.nearMines = 0;
        this.addActionListener(e->onLeftClick());
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                mouseInside = true;
            }
            public void mouseExited(MouseEvent e){
                mouseInside = false;
            }
            public void mouseReleased(MouseEvent e){
                if(SwingUtilities.isRightMouseButton(e) && mouseInside){
                    //my code
                    onRightClick();
                }
            }
        });
    }
    public void setMine(){
        isMine = true;
    }
    public boolean isMine(){
        return isMine;
    }
    public void onLeftClick(){
        if(Game.getInstance().finished) return; //refuse to click
        if(flagged) return;
        if(clicked) {
            if(nearMines != 0){
                Game.getInstance().showNear(this);
            }
            return;
        }
        if(!Game.getInstance().isSet) {
            Game.getInstance().setGame(pos_y, pos_x);
        }
        showContent();
        if(!Game.getInstance().finished) Game.getInstance().checkGame();
    }
    public void showContent(){
        if(clicked) return;
        clicked = true;
        if(isMine){
            setBackground(new Color(0xfee1e1));
            setIcon(ICONS[0]);
            if(!Game.getInstance().finished) Game.getInstance().triggerMines();
        }else{
            setBackground(new Color(0xc3c3c3));
            if(nearMines == 0){
                Game.getInstance().openNear(this);
            }else{
                setIcon(ICONS[nearMines]);
            }
        }
    }
    public void onRightClick(){
        if(!clicked){
            if(flagged){
                setIcon(null);
            }else{
                setIcon(ICONS[9]);
            }
            flagged ^= true;
        }
    }
}
