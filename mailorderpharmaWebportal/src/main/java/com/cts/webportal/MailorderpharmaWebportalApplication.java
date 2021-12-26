package com.cts.webportal;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableFeignClients
public class MailorderpharmaWebportalApplication implements ServletContextInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MailorderpharmaWebportalApplication.class, args);
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.getSessionCookieConfig().setName("MySession");
    }

}
