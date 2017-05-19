package vos;

/**
 *
 */
public class UsuarioVos
{
    private Long idusu;

    private String tipoId;

    private String nombre;

    private String email;

    private String password;

    private String rol;

    private Long idfest;

    public UsuarioVos (){}

    public Long getIdusu() {
        return idusu;
    }

    public void setIdusu(Long idusu) {
        this.idusu = idusu;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdfest() {
        return idfest;
    }

    public void setIdfest(Long idfest) {
        this.idfest = idfest;
    }
}
