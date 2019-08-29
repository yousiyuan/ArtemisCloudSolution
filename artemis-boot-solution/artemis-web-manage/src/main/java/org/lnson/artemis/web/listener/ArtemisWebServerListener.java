package org.lnson.artemis.web.listener;

import org.lnson.artemis.common.InetCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

import javax.servlet.ServletContext;

/**
 * 在 ServletWebServerApplicationContext 已经刷新 并且 WebServer已经准备好 之后 发布的事件
 * 用于获取正在运行的服务器的本地端口
 * 正常情况下，它将被启动，哈哈哈
 */
public class ArtemisWebServerListener implements ApplicationListener<ServletWebServerInitializedEvent> {

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
