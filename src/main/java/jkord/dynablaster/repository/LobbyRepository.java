package jkord.dynablaster.repository;

import jkord.dynablaster.entity.Lobby;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {

    @EntityGraph(value = "graph.Lobby.lobbyUsers", type = EntityGraph.EntityGraphType.LOAD)
    Lobby findOneById(Long lobbyId);

    List<Lobby> findAllByIsActiveIsFalse();
}
