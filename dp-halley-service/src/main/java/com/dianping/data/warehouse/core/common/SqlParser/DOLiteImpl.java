package com.dianping.data.warehouse.core.common.SqlParser;

import java.util.Iterator;
import java.util.List;

public class DOLiteImpl implements DOLite {
	private String taskId;
	private String fileName;
	private List<String> statements;
	public DOLiteImpl(String taskId, String fileName, List<String> statements){
		this.taskId=taskId;
		this.fileName=fileName;
		this.statements=statements;
		
	}

	public Iterator<String> iterator() {
		return this.statements.iterator();
	}

	public String get(int index) {
		return this.statements.get(index-1);
	}

	public String getFileName() {
		return this.fileName;
	}
	public String getTaskId(){
		return this.taskId;
	}
	public int indexOf(String statement) {
		return this.statements.indexOf(statement)+1;
	}

	public int size() {
		return this.statements.size();
	}

}
