package bg.softuni.bikes_shop.controller.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.concurrent.TimeUnit;

import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class CommentsRestControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Value("${comments-server.schema}")
    private String shema;
    @Value("${comments-server.port}")
    private int port;
    @Value("${comments-server.host}")
    private String host;
    @Value("${comments-server.path}")
    private String path;
    @Value("${comments-server.enabled}")
    private boolean enabled;

    private ClientAndServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = startClientAndServer(port);

    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
    }

    @Test
    void testGetOneComment() throws Exception {

        new MockServerClient(host, port)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/1")
                                .withHeader("Content-type", "application/json")
//                                .withBody(json("{\"username\": \"foo\", \"password\": \"bar\"}"))
                        , exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
//                                .withHeaders(
//                                        new Header("Content-Type", "application/json; charset=utf-8"),
//                                        new Header("Cache-Control", "public, max-age=86400"))
//                                .withBody("{ message: 'incorrect username and password combination' }")
                                .withDelay(TimeUnit.SECONDS, 1));



        mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/1")
                        .with(csrf())
                        ).andExpect(status().isOk());


    }




}