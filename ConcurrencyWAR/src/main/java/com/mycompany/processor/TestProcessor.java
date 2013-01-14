/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.processor;

import com.mycompany.entity.City;
import com.mycompany.entity.Country;
import com.mycompany.entity.CountryLanguage;
import com.mycompany.perfmonitor.interceptor.Aggregateable;
import com.mycompany.servlet.TestServlet;
import com.mycompany.worker.WeatherWorker;
import com.mycompany.worker.WorldCityWorker;
import com.mycompany.worker.WorldCountryWorker;
import com.mycompany.worker.WorldLanguageWorker;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dchakr
 */
public class TestProcessor {

  @Inject
  WorldCityWorker worldCityWorker;
  @Inject
  WorldCountryWorker worldCountryWorker;
  @Inject
  WorldLanguageWorker worldLanguageWorker;
  @Inject
  WeatherWorker weatherWorker;

  @Aggregateable
  public void process(HttpServletRequest request, HttpServletResponse response) {
    Future<List<City>> asyncCities = worldCityWorker.getCitiesAsync();
    Future<List<Country>> asyncCountries = worldCountryWorker.getCountriesAsync();
    Future<List<CountryLanguage>> asyncLanguages = worldLanguageWorker.getLanguagesAsync();

    Future<String> asyncString = weatherWorker.getWeatherAsync("India", "Bombay");

    try {
      request.setAttribute("asyncCities", asyncCities.get().size());
      request.setAttribute("asyncCountries", asyncCountries.get().size());
      request.setAttribute("asyncLanguages", asyncLanguages.get().size());
      request.setAttribute("asyncString", asyncString.get());
    } catch (InterruptedException ex) {
      Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ExecutionException ex) {
      Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}