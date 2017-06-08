package com.afollestad.json;

/**
 * @author Aidan Follestad (afollestad)
 */
public class InvalidJsonException extends IllegalArgumentException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6007326757184662136L;

	InvalidJsonException(String json, Exception inner) {
        super("Invalid JSON: " + json, inner);
    }
}
