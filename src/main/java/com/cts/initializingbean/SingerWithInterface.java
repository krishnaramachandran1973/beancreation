package com.cts.initializingbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.Data;

@Data
public class SingerWithInterface implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(SingerWithInterface.class);

	private static final String DEFAULT_NAME = "Eric Clapton";
	private String name;
	private int age = Integer.MIN_VALUE;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("Initializing bean");
		if (name == null) {
			logger.info("Using default name");
			name = DEFAULT_NAME;
		}
		if (age == Integer.MIN_VALUE) {
			throw new IllegalArgumentException(
					"You must set the age property of any beans of type " + SingerWithInterface.class);
		}
	}

	private static SingerWithInterface getBean(String beanName, ApplicationContext ctx) {
		try {
			SingerWithInterface bean = (SingerWithInterface) ctx.getBean(beanName);
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
