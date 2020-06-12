package com.transferwise.banks.demo.customer.web;

import com.transferwise.banks.demo.T4bBackendApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = T4bBackendApplication.class)
@AutoConfigureMockMvc
@DirtiesContext
@RunWith(SpringRunner.class)
public class OccupationControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturnBankCustomerOccupationByAddressId() throws Exception {
        mockMvc.perform(get("/customers/addresses/occupations?addressId=888").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$"), hasSize(1)))
                .andExpect(jsonPath("$.[0].code", is("Compliance Manager")))
                .andExpect(jsonPath("$.[0].format", is("FREE_FORM")))
                .andExpect(jsonPath("$.[0].addressId", is(888)));
    }

    @Test
    public void shouldCreateBankCustomerOccupation() throws Exception {
        String newCustomerRequestJson = "{\"code\":\"Circus Master\",\"format\":\"FREE_FORM\",\"addressId\":999}";

        mockMvc.perform(post("/customers/addresses/occupations").contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerRequestJson))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/customers/addresses/occupations?addressId=999").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$"), hasSize(1)))
                .andExpect(jsonPath("$.[0].code", is("Circus Master")))
                .andExpect(jsonPath("$.[0].format", is("FREE_FORM")))
                .andExpect(jsonPath("$.[0].addressId", is(999)));
    }

    @Test
    public void shouldReturnEmptyOccupationWhenNoOccupationExists() throws Exception {
        mockMvc.perform(get("/customers/addresses/occupations?addressId=5555").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$"), hasSize(0)));
    }

    @Test
    public void shouldReturnBadRequestWhenEmptyAddressId() throws Exception {
        mockMvc.perform(get("/customers/addresses/occupations?addressId=").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}