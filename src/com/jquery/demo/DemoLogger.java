package com.jquery.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for logging messages
 * 
 * @author
 * 
 */

public class DemoLogger {
	private Logger logger = null;

	private DemoLogger(Class<?> cClassName) {
		logger = LoggerFactory.getLogger(cClassName);
	}

	public static DemoLogger getLogger(Class<?> cClassName) {
		return new DemoLogger(cClassName);
	}

	/**
	 * @param message
	 * @param t
	 */
	public void debug(Object message, Throwable t) {
		logger.debug(message.toString(), t);
	}

	/**
	 * @param message
	 */
	public void debug(Object message) {
		logger.debug(message.toString());
	}

	/**
	 * @param message
	 * @param t
	 */
	public void error(Object message, Throwable t) {
		logger.error(message.toString(), t);
	}

	/**
	 * @param message
	 */
	public void error(Object message) {
		logger.error(message.toString());
	}

	/**
	 * @param message
	 * @param t
	 */
	public void info(Object message, Throwable t) {
		logger.info(message.toString(), t);
	}

	/**
	 * @param message
	 */
	public void info(Object... messages) {
		for (Object message : messages) {
			logger.info(message.toString());
		}
	}

	/**
	 * @param message
	 * @param t
	 */
	public void warn(Object message, Throwable t) {
		logger.warn(message.toString(), t);
	}

	/**
	 * @param message
	 */
	public void warn(Object message) {
		logger.warn(message.toString());
	}
}
