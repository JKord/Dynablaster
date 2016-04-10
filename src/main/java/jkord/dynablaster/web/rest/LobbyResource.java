package jkord.dynablaster.web.rest;

import jkord.core.security.AuthoritiesConstants;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.repository.LobbyRepository;
import jkord.dynablaster.service.LobbyService;
import jkord.dynablaster.web.dto.LobbyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

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
    public ResponseEntity<?> create(@Valid @RequestBody Lobby lobby) throws URISyntaxException {
        lobbyService.create(lobby);
        return ResponseEntity.created(
            new URI(String.format("/api/game/lobby/%d/get", lobby.getId())))
            .body(new LobbyDTO(lobby, true));
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
