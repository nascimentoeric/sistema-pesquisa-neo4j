import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import java.util.Scanner;

public class InstituicaoController {

    private final Driver driver;
    private final InstituicaoModel model;

    public InstituicaoController(Driver driver) {
        this.driver = driver;
        this.model = new InstituicaoModel(driver);
    }

    public void criarInstituicao() {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os dados da nova Instituição:");
        System.out.print("ID da Instituição: ");
        int id = input.nextInt();
        input.nextLine();
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("Tipo: ");
        String tipo = input.nextLine();
        System.out.print("País: ");
        String pais = input.nextLine();

        InstituicaoBean instituicao = new InstituicaoBean(id, nome, tipo, pais);

        model.create(instituicao);

        System.out.println("Instituição cadastrada com sucesso!");
    }


    public void listarInstituicoes() {
        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run("MATCH (i:Instituicao) RETURN i.idInstituicao AS idInstituicao, i.nome AS nome, i.tipo AS tipo, i.pais AS pais ORDER BY i.id");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.printf("ID: %d | Nome: %s | Tipo: %s | País: %s\n",
                            record.get("idInstituicao").asInt(),
                            record.get("nome").asString(),
                            record.get("tipo").asString(),
                            record.get("pais").asString()
                    );
                }
                return null;
            });
        }
    }

    public void removerInstituicao() {
        Scanner input = new Scanner(System.in);
        listarInstituicoes();
        System.out.print("Informe o ID da Instituição a ser removida: ");
        int id = input.nextInt();

        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (i:Instituicao {idInstituicao: $idInstituicao}) DETACH DELETE i",
                        org.neo4j.driver.Values.parameters("idInstituicao", id));
                return null;
            });
        }

        System.out.println("Instituição removida com sucesso.");
    }

    public void alterarInstituicao() {
        Scanner input = new Scanner(System.in);
        listarInstituicoes();
        System.out.print("Informe o ID da Instituição a ser alterada: ");
        int id = input.nextInt();
        input.nextLine();
        System.out.print("Novo nome: ");
        String nome = input.nextLine();
        System.out.print("Novo tipo: ");
        String tipo = input.nextLine();
        System.out.print("Novo país: ");
        String pais = input.nextLine();

        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (i:Instituicao {idInstituicao: $idInstituicao}) " +
                                "SET i.nome = $nome, i.tipo = $tipo, i.pais = $pais",
                        org.neo4j.driver.Values.parameters(
                                "idInstituicao", id,
                                "nome", nome,
                                "tipo", tipo,
                                "pais", pais
                        ));
                return null;
            });
        }

        System.out.println("Instituição atualizada com sucesso.");
    }
}
