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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = T4bBackendApplication.class)
@AutoConfigureMockMvc
@DirtiesContext
@RunWith(SpringRunner.class)
public class AddressControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturnBankCustomerAddress() throws Exception {
        mockMvc.perform(get("/customers/addresses?customerId=888").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstLine", is("1 Random Walk")))
                .andExpect(jsonPath("$.city", is("New-York")))
                .andExpect(jsonPath("$.postCode", is("34567")))
                .andExpect(jsonPath("$.state", is("New-York")))
                .andExpect(jsonPath("$.country", is("US")))
                .andExpect(jsonPath("$.customerId", is(888)));
    }

    @Test
    public void shouldReturnBankCustomerAddressById() throws Exception {
        mockMvc.perform(get("/customers/addresses/address?id=888").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstLine", is("1 Random Walk")))
                .andExpect(jsonPath("$.city", is("New-York")))
                .andExpect(jsonPath("$.postCode", is("34567")))
                .andExpect(jsonPath("$.state", is("New-York")))
                .andExpect(jsonPath("$.country", is("US")))
                .andExpect(jsonPath("$.customerId", is(888)));
    }

    @Test
    public void shouldCreateBankCustomerAddress() throws Exception {
        String newCustomerRequestJson = "{\"firstLine\":\"9, rue du Prado\",\"city\":\"Marseille\",\"postCode\":\"13009\",\"state\":\"\",\"country\":\"FR\",\"customerId\":\"999\"}";

        mockMvc.perform(post("/customers/addresses").contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerRequestJson))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/customers/addresses?customerId=999").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstLine", is("9, rue du Prado")))
                .andExpect(jsonPath("$.city", is("Marseille")))
                .andExpect(jsonPath("$.postCode", is("13009")))
                .andExpect(jsonPath("$.state", is("")))
                .andExpect(jsonPath("$.country", is("FR")))
                .andExpect(jsonPath("$.customerId", is(999)));
    }

    @Test
    public void shouldReturnNullAddressWhenNoAddressExists() throws Exception {
        mockMvc.perform(get("/customers/addresses?customerId=555").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(nullValue())));
    }

    @Test
    public void shouldReturnBadRequestWhenEmptyCustomerId() throws Exception {
        mockMvc.perform(get("/customers/addresses?customerId=").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}