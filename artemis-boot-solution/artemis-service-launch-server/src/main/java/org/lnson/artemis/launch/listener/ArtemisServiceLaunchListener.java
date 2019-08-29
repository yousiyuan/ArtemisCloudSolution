package org.lnson.artemis.launch.listener;

import org.lnson.artemis.common.InetCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

import javax.servlet.ServletContext;

public class ArtemisServiceLaunchListener implements ApplicationListener<ServletWebServerInitializedEvent> {

    @Autowired
    private ServletContext servletContext;

    private String root;

    public String getRoot() {
        return root;
    }

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent webServerInitializedEvent) {
        this.root = InetCommon.getHostAddress(servletContext.getContextPath(), webServerInitializedEvent.getWebServer().getPort());
    }

}
