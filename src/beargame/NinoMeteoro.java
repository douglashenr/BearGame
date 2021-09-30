/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.awt.Rectangle;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinny
 */
public class NinoMeteoro extends Thread{
    Game game;
    EnemyNino enemy_nino;
    int align_x_shot;
    int speed=5;
    Rectangle rect_meteoro;
    int largura=120;
    int altura=120;
    int posx=800;
    int altitude=50;
    javax.swing.JLabel lbl_meteoro;
    boolean active=true;
    int ID;
    
    public NinoMeteoro(Game gm, EnemyNino en, javax.swing.JLabel lbl_meteoro, int ID){
        this.game=gm;
        this.enemy_nino=en;
        this.lbl_meteoro=lbl_meteoro;
        rect_meteoro= new Rectangle();
        this.ID=ID;
        
        if (ID==1){
            posx=800;
            altitude=50;
        }  else if (ID==2){
            posx=700;
            altitude=100;
        } else if (ID==3){
            posx=100;
            altitude=50;
        }
    }
    
    @Override
    public void run(){
        lbl_meteoro.setVisible(true);
        lbl_meteoro.setBounds(posx, altitude, largura, altura);
        rect_meteoro.setBounds(posx, altitude, largura, altura);
        while (active == true){
            if (lbl_meteoro.getY()<500){
                try {
                    sleep(speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shot.class.getName()).log(Level.SEVERE, null, ex);
                }
                tick();
                try {
                    collide();
                } catch (InterruptedException ex) {
                    Logger.getLogger(NinoMeteoro.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                active=false;
            }
        }
        System.out.println("Morre Meteoro");
        active=false;
        lbl_meteoro.setVisible(false);    
    }
    
    void tick(){
        if (ID==1){
            posx-=2;
            altitude+=1;
            largura=60;
            altura=60;
            lbl_meteoro.setBounds(posx, altitude, largura, altura);
//                        game.lbl_test.setBounds(posx, altitude, largura, altura);
            rect_meteoro.setBounds(posx, altitude, largura, altura);
        }  else if (ID==2){
            posx-=2;
            altitude+=2;
            largura=60;
            altura=60;
            lbl_meteoro.setBounds(posx, altitude, largura, altura);
    //                    game.lbl_test.setBounds(posx, altitude, largura, altura);
            rect_meteoro.setBounds(posx, altitude, largura, altura);
        } else if (ID==3){
            posx+=2;
            altitude+=2;
            largura=120;
            altura=120;
            lbl_meteoro.setBounds(posx, altitude, largura, altura);
            largura=50;
            altura=60;
//            game.lbl_test.setBounds(posx+55, altitude+50, largura, altura);
            rect_meteoro.setBounds(posx+55, altitude+50, largura, altura);
        }
    }
    
    void collide() throws InterruptedException{
        if (game.ps.rect_teddy.intersects(rect_meteoro)) {
            active=false;
            game.setLife(-20);
            if (ID==1|| ID==2){
                game.ps.sofreDano(1);
            } else if (ID==3){
                game.setLife(-20);
                game.ps.sofreDano(2);
            }
        }
    }
    
    void collideEscudo() throws InterruptedException{
        if (game.ps.rect_escudo.intersects(rect_meteoro)) {
            active=false;
        }
    }
}
