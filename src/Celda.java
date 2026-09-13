public class Celda {
    //atributos de instancia
    private int posFila;
    private int posColumna;
    private boolean esMina;
    private boolean fueVisitada;
    //constructor
    public Celda(int f, int c){
        posFila = f;
        posColumna = c;
        esMina = false;
        fueVisitada = false;
    }
    //comandos
    public void setearMina(){
        esMina = true;
    }
    public void marcarVisitada(){
        fueVisitada = true;
    }
    public void desactivarMina(){
        esMina = false;
    }
    //consultas
    public boolean esMina(){
        return esMina;
    }
    public boolean equals(Celda c){
        return posFila == c.obtenerPosFila() && posColumna == c.obtenerPosColumna();
    }
    public int obtenerPosFila(){
        return posFila;
    }
    public int obtenerPosColumna(){
        return posColumna;
    }
    public boolean fueVisitada(){
        return fueVisitada;
    }
}