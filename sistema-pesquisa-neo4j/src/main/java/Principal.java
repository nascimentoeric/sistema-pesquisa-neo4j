import org.neo4j.driver.Driver;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Conexao conexao = new Conexao();
        Driver driver = conexao.getDriver();

        PesquisadorController pesquisadorController = new PesquisadorController(driver);
        InstituicaoController instituicaoController = new InstituicaoController(driver);
        OrientandoController orientandoController = new OrientandoController(driver);
        FinanciaController financiaController = new FinanciaController(driver);
        ProjetoController projetoController = new ProjetoController(driver);
        PublicacaoController publicacaoController = new PublicacaoController(driver);

        int op;
        do {
            op = menu();
            try {
                switch (op) {
                    // INSERÇÕES
                    case 1: pesquisadorController.criarPesquisador(); break;
                    case 2: instituicaoController.criarInstituicao(); break;
                    case 3: orientandoController.criarOrientando(); break;
                    case 4: financiaController.criarFinanciamento(); break;
                    case 5: projetoController.criarProjeto(); break;
                    case 6: publicacaoController.criarPublicacao(); break;

                    // REMOÇÕES
                    case 7: pesquisadorController.removerPesquisador(); break;
                    case 8: instituicaoController.removerInstituicao(); break;
                    case 9: orientandoController.removerOrientando(); break;
                    case 10: projetoController.removerProjeto(); break;
                    case 11: publicacaoController.removerPublicacao(); break;

                    // ALTERAÇÕES
                    case 12: pesquisadorController.alterarPesquisador(); break;
                    case 13: instituicaoController.alterarInstituicao(); break;
                    case 14: orientandoController.alterarOrientando(); break;
                    case 15: publicacaoController.atualizarPublicacao(); break;

                    // LISTAGENS BÁSICAS
                    case 16: pesquisadorController.listarPesquisadores(); break;
                    case 17: instituicaoController.listarInstituicoes(); break;
                    case 18: orientandoController.listarOrientandos(); break;
                    case 19: financiaController.listarFinanciamentos(); break;
                    case 20: projetoController.listarProjetos(); break;
                    case 21: publicacaoController.listarPublicacoes(); break;

                    // LISTAGENS COM RELACIONAMENTOS
                    case 22: publicacaoController.listarPublicacoesDetalhadas(); break;
                    case 23: financiaController.listarProjetosComInstituicoesFinanciadas(); break;
                    case 24: pesquisadorController.listarProjetosComCoordenadores(); break;
                    case 25: projetoController.listarProjetosComPesquisadoresParticipantes(); break;

                    default:
                        System.out.println("Saindo...");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        } while (op > 0 && op < 26);

        conexao.closeConnection();
    }

    private static int menu() {
        System.out.println("\n============================ MENU ============================\n");
        System.out.println("SUGESTÃO: Verifique os IDs correspondentes antes de operações que solicitam o mesmo.\n");

        System.out.println("INSERÇÕES");
        System.out.println(" 1 - Inserir um novo pesquisador");
        System.out.println(" 2 - Inserir uma nova instituição");
        System.out.println(" 3 - Inserir um novo orientando");
        System.out.println(" 4 - Inserir um novo financiamento");
        System.out.println(" 5 - Inserir um novo projeto");
        System.out.println(" 6 - Inserir uma nova publicação\n");

        System.out.println("REMOÇÕES");
        System.out.println(" 7 - Remover um pesquisador");
        System.out.println(" 8 - Remover uma instituição");
        System.out.println(" 9 - Remover um orientando");
        System.out.println("10 - Remover um projeto");
        System.out.println("11 - Remover uma publicação\n");

        System.out.println("ALTERAÇÕES");
        System.out.println("12 - Alterar dados de um pesquisador");
        System.out.println("13 - Alterar dados de uma instituição");
        System.out.println("14 - Alterar dados de um orientando");
        System.out.println("15 - Alterar dados de uma publicação\n");

        System.out.println("LISTAGENS BÁSICAS");
        System.out.println("16 - Listar pesquisadores");
        System.out.println("17 - Listar instituições");
        System.out.println("18 - Listar orientandos");
        System.out.println("19 - Listar financiamentos");
        System.out.println("20 - Listar projetos");
        System.out.println("21 - Listar publicações\n");

        System.out.println("LISTAGENS COM RELACIONAMENTOS");
        System.out.println("22 - Listar publicações com projeto e pesquisador");
        System.out.println("23 - Listar projetos com instituições e financiamento");
        System.out.println("24 - Listar projetos com coordenador e instituição");
        System.out.println("25 - Listar projetos e pesquisadores participantes\n");

        System.out.println("Digite qualquer outro valor para sair.");
        System.out.print("Sua opção: ");

        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
}
