/**
 *
 */
package com.dianping.data.warehouse.core.common.SqlParser;

import org.apache.commons.lang.text.StrBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author leo.chen
 */
public class AnalyzeResult {
    private String hiveSql;
    private Set<String> parentTabs;
    private Set<String> childTabs;

    public AnalyzeResult(String sql) {
        this.hiveSql = sql;
        parentTabs = new HashSet<String>();
        childTabs = new HashSet<String>();
    }

    public void addParentTab(String srcTab) {
        parentTabs.add(srcTab);
    }

    public void addChildTab(String tgtTab) {
        childTabs.add(tgtTab);
    }

    public Set<String> getParentTabs() {
        return parentTabs;
    }

    public Set<String> getChildTabs() {
        return childTabs;
    }

    public String toString() {
        StrBuilder sb = new StrBuilder(this.hiveSql).append("\n");
        sb.append("Analyze Result>>>>\n");
        sb.append("parent tables:");
        for (String src : parentTabs) {
            sb.append(" " + src);
        }
        sb.append("\n");
        sb.append("child tables:");
        for (String tgt : childTabs) {
            sb.append(" " + tgt);
        }
        sb.append("\n");
        return sb.toString();
    }


}
