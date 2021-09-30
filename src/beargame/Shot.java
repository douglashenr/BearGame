/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Rectangle;


/**
 *
 * @author Douglas
 */

public class Shot extends Thread{
    Game game;
    int align_x_shot;
    int speed=5;
    Rectangle rect_shot;
    int largura=37;
    int altura=15;
    
    public Shot(Game game, int align_x, Rectangle rect_shot){
        this.game=game;
        align_x_shot=align_x;
        this.rect_shot=rect_shot;
    }
    
    @Override
    public void run(){
        game.setEnergy(-25);
        game.lbl_shot.setVisible(true);
        game.lbl_shot.setBounds(align_x_shot, 410, largura, altura);
        while (game.ps.shot_active == true){
            if (game.lbl_shot.getX()<899){
                try {
                    sleep(speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shot.class.getName()).log(Level.SEVERE, null, ex);
                }

                rect_shot.setBounds(align_x_shot+=2, 410, largura, altura);
                game.lbl_shot.setBounds(align_x_shot+=2, 410, largura, altura);
            } else {
                game.ps.shot_active=false;
            }
        }
        System.out.println("Morre tiro");
        game.ps.rect_shot.setBounds(0, 0, 0, 0);
        game.ps.shot_active=false;
        game.lbl_shot.setVisible(false);    
    }
}
