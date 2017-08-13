package com.bankapp.exceptions;

public class OverDraftLimitExceededException extends NotEnoughFundsException {

	private static final long serialVersionUID = 1L;
	private String account;

	public OverDraftLimitExceededException(String account, float amount) {
		super(amount);
		this.account = account;
	}

	@Override
	public String getMessage() {
		return "Overdraft Limit exceeded on " + this.account + " amount: "
				+ amount;

	}
}
