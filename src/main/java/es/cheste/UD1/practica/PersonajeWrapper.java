/*
 * Copyright (©) 2024.
 * Hugo Almodóvar Fuster
 */

package es.cheste.UD1.practica;

import java.util.List;
import java.util.Objects;

public class PersonajeWrapper {

    private List<Personaje> personajes;

    public PersonajeWrapper(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    public PersonajeWrapper() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonajeWrapper that = (PersonajeWrapper) o;
        return Objects.equals(personajes, that.personajes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(personajes);
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WrapperPersonaje{");
        sb.append("personajes=").append(personajes);
        sb.append('}');
        return sb.toString();
    }
}
