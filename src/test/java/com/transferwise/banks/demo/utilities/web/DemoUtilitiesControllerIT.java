package com.transferwise.banks.demo.utilities.web;

import com.transferwise.banks.demo.T4bBackendApplication;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = T4bBackendApplication.class)
@AutoConfigureMockMvc
@DirtiesContext
@RunWith(SpringRunner.class)
public class DemoUtilitiesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnBankCustomerIDWithLinkedAccountFalse() throws Exception {
        mockMvc.perform(post("/demo/new-customer").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.any(Integer.class)));
    }
}
