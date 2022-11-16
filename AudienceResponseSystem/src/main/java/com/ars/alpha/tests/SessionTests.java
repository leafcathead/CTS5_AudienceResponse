package com.ars.alpha.tests;

import com.ars.alpha.AudienceResponseSystemApplication;
import com.ars.alpha.controller.SessionRoomController;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.testng.annotations.Test;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;



@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest @FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
class SessionTests extends AbstractTransactionalJUnit4SpringContextTests {


    @InjectMocks
    SessionRoomController sessionController;



    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {

    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(sessionController).isNotNull();
    }

    @Test
    @Transactional
    public void creatingSessions() throws Exception {

        this.mockMvc.perform(get("/session/createSession")).andExpect(status().isCreated());

    }

}
