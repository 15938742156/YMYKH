package com.ykh.yinmeng.ymykh2.common.jpush;

import java.util.Set;

/**
 * 2019/1/3 10:43
 * Description：
 */
public class TagAliasBean {
    public int action;
    public Set<String> tags;
    public String alias;
    public boolean isAliasAction;

    @Override
    public String toString() {
        return "TagAliasBean{" +
                "action=" + action +
                ", tags=" + tags +
                ", alias='" + alias + '\'' +
                ", isAliasAction=" + isAliasAction +
                '}';
    }
}
