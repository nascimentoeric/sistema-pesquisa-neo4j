import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.HashSet;

public class ParticipaProjetoModel {
    private final Driver driver;

    public ParticipaProjetoModel(Driver driver) {
        this.driver = driver;
    }

    public void create(ParticipaProjetoBean p) {
        try (Session session = driver.session()) {
            String cypher = """
                MATCH (pes:Pesquisador {idPesquisador: $idPesquisador})
                MATCH (proj:Projeto {idProjeto: $idProjeto})
                MERGE (pes)-[r:PARTICIPA {papel: $papel}]->(proj)
            """;

            session.writeTransaction(tx -> {
                tx.run(cypher, Values.parameters(
                        "idPesquisador", p.getIdPesquisador(),
                        "idProjeto", p.getIdProjeto(),
                        "papel", p.getPapel()
                ));
                return null;
            });
        }
    }

    public HashSet<ParticipaProjetoBean> listAll() {
        HashSet<ParticipaProjetoBean> list = new HashSet<>();

        try (Session session = driver.session()) {
            String cypher = """
                MATCH (pes:Pesquisador)-[r:PARTICIPA]->(proj:Projeto)
                RETURN pes.idPesquisador AS idPesquisador, proj.idProjeto AS idProjeto, r.papel AS papel
            """;

            session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                while (result.hasNext()) {
                    Record rec = result.next();
                    ParticipaProjetoBean p = new ParticipaProjetoBean(
                            rec.get("idPesquisador").asInt(),
                            rec.get("idProjeto").asInt(),
                            rec.get("papel").asString()
                    );
                    list.add(p);
                }
                return null;
            });
        }

        return list;
    }

    public void remove(int idPesquisador, int idProjeto) {
        try (Session session = driver.session()) {
            String cypher = """
                MATCH (pes:Pesquisador {idPesquisador: $idPesquisador})-[r:PARTICIPA]->(proj:Projeto {idProjeto: $idProjeto})
                DELETE r
            """;

            session.writeTransaction(tx -> {
                tx.run(cypher, Values.parameters(
                        "idPesquisador", idPesquisador,
                        "idProjeto", idProjeto
                ));
                return null;
            });
        }
    }

    public void update(ParticipaProjetoBean p) {
        try (Session session = driver.session()) {
            String cypher = """
                MATCH (pes:Pesquisador {idPesquisador: $idPesquisador})-[r:PARTICIPA]->(proj:Projeto {idProjeto: $idProjeto})
                SET r.papel = $papel
            """;

            session.writeTransaction(tx -> {
                tx.run(cypher, Values.parameters(
                        "idPesquisador", p.getIdPesquisador(),
                        "idProjeto", p.getIdProjeto(),
                        "papel", p.getPapel()
                ));
                return null;
            });
        }
    }
}
