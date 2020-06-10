package com.klnet.application;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Configuration
@Component
public class CargoSmartService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OwnerDAO ownerDAO;

	@Autowired
	CnediDAO cnediDAO;

	@Value("${cargosmart.appKey.COSU}")
	private String prosAppKeyCOSU;
	@Value("${cargosmart.url.schedules}")
	private String prosUrlSchedules;
	@Value("${cargosmart.url.cargoes}")
	private String prosUrlCargoes;
	
	
	
//	@ConfigurationProperties(prefix="cargosmart")
//	public void cargosmart() {
//		System.out.println("set....");
//		String appKey;
//	}
	
	
	
//	public void appKey(String appKey) {
//		System.out.println("appKey=" + appKey);
//	}


	/*
	 * https://apis.cargosmart.com/openapi/schedules/routeschedules/COSU?appKey=69338670-9c07-11ea-ae83-7956f3992d4c&porID=KRPUS&fndID=USLAX
	 * https://apis.cargosmart.com/openapi/cargoes/tracking/COSU?appKey=69338670-9c07-11ea-ae83-7956f3992d4c&cntrNumber=FCIU5812619&scacCode=COSU 
	 */
	
			
			
	@Scheduled(fixedDelay = 1000 * 60 * 2, initialDelay = 1000)
	public void selectPostgresqlToOracle() throws InterruptedException {

		System.out.println(prosAppKeyCOSU);
		
		Calendar calendar = new GregorianCalendar();

		int hour = calendar.get(Calendar.HOUR); // 12 hour clock
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int millisecond = calendar.get(Calendar.MILLISECOND);

		logger.info("START Postgressql To Oracle " + hour + ":" + minute + ":" + second + "." + millisecond);

		// String ret = ownerDAO.selectPostgresqlToOracle();

		logger.info("End Postgresql To Oracle:");

	}

}
