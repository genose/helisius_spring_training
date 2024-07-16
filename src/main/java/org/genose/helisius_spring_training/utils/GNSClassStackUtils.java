package org.genose.helisius_spring_training.utils;

import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode
public class GNSClassStackUtils {
    static public Logger logger = LoggerFactory.getLogger(GNSClassStackUtils.class);

    static public Class<?> getEnclosingClass() {
        Class currentClass = GNSClassStackUtils.class;
        System.out.println(" EnclosingClass class: Start : " + currentClass.getName());
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace.length > 1) {
                if (stackTrace[2] != null) {
                    if (!stackTrace[2].getClassName().equals(GNSClassStackUtils.class.getName())
                            && stackTrace[2].getClass().isAnonymousClass()) {
                        currentClass = ((stackTrace.length <= 3) ? Class.forName(stackTrace[2].getClassName()) : Class.forName(stackTrace[3].getClassName()));
                    } else {
                        currentClass = Class.forName(stackTrace[2].getClassName());
                    }
                }
            }
        } catch (Exception e) {
            GNSClassStackUtils.logger.error(GNSClassStackUtils.class.getSimpleName() + " : Excepetion : " + e);
        }
        return currentClass;
    }

    static public String getEnclosingMethodObject(Object thisClass) {
        String currentMethodName = "nullMethodName";
        System.out.println(" getEnclosingMethodObject class : ");
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            currentMethodName = stackTrace[2].getMethodName();
            Class currentClass = stackTrace[2].getClass();
            // GNSClassStackUtils.logger.debug(" Method @ 0 : " + stackTrace[0].getMethodName() + "::" + stackTrace[0].getClassName());
            //GNSClassStackUtils.logger.debug(" Method @ 1 : " + stackTrace[1].getMethodName() + "::" + stackTrace[1].getClassName());
            //GNSClassStackUtils.logger.debug(" Method @ 2 : " + stackTrace[2].getMethodName() + "::" + stackTrace[2].getClassName());

            if (thisClass != null) {
                if (thisClass.getClass().getEnclosingMethod() != null) {
                    GNSClassStackUtils.logger.debug(" Method @ 2 : " + stackTrace[2].getMethodName() + "::" + stackTrace[2].getClassName());
                    currentMethodName = thisClass.getClass().getEnclosingMethod().getName();
                } else if (stackTrace.length > 2 && stackTrace[2].getClassName().equals(GNSClassStackUtils.class.getName())) {
                    GNSClassStackUtils.logger.debug(" Method @ 2 : " + stackTrace[3].getMethodName() + "::" + stackTrace[3].getClassName());
                    currentMethodName = stackTrace[3].getClass().getEnclosingMethod().getName();
                }
            }
        } catch (Exception e) {
            System.out.println(" getEnclosingMethodObject class : Error : " + e.getMessage());
            GNSClassStackUtils.logger.error(GNSClassStackUtils.class.getSimpleName() + " : Excepetion : " + e);
        }
        return currentMethodName;
    }
}
