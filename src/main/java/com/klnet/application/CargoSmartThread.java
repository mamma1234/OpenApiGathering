package com.klnet.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;


//import io.swagger.models.Model;
//import io.swagger.models.Swagger;
//import io.swagger.parser.SwaggerParser;

//@Configuration
@Component
public class CargoSmartThread {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CnediDAO ebizDAO;
	
	@Autowired
	OwnerDAO ownerDAO;


	@Value("${cargosmart.appKey.COSU}")
	private String prosAppKeyCOSU;
	@Value("${cargosmart.originator.COSU}")
	private String prosOriginatorCOSU;
	@Value("${cargosmart.originator.MSCU}")
	private String prosOriginatorMSCU;

	
	
	
	@Value("${cargosmart.url.schedules}")
	private String prosUrlSchedules;
	@Value("${cargosmart.url.tracking}")
	private String prosUrlTracking;
	
	@Autowired
	SchedulesProperties properties;
	/*
	 * https://apis.cargosmart.com/openapi/schedules/routeschedules/COSU?appKey=69338670-9c07-11ea-ae83-7956f3992d4c&porID=KRPUS&fndID=USLAX
	 * https://apis.cargosmart.com/openapi/cargoes/tracking/COSU?appKey=69338670-9c07-11ea-ae83-7956f3992d4c&cntrNumber=FCIU5812619&scacCode=COSU 
	 */
	
			
//	@Scheduled(fixedDelay = 1000 * 60 * 2, initialDelay = 1000)
	@Scheduled(fixedDelay = 1000 * 60 * 2, initialDelay = 1000)
	public void selectCargoTracking() throws InterruptedException {

		System.out.println(this.prosAppKeyCOSU);
		System.out.println(this.prosUrlTracking);
		
		Calendar calendar = new GregorianCalendar();

		int hour = calendar.get(Calendar.HOUR); // 12 hour clock
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int millisecond = calendar.get(Calendar.MILLISECOND);

		logger.info("START Postgressql To Oracle " + hour + ":" + minute + ":" + second + "." + millisecond);

//		list:[{XMLDOC_SEQ=1, EQDCN_8260=BEAU4606400, CONT_SEQ=1, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4555375, CONT_SEQ=2, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4839216, CONT_SEQ=3, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9111516, CONT_SEQ=4, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9320373, CONT_SEQ=5, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8811011, CONT_SEQ=6, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8910966, CONT_SEQ=7, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8968947, CONT_SEQ=8, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9008365, CONT_SEQ=9, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9469475, CONT_SEQ=10, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU6848644, CONT_SEQ=11, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU7724381, CONT_SEQ=12, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CSLU6268966, CONT_SEQ=13, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6101322, CONT_SEQ=14, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6269569, CONT_SEQ=15, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6693957, CONT_SEQ=16, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7113971, CONT_SEQ=17, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7193998, CONT_SEQ=18, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU6179880, CONT_SEQ=19, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU7564937, CONT_SEQ=20, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9140003, CONT_SEQ=21, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9576553, CONT_SEQ=22, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9622290, CONT_SEQ=23, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9843053, CONT_SEQ=24, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=MAGU5362310, CONT_SEQ=25, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7005160, CONT_SEQ=26, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7878391, CONT_SEQ=27, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8391001, CONT_SEQ=28, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8529750, CONT_SEQ=29, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU9259598, CONT_SEQ=30, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1287293, CONT_SEQ=31, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1791283, CONT_SEQ=32, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5140870, CONT_SEQ=33, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5149728, CONT_SEQ=34, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU9616266, CONT_SEQ=35, XML_MSG_ID=20200831170434V391}, {XMLDOC_SEQ=1, EQDCN_8260=BEAU4606400, CONT_SEQ=1, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4555375, CONT_SEQ=2, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4839216, CONT_SEQ=3, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9111516, CONT_SEQ=4, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9320373, CONT_SEQ=5, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8811011, CONT_SEQ=6, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8910966, CONT_SEQ=7, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8968947, CONT_SEQ=8, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9008365, CONT_SEQ=9, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9469475, CONT_SEQ=10, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU6848644, CONT_SEQ=11, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU7724381, CONT_SEQ=12, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSLU6268966, CONT_SEQ=13, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6101322, CONT_SEQ=14, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6269569, CONT_SEQ=15, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6693957, CONT_SEQ=16, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7113971, CONT_SEQ=17, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7193998, CONT_SEQ=18, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU6179880, CONT_SEQ=19, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU7564937, CONT_SEQ=20, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9140003, CONT_SEQ=21, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9576553, CONT_SEQ=22, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9622290, CONT_SEQ=23, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9843053, CONT_SEQ=24, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=MAGU5362310, CONT_SEQ=25, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7005160, CONT_SEQ=26, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7878391, CONT_SEQ=27, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8391001, CONT_SEQ=28, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8529750, CONT_SEQ=29, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU9259598, CONT_SEQ=30, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1287293, CONT_SEQ=31, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1791283, CONT_SEQ=32, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5140870, CONT_SEQ=33, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5149728, CONT_SEQ=34, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU9616266, CONT_SEQ=35, XML_MSG_ID=20200831170434V392OU}, {XMLDOC_SEQ=1, EQDCN_8260=BEAU4606400, CONT_SEQ=1, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4555375, CONT_SEQ=2, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4839216, CONT_SEQ=3, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9111516, CONT_SEQ=4, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9320373, CONT_SEQ=5, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8811011, CONT_SEQ=6, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8910966, CONT_SEQ=7, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8968947, CONT_SEQ=8, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9008365, CONT_SEQ=9, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9469475, CONT_SEQ=10, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU6848644, CONT_SEQ=11, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU7724381, CONT_SEQ=12, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSLU6268966, CONT_SEQ=13, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6101322, CONT_SEQ=14, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6269569, CONT_SEQ=15, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6693957, CONT_SEQ=16, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7113971, CONT_SEQ=17, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7193998, CONT_SEQ=18, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU6179880, CONT_SEQ=19, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU7564937, CONT_SEQ=20, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9140003, CONT_SEQ=21, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9576553, CONT_SEQ=22, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9622290, CONT_SEQ=23, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9843053, CONT_SEQ=24, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=MAGU5362310, CONT_SEQ=25, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7005160, CONT_SEQ=26, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7878391, CONT_SEQ=27, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8391001, CONT_SEQ=28, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8529750, CONT_SEQ=29, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU9259598, CONT_SEQ=30, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1287293, CONT_SEQ=31, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1791283, CONT_SEQ=32, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5140870, CONT_SEQ=33, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5149728, CONT_SEQ=34, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU9616266, CONT_SEQ=35, XML_MSG_ID=20200831170434V391OU}, {XMLDOC_SEQ=1, EQDCN_8260=BEAU4606400, CONT_SEQ=1, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4555375, CONT_SEQ=2, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4839216, CONT_SEQ=3, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9111516, CONT_SEQ=4, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9320373, CONT_SEQ=5, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8811011, CONT_SEQ=6, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8910966, CONT_SEQ=7, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8968947, CONT_SEQ=8, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9008365, CONT_SEQ=9, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9469475, CONT_SEQ=10, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU6848644, CONT_SEQ=11, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU7724381, CONT_SEQ=12, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CSLU6268966, CONT_SEQ=13, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6101322, CONT_SEQ=14, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6269569, CONT_SEQ=15, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6693957, CONT_SEQ=16, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7113971, CONT_SEQ=17, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7193998, CONT_SEQ=18, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU6179880, CONT_SEQ=19, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU7564937, CONT_SEQ=20, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9140003, CONT_SEQ=21, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9576553, CONT_SEQ=22, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9622290, CONT_SEQ=23, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9843053, CONT_SEQ=24, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=MAGU5362310, CONT_SEQ=25, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7005160, CONT_SEQ=26, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7878391, CONT_SEQ=27, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8391001, CONT_SEQ=28, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8529750, CONT_SEQ=29, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU9259598, CONT_SEQ=30, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1287293, CONT_SEQ=31, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1791283, CONT_SEQ=32, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5140870, CONT_SEQ=33, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5149728, CONT_SEQ=34, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU9616266, CONT_SEQ=35, XML_MSG_ID=20200831170434V392}, {XMLDOC_SEQ=1, EQDCN_8260=BEAU4606400, CONT_SEQ=1, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4555375, CONT_SEQ=2, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4839216, CONT_SEQ=3, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9111516, CONT_SEQ=4, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9320373, CONT_SEQ=5, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8811011, CONT_SEQ=6, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8910966, CONT_SEQ=7, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8968947, CONT_SEQ=8, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9008365, CONT_SEQ=9, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9469475, CONT_SEQ=10, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU6848644, CONT_SEQ=11, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU7724381, CONT_SEQ=12, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CSLU6268966, CONT_SEQ=13, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6101322, CONT_SEQ=14, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6269569, CONT_SEQ=15, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6693957, CONT_SEQ=16, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7113971, CONT_SEQ=17, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7193998, CONT_SEQ=18, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU6179880, CONT_SEQ=19, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU7564937, CONT_SEQ=20, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9140003, CONT_SEQ=21, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9576553, CONT_SEQ=22, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9622290, CONT_SEQ=23, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9843053, CONT_SEQ=24, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=MAGU5362310, CONT_SEQ=25, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7005160, CONT_SEQ=26, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7878391, CONT_SEQ=27, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8391001, CONT_SEQ=28, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8529750, CONT_SEQ=29, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU9259598, CONT_SEQ=30, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1287293, CONT_SEQ=31, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1791283, CONT_SEQ=32, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5140870, CONT_SEQ=33, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5149728, CONT_SEQ=34, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU9616266, CONT_SEQ=35, XML_MSG_ID=20200831170434V393}, {XMLDOC_SEQ=1, EQDCN_8260=BEAU4606400, CONT_SEQ=1, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4555375, CONT_SEQ=2, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=BMOU4839216, CONT_SEQ=3, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9111516, CONT_SEQ=4, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CAIU9320373, CONT_SEQ=5, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8811011, CONT_SEQ=6, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8910966, CONT_SEQ=7, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU8968947, CONT_SEQ=8, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9008365, CONT_SEQ=9, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CBHU9469475, CONT_SEQ=10, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU6848644, CONT_SEQ=11, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CCLU7724381, CONT_SEQ=12, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSLU6268966, CONT_SEQ=13, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6101322, CONT_SEQ=14, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6269569, CONT_SEQ=15, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU6693957, CONT_SEQ=16, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7113971, CONT_SEQ=17, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=CSNU7193998, CONT_SEQ=18, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU6179880, CONT_SEQ=19, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=DFSU7564937, CONT_SEQ=20, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9140003, CONT_SEQ=21, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9576553, CONT_SEQ=22, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9622290, CONT_SEQ=23, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=FCIU9843053, CONT_SEQ=24, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=MAGU5362310, CONT_SEQ=25, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7005160, CONT_SEQ=26, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOCU7878391, CONT_SEQ=27, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8391001, CONT_SEQ=28, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU8529750, CONT_SEQ=29, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=OOLU9259598, CONT_SEQ=30, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1287293, CONT_SEQ=31, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU1791283, CONT_SEQ=32, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5140870, CONT_SEQ=33, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU5149728, CONT_SEQ=34, XML_MSG_ID=20200831170434V393OU}, {XMLDOC_SEQ=1, EQDCN_8260=TCNU9616266, CONT_SEQ=35, XML_MSG_ID=20200831170434V393OU}]
		
		try {
			
//			if ( ebizDAO.svcStatus() < 1 ) {
			if ( false ) {
				logger.info("TRACKING SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				Thread.sleep(60000);
			} else {
				logger.info("START TRACKING " + hour +":"  + minute+":" + second+"." + millisecond);
				Map argu = new HashMap();
				argu.put("NADCA_3039", "COSU");
				
//				List list = ebizDAO.getTrackingSrCntrList(argu);
				List list = new ArrayList();
				Map tmap = new HashMap();
				tmap.put("EQDCN_8260", "BEAU4606400");
//				BMOU4555375
//				BMOU4839216
				list.add(tmap);
				
//				{"containerDetail":{"_package":{"pieceCount":11,"pieceCountUnit":"Package"},"containerNumber":{"CSContainerSizeType":"","carrCntrSizeType":"","containerCheckDigit":"0","containerNumber":"BEAU460640","grossWeight":{"weight":23548,"weightUnit":{"value":"KGS"}},"haulage":null,"isSOC":false,"seal":[],"trafficMode":null},"detentionAndDemurrage":null,"doorDelivery":[],"event":[{"CSEvent":{"CSEventCode":"CS277","dimentionType":"Port","dimentionValue":"LastPOD","estActIndicator":"E"},"carrEventCode":"ssm_vsl_arr_last_pod_est","eventDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":18,"day":3,"hours":12,"minutes":0,"month":10,"seconds":0,"time":1605700800000,"timezoneOffset":0,"year":120},"timeInMillis":1605700800000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":{"CSTimeZone":"CCT","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":18,"day":3,"hours":20,"minutes":0,"month":10,"seconds":0,"time":1605729600000,"timezoneOffset":0,"year":120},"timeInMillis":1605729600000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}},"eventDescription":"Estimated arrival at last port of discharge","eventReceivedDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":24,"day":6,"hours":4,"minutes":9,"month":9,"seconds":56,"time":1603512596000,"timezoneOffset":0,"year":120},"timeInMillis":1603512596000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":null},"location":{"CSStandardCity":{"CSContinentCode":"AS","CSCountryCode":"CN","CSParentCityID":2532082,"CSStateCode":"SD"},"cityDetails":{"city":"Qingdao","country":"China","county":"Qingdao","locationCode":{"UNLocationCode":"CNTAO","mutuallyDefinedCode":"","schedKDCode":"57047","schedKDType":{"value":"K"}},"state":"Shandong"},"facility":null,"locationName":"Qingdao"},"mode":""},{"CSEvent":{"CSEventCode":"CS067","dimentionType":"Port","dimentionValue":"FirstPOL","estActIndicator":"E"},"carrEventCode":"ssm_vsl_dep_first_pol_est","eventDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":12,"day":1,"hours":4,"minutes":0,"month":9,"seconds":0,"time":1602475200000,"timezoneOffset":0,"year":120},"timeInMillis":1602475200000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":12,"day":1,"hours":6,"minutes":0,"month":9,"seconds":0,"time":1602482400000,"timezoneOffset":0,"year":120},"timeInMillis":1602482400000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}},"eventDescription":"Estimated Departure at First Port of Load","eventReceivedDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":24,"day":6,"hours":4,"minutes":9,"month":9,"seconds":56,"time":1603512596000,"timezoneOffset":0,"year":120},"timeInMillis":1603512596000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":null},"location":{"CSStandardCity":{"CSContinentCode":"EU","CSCountryCode":"DE","CSParentCityID":2539114,"CSStateCode":""},"cityDetails":{"city":"Hamburg","country":"Germany","county":"","locationCode":{"UNLocationCode":"DEHAM","mutuallyDefinedCode":"","schedKDCode":"42879","schedKDType":{"value":"K"}},"state":"Hamburg"},"facility":null,"locationName":"Hamburg"},"mode":""},{"CSEvent":{"CSEventCode":"CS070","dimentionType":"Port","dimentionValue":"FirstPOL","estActIndicator":"A"},"carrEventCode":"ssm_vsl_dep_first_pol","eventDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":12,"day":1,"hours":3,"minutes":54,"month":9,"seconds":6,"time":1602474846000,"timezoneOffset":0,"year":120},"timeInMillis":1602474846000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":12,"day":1,"hours":5,"minutes":54,"month":9,"seconds":6,"time":1602482046000,"timezoneOffset":0,"year":120},"timeInMillis":1602482046000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}},"eventDescription":"Vessel Departure at First POL","eventReceivedDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":24,"day":6,"hours":4,"minutes":9,"month":9,"seconds":53,"time":1603512593000,"timezoneOffset":0,"year":120},"timeInMillis":1603512593000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":null},"location":{"CSStandardCity":{"CSContinentCode":"EU","CSCountryCode":"DE","CSParentCityID":2539114,"CSStateCode":""},"cityDetails":{"city":"Hamburg","country":"Germany","county":"","locationCode":{"UNLocationCode":"DEHAM","mutuallyDefinedCode":"","schedKDCode":"42879","schedKDType":{"value":"K"}},"state":"Hamburg"},"facility":null,"locationName":"Hamburg"},"mode":""},{"CSEvent":{"CSEventCode":"CS500","dimentionType":"","dimentionValue":"","estActIndicator":"A"},"carrEventCode":"CS500","eventDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":11,"day":0,"hours":22,"minutes":0,"month":9,"seconds":0,"time":1602453600000,"timezoneOffset":0,"year":120},"timeInMillis":1602453600000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":12,"day":1,"hours":0,"minutes":0,"month":9,"seconds":0,"time":1602460800000,"timezoneOffset":0,"year":120},"timeInMillis":1602460800000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}},"eventDescription":"Original B/L Printed","eventReceivedDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":20,"day":2,"hours":6,"minutes":20,"month":9,"seconds":15,"time":1603174815000,"timezoneOffset":0,"year":120},"timeInMillis":1603174815000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":null},"location":{"CSStandardCity":{"CSContinentCode":"EU","CSCountryCode":"DE","CSParentCityID":2539114,"CSStateCode":""},"cityDetails":{"city":"Hamburg","country":"Germany","county":"","locationCode":{"UNLocationCode":"DEHAM","mutuallyDefinedCode":"","schedKDCode":"42879","schedKDType":{"value":"K"}},"state":"Hamburg"},"facility":null,"locationName":"Hamburg"},"mode":""},{"CSEvent":{"CSEventCode":"CS060","dimentionType":"","dimentionValue":"","estActIndicator":"A"},"carrEventCode":"Loaded on board at first port of load","eventDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":11,"day":0,"hours":12,"minutes":24,"month":9,"seconds":0,"time":1602419040000,"timezoneOffset":0,"year":120},"timeInMillis":1602419040000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":11,"day":0,"hours":14,"minutes":24,"month":9,"seconds":0,"time":1602426240000,"timezoneOffset":0,"year":120},"timeInMillis":1602426240000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}},"eventDescription":"Loaded on Board at First Port of Load","eventReceivedDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":20,"day":2,"hours":6,"minutes":19,"month":9,"seconds":52,"time":1603174792000,"timezoneOffset":0,"year":120},"timeInMillis":1603174792000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":null},"location":{"CSStandardCity":{"CSContinentCode":"EU","CSCountryCode":"DE","CSParentCityID":2539114,"CSStateCode":""},"cityDetails":{"city":"Hamburg","country":"Germany","county":"","locationCode":{"UNLocationCode":"DEHAM","mutuallyDefinedCode":"","schedKDCode":"42879","schedKDType":{"value":"K"}},"state":"Hamburg"},"facility":null,"locationName":"Hamburg"},"mode":"Vessel"},{"CSEvent":{"CSEventCode":"CS020","dimentionType":"","dimentionValue":"","estActIndicator":"A"},"carrEventCode":"Full received by carrier","eventDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":6,"day":2,"hours":8,"minutes":32,"month":9,"seconds":0,"time":1601973120000,"timezoneOffset":0,"year":120},"timeInMillis":1601973120000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":6,"day":2,"hours":10,"minutes":32,"month":9,"seconds":0,"time":1601980320000,"timezoneOffset":0,"year":120},"timeInMillis":1601980320000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}},"eventDescription":"Full Container Received by Carrier at Origin","eventReceivedDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":20,"day":2,"hours":6,"minutes":19,"month":9,"seconds":52,"time":1603174792000,"timezoneOffset":0,"year":120},"timeInMillis":1603174792000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":null},"location":{"CSStandardCity":{"CSContinentCode":"EU","CSCountryCode":"DE","CSParentCityID":2539114,"CSStateCode":""},"cityDetails":{"city":"Hamburg","country":"Germany","county":"","locationCode":{"UNLocationCode":"DEHAM","mutuallyDefinedCode":"","schedKDCode":"42879","schedKDType":{"value":"K"}},"state":"Hamburg"},"facility":null,"locationName":"Hamburg"},"mode":"Truck"},{"CSEvent":{"CSEventCode":"CS040","dimentionType":"","dimentionValue":"","estActIndicator":"A"},"carrEventCode":"Outbound intermodal leg arrived","eventDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":6,"day":2,"hours":8,"minutes":32,"month":9,"seconds":0,"time":1601973120000,"timezoneOffset":0,"year":120},"timeInMillis":1601973120000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":6,"day":2,"hours":10,"minutes":32,"month":9,"seconds":0,"time":1601980320000,"timezoneOffset":0,"year":120},"timeInMillis":1601980320000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}},"eventDescription":"Container Arrival at First Port of Load","eventReceivedDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":20,"day":2,"hours":6,"minutes":19,"month":9,"seconds":49,"time":1603174789000,"timezoneOffset":0,"year":120},"timeInMillis":1603174789000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":null},"location":{"CSStandardCity":{"CSContinentCode":"EU","CSCountryCode":"DE","CSParentCityID":2539114,"CSStateCode":""},"cityDetails":{"city":"Hamburg","country":"Germany","county":"","locationCode":{"UNLocationCode":"DEHAM","mutuallyDefinedCode":"","schedKDCode":"42879","schedKDType":{"value":"K"}},"state":"Hamburg"},"facility":null,"locationName":"Hamburg"},"mode":"Truck"},{"CSEvent":{"CSEventCode":"CS010","dimentionType":"","dimentionValue":"","estActIndicator":"A"},"carrEventCode":"Empty picked up, but not full return","eventDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":5,"day":1,"hours":8,"minutes":5,"month":9,"seconds":0,"time":1601885100000,"timezoneOffset":0,"year":120},"timeInMillis":1601885100000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":5,"day":1,"hours":10,"minutes":5,"month":9,"seconds":0,"time":1601892300000,"timezoneOffset":0,"year":120},"timeInMillis":1601892300000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}},"eventDescription":"Empty Container Picked Up","eventReceivedDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":20,"day":2,"hours":6,"minutes":19,"month":9,"seconds":49,"time":1603174789000,"timezoneOffset":0,"year":120},"timeInMillis":1603174789000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":null},"location":{"CSStandardCity":{"CSContinentCode":"EU","CSCountryCode":"DE","CSParentCityID":2539114,"CSStateCode":""},"cityDetails":{"city":"Hamburg","country":"Germany","county":"","locationCode":{"UNLocationCode":"DEHAM","mutuallyDefinedCode":"","schedKDCode":"42879","schedKDType":{"value":"K"}},"state":"Hamburg"},"facility":null,"locationName":"Dummy Depot HAM"},"mode":"Truck"}],"externalReference":[{"CSReferenceType":{"value":"BL"},"referenceDescription":"Bill of Lading Number","referenceNumber":"6277125670"},{"CSReferenceType":{"value":"BKG"},"referenceDescription":"Booking Number","referenceNumber":"6277125670"}],"route":{"FND":{"CSStandardCity":{"CSContinentCode":"AS","CSCountryCode":"CN","CSParentCityID":2532082,"CSStateCode":"SD"},"cityDetails":{"city":"Qingdao","country":"China","county":"Qingdao","locationCode":null,"state":"Shandong"},"facility":{"facilityCode":"TAO06","facilityName":"QQCTU Qingdao Qianwan United Ctn"},"locationName":""},"POR":{"CSStandardCity":{"CSContinentCode":"EU","CSCountryCode":"DE","CSParentCityID":2539114,"CSStateCode":""},"cityDetails":{"city":"Hamburg","country":"Germany","county":"","locationCode":null,"state":"Hamburg"},"facility":{"facilityCode":"HAM01","facilityName":"Container Terminal Tollerort GmbH"},"locationName":""},"arrivalAtFinalHub":[{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":20,"day":5,"hours":8,"minutes":0,"month":10,"seconds":0,"time":1605859200000,"timezoneOffset":0,"year":120},"timeInMillis":1605859200000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"indicator":{"value":"E"},"locDT":{"CSTimeZone":"","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":20,"day":5,"hours":16,"minutes":0,"month":10,"seconds":0,"time":1605888000000,"timezoneOffset":0,"year":120},"timeInMillis":1605888000000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":"CST"}}],"firstPOL":{"facility":{"facilityCode":"HAM01","facilityName":"Container Terminal Tollerort GmbH"},"port":{"CSCountryCode":"DE","CSPortID":181,"city":"Hamburg","country":"Germany","county":"","locationCode":{"UNLocationCode":"DEHAM","mutuallyDefinedCode":"","schedKDCode":"42879","schedKDType":{"value":"K"}},"portCode":"","portName":"Hamburg","state":"Hamburg"}},"fullReturnCutOffDT":{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":7,"day":3,"hours":17,"minutes":0,"month":9,"seconds":0,"time":1602090000000,"timezoneOffset":0,"year":120},"timeInMillis":1602090000000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":7,"day":3,"hours":19,"minutes":0,"month":9,"seconds":0,"time":1602097200000,"timezoneOffset":0,"year":120},"timeInMillis":1602097200000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":"CES"}},"haulage":{"inBound":{"value":"M"},"outBound":{"value":"M"}},"lastPOD":{"facility":{"facilityCode":"TAO06","facilityName":"QQCTU Qingdao Qianwan United Ctn"},"port":{"CSCountryCode":"CN","CSPortID":94,"city":"Qingdao","country":"China","county":"Qingdao","locationCode":{"UNLocationCode":"CNTAO","mutuallyDefinedCode":"","schedKDCode":"57047","schedKDType":{"value":"K"}},"portCode":"","portName":"Qingdao","state":"Shandong"}},"shipmentLeg":[{"POD":{"facility":{"facilityCode":"TAO06","facilityName":"QQCTU Qingdao Qianwan United Ctn"},"port":{"CSCountryCode":"CN","CSPortID":94,"city":"Qingdao","country":"China","county":"Qingdao","locationCode":null,"portCode":"","portName":"Qingdao","state":"Shandong"}},"POL":{"facility":{"facilityCode":"HAM01","facilityName":"Container Terminal Tollerort GmbH"},"port":{"CSCountryCode":"DE","CSPortID":181,"city":"Hamburg","country":"Germany","county":"","locationCode":null,"portCode":"","portName":"Hamburg","state":"Hamburg"}},"SVVD":{"discharge":{"callNumber":1,"callSign":"VRRV4","direction":"W","lloydsNumber":"9783538","service":"AEU3","vessel":"CSB","vesselName":"COSCO SHIPPING LIBRA","vesselNationality":"","voyage":"013"},"loading":{"callNumber":1,"callSign":"VRRV4","direction":"E","lloydsNumber":"9783538","service":"AEU3","vessel":"CSB","vesselName":"COSCO SHIPPING LIBRA","vesselNationality":"","voyage":"012"},"vesselVoyageType":{"value":"OUTBOUND"}},"arrivalDT":[{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":18,"day":3,"hours":12,"minutes":0,"month":10,"seconds":0,"time":1605700800000,"timezoneOffset":0,"year":120},"timeInMillis":1605700800000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"indicator":{"value":"E"},"locDT":{"CSTimeZone":"CCT","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":18,"day":3,"hours":20,"minutes":0,"month":10,"seconds":0,"time":1605729600000,"timezoneOffset":0,"year":120},"timeInMillis":1605729600000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}}],"departureDT":[{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":12,"day":1,"hours":4,"minutes":0,"month":9,"seconds":0,"time":1602475200000,"timezoneOffset":0,"year":120},"timeInMillis":1602475200000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"indicator":{"value":"E"},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":12,"day":1,"hours":6,"minutes":0,"month":9,"seconds":0,"time":1602482400000,"timezoneOffset":0,"year":120},"timeInMillis":1602482400000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}},{"GMT":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":12,"day":1,"hours":3,"minutes":54,"month":9,"seconds":6,"time":1602474846000,"timezoneOffset":0,"year":120},"timeInMillis":1602474846000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"indicator":{"value":"A"},"locDT":{"CSTimeZone":"CEST","_value":{"calendarType":"gregory","firstDayOfWeek":1,"gregorianChange":{"date":15,"day":5,"hours":0,"minutes":0,"month":9,"seconds":0,"time":-12219292800000,"timezoneOffset":0,"year":-318},"lenient":true,"minimalDaysInFirstWeek":1,"time":{"date":12,"day":1,"hours":5,"minutes":54,"month":9,"seconds":6,"time":1602482046000,"timezoneOffset":0,"year":120},"timeInMillis":1602482046000,"timeZone":{"DSTSavings":0,"ID":"GMT","dirty":false,"displayName":"Greenwich Mean Time","lastRuleInstance":null,"rawOffset":0},"weekDateSupported":true,"weekYear":2020,"weeksInWeekYear":52},"timeZone":""}}],"legSeq":1}]},"status":[]},"queryCriteria":{"carrierSCACCode":"COSU","containerNumber":"BEAU4606400","timeStamp":""},"queryError":[]}

				
				JsonParser springParser = JsonParserFactory.getJsonParser();
				RestTemplate restTemplate = new RestTemplate();
				
				for(int i=0; i<list.size(); i++){
					try {
						Map iftsta_d99b = new HashMap();
						Map map = (Map) list.get(i);
	//					ebizDAO.setTrackingSrCntrComplete(map);
						String url=this.prosUrlTracking+"/" + "COSU" + "?appKey=" +this.prosAppKeyCOSU + "&scacCode=COSU" + "&cntrNumber="+nullConvert(map.get("EQDCN_8260"));
						logger.info(url);
						String resp = restTemplate.getForObject(url, String.class);
						System.out.println("resp:" + resp);
//						logger.info(resp);
						Map<String, Object> parse = springParser.parseMap(resp);
						
						Map _queryCriteria$map = (Map) parse.get("queryCriteria");
						String _queryCriteria$map_carrierSCACCode = nullConvert(_queryCriteria$map.get("carrierSCACCode"));
//						String _queryCriteria$map_containerNumber = nullConvert(_queryCriteria$map.get("containerNumber"));
						iftsta_d99b.put("NADCA_039", _queryCriteria$map_carrierSCACCode);
						iftsta_d99b.put("TDT_3127", _queryCriteria$map_carrierSCACCode);
//						iftsta_d99b.put("EQDCN_260", _queryCriteria$map_containerNumber);
						
						Map _containerDetail$map = (Map) parse.get("containerDetail");
						
						boolean isEvent = false;
						
						List _event = (List) _containerDetail$map.get("event");						
						for(int evidx=0; evidx<_event.size(); evidx++) {
							Map _event$map = (Map) _event.get(evidx);
							Map _event$map_CSEvent$map = (Map) _event$map.get("CSEvent");
							String _event$map_CSEvent$map_CSEventCode = nullConvert(_event$map_CSEvent$map.get("CSEventCode"));
							
							Map _event$map_eventDT$map = (Map) _event$map.get("eventDT");
							Map _event$map_eventDT$map_GMT$map = (Map) _event$map_eventDT$map.get("GMT");
							
							
							String _event$map_eventDT$map_GMT$map_timeInMillis = nullConvert(_event$map_eventDT$map_GMT$map.get("timeInMillis"));
							logger.info("_event$map_eventDT$map_GMT$map_timeInMillis:"+_event$map_eventDT$map_GMT$map_timeInMillis);
							
//							Map _event$map_eventDT$map_GMT$map_time$map = (Map) _event$map_eventDT$map_GMT$map.get("time");
//							String _event$map_eventDT$map_GMT$map_time$map_date = nullConvert(_event$map_eventDT$map_GMT$map_time$map.get("date"));
//							String _event$map_eventDT$map_GMT$map_time$map_month = nullConvert(_event$map_eventDT$map_GMT$map_time$map.get("month"));
//							String _event$map_eventDT$map_GMT$map_time$map_year = nullConvert(_event$map_eventDT$map_GMT$map_time$map.get("year"));
//							String _event$map_eventDT$map_GMT$map_time$map_hours = nullConvert(_event$map_eventDT$map_GMT$map_time$map.get("hours"));
//							String _event$map_eventDT$map_GMT$map_time$map_minutes = nullConvert(_event$map_eventDT$map_GMT$map_time$map.get("minutes"));
//							String _event$map_eventDT$map_GMT$map_time$map_seconds = nullConvert(_event$map_eventDT$map_GMT$map_time$map.get("seconds"));
//							String _event$map_eventDT$map_GMT$map_time$map_time = nullConvert(_event$map_eventDT$map_GMT$map_time$map.get("time"));
							
							
							
							logger.info("_event$map_CSEvent_CSEventCode:"+_event$map_CSEvent$map_CSEventCode);
							
							if ("CS120".equals(_event$map_CSEvent$map_CSEventCode)) {
								iftsta_d99b.put("STS1_405", _event$map_CSEvent$map_CSEventCode);
								iftsta_d99b.put("DTM178_11", _event$map_eventDT$map_GMT$map_timeInMillis);
								isEvent = true;
								
//								ebizDAO.setTrackingSrCntrComplete(map);
								
								break;
							} else if ("CS277".equals(_event$map_CSEvent$map_CSEventCode)) {
								iftsta_d99b.put("STS1_405", _event$map_CSEvent$map_CSEventCode);
								iftsta_d99b.put("DTM132_11", _event$map_eventDT$map_GMT$map_timeInMillis);
								isEvent = true;
								break;
							} else if ("CS110".equals(_event$map_CSEvent$map_CSEventCode)) {
								iftsta_d99b.put("STS1_405", _event$map_CSEvent$map_CSEventCode);
								iftsta_d99b.put("DTM186_9", _event$map_eventDT$map_GMT$map_timeInMillis);
								isEvent = true;
								break;
							} else if ("CS080".equals(_event$map_CSEvent$map_CSEventCode)) {
								iftsta_d99b.put("STS1_405", _event$map_CSEvent$map_CSEventCode);
								iftsta_d99b.put("DTM178_11", _event$map_eventDT$map_GMT$map_timeInMillis);
								isEvent = true;
								break;
							} else if ("CS070".equals(_event$map_CSEvent$map_CSEventCode)) {
								iftsta_d99b.put("STS1_405", _event$map_CSEvent$map_CSEventCode);
								iftsta_d99b.put("DTM186_9", _event$map_eventDT$map_GMT$map_timeInMillis);
								isEvent = true;
								break;
							} else if ("CS067".equals(_event$map_CSEvent$map_CSEventCode)) {
								iftsta_d99b.put("STS1_405", _event$map_CSEvent$map_CSEventCode);
								iftsta_d99b.put("DTM133_9", _event$map_eventDT$map_GMT$map_timeInMillis);
								isEvent = true;
								break;
							} 
						}
						
						if (isEvent) {
							List _externalReference = (List) _containerDetail$map.get("externalReference");
							for(int eridx=0; eridx<_externalReference.size(); eridx++) {
								Map _externalReference$map = (Map) _externalReference.get(eridx);
								Map _externalReference$map_CSReferenceType$map = (Map) _externalReference$map.get("CSReferenceType");
								String _externalReference$map_CSReferenceType$map_value = nullConvert(_externalReference$map_CSReferenceType$map.get("value"));
								String _externalReference$map_referenceNumber = nullConvert(_externalReference$map.get("referenceNumber"));
//								logger.info("_externalReference$map_CSReferenceType$map_value:"+_externalReference$map_CSReferenceType$map_value);
								if ("BL".equals(_externalReference$map_CSReferenceType$map_value)) {
									iftsta_d99b.put("RFFBN_154", _externalReference$map_referenceNumber);
								} else if ("BKG".equals(_externalReference$map_CSReferenceType$map_value)) {
									iftsta_d99b.put("RFFBM_154", _externalReference$map_referenceNumber);
								}
							}
							
							Map _route$map = (Map) _containerDetail$map.get("route");
							List _route$map_shipmentLeg = (List) _route$map.get("shipmentLeg");
							for(int slidx=0; slidx < _route$map_shipmentLeg.size(); slidx++) {
								if (slidx == 0) {
									Map _route$map_shipmentLeg$map = (Map) _route$map_shipmentLeg.get(slidx);
									Map _route$map_shipmentLeg$map_SVVD$map = (Map) _route$map_shipmentLeg$map.get("SVVD");
									Map _route$map_shipmentLeg$map_SVVD$map_discharge$map = (Map) _route$map_shipmentLeg$map_SVVD$map.get("discharge");
									String _route$map_shipmentLeg$map_SVVD$map_discharge$map_voyage = nullConvert(_route$map_shipmentLeg$map_SVVD$map_discharge$map.get("voyage"));
									iftsta_d99b.put("TDT_8028", _route$map_shipmentLeg$map_SVVD$map_discharge$map_voyage);
									String _route$map_shipmentLeg$map_SVVD$map_discharge$map_callSign = nullConvert(_route$map_shipmentLeg$map_SVVD$map_discharge$map.get("callSign"));
									iftsta_d99b.put("TDT_8213", _route$map_shipmentLeg$map_SVVD$map_discharge$map_callSign);
									String _route$map_shipmentLeg$map_SVVD$map_discharge$map_vesselName = nullConvert(_route$map_shipmentLeg$map_SVVD$map_discharge$map.get("vesselName"));
									iftsta_d99b.put("TDT_8212", _route$map_shipmentLeg$map_SVVD$map_discharge$map_vesselName);

									Map _route$map_shipmentLeg$map_POL$map = (Map) _route$map_shipmentLeg$map.get("POL");
									Map _route$map_shipmentLeg$map_POL$map_port$map = (Map) _route$map_shipmentLeg$map_POL$map.get("port");
									String _route$map_shipmentLeg$map_POL$map_port$map_portName = nullConvert(_route$map_shipmentLeg$map_POL$map_port$map.get("portName"));
									iftsta_d99b.put("LOC9_3224", _route$map_shipmentLeg$map_POL$map_port$map_portName);
									
									Map _route$map_shipmentLeg$map_POD$map = (Map) _route$map_shipmentLeg$map.get("POD");
									Map _route$map_shipmentLeg$map_POD$map_port$map = (Map) _route$map_shipmentLeg$map_POD$map.get("port");
//									String _route$map_shipmentLeg$map_POD$map_port$map_portCode = nullConvert(_route$map_shipmentLeg$map_POD$map_port$map.get("portCode"));
//									iftsta_d99b.put("LOC11_3225", _route$map_shipmentLeg$map_POD$map_port$map_portCode);
									String _route$map_shipmentLeg$map_POD$map_port$map_portName = nullConvert(_route$map_shipmentLeg$map_POD$map_port$map.get("portName"));
									iftsta_d99b.put("LOC11_3224", _route$map_shipmentLeg$map_POD$map_port$map_portName);
									
																	
								}
								
							}
							
							Map _route$map_firstPOL$map = (Map) _route$map.get("firstPOL");
							Map _route$map_firstPOL$map_port$map = (Map) _route$map_firstPOL$map.get("port");
							Map _route$map_firstPOL$map_port$map_locationCode$map = (Map) _route$map_firstPOL$map_port$map.get("locationCode");
							String _route$map_firstPOL$map_port$map_locationCode$map_UNLocationCode = nullConvert(_route$map_firstPOL$map_port$map_locationCode$map.get("UNLocationCode"));
							iftsta_d99b.put("LOC9_3225", _route$map_firstPOL$map_port$map_locationCode$map_UNLocationCode);

							Map _route$map_lastPOD$map = (Map) _route$map.get("lastPOD");
							Map _route$map_lastPOD$map_port$map = (Map) _route$map_lastPOD$map.get("port");
							Map _route$map_lastPOD$map_port$map_locationCode$map = (Map) _route$map_lastPOD$map_port$map.get("locationCode");
							String _route$map_lastPOD$map_port$map_locationCode$map_UNLocationCode = nullConvert(_route$map_lastPOD$map_port$map_locationCode$map.get("UNLocationCode"));
							iftsta_d99b.put("LOC11_3225", _route$map_lastPOD$map_port$map_locationCode$map_UNLocationCode);
							
							
							Map _containerNumber$map = (Map) _containerDetail$map.get("containerNumber");
							String _containerNumber$map_carrCntrSizeType = nullConvert(_containerNumber$map.get("carrCntrSizeType"));
							iftsta_d99b.put("EQDCN_155", _containerNumber$map_carrCntrSizeType);
							String _containerNumber$map_containerNumber = nullConvert(_containerNumber$map.get("containerNumber"));
							iftsta_d99b.put("EQDCN_260", _containerNumber$map_containerNumber);
							
//						String xml_msg_id = ebizDAO.getCnEdiXmlMsgId();
//						iftsta_d99b.put("XML_MSG_ID", xml_msg_id);
//						iftsta_d99b.put("DOC_MSG_ID", xml_msg_id);
//						iftsta_d99b.put("XMLDOC_SEQ", "1");


logger.info("iftsta_d99b:"+iftsta_d99b);
//						ebizDAO.setiftsai___hd(iftsta);
//						ebizDAO.setScheduleMapinTbl(iftsta);
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				
				
				
				logger.info("END ROUTES SCHEDULE");
			}
		} catch (Exception e){
			logger.error("error", e);
		}
		
				

		
		
		
		
//		RestTemplate restTemplate = new RestTemplate();
//		String resp = restTemplate.getForObject(url, String.class);
		
//		String resp = "{\"containerDetail\":{\"_package\":{\"pieceCount\":80,\"pieceCountUnit\":\"Drum\"},\"containerNumber\":{\"CSContainerSizeType\":\"\",\"carrCntrSizeType\":\"\",\"containerCheckDigit\":\"9\",\"containerNumber\":\"FCIU581261\",\"grossWeight\":{\"weight\":21920,\"weightUnit\":{\"value\":\"KGS\"}},\"haulage\":null,\"isSOC\":false,\"seal\":[],\"trafficMode\":null},\"detentionAndDemurrage\":null,\"doorDelivery\":[],\"event\":[{\"CSEvent\":{\"CSEventCode\":\"CS070\",\"dimentionType\":\"Port\",\"dimentionValue\":\"FirstPOL\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"ssm_vsl_dep_first_pol\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":9,\"month\":4,\"seconds\":35,\"time\":1589501375000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589501375000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":9,\"minutes\":9,\"month\":4,\"seconds\":35,\"time\":1589533775000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589533775000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":27,\"month\":4,\"seconds\":46,\"time\":1589502466000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589502466000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":{\"UNLocationCode\":\"KRKAN\",\"mutuallyDefinedCode\":\"\",\"schedKDCode\":\"58031\",\"schedKDType\":{\"value\":\"K\"}},\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Gwangyang\"},\"mode\":\"\"},{\"CSEvent\":{\"CSEventCode\":\"CS060\",\"dimentionType\":\"\",\"dimentionValue\":\"\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"Loaded on board at first port of load\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":14,\"day\":4,\"hours\":17,\"minutes\":15,\"month\":4,\"seconds\":0,\"time\":1589476500000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589476500000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":2,\"minutes\":15,\"month\":4,\"seconds\":0,\"time\":1589508900000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589508900000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":14,\"day\":4,\"hours\":17,\"minutes\":42,\"month\":4,\"seconds\":26,\"time\":1589478146000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589478146000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Kwangyang\"},\"mode\":\"Vessel\"},{\"CSEvent\":{\"CSEventCode\":\"CS040\",\"dimentionType\":\"\",\"dimentionValue\":\"\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"Outbound intermodal leg arrived\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":10,\"day\":0,\"hours\":23,\"minutes\":27,\"month\":4,\"seconds\":0,\"time\":1589153220000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589153220000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":11,\"day\":1,\"hours\":8,\"minutes\":27,\"month\":4,\"seconds\":0,\"time\":1589185620000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589185620000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":11,\"day\":1,\"hours\":0,\"minutes\":0,\"month\":4,\"seconds\":16,\"time\":1589155216000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589155216000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Kwangyang\"},\"mode\":\"Truck\"},{\"CSEvent\":{\"CSEventCode\":\"CS020\",\"dimentionType\":\"\",\"dimentionValue\":\"\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"Full received by carrier\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":10,\"day\":0,\"hours\":23,\"minutes\":27,\"month\":4,\"seconds\":0,\"time\":1589153220000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589153220000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":11,\"day\":1,\"hours\":8,\"minutes\":27,\"month\":4,\"seconds\":0,\"time\":1589185620000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589185620000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":11,\"day\":1,\"hours\":0,\"minutes\":0,\"month\":4,\"seconds\":16,\"time\":1589155216000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589155216000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Korea International Terminal\"},\"mode\":\"Truck\"},{\"CSEvent\":{\"CSEventCode\":\"CS010\",\"dimentionType\":\"\",\"dimentionValue\":\"\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"Empty picked up, but not full return\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":9,\"day\":6,\"hours\":8,\"minutes\":11,\"month\":4,\"seconds\":0,\"time\":1589011860000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589011860000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":9,\"day\":6,\"hours\":17,\"minutes\":11,\"month\":4,\"seconds\":0,\"time\":1589044260000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589044260000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":9,\"day\":6,\"hours\":8,\"minutes\":25,\"month\":4,\"seconds\":8,\"time\":1589012708000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589012708000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Korea International Terminal\"},\"mode\":\"Truck\"}],\"externalReference\":[{\"CSReferenceType\":{\"value\":\"BL\"},\"referenceDescription\":\"Bill of Lading Number\",\"referenceNumber\":\"6262443190\"},{\"CSReferenceType\":{\"value\":\"BKG\"},\"referenceDescription\":\"Booking Number\",\"referenceNumber\":\"6262443190\"}],\"route\":{\"FND\":{\"CSStandardCity\":{\"CSContinentCode\":\"EU\",\"CSCountryCode\":\"PL\",\"CSParentCityID\":2547944,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gdansk\",\"country\":\"Poland\",\"county\":\"\",\"locationCode\":null,\"state\":\"Pomorskie\"},\"facility\":{\"facilityCode\":\"GDN02\",\"facilityName\":\"Deepwater Container Terminal Gdansk\"},\"locationName\":\"\"},\"POR\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Kwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":{\"facilityCode\":\"KAN05\",\"facilityName\":\"Korea International Terminal\"},\"locationName\":\"\"},\"arrivalAtFinalHub\":[{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":25,\"day\":4,\"hours\":7,\"minutes\":0,\"month\":5,\"seconds\":0,\"time\":1593068400000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1593068400000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"indicator\":{\"value\":\"E\"},\"locDT\":{\"CSTimeZone\":\"\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":25,\"day\":4,\"hours\":9,\"minutes\":0,\"month\":5,\"seconds\":0,\"time\":1593075600000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1593075600000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"CES\"}}],\"firstPOL\":{\"facility\":{\"facilityCode\":\"KAN05\",\"facilityName\":\"Korea International Terminal\"},\"port\":{\"CSCountryCode\":\"KR\",\"CSPortID\":360,\"city\":\"Kwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":{\"UNLocationCode\":\"KRKAN\",\"mutuallyDefinedCode\":\"\",\"schedKDCode\":\"58031\",\"schedKDType\":{\"value\":\"K\"}},\"portCode\":\"\",\"portName\":\"Kwangyang\",\"state\":\"Chollanam-do\"}},\"fullReturnCutOffDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":13,\"day\":3,\"hours\":3,\"minutes\":0,\"month\":4,\"seconds\":0,\"time\":1589338800000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589338800000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":13,\"day\":3,\"hours\":12,\"minutes\":0,\"month\":4,\"seconds\":0,\"time\":1589371200000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589371200000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"KST\"}},\"haulage\":{\"inBound\":{\"value\":\"M\"},\"outBound\":{\"value\":\"M\"}},\"lastPOD\":{\"facility\":{\"facilityCode\":\"GDN02\",\"facilityName\":\"Deepwater Container Terminal Gdansk\"},\"port\":{\"CSCountryCode\":\"PL\",\"CSPortID\":341,\"city\":\"Gdansk\",\"country\":\"Poland\",\"county\":\"\",\"locationCode\":{\"UNLocationCode\":\"PLGDN\",\"mutuallyDefinedCode\":\"\",\"schedKDCode\":\"45511\",\"schedKDType\":{\"value\":\"K\"}},\"portCode\":\"\",\"portName\":\"Gdansk\",\"state\":\"Pomorskie\"}},\"shipmentLeg\":[{\"POD\":{\"facility\":{\"facilityCode\":\"GDN02\",\"facilityName\":\"Deepwater Container Terminal Gdansk\"},\"port\":{\"CSCountryCode\":\"PL\",\"CSPortID\":341,\"city\":\"Gdansk\",\"country\":\"Poland\",\"county\":\"\",\"locationCode\":null,\"portCode\":\"\",\"portName\":\"Gdansk\",\"state\":\"Pomorskie\"}},\"POL\":{\"facility\":{\"facilityCode\":\"KAN05\",\"facilityName\":\"Korea International Terminal\"},\"port\":{\"CSCountryCode\":\"KR\",\"CSPortID\":360,\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"portCode\":\"\",\"portName\":\"Kwangyang\",\"state\":\"Chollanam-do\"}},\"SVVD\":{\"discharge\":{\"callNumber\":1,\"callSign\":\"VRRB8\",\"direction\":\"E\",\"lloydsNumber\":\"9776212\",\"service\":\"AEU1\",\"vessel\":\"NQ3\",\"vesselName\":\"OOCL SCANDINAVIA\",\"vesselNationality\":\"\",\"voyage\":\"012\"},\"loading\":{\"callNumber\":1,\"callSign\":\"VRRB8\",\"direction\":\"W\",\"lloydsNumber\":\"9776212\",\"service\":\"AEU1\",\"vessel\":\"NQ3\",\"vesselName\":\"OOCL SCANDINAVIA\",\"vesselNationality\":\"\",\"voyage\":\"012\"},\"vesselVoyageType\":{\"value\":\"OUTBOUND\"}},\"arrivalDT\":[{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":23,\"day\":2,\"hours\":17,\"minutes\":0,\"month\":5,\"seconds\":0,\"time\":1592931600000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1592931600000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"indicator\":{\"value\":\"E\"},\"locDT\":{\"CSTimeZone\":\"CEST\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":23,\"day\":2,\"hours\":19,\"minutes\":0,\"month\":5,\"seconds\":0,\"time\":1592938800000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1592938800000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}}],\"departureDT\":[{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":4,\"minutes\":0,\"month\":4,\"seconds\":0,\"time\":1589515200000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589515200000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"indicator\":{\"value\":\"E\"},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":13,\"minutes\":0,\"month\":4,\"seconds\":0,\"time\":1589547600000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589547600000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":9,\"month\":4,\"seconds\":35,\"time\":1589501375000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589501375000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"indicator\":{\"value\":\"A\"},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":9,\"minutes\":9,\"month\":4,\"seconds\":35,\"time\":1589533775000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589533775000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}}],\"legSeq\":1}]},\"status\":[]},\"queryCriteria\":{\"carrierSCACCode\":\"COSU\",\"containerNumber\":\"FCIU5812619\",\"timeStamp\":\"\"},\"queryError\":[]}";
//		
//		JsonParser springParser = JsonParserFactory.getJsonParser();
//		Map<String, Object> map = springParser.parseMap(resp);
//
//		String mapArray[] = new String[map.size()];
//		System.out.println("Items found: " + mapArray.length);
////		for (int i=0; i < mapArray.length; i++) {
////			System.out.println("mapArray:" + mapArray[i]);
////		}
//
////		int i = 0;
//		int step = 0;
//		for (Map.Entry<String, Object> entry : map.entrySet()) {
////				System.out.println(entry.getKey() + " = " + entry.getValue() + "?" + entry.getValue().getClass());
////				i++;
//			
////				if ( entry.getValue() instanceof java.lang.String ) {
////					System.out.println("1:" + entry.getKey() + " = " + entry.getValue() + "?" + entry.getValue().getClass());
////				}else if ( entry.getValue() instanceof java.util.LinkedHashMap ) {
////					Map<String, Object> map2 = (Map)entry.getValue(); //springParser.parseMap(entry.getValue().toString());
////					for (Map.Entry<String, Object> entry2 : map2.entrySet()) {
////						System.out.println("2:" + entry2.getKey() + " = " + entry2.getValue() + "?" + entry2.getValue().getClass());
////					}
////				}else if ( entry.getValue() instanceof java.util.ArrayList ) {
////					List<Object> list = (List<Object>) entry.getValue();
////					for(Object o : list) {
////						System.out.println("3:" + o.getClass());
////					}
////				}
//				
//				step = jsonRecursionPrint(++step, entry);
////				jsonObjectPrint(entry);
//				
////				Map<String, Object> map2 = springParser.parseMap(entry.getValue().toString());
////				for (Map.Entry<String, Object> entry2 : map2.entrySet()) {
////					System.out.println(entry2.getKey() + " = " + entry2.getValue());
////				}
//				
//		}
		logger.info("End Postgresql To Oracle:");

	}
	
//	@Scheduled(fixedDelay = 1000 * 60 * 1, initialDelay = 1000)
//	@Scheduled(cron = "0 50 9 * * ?")
	@Scheduled(cron = "0 0 5 * * ?")
	public void selectRoutesSchedules() throws InterruptedException {
		
		Calendar calendar = new GregorianCalendar();
		
		

		int hour = calendar.get(Calendar.HOUR); // 12 hour clock
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int millisecond = calendar.get(Calendar.MILLISECOND);
		int callCount = 0;
//		logger.info("START ROUTES SCHEDULES To Oracle " + hour + ":" + minute + ":" + second + "." + millisecond);		
		
		try {
			
			if ( ebizDAO.svcStatus() < 1 ) {
//			if ( false ) {
				logger.info("ROUTES SCHEDULE SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				Thread.sleep(60000);
			} else {
				logger.info("START ROUTES SCHEDULE " + hour +":"  + minute+":" + second+"." + millisecond);
				
				JsonParser springParser = JsonParserFactory.getJsonParser();
				
	//			String tmp = properties.getSchedules().replaceAll(" ", "");
	//			System.out.println(tmp);
				Map<String, Object> props = springParser.parseMap(properties.getSchedules());
				RestTemplate restTemplate = new RestTemplate();
				int step2 = 0;
				
				
				for (Map.Entry<String, Object> entry : props.entrySet()) {
		//			step2 = jsonRecursionPrint(++step2, entry);
					if ("schedule".equals((entry.getKey()).toString())) {
						List list = (List) entry.getValue();
//						System.out.println(list);	
//logger.info("selectRoutesSchedules list :" + list);					
						for(int i=0; i<list.size(); i++) {
//logger.info("selectRoutesSchedules map i:" + i);
							Map map = (Map) list.get(i);
							if ("COSU".equals(map.get("carrier"))) {
								String carrier = (String) map.get("carrier");
								String appKey = this.prosAppKeyCOSU;
								String originator = this.prosOriginatorCOSU;
								List routes = (List) map.get("routes");
								for(int j=0; j<routes.size(); j++) {
//logger.info("selectRoutesSchedules routes j:" + j);
									Map route = (Map) routes.get(j);
									String startport = (String) route.get("startport");
									List endports = (List) route.get("endport");
//									System.out.println("startport:" + startport);
logger.info("startport:" + startport + ",endports size:" + endports.size());
									for(int k=0; k<endports.size(); k++) {
										
										
										String endport = (String) endports.get(k);
										logger.info("startport:" + startport + ",	endport:" + endport);
										
										String url=this.prosUrlSchedules+"/" + carrier + "?appKey=" +appKey + "&porID="+startport+"&fndID="+endport;
//	System.out.println("url:" + url);
	logger.info("callCount:" + (callCount++) + "url:" + url);
										try {
											String resp = restTemplate.getForObject(url, String.class);
			//								System.out.println("resp:" + resp);
//											logger.info(resp);
											Map<String, Object> parse = springParser.parseMap(resp);
											
	/*
											int step = 0;
											for (Map.Entry<String, Object> result : parse.entrySet()) {
												step = jsonRecursionPrint(++step, result);
											}
	*/
											
											List _routeGroupsList = (List) parse.get("routeGroupsList");
	//										System.out.println(_routeGroupsList);
											for (int rglidx=0; rglidx<_routeGroupsList.size(); rglidx++) {

//logger.info("selectRoutesSchedules _routeGroupsList rglidx:" + rglidx);												
												Map schedule_root = new HashMap();
												
												if (rglidx == 0) {
													
													
													Map _routeGroupsList$map = (Map) _routeGroupsList.get(rglidx);
												 
													
													Map<String, Object> _carrier = (Map) _routeGroupsList$map.get("carrier");
													String _scac = nullConvert(_carrier.get("scac")); //NADCA_3039
													String _name = nullConvert(_carrier.get("name")); //NADCA_312A
													String _url = nullConvert(_carrier.get("url")); //NADCA_312B
													String _updatedBy = nullConvert(_carrier.get("updatedBy")); //CTAAL_3413
//													System.out.println("_scac"+_scac);
//													System.out.println("_name"+_name);
//													System.out.println("_url"+_url);
//													System.out.println("_updatedBy"+_updatedBy);									
													schedule_root.put("NADCA_3039", _scac);
													schedule_root.put("NADCA_312A", _name);
													schedule_root.put("NADCA_312B", _url);
													schedule_root.put("CTAAL_3413", _updatedBy);
													
													Map _por = (Map) _routeGroupsList$map.get("por");
													Map<String, Object> _por_location = (Map) _por.get("location");
													String _por_unlocode = nullConvert(_por_location.get("unlocode")); //LOC88_3225
													String _por_uc_name = nullConvert(_por_location.get("uc_name")); //LOC88_3224
//													System.out.println("_por_unlocode"+_por_unlocode);
//													System.out.println("_por_uc_name"+_por_uc_name);																			
													schedule_root.put("LOC88_3225", _por_unlocode);
													schedule_root.put("LOC88_3224", _por_uc_name);
													
													Map _fnd = (Map) _routeGroupsList$map.get("fnd");
													Map<String, Object> _fnd_location = (Map) _fnd.get("location");
													String _fnd_unlocode = nullConvert(_fnd_location.get("unlocode")); //LOC88_3225
													String _fnd_uc_name = nullConvert(_fnd_location.get("uc_name")); //LOC88_3224
//													System.out.println("_fnd_unlocode"+_fnd_unlocode);
//													System.out.println("_fnd_uc_name"+_fnd_uc_name);										
													schedule_root.put("LOC7__3225", _fnd_unlocode);
													schedule_root.put("LOC7__3224", _fnd_uc_name);
													
													List _route = (List) _routeGroupsList$map.get("route");
													for(int ridx=0; ridx<_route.size(); ridx++) {
//logger.info("selectRoutesSchedules _route ridx:" + ridx);
														
														Map schedule_hd = new HashMap();
														
														schedule_hd.putAll(schedule_root);
														
														String xml_msg_id = ebizDAO.getCnEdiXmlMsgId();
														schedule_hd.put("XML_MSG_ID", xml_msg_id);
														schedule_hd.put("DOC_MSG_ID", xml_msg_id);
														schedule_hd.put("XMLDOC_SEQ", "1");
														schedule_hd.put("ORIGINATOR", originator);

//														schedule_hd.put("BGM___1001", "404");
														schedule_hd.put("BGM___1004", CargoSmartThread.substring(xml_msg_id, 2,14));
//														schedule_hd.put("BGM___1225", "9");
														schedule_hd.put("DTM1372380", CargoSmartThread.substring(xml_msg_id, 0,12));									
														
														
														Map _route$map = (Map) _route.get(ridx);
														
														String _route$map_carrierScac = nullConvert(_route$map.get("carrierScac")); //TDT20_3127
//														System.out.println("_route$map_carrierScac"+_route$map_carrierScac);
//														schedule_hd.put("TDT20_3127", _route$map_carrierScac);
														schedule_hd.put("FTXAAI4440", _route$map_carrierScac);
														
														
														Map<String, Object> _route$map_fnd = (Map) _route$map.get("fnd");
														Map<String, Object> _route$map_fnd_location = (Map) _route$map_fnd.get("location");										
														String _route$map_fnd_location_unicode = nullConvert(_route$map_fnd_location.get("unlocode")); //LOC20_3225
//														System.out.println("_route$map_fnd_location_unicode"+_route$map_fnd_location_unicode);
														String _route$map_fnd_location_name = nullConvert(_route$map_fnd_location.get("name")); //LOC20_3224
//														System.out.println("_route$map_fnd_location_name"+_route$map_fnd_location_name);
														schedule_hd.put("LOC20_3225", _route$map_fnd_location_unicode);
														schedule_hd.put("LOC20_3224", _route$map_fnd_location_name);
														
														Map<String, Object> _route$map_defaultCutoff = (Map) _route$map.get("defaultCutoff");
														String _route$map_defaultCutoff_cutoffTime = nullConvert(_route$map_defaultCutoff.get("cutoffTime")); //DTM180___9
														_route$map_defaultCutoff_cutoffTime = replace(substring(_route$map_defaultCutoff_cutoffTime, 0, 10), "-", "") + replace(substring(_route$map_defaultCutoff_cutoffTime, 11, 16), ":", "");
														
//														System.out.println("_route$map_defaultCutoff_cutoffTime"+_route$map_defaultCutoff_cutoffTime);
														schedule_hd.put("DTM180___9", _route$map_defaultCutoff_cutoffTime);
														
														//HD 인서트
														
														
														List _route$map_leg = (List) _route$map.get("leg");
														for(int rlidx=0; rlidx<_route$map_leg.size(); rlidx++) {
															
//logger.info("selectRoutesSchedules _route$map_leg rlidx:" + rlidx);
															
															Map schedule_tdt = new HashMap();
															
//															schedule_tdt.put("XML_MSG_ID", xml_msg_id);
//															schedule_tdt.put("XMLDOC_SEQ", ridx);
															schedule_tdt.putAll(schedule_hd);
															
															schedule_tdt.put("TDTDOC_SEQ", rlidx+1);
															
															Map _route$map_leg$map = (Map) _route$map_leg.get(rlidx);
														
															Map<String, Object> _route$map_leg$map_service = (Map) _route$map_leg$map.get("service");
															String _route$map_leg$map_service_code = nullConvert(_route$map_leg$map_service.get("code")); //FTXAAI4440
//															System.out.println("_route$map_leg$map_service_code"+_route$map_leg$map_service_code);
															String _route$map_leg$map_externalVoyageNumber = nullConvert(_route$map_leg$map.get("externalVoyageNumber")); //TDT20_8028
//															System.out.println("_route$map_leg$map_externalVoyageNumber"+_route$map_leg$map_externalVoyageNumber);
															String _route$map_leg$map_imoNumber = nullConvert(_route$map_leg$map.get("imoNumber")); //TDT20_8213
															
//															System.out.println("_route$map_leg$map_imoNumber"+_route$map_leg$map_imoNumber);
															Map<String, Object> _route$map_leg$map_vessel = (Map) _route$map_leg$map.get("vessel");
															String _route$map_leg$map_vessel_name = nullConvert(_route$map_leg$map_vessel.get("name")); //TDT20_8212
//															System.out.println("_route$map_leg$map_vessel_name"+_route$map_leg$map_vessel_name);
//															schedule_tdt.put("FTXAAI4440", _route$map_leg$map_service_code);
															schedule_tdt.put("TDT20_3127", nullConvert(_route$map.get("carrierScac")));
															
															schedule_tdt.put("FTXAAI444A", _route$map_leg$map_service_code);															
															schedule_tdt.put("FTXAAI444B", _route$map_leg$map_service_code);
															
															schedule_tdt.put("TDT20_8028", _route$map_leg$map_externalVoyageNumber);
															schedule_tdt.put("TDT20_8213", _route$map_leg$map_imoNumber);
															schedule_tdt.put("TDT20_8212", _route$map_leg$map_vessel_name);
															
															Map<String, Object> _route$map_leg$map_fromPoint = (Map) _route$map_leg$map.get("fromPoint");
															Map<String, Object> _route$map_leg$map_fromPoint_location = (Map) _route$map_leg$map_fromPoint.get("location");										
															String _route$map_leg$map_fromPoint_location_unlocode = nullConvert(_route$map_leg$map_fromPoint_location.get("unlocode")); //LOC9__3225
//															System.out.println("_route$map_leg$map_fromPoint_location_unlocode"+_route$map_leg$map_fromPoint_location_unlocode);										
															String _route$map_leg$map_fromPoint_location_name = nullConvert(_route$map_leg$map_fromPoint_location.get("name")); //LOC9__3224
//															System.out.println("_route$map_leg$map_fromPoint_location_name"+_route$map_leg$map_fromPoint_location_name);										
															String _route$map_leg$map_fromPoint_etd = nullConvert(_route$map_leg$map_fromPoint.get("etd")); //DTM133___9
															_route$map_leg$map_fromPoint_etd = replace(substring(_route$map_leg$map_fromPoint_etd, 0, 10), "-", "") + replace(substring(_route$map_leg$map_fromPoint_etd, 11, 16), ":", "");
															
															System.out.println("_route$map_leg$map_fromPoint_etd"+_route$map_leg$map_fromPoint_etd);
															schedule_tdt.put("LOC9__3225", _route$map_leg$map_fromPoint_location_unlocode);
															schedule_tdt.put("LOC9__3224", _route$map_leg$map_fromPoint_location_name);
															schedule_tdt.put("DTM133___9", _route$map_leg$map_fromPoint_etd);
															
															Map<String, Object> _route$map_leg$map_toPoint = (Map) _route$map_leg$map.get("toPoint");
															Map<String, Object> _route$map_leg$map_toPoint_location = (Map) _route$map_leg$map_toPoint.get("location");										
															String _route$map_leg$map_toPoint_location_unlocode = nullConvert(_route$map_leg$map_toPoint_location.get("unlocode")); //LOC11_3225
//															System.out.println("_route$map_leg$map_toPoint_location_unlocode"+_route$map_leg$map_toPoint_location_unlocode);										
															String _route$map_leg$map_toPoint_location_name = nullConvert(_route$map_leg$map_toPoint_location.get("name")); //LOC11_3224
//															System.out.println("_route$map_leg$map_toPoint_location_name"+_route$map_leg$map_toPoint_location_name);										
															String _route$map_leg$map_toPoint_eta = nullConvert(_route$map_leg$map_toPoint.get("eta")); //DTM132__11
															_route$map_leg$map_toPoint_eta = replace(substring(_route$map_leg$map_toPoint_eta, 0, 10), "-", "") + replace(substring(_route$map_leg$map_toPoint_eta, 11, 16), ":", "");
															
															System.out.println("_route$map_leg$map_toPoint_eta"+_route$map_leg$map_toPoint_eta); 
															schedule_tdt.put("LOC11_3225", _route$map_leg$map_toPoint_location_unlocode);
															schedule_tdt.put("LOC11_3224", _route$map_leg$map_toPoint_location_name);
															schedule_tdt.put("DTM132__11", _route$map_leg$map_toPoint_eta);
															
															schedule_tdt.put("DTM132___9", "");
															schedule_tdt.put("DTM222___9", "");
															schedule_tdt.put("LOC13_3225", "");
															schedule_tdt.put("LOC13_3224", "");
															
															
																														
															
															
															
														
															//TDT 인서트

															/*
		_scacCOSU
		_nameCOSCO SHIPPING
		_urlhttp://lines.coscoshipping.com
		_updatedByCSDev
		_por_unlocodeKRPUS
		_por_uc_nameBUSAN
		_fnd_unlocodeDZALG
		_fnd_uc_nameALGIERS
		_route$map_carrierScacCOSU
		_route$map_leg$map_service_codeAEM3
		_route$map_leg$map_externalVoyageNumber0BX7BW1MA
		_route$map_leg$map_imoNumber9705067
		_route$map_leg$map_vessel_nameCMA CGM TIGRIS
		_route$map_leg$map_fromPoint_location_unlocodeKRPUS
		_route$map_leg$map_fromPoint_location_nameBusan
		_route$map_leg$map_fromPoint_etd2020-08-16T00:00:00.000Z
		_route$map_leg$map_toPoint_location_unlocodeGRPIR
		_route$map_leg$map_toPoint_location_namePiraeus
		_route$map_leg$map_toPoint_etd2020-09-27T00:00:00.000Z
		_route$map_fnd_location_unicodeDZALG
		_route$map_fnd_location_nameAlgiers
		_route$map_defaultCutoff_cutoffTime2020-08-15T04:00:00.000Z
															 */
															
															logger.info("schedule_tdt:"+schedule_tdt);
															ebizDAO.setiftsai__tdt(schedule_tdt);
														}
														

														
														logger.info("schedule_hd:"+schedule_hd);
														ebizDAO.setiftsai___hd(schedule_hd);
														ebizDAO.setScheduleMapinTbl(schedule_hd);
													}
												}
												
												//schedule_hd = null;
											}
											
											Thread.sleep(1000);
										} catch(Exception e) {
											logger.error("error", e);
										}
										
									}
								}
							}
						}
					}
	
				}
				
				logger.info("END ROUTES SCHEDULE");
			}
		} catch (Exception e){
			logger.error("error", e);
		}
		
//		SchedulesProperties properties = new SchedulesProperties();
//		System.out.println("aaa:" + properties.getSchedules());
//		System.out.println(map2);
		



//		String url=this.prosUrlSchedules+"/" + "COSU" + "?appKey=" +this.prosAppKeyCOSU + "&porID=KRPUS&fndID=USLAX";
				
//		String resp = "";
				
//		RestTemplate restTemplate = new RestTemplate();
//		String resp = restTemplate.getForObject(url, String.class);
//		resp = "{\"dataRange\":{\"departureFrom\":\"2020-06-11T00:00:00.000Z\",\"departureTo\":\"2020-06-24T23:59:59.999Z\"},\"requestRefNo\":\"af38921c-b420-4292-b1d0-34ab7a303be8\",\"routeGroupsList\":[{\"identification\":{\"dataSourceType\":\"SSM2014\",\"requestRefNo\":\"af38921c-b420-4292-b1d0-34ab7a303be8\"},\"carrier\":{\"_id\":\"557ab159395a5b8cdab49526\",\"carrierID\":2,\"name\":\"COSCO SHIPPING\",\"scac\":\"COSU\",\"shortName\":\"COSCO SHIPPING\",\"url\":\"http://lines.coscoshipping.com\",\"enabled\":true,\"ssmEnabled\":true,\"sortingKey\":\"coscon\",\"updatedAt\":\"2019-07-10T07:27:21.498Z\",\"updatedBy\":\"CSDev\",\"analyticsEnabled\":true},\"por\":{\"location\":{\"_id\":\"5ee1ac495f94ff0016204478\",\"source\":\"CS4\",\"unlocode\":\"KRPUS\",\"name\":\"Busan\",\"uc_name\":\"BUSAN\",\"geo\":[129.075642,35.179554],\"locationID\":\"P358\",\"csID\":358,\"csCityID\":2535304,\"type\":\"PORT\",\"fullName\":\"Busan, South Korea\",\"timezone\":\"Asia/Seoul\",\"refreshDateTime\":\"2020-06-11T04:00:09.791Z\"}},\"fnd\":{\"location\":{\"_id\":\"5ee1ac445f94ff00162040a3\",\"source\":\"CS4\",\"unlocode\":\"USLAX\",\"name\":\"Los Angeles\",\"uc_name\":\"LOS ANGELES\",\"geo\":[-118.25717,33.745753],\"locationID\":\"P426\",\"csID\":426,\"csCityID\":2532344,\"type\":\"PORT\",\"fullName\":\"Los Angeles, California, United States\",\"timezone\":\"America/Los_Angeles\",\"refreshDateTime\":\"2020-06-11T04:00:04.554Z\"}},\"route\":[{\"csRouteID\":2280240331,\"csPointPairID\":7295871,\"carrierScac\":\"COSU\",\"por\":{\"location\":{\"locationID\":\"P358\",\"name\":\"Busan\",\"unlocode\":\"KRPUS\",\"csID\":358,\"csCityId\":2535304,\"timezone\":\"Asia/Seoul\",\"facility\":{\"name\":\"Busan New Container Terminal\",\"code\":\"PUS83\",\"id\":11674,\"type\":\"Terminal\"}},\"etd\":\"2020-06-18T00:00:00.000Z\"},\"fnd\":{\"location\":{\"locationID\":\"P426\",\"name\":\"Los Angeles\",\"unlocode\":\"USLAX\",\"csID\":426,\"csCityId\":2532344,\"timezone\":\"America/Los_Angeles\",\"facility\":{\"name\":\"Long Beach Container Terminal , LLC\",\"code\":\"LGB08\",\"id\":17306,\"type\":\"Terminal\"}},\"eta\":\"2020-06-29T00:00:00.000Z\",\"arrivalTimeLocation\":{\"locationID\":\"P425\",\"name\":\"Long Beach\",\"unlocode\":\"USLGB\",\"csID\":425,\"csCityId\":2533284,\"timezone\":\"America/Los_Angeles\"}},\"transitTime\":11,\"direct\":true,\"importHaulage\":\"BOTH\",\"exportHaulage\":\"BOTH\",\"touchTime\":\"2020-06-10T15:00:26.000Z\",\"leg\":[{\"fromPoint\":{\"voyageStop\":{\"voyageStopId\":24948841,\"skip\":false},\"location\":{\"locationID\":\"P358\",\"name\":\"Busan\",\"unlocode\":\"KRPUS\",\"csID\":358,\"csCityId\":2535304,\"timezone\":\"Asia/Seoul\"},\"defaultCutoff\":\"2020-06-17T06:00:00.000Z\",\"etd\":\"2020-06-18T00:00:00.000Z\",\"gmtEtd\":\"2020-06-17T15:00:00.000Z\"},\"toPoint\":{\"voyageStop\":{\"voyageStopId\":24948842,\"skip\":false},\"location\":{\"locationID\":\"P425\",\"name\":\"Long Beach\",\"unlocode\":\"USLGB\",\"csID\":425,\"csCityId\":2533284,\"timezone\":\"America/Los_Angeles\"},\"eta\":\"2020-06-29T00:00:00.000Z\",\"gmtEta\":\"2020-06-29T07:00:00.000Z\"},\"transportMode\":\"VESSEL\",\"service\":{\"serviceID\":10025085,\"code\":\"AAC4\",\"name\":\"OOCL U.S. Southwest Coast Express Service\"},\"vessel\":{\"vesselGID\":\"V000000102\",\"name\":\"OOCL SOUTHAMPTON\",\"code\":\"QBQ\",\"IMO\":9310240},\"imoNumber\":9310240,\"externalVoyageNumber\":\"094E\",\"transitTime\":11}],\"transportSummary\":\"VESSEL:10025085:V000000102:094E\",\"defaultCutoff\":{\"cutoffTime\":\"2020-06-17T06:00:00.000Z\"},\"isPossibleDirect\":true,\"isUncertainTransitTime\":false}]}]}";
		
//		resp = "\"";
//		JsonParser springParser = JsonParserFactory.getJsonParser();
//		Map<String, Object> map = springParser.parseMap(resp);
//
//		String mapArray[] = new String[map.size()];
//		System.out.println("Items found: " + mapArray.length);
//		for (int i=0; i < mapArray.length; i++) {
//			System.out.println("mapArray:" + mapArray[i]);
//		}

//		int i = 0;
//		int step = 0;
//		for (Map.Entry<String, Object> entry : map.entrySet()) {
//				System.out.println(entry.getKey() + " = " + entry.getValue() + "?" + entry.getValue().getClass());
//				i++;
			
//				if ( entry.getValue() instanceof java.lang.String ) {
//					System.out.println("1:" + entry.getKey() + " = " + entry.getValue() + "?" + entry.getValue().getClass());
//				}else if ( entry.getValue() instanceof java.util.LinkedHashMap ) {
//					Map<String, Object> map2 = (Map)entry.getValue(); //springParser.parseMap(entry.getValue().toString());
//					for (Map.Entry<String, Object> entry2 : map2.entrySet()) {
//						System.out.println("2:" + entry2.getKey() + " = " + entry2.getValue() + "?" + entry2.getValue().getClass());
//					}
//				}else if ( entry.getValue() instanceof java.util.ArrayList ) {
//					List<Object> list = (List<Object>) entry.getValue();
//					for(Object o : list) {
//						System.out.println("3:" + o.getClass());
//					}
//				}
				
//				step = jsonRecursionPrint(++step, entry);
//				jsonObjectPrint(entry);
				
//				Map<String, Object> map2 = springParser.parseMap(entry.getValue().toString());
//				for (Map.Entry<String, Object> entry2 : map2.entrySet()) {
//					System.out.println(entry2.getKey() + " = " + entry2.getValue());
//				}
				
//		}
//		logger.info("End ROUTES SCHEDULES");

	}
	
	
//	public List getRouteList() {
//		List list = new ArrayList();
//		Map map = new HashMap();
//		map.put("POL", "P358"); map.put("POD", "P425");
//		
//		
//		return list;
//	}
	
/*	
	public int jsonRecursionPrint(int step, Object object) {
		System.out.println("step:" + step);
		try {
		String tab = "";
		for(int i=0; i<step; i++) {tab = tab + "    ";}
		
			if (step > 5) {
				return step;
			}else {		
				System.out.println(object.getClass());
				if ( object instanceof java.lang.String ) {
					System.out.println(tab + object + " ?" + object.getClass());
	//				System.out.println("string:" + object.toString());			
				} else if ( object instanceof java.util.LinkedHashMap ) {
					System.out.println(object.getClass());
					Map<String, Object> map = (Map) object;
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						System.out.println(tab + entry.getKey() + " ?" + entry.getValue().getClass());
						step = jsonRecursionPrint(++step, entry);
					}
				} else if ( object instanceof java.util.ArrayList ) {
					List list = (List)object;
					for(int i=0; i< list.size(); i++) {
						System.out.println(tab + i + " ?" + object.getClass());
						Object o = list.get(i);
						step = jsonRecursionPrint(++step, o);
					}
	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return --step;
	}
*/
	
//	@Scheduled(fixedDelay = 100000, initialDelay = 1000)
	@Scheduled(cron = "0 0 4 * * ?")
	public void selectOwnVslSchMSCList() throws InterruptedException {
		

		Calendar calendar = new GregorianCalendar();
		
		

		int hour = calendar.get(Calendar.HOUR); // 12 hour clock
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int millisecond = calendar.get(Calendar.MILLISECOND);

//		logger.info("START ROUTES SCHEDULES To Oracle " + hour + ":" + minute + ":" + second + "." + millisecond);		
		
		try {
			
			if ( ebizDAO.svcStatus() < 1 ) {
//			if ( false ) {
				logger.info("MSCU SCHEDULE SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				Thread.sleep(60000);
			} else {
				logger.info("START MSCU SCHEDULE " + hour +":"  + minute+":" + second+"." + millisecond);
				
				
				
				String originator = this.prosOriginatorMSCU;
				
				List list = ownerDAO.selectOwnVslSchMSCList();
				for(int i=0; i<list.size(); i++){
					Map map = (Map) list.get(i);
					System.out.println("map" + map);
					Map schedule_hd = new HashMap();
					String xml_msg_id = ebizDAO.getCnEdiXmlMsgId();
					schedule_hd.put("XML_MSG_ID", xml_msg_id);
					schedule_hd.put("DOC_MSG_ID", xml_msg_id);
					schedule_hd.put("XMLDOC_SEQ", "1");
//					schedule_hd.put("BGM___1001", "404");
					schedule_hd.put("BGM___1004", CargoSmartThread.substring(xml_msg_id, 2,14));
//					schedule_hd.put("BGM___1225", "9");
					schedule_hd.put("DTM1372380", CargoSmartThread.substring(xml_msg_id, 0,12));		
					
					schedule_hd.put("ORIGINATOR", originator);
					schedule_hd.put("FTXAAI4440", nullConvert(map.get("svc")));
					
					schedule_hd.put("LOC88_3225", nullConvert(map.get("start_port_code")));
					schedule_hd.put("LOC88_3224", nullConvert(map.get("start_port_name")));
					schedule_hd.put("LOC7__3225", nullConvert(map.get("end_port_code")));
					schedule_hd.put("LOC7__3224", nullConvert(map.get("end_port_name")));
					
					schedule_hd.put("NADCA_3039", "MSCU");
					schedule_hd.put("NADCA_312A", "MSC");
					schedule_hd.put("NADCA_312B", "");
					schedule_hd.put("CTAAL_3413", "");
					
					

					int tdtdoc_seq = 1;
					{
						Map schedule_tdt = new HashMap();
						schedule_tdt.putAll(schedule_hd);
						schedule_tdt.put("TDTDOC_SEQ", tdtdoc_seq++);
						schedule_tdt.put("TDT20_8028", nullConvert(map.get("voyage_no")));
						schedule_tdt.put("TDT20_3127", "MSCU");
						schedule_tdt.put("TDT20_8213", "");
						schedule_tdt.put("TDT20_8212", nullConvert(map.get("vsl_name")));
						
						schedule_tdt.put("LOC9__3225", nullConvert(map.get("start_port_code")));
						schedule_tdt.put("LOC9__3224", nullConvert(map.get("start_port_name")));
						schedule_tdt.put("DTM132___9", nullConvert(map.get("start_date")));
						schedule_tdt.put("DTM133___9", "");
						
						schedule_tdt.put("LOC11_3225", nullConvert(map.get("end_port_code")));
						schedule_tdt.put("LOC11_3224", nullConvert(map.get("end_port_name")));
						schedule_tdt.put("DTM132__11", nullConvert(map.get("end_date")));
						
						schedule_tdt.put("LOC20_3225", nullConvert(map.get("end_port_code")));
						schedule_tdt.put("LOC20_3224", nullConvert(map.get("end_port_name")));
						
						schedule_tdt.put("DTM222___9", nullConvert(map.get("doc_closing_date")));
						schedule_tdt.put("DTM180___9", nullConvert(map.get("cargo_closing_date")));
						
						schedule_tdt.put("FTXAAI444A", "");
						schedule_tdt.put("FTXAAI444B", "");
						schedule_tdt.put("LOC13_3225", "");
						schedule_tdt.put("LOC13_3224", "");
						
						logger.info("schedule_tdt:"+schedule_tdt);
						ebizDAO.setiftsai__tdt(schedule_tdt);
					}
					/*
					if ( !"".equals(nullConvert(map.get("ts1_port_code"))) && !"None".equals(nullConvert(map.get("ts1_port_code")))){
						Map schedule_tdt = new HashMap();
						schedule_tdt.putAll(schedule_hd);
						schedule_tdt.put("TDTDOC_SEQ", tdtdoc_seq++);
						schedule_tdt.put("TDT20_8028", "TBA");
						schedule_tdt.put("TDT20_3127", "MSCU");
						schedule_tdt.put("TDT20_8213", "");
						schedule_tdt.put("TDT20_8212", "");
						
						schedule_tdt.put("LOC9__3225", "");
						schedule_tdt.put("LOC9__3224", "");
						schedule_tdt.put("DTM132___9", "");
						schedule_tdt.put("DTM133___9", "");
						
						schedule_tdt.put("LOC11_3225", "");
						schedule_tdt.put("LOC11_3224", "");
						schedule_tdt.put("DTM132__11", "");
						
						schedule_tdt.put("LOC20_3225", "");
						schedule_tdt.put("LOC20_3224", "");
						
						schedule_tdt.put("DTM222___9", "");
						schedule_tdt.put("DTM180___9", "");
						
						schedule_tdt.put("FTXAAI444A", "");
						schedule_tdt.put("FTXAAI444B", "");
						schedule_tdt.put("LOC13_3225", map.get("ts1_port_code"));
						schedule_tdt.put("LOC13_3224", map.get("ts1_port_name"));
						
						logger.info("schedule_tdt:"+schedule_tdt);
						ebizDAO.setiftsai__tdt(schedule_tdt);
					}
	
					if ( !"".equals(nullConvert(map.get("ts2_port_code"))) && !"None".equals(nullConvert(map.get("ts2_port_code")))){
						Map schedule_tdt = new HashMap();
						schedule_tdt.putAll(schedule_hd);
						schedule_tdt.put("TDTDOC_SEQ", tdtdoc_seq++);
						schedule_tdt.put("TDT20_8028", "TBA");
						schedule_tdt.put("TDT20_3127", "MSCU");
						schedule_tdt.put("TDT20_8213", "");
						schedule_tdt.put("TDT20_8212", "");
						
						schedule_tdt.put("LOC9__3225", "");
						schedule_tdt.put("LOC9__3224", "");
						schedule_tdt.put("DTM132___9", "");
						schedule_tdt.put("DTM133___9", "");
						
						schedule_tdt.put("LOC11_3225", "");
						schedule_tdt.put("LOC11_3224", "");
						schedule_tdt.put("DTM132__11", "");
						
						schedule_tdt.put("LOC20_3225", "");
						schedule_tdt.put("LOC20_3224", "");
						
						schedule_tdt.put("DTM222___9", "");
						schedule_tdt.put("DTM180___9", "");
						
						schedule_tdt.put("FTXAAI444A", "");
						schedule_tdt.put("FTXAAI444B", "");
						schedule_tdt.put("LOC13_3225", map.get("ts2_port_code"));
						schedule_tdt.put("LOC13_3224", map.get("ts2_port_name"));
						
						logger.info("schedule_tdt:"+schedule_tdt);
						ebizDAO.setiftsai__tdt(schedule_tdt);
					}					

					if ( !"".equals(nullConvert(map.get("ts3_port_code"))) && !"None".equals(nullConvert(map.get("ts3_port_code")))){
						Map schedule_tdt = new HashMap();
						schedule_tdt.putAll(schedule_hd);
						schedule_tdt.put("TDTDOC_SEQ", tdtdoc_seq++);
						schedule_tdt.put("TDT20_8028", "TBA");
						schedule_tdt.put("TDT20_3127", "MSCU");
						schedule_tdt.put("TDT20_8213", "");
						schedule_tdt.put("TDT20_8212", "");
						
						schedule_tdt.put("LOC9__3225", "");
						schedule_tdt.put("LOC9__3224", "");
						schedule_tdt.put("DTM132___9", "");
						schedule_tdt.put("DTM133___9", "");
						
						schedule_tdt.put("LOC11_3225", "");
						schedule_tdt.put("LOC11_3224", "");
						schedule_tdt.put("DTM132__11", "");
						
						schedule_tdt.put("LOC20_3225", "");
						schedule_tdt.put("LOC20_3224", "");
						
						schedule_tdt.put("DTM222___9", "");
						schedule_tdt.put("DTM180___9", "");
						
						schedule_tdt.put("FTXAAI444A", "");
						schedule_tdt.put("FTXAAI444B", "");
						schedule_tdt.put("LOC13_3225", map.get("ts3_port_code"));
						schedule_tdt.put("LOC13_3224", map.get("ts3_port_name"));
						
						logger.info("schedule_tdt:"+schedule_tdt);
						ebizDAO.setiftsai__tdt(schedule_tdt);
					}					
					*/
					logger.info("schedule_hd:"+schedule_hd);
					ebizDAO.setiftsai___hd(schedule_hd);
					ebizDAO.setScheduleMapinTbl(schedule_hd);
					
				}
				
				
													
													
				logger.info("END ROUTES SCHEDULE");
			}
		} catch (Exception e){
			logger.error("error", e);
		}
		
	}
	
	
	public int jsonRecursionPrint(int step, Map.Entry<String, Object> entry) {
//		System.out.println("step:" + step);
		String tab = "";
		for(int i=0; i<step; i++) {tab = tab + "    ";}

		if (step > 100) {
			return step;
		}else {
			if ( entry.getValue() instanceof java.lang.String ) {
				System.out.println(tab + entry.getKey() + " : " + entry.getValue() + " ?" + entry.getValue().getClass());
			}else if ( entry.getValue() instanceof java.util.LinkedHashMap ) {
				System.out.println(tab + entry.getKey() + " ?" + entry.getValue().getClass());
				Map<String, Object> map = (Map)entry.getValue(); //springParser.parseMap(entry.getValue().toString());
//				step++;
				for (Map.Entry<String, Object> entry2 : map.entrySet()) {
					step = jsonRecursionPrint(++step, entry2);
				}
			}else if ( entry.getValue() instanceof java.util.ArrayList ) {
				System.out.println(tab + entry.getKey() + " ?" + entry.getValue().getClass());
				List<Object> list = (List<Object>) entry.getValue();
				for(int i=0; i<list.size(); i++) {
					++step;
//					tab = tab + "    ";
					tab = ""; 
					for(int j=0; j<step; j++) {tab = tab + "    ";}
					System.out.println(tab + "" + i);
					Object o = list.get(i);
					step = jsonObjectPrint(++step, o);
					--step;
//					++step;
////					System.out.println("object:"+o +">>" + o.getClass());
////					jsonObjectPrint(step++, o);
//					if ( o instanceof java.lang.String ) {
//						System.out.println(tab + entry.getKey() + " : " + entry.getValue() + " ?" + entry.getValue().getClass());
//					} else if ( o instanceof java.util.LinkedHashMap ) {
////						System.out.println("LinkedHashMap:" + o.toString());
//						System.out.println(tab + entry.getKey() + " ?" + entry.getValue().getClass());
//						Map<String, Object> map = (Map) o;
//						for (Map.Entry<String, Object> entry1 : map.entrySet()) {
//							step = jsonRecursionPrint(++step, entry1);
//						}
//					} else if ( o instanceof java.util.ArrayList ) {
////						System.out.println("ArrayList:" + o.toString());
//						for(Object o2 : (List) o) {
////							System.out.println("3:" + o2.getClass());
//							step = jsonObjectPrint(++step, o2);
//						}
//					} else {
//						System.out.println(tab + entry.getKey() + " : " + entry.getValue() + " ?" + entry.getValue().getClass());
//					}
//					--step;
				}
			} else {
//				if (entry.getValue() == null) System.out.println("null value");
//				if (entry.getValue().equals("null")) System.out.println("null value2");
//				System.out.println(tab + entry.getKey() + " : " + entry.getValue() + " ?");
				if (entry.getValue() == null ) {
					System.out.println(tab + entry.getKey() + " : " + entry.getValue() + " ?" + "null");
				}else {
					System.out.println(tab + entry.getKey() + " : " + entry.getValue() + " ?" + entry.getValue().getClass());
				}
				
			}
		}
		return --step;
	}

	public int jsonObjectPrint(int step, Object object) {
		String tab = "";
		for(int i=0; i<step; i++) {tab = tab + "    ";}

		if (step > 100) {
			return step;
		}else {		
//			System.out.println(object.getClass());
			if ( object instanceof java.lang.String ) {
				System.out.println(tab + object.toString());
			} else if ( object instanceof java.util.LinkedHashMap ) {
//				System.out.println("LinkedHashMap:" + object.toString());
				Map<String, Object> map = (Map) object;
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					step = jsonRecursionPrint(++step, entry);
				}
			} else if ( object instanceof java.util.ArrayList ) {
//				System.out.println("ArrayList:" + object.toString());
				for(Object o : (List) object) {
//					System.out.println("3:" + o.getClass());
					step = jsonObjectPrint(step++, o);
				}
			} else {
				System.out.println(tab + object.toString());
			}
		}
		return --step;
	}

	public static String nullConvert(Object src) {
		// if (src != null &&
		// src.getClass().getName().equals("java.math.BigDecimal")) {
		if (src != null && src instanceof java.math.BigDecimal) {
			return ((BigDecimal) src).toString();
		}

		if (src == null || src.equals("null")) {
			return "";
		} else {
			return (String.valueOf(src)).trim();
		}
	}

	public static String nullConvert(String src) {

		if (src == null || src.equals("null") || "".equals(src) || " ".equals(src)) {
			return "";
		} else {
			return src.trim();
		}
	}	
	
    public static String replace(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        String srcStr  = source;
        
        while (srcStr.indexOf(subject) >= 0) {
            preStr = srcStr.substring(0, srcStr.indexOf(subject));
            nextStr = srcStr.substring(srcStr.indexOf(subject) + subject.length(), srcStr.length());
            srcStr = nextStr;
            rtnStr.append(preStr).append(object);
        }
        rtnStr.append(nextStr);
        return rtnStr.toString();
    }	
	
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return "";
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }    
	
	public static void main(String[] args) {
		
		String a = "2020-08-17T04:00:00.000Z";
//		String a = "";
		
		
		System.out.println("?" + CargoSmartThread.replace(CargoSmartThread.substring(a, 0, 10), "-", ""));
		System.out.println("?" + CargoSmartThread.replace(CargoSmartThread.substring(a, 11, 16), ":", ""));
		
		System.out.println(a.substring(0, 10).replaceAll("-", ""));
		System.out.println(a.substring(11, 16).replaceAll(":", ""));
		
		
		System.out.println(CargoSmartThread.replace(a,"-",""));
		
		
		
		String b = "20200327072019F597";
//		schedule_hd.put("BGM___1004", xml_msg_id.substring(2,14));
////		schedule_hd.put("BGM___1225", "9");
//		schedule_hd.put("DTM1372380", xml_msg_id.substring(1,12));	
		
		System.out.println(CargoSmartThread.substring(b, 2, 14));
		System.out.println(CargoSmartThread.substring(b, 0, 12));
		
	}
}
