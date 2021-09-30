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
public class EnemyAviao extends Thread{
    Game game;
    Image[] image_sprite;
    int position;
    int largura = 130;
    int altura = 80;
    int altitude = 335;
    int sprite_position;
    Rectangle rect;
    boolean active=true;
    int life=30;
    int rep_altitude=0;
    boolean troca_altitude=false;
    
    public EnemyAviao(Game game){
        this.game=game;
        carregaSprite();
        rect = new Rectangle();
        
        // Inserindo imagem de inicialização
        game.lbl_en_aviao.setIcon(new ImageIcon(image_sprite[0]));
        game.lbl_en_aviao.setBounds(900, altitude, largura, altura);
        
        sprite_position=0;
        active=true;
        
        game.lbl_en_aviao.setVisible(true);
        game.lbl_en_aviao.setBounds(900, altitude, largura, altura);
        position=game.lbl_en_aviao.getX();
    }
        
    @Override
    public void run(){     
        int yy=(int) rect.getY();
        
        while (active == true){
            if(game.lbl_en_aviao.getX()>-40){
                try {
                    sleep(game.enemy_speed);
                    tick();
                    draw();
                    collideTeddy(-40);
                    collide();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shot.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                active = false;
            }
            if (life<=0){
                active=false;
//                game.setLife(20);
            }
        }
        game.ec.enemy_active=false;
        System.out.println("E morreu: " + active);
        game.lbl_en_aviao.setVisible(false);
    }
    
     void collide() throws InterruptedException{
        if (game.ps.rect_shot.intersects(rect)) {
            game.ps.shot_active=false;
            life-=10;
            
            if(life<=0){
                game.pontuacao+=10;
                int yy=(int) rect.getY();
                int xx=(int) rect.getX();
                while(yy<=500){
                    game.lbl_en_aviao.setBounds(xx--, yy++, largura, altura);
                    rect.setBounds(xx--, yy++, largura, altura);
                    collideTeddy(-5);
                    sleep(7);
                }   
            }
        }
    }
     
     void collideTeddy(int dano){
        if (game.ps.rect_teddy.intersects(rect)) {
            System.out.println("collide Teddy");
            try {
                game.ps.sofreDano(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemyAviao.class.getName()).log(Level.SEVERE, null, ex);
            }
            game.setLife(dano);
            active=false;
        }
     }
     
    void tick() throws InterruptedException{
        sleep(30);
        position=position-(game.enemy_move+10);
        game.lbl_en_aviao.setBounds(position, altitude, largura, altura);
        rect.setBounds(position, altitude, largura, altura);
    }
    
    void draw(){
        // Mudança de Sprite
        if(sprite_position<image_sprite.length-1){
            sprite_position++;
        } else{
            sprite_position=0;
        }
        game.lbl_en_aviao.setIcon(new ImageIcon(image_sprite[sprite_position]));
    }
    
     void carregaSprite(){
       image_sprite = new Image[12];

       ImageIcon img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao1.png"));
       Image image = img_url.getImage();  
       image_sprite[0] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
       image=null;
       img_url=null;

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao2.png"));
       image = img_url.getImage();  
       image_sprite[1] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
       image=null;
       img_url=null;

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao3.png"));
       image = img_url.getImage();  
       image_sprite[2] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
       image=null;
       img_url=null;

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao4.png"));
       image = img_url.getImage();  
       image_sprite[3] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao5.png"));
       image = img_url.getImage();  
       image_sprite[4] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao6.png"));
       image = img_url.getImage();  
       image_sprite[5] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao7.png"));
       image = img_url.getImage();  
       image_sprite[6] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao8.png"));
       image = img_url.getImage();  
       image_sprite[7] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao9.png"));
       image = img_url.getImage();  
       image_sprite[8] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao10.png"));
       image = img_url.getImage();  
       image_sprite[9] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao11.png"));
       image = img_url.getImage();  
       image_sprite[10] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);

       img_url = new javax.swing.ImageIcon(getClass().getResource("/res/aviao/aviao12.png"));
       image = img_url.getImage();  
       image_sprite[11] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
   }
}
