/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import net.webservicex.GlobalWeather;
import net.webservicex.GlobalWeatherSoap;

/**
 *
 * @author dchakr
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class WeatherWorker {

  @Inject
  GlobalWeather globalWeather;

  public String getWeather(String country, String city) {
    GlobalWeatherSoap globalWeatherSoap = globalWeather.getGlobalWeatherSoap();

    BindingProvider bindingProvider = (BindingProvider) globalWeatherSoap;
    bindingProvider.getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:6789/globalweather.asmx");
    insertDealy(1500);
    return globalWeatherSoap.getWeather(city, country);
  }

  @Asynchronous
  public Future<String> getWeatherAsync(String country, String city) {
    return new AsyncResult<String>(getWeather(country, city));
  }

  private void insertDealy(long milliSeconds) {
    try {
      Thread.sleep(milliSeconds);
    } catch (InterruptedException ex) {
      Logger.getLogger(WorldWorker.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
