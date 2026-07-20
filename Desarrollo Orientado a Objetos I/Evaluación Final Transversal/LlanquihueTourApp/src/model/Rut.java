package model;

import utils.RutInvalidoException;

public class Rut {

    private String valor;

    public Rut(String valor) throws RutInvalidoException {
        setValor(valor);
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) throws RutInvalidoException {

        if (valor == null || !valor.matches("\\d{7,8}-[0-9kK]")) {
            throw new RutInvalidoException(
                    "El RUT debe tener un formato como 12345678-9"
            );
        }

        this.valor = valor.toUpperCase();
    }

    @Override
    public String toString() {
        return valor;
    }
}