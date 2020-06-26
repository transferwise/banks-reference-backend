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
public class CustomersControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturnBankCustomer() throws Exception {
        mockMvc.perform(get("/customers?id=777").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(777)))
                .andExpect(jsonPath("$.firstName", is("Bank")))
                .andExpect(jsonPath("$.lastName", is("Customer 1")))
                .andExpect(jsonPath("$.dateOfBirth", is("1980-01-01")))
                .andExpect(jsonPath("$.phoneNumber", is("+37211223344")))
                .andExpect(jsonPath("$.email", is("bank-customer-1@bank.com")))
                .andExpect(jsonPath("$.address.firstLine", is("56 Shoreditch High Street")))
                .andExpect(jsonPath("$.address.city", is("London")))
                .andExpect(jsonPath("$.address.postCode", is("EC1 6JJ")))
                .andExpect(jsonPath("$.address.state", is("")))
                .andExpect(jsonPath("$.address.country", is("GB")))
                .andExpect(jsonPath("$.address.customerId", is(777)))
                .andExpect(jsonPath("$.address.occupations").isArray())
                .andExpect(jsonPath("$.address.occupations", hasSize(1)))
                .andExpect(jsonPath("$.address.occupations[0].code", is("QA Lead")))
                .andExpect(jsonPath("$.address.occupations[0].format", is("FREE_FORM")));
    }

    @Test
    public void shouldReturnBankCustomerWithLinkedAccountTrue() throws Exception {
        mockMvc.perform(get("/customers?id=777").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transferWiseAccountLinked", is(true)));
    }

    @Test
    public void shouldReturnBankCustomerWithLinkedAccountFalse() throws Exception {
        mockMvc.perform(get("/customers?id=999").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transferWiseAccountLinked", is(false)));
    }

    @Test
    public void shouldCreateBankCustomer() throws Exception {
        String newCustomerRequestJson = "{\n" +
                "    \"firstName\":\"test\",\n" +
                "    \"lastName\":\"customer\",\n" +
                "    \"email\":\"test@test.com\",\n" +
                "    \"dateOfBirth\":\"1950-05-05\",\n" +
                "    \"phoneNumber\":\"+37211223344\",\n" +
                "    \"address\": {\n" +
                "        \"firstLine\": \"56, Shoreditch High Street\",\n" +
                "        \"postCode\": \"EC1V 6JJ\",\n" +
                "        \"city\": \"London\", \n" +
                "        \"state\": \"\",\n" +
                "        \"country\": \"GB\",\n" +
                "        \"occupations\": [{\n" +
                "            \"code\": \"Clown\",\n" +
                "            \"format\": \"FREE_FORM\"\n" +
                "        }]\n" +
                "    }\n" +
                "}";

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerRequestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        mockMvc.perform(get("/customers?id=1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("test")))
                .andExpect(jsonPath("$.lastName", is("customer")))
                .andExpect(jsonPath("$.dateOfBirth", is("1950-05-05")))
                .andExpect(jsonPath("$.phoneNumber", is("+37211223344")))
                .andExpect(jsonPath("$.email", is("test@test.com")))
                .andExpect(jsonPath("$.address.id", is(2)))
                .andExpect(jsonPath("$.address.firstLine", is("56, Shoreditch High Street")))
                .andExpect(jsonPath("$.address.city", is("London")))
                .andExpect(jsonPath("$.address.postCode", is("EC1V 6JJ")))
                .andExpect(jsonPath("$.address.state", is("")))
                .andExpect(jsonPath("$.address.country", is("GB")))
                .andExpect(jsonPath("$.address.occupations").isArray())
                .andExpect(jsonPath("$.address.occupations", hasSize(1)))
                .andExpect(jsonPath("$.address.occupations[0].code", is("Clown")))
                .andExpect(jsonPath("$.address.occupations[0].format", is("FREE_FORM")));
    }

    @Test
    public void shouldReturnBadRequestWhenEmptyFirstName() throws Exception {
        String newCustomerRequestJson = "{\"lastName\":\"customer\",\"email\":\"test@test.com\",\"dateOfBirth\":\"1950-05-05\",\"phoneNumber\":\"+37211223344\"}";

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerRequestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenEmptyLastName() throws Exception {
        String newCustomerRequestJson = "{\"firstName\":\"test\",\"email\":\"test@test.com\",\"dateOfBirth\":\"1950-05-05\",\"phoneNumber\":\"+37211223344\"}";

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerRequestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenEmptyEmail() throws Exception {
        String newCustomerRequestJson = "{\"firstName\":\"test\",\"lastName\":\"customer\",\"dateOfBirth\":\"1950-05-05\",\"phoneNumber\":\"+37211223344\"}";


        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerRequestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenEmptyDateOfBirth() throws Exception {
        String newCustomerRequestJson = "{\"firstName\":\"test\",\"lastName\":\"customer\",\"email\":\"test@test.com\",\"phoneNumber\":\"+37211223344\"}";

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerRequestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenEmptyPhoneNumber() throws Exception {
        String newCustomerRequestJson = "{\"firstName\":\"test\",\"lastName\":\"customer\",\"email\":\"test@test.com\",\"dateOfBirth\":\"1950-05-05\"}";

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerRequestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}