public class ColeccionMinas {
    //atributos de instancia
    private Celda [] colMinas;
    private int cant;
    //constructor
    public ColeccionMinas(int n){
        cant = 0;
        colMinas = new Celda[n];
    }
    //comandos 
    public void insertarMina(Celda c){
        colMinas[cant] = c;
        cant++;
    }
    //consulta
    public boolean existeMina(Celda c){
        boolean existe = false;
        for(int i = 0; i < cant && !existe; i++){
            existe = colMinas[i].equals(c);
        }
        return existe;
    }
}