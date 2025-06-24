package com.topicossoftware.marino.upb.edu.api.app.exceptions;

import com.topicossoftware.marino.upb.edu.api.TestConfig;
import com.topicossoftware.marino.upb.edu.api.domain.model.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Dummy controller to trigger the exception
@RestController
@RequestMapping("/test")
class DummyController {
    @GetMapping("/usuario-error")
    public void triggerException() {
        throw new UsuarioServiceException("Sin autorización", "401");
    }
}

@WebMvcTest(controllers = DummyController.class)
@Import({TestConfig.class, RestResponseEntityExceptionHandler.class})
class RestResponseEntityExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHandleUsuarioServiceException() throws Exception {
        mockMvc.perform(get("/test/usuario-error"))
                .andExpect(status().isUnauthorized());
    }
}