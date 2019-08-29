package org.lnson.artemis.service.fallback.factory;

import feign.hystrix.FallbackFactory;
import org.lnson.artemis.service.DepartmentClientService;
import org.lnson.artemis.service.fallback.DepartmentClientServiceFallback;
import org.springframework.stereotype.Component;

@Component
public class DepartmentClientServiceFallbackFactory implements FallbackFactory<DepartmentClientService> {
    @Override
    public DepartmentClientService create(Throwable throwable) {
        return new DepartmentClientServiceFallback();
    }
}
