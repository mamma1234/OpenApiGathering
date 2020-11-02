package com.klnet.application;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


public class Db2AbstractDAO {
//	protected Log log = LogFactory.getLog(AbstractDAO.class);
	Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    @Qualifier("db2SqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
    
	@Autowired 
	@Qualifier("db2transactionManager")
	PlatformTransactionManager transactionManager; 
	
    protected void printQueryId(String queryId) {
        if(logger.isDebugEnabled()){
        	logger.debug("\t QueryId  \t:  " + queryId);
        }
    }
     
    public Object insert(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.insert(queryId, params);
    }
     
    public Object update(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.update(queryId, params);
    }
     
    public Object delete(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.delete(queryId, params);
    }
     
    public Object selectOne(String queryId){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId);
    }
     
    public Object selectOne(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId, params);
    }
     
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId){
        printQueryId(queryId);
        return sqlSession.selectList(queryId);
    }
     
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectList(queryId,params);
    }

	
	/**
	 * @param queryId
	 * @param parameterObject
	 */
	protected void commitExecute(String queryId, Object parameterObject) {
		transaction(queryId, parameterObject);
	}

	/**
	 * @param queryId
	 * @param parameterObject
	 */
	private void transaction(final String queryId, final Object parameterObject) {
		TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);                
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		txTemplate.execute(new TransactionCallbackWithoutResult() {
		    public void doInTransactionWithoutResult(TransactionStatus status) {
		    	insert(queryId, parameterObject);
		    }
		});
	}
    


}
