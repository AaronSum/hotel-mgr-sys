package com.mk.ots.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * MongoClient 外观.
 *
 * @author zhaoshb
 *
 */
public class MongoClientFacade implements InitializingBean {

	private static final String TN_CHAT_MSG = "chat_message";

	private static final String TN_FAILED_RECORD = "failed_record";

	private static final String TN_FILE_FAILED_RECORD = "file_failed_record";

	private List<String> hostList = null;

	private String userName = null;

	private String password = null;

	private MongoClient mongoClient = null;

	private String database = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.initMongoClient();
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DB getDB() {
		return this.getMongoClient().getDB(this.getDatabase());
	}

	public DBCollection getChatMsgCollection() {
		DB db = this.getDB();

		return db.getCollection(MongoClientFacade.TN_CHAT_MSG);
	}

	public DBCollection getFailedRecordCollection() {
		DB db = this.getDB();

		return db.getCollection(MongoClientFacade.TN_FAILED_RECORD);
	}

	public DBCollection getFileFailedRecordCollection() {
		DB db = this.getDB();

		return db.getCollection(MongoClientFacade.TN_FILE_FAILED_RECORD);
	}

	private void initMongoClient() throws UnknownHostException {
		this.mongoClient = new MongoClient(this.createServerAddressList());
	}

	private List<ServerAddress> createServerAddressList() throws UnknownHostException {
		List<ServerAddress> saList = new ArrayList<ServerAddress>();
		for (String host : this.getHostList()) {
			saList.add(this.creatServerAddress(host));
		}
		return saList;
	}

	@SuppressWarnings("unused")
	private List<MongoCredential> createCredentialList() {
		List<MongoCredential> mcList = new ArrayList<MongoCredential>();
		for (int i = 0; i < this.getHostList().size(); i++) {
			mcList.add(this.createMongoCredential());
		}
		return mcList;
	}

	private MongoCredential createMongoCredential() {
		return MongoCredential.createCredential(this.getUserName(), this.getDatabase(), this.getPassword().toCharArray());
	}

	private ServerAddress creatServerAddress(String host) throws UnknownHostException {
		return new ServerAddress(host);
	}

	public void setHostList(List<String> hostList) {
		this.hostList = hostList;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	private List<String> getHostList() {
		return this.hostList;
	}

	private String getUserName() {
		return this.userName;
	}

	private String getPassword() {
		return this.password;
	}

	private String getDatabase() {
		return this.database;
	}

	private MongoClient getMongoClient() {
		return this.mongoClient;
	}

}
