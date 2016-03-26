package jkord.dynablaster.web;

import jkord.core.domain.User;
import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.service.GameService;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import javax.inject.Inject;

abstract class BaseGameController {

    @Inject
    protected GameService gameService;

    protected IGame getGame(SimpMessageHeaderAccessor headerAccessor) {
        String key = (String) headerAccessor.getSessionAttributes().get(IGame.KEY_NAME);
        IGame game = gameService.getGame(key);

        if (game == null) {
            throw new CustomParameterizedException("Game not started");
        }

        return game;

    }

    protected User getUser(SimpMessageHeaderAccessor headerAccessor) {
        return gameService.getUserInGamaByName(getGame(headerAccessor), headerAccessor.getUser().getName());
    }
}
