package com.cts.jsr250;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.Data;

@Data
public class SingerWithJSR250 {
	private static final Logger logger = LoggerFactory.getLogger(SingerWithJSR250.class);

	private static final String DEFAULT_NAME = "Eric Clapton";
	private String name;
	private int age = Integer.MIN_VALUE;

	@PostConstruct
	public void init() throws Exception {
		logger.info("Initializing bean");
		if (name == null) {
			logger.info("Using default name");
			name = DEFAULT_NAME;
		}
		if (age == Integer.MIN_VALUE) {
			throw new IllegalArgumentException(
					"You must set the age property of any beans of type " + SingerWithJSR250.class);
		}
	}

	public static SingerWithJSR250 getBean(String beanName, ApplicationContext ctx) {
		try {
			SingerWithJSR250 bean = (SingerWithJSR250) ctx.getBean(beanName);
			logger.info("{}", bean);
			return bean;
		} catch (BeanCreationException ex) {
			logger.info("An error occured in bean configuration: " + ex.getMessage());
			return null;
		}
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
		getBean("singerOne", ctx);
		getBean("singerTwo", ctx);
		getBean("singerThree", ctx);
	}

}
