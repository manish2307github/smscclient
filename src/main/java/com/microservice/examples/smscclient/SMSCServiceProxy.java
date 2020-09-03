package com.microservice.examples.smscclient;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 
 * FILE_NAME: SMSCServiceProxy.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author egrgmsh, Date: Aug 31, 2020 6:10:01 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */

//@FeignClient(name="SMSCSERVICE", url="localhost:8788")
//@FeignClient(name="SMSCSERVICE")
@FeignClient(name="netflix-zuul-api-gateway")
@RibbonClient(name="SMSCSERVICE")
public interface SMSCServiceProxy {
	
//	@GetMapping("/sendsms/{message}/{mobileNum}")
	@GetMapping("/smscservice/sendsms/{message}/{mobileNum}")
	public String sendsms(@PathVariable("message") String message, @PathVariable("mobileNum") String mobileNum); 
}
