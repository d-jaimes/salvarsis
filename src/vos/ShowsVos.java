package vos;

/**
 *
 */
public class ShowsVos
{
    private Long idShows;

    private String nombre;

    private String descripcion;

    private Long duracion;

    private String idioma;

    private Long publicoAdmitido;

    private double costo;

    private boolean participacionPublico;

    private Long idFest;

    public ShowsVos(){}

    public Long getIdShows() {
        return idShows;
    }

    public void setIdShows(Long idShows) {
        this.idShows = idShows;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getDuracion() {
        return duracion;
    }

    public void setDuracion(Long duracion) {
        this.duracion = duracion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getPublicoAdmitido() {
        return publicoAdmitido;
    }

    public void setPublicoAdmitido(Long publicoAdmitido) {
        this.publicoAdmitido = publicoAdmitido;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isParticipacionPublico() {
        return participacionPublico;
    }

    public void setParticipacionPublico(boolean participacionPublico) {
        this.participacionPublico = participacionPublico;
    }

    public Long getIdFest() {
        return idFest;
    }

    public void setIdFest(Long idFest) {
        this.idFest = idFest;
    }
}
