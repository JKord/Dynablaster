package jkord.dynablaster.web.rest;

import jkord.core.security.AuthoritiesConstants;
import jkord.core.service.UserService;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.repository.LobbyRepository;
import jkord.dynablaster.service.LobbyService;
import jkord.dynablaster.web.MsgRoute;
import jkord.dynablaster.web.dto.LobbyDTO;
import jkord.dynablaster.web.dto.LobbyInfoDTO;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Secured(AuthoritiesConstants.USER)
@RequestMapping("/api/game/lobby")
public class LobbyResource {

    @Inject
    private LobbyService lobbyService;

    @Inject
    private LobbyRepository lobbyRepository;

    @Inject
    private UserService userService;

    @Transactional
    @RequestMapping(value = "/create", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody Lobby lobby) throws URISyntaxException {
        lobbyService.create(lobby);
        return ResponseEntity.created(
            new URI(String.format(MsgRoute.LOBBY_GET, lobby.getId())))
            .body(new LobbyDTO(lobby, userService.getUserWithAuthorities()));
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(
            new LobbyDTO(lobbyService.findOneActiveById(id), userService.getUserWithAuthorities())
        );
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list() {
        List<LobbyInfoDTO> lobbysInfoDTO = lobbyRepository
            .findAllByIsActiveIsFalse()
            .stream()
            .map(LobbyInfoDTO::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(lobbysInfoDTO);
    }

    @Transactional
    @RequestMapping(value = "/{id}/addUser", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@PathVariable Long id) {
        lobbyService.addUserToLobby(id);

        return ResponseEntity.ok().body(id);
    }
}
