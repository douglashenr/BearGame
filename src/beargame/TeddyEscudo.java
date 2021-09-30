/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinny
 */
public class TeddyEscudo extends Thread{
    Game game;
    Personagem ps;
    int altura=40;
    int largura=21;
    int altitude=340;
    int posx;
    
    public TeddyEscudo(Game gm, Personagem pr, int alix){
        posx=alix+70;
        this.game=gm;
        ps=pr;
        game.lbl_escudo.setBounds(0, 0, 0, 0);
        ps.rect_escudo.setBounds(0, 0, 0, 0);
    }
    
    @Override
    public void run(){
        System.out.println("Inicia Escudo");
        int x;
        game.setEnergy(-15);

        try {
            
            altitude=390;
            game.lbl_escudo.setVisible(true);
            game.lbl_escudo.setIcon(new ImageIcon(ps.image_sprite_escudo[0]));
            
            for (x=0; x<10; x++){
                sleep(25);
                game.lbl_escudo.setBounds(posx, altitude, largura, altura);
                ps.rect_escudo.setBounds(posx, altitude, largura, altura);
                posx++;
            }
  
            altura+=40;
            largura+=21;
            altitude=370;
            game.lbl_escudo.setIcon(new ImageIcon(ps.image_sprite_escudo[1]));
            for (x=0; x<10; x++){
                sleep(20);
                game.lbl_escudo.setBounds(posx, altitude, largura, altura);
                ps.rect_escudo.setBounds(posx, altitude, largura, altura);
                posx++;
            }

            altura+=40;
            largura+=21;
            altitude=340;
            game.lbl_escudo.setIcon(new ImageIcon(ps.image_sprite_escudo[2]));
            for (x=0; x<10; x++){
                sleep(20);
                game.lbl_escudo.setBounds(posx, altitude, largura, altura);
                ps.rect_escudo.setBounds(posx, altitude, largura, altura);
                posx++;
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(TeddyEscudo.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (ps.escuso_active == true && (game.energy>=10)){
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Shot.class.getName()).log(Level.SEVERE, null, ex);
            }
            game.setEnergy(-5);
        }
        System.out.println("Morre escudo");
        ps.rect_escudo.setBounds(0, 0, 0, 0);
        ps.escuso_active=false;
        game.lbl_escudo.setVisible(false);     
    }
}
