package vos;

/**
 *
 */
public class PublicoAdmitidoVos
{
    private Long idPublico;

    private String categoria;

    public PublicoAdmitidoVos()
    {

    }

    public Long getIdPublico() {
        return idPublico;
    }

    public void setIdPublico(Long idPublico) {
        this.idPublico = idPublico;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void borraEstoCuandoHagasPull ()
    {

    }
}
