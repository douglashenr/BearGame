/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinny
 */
public class GameControl extends Thread{
    Game game;
    boolean game_running=true;
    
    public GameControl(Game game){
        this.game=game;
    }
    
    @Override
    public void run(){
        while (game_running==true) {
            try {
                tick();
                vLife();
                sleep(35);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    void tick(){
        if (game.energy<100){
            game.setEnergy(1);
        }
        game.lbl_pontuacao.setText(String.valueOf(game.pontuacao));
    }
    
    void vLife(){
        if (game.life<=0){
            game_running=false;
            game.bg.active=false;
            game.ps.active=false;
            game.gameOver();
        }
    }
}
