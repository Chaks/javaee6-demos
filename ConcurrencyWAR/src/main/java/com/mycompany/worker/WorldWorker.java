/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import com.mycompany.entity.City;
import com.mycompany.entity.Country;
import com.mycompany.entity.CountryLanguage;
import com.mycompany.entity.controller.CityJpaController;
import com.mycompany.entity.controller.CountryJpaController;
import com.mycompany.entity.controller.CountryLanguageJpaController;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author dchakr
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class WorldWorker {
  
  @PersistenceContext(unitName = "com.mycompany.demo_ConcurrencyWAR_war_1.0-SNAPSHOTPU")
  EntityManager entityManager;
  @Resource
  UserTransaction userTransaction;
  
  public List<City> getCities() {
    CityJpaController cityJpaController =
            new CityJpaController(userTransaction, entityManager.getEntityManagerFactory());
    insertDealy(1000);
    return cityJpaController.findCityEntities();
  }
  
  public List<Country> getCountries() {
    CountryJpaController countryJpaController =
            new CountryJpaController(userTransaction, entityManager.getEntityManagerFactory());
    insertDealy(500);
    return countryJpaController.findCountryEntities();
  }
  
  public List<CountryLanguage> getLanguages() {
    CountryLanguageJpaController countryLanguageJpaController =
            new CountryLanguageJpaController(userTransaction, entityManager.getEntityManagerFactory());
    insertDealy(500);
    return countryLanguageJpaController.findCountryLanguageEntities();
  }
  
  public Future<List<City>> getCitiesAsync() {
    return new AsyncResult<List<City>>(getCities());
  }
  
  public Future<List<Country>> getCountriesAsync() {
    return new AsyncResult<List<Country>>(getCountries());
  }
  
  public Future<List<CountryLanguage>> getLanguagesAsync() {
    return new AsyncResult<List<CountryLanguage>>(getLanguages());
  }
  
  private void insertDealy(long milliSeconds) {
    try {
      Thread.sleep(milliSeconds);
    } catch (InterruptedException ex) {
      Logger.getLogger(WorldWorker.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
