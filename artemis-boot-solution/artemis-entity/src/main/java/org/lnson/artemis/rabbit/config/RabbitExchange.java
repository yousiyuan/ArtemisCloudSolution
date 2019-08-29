package org.lnson.artemis.rabbit.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RabbitExchange implements Serializable {

    private String exchangeName;
    private String exchangeType;
    private Boolean exchangeDurable;
    private Boolean exchangeAutoDelete;
    private Map<String, Object> exchangeArguments;


    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Boolean getExchangeDurable() {
        return exchangeDurable;
    }

    public void setExchangeDurable(Boolean exchangeDurable) {
        this.exchangeDurable = exchangeDurable;
    }

    public Boolean getExchangeAutoDelete() {
        return exchangeAutoDelete;
    }

    public void setExchangeAutoDelete(Boolean exchangeAutoDelete) {
        this.exchangeAutoDelete = exchangeAutoDelete;
    }

    public Map<String, Object> getExchangeArguments() {
        return exchangeArguments == null ? new HashMap<String, Object>() : exchangeArguments;
    }

    public void setExchangeArguments(Map<String, Object> exchangeArguments) {
        this.exchangeArguments = exchangeArguments;
    }

}
