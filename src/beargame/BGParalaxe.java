/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Vinny
 */
public class BGParalaxe extends Thread{
    Game game;
    JLabel lblbg;
    ImageIcon img_bg;
    public int alignx;
    boolean paralaxe_moving=true;
    int new_progresso=700;
    int new_teddy_speed=4;
    boolean active=true;
    
    public BGParalaxe(Game game){
        this.game=game;
    }

    
    @Override
    public void run(){
//        if(game.bg.alignx>=-4767){
//        System.out.println("Align: "+ alignx);
	while (active==true) {
            try {
                if(paralaxe_moving==true && (game.bg.alignx>=-4767)){
                    game.lbl_bg.setBounds(alignx, -15, 5668, 510);
                } else{
                    game.ps.ps_progresso=new_progresso; //160
                    game.ps.speed_direita=new_teddy_speed; //1
                    if (game.ps.align_x>=600 && paralaxe_moving==true){
                        game.ec.enemy_running=false;
                        vencer();
                    }
        //                game.ps.speed=5;
                }
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(BGParalaxe.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    }
    
    void vencer() throws InterruptedException{
        active=false;
        game.ec.enemy_running=false;
         // Carregando imagem de inicialização
        ImageIcon img_url = new javax.swing.ImageIcon(getClass().getResource("/res/vencedor.png"));
        Image image = img_url.getImage();  
        Image image_vencer = image.getScaledInstance(900, 510, java.awt.Image.SCALE_SMOOTH);
        
        sleep(500);
        // Inserindo imagem de inicialização
        game.lbl_bg.setIcon(new ImageIcon(image_vencer));
        game.lbl_bg.setBounds(0, 0, 900, 510);
        JOptionPane.showConfirmDialog(game, "Próxima Fase ?");
        MainMenu mm = new MainMenu();
        mm.setLocationRelativeTo(null);
        game.dispose();
        mm.setVisible(true);
    }
}
