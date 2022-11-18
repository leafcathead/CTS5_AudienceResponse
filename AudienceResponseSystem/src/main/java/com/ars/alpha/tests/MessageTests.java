package com.ars.alpha.tests;

import com.ars.alpha.AudienceResponseSystemApplication;
import com.ars.alpha.controller.MessageController;
import com.ars.alpha.controller.SessionRoomController;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.isEnum;
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
import static org.testng.AssertJUnit.assertTrue;


import com.ars.alpha.dao.MessageRepository;
import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.model.SessionUser;
import com.ars.alpha.other.Password;
import com.ars.alpha.other.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.testng.annotations.Test;

import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.StringWriter;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest @FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class MessageTests extends AbstractTransactionalJUnit4SpringContextTests {

    final static JsonFactory jFactory = new JsonFactory();


    @InjectMocks
    SessionRoomController sessionController;

    @InjectMocks
    MessageController messageController;

    @InjectMocks
    @Autowired
    SessionRepository messageRepository;



    public static Long TEST_SESSION_ID;
    public static Long TEST_USERID_OWNER;
    public static Long TEST_USERID_2;
    public static Long TEST_USERID_3;



    //@LocalServerPort
    //private int port;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    @Transactional
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

        testObj = objMapper.readValue(responseString, MessageTests.TestObj.class);

        TEST_USERID_2 = testObj.newUserID;

        // Get the third user

        result = this.mockMvc.perform(post("/session/joinSession").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        testObj = objMapper.readValue(responseString, MessageTests.TestObj.class);

        TEST_USERID_3 = testObj.newUserID;

    }

    @AfterEach
    @Rollback
    void teardown() {

    }

    @Test
    public void assertSetupSuccessful() {
        assertNotNull("TEST_SESSION_ID IS NULL", TEST_SESSION_ID);
        assertNotNull("TEST_USERID_OWNER IS NULL", TEST_USERID_OWNER);
        assertNotNull("TEST_USERID_2 IS NULL", TEST_USERID_2);
        assertNotNull("TEST_USERID_3 IS NULL", TEST_USERID_3);

    }

    @Test
    @Transactional
    public void insertSeveralMessages() throws Exception {

        String jsonString = writeMessageJSON(TEST_USERID_2, TEST_SESSION_ID, null, "Guten Tag!");

        // Post the first message

        MvcResult result = this.mockMvc.perform(post("/message/postComment").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        // Post the second message

        jsonString = writeMessageJSON(TEST_USERID_3, TEST_SESSION_ID, null, "Wie geht es dir?");

        result = this.mockMvc.perform(post("/message/postComment").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();



        jsonString = writeMessageJSON(TEST_USERID_OWNER, TEST_SESSION_ID, null, "Willkommen zur Vorlesung!");

        result = this.mockMvc.perform(post("/message/postComment").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();



    }



    @Test
    @Transactional
    public void insertSeveralReplies() throws Exception {

        ObjectMapper objMapper = new ObjectMapper();
        String responseString;

        // Post the first message. The one that will be replied to

        String jsonString = writeMessageJSON(TEST_USERID_2, TEST_SESSION_ID, null, "Guten Tag!");

        MvcResult result = this.mockMvc.perform(post("/message/postComment").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        MessageTests.TestObj messageInfo = objMapper.readValue(responseString, MessageTests.TestObj.class);

        // Post the second message as a reply to the first one.

        jsonString = writeMessageJSON(TEST_USERID_3, TEST_SESSION_ID, messageInfo.MessageID, "I am replying to you!");

        result = this.mockMvc.perform(post("/message/postReply").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        messageInfo = objMapper.readValue(responseString, MessageTests.TestObj.class);

        // Make the third comment a reply to the second one.

        jsonString = writeMessageJSON(TEST_USERID_OWNER, TEST_SESSION_ID, messageInfo.MessageID, "Gute Nacht!");

        result = this.mockMvc.perform(post("/message/postReply").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

    }

    @Test
    @Transactional
    public void getMessagesTest() throws Exception {

        ObjectMapper objMapper = new ObjectMapper();
        String responseString;

        // Check for an empty session.

        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(TEST_SESSION_ID));
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        String jsonString = writer.toString();

        MvcResult result = this.mockMvc.perform(get("/message/getMessages").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Messages", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Messages", anEmptyMap()))
                .andReturn();


        // Post the first message. The one that will be replied to

        jsonString = writeMessageJSON(TEST_USERID_2, TEST_SESSION_ID, null, "Guten Tag!");

        result = this.mockMvc.perform(post("/message/postComment").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        MessageTests.TestObj messageInfo = objMapper.readValue(responseString, MessageTests.TestObj.class);

        // Post the second message as a reply to the first one.

        jsonString = writeMessageJSON(TEST_USERID_3, TEST_SESSION_ID, messageInfo.MessageID, "I am replying to you!");

        result = this.mockMvc.perform(post("/message/postReply").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        messageInfo = objMapper.readValue(responseString, MessageTests.TestObj.class);

        // Make the third comment a reply to the second one.

        jsonString = writeMessageJSON(TEST_USERID_OWNER, TEST_SESSION_ID, messageInfo.MessageID, "Gute Nacht!");

        result = this.mockMvc.perform(post("/message/postReply").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        // Insert another regular message

        jsonString = writeMessageJSON(TEST_USERID_OWNER, TEST_SESSION_ID, null, "Okay! That's everything for today.");

        result = this.mockMvc.perform(post("/message/postComment").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        // Now to actually check how many comments there are!

        writer = new StringWriter();
        jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(TEST_SESSION_ID));
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        jsonString = writer.toString();

        result = this.mockMvc.perform(get("/message/getMessages").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Messages", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Messages", aMapWithSize(4)))
                .andReturn();


    }

    @Test
    @Transactional
    public void testBadMessageRequests() {
        // TODO ONCE ERROR CHECKING IS FINISHED!
    }


    /**
     * This helper function writes JSON strings for the messages.
     *
     * @param poster
     * @param session
     * @param replyToID
     * @param message
     * @return
     * @throws IOException
     */
    private String writeMessageJSON(Long poster, Long session, Long replyToID, String message) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("poster");
        jsonGenerator.writeObjectField("id", String.valueOf(poster));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("session");
        jsonGenerator.writeObjectField("id", String.valueOf(session));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("messageContent", message);
        if (replyToID != null) {
            jsonGenerator.writeObjectFieldStart("replyTo");
            jsonGenerator.writeObjectField("id", String.valueOf(replyToID));
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        return writer.toString();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TestObj {
        public Long newUserID;
        public Long newSessionID;
        public String randomPassword;

        public Long MessageID;

    }


}
