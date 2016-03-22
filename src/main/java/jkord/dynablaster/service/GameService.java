package jkord.dynablaster.service;

import jkord.dynablaster.domain.GameMap;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    public GameMap createMap() {
        return new GameMap();
    }
}
