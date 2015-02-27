/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author xp
 */
public class Disparo {
     Image imagenDisparo = null;
     //Coordenadas del disparo
    private int x = 0;
    private int y = 0;
    Clip sonidoDisparo;
    
    public Disparo()
    {
    try {
            sonidoDisparo = AudioSystem.getClip();
            sonidoDisparo.open(AudioSystem.getAudioInputStream(getClass().getResource("/sonidos/laser.wav")));
            
            
            imagenDisparo = ImageIO.read((getClass().getResource("/Marcianitos/disparo2.png")));
        } catch (Exception ex) {
           
        }
    
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
