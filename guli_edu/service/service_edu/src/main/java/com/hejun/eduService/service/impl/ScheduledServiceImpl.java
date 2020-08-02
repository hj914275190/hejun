package com.hejun.eduService.service.impl;

import org.springframework.stereotype.Service;
import com.hejun.eduService.service.ScheduledService;

@Service
public class ScheduledServiceImpl implements ScheduledService {

	@Override
	//@Scheduled(cron = "0,10,20,30,40 * * * * ? ")
	public void test() {
		
		System.out.println("hello");
	}

}
