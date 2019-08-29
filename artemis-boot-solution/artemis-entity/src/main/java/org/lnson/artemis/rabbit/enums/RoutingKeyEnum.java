package org.lnson.artemis.rabbit.enums;

/**
 * 枚举 路由标识
 */
public enum RoutingKeyEnum {
    QUERY_KEY("query"),
    LIST_KEY("list"),
    INSERT_KEY("insert"),
    DELETE_KEY("delete"),
    UPDATE_KEY("update");

    RoutingKeyEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
