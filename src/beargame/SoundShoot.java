/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

/**
 *
 * @author Douglas
 */
public class SoundShoot extends Thread {
    
    public void SoundShoot(){
        
    }
    
    public void run(){
        Sound sond = new Sound();
        sond.play("Fire");
    }
    
}
