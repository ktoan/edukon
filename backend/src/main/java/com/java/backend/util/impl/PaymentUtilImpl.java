package com.java.backend.util.impl;

import com.java.backend.exception.InternalServerException;
import com.java.backend.request.ExecutePaypalRequest;
import com.java.backend.util.PaymentUtil;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class PaymentUtilImpl implements PaymentUtil {
	@Value("${front-end.host}")
	private String frontEndUrl;
	private final APIContext apiContext;

	@Override
	public String createPaypalPayment(Double total, String currency) {
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));

		Transaction transaction = new Transaction();
		transaction.setDescription("Payment created at " + new Date() + ", total is " + total + " " + currency + ".");
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(frontEndUrl + "/enroll/cancel");
		redirectUrls.setReturnUrl(frontEndUrl + "/enroll/paypal");
		payment.setRedirectUrls(redirectUrls);
		apiContext.setMaskRequestId(true);

		try {
			Payment createdPayment = payment.create(apiContext);
			for (Links links : createdPayment.getLinks()) {
				if (links.getRel().equals("approval_url")) {
					return links.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			throw new InternalServerException(e.getMessage());
		}
		return null;
	}

	@Override
	public boolean isExecutePaypalPayment(ExecutePaypalRequest executePaypalRequest) {
		Payment payment = new Payment();
		payment.setId(executePaypalRequest.getPaymentId());
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(executePaypalRequest.getPayerId());
		try {
			Payment executedPayment = payment.execute(apiContext, paymentExecute);
			if ("approved".equals(executedPayment.getState())) {
				return true;
			}
		} catch (PayPalRESTException e) {
			throw new InternalServerException(e.getMessage());
		}

		return false;
	}
}
