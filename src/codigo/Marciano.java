
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author xp
 */
public class Marciano {
   public Image imagen1 =null;
   public Image imagen2 =null;
    
    int ancho = 0;
    private int x = 0;
    private int y = 0;

    public Marciano(){
       
        try {
            imagen1 = ImageIO.read((getClass().getResource("/Marcianitos/marcianito1.png")));
            imagen2 = ImageIO.read((getClass().getResource("/Marcianitos/marcianito2.png")));
            ancho = imagen1.getWidth(null);
        } catch (IOException ex) {
            
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