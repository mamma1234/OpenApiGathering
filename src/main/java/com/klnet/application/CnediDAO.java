package com.klnet.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("cnediDAO")
public class CnediDAO extends Db2AbstractDAO {

	public int svcStatus() {
		return (Integer) selectOne("svcStatus");
	}

	public int svcStatus2(String hostname) {
		// return (Integer) selectOne("svcStatus2", hostname);
		return (Integer) selectOne("svcStatus2", "Develop-SVR");
		// return (Integer) selectOne("svcStatus2", "Dual-MFCS5");
	}

	public String svcStatusCheck() {
		return (String) selectOne("svcStatusCheck");
	}

	public String svcStatusCheck2() {
		return (String) selectOne("svcStatusCheck2");
	}

	// public void ebizFlowThread(){
	// selectOne("ebizFlowThread");
	// }

	public void ebizFlowThread(HashMap map) {
		selectOne("ebizFlowThread", map);
	}

	public void ebizFlowThread2(HashMap map) {
		selectOne("ebizFlowThread2", map);
	}

	public void ebizFlowThread3(HashMap map) {
		selectOne("ebizFlowThread3", map);
	}

	public List getTcsFlowThreadList() {
		return selectList("getTcsFlowThreadList");
	}

	public void setTcsFlowThreadClear(HashMap map) {
		update("setTcsFlowThreadClear", map);
	}

	public void mapinCnediThread() {
		selectOne("mapinCnediThread");
	}

	public void mapoutCnediThread() {
		selectOne("mapoutCnediThread");
	}

	public void chargeFlowThread() {
		selectOne("chargeFlowThread");
	}

	public void chargeFlowThreadImport() {
		selectOne("chargeFlowThreadImport");
	}

	public void chargeFlowThreadExport() {
		selectOne("chargeFlowThreadExport");
	}

	public void ownerFromMfcsThread() {
		selectOne("ownerFromMfcsThread");
	}

	public void ownerFromCjCntrThread() {
		selectOne("ownerFromCjCntrThread");
	}

	public String getCnEdiXmlMsgId() {
		return (String) selectOne("getCnEdiXmlMsgId");
	}

	public void setScheduleMapinTbl(Map map) {
		insert("setScheduleMapinTbl", map);
	}

	public void setiftsai___hd(Map map) {
		insert("setiftsai___hd", map);
	}

	public void setiftsai__tdt(Map map) {
		insert("setiftsai__tdt", map);
	}

	public List getTrackingSrCntrList(Map map) {
		return selectList("getTrackingSrCntrList", map);
	}
	
	public void setTrackingSrCntrComplete(Map map) {
		update("setTrackingSrCntrComplete", map);
	}
	
}