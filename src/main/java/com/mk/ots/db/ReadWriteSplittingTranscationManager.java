package com.mk.ots.db;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 读写分离事务管理,提前设定是否为只读事务，供数据源路由使用.
 *
 * @see ReadWriteSplittingDataSource
 * @author zhaoshb.
 *
 */
public class ReadWriteSplittingTranscationManager extends DataSourceTransactionManager {

	private static final long serialVersionUID = -8215411857826198091L;

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		TransactionSynchronizationManager.setCurrentTransactionReadOnly(definition.isReadOnly());
		super.doBegin(transaction, definition);
	}
}