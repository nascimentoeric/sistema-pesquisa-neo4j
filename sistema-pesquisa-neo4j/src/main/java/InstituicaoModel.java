import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;

import java.util.HashSet;

import static org.neo4j.driver.Values.parameters;

public class InstituicaoModel {

    private final Driver driver;

    public InstituicaoModel(Driver driver) {
        this.driver = driver;
    }

    public void create(InstituicaoBean a) {
        try (Session session = driver.session()) {
            String checkCypher = "MATCH (i:Instituicao {idInstituicao: $idInstituicao}) RETURN i LIMIT 1";
            boolean exists = session.readTransaction(tx -> {
                Result result = tx.run(checkCypher, parameters("idInstituicao", a.getIdInstituicao()));
                return result.hasNext();
            });

            int idParaUsar = a.getIdInstituicao();

            if (exists) {
                String maxIdCypher = "MATCH (i:Instituicao) RETURN COALESCE(MAX(i.idInstituicao), 0) + 1 AS nextId";
                idParaUsar = session.readTransaction(tx -> {
                    Result result = tx.run(maxIdCypher);
                    return result.single().get("nextId").asInt();
                });
                System.out.println("ID já existente. Utilizando novo ID: " + idParaUsar);
            }

            int finalId = idParaUsar;

            session.writeTransaction(tx -> {
                tx.run("CREATE (i:Instituicao {idInstituicao: $idInstituicao, nome: $nome, tipo: $tipo, pais: $pais})",
                        parameters(
                                "idInstituicao", finalId,
                                "nome", a.getNome(),
                                "tipo", a.getTipo(),
                                "pais", a.getPais()
                        ));
                return null;
            });
        }
    }


    public HashSet<InstituicaoBean> listAll() {
        HashSet<InstituicaoBean> list = new HashSet<>();
        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run("MATCH (i:Instituicao) RETURN i.idInstituicao AS idInstituicao, i.nome AS nome, i.tipo AS tipo, i.pais AS pais ORDER BY i.id");
                while (result.hasNext()) {
                    Record record = result.next();
                    InstituicaoBean inst = new InstituicaoBean(
                            record.get("idInstituicao").asInt(),
                            record.get("nome").asString(),
                            record.get("tipo").asString(),
                            record.get("pais").asString()
                    );
                    list.add(inst);
                }
                return null;
            });
        }
        return list;
    }

    public void remove(int id) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (i:Instituicao {idInstituicao: $idInstituicao}) DETACH DELETE i",
                        parameters("idInstituicao", id));
                return null;
            });
        }
    }

    public void update(InstituicaoBean a) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (i:Instituicao {idInstituicao: $idInstituicao}) " +
                                "SET i.nome = $nome, i.tipo = $tipo, i.pais = $pais",
                        parameters(
                                "idInstituicao", a.getIdInstituicao(),
                                "nome", a.getNome(),
                                "tipo", a.getTipo(),
                                "pais", a.getPais()
                        ));
                return null;
            });
        }
    }
}
