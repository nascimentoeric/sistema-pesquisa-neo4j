import java.sql.Date;

public class ProjetoBean {
    private int idProjeto;
    private int idCoordenador;
    private int idInstituicao;
    private String titulo;
    private String areaPesquisa;
    private Date dataInicio;
    private Date dataFim;

    public ProjetoBean(int idProjeto, int idCoordenador, int idInstituicao, String titulo, String areaPesquisa, Date dataInicio, Date dataFim) {
        this.idProjeto = idProjeto;
        this.idCoordenador = idCoordenador;
        this.idInstituicao = idInstituicao;
        this.titulo = titulo;
        this.areaPesquisa = areaPesquisa;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public int getIdCoordenador() {
        return idCoordenador;
    }

    public void setIdCoordenador(int idCoordenador) {
        this.idCoordenador = idCoordenador;
    }

    public int getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(int idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAreaPesquisa() {
        return areaPesquisa;
    }

    public void setAreaPesquisa(String areaPesquisa) {
        this.areaPesquisa = areaPesquisa;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return "ID: " + idProjeto + " | Projeto: " + titulo + " | Área: " + areaPesquisa;
    }
}
