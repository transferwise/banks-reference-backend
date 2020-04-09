package com.transferwise.banks.demo.utilities.web;

import org.junit.Test;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
public class DemoUtilitiesTest {

    private final DemoUtilities sut = new DemoUtilities();

    @Test
    public void shouldGenerateAValidPhoneNumber(){
        String aPhoneNumber = sut.randomizePhone();

        assertThat(aPhoneNumber).isNotNull();
        assertThat(aPhoneNumber.length()).isEqualTo(13);
        assertThat(aPhoneNumber.substring(0,4)).isEqualTo("+447");
    }

    @Test
    public void shouldGenerateAValidEmailAddress(){
        String anEmail = sut.randomizeEmail();

        assertThat(anEmail).isNotNull();

        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$"); //Email regex

        assertThat(pattern.matcher(anEmail).matches()).isTrue();
    }

    @Test
    public void shouldGenerateAValidDOB(){
        LocalDate aDOB = sut.randomizeDOB(); //LocalDate includes all range validations

        assertThat(aDOB).isNotNull();
        assertThat(aDOB.getYear()).isGreaterThanOrEqualTo(1900);
        assertThat(aDOB.getYear()).isLessThan(2000);
    }



}
