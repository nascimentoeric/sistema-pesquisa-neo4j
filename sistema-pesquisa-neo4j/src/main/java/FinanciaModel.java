import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.HashSet;
import java.util.Set;

public class FinanciaModel {

    private final Driver driver;

    public FinanciaModel(Driver driver) {
        this.driver = driver;
    }


    public void create(FinanciaBean f) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (i:Instituicao {id: $idInstituicao}), (p:Projeto {id: $idProjeto}) " +
                    "MERGE (i)-[:FINANCIA]->(p)";
            session.writeTransaction(tx -> tx.run(cypher,
                    Values.parameters("idInstituicao", f.getIdInstituicao(),
                            "idProjeto", f.getIdProjeto())));
        }
    }


    public Set<FinanciaBean> listAll() {
        Set<FinanciaBean> list = new HashSet<>();

        String cypher = "MATCH (i:Instituicao)-[:FINANCIA]->(p:Projeto) " +
                "RETURN i.id AS idInstituicao, i.nome AS nomeInstituicao, " +
                "p.id AS idProjeto, p.titulo AS nomeProjeto";

        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                while (result.hasNext()) {
                    Record record = result.next();
                    FinanciaBean f = new FinanciaBean(
                            record.get("idInstituicao").asInt(),
                            record.get("nomeInstituicao").asString(),
                            record.get("idProjeto").asInt(),
                            record.get("nomeProjeto").asString()
                    );
                    list.add(f);
                }
                return null;
            });
        }
        return list;
    }


    public void remove(int idInstituicao, int idProjeto) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (i:Instituicao {id: $idInstituicao})-[f:FINANCIA]->(p:Projeto {id: $idProjeto}) " +
                    "DELETE f";
            session.writeTransaction(tx -> tx.run(cypher,
                    Values.parameters("idInstituicao", idInstituicao,
                            "idProjeto", idProjeto)));
        }
    }
}
