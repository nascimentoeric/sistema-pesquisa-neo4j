import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.HashSet;
import java.util.Set;

public class PesquisadorModel {

    private final Driver driver;

    public PesquisadorModel(Driver driver) {
        this.driver = driver;
    }

    public void create(PesquisadorBean p) {
        try (Session session = driver.session()) {
            String cypher = "CREATE (pes:Pesquisador {id: $id, nome: $nome, email: $email, instituicao: $instituicao})";
            session.writeTransaction(tx -> tx.run(cypher,
                    Values.parameters(
                            "id", p.getIdPesquisador(),
                            "nome", p.getNome(),
                            "email", p.getEmail(),
                            "instituicao", p.getInstituicao()
                    )));
        }
    }

    public Set<PesquisadorBean> listAll() {
        Set<PesquisadorBean> list = new HashSet<>();
        String cypher = "MATCH (pes:Pesquisador) RETURN pes.id AS id, pes.nome AS nome, pes.email AS email, pes.instituicao AS instituicao";

        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                while (result.hasNext()) {
                    Record r = result.next();
                    PesquisadorBean p = new PesquisadorBean(
                            r.get("id").asInt(),
                            r.get("nome").asString(),
                            r.get("email").asString(),
                            r.get("instituicao").asString()
                    );
                    list.add(p);
                }
                return null;
            });
        }

        return list;
    }

    public void remove(int idPesquisador) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (pes:Pesquisador {id: $id}) DETACH DELETE pes";
            session.writeTransaction(tx -> tx.run(cypher, Values.parameters("id", idPesquisador)));
        }
    }

    public void update(PesquisadorBean p) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (pes:Pesquisador {id: $id}) " +
                    "SET pes.nome = $nome, pes.email = $email, pes.instituicao = $instituicao";
            session.writeTransaction(tx -> tx.run(cypher, Values.parameters(
                    "id", p.getIdPesquisador(),
                    "nome", p.getNome(),
                    "email", p.getEmail(),
                    "instituicao", p.getInstituicao()
            )));
        }
    }
}
