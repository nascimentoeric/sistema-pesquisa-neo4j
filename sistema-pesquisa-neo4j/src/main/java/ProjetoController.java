import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;

import java.sql.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletionStage;

public class ProjetoController {

    private final ProjetoModel model;
    private final PesquisadorController pesquisadorController;
    private final InstituicaoController instituicaoController;
    private Driver driver;

    public ProjetoController(Driver driver) {
        this.driver = driver;
        this.model = new ProjetoModel(driver);
        this.pesquisadorController = new PesquisadorController(driver);
        this.instituicaoController = new InstituicaoController(driver);
    }

    public void criarProjeto() {
        Scanner input = new Scanner(System.in);

        System.out.println("Insira os dados do projeto:\n");

        pesquisadorController.listarPesquisadores();
        System.out.print("ID do Coordenador: ");
        int idCoordenador = input.nextInt();

        instituicaoController.listarInstituicoes();
        System.out.print("ID da Instituição: ");
        int idInstituicao = input.nextInt();
        input.nextLine();

        System.out.print("Título: ");
        String titulo = input.nextLine();

        System.out.print("Área de Pesquisa: ");
        String areaPesquisa = input.nextLine();

        System.out.print("Data de Início (yyyy-mm-dd): ");
        Date dataInicio = Date.valueOf(input.nextLine());

        System.out.print("Data de Fim (yyyy-mm-dd): ");
        Date dataFim = Date.valueOf(input.nextLine());

        int idProjeto = model.getNextId();

        ProjetoBean projeto = new ProjetoBean(idProjeto, idCoordenador, idInstituicao, titulo, areaPesquisa, dataInicio, dataFim);
        model.create(projeto);

        System.out.println("Projeto cadastrado com sucesso!");
    }

    public void listarProjetos() {
        try (Session session = driver.session()) {
            String cypher = "MATCH (p:Projeto)-[:COORDENADO_POR]->(pesq:Pesquisador) " +
                    "RETURN p.idProjeto AS idProjeto, p.titulo AS titulo, " +
                    "pesq.idPesquisador AS idCoordenador, pesq.nome AS nomeCoordenador";

            session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                System.out.println("Projetos com seus respectivos coordenadores:\n");
                while (result.hasNext()) {
                    Record record = result.next();
                    int idProjeto = record.get("idProjeto").asInt();
                    String titulo = record.get("titulo").asString();
                    int idCoordenador = record.get("idCoordenador").asInt();
                    String nomeCoordenador = record.get("nomeCoordenador").asString();

                    System.out.println("Projeto ID: " + idProjeto + " | Título: " + titulo +
                            " | Coordenador ID: " + idCoordenador + " | Nome: " + nomeCoordenador);
                }
                return null;
            });
        } catch (Exception e) {
            System.out.println("Erro ao listar projetos com coordenadores: " + e.getMessage());
        }
    }

    public void removerProjeto() {
        Scanner input = new Scanner(System.in);
        listarProjetos();

        System.out.print("Informe o ID do Projeto a ser removido: ");
        int idProjeto = input.nextInt();

        model.remove(idProjeto);
        System.out.println("Projeto removido com sucesso.");
    }

    public void listarProjetosComPesquisadoresParticipantes() {
        try (Session session = model.getDriver().session()) {
            session.readTransaction(tx -> {
                String cypher = """
                MATCH (pesq:Pesquisador)-[:PARTICIPA]->(proj:Projeto)
                RETURN proj.idProjeto AS idProjeto, proj.titulo AS titulo,
                       pesq.idPesquisador AS idPesquisador, pesq.nome AS nomePesquisador
                ORDER BY proj.idProjeto, pesq.nome
            """;

                Result result = tx.run(cypher);

                System.out.println("\n--- Projetos com Pesquisadores Participantes ---\n");

                int lastProjetoId = -1;
                while (result.hasNext()) {
                    Record rec = result.next();
                    int idProjeto = rec.get("idProjeto").asInt();

                    if (idProjeto != lastProjetoId) {
                        System.out.printf("\nProjeto ID: %d | Título: %s\n", idProjeto, rec.get("titulo").asString());
                        System.out.println("Pesquisadores Participantes:");
                        lastProjetoId = idProjeto;
                    }

                    System.out.printf("   - [%d] %s\n",
                            rec.get("idPesquisador").asInt(),
                            rec.get("nomePesquisador").asString());
                }

                return null;
            });
        } catch (Exception e) {
            System.out.println("Erro ao listar projetos com pesquisadores: " + e.getMessage());
        }
    }

}
