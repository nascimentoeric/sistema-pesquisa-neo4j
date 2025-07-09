public class InstituicaoBean {
    private int idInstituicao;
    private String nome;
    private String tipo;
    private String pais;

    public InstituicaoBean(int idInstituicao, String nome, String tipo, String pais) {
        this.idInstituicao = idInstituicao;
        this.nome = nome;
        this.tipo = tipo;
        this.pais = pais;
    }

    public int getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(int idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "ID: " + idInstituicao + " | Instituicao: " + nome + " | Tipo: " + tipo + " | País: " + pais;
    }
}
