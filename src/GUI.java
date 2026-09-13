import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import java.util.Random;

public class GUI extends JFrame {
    private JFrame ventana;
    private JPanel panelSuperior;
    private JPanel cuerpo;
    private JLabel mensaje;
    
    private Celda [][] celdas;  //Matriz que almacena celdas
    private JButton [][] botones;
    
    private int tamanoCelda = 50; //En pixeles
    private int numFilas = 15;
    private int numColumnas = numFilas;
    private int ancho = numColumnas * tamanoCelda;
    private int alto = numFilas * tamanoCelda;
    private int cantMinas = 36;
    private int cantPistas = 0;
    private int celdasVisitadas = 0;
    private int banderas = 0;
    
    private boolean primeraJugada;
    
    private ColeccionMinas celdasConMinas;
    
    public GUI(){
        ventana = new JFrame("BuscaMinas");
        ventana.setSize(ancho,alto);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());
        
        mensaje = new JLabel();
        mensaje.setFont(new Font("Calibri",Font.PLAIN,20));
        mensaje.setHorizontalAlignment(JLabel.CENTER);
        mensaje.setText("Busca Minas");
        
        panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.add(mensaje);
        ventana.add(panelSuperior,BorderLayout.NORTH);
        
        cuerpo = new JPanel();
        cuerpo.setLayout(new GridLayout(numFilas,numColumnas)); //15x15
        ventana.add(cuerpo);
        
        celdas = new Celda[numFilas][numColumnas];  //Creo una matriz de celdas 15x15
        botones = new JButton[numFilas][numColumnas];   //Creo una matriz de botones 15x15
        primeraJugada = true;
        for(int f = 0; f < numFilas; f++){
            for(int c = 0; c < numColumnas; c++){
                celdas[f][c] = new Celda(f,c);   //Se inicializa en falso
                botones[f][c] = new JButton();
                botones[f][c].setEnabled(true);
                botones[f][c].setName(f+","+c);
                botones[f][c].setFont(new Font("Arial Unicode MS",Font.PLAIN,14));
                botones[f][c].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        JButton botonPresionado = (JButton) e.getSource();  //Obtengo el boton que fue presioando
                        String nombreBoton = botonPresionado.getName();     //Obtengo el nombre que tiene forma f,c
                        String[] coordenadas = nombreBoton.split(",");      //Divido sus coordenadas
                        int fBoton = Integer.parseInt(coordenadas[0]);      //Posicion x del boton
                        int cBoton = Integer.parseInt(coordenadas[1]);      //Posicion y del boton
                        if (SwingUtilities.isRightMouseButton(e)) {
                            if(botonPresionado.getText().equals("🚩") && botonPresionado.isEnabled()){
                                botonPresionado.setText("");
                            } else if(botonPresionado.isEnabled()){
                                botonPresionado.setText("🚩");
                                if(banderas == cantMinas){
                                    chequearGanador();
                                }
                                banderas = banderas + 1;
                            }
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            if(primeraJugada){
                                generarMinas();
                                primeraJugada = false;
                            }
                            if(botonPresionado.getText().equals("")){
                                if(celdas[fBoton][cBoton].esMina()){
                                revelarMinas();
                                JOptionPane.showMessageDialog(null, "Has perdido.", "Información", JOptionPane.INFORMATION_MESSAGE);
                                reiniciar();
                            } else {
                                //No es mina
                                botonPresionado.setEnabled(false);
                                cantPistas = cantidadPistas(celdas[fBoton][cBoton]);
                                if(cantPistas != 0){
                                    botonPresionado.setText(String.valueOf(cantPistas));
                                    celdas[fBoton][cBoton].marcarVisitada();
                                } else {
                                    //Cantidad de pistas igual a 0
                                    liberarEspacio(celdas[fBoton][cBoton]);
                                }
                            }
                            }
                        }
                    }
                });
                cuerpo.add(botones[f][c]);
            }
        }
        //generarMinas();
        ventana.setVisible(true);
    }
    
    private void generarMinas(){
        Random n = new Random();
        int f;
        int c;
        int minasRestantes = cantMinas;
        celdasConMinas = new ColeccionMinas(cantMinas);
        while(minasRestantes > 0){
            f = n.nextInt(numFilas);
            c = n.nextInt(numColumnas);
            if(!celdasConMinas.existeMina(celdas[f][c])){
                celdas[f][c].setearMina();
                celdasConMinas.insertarMina(celdas[f][c]);
                minasRestantes--;
            }
        }
    }
    
    private void revelarMinas(){
        for(int f = 0; f < numFilas; f++){
            for(int c = 0; c < numColumnas; c++){
                if(celdas[f][c].esMina()){
                    botones[f][c].setText("💣");
                }
            }
        }
    }
    
    private int cantidadPistas(Celda c){
        int aDevolver = 0;
        int f = c.obtenerPosFila();
        int col = c.obtenerPosColumna();
        //Las 3 de arriba de la celda
        if(f-1 >= 0 && col-1 >= 0){ 
            //Arriba a la izquierda
            if(celdas[f-1][col-1].esMina()){
                aDevolver++;
            }
        }
        if(f-1 >=0){
            //Arriba
            if(celdas[f-1][col].esMina()){
                aDevolver++;
            }
        }
        if(f-1 >= 0 && col+1 < numColumnas){
            if(celdas[f-1][col+1].esMina()){
                aDevolver++;
            }
        }
        //La de los costados
        if(col-1 >= 0){
            if(celdas[f][col-1].esMina()){
                //Izquierda
                aDevolver++;
            }
        }
        if(col+1 < numColumnas){
            if(celdas[f][col+1].esMina()){
                //Derecha
                aDevolver++;
            }
        }
        //Las 3 de abajo
        if(f+1 < numFilas && col-1 >=0){
            //Abajo a la izquierda
            if(celdas[f+1][col-1].esMina()){
                aDevolver++;
            }
        }
        if(f+1 < numFilas){
            //Abajo
            if(celdas[f+1][col].esMina()){
                aDevolver++;
            }
        }
        if(f+1 < numFilas && col+1 < numColumnas){
            //Abajo a la derecha
            if(celdas[f+1][col+1].esMina()){
                aDevolver++;
            }
        }
        return aDevolver;
    }
    
    public void liberarEspacio(Celda c){
        int f = c.obtenerPosFila();
        int col = c.obtenerPosColumna();
        if(f-1 >= 0 && col-1 >= 0){ 
            //Arriba a la izquierda
            if(cantidadPistas(celdas[f-1][col-1]) == 0 && !celdas[f-1][col-1].fueVisitada()){
                celdas[f-1][col-1].marcarVisitada();
                botones[f-1][col-1].setEnabled(false);
                liberarEspacio(celdas[f-1][col-1]);
            } else if(cantidadPistas(celdas[f-1][col-1]) != 0){
                botones[f-1][col-1].setEnabled(false);    
                celdas[f-1][col-1].marcarVisitada();
                botones[f-1][col-1].setText(String.valueOf(cantidadPistas(celdas[f-1][col-1])));
            }
        }
        if(f-1 >= 0){ 
            //Arriba
            if(cantidadPistas(celdas[f-1][col]) == 0 &&!celdas[f-1][col].fueVisitada()){
                celdas[f-1][col].marcarVisitada();
                botones[f-1][col].setEnabled(false);
                liberarEspacio(celdas[f-1][col]);
            } else if(cantidadPistas(celdas[f-1][col]) != 0){
                botones[f-1][col].setEnabled(false);    
                celdas[f-1][col].marcarVisitada();
                botones[f-1][col].setText(String.valueOf(cantidadPistas(celdas[f-1][col])));
            }
        }
        if(f-1 >= 0 && col+1 < numColumnas){ 
            //Arriba a la derecha
            if(cantidadPistas(celdas[f-1][col+1]) == 0 && !celdas[f-1][col+1].fueVisitada()){
                celdas[f-1][col+1].marcarVisitada();
                botones[f-1][col+1].setEnabled(false);
                liberarEspacio(celdas[f-1][col+1]);
            } else if(cantidadPistas(celdas[f-1][col+1]) != 0){
                botones[f-1][col+1].setEnabled(false);    
                celdas[f-1][col+1].marcarVisitada();
                botones[f-1][col+1].setText(String.valueOf(cantidadPistas(celdas[f-1][col+1])));
            }
        }
        if(col-1 >= 0){ 
            //Izquierda
            if(cantidadPistas(celdas[f][col-1]) == 0 && !celdas[f][col-1].fueVisitada()){
                celdas[f][col-1].marcarVisitada();
                botones[f][col-1].setEnabled(false);
                liberarEspacio(celdas[f][col-1]);
            } else if(cantidadPistas(celdas[f][col-1]) != 0){
                botones[f][col-1].setEnabled(false);    
                celdas[f][col-1].marcarVisitada();
                botones[f][col-1].setText(String.valueOf(cantidadPistas(celdas[f][col-1])));
            }
        }
        if(col+1 < numColumnas){ 
            //Derecha
            if(cantidadPistas(celdas[f][col+1]) == 0 && !celdas[f][col+1].fueVisitada()){
                celdas[f][col+1].marcarVisitada();
                botones[f][col+1].setEnabled(false);
                liberarEspacio(celdas[f][col+1]);
            } else if(cantidadPistas(celdas[f][col+1]) != 0){
                botones[f][col+1].setEnabled(false);    
                celdas[f][col+1].marcarVisitada();
                botones[f][col+1].setText(String.valueOf(cantidadPistas(celdas[f][col+1])));
            }
        }
        if(f+1 < numFilas && col-1 >= 0){ 
            //Abajo a la izquierda
            if(cantidadPistas(celdas[f+1][col-1]) == 0 && !celdas[f+1][col-1].fueVisitada()){
                celdas[f+1][col-1].marcarVisitada();
                botones[f+1][col-1].setEnabled(false);
                liberarEspacio(celdas[f+1][col-1]);
            } else if(cantidadPistas(celdas[f+1][col-1]) != 0){
                botones[f+1][col-1].setEnabled(false);    
                celdas[f+1][col-1].marcarVisitada();
                botones[f+1][col-1].setText(String.valueOf(cantidadPistas(celdas[f+1][col-1])));
            }
        }
        if(f+1 < numFilas){ 
            //Abajo
            if(cantidadPistas(celdas[f+1][col]) == 0 && !celdas[f+1][col].fueVisitada()){
                celdas[f+1][col].marcarVisitada();
                botones[f+1][col].setEnabled(false);
                liberarEspacio(celdas[f+1][col]);
            } else if(cantidadPistas(celdas[f+1][col]) != 0){
                botones[f+1][col].setEnabled(false);    
                celdas[f+1][col].marcarVisitada();
                botones[f+1][col].setText(String.valueOf(cantidadPistas(celdas[f+1][col])));
            }
        }
        if(f+1 < numFilas && col+1 < numColumnas){ 
            //Abajo a la derecha
            if(cantidadPistas(celdas[f+1][col+1]) == 0 && !celdas[f+1][col+1].fueVisitada()){
                celdas[f+1][col+1].marcarVisitada();
                botones[f+1][col+1].setEnabled(false);
                liberarEspacio(celdas[f+1][col+1]);
            } else if(cantidadPistas(celdas[f+1][col+1]) != 0){
                botones[f+1][col+1].setEnabled(false);    
                celdas[f+1][col+1].marcarVisitada();
                botones[f+1][col+1].setText(String.valueOf(cantidadPistas(celdas[f+1][col+1])));
            }
        }
    }
    
    private void reiniciar(){
        for(int f = 0; f < numFilas; f++){
            for(int c = 0; c < numColumnas; c++){
                botones[f][c].setText("");
                botones[f][c].setEnabled(true);
                celdas[f][c].desactivarMina();
                cantPistas = 0;
                celdasVisitadas = 0;
                banderas = 0;
                primeraJugada = true;
            }
        }
        //generarMinas();
    }
    
    private void chequearGanador(){
        for(int f = 0; f < numFilas; f++){
            for(int c = 0; c < numColumnas; c++){
                if(celdas[f][c].fueVisitada()){
                    celdasVisitadas++;
                }
            }
        }
        if(celdasVisitadas == numFilas * numColumnas - cantMinas){
            JOptionPane.showMessageDialog(null, "Has ganado.", "Información", JOptionPane.INFORMATION_MESSAGE);
            reiniciar();
        }
    }
}