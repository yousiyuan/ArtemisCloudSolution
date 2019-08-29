package org.lnson.artemis.rabbit.config;

import org.springframework.amqp.core.Binding;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RabbitBinding implements Serializable {

    private String destination;
    private Binding.DestinationType destinationType;
    private String exchange;
    private List<String> routingKeyList;
    private Map<String, Object> bindArguments;


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Binding.DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(Binding.DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public List<String> getRoutingKeyList() {
        return routingKeyList;
    }

    public void setRoutingKeyList(List<String> routingKeyList) {
        this.routingKeyList = routingKeyList;
    }

    public Map<String, Object> getBindArguments() {
        return bindArguments == null ? new HashMap<String, Object>() : bindArguments;
    }

    public void setBindArguments(Map<String, Object> bindArguments) {
        this.bindArguments = bindArguments;
    }

}
