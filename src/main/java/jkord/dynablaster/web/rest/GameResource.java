package jkord.dynablaster.web.rest;

import jkord.core.security.AuthoritiesConstants;
import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.entity.Statistics;
import jkord.dynablaster.service.GameService;
import jkord.dynablaster.web.dto.GameDTO;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@RestController
@Secured(AuthoritiesConstants.USER)
@RequestMapping("/api/game")
public class GameResource {

    @Inject
    private GameService gameService;

    @RequestMapping(value = "/start/{type}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDTO start(HttpSession session, @PathVariable String type) {
        IGame game = gameService.getGame(session);
        if (game == null) {
            game = gameService.createGame(GameType.valueOf(type.toUpperCase()));
            session.setAttribute(IGame.KEY_NAME, game.getKey());
        }

        return new GameDTO(game);
    }

    @RequestMapping(value = "/end", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Statistics end(HttpSession session) {
        IGame game = gameService.getGame(session);
        if (game != null) {
            gameService.endGame(game);
            session.setAttribute(IGame.KEY_NAME, null);
        }

        return game.getStatistics();
    }
}
