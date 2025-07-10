import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.HashSet;
import java.util.Set;

public class PesquisadorModel {

    private final Driver driver;
    public Driver getDriver() {
        return this.driver;
    }


    public PesquisadorModel(Driver driver) {
        this.driver = driver;
    }

    public void create(PesquisadorBean p) {
        try (Session session = driver.session()) {
            String checkCypher = "MATCH (pes:Pesquisador {idPesquisador: $idPesquisador}) RETURN pes LIMIT 1";
            boolean exists = session.readTransaction(tx -> {
                Result result = tx.run(checkCypher, Values.parameters("idPesquisador", p.getIdPesquisador()));
                return result.hasNext();
            });

            int idParaUsar = p.getIdPesquisador();

            if (exists) {
                String maxIdCypher = "MATCH (pes:Pesquisador) RETURN COALESCE(MAX(pes.idPesquisador), 0) + 1 AS nextId";
                idParaUsar = session.readTransaction(tx -> {
                    Result result = tx.run(maxIdCypher);
                    int nextId = 0;
                    if (result.hasNext()) {
                        nextId = result.next().get("nextId").asInt();
                    }
                    return nextId;
                });
                System.out.println("ID informado já existe. Usando novo ID: " + idParaUsar);
            }

            String createCypher = "CREATE (pes:Pesquisador {idPesquisador: $idPesquisador, nome: $nome, email: $email, instituicao: $instituicao})";
            int finalIdParaUsar = idParaUsar;
            session.writeTransaction(tx -> {
                tx.run(createCypher,
                        Values.parameters(
                                "idPesquisador", finalIdParaUsar,
                                "nome", p.getNome(),
                                "email", p.getEmail(),
                                "instituicao", p.getInstituicao()
                        )).consume();
                return null;
            });
        }
    }


    public Set<PesquisadorBean> listAll() {
        Set<PesquisadorBean> list = new HashSet<>();
        String cypher = "MATCH (pes:Pesquisador) RETURN pes.idPesquisador AS idPesquisador, pes.nome AS nome, pes.email AS email, pes.instituicao AS instituicao";

        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                while (result.hasNext()) {
                    Record r = result.next();
                    PesquisadorBean p = new PesquisadorBean(
                            r.get("idPesquisador").asInt(),
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
            String cypher = "MATCH (pes:Pesquisador {idPesquisador: $idPesquisador}) DETACH DELETE pes";
            session.writeTransaction(tx -> {
                tx.run(cypher, Values.parameters("idPesquisador", idPesquisador)).consume();
                return null;
            });
        }
    }

    public void update(PesquisadorBean p) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (pes:Pesquisador {idPesquisador: $idPesquisador}) " +
                    "SET pes.nome = $nome, pes.email = $email, pes.instituicao = $instituicao";
            session.writeTransaction(tx -> tx.run(cypher, Values.parameters(
                    "idPesquisador", p.getIdPesquisador(),
                    "nome", p.getNome(),
                    "email", p.getEmail(),
                    "instituicao", p.getInstituicao()
            )).consume());
        }
    }
}
