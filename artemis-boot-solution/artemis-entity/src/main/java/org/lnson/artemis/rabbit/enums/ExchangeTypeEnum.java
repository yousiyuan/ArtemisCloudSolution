package org.lnson.artemis.rabbit.enums;

/**
 * 枚举 交换机类型
 */
public enum ExchangeTypeEnum {

    DIRECT_EXCHANGE("direct"),
    FANOUT_EXCHANGE("fanout"),
    TOPIC_EXCHANGE("topic");

    private String exchangeType;

    public String getExchangeType() {
        return exchangeType;
    }

    ExchangeTypeEnum(String exchangeType) {
        this.exchangeType = exchangeType;
    }

}
