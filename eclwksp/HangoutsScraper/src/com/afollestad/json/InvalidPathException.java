package com.afollestad.json;

/**
 * @author Aidan Follestad (afollestad)
 */
public class InvalidPathException extends IllegalArgumentException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6876952740908702672L;

	InvalidPathException(String message) {
        super(message);
    }
}
