package com.transferwise.banks.demo.quote;

import org.junit.Test;

import java.util.List;

import static com.transferwise.banks.demo.support.Fabricator.bankTransfer;
import static com.transferwise.banks.demo.support.Fabricator.swift;
import static org.junit.Assert.assertTrue;

public class PaymentOptionServiceTest {

    private PaymentOptionService paymentOptionService = new PaymentOptionService();

    @Test
    public void bankTransferIsTheDefaultPaymentOption() {
        final var quote = new Quote(List.of(bankTransfer(), swift()));
        final var bestPaymentOption = paymentOptionService.getBestPaymentOption(quote, "BANK_TRANSFER");

        assertTrue(bestPaymentOption.isBankTransfer());
    }

    @Test
    public void swiftIsTheBestToRecipientTypeSwift() {
        final var quote = new Quote(List.of(bankTransfer(), swift()));
        final var bestPaymentOption = paymentOptionService.getBestPaymentOption(quote, "SWIFT");

        assertTrue(bestPaymentOption.isSwift());
    }

    @Test
    public void usesBankTransferWhenSwiftOptionIsNotAvailable() {
        final var quote = new Quote(List.of(bankTransfer()));
        final var bestPaymentOption = paymentOptionService.getBestPaymentOption(quote, "SWIFT");

        assertTrue(bestPaymentOption.isBankTransfer());
    }
}