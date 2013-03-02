/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import com.mycompany.entity.Country;
import com.mycompany.entity.controller.CountryJpaController;
import com.mycompany.perfmonitor.interceptor.Stopwatchable;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
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
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Stopwatchable
public class WorldCountryWorker {

  @PersistenceContext(unitName = "com.mycompany.demo_ConcurrencyWAR_war_1.0-SNAPSHOTPU")
  EntityManager entityManager;
  @Resource
  UserTransaction userTransaction;

  public List<Country> getCountries() {
    CountryJpaController countryJpaController =
            new CountryJpaController(userTransaction, entityManager.getEntityManagerFactory());
    insertDealy(0);
    return countryJpaController.findCountryEntities();
  }

  @Asynchronous
  public Future<List<Country>> getCountriesAsync() {
    return new AsyncResult<List<Country>>(getCountries());
  }

  private void insertDealy(long milliSeconds) {
    try {
      Thread.sleep(milliSeconds);
    } catch (InterruptedException ex) {
      Logger.getLogger(WorldCountryWorker.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}