/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.perfmonitor.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.javasimon.javaee.SimonInterceptor;

/**
 *
 * @author dchakr
 */
@Stopwatchable
@Interceptor
public class StopwatchInterceptor extends SimonInterceptor {

  @Override
  @AroundInvoke
  public Object monitor(InvocationContext context) throws Exception {
    System.out.println("^^^^^^^^^^^^ monitor ^^^^^^^^^^^^^^^^");
    return super.monitor(context);
  }
}