import org.neo4j.driver.Driver;

import java.util.Scanner;
import java.util.Set;

public class FinanciaController {

    private final FinanciaModel model;
    private final InstituicaoController instituicaoController;
    private final ProjetoController projetoController;

    public FinanciaController(Driver driver) {
        this.model = new FinanciaModel(driver);
        this.instituicaoController = new InstituicaoController(driver);
        this.projetoController = new ProjetoController(driver);
    }

    public void criarFinanciamento() {
        Scanner input = new Scanner(System.in);

        System.out.println("Insira os dados do financiamento:\n");
        instituicaoController.listarInstituicoes();
        System.out.print("\nID da Instituição: ");
        int idInstituicao = input.nextInt();

        projetoController.listarProjetos();
        System.out.print("\nID do Projeto: ");
        int idProjeto = input.nextInt();

        FinanciaBean f = new FinanciaBean(idInstituicao, idProjeto);
        model.create(f);
        System.out.println("Financiamento cadastrado com sucesso!");
    }

    public void listarFinanciamentos() {
        Set<FinanciaBean> todos = model.listAll();
        // System.out.println("ID Instituição | Instituição | ID Projeto | Projeto");
        System.out.println("------------------------------------------------------------");

        for (FinanciaBean f : todos) {
            System.out.println(f);
        }
    }

    public void removerFinanciamento() {
        Scanner input = new Scanner(System.in);

        System.out.println("Lista de financiamentos:");
        listarFinanciamentos();

        System.out.println("\nLista de Instituições:");
        instituicaoController.listarInstituicoes();

        System.out.println("\nLista de Projetos:");
        projetoController.listarProjetos();

        System.out.print("\nInforme o ID da Instituição: ");
        int idInstituicao = input.nextInt();

        System.out.print("Informe o ID do Projeto: ");
        int idProjeto = input.nextInt();

        model.remove(idInstituicao, idProjeto);
        System.out.println("Financiamento removido com sucesso.");
    }

    public void listarProjetosComInstituicoesFinanciadas() {
        listarFinanciamentos();
    }
}
