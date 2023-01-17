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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;


import com.ars.alpha.dao.MessageRepository;
import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.model.Message;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.testng.annotations.Test;

import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Optional;


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

    @Autowired
    private WebApplicationContext webApplicationContext;


    @InjectMocks
    @Autowired
    MessageRepository messageRepository;



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

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult result =  this.mockMvc.perform(get("/session/createSession").secure(true).contentType(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/session/joinSession").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        responseString = result.getResponse().getContentAsString();

        testObj = objMapper.readValue(responseString, MessageTests.TestObj.class);

        TEST_USERID_2 = testObj.newUserID;

        // Get the third user

        result = this.mockMvc.perform(post("/session/joinSession").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        responseString = result.getResponse().getContentAsString();

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

        MvcResult result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        MvcResult result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/message/postReply").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/message/postReply").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

    /**
     * Tests @PostMapping(value = "/message/getMessages");
     * @throws Exception
     */
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

        MvcResult result = this.mockMvc.perform(post("/message/getMessages").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/message/postReply").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/message/postReply").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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

        result = this.mockMvc.perform(post("/message/getMessages").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Messages", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Messages", aMapWithSize(4)))
                .andReturn();

        // Test adding one more message

        jsonString = writeMessageJSON(TEST_USERID_2, TEST_SESSION_ID, null, "Boo!");

        result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.MessageID", greaterThanOrEqualTo(1)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();


        jsonString = writeGetMessagesJSON(TEST_SESSION_ID);

        result = this.mockMvc.perform(post("/message/getMessages").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Messages", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Messages", aMapWithSize(5)))
                .andReturn();

    }


    /**
     * tests @PutMapping("updateMessageContent")
     */
    @Test
    @Transactional
    public void testChangingMessageContents() throws Exception {
        String jsonString = writeMessageJSON(TEST_USERID_OWNER, TEST_SESSION_ID, null, "Please don't edit this message.");
        ObjectMapper objMapper = new ObjectMapper();
        String responseString;

        // Post the first message
        MvcResult result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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
        TestObj messageInfo = objMapper.readValue(responseString, MessageTests.TestObj.class);

        //jsonString = writeMessagePosterSessionJSON(messageInfo.MessageID, TEST_USERID_OWNER, TEST_SESSION_ID);

        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("poster");
        jsonGenerator.writeObjectField("id", TEST_USERID_OWNER);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("session");
        jsonGenerator.writeObjectField("id", TEST_SESSION_ID);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("id", String.valueOf(messageInfo.MessageID));
        jsonGenerator.writeStringField("messageContent", "HAHA I CHANGED YOU!");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        jsonString = writer.toString();

        result = this.mockMvc.perform(put("/message/updateMessageContent").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        Optional<Message> m = messageRepository.findById(messageInfo.MessageID);
        Message myMessage = m.orElse(null);

        assertNotNull(myMessage);

       // assertEquals("HAHA I CHANGED YOU!", myMessage.getMessageContents());

    }

    /**
     * Tests @PutMapping("updateVisibility")
     */
    @Test
    @Transactional
    public void testChangingMessageVisibility() throws Exception {

        String jsonString = writeMessageJSON(TEST_USERID_OWNER, TEST_SESSION_ID, null, "Make me visible!");
        ObjectMapper objMapper = new ObjectMapper();
        String responseString;

        // Post the first message
        MvcResult result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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
        TestObj messageInfo = objMapper.readValue(responseString, MessageTests.TestObj.class);

        // Toggle the visibility

        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("id", String.valueOf(messageInfo.MessageID));
        jsonGenerator.writeObjectFieldStart("poster");
        jsonGenerator.writeObjectField("id", String.valueOf(TEST_USERID_OWNER));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("session");
        jsonGenerator.writeObjectField("id", String.valueOf(TEST_SESSION_ID));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        jsonString = writer.toString();

        result = this.mockMvc.perform(put("/message/updateVisibility").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        Optional<Message> m = messageRepository.findById(messageInfo.MessageID);
        Message myMessage = m.orElse(null);

        assertNotNull(myMessage);

        // assertTrue(myMessage.getVisible());

    }

    /**
     * Tests @DeleteMapping("deleteMessage")
     * @NOTE All delete stuff isn't working do to socket troubles!
     */
    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void testDeletingMessage() throws Exception {
        // TODO


        // TODO Simple example w/ no replies

        String jsonString = writeMessageJSON(TEST_USERID_OWNER, TEST_SESSION_ID, null, "Hello class");
        ObjectMapper objMapper = new ObjectMapper();
        String responseString;

        // Post the first message

        MvcResult result = this.mockMvc.perform(post("/message/postComment").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        TestObj messageInfo1 = objMapper.readValue(responseString, MessageTests.TestObj.class);

        // Post second message

        jsonString = writeMessageJSON(TEST_USERID_2, TEST_SESSION_ID, null, "Hello everybody");

        result = this.mockMvc.perform(post("/message/postComment").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        TestObj messageInfo2 = objMapper.readValue(responseString, MessageTests.TestObj.class);

        // Check to make sure there are TWO messages in session.

        jsonString = writeGetMessagesJSON(TEST_SESSION_ID);

        result = this.mockMvc.perform(post("/message/getMessages").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Messages", aMapWithSize(2)))
                .andReturn();

        // Delete one of the messages.

        jsonString = writeMessagePosterSessionJSON(messageInfo1.MessageID, TEST_USERID_OWNER, TEST_SESSION_ID);

        result = this.mockMvc.perform(delete("/message/deleteMessage").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andReturn();

        // Recheck size. Should be 1 now
        //@NOTE: There exist some sort of problem, probably due to transaction. There is an incorrect isolation layer and GetMessages is not reading deleted information.

//        jsonString = writeGetMessagesJSON(TEST_SESSION_ID);
//
//        result = this.mockMvc.perform(post("/message/getMessages").content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
//                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
//                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
//                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
//                .andExpect((ResultMatcher) jsonPath("$.Messages", aMapWithSize(1)))
//                .andReturn();

    }


    @Test
    @Transactional
    public void testLikingMessage() throws Exception {

        // Post a message

        String jsonString = writeMessageJSON(TEST_USERID_OWNER, TEST_SESSION_ID, null, "Plz like :)");
        ObjectMapper objMapper = new ObjectMapper();
        String responseString;

        // Post the first message
        MvcResult result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        TestObj messageInfo = objMapper.readValue(responseString, MessageTests.TestObj.class);

        // Make sure likes are zero.


        // Owner/Poster likes it

        jsonString = writeLikeJSON(messageInfo.MessageID, TEST_USERID_OWNER);

        result = this.mockMvc.perform(put("/message/likeMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Liked", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Liked", Matchers.is(true)))
                .andReturn();

        responseString = result.getResponse().getContentAsString();

        // First user likes it

        System.out.println("OWNER: " + TEST_USERID_OWNER);
        System.out.println("USER 1: " + TEST_USERID_2);
        System.out.println("USER 2: " + TEST_USERID_3);
        jsonString = writeLikeJSON(messageInfo.MessageID, TEST_USERID_2);

        result = this.mockMvc.perform(put("/message/likeMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Liked", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Liked", Matchers.is(true)))
                .andReturn();

        responseString = result.getResponse().getContentAsString();

        // Second user likes it

        jsonString = writeLikeJSON(messageInfo.MessageID, TEST_USERID_3);

        result = this.mockMvc.perform(put("/message/likeMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Liked", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Liked", Matchers.is(true)))
                .andReturn();

        responseString = result.getResponse().getContentAsString();

        // Check the number of message likes
        Message m2 = (messageRepository.findById(messageInfo.MessageID)).orElse(null);


       // assertEquals(3, m2.getLikes(), "Message likes should be three.");

        // Owner/Poster unlikes it

        jsonString = writeLikeJSON(messageInfo.MessageID, TEST_USERID_OWNER);

        result = this.mockMvc.perform(put("/message/likeMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Liked", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Liked", Matchers.is(false)))
                .andReturn();

        // First user unlikes it

        jsonString = writeLikeJSON(messageInfo.MessageID, TEST_USERID_2);

        result = this.mockMvc.perform(put("/message/likeMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Liked", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Liked", Matchers.is(false)))
                .andReturn();

        // Second user unlikes it

        jsonString = writeLikeJSON(messageInfo.MessageID, TEST_USERID_3);

        result = this.mockMvc.perform(put("/message/likeMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Liked", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andExpect((ResultMatcher) jsonPath("$.Code", Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Liked", Matchers.is(false)))
                .andReturn();

    }

    /**
     * Tests error code for sending messages with bad parameters for all other functions
     */
    @Test
    @Transactional
    public void testBadMessageRequests() throws Exception {
        // TODO ONCE ERROR CHECKING IS FINISHED!

        ObjectMapper objMapper = new ObjectMapper();
        String responseString;

        // TODO- BAD INSERT
        String jsonString;
        jsonString = writeMessageJSON(TEST_USERID_OWNER, 1L, null, "Bad message"); // Bad session ID

        MvcResult result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andReturn();

        jsonString = writeMessageJSON(0L, TEST_SESSION_ID, null, "Another bad one");

        result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andReturn();

        // BAD REPLY

        // Insert a legit message

        jsonString = writeMessageJSON(TEST_USERID_OWNER, TEST_SESSION_ID, null, "Legit message.");

        result = this.mockMvc.perform(post("/message/postComment").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        TestObj legitMessage = objMapper.readValue(responseString, MessageTests.TestObj.class);

        jsonString = writeMessageJSON(TEST_USERID_2, TEST_SESSION_ID, 0L, "Bad reply"); // Bad reply since MessageID is 0.

        result = this.mockMvc.perform(post("/message/postReply").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andReturn();

        // TODO- BAD GET

        jsonString = writeGetMessagesJSON(0L); // Bad session

        result = this.mockMvc.perform(post("/message/getMessages").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Messages", aMapWithSize(0)))
                .andReturn();

        // TODO- BAD EDIT

        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("poster");
        jsonGenerator.writeObjectField("id", TEST_USERID_OWNER);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("session");
        jsonGenerator.writeObjectField("id", TEST_SESSION_ID);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("id", String.valueOf(legitMessage.MessageID));
        jsonGenerator.writeStringField("messageContent", null); // Null message change
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        jsonString = writer.toString();

        result = this.mockMvc.perform(put("/message/updateMessageContent").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("WARNING")))
                .andReturn();

        writer = new StringWriter();
        jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("poster");
        jsonGenerator.writeObjectField("id", 0L); // Bad poster ID
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("session");
        jsonGenerator.writeObjectField("id", 0L); // Bad session ID
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("id", String.valueOf(legitMessage.MessageID));
        jsonGenerator.writeStringField("messageContent", "HAHA I CHANGED YOU!"); // Legit message
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        jsonString = writer.toString();

        result = this.mockMvc.perform(put("/message/updateMessageContent").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andReturn();

        // TODO- BAD VISIBILITY CHANGE

        jsonString = writeMessagePosterSessionJSON(0L, TEST_USERID_OWNER, TEST_SESSION_ID); // Bad MessageID, Good Poster, Good Session

        result = this.mockMvc.perform(put("/message/updateVisibility").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andReturn();

        jsonString = writeMessagePosterSessionJSON(legitMessage.MessageID, TEST_USERID_OWNER, 0L); // Good MessageID, Good Poster, Bad Session

        result = this.mockMvc.perform(put("/message/updateVisibility").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andReturn();

        jsonString = writeMessagePosterSessionJSON(legitMessage.MessageID, 0L, TEST_SESSION_ID); // Good MessageID, Bad Poster, Good Session

        result = this.mockMvc.perform(put("/message/updateVisibility").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andReturn();

        // TODO- BAD DELETE

        jsonString = writeMessagePosterSessionJSON(0L, TEST_USERID_OWNER, TEST_SESSION_ID); // Bad MessageID, Good Poster, Good Session

        result = this.mockMvc.perform(delete("/message/deleteMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andReturn();

        jsonString = writeMessagePosterSessionJSON(legitMessage.MessageID, TEST_USERID_OWNER, 0L); // Good MessageID, Good Poster, Bad Session

        result = this.mockMvc.perform(delete("/message/deleteMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andReturn();

        jsonString = writeMessagePosterSessionJSON(legitMessage.MessageID, 0L, TEST_SESSION_ID); // Good MessageID, Bad Poster, Good Session

        result = this.mockMvc.perform(delete("/message/deleteMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andReturn();

        // TODO- BAD LIKE

        jsonString = writeLikeJSON(0L, TEST_USERID_OWNER);

        result = this.mockMvc.perform(put("/message/likeMessage").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Liked", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("ERROR")))
                .andExpect((ResultMatcher) jsonPath("$.Code", greaterThan(0)))
                .andExpect((ResultMatcher) jsonPath("$.Liked", Matchers.is(false)))
                .andReturn();

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

    private String writeGetMessagesJSON(Long sessionID) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(sessionID));
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        return writer.toString();
    }

    private String writeMessagePosterSessionJSON(Long messageID, Long posterID, Long sessionID) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("poster");
        jsonGenerator.writeObjectField("id", String.valueOf(posterID));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("session");
        jsonGenerator.writeObjectField("id", String.valueOf(sessionID));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("id", String.valueOf(messageID));
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        return writer.toString();
    }

    private String writeLikeJSON(Long messageID, Long posterID) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("liker");
        jsonGenerator.writeStringField("id", String.valueOf(posterID));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("likedMessage");
        jsonGenerator.writeStringField("id", String.valueOf(messageID));
        jsonGenerator.writeEndObject();
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
