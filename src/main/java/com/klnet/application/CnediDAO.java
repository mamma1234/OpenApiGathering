package com.klnet.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("cnediDAO")
public class CnediDAO extends Db2AbstractDAO{


	
	public List selectRequestCntr(){
		return selectList("selectRequestCntr");
	}
	
	
	public int svcStatus(){
		return (Integer) selectOne("svcStatus");
	}
	public int svcStatus2(String hostname){
//		return (Integer) selectOne("svcStatus2", hostname);
		return (Integer) selectOne("svcStatus2", "Develop-SVR");
//		return (Integer) selectOne("svcStatus2", "Dual-MFCS5");
	}
	public String svcStatusCheck(){
		return (String) selectOne("svcStatusCheck");
	}
	
	
	public String svcStatusCheck2(){
		return (String) selectOne("svcStatusCheck2");
	}
	
	


//	public void ebizFlowThread(){
//		selectOne("ebizFlowThread");
//	}
	
	public void ebizFlowThread(HashMap map){
		selectOne("ebizFlowThread", map);
	}

	public List getTcsFlowThreadList(){
		return selectList("getTcsFlowThreadList");
	}

	public void setTcsFlowThreadClear(HashMap map){
		update("setTcsFlowThreadClear", map);
	}

	
	
	public void mapinCnediThread(){
		selectOne("mapinCnediThread");
	}
	
	
	public void mapoutCnediThread(){
		selectOne("mapoutCnediThread");
	}
	

	public void chargeFlowThread(){
		selectOne("chargeFlowThread");
	}
	
	public void ownerFromMfcsFlowThread(){
		selectOne("ownerFromMfcsFlowThread");
	}
        	

}