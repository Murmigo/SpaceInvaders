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
public class Explosion {
   public Image imagen1 =null;
   public Image imagen2 =null;
   
   Clip sonidoExplosion;
   
    private int x = 0;
    private int y = 0;
    private int tiempoDeVida =20;

    public int getTiempoDeVida() {
        return tiempoDeVida;
    }

    public void setTiempoDeVida(int tiempoDeVida) {
        this.tiempoDeVida = tiempoDeVida;
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

    public Explosion(){
       
        try {
            sonidoExplosion = AudioSystem.getClip();
            sonidoExplosion.open(AudioSystem.getAudioInputStream(getClass().getResource("/sonidos/explosion.wav")));
            
            imagen1 = ImageIO.read((getClass().getResource("/Marcianitos/e1.png")));
            imagen2 = ImageIO.read((getClass().getResource("/Marcianitos/e2.png")));
        } catch (Exception ex) {
            
        }
       
    }
}
