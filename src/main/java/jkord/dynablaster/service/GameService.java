package jkord.dynablaster.service;

import jkord.core.service.util.RandomUtil;
import jkord.dynablaster.domain.Game;
import jkord.dynablaster.domain.GameMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {

    private static final Map<String, Game> games = new HashMap<>();

    public Game createGame() {
        Game game = new Game(RandomUtil.generateKeyGame());
        addGame(game);

        return game;
    }

    public void addGame(Game game) {
        games.put(game.getKey(), game);
    }

    public Game getGameByKey(String key) {
        return games.get(key);
    }

    public void removeGame(String key) {
        games.remove(key);
    }
}
