import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProjetoModel {

    private final Driver driver;

    public ProjetoModel(Driver driver) {
        this.driver = driver;
    }

    public void create(ProjetoBean projeto) {
        try (Session session = driver.session()) {

            String cypher = "CREATE (p:Projeto {id: $id, idCoordenador: $idCoordenador, idInstituicao: $idInstituicao, " +
                    "titulo: $titulo, areaPesquisa: $areaPesquisa, dataInicio: $dataInicio, dataFim: $dataFim})";

            session.writeTransaction(tx -> tx.run(cypher,
                    Values.parameters(
                            "id", projeto.getIdProjeto(),
                            "idCoordenador", projeto.getIdCoordenador(),
                            "idInstituicao", projeto.getIdInstituicao(),
                            "titulo", projeto.getTitulo(),
                            "areaPesquisa", projeto.getAreaPesquisa(),
                            "dataInicio", projeto.getDataInicio().toString(),
                            "dataFim", projeto.getDataFim().toString()
                    )));
        }
    }

    public Set<ProjetoBean> listAll() {
        Set<ProjetoBean> list = new HashSet<>();

        String cypher = "MATCH (p:Projeto) RETURN p.id AS id, p.idCoordenador AS idCoordenador, " +
                "p.idInstituicao AS idInstituicao, p.titulo AS titulo, p.areaPesquisa AS areaPesquisa, " +
                "p.dataInicio AS dataInicio, p.dataFim AS dataFim";

        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                while (result.hasNext()) {
                    Record record = result.next();
                    ProjetoBean p = new ProjetoBean(
                            record.get("id").asInt(),
                            record.get("idCoordenador").asInt(),
                            record.get("idInstituicao").asInt(),
                            record.get("titulo").asString(),
                            record.get("areaPesquisa").asString(),
                            Date.valueOf(record.get("dataInicio").asString()),
                            Date.valueOf(record.get("dataFim").asString())
                    );
                    list.add(p);
                }
                return null;
            });
        }
        return list;
    }

    public void remove(int idProjeto) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (p:Projeto {id: $id}) DETACH DELETE p";
            session.writeTransaction(tx -> tx.run(cypher, Values.parameters("id", idProjeto)));
        }
    }


    public boolean exists(int idProjeto) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (p:Projeto {id: $id}) RETURN p LIMIT 1";
            return session.readTransaction(tx -> {
                Result result = tx.run(cypher, Values.parameters("id", idProjeto));
                return result.hasNext();
            });
        }
    }


    public int getNextId() {
        try (Session session = driver.session()) {
            String cypher = "MATCH (p:Projeto) RETURN COALESCE(MAX(p.id), 0) + 1 AS nextId";
            return session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                return result.single().get("nextId").asInt();
            });
        }
    }
}
