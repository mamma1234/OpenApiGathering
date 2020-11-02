package com.klnet.application;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("ownerDAO")
public class OwnerDAO extends Db1AbstractDAO{


	
	public List selectIsoPort(){
		return selectList("selectIsoPort");
	}
	
	
//	public int selectPostgresqlToOracle(){
//		return (Integer) selectOne("selectPostgresqlToOracle");
//	}
//	
//	public int selectOracleToPostgresql(){
//		return (Integer) selectOne("selectOracleToPostgresql");
//	}
//	
//	public int selectPostgresqlThread(){
//		return (Integer) selectOne("selectPostgresqlThread");
//	}
	
	
	public String selectPostgresqlToOracle(){
		return (String) selectOne("selectPostgresqlToOracle");
	}
	
//	public String selectPostgresqlFromOracle(){
//		return (String) selectOne("selectPostgresqlFromOracle");
//	}
	
//	public String selectPostgresqlFromOracle2(){
//		return (String) selectOne("selectPostgresqlFromOracle2");
//	}
	
	public String selectPostgresqlThread(){
		return (String) selectOne("selectPostgresqlThread");
	}
	
	public void selectPostgresqlFromOracle(){
		callProcedure("call sp_postgresql_from_oracle()");
	}
	
	public void selectPostgresqlFromOracle2(){
		callProcedure("call sp_postgresql_from_oracle2()");
	}
	
	public void selectPostgresqlFromOracle3(){
		callProcedure("call sp_postgresql_from_oracle3()");
	}
	
	public List selectOwnVslSchMSCList(){
		return selectList("selectOwnVslSchMSCList");
	}
	
//	public void selectPostgresqlFromOracle(){
//		selectOne("selectPostgresqlFromOracle");
//	}
//		
//	
//	public void selectPostgresqlFromOracle2(){
//		selectOne("selectPostgresqlFromOracle2");
//	}
	
}