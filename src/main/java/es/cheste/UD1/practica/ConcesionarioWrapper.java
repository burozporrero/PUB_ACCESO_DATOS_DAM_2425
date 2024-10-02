package es.cheste.UD1.practica;

public class ConcesionarioWrapper {

    private Concesionario concesionario;

    // Constructor sin parámetros (requerido para la deserialización)
    public ConcesionarioWrapper() {
    }

    // Constructor con parámetros
    public ConcesionarioWrapper(Concesionario concesionario) {
        this.concesionario = concesionario;
    }

    // Getter para 'concesionario'
    public Concesionario getConcesionario() {
        return concesionario;
    }

    // Setter para 'concesionario'
    public void setConcesionario(Concesionario concesionario) {
        this.concesionario = concesionario;
    }

    // Método toString para una representación legible del objeto
    @Override
    public String toString() {
        return "ConcesionarioWrapper{" +
                "concesionario=" + concesionario +
                '}';
    }
}
