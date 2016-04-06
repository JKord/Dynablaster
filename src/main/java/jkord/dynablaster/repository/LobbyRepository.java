package jkord.dynablaster.repository;

import jkord.dynablaster.entity.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {

    Optional<Lobby> findOneById(Long lobbyId);

    List<Lobby> findAllByIsActiveIsFalse();
}
