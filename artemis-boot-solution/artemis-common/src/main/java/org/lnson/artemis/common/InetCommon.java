package org.lnson.artemis.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

public class InetCommon {

    private final static Logger logger = LoggerFactory.getLogger(InetCommon.class);

    public static String getHostAddress(String contextPath, Integer port) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            if (localHost == null) {
                return null;
            }
            return MessageFormat.format("http://{0}:{1}{2}", localHost.getHostAddress(), String.valueOf(port), contextPath);
        } catch (UnknownHostException e) {
            logger.error(GenerateCommon.printException(e));
            return null;
        }
    }
}
