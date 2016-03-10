package com.mk.hms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author hdy
 *
 */
public abstract class ThreadTask implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(ThreadTask.class);

    @Override
    public final void run() {
        try {
            execute();
        } catch (Throwable e) {
        	ThreadTask.log.error(e.getMessage(), e);
        }
    }

    public abstract void execute();

}