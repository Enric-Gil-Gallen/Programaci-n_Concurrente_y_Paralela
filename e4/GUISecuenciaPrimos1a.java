package e4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class ZonaIntercambio1a {
  // ===========================================================================
  volatile long tiempo = 250;

  // ------------------------ -------------------------------------------------
  void setTiempo( long tiempo ) {
    this.tiempo = tiempo;
  }

  // -------------------------------------------------------------------------
  long getTiempo() {
    return tiempo;
  }

}

class HebraTrabajadora2_2 extends Thread {
  JTextField txfMensajes;
  volatile boolean fin = false;
  public HebraTrabajadora2_2(JTextField txfMensajes){
    this.txfMensajes = txfMensajes;
  }
  public void acabar(boolean fin){
    this.fin = fin;
  }

  @Override
  public void run() {
    long i = 1L;
    while ( !fin ) {
      if ( GUISecuenciaPrimos1a.esPrimo ( i ) ) {
        final long acaba = i;
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            txfMensajes.setText(String.valueOf(acaba));
          }
        });
      }
      i ++;
    }

  }
}

class HebraTrabajadora2_3 extends Thread {
  JTextField txfMensaje;
  volatile boolean fin = false;
  ZonaIntercambio1a zonaIntercambio1a;
  public HebraTrabajadora2_3(JTextField txfMensaje, ZonaIntercambio1a zona){
    this.txfMensaje = txfMensaje;
    this.zonaIntercambio1a = zona;

  }
  public void acabar(boolean fin){
    this.fin = fin;
  }

  @Override
  public void run() {
    long i = 1L;
    while ( !fin ) {

      if ( GUISecuenciaPrimos1a.esPrimo ( i ) ) {
        final long acaba = i;
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            txfMensaje.setText(String.valueOf(acaba));
          }
        });
        try {
          sleep(zonaIntercambio1a.getTiempo());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      i ++;
    }

  }
}

// ===========================================================================
public class GUISecuenciaPrimos1a {
// ===========================================================================
  JFrame      container;
  JPanel      jpanel;
  JTextField  txfMensajes;
  JButton     btnComienzaSecuencia, btnCancelaSecuencia;
  JSlider     sldEspera;
  HebraTrabajadora2_2 t;// Ejercicio 2.2
  HebraTrabajadora2_3 t2; // Ejercicio 2.3
  ZonaIntercambio1a   z; // Ejercicio 2.3

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    GUISecuenciaPrimos1a gui = new GUISecuenciaPrimos1a();
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        gui.go();
      }
    });
  }

  // -------------------------------------------------------------------------
  public void go() {
    // Constantes.
    final int valorMaximo = 1000;
    final int valorMedio  = 500;

    // Variables.
    JPanel  tempPanel;

    // Crea el JFrame principal.
    container = new JFrame( "GUI Secuencia de Primos 1a" );

    // Consigue el panel principal del Frame "container".
    jpanel = ( JPanel ) container.getContentPane();
    jpanel.setLayout( new GridLayout( 3, 1 ) );

    // Crea e inserta la etiqueta y el campo de texto para los mensajes.
    txfMensajes = new JTextField( 20 );
    txfMensajes.setEditable( false );
    tempPanel = new JPanel();
    tempPanel.setLayout( new FlowLayout() );
    tempPanel.add( new JLabel( "Secuencia: " ) );
    tempPanel.add( txfMensajes );
    jpanel.add( tempPanel );

    // Crea e inserta los botones de Comienza secuencia y Cancela secuencia.
    btnComienzaSecuencia = new JButton( "Comienza secuencia" );
    btnCancelaSecuencia = new JButton( "Cancela secuencia" );
    tempPanel = new JPanel();
    tempPanel.setLayout( new FlowLayout() );
    tempPanel.add( btnComienzaSecuencia );
    tempPanel.add( btnCancelaSecuencia );
    jpanel.add( tempPanel );

    // Crea e inserta el slider para controlar el tiempo de espera.
    sldEspera = new JSlider( JSlider.HORIZONTAL, 0, valorMaximo , valorMedio );
    tempPanel = new JPanel();
    tempPanel.setLayout( new BorderLayout() );
    tempPanel.add( new JLabel( "Tiempo de espera: " ) );
    tempPanel.add( sldEspera );
    jpanel.add( tempPanel );

    // Activa inicialmente los 2 botones.
    btnComienzaSecuencia.setEnabled( true );
    btnCancelaSecuencia.setEnabled( false );
    z = new ZonaIntercambio1a();

    // Anyade codigo para procesar el evento del boton de Comienza secuencia.
    btnComienzaSecuencia.addActionListener( new ActionListener() {
        public void actionPerformed( ActionEvent e ) {
          btnComienzaSecuencia.setEnabled(false);
          btnCancelaSecuencia.setEnabled(true);
          // t = new HebraTrabajadora2_2(txfMensajes);
          // t.start();
          t2 = new HebraTrabajadora2_3(txfMensajes, z);
          t2.start();
        }
    } );

    // Anyade codigo para procesar el evento del boton de Cancela secuencia.
    btnCancelaSecuencia.addActionListener( new ActionListener() {
        public void actionPerformed( ActionEvent e ) {
          btnComienzaSecuencia.setEnabled(true);
          btnCancelaSecuencia.setEnabled(false);
          //t.acabar(true);
          t2.acabar(true);
        }
    } );

    // Anyade codigo para procesar el evento del slider " Espera " .
    sldEspera.addChangeListener( new ChangeListener() {
      public void stateChanged( ChangeEvent e ) {
        JSlider sl = ( JSlider ) e.getSource();
        if ( ! sl.getValueIsAdjusting() ) {
          long tiempoMilisegundos = ( long ) sl.getValue();
          System.out.println( "JSlider value = " + tiempoMilisegundos );
          z.setTiempo(tiempoMilisegundos);

        }
      }
    } );

    // Fija caracteristicas del container.
    container.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    container.pack();
    container.setResizable( false );
    container.setVisible( true );

    System.out.println( "% End of routine: go.\n" );
  }

  // -------------------------------------------------------------------------
  static boolean esPrimo( long num ) {
    boolean primo;
    if( num < 2 ) {
      primo = false;
    } else {
      primo = true;
      long i = 2;
      while( ( i < num )&&( primo ) ) {
        primo = ( num % i != 0 );
        i++;
      }
    }
    return( primo );
  }
}

