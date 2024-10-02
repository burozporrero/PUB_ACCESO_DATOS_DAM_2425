package es.cheste.UD1.practica;

import java.util.Objects;

public class Coche {
    private String matricula;
    private String marca;
    private double precio;

    // Constructor sin parámetros (requerido para la deserialización)
    public Coche() {
        super();
    }

    public Coche(String matricula, String marca, double precio) {
        this.matricula = matricula;
        this.marca = marca;
        this.precio = precio;
    }

// Getters y Setters

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coche{");
        sb.append("matricula='").append(getMatricula()).append('\'');
        sb.append(", marca='").append(getMarca()).append('\'');
        sb.append(", precio=").append(getPrecio());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coche coche = (Coche) o;
        return Double.compare(precio, coche.precio) == 0 && Objects.equals(matricula, coche.matricula) && Objects.equals(marca, coche.marca);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(matricula);
        result = 31 * result + Objects.hashCode(marca);
        result = 31 * result + Double.hashCode(precio);
        return result;
    }
}
