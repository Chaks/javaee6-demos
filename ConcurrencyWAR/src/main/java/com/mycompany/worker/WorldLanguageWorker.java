/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import com.mycompany.entity.CountryLanguage;
import com.mycompany.entity.controller.CountryLanguageJpaController;
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
public class WorldLanguageWorker {

  @PersistenceContext(unitName = "com.mycompany.demo_ConcurrencyWAR_war_1.0-SNAPSHOTPU")
  EntityManager entityManager;
  @Resource
  UserTransaction userTransaction;

  public List<CountryLanguage> getLanguages() {
    CountryLanguageJpaController countryLanguageJpaController =
            new CountryLanguageJpaController(userTransaction, entityManager.getEntityManagerFactory());
    insertDealy(0);
    return countryLanguageJpaController.findCountryLanguageEntities();
  }

  @Asynchronous
  public Future<List<CountryLanguage>> getLanguagesAsync() {
    return new AsyncResult<List<CountryLanguage>>(getLanguages());
  }

  private void insertDealy(long milliSeconds) {
    try {
      Thread.sleep(milliSeconds);
    } catch (InterruptedException ex) {
      Logger.getLogger(WorldLanguageWorker.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}