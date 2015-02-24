/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author xp
 */
public class VentanaJuego extends javax.swing.JFrame {
    //alto y ancho de la ventana.
    int anchoVentana = 600;
    int altoVentana = 500;
    int velocidadMarciano=2;
    int velocidadDisparo = 3;
    
    //buffer para dibujar
    BufferedImage buffer = null;
    
    //declaro un objeto de tipo nave
    Nave miNave = new Nave(anchoVentana);
    //declaro dos variables booleanas que controlen el movimiento de la nave
    boolean pulsadaDerecha = false, pulsadaIzquierda = false;
    boolean cambiaVelocidadIzquierda = false;
    ArrayList<Disparo> listaDisparos = new ArrayList();
    
    //array de explosiones dinámico
    ArrayList<Explosion> listaExplosiones = new ArrayList();
    
    Disparo disparoAux = new Disparo();
    
    ArrayList<Marciano> listaMarcianos = new ArrayList();
    
    Timer temporizador= new Timer(10, new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e)
    {
        bucleDelJuego();
    }
     
    private void pintaMarcianos(Graphics2D g2){
    for (int i=0;i<listaMarcianos.size();i++)
        {
            Marciano m = listaMarcianos.get(i);
            m.setX(m.getX()+velocidadMarciano);
            if(m.getX() + m.ancho > anchoVentana)
                cambiaVelocidadIzquierda = true;
            else if(m.getX()<0)
                cambiaVelocidadIzquierda = true;
            g2.drawImage(m.imagen1, m.getX(), m.getY(),null);
        }   
        if(cambiaVelocidadIzquierda){
            velocidadMarciano = -velocidadMarciano;
            cambiaVelocidadIzquierda = false;
            for(int i=0;i<listaMarcianos.size();i++)
            {
                Marciano m = listaMarcianos.get(i);
                m.setY(m.getY() +m.ancho/4 );
            }
        }
    
    }
    private void pintaNave(Graphics2D g2)
    {
        if(pulsadaIzquierda)
        {
            miNave.setX(miNave.getX() - 1);
        }else if(pulsadaDerecha)
        {
            miNave.setX(miNave.getX() + 1);
        }
        
        g2.drawImage(miNave.imagenNave, miNave.getX(), miNave.getY()- miNave.imagenNave.getHeight(null), null);
    }
    private void pintaDisparos(Graphics2D g2)
    {
            for(int i=0; i<listaDisparos.size();i++)
        {
            disparoAux= listaDisparos.get(i);
            disparoAux.setY(disparoAux.getY()-velocidadDisparo);
            if(disparoAux.getY() <0)
                listaDisparos.remove(i);
            
            g2.drawImage(disparoAux.imagenDisparo, disparoAux.getX(),disparoAux.getY()-miNave.imagenNave.getHeight(null),null);
        }
    }
    
    private void chequeaColision()
    {
        Rectangle2D.Double rectanguloMarciano = new Rectangle2D.Double();
        
        Rectangle2D.Double rectanguloDisparo = new Rectangle2D.Double();
        
        for(int j = 0; j<listaDisparos.size();j++)
        {
            Disparo d= listaDisparos.get(j);
            rectanguloDisparo.setFrame(d.getX(),d.getY(),d.imagenDisparo.getWidth(null),d.imagenDisparo.getHeight(null));
        for(int i=0; i<listaMarcianos.size();i++)
        {
            Marciano m = listaMarcianos.get(i);
            rectanguloMarciano.setFrame(m.getX(),m.getY(),m.ancho,m.imagen1.getHeight(null));
            if(rectanguloDisparo.intersects(rectanguloMarciano))
            {
                guardaExplosion(m);
                listaMarcianos.remove(i);
                listaDisparos.remove(j); 
            }
        }
        }
    }
    private void guardaExplosion(Marciano m)
    {
        Explosion e = new Explosion();
        e.setX(m.getX());
        e.setY(m.getY());
            listaExplosiones.add(e);
   
    }
    private void pintaExplosion(Graphics2D g2)
    {
        for(int i=0; i<listaExplosiones.size();i++)
        {
            Explosion e = listaExplosiones.get(i);
            if(e.getTiempoDeVida()<=10)
            {
                g2.drawImage(e.imagen2, e.getX(), e.getY(),null);
            }
            else
                g2.drawImage(e.imagen1, e.getX(), e.getY(),null);
             
            e.setTiempoDeVida(e.getTiempoDeVida()-1);
            if(e.getTiempoDeVida() <=0)
                listaExplosiones.remove(i);
        }
    
    
    }
    private void bucleDelJuego()
    {
        //apuntamos al buffer
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        
        //pinto un rectángulo negro del tamaño de la ventana
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,anchoVentana,altoVentana);
        
        //CODIGO DEL JUEGO
        pintaMarcianos(g2);
        pintaDisparos(g2);
        pintaNave(g2);
        chequeaColision();
        pintaExplosion(g2);
       g2 = (Graphics2D) jPanel1.getGraphics();
       g2.drawImage(buffer,0,0,null);
    }           
            });
    /**
     * Creates new form VentanaJuego
     */
    public VentanaJuego() {
        initComponents();
        
        this.setSize(anchoVentana,altoVentana);
        
        buffer = (BufferedImage) jPanel1.createImage(anchoVentana, altoVentana);
        buffer.createGraphics();
        
        temporizador.start();
        miNave.setX(anchoVentana /2);
        miNave.setY(altoVentana -miNave.imagenNave.getHeight(null));
        
        //inicializo el arrayList de los amrcianos
        for(int j=0; j<4;j++)
        {
            for(int i=0; i<10;i++)
            {
                Marciano m = new Marciano();
                m.setX(i * m.ancho + 15);
                m.setY(m.ancho*j);
                
                listaMarcianos.add(m);
            }       
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_A){
            pulsadaIzquierda = true;
            pulsadaDerecha = false;
        } else if(evt.getKeyCode() == KeyEvent.VK_D){
            pulsadaIzquierda = false;
            pulsadaDerecha = true;
        }       
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
       if(evt.getKeyCode() != KeyEvent.VK_W){
        pulsadaDerecha = false;
        pulsadaIzquierda= false;
       }
        if(evt.getKeyCode() == KeyEvent.VK_W && listaDisparos.size()<=5)
         {
             
            Disparo d = new Disparo();
            d.setX( miNave.getX() +  (miNave.getAnchoNave()/2)-3);
            d.setY(miNave.getY()+d.imagenDisparo.getHeight(null)/2);           
            listaDisparos.add(d);
         }
    }//GEN-LAST:event_formKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaJuego().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
