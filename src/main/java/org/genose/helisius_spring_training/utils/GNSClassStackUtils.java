package org.genose.helisius_spring_training.utils;

import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode
public class GNSClassStackUtils {
    static public Logger logger = LoggerFactory.getLogger(GNSClassStackUtils.class);

    static public String getEnclosingMethodObject(Object thisClass) {
        String currentMethodName = "nullMethodName";

        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            currentMethodName = stackTrace[2].getMethodName();
            GNSClassStackUtils.logger.debug(" Method @ 0 : " + stackTrace[0].getMethodName() + "::" + stackTrace[0].getClassName());
            GNSClassStackUtils.logger.debug(" Method @ 1 : " + stackTrace[1].getMethodName() + "::" + stackTrace[1].getClassName());
            GNSClassStackUtils.logger.debug(" Method @ 2 : " + stackTrace[2].getMethodName() + "::" + stackTrace[2].getClassName());

            if (thisClass != null) {
                if (thisClass.getClass().getEnclosingMethod() != null) {
                    currentMethodName = thisClass.getClass().getEnclosingMethod().getName();
                }
            }
        } catch (Exception e) {
            GNSClassStackUtils.logger.error(GNSClassStackUtils.class.getSimpleName() + " : Excepetion : " + e);
        }
        return currentMethodName;
    }
}
