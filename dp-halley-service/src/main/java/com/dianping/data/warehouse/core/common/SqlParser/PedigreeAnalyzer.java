/**
 *
 */
package com.dianping.data.warehouse.core.common.SqlParser;

import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.HiveParser;
import org.apache.hadoop.hive.ql.parse.ParseDriver;
import org.apache.hadoop.hive.ql.parse.ParseException;
import org.apache.hadoop.hive.ql.parse.ParseUtils;
import org.apache.hadoop.hive.ql.parse.BaseSemanticAnalyzer;

/**
 * @author leo.chen
 */
public class PedigreeAnalyzer {
    private static PedigreeAnalyzer analyzer = null;

    private PedigreeAnalyzer() {

    }

    public static PedigreeAnalyzer getAnalyzer() {
        if (analyzer == null) {
            analyzer = new PedigreeAnalyzer();
        }
        return analyzer;
    }

    public ASTNode compile(String hiveSql) throws ParseException {
        ParseDriver pd = new ParseDriver();
        ASTNode tree = pd.parse(hiveSql);
        tree = ParseUtils.findRootNonNullToken(tree);
        return tree;
    }

    public AnalyzeResult analyze(String hiveSql) throws ParseException {
        ASTNode tree = compile(hiveSql);
        switch (tree.getToken().getType()) {
            case HiveParser.TOK_EXPLAIN:
            case HiveParser.TOK_LOAD:
            case HiveParser.TOK_EXPORT:
            case HiveParser.TOK_IMPORT:
            case HiveParser.TOK_CREATEDATABASE:
            case HiveParser.TOK_DROPDATABASE:
            case HiveParser.TOK_SWITCHDATABASE:
            case HiveParser.TOK_DROPTABLE:
            case HiveParser.TOK_DROPVIEW:
            case HiveParser.TOK_DESCDATABASE:
            case HiveParser.TOK_DESCTABLE:
            case HiveParser.TOK_DESCFUNCTION:
            case HiveParser.TOK_MSCK:
            case HiveParser.TOK_ALTERTABLE_ADDCOLS:
            case HiveParser.TOK_ALTERTABLE_RENAMECOL:
            case HiveParser.TOK_ALTERTABLE_REPLACECOLS:
            case HiveParser.TOK_ALTERTABLE_RENAME:
            case HiveParser.TOK_ALTERTABLE_DROPPARTS:
            case HiveParser.TOK_ALTERTABLE_ADDPARTS:
            case HiveParser.TOK_ALTERTABLE_PROPERTIES:
            case HiveParser.TOK_ALTERTABLE_SERIALIZER:
            case HiveParser.TOK_ALTERTABLE_SERDEPROPERTIES:
            case HiveParser.TOK_ALTERINDEX_REBUILD:
            case HiveParser.TOK_ALTERINDEX_PROPERTIES:
            case HiveParser.TOK_ALTERVIEW_PROPERTIES:
            case HiveParser.TOK_ALTERVIEW_ADDPARTS:
            case HiveParser.TOK_ALTERVIEW_DROPPARTS:
            case HiveParser.TOK_ALTERVIEW_RENAME:
            case HiveParser.TOK_SHOWDATABASES:
            case HiveParser.TOK_SHOWTABLES:
            case HiveParser.TOK_SHOW_TABLESTATUS:
            case HiveParser.TOK_SHOWFUNCTIONS:
            case HiveParser.TOK_SHOWPARTITIONS:
            case HiveParser.TOK_SHOWINDEXES:
            case HiveParser.TOK_SHOWLOCKS:
            case HiveParser.TOK_CREATEINDEX:
            case HiveParser.TOK_DROPINDEX:
            case HiveParser.TOK_ALTERTABLE_CLUSTER_SORT:
            case HiveParser.TOK_ALTERTABLE_TOUCH:
            case HiveParser.TOK_ALTERTABLE_ARCHIVE:
            case HiveParser.TOK_ALTERTABLE_UNARCHIVE:
            case HiveParser.TOK_LOCKTABLE:
            case HiveParser.TOK_UNLOCKTABLE:
            case HiveParser.TOK_CREATEROLE:
            case HiveParser.TOK_DROPROLE:
            case HiveParser.TOK_GRANT:
            case HiveParser.TOK_REVOKE:
            case HiveParser.TOK_SHOW_GRANT:
            case HiveParser.TOK_GRANT_ROLE:
            case HiveParser.TOK_REVOKE_ROLE:
            case HiveParser.TOK_SHOW_ROLE_GRANT:
            case HiveParser.TOK_ALTERDATABASE_PROPERTIES:
            case HiveParser.TOK_ALTERTABLE_PARTITION:
            case HiveParser.TOK_CREATEFUNCTION:
            case HiveParser.TOK_DROPFUNCTION:
                break;
            default:
                return analyzeInternal(tree, hiveSql);
        }
        return new AnalyzeResult(hiveSql);
    }

    private AnalyzeResult analyzeInternal(ASTNode ast, String hiveSql) {
        ASTNode child = ast;
        AnalyzeResult pr = new AnalyzeResult(hiveSql);
        if (ast.getToken().getType() == HiveParser.TOK_CREATETABLE) {
            if ((child = analyzeCreateTable(ast, pr)) == null) {
                return pr;
            }
        }
        if (ast.getToken().getType() == HiveParser.TOK_CREATEVIEW) {
            if ((child = analyzeCreateView(ast, pr)) == null) {
                return pr;
            }
        }
        doPhase1(child, pr);
        return pr;

    }

    private ASTNode analyzeCreateTable(ASTNode ast, AnalyzeResult pr) {
        String tableName = BaseSemanticAnalyzer.getUnescapedName((ASTNode) ast
                .getChild(0));
        int numCh = ast.getChildCount();
        for (int num = 1; num < numCh; num++) {
            ASTNode child = (ASTNode) ast.getChild(num);
            if (child.getToken().getType() == HiveParser.TOK_QUERY) {
                pr.addChildTab(tableName);
                return child;
            }
        }
        return null;
    }

    private ASTNode analyzeCreateView(ASTNode ast, AnalyzeResult pr) {
        String tableName = BaseSemanticAnalyzer.getUnescapedName((ASTNode) ast
                .getChild(0));
        int numCh = ast.getChildCount();
        for (int num = 1; num < numCh; num++) {
            ASTNode child = (ASTNode) ast.getChild(num);
            if (child.getToken().getType() == HiveParser.TOK_QUERY) {
                pr.addChildTab(tableName);
                return child;
            }
        }
        return null;
    }

    private void doPhase1(ASTNode ast, AnalyzeResult pr) {
        String tabName;
        boolean skipRecursion = false;

        if (ast.getToken() != null) {
            skipRecursion = true;
            switch (ast.getToken().getType()) {
                case HiveParser.TOK_SELECTDI:
                case HiveParser.TOK_SELECT:
                case HiveParser.TOK_WHERE:
                    break;
                case HiveParser.TOK_INSERT_INTO:
                    tabName = BaseSemanticAnalyzer.getUnescapedName((ASTNode) ast
                            .getChild(0).getChild(0));
                    pr.addChildTab(tabName);
                    break;
                case HiveParser.TOK_DESTINATION:
                    ASTNode ch = (ASTNode) ast.getChild(0);
                    if (ch.getToken().getType() == HiveParser.TOK_TAB) {
                        tabName = BaseSemanticAnalyzer
                                .getUnescapedName((ASTNode) ast.getChild(0)
                                        .getChild(0));
                        pr.addChildTab(tabName);
                    }
                    break;
                case HiveParser.TOK_FROM:
                    // Check if this is a subquery / lateral view
                    ASTNode frm = (ASTNode) ast.getChild(0);
                    if (frm.getToken().getType() == HiveParser.TOK_TABREF) {
                        processTable(frm, pr);
                    } else if (frm.getToken().getType() == HiveParser.TOK_SUBQUERY) {
                        processSubQuery(frm, pr);
                    } else if (frm.getToken().getType() == HiveParser.TOK_LATERAL_VIEW) {
                        processLateralView(frm, pr);
                    } else if (isJoinToken(frm)) {
                        processJoin(frm, pr);
                    }
                    break;
                case HiveParser.TOK_CLUSTERBY:
                case HiveParser.TOK_DISTRIBUTEBY:
                case HiveParser.TOK_SORTBY:
                case HiveParser.TOK_ORDERBY:
                case HiveParser.TOK_GROUPBY:
                case HiveParser.TOK_HAVING:
                case HiveParser.TOK_LIMIT:
                case HiveParser.TOK_ANALYZE:
                case HiveParser.TOK_UNION:
                    break;
                default:
                    skipRecursion = false;
                    break;
            }
        }
        if (!skipRecursion) {
            // Iterate over the rest of the children
            int child_count = ast.getChildCount();
            for (int child_pos = 0; child_pos < child_count; ++child_pos) {
                // Recurse
                doPhase1((ASTNode) ast.getChild(child_pos), pr);
            }
        }
    }

    private void processTable(ASTNode tabref, AnalyzeResult pr) {
        ASTNode tableTree = (ASTNode) (tabref.getChild(0));
        String tabName = BaseSemanticAnalyzer.getUnescapedName(tableTree);
        pr.addParentTab(tabName);
    }

    private void processSubQuery(ASTNode subq, AnalyzeResult pr) {
        ASTNode subqref = (ASTNode) subq.getChild(0);
        doPhase1QBExpr(subqref, pr);
    }

    private void doPhase1QBExpr(ASTNode ast, AnalyzeResult pr) {
        switch (ast.getToken().getType()) {
            case HiveParser.TOK_QUERY: {
                doPhase1(ast, pr);
            }
            break;
            case HiveParser.TOK_UNION: {
                // query 1
                assert (ast.getChild(0) != null);
                doPhase1QBExpr((ASTNode) ast.getChild(0), pr);
                // query 2
                assert (ast.getChild(1) != null);
                doPhase1QBExpr((ASTNode) ast.getChild(1), pr);
            }
            break;
        }
    }

    private void processLateralView(ASTNode lateralView, AnalyzeResult pr) {
        int numChildren = lateralView.getChildCount();

        assert (numChildren == 2);
        ASTNode next = (ASTNode) lateralView.getChild(1);
        switch (next.getToken().getType()) {
            case HiveParser.TOK_TABREF:
                processTable(next, pr);
                break;
            case HiveParser.TOK_SUBQUERY:
                processSubQuery(next, pr);
                break;
            case HiveParser.TOK_LATERAL_VIEW:
                processLateralView(next, pr);
                break;
        }

    }

    private void processJoin(ASTNode join, AnalyzeResult pr) {
        int numChildren = join.getChildCount();
        for (int num = 0; num < numChildren; num++) {
            ASTNode child = (ASTNode) join.getChild(num);
            if (child.getToken().getType() == HiveParser.TOK_TABREF) {
                processTable(child, pr);
            } else if (child.getToken().getType() == HiveParser.TOK_SUBQUERY) {
                processSubQuery(child, pr);
            } else if (child.getToken().getType() == HiveParser.TOK_LATERAL_VIEW) {
                // SELECT * FROM src1 LATERAL VIEW udtf() AS myTable JOIN src2
                // ...
                // is not supported. Instead, the lateral view must be in a
                // subquery
                // SELECT * FROM (SELECT * FROM src1 LATERAL VIEW udtf() AS
                // myTable) a
                // JOIN src2 ...
                processLateralView(child, pr);
            } else if (isJoinToken(child)) {
                processJoin(child, pr);
            }
        }

    }

    private boolean isJoinToken(ASTNode node) {
        if ((node.getToken().getType() == HiveParser.TOK_JOIN)
                || (node.getToken().getType() == HiveParser.TOK_LEFTOUTERJOIN)
                || (node.getToken().getType() == HiveParser.TOK_RIGHTOUTERJOIN)
                || (node.getToken().getType() == HiveParser.TOK_FULLOUTERJOIN)
                || (node.getToken().getType() == HiveParser.TOK_LEFTSEMIJOIN)
                || (node.getToken().getType() == HiveParser.TOK_UNIQUEJOIN)) {
            return true;
        }

        return false;
    }
}
