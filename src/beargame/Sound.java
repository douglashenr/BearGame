/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beargame;

import java.applet.Applet;
import java.applet.AudioClip;
import static java.awt.SystemColor.control;
import java.net.URL;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Vinny
 */
public class Sound {
    
    public void play (String name){
        URL url = getClass().getResource(name+".wav");
        AudioClip audio = Applet.newAudioClip(url);
        audio.play();
    }
    
}
