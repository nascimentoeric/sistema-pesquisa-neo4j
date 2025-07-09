import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.sql.Date;
import java.util.HashSet;

public class PublicacaoModel {
    private final Driver driver;


    public PublicacaoModel(Driver driver) {
        this.driver = driver;
    }

    public int getNextIdPublicacao() {
        try (Session session = driver.session()) {
            String cypher = "MATCH (p:Publicacao) RETURN max(p.idPublicacao) AS maxId";
            Record record = session.readTransaction(tx -> tx.run(cypher).single());
            if (record.get("maxId").isNull()) {
                return 1;
            } else {
                return record.get("maxId").asInt() + 1;
            }
        }
    }

    public void create(PublicacaoBean p) {
        int newId = getNextIdPublicacao();
        try (Session session = driver.session()) {
            String cypher =
                    "MATCH (proj:Projeto {idProjeto: $idProjeto}) " +
                            "CREATE (pub:Publicacao {idPublicacao: $idPublicacao, titulo: $titulo, tipo: $tipo, " +
                            "dataPublicacao: date($dataPublicacao), doi: $doi})-[:PERTENCE_A]->(proj)";

            session.writeTransaction(tx -> {
                tx.run(cypher, Values.parameters(
                        "idProjeto", p.getIdProjeto(),
                        "idPublicacao", newId,
                        "titulo", p.getTitulo(),
                        "tipo", p.getTipo(),
                        "dataPublicacao", p.getDataPublicacao().toString(),
                        "doi", p.getDoi()
                ));
                return null;
            });
        }
        p.setIdPublicacao(newId);
    }

    public HashSet<PublicacaoBean> listAll() {
        HashSet<PublicacaoBean> lista = new HashSet<>();

        try (Session session = driver.session()) {
            String cypher = "MATCH (pub:Publicacao)-[:PERTENCE_A]->(proj:Projeto) RETURN pub, proj.idProjeto AS idProjeto";

            session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                while (result.hasNext()) {
                    Record rec = result.next();
                    Node pubNode = rec.get("pub").asNode();

                    PublicacaoBean p = new PublicacaoBean(
                            pubNode.get("idPublicacao").asInt(),
                            rec.get("idProjeto").asInt(),
                            pubNode.get("titulo").asString(),
                            pubNode.get("tipo").asString(),
                            Date.valueOf(pubNode.get("dataPublicacao").asString()),
                            pubNode.get("doi").asString()
                    );
                    lista.add(p);
                }
                return null;
            });
        }
        return lista;
    }

    public void update(PublicacaoBean p) {
        try (Session session = driver.session()) {
            String cypher =
                    "MATCH (pub:Publicacao {idPublicacao: $idPublicacao}) " +
                            "SET pub.titulo = $titulo, pub.tipo = $tipo, pub.dataPublicacao = date($dataPublicacao), pub.doi = $doi";

            session.writeTransaction(tx -> {
                tx.run(cypher, Values.parameters(
                        "idPublicacao", p.getIdPublicacao(),
                        "titulo", p.getTitulo(),
                        "tipo", p.getTipo(),
                        "dataPublicacao", p.getDataPublicacao().toString(),
                        "doi", p.getDoi()
                ));
                return null;
            });
        }
    }

    public void remove(int idPublicacao) {
        try (Session session = driver.session()) {
            String cypher = "MATCH (pub:Publicacao {idPublicacao: $idPublicacao}) DETACH DELETE pub";
            session.writeTransaction(tx -> {
                tx.run(cypher, Values.parameters("idPublicacao", idPublicacao));
                return null;
            });
        }
    }

    public HashSet<String> listarPublicacoesDetalhadas() {
        HashSet<String> resultados = new HashSet<>();

        try (Session session = driver.session()) {
            String cypher =
                    "MATCH (pub:Publicacao)-[:PERTENCE_A]->(proj:Projeto)-[:COORDENADO_POR]->(pes:Pesquisador) " +
                            "RETURN pub.titulo AS tituloPub, pub.tipo AS tipoPub, pub.dataPublicacao AS dataPub, " +
                            "proj.titulo AS tituloProj, pes.nome AS nomePesq";

            session.readTransaction(tx -> {
                Result result = tx.run(cypher);
                while (result.hasNext()) {
                    Record rec = result.next();
                    String linha = String.format("%s | %s | %s | %s | %s",
                            rec.get("tituloPub").asString(),
                            rec.get("tipoPub").asString(),
                            rec.get("dataPub").asLocalDate().toString(),
                            rec.get("tituloProj").asString(),
                            rec.get("nomePesq").asString()
                    );
                    resultados.add(linha);
                }
                return null;
            });
        }
        return resultados;
    }
}
