/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import net.webservicex.GlobalWeather;
import net.webservicex.GlobalWeatherSoap;

/**
 *
 * @author dchakr
 */
public class WeatherWorker implements Callable<String> {

  private String city;
  private String country;

  public WeatherWorker(String country, String city) {
    this.city = city;
    this.country = country;
  }

  @Override
  public String call() throws Exception {
    GlobalWeather globalWeather = new GlobalWeather();
    GlobalWeatherSoap globalWeatherSoap = globalWeather.getGlobalWeatherSoap();

    BindingProvider bindingProvider = (BindingProvider) globalWeatherSoap;
    bindingProvider.getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:6789/globalweather.asmx");
    insertDealy(1500);
    return globalWeatherSoap.getWeather(city, country);
  }

  private void insertDealy(long milliSeconds) {
    try {
      Thread.sleep(milliSeconds);
    } catch (InterruptedException ex) {
      Logger.getLogger(WeatherWorker.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
