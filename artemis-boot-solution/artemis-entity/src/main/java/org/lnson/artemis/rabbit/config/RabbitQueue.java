package org.lnson.artemis.rabbit.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RabbitQueue implements Serializable {

    private String queueName;
    private Boolean queueDurable;
    private Boolean queueExclusive;
    private Boolean queueAutoDelete;
    private String delegateClass;
    private String delegateMethod;
    private Map<String, Object> queueArguments;


    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Boolean getQueueDurable() {
        return queueDurable;
    }

    public void setQueueDurable(Boolean queueDurable) {
        this.queueDurable = queueDurable;
    }

    public Boolean getQueueExclusive() {
        return queueExclusive;
    }

    public void setQueueExclusive(Boolean queueExclusive) {
        this.queueExclusive = queueExclusive;
    }

    public Boolean getQueueAutoDelete() {
        return queueAutoDelete;
    }

    public void setQueueAutoDelete(Boolean queueAutoDelete) {
        this.queueAutoDelete = queueAutoDelete;
    }

    public String getDelegateClass() {
        return delegateClass;
    }

    public void setDelegateClass(String delegateClass) {
        this.delegateClass = delegateClass;
    }

    public String getDelegateMethod() {
        return delegateMethod;
    }

    public void setDelegateMethod(String delegateMethod) {
        this.delegateMethod = delegateMethod;
    }

    public Map<String, Object> getQueueArguments() {
        return queueArguments == null ? new HashMap<String, Object>() : queueArguments;
    }

    public void setQueueArguments(Map<String, Object> queueArguments) {
        this.queueArguments = queueArguments;
    }

}
