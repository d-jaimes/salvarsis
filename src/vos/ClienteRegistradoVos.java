package vos;

/**
 *
 */
public class ClienteRegistradoVos extends UsuarioVos
{
    private Integer edad;

    public ClienteRegistradoVos ()
    {
        super();
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}