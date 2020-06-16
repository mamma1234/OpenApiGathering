package com.klnet.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:schedules.properties")
public class SchedulesProperties {
	@Value("${schedules}")
	private String schedules;
	
	public String getSchedules() {
		return schedules;
	}
}
