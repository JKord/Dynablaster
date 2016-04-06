package jkord.dynablaster.repository;

import jkord.dynablaster.entity.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {
}
