/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.omg.PortableInterceptor.ACTIVE;

public class Personagem extends Thread implements KeyListener{
    public int align_x;
    public int speed = 3;
    public int speed_direita=1;
    public int count_distancia;
    boolean Direita = false, Esquerda = false, Atira = false, anima_stop=false, defesa=false, morte=false;
    public boolean shot_active=false;
    Image image_init, image_right, image_poser, image_stop, image_machuca;
    Image[] image_sprite_shot;
    Image[] image_sprite_defesa;
    Image[] image_sprite_escudo;
    Image[] image_sprite_morte;
    Image image_shot;
    Game game;
    BGParalaxe pr;
    boolean stop_bg = false;
    public int ps_progresso=160;
    Rectangle rect_shot;
    Rectangle rect_teddy;
    boolean active=true;
    boolean escuso_active=false;
    Rectangle rect_escudo;
    boolean in_dano=false;
    
    public Personagem(Game game){
        image_sprite_shot = new Image[3];
        image_sprite_defesa = new Image[12];
        image_sprite_escudo = new Image[3];
        image_sprite_morte = new Image[5];
        
        this.game = game;
        count_distancia=0;
        rect_shot = new Rectangle();
        rect_teddy = new Rectangle();
        rect_escudo=new Rectangle();
        
        // Carregando imagem de inicialização
        ImageIcon img_url = new javax.swing.ImageIcon(getClass().getResource("/res/teddy/movies/sprite_stop2.png"));
        Image image = img_url.getImage();  
        image_init = image.getScaledInstance(80, 124, java.awt.Image.SCALE_SMOOTH);
        
        // Inserindo imagem de inicialização
        game.lbl_person.setIcon(new ImageIcon(image_init));
        game.lbl_person.setBounds(100, 335, 80, 124);
        rect_teddy.setBounds(100, 365, 40, 90);
        align_x= (int)game.lbl_person.getBounds().x;
        game.lbl_person.setVisible(true);
        
        // Carregando Imagem de movimento para a direita
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/teddy/movies/movimento_direita.png"));
        image = img_url.getImage();  
        image_right = image.getScaledInstance(131, 110, java.awt.Image.SCALE_SMOOTH);  

        // Carregando Imagem de movimento para a esquerda
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/teddy/movies/poser.png"));
        image = img_url.getImage();  
        image_poser = image.getScaledInstance(68, 110, java.awt.Image.SCALE_SMOOTH);  
        
        // Carregando Imagem de brecar
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/teddy/movies/image_stop.png"));
        image = img_url.getImage();  
        image_stop = image.getScaledInstance(71, 110, java.awt.Image.SCALE_SMOOTH);  
        
        // carregando imagens de atirar
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/teddy/shot/atirar1.png"));
        image = img_url.getImage();  
        image_sprite_shot[0] = image.getScaledInstance(84, 110, java.awt.Image.SCALE_SMOOTH);  
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/teddy/shot/atirar2.png"));
        image = img_url.getImage();  
        image_sprite_shot[1] = image.getScaledInstance(135, 110, java.awt.Image.SCALE_SMOOTH);  
        
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/teddy/shot/atirar3.png"));
        image = img_url.getImage();  
        image_sprite_shot[2] = image.getScaledInstance(88, 110, java.awt.Image.SCALE_SMOOTH);  
        
        String mod;
        int x;
        
        // Carregando imagens de sprite de defesa
        for (x=0;x<12;x++){
            mod="/res/teddy/defesa/defesa"+String.valueOf(x+1)+".png";
            img_url = new javax.swing.ImageIcon(getClass().getResource(mod));
            image = img_url.getImage();  
            image_sprite_defesa[x] = image.getScaledInstance(84, 110, java.awt.Image.SCALE_SMOOTH);  
        }
        
        // Carregando imagens de escudo
        for (x=0;x<3;x++){
            mod="/res/teddy/defesa/escudo"+String.valueOf(x+1)+".png";
            img_url = new javax.swing.ImageIcon(getClass().getResource(mod));
            image = img_url.getImage();  
            image_sprite_escudo[x] = image.getScaledInstance(21*(x+1),40*(x+1), java.awt.Image.SCALE_SMOOTH);  //63x120
        }
        
        // carregando imagens de morte
        for (x=0;x<5;x++){
            mod="/res/teddy/morte/morte"+String.valueOf(x+1)+".png";
            img_url = new javax.swing.ImageIcon(getClass().getResource(mod));
            image = img_url.getImage();  
            image_sprite_morte[x] = image.getScaledInstance(75,110, java.awt.Image.SCALE_SMOOTH);
        }
        
        // Carregando Imagem de dano sofrido
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/teddy/movies/machuca.png"));
        image = img_url.getImage();  
        image_machuca = image.getScaledInstance(89, 120, java.awt.Image.SCALE_SMOOTH);  
        
        // carregando tiro
        img_url = new javax.swing.ImageIcon(getClass().getResource("/res/teddy/shot/disparo.png"));
        image = img_url.getImage();  
        image_shot = image.getScaledInstance(37, 15, java.awt.Image.SCALE_SMOOTH);  
        game.lbl_shot.setIcon(new ImageIcon(image_shot));
        game.lbl_shot.setBounds(game.lbl_person.getX()+20, 410, 50, 50);
        game.lbl_shot.setVisible(false);
 
    }
    
    void animaStop() throws InterruptedException{
        game.lbl_person.setBounds(align_x, 350, 71, 110); 
        rect_teddy.setBounds(align_x+15, 365, 40, 90);
        int r=0;
        while (r<15) {  
            sleep(15);
            game.bg.alignx-=speed;
            r++;
        }
        sleep(70);
        game.lbl_person.setIcon(new ImageIcon(image_poser)); 
    }
    
    void moverDireita(){
        if (game.bg.paralaxe_moving==true){
            game.bg.alignx-=speed;
        }
        
        count_distancia++;
        game.lbl_person.setBounds(align_x, 350, 131, 110); 
        rect_teddy.setBounds(align_x+75, 365, 40, 90);
        game.lbl_person.setIcon(new ImageIcon(image_right));
        
        if(game.lbl_person.getX() <=ps_progresso && (in_dano==false)){
            // Inserindo imagem de movimento
            game.lbl_person.setBounds(align_x+=speed_direita, 350, 131, 110); 
            rect_teddy.setBounds(align_x+75, 365, 40, 90);
        }
    }
	
    void moverEsquerda(){
        game.lbl_person.setBounds(align_x, 350, 68, 110);
        rect_teddy.setBounds(align_x+15, 365, 40, 90);
        game.lbl_person.setIcon(new ImageIcon(image_poser));
        
        if(align_x >=30){
            game.lbl_person.setBounds(align_x-=speed, 350, 68, 110);
            rect_teddy.setBounds(align_x+15, 365, 40, 90);
        }
    }
	
    void atirar() throws InterruptedException{
        game.lbl_person.setBounds(align_x, 350, 110, 110);
        rect_teddy.setBounds(align_x, 365, 40, 90);
        shot_active=true;
        game.enemy_move=1;
        
        int atr=0;
        while(atr<image_sprite_shot.length){
            game.lbl_person.setIcon(new ImageIcon(image_sprite_shot[atr])); 
            atr++;
            sleep(150);
        }
        game.lbl_person.setIcon(new ImageIcon(image_poser));
    }
    
    public void returnPos() throws InterruptedException{
        while (align_x>ps_progresso){
            game.lbl_person.setBounds(align_x-=speed_direita, 350, 131, 110); 
            rect_teddy.setBounds(align_x-75, 365, 40, 90);
            sleep(2);
        }
    }
    
    public void sofreDano(int pos) throws InterruptedException{
        if (active==true){
            in_dano=true;
            int x;
    //        game.lbl_person.setIcon(new ImageIcon(image_machuca));
    //        rect_teddy.setBounds(align_x+15, 365, 89, 120);

            if (align_x >30){
                if (pos==1){
                    for (x=0; x<10; x++){
                        game.lbl_person.setBounds(align_x-=speed_direita, 350, 131, 110); 
                        game.ps.rect_teddy.setBounds(align_x-75, 365, 40, 90);
                        sleep(10);
                    }
                } else {
                    for (x=0; x<10; x++){
                        game.lbl_person.setBounds(align_x+=speed_direita, 350, 131, 110); 
                        rect_teddy.setBounds(align_x+75, 365, 40, 90);
                        sleep(10);
                    }
                }
            } else {
                sleep(100);
            }

            game.lbl_person.setIcon(new ImageIcon(image_poser));
            game.ps.rect_teddy.setBounds(align_x+15, 365, 40, 90);
            in_dano=false;
        }
    }
    
    public void morre() throws InterruptedException{
        sleep(300);
        game.lbl_person.setBounds(align_x, 350, 75, 110);
        int atr=0;
        while(atr<image_sprite_morte.length){
            game.lbl_person.setIcon(new ImageIcon(image_sprite_morte[atr]));
            sleep(110);
            atr++;
        }
//        game.lbl_person.setIcon(new ImageIcon(image_poser));
        active=false;
    }
    
    void defesa() throws InterruptedException{
        game.lbl_person.setBounds(align_x, 350, 84, 110);
        rect_teddy.setBounds(align_x, 365, 40, 90);
        game.enemy_move=1;

        int atr=0;
        while(atr<image_sprite_defesa.length){
            game.lbl_person.setIcon(new ImageIcon(image_sprite_defesa[atr])); 
            atr++;
            sleep(50);
        }
        game.lbl_person.setIcon(new ImageIcon(image_poser));
          
    }
        
    @Override
    public void run(){
        
        game.lbl_person.setVisible(true);
	while (active==true) {
            try {
                tick();
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Personagem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            morre();
        } catch (InterruptedException ex) {
            Logger.getLogger(Personagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tick() throws InterruptedException{
        /* Verificar posicionamento através do chaveamento definido no KeyListener*/
        if (Direita==true){
            moverDireita();
        }else if (Esquerda==true){
            moverEsquerda();
        }
        if (Atira==true){
            if(shot_active==false && escuso_active==false){
                Shot sh = new Shot(game, game.lbl_person.getX()+20, rect_shot);
                sh.start();
                atirar();
            }
        } 
        if(anima_stop==true){
            anima_stop=false;
            animaStop();
        }
        if (defesa==true){
            if (escuso_active==false && game.energy>=40){
                escuso_active=true;
                TeddyEscudo te = new TeddyEscudo(game, this, game.lbl_person.getX()+20);
                te.start();
                defesa();
            }
        }
        if (morte ==true){
            morte=false;
            morre();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        /* Com o chaveamento é possível efetuar o uso de teclas simultâneas */
        if(keyCode == KeyEvent.VK_D){
            Direita=true; 
            game.enemy_move=2;
//            System.out.println("Direita");
        } 
        if(keyCode == KeyEvent.VK_A){
            Esquerda=true;
        }
        if(keyCode == KeyEvent.VK_SPACE){
            if (game.energy>=30){
                Atira=true;
            }
        }
        if(keyCode == KeyEvent.VK_S){
            if (game.energy>=20){
                defesa=true;
            }
        }
        
//        if(keyCode == KeyEvent.VK_M){
//            EnemyTotem et = new EnemyTotem(game);
//            et.start();
//        }
//        if(keyCode == KeyEvent.VK_N){
//            EnemyBird enb = new EnemyBird(game);
//            enb.start();
//        }
//        if(keyCode == KeyEvent.VK_B){
//            EnemyAviao ed = new EnemyAviao(game);
//            ed.start();
//        }
//        if(keyCode == KeyEvent.VK_V){
//            EnemyFloco ef = new EnemyFloco(game);
//            ef.start();
//        }
//        if(keyCode == KeyEvent.VK_C){
//            EnemyNino en = new EnemyNino(game);
//            en.start();
//        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        /* Quando uma das teclas é solta sua variável de chaveamento fica inativa */
        if(keyCode == KeyEvent.VK_D){
            Direita=false;
            game.enemy_move=1;
            if(count_distancia>50){
                anima_stop=true;
                game.lbl_person.setIcon(new ImageIcon(image_stop));
            } else{
                game.lbl_person.setIcon(new ImageIcon(image_poser));
            }
            rect_teddy.setBounds(align_x+15, 365, 40, 90);
            count_distancia=0;
        }
        if(keyCode == KeyEvent.VK_A){
            game.lbl_person.setIcon(new ImageIcon(image_poser));
            Esquerda=false;
            rect_teddy.setBounds(align_x+15, 365, 40, 90);
        }
        if(keyCode == KeyEvent.VK_SPACE){
            Atira=false;
            rect_teddy.setBounds(align_x+15, 365, 40, 90);
        }
        if(keyCode == KeyEvent.VK_S){
            defesa=false;
            escuso_active=false;
            rect_teddy.setBounds(align_x+15, 365, 40, 90);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
}
