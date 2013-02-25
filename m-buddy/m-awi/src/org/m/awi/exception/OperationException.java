package org.m.awi.exception;

public class OperationException extends Exception {

	private static final long serialVersionUID = 1L;

	public OperationException() {
		super();
	}

	public OperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public OperationException(String arg0) {
		super(arg0);

	}

	public OperationException(Throwable arg0) {
		super(arg0);
	}
}
