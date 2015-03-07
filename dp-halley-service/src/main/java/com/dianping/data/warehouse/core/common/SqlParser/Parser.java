package com.dianping.data.warehouse.core.common.SqlParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import org.apache.hadoop.hive.ql.parse.ParseException;


/**
 * Created by Sunny on 14-6-24.
 */
public class Parser {

    private CanaanConf conf;
    private DOLite dolite;


    private void init() {
        conf = CanaanConf.getConf();
    }

    public Map<Integer, AnalyzeResult> parse(String fileName) throws Exception {
        init();
        parseDOL(fileName);
        Map<Integer, AnalyzeResult> result = parseSQL();
        return result;
    }

    private void parseDOL(String fileName) throws Exception {
        DOLParser dolParser = new DOLParser(conf, fileName);
        dolite = dolParser.getDOLite();
    }

    private Map<Integer, AnalyzeResult> parseSQL() throws ParseException, ClassNotFoundException, IOException, SQLException {
        SQLParser sqlParser = new SQLParser(dolite);
        return sqlParser.parse();
    }

    public static void main(String args[]) throws Exception {
        Parser parser = new Parser();
        parser.parse("/Users/Sunny/Desktop/test1.dol");
    }

}
