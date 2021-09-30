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
public class EnemyNino extends Thread{
    Game game;
    Image[] image_andar;
    Image[] image_meditar;
    
    int position=900;
    int largura = 97;
    int altura = 75;
    int altitude = 390;
    int sprite_position;
    Rectangle rect;
    boolean active=true;
    int life=100;
    int count_pulo=80;
    
    public EnemyNino(Game game){
        this.game=game;
        carregaSprite();
        rect = new Rectangle();
        
        // Inserindo imagem de inicialização
        game.lbl_nino.setIcon(new ImageIcon(image_andar[0]));
        game.lbl_nino.setBounds(position, altitude, largura, altura);
        
        sprite_position=0;
        active=true;
        game.lbl_nino.setVisible(true);
    }
        
    @Override
    public void run(){     
        int pos_paralaxe=game.bg.alignx;
        game.bg.paralaxe_moving=false;
        
        while (active == true){
            if(game.lbl_nino.getX()>-40){
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
        game.lbl_nino.setVisible(false);
        
        // Voltando paralaxe
        game.bg.alignx=pos_paralaxe;
        game.bg.paralaxe_moving=true;
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
                    game.lbl_nino.setBounds(position, altitude, largura, altura);
                    sleep(10);
                }
            } else {
                game.pontuacao+=50;
                for (x=position; x<700; x++){
                    position+=2;
                    game.lbl_nino.setBounds(position, altitude, largura, altura);
                    sleep(5);
                }
            }
        }
    }
     
     void collideTeddy(){
        if (game.ps.rect_teddy.intersects(rect)) {
            game.setLife(-10);
            try {
                game.ps.sofreDano(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemyNino.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
    
     void medita() throws InterruptedException{
        int yy=(int) rect.getY();
//          int yy=(int) rect.getX();
        while(yy>=250){
            game.lbl_nino.setBounds(position, yy-=2, largura, altura);
            rect.setBounds(position, yy-=2, largura, altura);
            sleep(20);
        }

        NinoMeteoro nm1 = new NinoMeteoro(game, this, game.lbl_meteoro_azul, 1);
        nm1.start();
        int meditar_position=0;
        int r=0;
        while(r<50){
            if(meditar_position<image_meditar.length-1){
                meditar_position++;
            } else{
                meditar_position=0;
            }
            if(r==25){
               NinoMeteoro nm2 = new NinoMeteoro(game, this, game.lbl_meteoro_roxo, 2);
               nm2.start();
            } else if (r==48){
                NinoMeteoro nm3 = new NinoMeteoro(game, this, game.lbl_meteoro_vermelho, 3);
                nm3.start();
            }
            game.lbl_nino.setIcon(new ImageIcon(image_meditar[meditar_position]));
            sleep(60);
            r++;
        }
        
        while(yy<=390){
            game.lbl_nino.setBounds(position, yy+=2, largura, altura);
            rect.setBounds(position, yy+=2, largura, altura);
//            collideTeddy();
            sleep(20);
        }
     }
     
    void tick() throws InterruptedException{
        if(count_pulo>1){
            count_pulo--;
        } else {
            medita();
            count_pulo=80;
        }
        sleep(20);
        position-=3;
        game.lbl_nino.setBounds(position, altitude, largura, altura);
        rect.setBounds(position+30, altitude, largura, altura);
    }
    
    void draw(){
        // Mudança de Sprite
        if(sprite_position<image_andar.length-1){
            sprite_position++;
        } else{
            sprite_position=0;
        }
        game.lbl_nino.setIcon(new ImageIcon(image_andar[sprite_position]));
    }
    
     void carregaSprite(){
        image_andar = new Image[5];
        
        ImageIcon img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/andar/andar1.png"));
        Image image = img_url.getImage();  
        image_andar[0] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/andar/andar2.png"));
        image = img_url.getImage();  
        image_andar[1] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/andar/andar3.png"));
        image = img_url.getImage();  
        image_andar[2] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/andar/andar4.png"));
        image = img_url.getImage();  
        image_andar[3] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/andar/andar5.png"));
        image = img_url.getImage();  
        image_andar[4] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        // Inserindo imagens de meditação do nino
        image_meditar = new Image[4];
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/meditar/meditar1.png"));
        image = img_url.getImage();  
        image_meditar[0] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/meditar/meditar2.png"));
        image = img_url.getImage();  
        image_meditar[1] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/meditar/meditar3.png"));
        image = img_url.getImage();  
        image_meditar[2] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/meditar/meditar4.png"));
        image = img_url.getImage();  
        image_meditar[3] = image.getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH);
        
        // Inserindo imagens do meteoro
        int larg_meteoro=60;
        int alt_meteoro=60;
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/meteoro/azul.png"));
        image = img_url.getImage();  
        game.lbl_meteoro_azul.setIcon(new ImageIcon(image.getScaledInstance(larg_meteoro, alt_meteoro, java.awt.Image.SCALE_SMOOTH)));
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/meteoro/roxo.png"));
        image = img_url.getImage();  
        game.lbl_meteoro_roxo.setIcon(new ImageIcon(image.getScaledInstance(larg_meteoro, alt_meteoro, java.awt.Image.SCALE_SMOOTH)));
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/nino/meteoro/vermelho.png"));
        image = img_url.getImage();  
        game.lbl_meteoro_vermelho.setIcon(new ImageIcon(image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH)));
    }
}
