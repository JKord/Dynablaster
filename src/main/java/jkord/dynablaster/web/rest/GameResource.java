package jkord.dynablaster.web.rest;

import jkord.core.domain.User;
import jkord.core.security.AuthoritiesConstants;
import jkord.core.service.UserService;
import jkord.dynablaster.domain.MapObject;
import jkord.dynablaster.service.GameService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;

@RestController
@Secured(AuthoritiesConstants.USER)
@RequestMapping("/api/game")
public class GameResource {

    @Inject
    private GameService gameService;

    @Inject
    private UserService userService;

    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User map() {
        MapObject[][] map = gameService.createMap().getMap();

        return userService.getUserWithAuthorities();


        //return new ResponseEntity(map, HttpStatus.OK) ;
    }
}
