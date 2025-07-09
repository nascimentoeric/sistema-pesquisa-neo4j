import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.HashSet;

public class OrientandoModel {

    private final Driver driver;

    public OrientandoModel(Driver driver) {
        this.driver = driver;
    }

    public void create(OrientandoBean o) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE (o:Orientando {idOrientando: $idOrientando, nome: $nome, email: $email, nivelAcademico: $nivelAcademico})",
                        Values.parameters(
                                "idOrientando", o.getIdOrientando(),
                                "nome", o.getNome(),
                                "email", o.getEmail(),
                                "nivelAcademico", o.getNivelAcademico()
                                //"idProjeto", o.getIdProjeto()
                        ));
                return null;
            });
        }
    }

    public HashSet<OrientandoBean> listAll() {
        HashSet<OrientandoBean> list = new HashSet<>();

        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run("MATCH (o:Orientando) RETURN o");
                while (result.hasNext()) {
                    Record record = result.next();
                    Value node = record.get("o");
                    OrientandoBean o = new OrientandoBean(
                            node.get("idOrientando").asInt(),
                            node.get("nome").asString(),
                            node.get("email").asString(),
                            node.get("nivelAcademico").asString()
                            //node.get("idProjeto").asInt()
                    );
                    list.add(o);
                }
                return null;
            });
        }

        return list;
    }

    public void remove(int id) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (o:Orientando {idOrientando: $idOrientando}) DETACH DELETE o",
                        Values.parameters("idOrientando", id));
                return null;
            });
        }
    }

    public void update(OrientandoBean o) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (ori:Orientando {idOrientando: $idOrientando}) " +
                                "SET ori.nome = $nome, ori.email = $email, ori.nivelAcademico = $nivelAcademico",
                        Values.parameters(
                                "idOrientando", o.getIdOrientando(),
                                "nome", o.getNome(),
                                "email", o.getEmail(),
                                "nivelAcademico", o.getNivelAcademico()
                                //"idProjeto", o.getIdProjeto()
                        ));
                return null;
            });
        }
    }
}
