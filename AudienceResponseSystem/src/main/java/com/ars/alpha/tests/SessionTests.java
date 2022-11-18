package com.ars.alpha.tests;

import com.ars.alpha.AudienceResponseSystemApplication;
import com.ars.alpha.controller.SessionRoomController;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.other.Password;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.testng.annotations.Test;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.StringWriter;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest @FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class SessionTests extends AbstractTransactionalJUnit4SpringContextTests {

    final static JsonFactory jFactory = new JsonFactory();


    @InjectMocks
    SessionRoomController sessionController;

    @InjectMocks
    @Autowired
    SessionRepository sessionRepository;



    //@LocalServerPort
    //private int port;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {

    }


    /**
     * Checks to make sure we can get sessions from the database.
     * @throws Exception
     */
    @Test
    @Transactional
    public void creatingSessionsDatabase() throws Exception {

        this.mockMvc.perform(get("/session/createSession")).andExpect(status().isOk());

    }

    /**
     * Checks to make sure we can successfully create and receive a new session and its new user.
     * @throws Exception
     */
    @Test
    @Transactional
    public void creatingSessionBackend() throws Exception {

        MvcResult result =  this.mockMvc.perform(get("/session/createSession").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect((ResultMatcher) jsonPath("$", anyList())) // How can I check that the length is the same size
                .andExpect((ResultMatcher) jsonPath("$.newUserID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("['newUserID']", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.newSessionID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("['newSessionID']", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.randomPassword", notNullValue()))
                .andReturn();
        String responseString = result.getResponse().getContentAsString();

        TestObj someClass = new ObjectMapper().readValue(responseString, TestObj.class);

        assertEquals(4,  someClass.randomPassword.length());

    }

    @Test
    @Transactional
    public void joiningRealSession() throws Exception {

        // Create session to test.
        MvcResult result =  this.mockMvc.perform(get("/session/createSession").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String responseString = result.getResponse().getContentAsString();

        TestObj sessionInfo = new ObjectMapper().readValue(responseString, TestObj.class);


        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("password", sessionInfo.randomPassword);
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        String jsonString = writer.toString();

        result = this.mockMvc.perform(post("/session/joinSession").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
     //           .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.newUserID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("['newUserID']", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.newSessionID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("['newSessionID']", greaterThanOrEqualTo(1)))
                .andReturn();


    }

    /**
     * Tests that a session that does not exist, returns the correct error codes.
     * @throws Exception
     */
    @Test
    @Transactional
    public void joiningFakeSession() throws Exception {

        final String fakeSessionPass = "XXXX";

        // Use the service to check that a random String does not exist.
        Long sessionID;
        try {
            sessionID = sessionRepository.GET_SESSION_ROOM_ID_FROM_PASSWORD(fakeSessionPass, 0L);
        } catch (PersistenceException e){
            sessionID = 0L;
        }

        assertEquals(0, sessionID);

        // Send a post to join the session, check to make sure you get a 0 for both parameters back.

        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("password", fakeSessionPass);
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        String jsonString = writer.toString();

         this.mockMvc.perform(post("/session/joinSession").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //           .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.userID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("['userID']", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.sessionID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("['sessionID']", Matchers.is(0)))
                .andReturn();

    }

    static class TestObj {
        public Long newUserID;
        public Long newSessionID;
        public String randomPassword;
    }


}
