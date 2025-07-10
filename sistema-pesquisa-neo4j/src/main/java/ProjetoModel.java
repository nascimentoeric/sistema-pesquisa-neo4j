import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProjetoModel {

    private final Driver driver;

    public Driver getDriver() {
        return this.driver;
    }

    public ProjetoModel(Driver driver) {
        this.driver = driver;
    }

    public void create(ProjetoBean projeto) {
        try (Session session = driver.session()) {

            String checkIdCypher = "MATCH (p:Projeto {idProjeto: $idProjeto}) RETURN p LIMIT 1";
            boolean exists = session.readTransaction(tx -> {
                Result result = tx.run(checkIdCypher, Values.parameters("idProjeto", projeto.getIdProjeto()));
                return result.hasNext();
            });

            int idParaUsar = projeto.getIdProjeto();

            if (exists) {
                String maxIdCypher = "MATCH (p:Projeto) RETURN COALESCE(MAX(p.idProjeto), 0) + 1 AS nextId";
                idParaUsar = session.readTransaction(tx -> {
                    Result result = tx.run(maxIdCypher);
                    return result.single().get("nextId").asInt();
                });
                System.out.println("ID informado já existe. Usando novo ID: " + idParaUsar);
            }

            String createProjetoCypher = "CREATE (p:Projeto {idProjeto: $idProjeto, titulo: $titulo, areaPesquisa: $areaPesquisa, dataInicio: $dataInicio, dataFim: $dataFim})";
            int finalId = idParaUsar;
            session.writeTransaction(tx -> tx.run(createProjetoCypher, Values.parameters(
                    "idProjeto", finalId,
                    "titulo", projeto.getTitulo(),
                    "areaPesquisa", projeto.getAreaPesquisa(),
                    "dataInicio", projeto.getDataInicio().toLocalDate(),
                    "dataFim", projeto.getDataFim().toLocalDate()
            )).consume());


            String coordenaCypher = "MATCH (p:Projeto {idProjeto: $idProjeto}), (pes:Pesquisador {idPesquisador: $idCoordenador}) " +
                    "MERGE (p)-[:COORDENADO_POR]->(pes)";
            session.writeTransaction(tx -> tx.run(coordenaCypher, Values.parameters(
                    "idProjeto", finalId,
                    "idCoordenador", projeto.getIdCoordenador()
            )).consume());


            projeto.setIdProjeto(finalId);
        }
    }


//    public Set<ProjetoBean> listAll() {
//        Set<ProjetoBean> list = new HashSet<>();
//
//        String cypher = "MATCH (p:Projeto)-[:COORDENADO_POR]->(pesq:Pesquisador) " +
//                "RETURN p, pesq.idPesquisador AS idCoordenador";
//
//        try (Session session = driver.session()) {
//            session.readTransaction(tx -> {
//                Result result = tx.run(cypher);
//                while (result.hasNext()) {
//                    Record record = result.next();
//                    Node projNode = record.get("p").asNode();
//
//                    ProjetoBean p = new ProjetoBean(
//                            projNode.get("idProjeto").asInt(),
//                            record.get("idCoordenador").asInt(),
//                            projNode.get("idInstituicao").asInt(),
//                            projNode.get("titulo").asString(),
//                            projNode.get("areaPesquisa").asString(),
//                            Date.valueOf(projNode.get("dataInicio").asLocalDate()),
//                            Date.valueOf(projNode.get("dataFim").asLocalDate())
//                    );
//                    list.add(p);
//                }
//                return null;
//            });
//        }
//        return list;
//    }


    public void remove(int idProjeto) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (p:Projeto {idProjeto: $idProjeto}) DETACH DELETE p";
            session.writeTransaction(tx -> tx.run(cypher, Values.parameters("idProjeto", idProjeto)).consume());
        }
    }


    public boolean exists(int idProjeto) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (p:Projeto {idProjeto: $idProjeto}) RETURN p LIMIT 1";
            return session.readTransaction(tx -> {
                Result result = tx.run(cypher, Values.parameters("id", idProjeto));
                return result.hasNext();
            });
        }
    }


    public int getNextId() {
        try (Session session = driver.session()) {
            String cypher = "MATCH (p:Projeto) RETURN COALESCE(MAX(p.idProjeto), 0) + 1 AS nextId";
            return session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                return result.single().get("nextId").asInt();
            });
        }
    }
}
