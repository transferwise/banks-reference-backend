package com.transferwise.banks.demo.utilities.web;

import java.time.LocalDate;
import java.util.Random;

class DemoUtilities {
    public String randomizePhone() {
        var phoneNo = 0;
        var randomizer = new Random();
        for (int i = 8; i > 0; i--){
            phoneNo += Math.abs(randomizer.nextInt(9)) * Math.pow(10,i);
        }
        return "+447".concat(String.valueOf(phoneNo));
    }

    public LocalDate randomizeDOB() {
        var rDay = 1+Math.abs((new Random()).nextInt(28));
        var rMonth = 1+Math.abs((new Random()).nextInt(11));
        var rYear = 1900+Math.abs((new Random()).nextInt(100));

        return LocalDate.of(rYear, rMonth, rDay);
    }

    public String randomizeEmail() {
        var aPositiveNumber = String.valueOf(Math.abs((new Random()).nextInt(9999)));
        return "test.reference.email+".concat(aPositiveNumber).concat("@gmail.com");
    }
}
