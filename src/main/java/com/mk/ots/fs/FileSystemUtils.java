package com.mk.ots.fs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件系統工具(线程安全).<br>
 * 由spring进行生命周期管理.<br>
 *
 * @author zhaoshb
 *
 */
public class FileSystemUtils {

	private static final String FDFS_CLIENT = "fdfs_client.conf";

	private static final String UPLOAD_LOG_FORMAT = "upload file [%s.%s] success.";

	private static final String INIT_FILESYSTEM_FAILED = "initialize file system failed.";

	private static Logger logger = LoggerFactory.getLogger(FileSystemUtils.class);

	private TrackerClient tracker = null;

	public FileSystemUtils() {
		try {
			ClientGlobal.init(FileSystemUtils.FDFS_CLIENT);
			this.setTracker(new TrackerClient());
		} catch (FileNotFoundException e) {
			this.getLogger().error(FileSystemUtils.INIT_FILESYSTEM_FAILED, e);
		} catch (IOException e) {
			this.getLogger().error(FileSystemUtils.INIT_FILESYSTEM_FAILED, e);
		} catch (FSException e) {
			this.getLogger().error(FileSystemUtils.INIT_FILESYSTEM_FAILED, e);
		} catch (URISyntaxException e) {
			this.getLogger().error(FileSystemUtils.INIT_FILESYSTEM_FAILED, e);
		}
	}

	public String[] upload(String localName, String extName, InputStream is, long fileSize, NameValuePair[] metaList) throws IOException, FSException {
		TrackerServer trackerServer = this.getTracker().getConnection();
		StorageServer storageServer = null;
		StorageClient client = new StorageClient(trackerServer, storageServer);
		String[] results = client.upload_file(localName, extName, is, fileSize, metaList);
		this.getLogger().info(FileSystemUtils.UPLOAD_LOG_FORMAT + new String[] { localName, extName });

		return results;
	}

	public void download(String groupName, String remoteFileName, DownloadCallback callback) throws IOException, FSException {
		TrackerServer trackerServer = this.getTracker().getConnection();
		StorageServer storageServer = null;
		StorageClient client = new StorageClient(trackerServer, storageServer);
		client.download_file(groupName, remoteFileName, callback);
	}

	private TrackerClient getTracker() {
		return this.tracker;
	}

	private void setTracker(TrackerClient tracker) {
		this.tracker = tracker;
	}

	private Logger getLogger() {
		return FileSystemUtils.logger;
	}

}
