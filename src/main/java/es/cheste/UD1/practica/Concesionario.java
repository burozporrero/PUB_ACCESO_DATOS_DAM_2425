package es.cheste.UD1.practica;

import java.util.List;

public class Concesionario {
    private List<Coche> coches;

    // Constructor sin parámetros
    public Concesionario() {
        super();
    }

    // Constructor con parámetros
    public Concesionario(List<Coche> coches) {
        this.coches = coches;
    }

    // Getters y Setters
    public List<Coche> getCoches() {
        return coches;
    }

    public void setCoches(List<Coche> coches) {
        this.coches = coches;
    }

    // Método toString
    @Override
    public String toString() {
        return "Concesionario{" +
                "coches=" + coches +
                '}';
    }
}
