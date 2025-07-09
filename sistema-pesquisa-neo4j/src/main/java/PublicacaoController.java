import org.neo4j.driver.Driver;

import java.sql.Date;
import java.util.HashSet;
import java.util.Scanner;

public class PublicacaoController {

    private final PublicacaoModel model;

    public PublicacaoController(Driver driver) {
        this.model = new PublicacaoModel(driver);
    }

    public void criarPublicacao() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Insira os dados da publicação:");

            System.out.print("ID do Projeto: ");
            int idProjeto = input.nextInt();
            input.nextLine(); // limpar buffer

            System.out.print("Título: ");
            String titulo = input.nextLine();

            System.out.print("Tipo: ");
            String tipo = input.nextLine();

            System.out.print("Data de Publicação (AAAA-MM-DD): ");
            String dataStr = input.nextLine();
            Date dataPublicacao = Date.valueOf(dataStr);

            System.out.print("DOI: ");
            String doi = input.nextLine();

            PublicacaoBean p = new PublicacaoBean(0, idProjeto, titulo, tipo, dataPublicacao, doi);
            model.create(p);

            System.out.println("Publicação cadastrada com sucesso! ID gerado: " + p.getIdPublicacao());
        } catch (Exception e) {
            System.out.println("Erro ao criar publicação: " + e.getMessage());
        }
    }

    public void listarPublicacoes() {
        HashSet<PublicacaoBean> publicacoes = model.listAll();
        for (PublicacaoBean p : publicacoes) {
            System.out.println(p);
        }
    }

    public void atualizarPublicacao() {
        Scanner input = new Scanner(System.in);
        try {
            listarPublicacoes();

            System.out.print("Informe o ID da Publicação que deseja atualizar: ");
            int idPublicacao = input.nextInt();
            input.nextLine();

            System.out.print("Novo ID do Projeto: ");
            int idProjeto = input.nextInt();
            input.nextLine();

            System.out.print("Novo Título: ");
            String titulo = input.nextLine();

            System.out.print("Novo Tipo: ");
            String tipo = input.nextLine();

            System.out.print("Nova Data de Publicação (AAAA-MM-DD): ");
            String dataStr = input.nextLine();
            Date dataPublicacao = Date.valueOf(dataStr);

            System.out.print("Novo DOI: ");
            String doi = input.nextLine();

            PublicacaoBean p = new PublicacaoBean(idPublicacao, idProjeto, titulo, tipo, dataPublicacao, doi);
            model.update(p);

            System.out.println("Publicação atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar publicação: " + e.getMessage());
        }
    }

    public void removerPublicacao() {
        Scanner input = new Scanner(System.in);
        try {
            listarPublicacoes();

            System.out.print("Informe o ID da Publicação que deseja remover: ");
            int idPublicacao = input.nextInt();

            model.remove(idPublicacao);
            System.out.println("Publicação removida com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao remover publicação: " + e.getMessage());
        }
    }

    public void listarPublicacoesDetalhadas() {
        HashSet<String> lista = model.listarPublicacoesDetalhadas();
        System.out.println("Título | Tipo | Data | Projeto | Pesquisador");
        System.out.println("--------------------------------------------------------");
        for (String linha : lista) {
            System.out.println(linha);
        }
    }
}
