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
                tx.run("CREATE (o:Orientando {id: $id, nome: $nome, email: $email, nivel: $nivel, idProjeto: $idProjeto})",
                        Values.parameters(
                                "id", o.getIdOrientando(),
                                "nome", o.getNome(),
                                "email", o.getEmail(),
                                "nivel", o.getNivelAcademico(),
                                "idProjeto", o.getIdProjeto()
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
                            node.get("id").asInt(),
                            node.get("nome").asString(),
                            node.get("email").asString(),
                            node.get("nivel").asString(),
                            node.get("idProjeto").asInt()
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
                tx.run("MATCH (o:Orientando {id: $id}) DETACH DELETE o",
                        Values.parameters("id", id));
                return null;
            });
        }
    }

    public void update(OrientandoBean o) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (ori:Orientando {id: $id}) " +
                                "SET ori.nome = $nome, ori.email = $email, ori.nivel = $nivel, ori.idProjeto = $idProjeto",
                        Values.parameters(
                                "id", o.getIdOrientando(),
                                "nome", o.getNome(),
                                "email", o.getEmail(),
                                "nivel", o.getNivelAcademico(),
                                "idProjeto", o.getIdProjeto()
                        ));
                return null;
            });
        }
    }
}
