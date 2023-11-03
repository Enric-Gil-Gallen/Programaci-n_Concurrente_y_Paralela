package e5;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class MiHebraCalculadoraVariosDisparoEje4 extends Thread {
    CanvasCampoTiro4 canvas;
    JTextField mensajeJT;
    NuevoDisparo4 disparo;
    Point objetivo;
    LinkedBlockingQueue<NuevoDisparo4> lD;
    ArrayList<Proyectil4> lP;
    boolean impactado;

    public MiHebraCalculadoraVariosDisparoEje4(CanvasCampoTiro4 canvas, JTextField mensajeJT, Point objetivo, LinkedBlockingQueue<NuevoDisparo4> lD) {
        this.canvas = canvas;
        this.mensajeJT = mensajeJT;
        this.objetivo = objetivo;
        this.lD = lD;
        this.lP = new ArrayList<>();
    }

    public void run() {
        while (true) {
            while (lP.size() == 0 || !lD.isEmpty()) {
                try {
                    disparo = lD.take();
                    Proyectil4 proyectil4 = new Proyectil4(disparo.velocidadInicial, disparo.anguloInicial, canvas);
                    lP.add(proyectil4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < lP.size(); i++) {
                Proyectil4 p = lP.get(i);

                // Muestra en pantalla los datos del proyectil p.
                p.imprimeEstadoProyectilEnConsola();

                // Mueve el proyectil p.
                p.mueveUnIncremental();

                // Dibuja el proyectil p..
                p.actualizaDibujoProyectil();

                impactado = determinaEstadoProyectil(p);

                duermeUnPoco(1L);

                if (impactado) {
                    lP.remove(p);
                    i--;
                }

            }
        }

    }
    void duermeUnPoco( long millis ) {
        try {
            Thread.sleep( millis );
        } catch( InterruptedException ex ) {
            ex.printStackTrace();
        }
    }

    boolean determinaEstadoProyectil( Proyectil4 p ) {
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
