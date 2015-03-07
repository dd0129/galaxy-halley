package com.dianping.data.warehouse.core.common.SqlParser;

//import com.dianping.data.warehouse.canaan.dolite.DOLite;
//import com.dianping.data.warehouse.canaan.dolite.DOLiteFactory;
//import com.dianping.data.warehouse.canaan.dolite.VelocityDOLiteFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class DOLParser extends AbstractModule {
    private DOLite dolite;
    private String DOLHome;
    private Properties props;
    private String fileName;
    private String str;
    private String taskId;
    private static final String fileEncoding = "utf-8";

    @Override
    protected void configure() {
        bind(DOLiteFactory.class).to(VelocityDOLiteFactory.class);
        //bind(File.class).annotatedWith(Names.named("DOLHome")).toInstance(new File(this.DOLHome));
        bind(String.class).annotatedWith(Names.named("fileEncoding")).toInstance(fileEncoding);
        bind(Properties.class).annotatedWith(Names.named("props")).toInstance(this.props);
    }

    public DOLParser(CanaanConf canaanConf, String fileName) throws IOException {
        this.str = FileUtils.readFileToString(new File(fileName), fileEncoding);
        this.props = canaanConf.getCanaanProperties();
    }

    public DOLite getDOLite() throws Exception {
        Injector injector = Guice.createInjector(new Module[]{this});
        DOLiteFactory factory = (DOLiteFactory) injector.getInstance(DOLiteFactory.class);
        dolite = factory.produce(taskId, fileName, str);
        return dolite;
    }
}
