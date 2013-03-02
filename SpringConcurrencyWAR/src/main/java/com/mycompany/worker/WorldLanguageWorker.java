/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import com.mycompany.entity.CountryLanguage;
import com.mycompany.entity.controller.CountryLanguageJpaController;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author dchakr
 */
public class WorldLanguageWorker implements Callable<List<CountryLanguage>> {

  @PersistenceContext(unitName = "com.mycompany.demo_SpringConcurrencyWAR_war_1.0-SNAPSHOTPU")
  EntityManager entityManager;
  @Resource
  UserTransaction userTransaction;

  public WorldLanguageWorker(EntityManager entityManager, UserTransaction userTransaction) {
    this.entityManager = entityManager;
    this.userTransaction = userTransaction;
  }

  @Override
  public List<CountryLanguage> call() throws Exception {
    CountryLanguageJpaController countryLanguageJpaController =
            new CountryLanguageJpaController(userTransaction, entityManager.getEntityManagerFactory());
    insertDealy(0);
    return countryLanguageJpaController.findCountryLanguageEntities();
  }

  private void insertDealy(long milliSeconds) {
    try {
      Thread.sleep(milliSeconds);
    } catch (InterruptedException ex) {
      Logger.getLogger(WorldLanguageWorker.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
