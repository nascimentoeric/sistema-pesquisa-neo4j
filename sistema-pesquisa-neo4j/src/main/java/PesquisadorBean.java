public class PesquisadorBean {
    private int idPesquisador;
    private String nome;
    private String email;
    private String instituicao;

    public PesquisadorBean(int idPesquisador, String nome, String email, String instituicao) {
        this.idPesquisador = idPesquisador;
        this.nome = nome;
        this.email = email;
        this.instituicao = instituicao;
    }

    public int getIdPesquisador() {
        return idPesquisador;
    }

    public void setIdPesquisador(int idPesquisador) {
        this.idPesquisador = idPesquisador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    @Override
    public String toString() {
        return "ID: "+ idPesquisador + " | Pesquisador: " + nome + " | Email: " + email + " | Instituição: " + instituicao;
    }
}
