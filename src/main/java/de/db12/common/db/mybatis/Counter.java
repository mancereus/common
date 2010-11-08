package de.db12.common.db.mybatis;

/**
 * 
 * 
 * @version $Id$
 */
public class Counter {

	private int count;

	public void increment() {
		count++;
	}

	public void reset() {
		count = 0;
	}

	public int getCount() {
		return count;
	}

}
