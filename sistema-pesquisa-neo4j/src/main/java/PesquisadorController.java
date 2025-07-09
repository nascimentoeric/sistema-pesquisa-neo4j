import org.neo4j.driver.Driver;

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
        input.nextLine(); // limpar buffer
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
}
