/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.awt.Image;
import java.awt.Rectangle;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinny
 */
public class EnemyTotem extends Thread{
    Game game;
    Image[] image_sprite;
    int position;
    int largura = 120;
    int altura = 120;
    int altitude = 350;
    int sprite_position;
    Rectangle rect;
    boolean active=true;
    int life=60;
    int count_pulo=60;
    
    public EnemyTotem(Game game){
        this.game=game;
        carregaSprite();
        rect = new Rectangle();
        
        // Inserindo imagem de inicialização
        game.lbl_en_totem.setIcon(new ImageIcon(image_sprite[0]));
        game.lbl_en_totem.setBounds(900, altitude, largura, altura);
        
        sprite_position=0;
        active=true;
        
        game.lbl_en_totem.setVisible(true);
        game.lbl_en_totem.setBounds(800, 100, largura, altura);
        position=game.lbl_en_totem.getX();
    }
        
    @Override
    public void run(){     
        try {
            nasce();
        } catch (InterruptedException ex) {
            Logger.getLogger(EnemyTotem.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (active == true){
            if(game.lbl_en_totem.getX()>-40){
                try {
                    sleep(game.enemy_speed);
                    tick();
                    draw();
                    collideTeddy(-5);
                    collide();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shot.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                active = false;
            }
            if (life<=0){
                active=false;
            }
        }
        game.ec.enemy_active=false;
        System.out.println("E morreu: " + active);
        game.lbl_en_totem.setVisible(false);
    }
    
    void nasce() throws InterruptedException{
        int yy=(int) rect.getY();
        while(yy<=350){
            game.lbl_en_totem.setBounds(position, yy+=2, largura, altura);
            sleep(3);
        }
    }
     void collide() throws InterruptedException{
        if (game.ps.rect_shot.intersects(rect)) {
        
            System.out.println("collide");
            game.ps.shot_active=false;
            
            life-=10;
            
            int x;
            if (life>0){
                for (x=0; x<10; x++){
                    position+=2;
                    game.lbl_en_totem.setBounds(position, altitude, largura, altura);
                    sleep(10);
                }
            } else {
                game.pontuacao+=30;
                for (x=position; x<700; x++){
                    position+=2;
                    game.lbl_en_totem.setBounds(position, altitude, largura, altura);
                    sleep(5);
                }
            }
        }
    }
     
     void collideTeddy(int dano){
        if (game.ps.rect_teddy.intersects(rect)) {
            game.setLife(dano);
            try {
                game.ps.sofreDano(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemyTotem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
    
     void pula() throws InterruptedException{
        int yy=(int) rect.getY();
//          int yy=(int) rect.getX();
        while(yy>=150){
            game.lbl_en_totem.setBounds(position-=game.enemy_move, yy-=5, largura, altura);
            rect.setBounds(position-=game.enemy_move, yy-=5, largura, altura);
            sleep(20);
        }
        while(yy<=350){
            game.lbl_en_totem.setBounds(position-=game.enemy_move*2, yy+=3, largura, altura);
            rect.setBounds(position-=game.enemy_move*2, yy+=3, largura, altura);
            collideTeddy(-99);
            sleep(20);
        }
     }
     
    void tick() throws InterruptedException{
        if(count_pulo>1){
            count_pulo--;
        } else {
            pula();
            count_pulo=60;
        }
        sleep(30);
        position=position-(game.enemy_move*3);
        game.lbl_en_totem.setBounds(position, altitude, largura, altura);
        rect.setBounds(position, altitude, largura, altura);
    }
    
    void draw(){
        // Mudança de Sprite
        if(sprite_position<image_sprite.length-1){
            sprite_position++;
        } else{
            sprite_position=0;
        }
        game.lbl_en_totem.setIcon(new ImageIcon(image_sprite[sprite_position]));
    }
    
     void carregaSprite(){
        image_sprite = new Image[4];
        
        ImageIcon img_url = new javax.swing.ImageIcon(getClass().getResource("/res/totem/totem1.png"));
        Image image = img_url.getImage();  
        image_sprite[0] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        image=null;
        img_url=null;
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/totem/totem2.png"));
        image = img_url.getImage();  
        image_sprite[1] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        image=null;
        img_url=null;
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/totem/totem3.png"));
        image = img_url.getImage();  
        image_sprite[2] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        image=null;
        img_url=null;
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/totem/totem4.png"));
        image = img_url.getImage();  
        image_sprite[3] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
    }
}
