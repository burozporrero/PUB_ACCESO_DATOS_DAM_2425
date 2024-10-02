package es.cheste.UD1.practica;

import java.io.Serializable;
import java.util.Objects;

public class Modulo implements Serializable {
    String nom;
    int hores;
    double nota;

    public Modulo() {
        super();
    }

    public Modulo(String nom, int hores, double nota) {
        this.nom = nom;
        this.hores = hores;
        this.nota = nota;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getHores() {
        return hores;
    }

    public void setHores(int hores) {
        this.hores = hores;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Modulo modulo = (Modulo) o;
        return hores == modulo.hores && Double.compare(nota, modulo.nota) == 0 && Objects.equals(nom, modulo.nom);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(nom);
        result = 31 * result + hores;
        result = 31 * result + Double.hashCode(nota);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getNom());
        sb.append(";").append(getHores());
        sb.append(";").append(getNota());
        return sb.toString();
    }
}
