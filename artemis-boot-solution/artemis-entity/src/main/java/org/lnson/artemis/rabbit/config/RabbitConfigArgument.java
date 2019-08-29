package org.lnson.artemis.rabbit.config;

import java.io.Serializable;
import java.util.Map;

public class RabbitConfigArgument implements Serializable {

    private String addresses;
    private String virtualHost;
    private String userName;
    private String password;
    private Map<String, RabbitExchange> exchangeMap;
    private Map<String, RabbitBinding> bindingMap;
    private Map<String, RabbitQueue> queueMap;


    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, RabbitExchange> getExchangeMap() {
        return exchangeMap;
    }

    public void setExchangeMap(Map<String, RabbitExchange> exchangeMap) {
        this.exchangeMap = exchangeMap;
    }

    public Map<String, RabbitBinding> getBindingMap() {
        return bindingMap;
    }

    public void setBindingMap(Map<String, RabbitBinding> bindingMap) {
        this.bindingMap = bindingMap;
    }

    public Map<String, RabbitQueue> getQueueMap() {
        return queueMap;
    }

    public void setQueueMap(Map<String, RabbitQueue> queueMap) {
        this.queueMap = queueMap;
    }

}
