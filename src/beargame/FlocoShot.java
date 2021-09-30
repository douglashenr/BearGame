/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.awt.Rectangle;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinny
 */
public class FlocoShot extends Thread{
    Game game;
    EnemyFloco enemy_floco;
    int align_x_shot;
    int speed=5;
    Rectangle rect_shot;
    int largura=37;
    int altura=15;
    javax.swing.JLabel lbl_shot;
    
    public FlocoShot(Game game, EnemyFloco ef, int align_x, Rectangle rect_shot, javax.swing.JLabel lbl_shot){
        this.game=game;
        align_x_shot=align_x;
        this.rect_shot=rect_shot;
        this.lbl_shot=lbl_shot;
        this.enemy_floco=ef;
    }
    
    @Override
    public void run(){
        lbl_shot.setVisible(true);
        lbl_shot.setBounds(align_x_shot, 410, largura, altura);
        while (enemy_floco.shot_active == true){
            if (lbl_shot.getX()>5){
                try {
                    sleep(speed);
                    collideEscudo();
                    collide();
                } catch (InterruptedException ex) {
                   
                }    
                rect_shot.setBounds(align_x_shot-=2, 410, largura, altura);
                lbl_shot.setBounds(align_x_shot-=2, 410, largura, altura);
            } else {
                enemy_floco.shot_active=false;
            }
        }
        System.out.println("Morre tiro");
        rect_shot.setBounds(0, 0, 0, 0);
        lbl_shot.setBounds(0, 0, 0, 0);
        enemy_floco.shot_active=false;
        lbl_shot.setVisible(false);    
    }
    
    void collide() throws InterruptedException{
        if (game.ps.rect_teddy.intersects(rect_shot)) {
            enemy_floco.shot_active=false;
            game.setLife(-10);
            game.lbl_person.setIcon(new ImageIcon(game.ps.image_machuca));
            game.ps.rect_teddy.setBounds(game.ps.align_x+15, 365, 92, 124);
            
            game.ps.sofreDano(1);
        }
    }
    
    void collideEscudo() throws InterruptedException{
        if (game.ps.rect_escudo.intersects(rect_shot)) {
            enemy_floco.shot_active=false;
        }
    }
}
