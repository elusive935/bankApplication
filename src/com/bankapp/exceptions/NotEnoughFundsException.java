package com.bankapp.exceptions;

public class NotEnoughFundsException extends BankException{

	private static final long serialVersionUID = 1L;
	protected float amount;
	public NotEnoughFundsException(float amount) {
		this.amount = amount;
	}
	@Override
	public String getMessage() {
		return "Not Enough Funds "+amount;
	}

}
