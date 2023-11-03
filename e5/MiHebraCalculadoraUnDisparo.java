package e5;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class MiHebraCalculadoraUnDisparo extends  Thread{
    CanvasCampoTiro4 canvas;
    JTextField mensajeJT;
    NuevoDisparo4 disparo;
    Point objetivo;

    public MiHebraCalculadoraUnDisparo(CanvasCampoTiro4 canvas, JTextField mensaje, NuevoDisparo4 disparo, Point objetivo) {
        this.canvas = canvas;
        this.mensajeJT = mensaje;
        this.disparo = disparo;
        this.objetivo = objetivo;
    }

    @Override
    public void run() {
        Proyectil1a p;
        boolean      impactado;

        p = new Proyectil1a( disparo.velocidadInicial, disparo.anguloInicial, canvas );
        impactado = false;
        while( ! impactado ) {
            // Muestra en pantalla los datos del proyectil p.
            p.imprimeEstadoProyectilEnConsola();

            // Mueve el proyectil p.
            p.mueveUnIncremental();

            // Dibuja el proyectil p..
            p.actualizaDibujoProyectil();

            // Comprueba si el proyectil p ha impactado o continua en vuelo.
            impactado = determinaEstadoProyectil( p );

            duermeUnPoco( 1L );
        }
    }

    void duermeUnPoco( long millis ) {
        try {
            Thread.sleep( millis );
        } catch( InterruptedException ex ) {
            ex.printStackTrace();
        }
    }

    boolean determinaEstadoProyectil( Proyectil1a p ) {
        // Devuelve cierto si el proyectil ha impactado contra el suelo o contra
        // el objetivo.
        boolean  impactado;
        String   mensaje;

        if ( ( p.intPosX == objetivo.x )&&( p.intPosY == objetivo.y ) ) {
            // El proyectil ha acertado el objetivo.
            impactado = true;

            mensaje = " Destruido!!!";
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    muestraMensajeEnCampoInformacion( mensaje );
                }
            });

        } else if( ( p.intPosY <= 0 )&&( p.velY < 0.0 ) ) {
            // El proyectil ha impactado contra el suelo, pero no ha acertado.
            impactado = true;

            mensaje = "Has fallado. Esta en " + objetivo.x + ". " +
                    "Has disparado a " + p.intPosX + ".";
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    muestraMensajeEnCampoInformacion( mensaje );
                }
            });

        } else {
            // El proyectil continua en vuelo.
            impactado = false;
        }
        return impactado;
    }

    void muestraMensajeEnCampoInformacion( String mensaje ) {
        // Muestra mensaje en el cuadro de texto de informacion.

        String miMensaje = mensaje;
        mensajeJT.setText( miMensaje );
    }

}
