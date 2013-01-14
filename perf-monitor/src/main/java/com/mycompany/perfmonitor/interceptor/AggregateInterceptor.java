/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.perfmonitor.interceptor;

import java.util.List;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.javasimon.Simon;
import org.javasimon.SimonManager;

/**
 *
 * @author dchakr
 */
@Aggregateable
@Interceptor
public class AggregateInterceptor {

  private static final String DEFAULT_INTERCEPTOR_PREFIX = "org.javasimon.business";

  @AroundInvoke
  public Object aggregate(InvocationContext invocationContext) throws Exception {

    System.out.println("^^^^^^^^^^^^ aggregate enter ^^^^^^^^^^^^^^^^");
    Object retObject = null;
    try {
      retObject = invocationContext.proceed();
    } catch (Exception e) {
      throw e;
    } finally {
      Simon parent = SimonManager.getSimon(DEFAULT_INTERCEPTOR_PREFIX);
      if (parent != null) {
        List<Simon> children = parent.getChildren();
        if (children != null) {
          for (Simon child : children) {
            //System.out.println(child);
            if (child.getChildren() != null) {
              List<Simon> children1 = child.getChildren();
              for (Simon child1 : children1) {
                System.out.println(child1.sample());
              }
            } else {
              System.out.println(child.sample());
            }
          }
        }
      }
      System.out.println("^^^^^^^^^^^^ aggregate exit ^^^^^^^^^^^^^^^^");
    }
    return retObject;
  }
}
