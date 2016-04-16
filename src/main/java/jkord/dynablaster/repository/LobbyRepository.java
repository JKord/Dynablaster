package jkord.dynablaster.repository;

import jkord.dynablaster.entity.Lobby;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {

    @EntityGraph(value = "graph.Lobby.lobbyUsers", type = EntityGraph.EntityGraphType.LOAD)
    Lobby findOneById(Long lobbyId);

    @Query("select l from Lobby l where l.isActive=false AND l.name LIKE :name order by l.createdAt desc")
    Page<Lobby> search(@Param("name") String name, Pageable pageable);
}
