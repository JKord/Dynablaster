package jkord.dynablaster.web.rest;

import jkord.core.security.AuthoritiesConstants;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.repository.LobbyRepository;
import jkord.dynablaster.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@Secured(AuthoritiesConstants.USER)
@RequestMapping("/api/game/lobby")
public class LobbyResource {

    @Inject
    private LobbyService lobbyService;

    @Inject
    private LobbyRepository lobbyRepository;

    @Transactional
    @RequestMapping(value = "/create", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lobby> create(@Valid @RequestBody Lobby lobby) {
        lobbyService.create(lobby);

        lobby.getUsers().clear(); // FIXME

        return new ResponseEntity<>(lobby, HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return new ResponseEntity<>(lobbyService.findOneActiveById(id), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  list() {

        return new ResponseEntity<>(lobbyRepository.findAllByIsActiveIsFalse(), HttpStatus.OK);
    }
}
