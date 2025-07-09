import org.neo4j.driver.Driver;

import java.util.HashSet;
import java.util.Scanner;

public class ParticipaProjetoController {
    private final ParticipaProjetoModel model;

    public ParticipaProjetoController(Driver driver) {
        this.model = new ParticipaProjetoModel(driver);
    }

    public void criarParticipacao() {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os dados da participação do pesquisador em projeto:");
        System.out.print("ID do Pesquisador: ");
        int idPesquisador = input.nextInt();
        System.out.print("ID do Projeto: ");
        int idProjeto = input.nextInt();
        input.nextLine();
        System.out.print("Papel: ");
        String papel = input.nextLine();

        ParticipaProjetoBean p = new ParticipaProjetoBean(idPesquisador, idProjeto, papel);
        model.create(p);
        System.out.println("Participação cadastrada com sucesso!");
    }

    public void listarParticipacoes() {
        HashSet<ParticipaProjetoBean> todas = model.listAll();
        for (ParticipaProjetoBean p : todas) {
            System.out.println(p);
        }
    }

    public void removerParticipacao() {
        Scanner input = new Scanner(System.in);
        listarParticipacoes();
        System.out.print("Informe o ID do Pesquisador: ");
        int idPesquisador = input.nextInt();
        System.out.print("Informe o ID do Projeto: ");
        int idProjeto = input.nextInt();

        model.remove(idPesquisador, idProjeto);
        System.out.println("Participação removida com sucesso.");
    }

    public void alterarParticipacao() {
        Scanner input = new Scanner(System.in);
        listarParticipacoes();
        System.out.print("Informe o ID do Pesquisador: ");
        int idPesquisador = input.nextInt();
        System.out.print("Informe o ID do Projeto: ");
        int idProjeto = input.nextInt();
        input.nextLine();
        System.out.print("Novo papel: ");
        String papel = input.nextLine();

        ParticipaProjetoBean p = new ParticipaProjetoBean(idPesquisador, idProjeto, papel);
        model.update(p);
        System.out.println("Participação atualizada com sucesso.");
    }
}
