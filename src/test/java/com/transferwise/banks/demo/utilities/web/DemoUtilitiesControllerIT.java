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
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void shouldCreateANewCustomerWithTheRightData() throws Exception {
        ResultActions resultActionsPost = mockMvc.perform(post("/demo/new-customer").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        String customerID = resultActionsPost.andReturn().getResponse().getContentAsString();

        ResultActions resultActionsGet = mockMvc.perform(get("/customers?id=".concat(customerID)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.valueOf(customerID))))
                .andExpect(jsonPath("$.firstName", is("Test")))
                .andExpect(jsonPath("$.lastName", is("Name")))
                .andExpect(jsonPath("$.dateOfBirth", stringContainsInOrder(List.of("19","-"))))
                .andExpect(jsonPath("$.phoneNumber", startsWith("07")))
                .andExpect(jsonPath("$.email", stringContainsInOrder(List.of("test.reference.email", "@gmail.com"))));
    }
}
