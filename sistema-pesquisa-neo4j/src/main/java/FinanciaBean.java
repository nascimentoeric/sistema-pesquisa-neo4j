public class FinanciaBean {
    private int idInstituicao;
    private String nomeInstituicao;
    private int idProjeto;
    private String nomeProjeto;

    
    public FinanciaBean(int idInstituicao, int idProjeto) {
        this.idInstituicao = idInstituicao;
        this.idProjeto = idProjeto;
        this.nomeInstituicao = "";
        this.nomeProjeto = "";
    }

    
    public FinanciaBean(int idInstituicao, String nomeInstituicao,
                        int idProjeto, String nomeProjeto) {
        this.idInstituicao = idInstituicao;
        this.nomeInstituicao = nomeInstituicao;
        this.idProjeto = idProjeto;
        this.nomeProjeto = nomeProjeto;
    }

    
    public int getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(int idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    @Override
    public String toString() {
        
        if (!nomeInstituicao.isEmpty() && !nomeProjeto.isEmpty()) {
            return "Instituição: " + nomeInstituicao + " (ID: " + idInstituicao + ") financia o projeto: " +
                   nomeProjeto + " (ID: " + idProjeto + ")";
        } else {
            
            return "Instituição ID: " + idInstituicao + " financia o projeto ID: " + idProjeto;
        }
    }
}
