package org.lnson.artemis.common;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GenerateCommon {

    /**
     * 打印详细错误信息
     */
    public static String printException(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        printWriter.flush();
        printWriter.close();
        return stringWriter.toString();
    }

}
