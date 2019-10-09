package com.transferwise.banks.demo.quote;

import org.junit.Test;

import java.util.List;

import static com.transferwise.banks.demo.support.Fabricator.bankTransfer;
import static com.transferwise.banks.demo.support.Fabricator.swift;
import static org.junit.Assert.assertTrue;

public class BestPaymentOptionTest {

    @Test
    public void bankTransferIsTheDefaultPaymentOption() {
        final var quote = new Quote(List.of(bankTransfer(), swift()));
        final var bestPaymentOption = new BestPaymentOption(quote, "BANK_TRANSFER");

        assertTrue(bestPaymentOption.get().isBankTransfer());
    }

    @Test
    public void swiftIsTheBestToRecipientTypeSwift() {
        final var quote = new Quote(List.of(bankTransfer(), swift()));
        final var bestPaymentOption = new BestPaymentOption(quote, "SWIFT");

        assertTrue(bestPaymentOption.get().isSwift());
    }

    @Test
    public void usesBankTransferWhenSwiftOptionIsNotAvailable() {
        final var quote = new Quote(List.of(bankTransfer()));
        final var bestPaymentOption = new BestPaymentOption(quote, "SWIFT");

        assertTrue(bestPaymentOption.get().isBankTransfer());
    }
}
