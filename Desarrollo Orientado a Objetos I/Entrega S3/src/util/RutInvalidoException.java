package util;

/**Excepción personalizada para manejar errores relacionados con el formato del RUT.*/

public class RutInvalidoException extends Exception {

    /**Constructor que recibe el mensaje de error.Mensaje que explica el error ocurrido*/

    public RutInvalidoException(String mensaje) {
        super(mensaje);
    }
}
