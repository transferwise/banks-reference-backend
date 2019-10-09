package com.transferwise.banks.demo.transfer;

import com.transferwise.banks.demo.quote.PaymentOption;
import com.transferwise.banks.demo.quote.Quote;
import com.transferwise.banks.demo.recipient.Recipient;

public class TransferRequest {

    private TransferWiseTransferRequest transferWiseTransfer;
    private PaymentOption paymentOption;
    private Quote quote;
    private Recipient recipient;

    public TransferRequest(TransferWiseTransferRequest transferWiseTransfer, PaymentOption paymentOption, Quote quote, Recipient recipient) {
        this.transferWiseTransfer = transferWiseTransfer;
        this.paymentOption = paymentOption;
        this.quote = quote;
        this.recipient = recipient;
    }

    public TransferWiseTransferRequest getTransferWiseTransfer() {
        return transferWiseTransfer;
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public Quote getQuote() {
        return quote;
    }

    public Recipient getRecipient() {
        return recipient;
    }
}
