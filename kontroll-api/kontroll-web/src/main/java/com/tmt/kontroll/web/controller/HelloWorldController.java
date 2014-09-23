package com.tmt.kontroll.web.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmt.kontroll.web.Greeting;

@RestController
public class HelloWorldController {

	private static final String	template	= "Hello, %s!";
	private final AtomicLong		counter		= new AtomicLong();

	@RequestMapping
	public Greeting greeting(@RequestParam(value = "name", required = false, defaultValue = "World") final String name) {
		return new Greeting(this.counter.incrementAndGet(), String.format(template, name));
	}
}
