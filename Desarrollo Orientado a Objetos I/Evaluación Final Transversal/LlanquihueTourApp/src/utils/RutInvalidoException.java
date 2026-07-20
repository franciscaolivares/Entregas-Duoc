package utils;

public class RutInvalidoException extends Exception {

    //Exceeption para rut en formato incorrecto

    public RutInvalidoException(String mensaje){
        super(mensaje);
    }
}

