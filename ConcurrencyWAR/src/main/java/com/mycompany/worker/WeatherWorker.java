/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import net.webservicex.GlobalWeather;
import net.webservicex.GlobalWeatherSoap;

/**
 *
 * @author dchakr
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class WeatherWorker {

  public String getWeather(String country, String city) {
    GlobalWeather globalWeather = new GlobalWeather();
    GlobalWeatherSoap globalWeatherSoap = globalWeather.getGlobalWeatherSoap();
    return globalWeatherSoap.getWeather(city, country);
  }

  @Asynchronous
  public Future<String> getWeatherAsync(String country, String city) {
    return new AsyncResult<String>(getWeather(country, city));
  }
}