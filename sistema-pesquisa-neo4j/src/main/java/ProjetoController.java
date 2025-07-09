import org.neo4j.driver.Driver;

import java.sql.Date;
import java.util.Scanner;
import java.util.Set;

public class ProjetoController {

    private final ProjetoModel model;
    private final PesquisadorController pesquisadorController;
    private final InstituicaoController instituicaoController;

    public ProjetoController(Driver driver) {
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
        Set<ProjetoBean> todos = model.listAll();
        System.out.println("ID | Coordenador | Instituição | Título | Área Pesquisa | Data Início | Data Fim");
        System.out.println("---------------------------------------------------------------------------------");
        for (ProjetoBean p : todos) {
            System.out.println(p);
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
}
