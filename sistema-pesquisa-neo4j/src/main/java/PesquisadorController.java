import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.Scanner;
import java.util.Set;

public class PesquisadorController {

    private final PesquisadorModel model;

    public PesquisadorController(Driver driver) {
        this.model = new PesquisadorModel(driver);
    }

    public void criarPesquisador() {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os dados do pesquisador:");
        System.out.print("ID do Pesquisador: ");
        int idPesquisador = input.nextInt();
        input.nextLine();
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Instituição: ");
        String instituicao = input.nextLine();

        PesquisadorBean p = new PesquisadorBean(idPesquisador, nome, email, instituicao);
        model.create(p);
        System.out.println("Pesquisador cadastrado com sucesso!");
    }

    public void listarPesquisadores() {
        Set<PesquisadorBean> todos = model.listAll();
        for (PesquisadorBean p : todos) {
            System.out.println(p);
        }
    }

    public void removerPesquisador() {
        Scanner input = new Scanner(System.in);
        listarPesquisadores();
        System.out.print("Informe o ID do pesquisador a ser removido: ");
        int idPesquisador = input.nextInt();

        model.remove(idPesquisador);
        System.out.println("Pesquisador removido com sucesso.");
    }

    public void alterarPesquisador() {
        Scanner input = new Scanner(System.in);
        listarPesquisadores();
        System.out.print("Informe o ID do pesquisador a ser alterado: ");
        int idPesquisador = input.nextInt();
        input.nextLine();
        System.out.print("Novo nome: ");
        String nome = input.nextLine();
        System.out.print("Novo email: ");
        String email = input.nextLine();
        System.out.print("Nova instituição: ");
        String instituicao = input.nextLine();

        PesquisadorBean p = new PesquisadorBean(idPesquisador, nome, email, instituicao);
        model.update(p);
        System.out.println("Pesquisador atualizado com sucesso.");
    }

    public void listarProjetosComCoordenadores() {
        try (Session session = model.getDriver().session()) {
            session.readTransaction(tx -> {
                String cypher = "MATCH (p:Projeto)-[:COORDENADO_POR]->(pesq:Pesquisador) " +
                        "RETURN p.idProjeto AS idProjeto, p.titulo AS titulo, p.areaPesquisa AS area, " +
                        "p.dataInicio AS inicio, p.dataFim AS fim, " +
                        "pesq.nome AS nomeCoordenador, pesq.email AS emailCoordenador " +
                        "ORDER BY p.idProjeto";

                Result result = tx.run(cypher);

                System.out.println("\n--- Projetos com Coordenadores ---\n");

                while (result.hasNext()) {
                    Record rec = result.next();
                    System.out.printf("ID: %d | Título: %s | Área: %s\n",
                            rec.get("idProjeto").asInt(),
                            rec.get("titulo").asString(),
                            rec.get("area").asString());

                    System.out.printf("   Início: %s | Fim: %s\n",
                            rec.get("inicio").asLocalDate().toString(),
                            rec.get("fim").asLocalDate().toString());

                    System.out.printf("   Coordenado por: %s (%s)\n\n",
                            rec.get("nomeCoordenador").asString(),
                            rec.get("emailCoordenador").asString());
                }

                return null;
            });
        } catch (Exception e) {
            System.out.println("Erro ao listar projetos com coordenadores: " + e.getMessage());
        }
    }



}
