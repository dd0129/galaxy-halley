package com.dianping.data.warehouse.core.common.SqlParser;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class VelocityDOLiteFactory implements DOLiteFactory {
	private String fileEncoding = "utf-8";
	private File DOLHome = new File("/");
	private Properties props = new Properties();
	private StrBuilder template;
	private String[] statementStrings;

	public Properties getProps() {
		return props;
	}

	@Inject
	public void setProps(@Named("props") Properties props) {
		this.props = props;
	}

	public DOLite produce(String taskId,String fileName, String str)
			throws ParseErrorException, MethodInvocationException,
			ResourceNotFoundException, IOException {
		List<String> statements = this.loadTemplateFrom(str)
				.evaluateWithContext().createStatements();
		return new DOLiteImpl(taskId, fileName, statements);
	}

	protected List<String> createStatements() {
		return Arrays.asList(statementStrings);
	}

	public String getFileEncoding() {
		return fileEncoding;
	}

	@Inject
	public void setFileEncoding(@Named("fileEncoding") String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public File getDOLHome() {
		return DOLHome;
	}

//	@Inject
//	public void setDOLHome(@Named("DOLHome") File DOLHome) {
//		this.DOLHome = DOLHome;
//	}

	protected Context getContextFromProperties() {
		Map<String, String> env = new HashMap<String, String>();
		for (Object key : this.props.keySet()) {
			env.put(key.toString(), props.get(key).toString());
		}
		Context context = new VelocityContext();
		context.put("env", env);
		DateTimeContext dtCtx = new DateTimeContext();
		context.put("dt", dtCtx);
		return context;

	}

	protected VelocityDOLiteFactory evaluateWithContext()
			throws ParseErrorException, MethodInvocationException,
			ResourceNotFoundException, IOException {
		StringWriter writer = new StringWriter();
		Velocity.addProperty("runtime.log", "");
		Velocity.addProperty("file.resource.loader.path",
				this.DOLHome.toString());

		Velocity.evaluate(getContextFromProperties(), writer, getClass()
				.getSimpleName(), this.template.asReader());
		//对sql中分号转义，参见hive -f 中处理方式
		ArrayList<String> statementArray = new ArrayList<String>();
		String command = "";
		String line = writer.toString();
		for (String oneCmd : line.split(";")) {
			if (StringUtils.endsWith(oneCmd, "\\")) {
				command += oneCmd + ";";
				continue;
			} else {
				command += oneCmd;
			}
			if (StringUtils.isBlank(command)) {
				continue;
			}
			statementArray.add(command);
			command = "";
		}

		int i = 0;
		this.statementStrings = new String[statementArray.size()];
		for (String statement : statementArray) {
			this.statementStrings[i] = statement.trim();
			i++;
		}

		return this;

	}

	protected VelocityDOLiteFactory loadTemplateFrom(String str)
			throws IOException {
		this.template = new StrBuilder(str);
		final byte[] bom = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		if (this.template.toString().startsWith(new String(bom, "UTF8"))) {
			this.template.delete(0, 1);
		}
		String commonVmPath =  "vm" + File.separator + "common.vm";
		if (new File(this.DOLHome+File.separator+commonVmPath).exists())
        {
            String strParseVm = "#parse(\"" + commonVmPath + "\")";
            this.template.insert(0, strParseVm);
        }
		return this;
	}
}
