package jkord.dynablaster.web.rest;

import jkord.core.security.AuthoritiesConstants;
import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.entity.Statistic;
import jkord.dynablaster.service.GameService;
import jkord.dynablaster.web.dto.GameDTO;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
@Secured(AuthoritiesConstants.USER)
@RequestMapping("/api/game")
public class GameResource {

    @Inject private GameService gameService;

    @RequestMapping(value = "/start/{type}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDTO start(HttpServletResponse response,
        HttpSession session,
        @PathVariable String type,
        @CookieValue(name = "lobbyId") String lobbyIdStr,
        @CookieValue(name = "gameKey") String gameKey
    ) {
        IGame game = gameService.getGame(session);
        if (game == null) {
            if (gameKey.equals("null")) {
                Long lobbyId = (lobbyIdStr.equals("null"))? -1 : Long.decode(lobbyIdStr);
                game = gameService.createGame(GameType.valueOf(type.toUpperCase()), lobbyId);
            } else {
                game = gameService.getGame(gameKey);
            }
            session.setAttribute(IGame.KEY_NAME, game.getKey());
        }

        return new GameDTO(game);
    }

    @RequestMapping(value = "/end", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Statistic end(HttpSession session) {
        IGame game = gameService.getGame(session);
        if (game != null) {
            gameService.endGame(game);
            session.setAttribute(IGame.KEY_NAME, null);
        } else {
            throw new CustomParameterizedException("The game has been completed!");
        }

        return game.getStatistic();
    }
}
