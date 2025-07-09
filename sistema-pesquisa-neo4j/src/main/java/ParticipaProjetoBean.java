public class ParticipaProjetoBean {
    private int idPesquisador;
    private int idProjeto;
    private String papel;

    public ParticipaProjetoBean(int idPesquisador, int idProjeto, String papel) {
        this.idPesquisador = idPesquisador;
        this.idProjeto = idProjeto;
        this.papel = papel;
    }

    public int getIdPesquisador() {
        return idPesquisador;
    }

    public void setIdPesquisador(int idPesquisador) {
        this.idPesquisador = idPesquisador;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    @Override
    public String toString() {
        return "Pesquisador ID: " + idPesquisador + " participa do projeto ID: " + idProjeto + " como " + papel;
    }
}
