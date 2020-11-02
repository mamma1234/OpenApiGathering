package com.klnet.application;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CnediFlowThread {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	  @Autowired
	  CnediDAO ebizDAO;
 
	  
//	  @Scheduled(fixedDelay = 1000, initialDelay = 10000)
	  public void ebizFlowThread_all() throws InterruptedException{
		  
		  
		  Calendar calendar = new GregorianCalendar();
		  
		  int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
		  int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		  int minute     = calendar.get(Calendar.MINUTE);
		  int second     = calendar.get(Calendar.SECOND);
		  int millisecond= calendar.get(Calendar.MILLISECOND);
		  
		  
		  try {
			  
//			  logger.info( " cheack" + ebizDAO.svcStatusCheck() );
			  
			  
			  if ( ebizDAO.svcStatus() < 1 ) {
//			  if ( ebizDAO.svcStatus2(System.getenv("HOSTNAME")) < 1 ) {
//			  if ( false ) {
				  logger.info("EBIZ FLOW SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				  Thread.sleep(60000);
				  
			  } else {
				  
				  logger.info("START EBIZ FLOW " + hour +":"  + minute+":" + second+"." + millisecond);
				  HashMap map = new HashMap();
				  map.put("carrier_code", "");
				  map.put("doc_name", "ALL");
				  ebizDAO.ebizFlowThread(map);
				  logger.info("END EBIZ FLOW");
			  }
			  
			  
			  
		  } catch (Exception e) {
			  logger.error("error", e);
//			  throw e;
		  }		  
		  
	  }	  
	  

	  
	  
//	  @Scheduled(fixedDelay = 1000, initialDelay = 10000)
	  public void mapinCnediThread() throws InterruptedException{
		  
		  
		  Calendar calendar = new GregorianCalendar();
		  
		  int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
		  int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		  int minute     = calendar.get(Calendar.MINUTE);
		  int second     = calendar.get(Calendar.SECOND);
		  int millisecond= calendar.get(Calendar.MILLISECOND);

		  
		  try {
			  
//			  logger.info( " cheack" + ebizDAO.svcStatusCheck() );
			  

			  if ( ebizDAO.svcStatus() < 1 ) {
//			  if ( ebizDAO.svcStatus2(System.getenv("HOSTNAME")) < 1 ) {
//			  if ( false ) {
				  logger.info("CNEDI MAP IN SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				  Thread.sleep(60000);

			  } else {
				  
				  logger.info("START MAP IN CNEDI " + hour +":"  + minute+":" + second+"." + millisecond);
				  ebizDAO.mapinCnediThread();
				  logger.info("CNEDI MAP IN END");
			  }
			  
		  
		  
		  } catch (Exception e) {
			  logger.error("error", e);
//			  throw e;
		  }		  
		  
	  }
	  
	  
//	  @Scheduled(fixedDelay = 1000, initialDelay = 10000)
	  public void mapoutCnediThread() throws InterruptedException{
		  
		  
		  Calendar calendar = new GregorianCalendar();
		  
		  int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
		  int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		  int minute     = calendar.get(Calendar.MINUTE);
		  int second     = calendar.get(Calendar.SECOND);
		  int millisecond= calendar.get(Calendar.MILLISECOND);
		  
		  
		  try {
			  
//			  logger.info( " cheack" + ebizDAO.svcStatusCheck() );
			  
			  
			  if ( ebizDAO.svcStatus() < 1 ) {
//			  if ( ebizDAO.svcStatus2(System.getenv("HOSTNAME")) < 1 ) {
//			  if ( false ) {
				  logger.info("CNEDI MAP OUT SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				  Thread.sleep(60000);
				  
			  } else {
				  
				  logger.info("START MAP OUT CNEDI " + hour +":"  + minute+":" + second+"." + millisecond);
				  ebizDAO.mapoutCnediThread();
				  logger.info("CNEDI MAP OUT END");
			  }
			  
			  
			  
		  } catch (Exception e) {
			  logger.error("error", e);
//			  throw e;
		  }		  
		  
	  }

	  
	  
	  //Seconds Minutes Hours DayOfMonth Month DayOfWeek year
//	  @Scheduled(fixedDelay = 1000, initialDelay = 10000)
//	  @Scheduled(cron = "0 0 4 * * ?")
	  public void ownerFromMfcsThread() throws InterruptedException{
		  
		  
		  Calendar calendar = new GregorianCalendar();
		  
		  int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
		  int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		  int minute     = calendar.get(Calendar.MINUTE);
		  int second     = calendar.get(Calendar.SECOND);
		  int millisecond= calendar.get(Calendar.MILLISECOND);
		  
		  
		  try {
			  
//			  logger.info( " cheack" + ebizDAO.svcStatusCheck() );
			  
			  
			  if ( ebizDAO.svcStatus() < 1 ) {
//			  if ( ebizDAO.svcStatus2(System.getenv("HOSTNAME")) > 0 ) {
//			  if ( false ) {
				  logger.info("OWNER FROM MFCS SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				  Thread.sleep(60000);
			  } else {				  
				  logger.info("START OWNER FROM MFCS FLOW THREAD " + hour +":"  + minute+":" + second+"." + millisecond);
				  ebizDAO.ownerFromMfcsThread();
				  logger.info("END OWNER FROM MFCS FLOW THREAD");
			  }
			  
			  
			  
		  } catch (Exception e) {
			  logger.error("error", e);
//			  throw e;
		  }		  
		  
	  }

//	  @Scheduled(cron = "0 0 2,8,13,17 * * ?")
	  public void ownerFromCjCntrThread() throws InterruptedException{
		  
		  
		  Calendar calendar = new GregorianCalendar();
		  
		  int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
		  int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		  int minute     = calendar.get(Calendar.MINUTE);
		  int second     = calendar.get(Calendar.SECOND);
		  int millisecond= calendar.get(Calendar.MILLISECOND);
		  
		  
		  try {
			  
//			  logger.info( " cheack" + ebizDAO.svcStatusCheck() );
			  
			  
			  if ( ebizDAO.svcStatus() < 1 ) {
//			  if ( ebizDAO.svcStatus2(System.getenv("HOSTNAME")) > 0 ) {
//			  if ( false ) {
				  logger.info("OWNER FROM CJ SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				  Thread.sleep(60000);
			  } else {				  
				  logger.info("START OWNER FROM CJ CNTR FLOW THREAD " + hour +":"  + minute+":" + second+"." + millisecond);
				  ebizDAO.ownerFromCjCntrThread();
				  logger.info("END OWNER FROM CJ CNTR FLOW THREAD");
			  }
			  
			  
			  
		  } catch (Exception e) {
			  logger.error("error", e);
//			  throw e;
		  }		  
		  
	  }

	  
//	  @Scheduled(fixedDelay = 1000 * 60 * 5, initialDelay = 10000)
//	  @Scheduled(fixedDelay = 1000, initialDelay = 10000)
	  public void chargeFlowThreadImport() throws InterruptedException{
		  
		  
		  Calendar calendar = new GregorianCalendar();
		  
		  int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
		  int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		  int minute     = calendar.get(Calendar.MINUTE);
		  int second     = calendar.get(Calendar.SECOND);
		  int millisecond= calendar.get(Calendar.MILLISECOND);
		  
		  
		  try {
			  
//			  logger.info( " cheack" + ebizDAO.svcStatusCheck() );
			  
			  logger.info( "HOSTNAME:" + System.getenv("HOSTNAME") );
			  
			  
			  
			  
			  if ( ebizDAO.svcStatus() < 1 ) {
//			  if ( ebizDAO.svcStatus2(System.getenv("HOSTNAME")) > 0 ) {
//			  if ( false ) {
				  logger.info("CHARGE FLOW IMPORT SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				  Thread.sleep(60000);
			  } else {				  
				  logger.info("START CHARGE FLOW IMPORT THREAD " + hour +":"  + minute+":" + second+"." + millisecond);
				  ebizDAO.chargeFlowThreadImport();
				  logger.info("END CHARGE FLOW IMPORT THREAD");
			  }
			  
			  
			  
		  } catch (Exception e) {
			  logger.error("error", e);
//			  throw e;
		  }		  
		  
	  }
	  
//	  @Scheduled(fixedDelay = 1000, initialDelay = 10000)
	  public void chargeFlowThreadExport() throws InterruptedException{
		  
		  
		  Calendar calendar = new GregorianCalendar();
		  
		  int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
		  int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		  int minute     = calendar.get(Calendar.MINUTE);
		  int second     = calendar.get(Calendar.SECOND);
		  int millisecond= calendar.get(Calendar.MILLISECOND);
		  
		  
		  try {
			  
//			  logger.info( " cheack" + ebizDAO.svcStatusCheck() );
			  
			  logger.info( "HOSTNAME:" + System.getenv("HOSTNAME") );
			  
			  
			  
			  
			  if ( ebizDAO.svcStatus() < 1 ) {
//			  if ( ebizDAO.svcStatus2(System.getenv("HOSTNAME")) > 0 ) {
//			  if ( false ) {
				  logger.info("CHARGE FLOW EXPORT SLEEP " + hour +":"  + minute+":" + second+"." + millisecond);
				  Thread.sleep(60000);
			  } else {				  
				  logger.info("START CHARGE FLOW EXPORT THREAD " + hour +":"  + minute+":" + second+"." + millisecond);
				  ebizDAO.chargeFlowThreadExport();
				  logger.info("END CHARGE FLOW EXPORT THREAD");
			  }
			  
			  
			  
		  } catch (Exception e) {
			  logger.error("error", e);
//			  throw e;
		  }		  
		  
	  }
	  
	  
}
