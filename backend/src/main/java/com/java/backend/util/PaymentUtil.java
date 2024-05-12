package com.java.backend.util;

import com.java.backend.request.ExecutePaypalRequest;

public interface PaymentUtil {
	String createPaypalPayment(Double total, String currency);

	boolean isExecutePaypalPayment(ExecutePaypalRequest executePaypalRequest);
}
