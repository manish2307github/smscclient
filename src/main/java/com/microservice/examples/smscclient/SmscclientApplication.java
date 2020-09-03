package com.microservice.examples.smscclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.microservice.examples.smscclient")
@EnableHystrix
public class SmscclientApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmscclientApplication.class, args);
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}


@RestController
class SMSCClient {
	
	private static Logger LOG  = LogManager.getLogger(SMSCClient.class);
	
	@Autowired
	private SMSCServiceProxy smscProxy;
	
	@GetMapping("/health")
	public String healthCheck() {
		LOG.info("health Client SMSC");
		return "Hello message send to smsc client, smsc client up and running";
	}
	
	@GetMapping("/sendsmsclient/{message}/{mobileNum}")
	public String sendsmsclient(@PathVariable String message, @PathVariable String mobileNum) {
		LOG.info("Client sendsmsclient triggering request");
		return smscProxy.sendsms(message, mobileNum);
	}
	
	@GetMapping("/fault-toleration")
	@HystrixCommand(fallbackMethod = "reliable")
	public String faultTolerartion() {
		LOG.info("faultTolerartion SMSC CLIENT");
		throw new RuntimeException("Service is down, please try after some time");
	}
	
	public String reliable() {
		LOG.info("reliable");
		return "fallback method smsc client, please try after sometime";
	}
}
