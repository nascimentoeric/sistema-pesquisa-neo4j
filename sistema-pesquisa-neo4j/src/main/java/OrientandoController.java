import org.neo4j.driver.Driver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class OrientandoController {

    private final OrientandoModel model;

    public OrientandoController(Driver driver) {
        this.model = new OrientandoModel(driver);
    }

    public void criarOrientando() {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os dados do novo orientando:");

        System.out.print("ID do Orientando: ");
        int idOrientando = input.nextInt();
        input.nextLine();

        System.out.print("Nome: ");
        String nome = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Nível Acadêmico: ");
        String nivel = input.nextLine();

        System.out.print("ID do Projeto: ");
        int idProjeto = input.nextInt();

        OrientandoBean o = new OrientandoBean(idOrientando, nome, email, nivel, idProjeto);
        model.create(o);

        System.out.println("Orientando cadastrado com sucesso!");
    }

    public void listarOrientandos() {
        HashSet<OrientandoBean> todos = model.listAll();
        Iterator<OrientandoBean> it = todos.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    public void removerOrientando() {
        Scanner input = new Scanner(System.in);
        listarOrientandos();
        System.out.print("Informe o ID do Orientando a ser removido: ");
        int id = input.nextInt();
        model.remove(id);
        System.out.println("Orientando removido com sucesso.");
    }

    public void alterarOrientando() {
        Scanner input = new Scanner(System.in);
        listarOrientandos();
        System.out.print("Informe o ID do Orientando a ser alterado: ");
        int idOrientando = input.nextInt();
        input.nextLine();

        System.out.print("Novo nome: ");
        String nome = input.nextLine();

        System.out.print("Novo email: ");
        String email = input.nextLine();

        System.out.print("Novo nível acadêmico: ");
        String nivel = input.nextLine();

        System.out.print("Novo ID do Projeto: ");
        int idProjeto = input.nextInt();

        OrientandoBean o = new OrientandoBean(idOrientando, nome, email, nivel, idProjeto);
        model.update(o);
        System.out.println("Orientando atualizado com sucesso.");
    }
}
