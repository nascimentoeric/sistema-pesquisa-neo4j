import java.sql.Date;

public class PublicacaoBean {
    private int idPublicacao;
    private int idProjeto;
    private String titulo;
    private String tipo;
    private Date dataPublicacao;
    private String doi;

    public PublicacaoBean(int idPublicacao, int idProjeto, String titulo, String tipo, Date dataPublicacao, String doi) {
        this.idPublicacao = idPublicacao;
        this.idProjeto = idProjeto;
        this.titulo = titulo;
        this.tipo = tipo;
        this.dataPublicacao = dataPublicacao;
        this.doi = doi;
    }

    public int getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(int idPublicacao) {
        this.idPublicacao = idPublicacao;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    @Override
    public String toString() {
        return "ID: " + idPublicacao + " | Publicação: " + titulo + " | Tipo: " + tipo + " | DOI: " + doi;
    }
}
