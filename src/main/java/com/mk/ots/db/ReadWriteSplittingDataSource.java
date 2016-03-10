package com.mk.ots.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 读写分离根据当前事务的只读属性进行区分.
 *
 * @author zhaoshb.
 *
 */
public class ReadWriteSplittingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
		if (readOnly) {
			return "slave";
		}
		return "master";
	}
}