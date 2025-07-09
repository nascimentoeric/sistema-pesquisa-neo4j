import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.exceptions.ServiceUnavailableException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {
    private Driver driver;

    public Conexao() {
        String uri = "neo4j://127.0.0.1:7687";
        String user = "neo4j";
        String senha = "12345678";

        try {
            this.driver = GraphDatabase.driver(uri, AuthTokens.basic(user, senha));
            // teste
            driver.verifyConnectivity();
        } catch (ServiceUnavailableException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, "Erro de conexão com Neo4j", ex);
            ex.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, "Erro geral ao conectar com Neo4j", ex);
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public Driver getDriver() {
        return driver;
    }

    public void closeConnection() {
        if (driver != null) {
            driver.close();
        }
    }
}
