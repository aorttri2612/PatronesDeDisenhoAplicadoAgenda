package agendaConPatrones;
import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Contacto> contactos;
    private int siguienteId;
    
    // 1. Instancia única estática (Eager Initialization)
    private static final Agenda instancia = new Agenda();

    // 2. Constructor PRIVADO
    private Agenda() {
        this.contactos = new ArrayList<>();
        this.siguienteId = 1;
    }

    // 3. Método para obtener la instancia
    public static Agenda getInstance() {
        return instancia;
    }

    // --- El resto de los métodos se mantienen igual ---

    public int getSiguienteId() {
        return this.siguienteId++;
    }

    public Contacto crearContacto(String nombre, String apellidos, String email) {
        int id = getSiguienteId();
        Contacto nuevoContacto = new Contacto(id, nombre, apellidos, email);
        this.contactos.add(nuevoContacto);
        return nuevoContacto;
    }

    public List<Contacto> listarContactos() {
        return new ArrayList<>(this.contactos);
    }

    public Contacto obtenerPorId(int id) {
        for (Contacto c : this.contactos) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public boolean eliminarContactoPorId(int id) {
        return contactos.removeIf(c -> c.getId() == id);
    }

    public List<Contacto> buscarPorNombre(String texto) {
        String t = texto.toLowerCase();
        List<Contacto> resultados = new ArrayList<>();
        for (Contacto c : this.contactos) {
            String nombreCompleto = (c.getNombre() + " " + c.getApellidos()).toLowerCase();
            if (nombreCompleto.contains(t)) {
                resultados.add(c);
            }
        }
        return resultados;
    }
}