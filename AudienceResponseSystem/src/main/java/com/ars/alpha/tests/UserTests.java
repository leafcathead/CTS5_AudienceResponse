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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;


import com.ars.alpha.controller.SessionUserController;
import com.ars.alpha.dao.MessageRepository;
import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.dao.UserRepository;
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
public class UserTests extends AbstractTransactionalJUnit4SpringContextTests {

    final static JsonFactory jFactory = new JsonFactory();

    @InjectMocks
    SessionUserController userController;

    @InjectMocks
    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public static Long TEST_SESSION_ID;
    public static Long TEST_USER_ID;

    @BeforeEach
    @Transactional
    void setup() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        // Create a new session to run the test in. Session consists of one user: the owner.

        MvcResult result =  this.mockMvc.perform(get("/session/createSession").secure(true).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();

        ObjectMapper objMapper = new ObjectMapper();

        MessageTests.TestObj testObj = objMapper.readValue(responseString, MessageTests.TestObj.class);

        TEST_SESSION_ID = testObj.newSessionID;
        TEST_USER_ID = testObj.newUserID;

    }

    @AfterEach
    @Rollback
    void teardown() {

    }

    /**
     * Checks to make sure the setup() completed without error.
     */
    @Test
    void testSetupSuccessful() {

        assertNotNull(TEST_SESSION_ID);
        assertNotNull(TEST_USER_ID);
    }

    /**
     * tests PutMapping("/user/updateDisplayName")
     */
    @Test
    @Transactional
    void testUpdateDisplayName() throws Exception {

        String jsonString;
        String responseString;

        // Change username

        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jFactory.createGenerator(writer);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("id", String.valueOf(TEST_USER_ID));
        jsonGenerator.writeObjectFieldStart("session");
        jsonGenerator.writeObjectField("id", String.valueOf(TEST_SESSION_ID));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("displayName", "MY_NEW_NAME");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        jsonString = writer.toString();

        MvcResult result = this.mockMvc.perform(put("/user/updateDisplayName").secure(true).content(jsonString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) jsonPath("$.Code", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Status", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.Code",  Matchers.is(0)))
                .andExpect((ResultMatcher) jsonPath("$.Status", Matchers.is("SUCCESS")))
                .andReturn();

        Optional<SessionUser> su = userRepository.findById(TEST_USER_ID);
        SessionUser user = su.orElse(null);

        assertNotNull(user);
        assertEquals("MY_NEW_NAME", user.getDisplayName());

    }

}
