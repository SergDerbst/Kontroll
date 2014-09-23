package com.tmt.kontroll.web;

public class Greeting {

	private final long	id;
	private final String	content;

	public Greeting(final long id,
									final String content) {
		this.id = id;
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public long getId() {
		return this.id;
	}
}
