/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.awt.Rectangle;
import java.awt.Image;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinny
 */
public class EnemyBird extends Thread{
    Game game;
    Image[] image_sprite_bird;
    int position;
    int largura = 71;
    int altura = 50;
    int altitude = 200;
    boolean altitude_down=false;
    int sprite_position;
    Rectangle rect;
    boolean active=true;
    int life=20;
    
    public EnemyBird(Game game){
        this.game=game;
        carregaSprite();
        
        rect = new Rectangle();
        
        // Inserindo imagem de inicialização
        game.lbl_en_bird.setIcon(new ImageIcon(image_sprite_bird[0]));
        game.lbl_en_bird.setBounds(900, altitude, largura, altura);
        sprite_position=0;
        active=true;
        
    }
        
    @Override
    public void run(){
        game.lbl_en_bird.setVisible(true);
        game.lbl_en_bird.setBounds(900, altitude, largura, altura);
         System.out.println("Nasceu: " + active );
        position=game.lbl_en_bird.getX();
//        
        while (active == true){
            if(game.lbl_en_bird.getX()>-40){
                try {
                    sleep(game.enemy_speed);
                    tick();
                    draw();
                    collideTeddy();
                    collide();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shot.class.getName()).log(Level.SEVERE, null, ex);
                }
                position-=game.enemy_move;
                game.lbl_en_bird.setBounds(position, altitude, largura, altura);
                rect.setBounds(position, altitude, largura, altura);
            } else {
                active = false;
            }
        }
        game.ec.enemy_active=false;
        System.out.println("E morreu: " + active);
        game.lbl_en_bird.setVisible(false);
    }
    
     void collide() throws InterruptedException{
        if (game.ps.rect_shot.intersects(rect)) {
            game.ps.shot_active=false;
            life-=10;
            System.out.print("Life: "+life);
            
            if (life<=0){
                game.pontuacao+=10;
                int yy=(int) rect.getY();
                while(yy<=500){
                    game.lbl_en_bird.setBounds(position, yy++, largura, altura);
                    sleep(2);
                }
                active=false;
            }
        }
    }
     
     void collideTeddy(){
        if (game.ps.rect_teddy.intersects(rect)) {
            game.setLife(-20);
            try {
                game.ps.sofreDano(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemyBird.class.getName()).log(Level.SEVERE, null, ex);
            }
            active=false;
        }
     }
     
    void tick(){
        if(altitude>=390){
            altitude_down=true;
        } else if(altitude<=250){
            altitude_down=false;
        }
        
        if (altitude_down==true){
            altitude--;
        } else {
            altitude++;
        }
    }
    
    void draw(){
        // Mudança de Sprite
        if(sprite_position<image_sprite_bird.length-1){
            sprite_position++;
        } else{
            sprite_position=0;
        }
        game.lbl_en_bird.setIcon(new ImageIcon(image_sprite_bird[sprite_position]));
    }
    
     void carregaSprite(){
        image_sprite_bird = new Image[4];
        
        ImageIcon img_url = new javax.swing.ImageIcon(getClass().getResource("/res/bird/bird1.png"));
        Image image = img_url.getImage();  
        image_sprite_bird[0] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        image=null;
        img_url=null;
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/bird/bird2.png"));
        image = img_url.getImage();  
        image_sprite_bird[1] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        image=null;
        img_url=null;
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/bird/bird3.png"));
        image = img_url.getImage();  
        image_sprite_bird[2] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        image=null;
        img_url=null;
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/bird/bird4.png"));
        image = img_url.getImage();  
        image_sprite_bird[3] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
    }
}
