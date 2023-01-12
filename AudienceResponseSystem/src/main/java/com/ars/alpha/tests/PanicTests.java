package com.ars.alpha.tests;

import com.ars.alpha.AudienceResponseSystemApplication;
import com.ars.alpha.controller.PanicController;
import com.ars.alpha.controller.SessionRoomController;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.testng.AssertJUnit.assertNotNull;


import com.ars.alpha.dao.PanicRepository;
import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.other.Password;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
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
public class PanicTests extends AbstractTransactionalJUnit4SpringContextTests {

    final static JsonFactory jFactory = new JsonFactory();


    @InjectMocks
    PanicController panicController;

    @InjectMocks
    @Autowired
    PanicRepository panicRepository;

    public static Long TEST_SESSION_ID;
    public static Long TEST_USERID_OWNER;
    public static Long TEST_USERID_2;


    //@LocalServerPort
    //private int port;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {

        // Create a new session.


        MvcResult result =  this.mockMvc.perform(get("/session/createSession").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();

        ObjectMapper objMapper = new ObjectMapper();

        MessageTests.TestObj testObj = objMapper.readValue(responseString, MessageTests.TestObj.class);

        TEST_SESSION_ID = testObj.newSessionID;
        TEST_USERID_OWNER = testObj.newUserID;

        // Add users to that session

        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("password", testObj.randomPassword);
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        String jsonString = writer.toString();

        // Get the second user

        result = this.mockMvc.perform(post("/session/joinSession").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        responseString = result.getResponse().getContentAsString();

        testObj = objMapper.readValue(responseString, MessageTests.TestObj.class);

        TEST_USERID_2 = testObj.newUserID;
    }

    @Test
    @Transactional
    public void insertPanic() throws Exception {

        String jsonString = createPanicJSON("2FST", TEST_USERID_2,  TEST_SESSION_ID);

        MvcResult result = this.mockMvc.perform(post("/panic/postPanic").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andReturn();
    }

    @Test
    @Transactional
    public void insertTwoPanics() throws Exception {

        String jsonString = createPanicJSON("2FST", TEST_USERID_2,  TEST_SESSION_ID);

        MvcResult result = this.mockMvc.perform(post("/panic/postPanic").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andReturn();

        jsonString = createPanicJSON("2QIT", TEST_USERID_2,  TEST_SESSION_ID);

        result = this.mockMvc.perform(post("/panic/postPanic").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andReturn();

        jsonString = createResponsesJSON( TEST_SESSION_ID);

        result = this.mockMvc.perform(post("/panic/getPanicResponses").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.PanicResponse", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.PanicResponse", Matchers.hasSize(1)))
                .andReturn();
    }

    private String createPanicJSON(String panicType, Long panicker, Long session) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("panicType");
        jsonGenerator.writeObjectField("panicType", panicType);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("panicker");
        jsonGenerator.writeObjectField("id", String.valueOf(panicker));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("session");
        jsonGenerator.writeObjectField("id", String.valueOf(session));
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        return writer.toString();
    }

    private String createResponsesJSON(Long session) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("id", String.valueOf(session));
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        return writer.toString();
    }

    @Test
    public void assertSetupSuccessful() {
        assertNotNull("TEST_SESSION_ID IS NULL", TEST_SESSION_ID);
        assertNotNull("TEST_USERID_OWNER IS NULL", TEST_USERID_OWNER);
        assertNotNull("TEST_USERID_2 IS NULL", TEST_USERID_2);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TestObj {
        public Long newUserID;
        public Long newSessionID;
        public String randomPassword;

        public Long MessageID;

    }

}
