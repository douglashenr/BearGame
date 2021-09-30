/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinny
 */
public class EnemyesControl extends Thread{
    Game game;
    public boolean enemy_running=true;
    public boolean enemy_active=false;
    Random rand;
    
    public EnemyesControl(Game game){
        this.game=game;
        rand = new Random();
    }
    
    public void getRand(){
        /* Os tiros têm velocidades randômicas */
        rand = new Random();
    }
    @Override
    public void run(){
        while (enemy_running=true) {
            System.out.println("AAAAC"+enemy_active);
            try {
                if (enemy_active==false){
                    sleep(1500);
                    if (enemy_running==true){
                        if (game.bg.alignx>=-2000){
                        setEnemy(rand.nextInt(3));
                      } else {
                          setEnemy(rand.nextInt(5));
                      }
                    }
                } 
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemyesControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    void setEnemy(int code){
        enemy_active=true;
        if(code == 0){
            EnemyAviao ed = new EnemyAviao(game);
            ed.start();
        }
        if(code == 1){
            EnemyBird enb = new EnemyBird(game);
            enb.start();
        }
        if(code == 2){
            EnemyTotem et = new EnemyTotem(game);
            et.start();
        }
        if(code == 3){
            EnemyFloco ef = new EnemyFloco(game);
            ef.start();
        }
        if(code == 4){
            EnemyNino en = new EnemyNino(game);
            en.start();
        }
    }
    
}
