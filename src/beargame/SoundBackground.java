/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

/**
 *
 * @author Vinny
 */
public class SoundBackground{
    Sound sond;
    public void SondBackground(){
        sond = new Sound();
        sond.play("BackgroundMusic");
    }
    
}
