public class OrientandoBean {
    private int idOrientando;
    private String nome;
    private String email;
    private String nivelAcademico;
    private int idProjeto;

    public OrientandoBean(int idOrientando, String nome, String email, String nivelAcademico, int idProjeto) {
        this.idOrientando = idOrientando;
        this.nome = nome;
        this.email = email;
        this.nivelAcademico = nivelAcademico;
        this.idProjeto = idProjeto;
    }

    public int getIdOrientando() {
        return idOrientando;
    }

    public void setIdOrientando(int idOrientando) {
        this.idOrientando = idOrientando;
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

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    @Override
    public String toString() {
        return "Orientando: " + nome + " | Nível: " + nivelAcademico;
    }
}
