package com.tmt.kontroll.content;

import org.springframework.stereotype.Component;


/**
 * The actual component matching given application values against conditions in
 * order to deliver content.
 * 
 * @author Serg Derbst
 * 
 */
@Component
public class ContentVerifier {

	public <C extends ContentContext> boolean verify(C context) {
		//TODO implement the fucker!
		return false;
	}
}
