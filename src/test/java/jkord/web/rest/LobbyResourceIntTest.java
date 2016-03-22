package jkord.web.rest;

import jkord.core.Application;
import jkord.domain.Lobby;
import jkord.repository.LobbyRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LobbyResource REST controller.
 *
 * @see LobbyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LobbyResourceIntTest {


    @Inject
    private LobbyRepository lobbyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLobbyMockMvc;

    private Lobby lobby;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LobbyResource lobbyResource = new LobbyResource();
        ReflectionTestUtils.setField(lobbyResource, "lobbyRepository", lobbyRepository);
        this.restLobbyMockMvc = MockMvcBuilders.standaloneSetup(lobbyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lobby = new Lobby();
    }

    @Test
    @Transactional
    public void createLobby() throws Exception {
        int databaseSizeBeforeCreate = lobbyRepository.findAll().size();

        // Create the Lobby

        restLobbyMockMvc.perform(post("/api/lobbys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lobby)))
                .andExpect(status().isCreated());

        // Validate the Lobby in the database
        List<Lobby> lobbys = lobbyRepository.findAll();
        assertThat(lobbys).hasSize(databaseSizeBeforeCreate + 1);
        Lobby testLobby = lobbys.get(lobbys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllLobbys() throws Exception {
        // Initialize the database
        lobbyRepository.saveAndFlush(lobby);

        // Get all the lobbys
        restLobbyMockMvc.perform(get("/api/lobbys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lobby.getId().intValue())));
    }

    @Test
    @Transactional
    public void getLobby() throws Exception {
        // Initialize the database
        lobbyRepository.saveAndFlush(lobby);

        // Get the lobby
        restLobbyMockMvc.perform(get("/api/lobbys/{id}", lobby.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lobby.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLobby() throws Exception {
        // Get the lobby
        restLobbyMockMvc.perform(get("/api/lobbys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLobby() throws Exception {
        // Initialize the database
        lobbyRepository.saveAndFlush(lobby);

		int databaseSizeBeforeUpdate = lobbyRepository.findAll().size();

        // Update the lobby

        restLobbyMockMvc.perform(put("/api/lobbys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lobby)))
                .andExpect(status().isOk());

        // Validate the Lobby in the database
        List<Lobby> lobbys = lobbyRepository.findAll();
        assertThat(lobbys).hasSize(databaseSizeBeforeUpdate);
        Lobby testLobby = lobbys.get(lobbys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteLobby() throws Exception {
        // Initialize the database
        lobbyRepository.saveAndFlush(lobby);

		int databaseSizeBeforeDelete = lobbyRepository.findAll().size();

        // Get the lobby
        restLobbyMockMvc.perform(delete("/api/lobbys/{id}", lobby.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lobby> lobbys = lobbyRepository.findAll();
        assertThat(lobbys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
