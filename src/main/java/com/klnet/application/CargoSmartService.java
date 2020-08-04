package com.klnet.application;

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
	@Value("${cargosmart.url.tracking}")
	private String prosUrlTracking;
	
	@Autowired
	SchedulesProperties properties;
	/*
	 * https://apis.cargosmart.com/openapi/schedules/routeschedules/COSU?appKey=69338670-9c07-11ea-ae83-7956f3992d4c&porID=KRPUS&fndID=USLAX
	 * https://apis.cargosmart.com/openapi/cargoes/tracking/COSU?appKey=69338670-9c07-11ea-ae83-7956f3992d4c&cntrNumber=FCIU5812619&scacCode=COSU 
	 */
	
			
//	@Scheduled(fixedDelay = 1000 * 60 * 2, initialDelay = 1000)
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


		String url=this.prosUrlTracking+"/" + "COSU" + "?appKey=" +this.prosAppKeyCOSU + "&scacCode=COSU" + "&cntrNumber=FCIU5812619";
				
		
		logger.info(url);
//		RestTemplate restTemplate = new RestTemplate();
//		String resp = restTemplate.getForObject(url, String.class);
		
		String resp = "{\"containerDetail\":{\"_package\":{\"pieceCount\":80,\"pieceCountUnit\":\"Drum\"},\"containerNumber\":{\"CSContainerSizeType\":\"\",\"carrCntrSizeType\":\"\",\"containerCheckDigit\":\"9\",\"containerNumber\":\"FCIU581261\",\"grossWeight\":{\"weight\":21920,\"weightUnit\":{\"value\":\"KGS\"}},\"haulage\":null,\"isSOC\":false,\"seal\":[],\"trafficMode\":null},\"detentionAndDemurrage\":null,\"doorDelivery\":[],\"event\":[{\"CSEvent\":{\"CSEventCode\":\"CS070\",\"dimentionType\":\"Port\",\"dimentionValue\":\"FirstPOL\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"ssm_vsl_dep_first_pol\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":9,\"month\":4,\"seconds\":35,\"time\":1589501375000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589501375000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":9,\"minutes\":9,\"month\":4,\"seconds\":35,\"time\":1589533775000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589533775000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":27,\"month\":4,\"seconds\":46,\"time\":1589502466000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589502466000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":{\"UNLocationCode\":\"KRKAN\",\"mutuallyDefinedCode\":\"\",\"schedKDCode\":\"58031\",\"schedKDType\":{\"value\":\"K\"}},\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Gwangyang\"},\"mode\":\"\"},{\"CSEvent\":{\"CSEventCode\":\"CS060\",\"dimentionType\":\"\",\"dimentionValue\":\"\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"Loaded on board at first port of load\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":14,\"day\":4,\"hours\":17,\"minutes\":15,\"month\":4,\"seconds\":0,\"time\":1589476500000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589476500000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":2,\"minutes\":15,\"month\":4,\"seconds\":0,\"time\":1589508900000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589508900000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":14,\"day\":4,\"hours\":17,\"minutes\":42,\"month\":4,\"seconds\":26,\"time\":1589478146000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589478146000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Kwangyang\"},\"mode\":\"Vessel\"},{\"CSEvent\":{\"CSEventCode\":\"CS040\",\"dimentionType\":\"\",\"dimentionValue\":\"\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"Outbound intermodal leg arrived\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":10,\"day\":0,\"hours\":23,\"minutes\":27,\"month\":4,\"seconds\":0,\"time\":1589153220000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589153220000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":11,\"day\":1,\"hours\":8,\"minutes\":27,\"month\":4,\"seconds\":0,\"time\":1589185620000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589185620000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":11,\"day\":1,\"hours\":0,\"minutes\":0,\"month\":4,\"seconds\":16,\"time\":1589155216000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589155216000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Kwangyang\"},\"mode\":\"Truck\"},{\"CSEvent\":{\"CSEventCode\":\"CS020\",\"dimentionType\":\"\",\"dimentionValue\":\"\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"Full received by carrier\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":10,\"day\":0,\"hours\":23,\"minutes\":27,\"month\":4,\"seconds\":0,\"time\":1589153220000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589153220000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":11,\"day\":1,\"hours\":8,\"minutes\":27,\"month\":4,\"seconds\":0,\"time\":1589185620000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589185620000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":11,\"day\":1,\"hours\":0,\"minutes\":0,\"month\":4,\"seconds\":16,\"time\":1589155216000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589155216000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Korea International Terminal\"},\"mode\":\"Truck\"},{\"CSEvent\":{\"CSEventCode\":\"CS010\",\"dimentionType\":\"\",\"dimentionValue\":\"\",\"estActIndicator\":\"A\"},\"carrEventCode\":\"Empty picked up, but not full return\",\"eventDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":9,\"day\":6,\"hours\":8,\"minutes\":11,\"month\":4,\"seconds\":0,\"time\":1589011860000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589011860000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":9,\"day\":6,\"hours\":17,\"minutes\":11,\"month\":4,\"seconds\":0,\"time\":1589044260000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589044260000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},\"eventDescription\":\"\",\"eventReceivedDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":9,\"day\":6,\"hours\":8,\"minutes\":25,\"month\":4,\"seconds\":8,\"time\":1589012708000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589012708000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":null},\"location\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":null,\"locationName\":\"Korea International Terminal\"},\"mode\":\"Truck\"}],\"externalReference\":[{\"CSReferenceType\":{\"value\":\"BL\"},\"referenceDescription\":\"Bill of Lading Number\",\"referenceNumber\":\"6262443190\"},{\"CSReferenceType\":{\"value\":\"BKG\"},\"referenceDescription\":\"Booking Number\",\"referenceNumber\":\"6262443190\"}],\"route\":{\"FND\":{\"CSStandardCity\":{\"CSContinentCode\":\"EU\",\"CSCountryCode\":\"PL\",\"CSParentCityID\":2547944,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Gdansk\",\"country\":\"Poland\",\"county\":\"\",\"locationCode\":null,\"state\":\"Pomorskie\"},\"facility\":{\"facilityCode\":\"GDN02\",\"facilityName\":\"Deepwater Container Terminal Gdansk\"},\"locationName\":\"\"},\"POR\":{\"CSStandardCity\":{\"CSContinentCode\":\"AS\",\"CSCountryCode\":\"KR\",\"CSParentCityID\":1902190,\"CSStateCode\":\"\"},\"cityDetails\":{\"city\":\"Kwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"state\":\"Chollanam-do\"},\"facility\":{\"facilityCode\":\"KAN05\",\"facilityName\":\"Korea International Terminal\"},\"locationName\":\"\"},\"arrivalAtFinalHub\":[{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":25,\"day\":4,\"hours\":7,\"minutes\":0,\"month\":5,\"seconds\":0,\"time\":1593068400000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1593068400000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"indicator\":{\"value\":\"E\"},\"locDT\":{\"CSTimeZone\":\"\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":25,\"day\":4,\"hours\":9,\"minutes\":0,\"month\":5,\"seconds\":0,\"time\":1593075600000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1593075600000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"CES\"}}],\"firstPOL\":{\"facility\":{\"facilityCode\":\"KAN05\",\"facilityName\":\"Korea International Terminal\"},\"port\":{\"CSCountryCode\":\"KR\",\"CSPortID\":360,\"city\":\"Kwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":{\"UNLocationCode\":\"KRKAN\",\"mutuallyDefinedCode\":\"\",\"schedKDCode\":\"58031\",\"schedKDType\":{\"value\":\"K\"}},\"portCode\":\"\",\"portName\":\"Kwangyang\",\"state\":\"Chollanam-do\"}},\"fullReturnCutOffDT\":{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":13,\"day\":3,\"hours\":3,\"minutes\":0,\"month\":4,\"seconds\":0,\"time\":1589338800000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589338800000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":13,\"day\":3,\"hours\":12,\"minutes\":0,\"month\":4,\"seconds\":0,\"time\":1589371200000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589371200000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"KST\"}},\"haulage\":{\"inBound\":{\"value\":\"M\"},\"outBound\":{\"value\":\"M\"}},\"lastPOD\":{\"facility\":{\"facilityCode\":\"GDN02\",\"facilityName\":\"Deepwater Container Terminal Gdansk\"},\"port\":{\"CSCountryCode\":\"PL\",\"CSPortID\":341,\"city\":\"Gdansk\",\"country\":\"Poland\",\"county\":\"\",\"locationCode\":{\"UNLocationCode\":\"PLGDN\",\"mutuallyDefinedCode\":\"\",\"schedKDCode\":\"45511\",\"schedKDType\":{\"value\":\"K\"}},\"portCode\":\"\",\"portName\":\"Gdansk\",\"state\":\"Pomorskie\"}},\"shipmentLeg\":[{\"POD\":{\"facility\":{\"facilityCode\":\"GDN02\",\"facilityName\":\"Deepwater Container Terminal Gdansk\"},\"port\":{\"CSCountryCode\":\"PL\",\"CSPortID\":341,\"city\":\"Gdansk\",\"country\":\"Poland\",\"county\":\"\",\"locationCode\":null,\"portCode\":\"\",\"portName\":\"Gdansk\",\"state\":\"Pomorskie\"}},\"POL\":{\"facility\":{\"facilityCode\":\"KAN05\",\"facilityName\":\"Korea International Terminal\"},\"port\":{\"CSCountryCode\":\"KR\",\"CSPortID\":360,\"city\":\"Gwangyang\",\"country\":\"South Korea\",\"county\":\"\",\"locationCode\":null,\"portCode\":\"\",\"portName\":\"Kwangyang\",\"state\":\"Chollanam-do\"}},\"SVVD\":{\"discharge\":{\"callNumber\":1,\"callSign\":\"VRRB8\",\"direction\":\"E\",\"lloydsNumber\":\"9776212\",\"service\":\"AEU1\",\"vessel\":\"NQ3\",\"vesselName\":\"OOCL SCANDINAVIA\",\"vesselNationality\":\"\",\"voyage\":\"012\"},\"loading\":{\"callNumber\":1,\"callSign\":\"VRRB8\",\"direction\":\"W\",\"lloydsNumber\":\"9776212\",\"service\":\"AEU1\",\"vessel\":\"NQ3\",\"vesselName\":\"OOCL SCANDINAVIA\",\"vesselNationality\":\"\",\"voyage\":\"012\"},\"vesselVoyageType\":{\"value\":\"OUTBOUND\"}},\"arrivalDT\":[{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":23,\"day\":2,\"hours\":17,\"minutes\":0,\"month\":5,\"seconds\":0,\"time\":1592931600000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1592931600000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"indicator\":{\"value\":\"E\"},\"locDT\":{\"CSTimeZone\":\"CEST\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":23,\"day\":2,\"hours\":19,\"minutes\":0,\"month\":5,\"seconds\":0,\"time\":1592938800000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1592938800000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}}],\"departureDT\":[{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":4,\"minutes\":0,\"month\":4,\"seconds\":0,\"time\":1589515200000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589515200000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"indicator\":{\"value\":\"E\"},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":13,\"minutes\":0,\"month\":4,\"seconds\":0,\"time\":1589547600000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589547600000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}},{\"GMT\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":9,\"month\":4,\"seconds\":35,\"time\":1589501375000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589501375000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"indicator\":{\"value\":\"A\"},\"locDT\":{\"CSTimeZone\":\"KRT\",\"_value\":{\"calendarType\":\"gregory\",\"firstDayOfWeek\":1,\"gregorianChange\":{\"date\":15,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":9,\"seconds\":0,\"time\":-12219292800000,\"timezoneOffset\":0,\"year\":-318},\"lenient\":true,\"minimalDaysInFirstWeek\":1,\"time\":{\"date\":15,\"day\":5,\"hours\":9,\"minutes\":9,\"month\":4,\"seconds\":35,\"time\":1589533775000,\"timezoneOffset\":0,\"year\":120},\"timeInMillis\":1589533775000,\"timeZone\":{\"DSTSavings\":0,\"ID\":\"GMT\",\"dirty\":false,\"displayName\":\"Greenwich Mean Time\",\"lastRuleInstance\":null,\"rawOffset\":0},\"weekDateSupported\":true,\"weekYear\":2020,\"weeksInWeekYear\":52},\"timeZone\":\"\"}}],\"legSeq\":1}]},\"status\":[]},\"queryCriteria\":{\"carrierSCACCode\":\"COSU\",\"containerNumber\":\"FCIU5812619\",\"timeStamp\":\"\"},\"queryError\":[]}";
		
		JsonParser springParser = JsonParserFactory.getJsonParser();
		Map<String, Object> map = springParser.parseMap(resp);

		String mapArray[] = new String[map.size()];
		System.out.println("Items found: " + mapArray.length);
//		for (int i=0; i < mapArray.length; i++) {
//			System.out.println("mapArray:" + mapArray[i]);
//		}

//		int i = 0;
		int step = 0;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
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
				
				step = jsonRecursionPrint(++step, entry);
//				jsonObjectPrint(entry);
				
//				Map<String, Object> map2 = springParser.parseMap(entry.getValue().toString());
//				for (Map.Entry<String, Object> entry2 : map2.entrySet()) {
//					System.out.println(entry2.getKey() + " = " + entry2.getValue());
//				}
				
		}
		logger.info("End Postgresql To Oracle:");

	}
	
	@Scheduled(fixedDelay = 1000 * 60 * 2, initialDelay = 1000)
	public void selectRoutesSchedules() throws InterruptedException {
		
		Calendar calendar = new GregorianCalendar();
		
		

		int hour = calendar.get(Calendar.HOUR); // 12 hour clock
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int millisecond = calendar.get(Calendar.MILLISECOND);

		logger.info("START Postgressql To Oracle " + hour + ":" + minute + ":" + second + "." + millisecond);		
		
		try {
			
			JsonParser springParser = JsonParserFactory.getJsonParser();
			
			String tmp = properties.getSchedules().replaceAll(" ", "");
			System.out.println(tmp);
			Map<String, Object> props = springParser.parseMap(properties.getSchedules());
			RestTemplate restTemplate = new RestTemplate();
			int step2 = 0;
			for (Map.Entry<String, Object> entry : props.entrySet()) {
	//			step2 = jsonRecursionPrint(++step2, entry);
				if ("schedule".equals((entry.getKey()).toString())) {
					List list = (List) entry.getValue();
					System.out.println(list);	
					for(int i=0; i<list.size(); i++) {
						Map map = (Map) list.get(0);
						if ("COSU".equals(map.get("carrier"))) {
							String carrier = (String) map.get("carrier");
							String appKey = this.prosAppKeyCOSU;
							List routes = (List) map.get("routes");
							for(int j=0; j<routes.size(); j++) {
								Map route = (Map) routes.get(j);
								String startport = (String) route.get("startport");
								List endports = (List) route.get("endport");
								System.out.println("startport:" + startport);
								for(int k=0; k<endports.size(); k++) {
									
									
									String endport = (String) endports.get(k);
									System.out.println("		endport:" + endport);
									
									String url=this.prosUrlSchedules+"/" + carrier + "?appKey=" +appKey + "&porID="+startport+"&fndID="+endport;
									
									try {
										String resp = restTemplate.getForObject(url, String.class);
		//								System.out.println("resp:" + resp);
										logger.info(resp);
										Map<String, Object> parse = springParser.parseMap(resp);
										
/*
										int step = 0;
										for (Map.Entry<String, Object> result : parse.entrySet()) {
											step = jsonRecursionPrint(++step, result);
										}
*/
										
										List _routeGroupsList = (List) parse.get("routeGroupsList");
//										System.out.println(_routeGroupsList);
										Map _routeGroupsList0 = (Map) _routeGroupsList.get(0);

										Map<String, Object> _carrier = (Map) _routeGroupsList0.get("carrier");
										String _scac = (String) _carrier.get("scac"); //NADCA_3039
										String _name = (String) _carrier.get("name"); //NADCA_312A
										String _url = (String) _carrier.get("url"); //NADCA_312B
										String _updatedBy = (String) _carrier.get("updatedBy"); //CTAAL_3413
System.out.println("_scac"+_scac);
System.out.println("_name"+_name);
System.out.println("_url"+_url);
System.out.println("_updatedBy"+_updatedBy);
										

										Map _por = (Map) _routeGroupsList0.get("por");
										Map<String, Object> _por_location = (Map) _por.get("location");
										String _por_unlocode = (String) _por_location.get("unlocode"); //LOC88_3225
										String _por_uc_name = (String) _por_location.get("uc_name"); //LOC88_3224
System.out.println("_por_unlocode"+_por_unlocode);
System.out.println("_por_uc_name"+_por_uc_name);										
										
										
										Map _fnd = (Map) _routeGroupsList0.get("fnd");
										Map<String, Object> _fnd_location = (Map) _fnd.get("location");
										String _fnd_unlocode = (String) _fnd_location.get("unlocode"); //LOC88_3225
										String _fnd_uc_name = (String) _fnd_location.get("uc_name"); //LOC88_3224
System.out.println("_fnd_unlocode"+_fnd_unlocode);
System.out.println("_fnd_uc_name"+_fnd_uc_name);										

										
										List _route = (List) _routeGroupsList0.get("route");
										Map _route0 = (Map) _route.get(0);
										String _route0_carrierScac = (String) _route0.get("carrierScac"); //TDT20_3127
System.out.println("_route0_carrierScac"+_route0_carrierScac);
										
										List _route0_leg = (List) _route0.get("leg");
										Map _route0_leg0 = (Map) _route0_leg.get(0);
										Map<String, Object> _route0_leg0_service = (Map) _route0_leg0.get("service");
										String _route0_leg0_service_code = (String) _route0_leg0_service.get("code"); //FTXAAI4440
System.out.println("_route0_leg0_service_code"+_route0_leg0_service_code);
										String _route0_leg0_externalVoyageNumber = (String) _route0_leg0.get("externalVoyageNumber"); //TDT20_8028
System.out.println("_route0_leg0_externalVoyageNumber"+_route0_leg0_externalVoyageNumber);
										Integer _route0_leg0_imoNumber = (Integer) _route0_leg0.get("imoNumber"); //TDT20_8213
System.out.println("_route0_leg0_imoNumber"+_route0_leg0_imoNumber);
										Map<String, Object> _route0_leg0_vessel = (Map) _route0_leg0.get("vessel");
										String _route0_leg0_vessel_name = (String) _route0_leg0_vessel.get("name"); //TDT20_8212
System.out.println("_route0_leg0_vessel_name"+_route0_leg0_vessel_name);
										Map<String, Object> _route0_leg0_fromPoint = (Map) _route0_leg0.get("fromPoint");
										Map<String, Object> _route0_leg0_fromPoint_location = (Map) _route0_leg0_fromPoint.get("location");										
										String _route0_leg0_fromPoint_location_unlocode = (String) _route0_leg0_fromPoint_location.get("unlocode"); //LOC9__3225
System.out.println("_route0_leg0_fromPoint_location_unlocode"+_route0_leg0_fromPoint_location_unlocode);										
										String _route0_leg0_fromPoint_location_name = (String) _route0_leg0_fromPoint_location.get("name"); //LOC9__3224
System.out.println("_route0_leg0_fromPoint_location_name"+_route0_leg0_fromPoint_location_name);										
										String _route0_leg0_fromPoint_etd = (String) _route0_leg0_fromPoint.get("etd"); //DTM133___9
System.out.println("_route0_leg0_fromPoint_etd"+_route0_leg0_fromPoint_etd); 
										Map<String, Object> _route0_leg0_toPoint = (Map) _route0_leg0.get("toPoint");
										Map<String, Object> _route0_leg0_toPoint_location = (Map) _route0_leg0_toPoint.get("location");										
										String _route0_leg0_toPoint_location_unlocode = (String) _route0_leg0_toPoint_location.get("unlocode"); //LOC11_3225
System.out.println("_route0_leg0_toPoint_location_unlocode"+_route0_leg0_toPoint_location_unlocode);										
										String _route0_leg0_toPoint_location_name = (String) _route0_leg0_toPoint_location.get("name"); //LOC11_3224
System.out.println("_route0_leg0_toPoint_location_name"+_route0_leg0_toPoint_location_name);										
										String _route0_leg0_toPoint_eta = (String) _route0_leg0_toPoint.get("eta"); //DTM132__11
System.out.println("_route0_leg0_toPoint_etd"+_route0_leg0_toPoint_eta); 

										Map<String, Object> _route0_fnd = (Map) _route0.get("fnd");
										Map<String, Object> _route0_fnd_location = (Map) _route0_fnd.get("location");										
										String _route0_fnd_location_unicode = (String) _route0_fnd_location.get("unlocode"); //LOC20_3225
System.out.println("_route0_fnd_location_unicode"+_route0_fnd_location_unicode);
										String _route0_fnd_location_name = (String) _route0_fnd_location.get("name"); //LOC20_3224
System.out.println("_route0_fnd_location_name"+_route0_fnd_location_name);

										Map<String, Object> _route0_defaultCutoff = (Map) _route0.get("defaultCutoff");
										String _route0_defaultCutoff_cutoffTime = (String) _route0_defaultCutoff.get("cutoffTime"); //DTM1802380
System.out.println("_route0_defaultCutoff_cutoffTime"+_route0_defaultCutoff_cutoffTime);
										
/*
_scacCOSU
_nameCOSCO SHIPPING
_urlhttp://lines.coscoshipping.com
_updatedByCSDev
_por_unlocodeKRPUS
_por_uc_nameBUSAN
_fnd_unlocodeDZALG
_fnd_uc_nameALGIERS
_route0_carrierScacCOSU
_route0_leg0_service_codeAEM3
_route0_leg0_externalVoyageNumber0BX7BW1MA
_route0_leg0_imoNumber9705067
_route0_leg0_vessel_nameCMA CGM TIGRIS
_route0_leg0_fromPoint_location_unlocodeKRPUS
_route0_leg0_fromPoint_location_nameBusan
_route0_leg0_fromPoint_etd2020-08-16T00:00:00.000Z
_route0_leg0_toPoint_location_unlocodeGRPIR
_route0_leg0_toPoint_location_namePiraeus
_route0_leg0_toPoint_etd2020-09-27T00:00:00.000Z
_route0_fnd_location_unicodeDZALG
_route0_fnd_location_nameAlgiers
_route0_defaultCutoff_cutoffTime2020-08-15T04:00:00.000Z
*/

									} catch(Exception e) {
										e.printStackTrace();
									}
									
								}
							}
						}
					}
				}

			}
			
		} catch (Exception e){
			e.printStackTrace();
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
		logger.info("End Postgresql To Oracle:");

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
}
