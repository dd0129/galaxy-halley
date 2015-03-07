package com.dianping.data.warehouse.core.common.SqlParser;

/**
 * 
 * @author leo.chen
 */
public interface DOLiteFactory {
	/**
	 * Produce HQLite
     * @param ID
     * @param fileName
	 * @param str
	 * @return
	 * @throws Exception
	 * 
	 */
	  public abstract DOLite produce(String ID, String fileName, String str)
			    throws Exception;
}
