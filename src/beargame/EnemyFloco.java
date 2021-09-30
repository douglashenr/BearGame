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
public class EnemyFloco extends Thread{
    Game game;
    Image[] image_sprite;
    int position;
    int largura = 120;
    int altura = 120;
    int altitude = 350;
    int sprite_position;
    Rectangle rect;
    boolean active=true;
    int life=100;
    int count_pulo=60;
    Rectangle rect_shot;
    boolean shot_active=false;
    boolean in_right=false;
    
    public EnemyFloco(Game game){
        this.game=game;
        carregaSprite();
        rect = new Rectangle();
        rect_shot = new Rectangle();
        
        // Inserindo imagem de inicialização
        game.lbl_en_floco.setIcon(new ImageIcon(image_sprite[0]));
        game.lbl_en_floco.setBounds(550, altitude, largura, altura);
        
        sprite_position=0;
        active=true;
        
        game.lbl_en_floco.setVisible(true);
        position=game.lbl_en_floco.getX();
    }
        
    @Override
    public void run(){     
        int pos_paralaxe=game.bg.alignx;
        game.bg.paralaxe_moving=false;
        game.bg.new_progresso=250;
        
        try {
            nasce();
        } catch (InterruptedException ex) {
            Logger.getLogger(EnemyTotem.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (active == true){
            if(game.lbl_en_floco.getX()>-40){
                try {
                    sleep(game.enemy_speed);
                    tick();
                    draw();
                    collideTeddy();
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
        System.out.println("E morreu: " + active);
        game.lbl_en_floco.setVisible(false);
        
        // Voltando paralaxe
        game.bg.alignx=pos_paralaxe;
        game.bg.paralaxe_moving=true;
        game.bg.new_progresso=700;
        game.ps.ps_progresso=160; 
        game.ps.speed_direita=1;
        try {
            // voltando posição inicial do Teddy
            game.ps.returnPos();
            game.ps.returnPos();
//        game.bg.alignx+=46;
        } catch (InterruptedException ex) {
            Logger.getLogger(EnemyNino.class.getName()).log(Level.SEVERE, null, ex);
        }
        game.ec.enemy_active=false;
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
                    game.lbl_en_floco.setBounds(position, altitude, largura, altura);
                    sleep(10);
                }
            } else {
                game.pontuacao+=40;
                for (x=position; x<750; x++){
                    position+=2;
                    game.lbl_en_floco.setBounds(position, altitude, largura, altura);
                    sleep(5);
                }
            }
        }
    }
     
    void collideTeddy(){
        if (game.ps.rect_teddy.intersects(rect)) {
            game.setLife(-5);
            try {
                game.ps.sofreDano(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemyFloco.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
     
    void nasce() throws InterruptedException{
        int yy=(int) rect.getY();
        while(yy<=350){
            game.lbl_en_floco.setBounds(position, yy+=2, largura, altura);
            sleep(2);
        }
    }
    void pula() throws InterruptedException{
        atira();
        
        int yy=(int) rect.getY();
//          int yy=(int) rect.getX();
        while(yy>=150){
            if(in_right==false){
                game.lbl_en_floco.setBounds(position-=1, yy-=5, largura, altura);
                rect.setBounds(position-=1, yy-=5, largura, altura);
            } else {
                game.lbl_en_floco.setBounds(position+=1, yy-=5, largura, altura);
                rect.setBounds(position+=1, yy-=5, largura, altura);
            }
            
            sleep(20);
        }
        while(yy<=350){
            if(in_right==false){
                game.lbl_en_floco.setBounds(position-=2, yy+=3, largura, altura);
                rect.setBounds(position-=2, yy+=3, largura, altura);
            } else {
                game.lbl_en_floco.setBounds(position+=2, yy+=3, largura, altura);
                rect.setBounds(position+=2, yy+=3, largura, altura);
            }
//            collideTeddy();
            sleep(20);
        }
        if (in_right==false){
            in_right=true;
        } else {
            in_right=false;
        }
        atira();
     }
    
    void atira(){
        if(shot_active==false){
            shot_active=true;
            FlocoShot fs = new FlocoShot(game, this, position, rect_shot, game.lbl_floco_shot);
            fs.start();
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
        if(in_right==false){
            position+=2;
        } else {
            position-=3;
        }
        
        game.lbl_en_floco.setBounds(position, altitude, largura, altura);
        rect.setBounds(position, altitude, largura, altura);
    }
    
    void draw(){
        // Mudança de Sprite
        if(sprite_position<image_sprite.length-1){
            sprite_position++;
        } else{
            sprite_position=0;
        }
        game.lbl_en_floco.setIcon(new ImageIcon(image_sprite[sprite_position]));
    }
    
     void carregaSprite(){
        image_sprite = new Image[5];
        
        ImageIcon img_url = new javax.swing.ImageIcon(getClass().getResource("/res/floco/floco1.png"));
        Image image = img_url.getImage();  
        image_sprite[0] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/floco/floco2.png"));
        image = img_url.getImage();  
        image_sprite[1] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/floco/floco3.png"));
        image = img_url.getImage();  
        image_sprite[2] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/floco/floco4.png"));
        image = img_url.getImage();  
        image_sprite[3] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/floco/floco5.png"));
        image = img_url.getImage();  
        image_sprite[4] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        // carregando tiro
        Image image_shot;
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/floco/bala.png"));
        image = img_url.getImage();  
        image_shot = image.getScaledInstance(37, 15, java.awt.Image.SCALE_SMOOTH);  
        game.lbl_floco_shot.setIcon(new ImageIcon(image_shot));
        game.lbl_floco_shot.setBounds(game.lbl_person.getX()+20, 410, 50, 50);
        game.lbl_floco_shot.setVisible(false);
    }
}
