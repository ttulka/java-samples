package com.ttulka.exercise;

import java.io.Serializable;
import java.util.Date;

/**
 * The encryption can be very simple, we don't put much emphasis on the
 * encryption algorithm.
 */
public class Account implements Serializable {

	private int id;

	private String username;

	private byte[] encryptedPassword;

	private String salt;

	private String email;

	private Date lastLogin;

}
