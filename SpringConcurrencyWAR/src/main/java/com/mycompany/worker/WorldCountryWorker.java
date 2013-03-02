/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import com.mycompany.entity.Country;
import com.mycompany.entity.controller.CountryJpaController;
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
public class WorldCountryWorker implements Callable<List<Country>> {

  @PersistenceContext(unitName = "com.mycompany.demo_SpringConcurrencyWAR_war_1.0-SNAPSHOTPU")
  EntityManager entityManager;
  @Resource
  UserTransaction userTransaction;

  public WorldCountryWorker(EntityManager entityManager, UserTransaction userTransaction) {
    this.entityManager = entityManager;
    this.userTransaction = userTransaction;
  }

  @Override
  public List<Country> call() throws Exception {
    CountryJpaController countryJpaController =
            new CountryJpaController(userTransaction, entityManager.getEntityManagerFactory());
    insertDealy(0);
    return countryJpaController.findCountryEntities();
  }

  private void insertDealy(long milliSeconds) {
    try {
      Thread.sleep(milliSeconds);
    } catch (InterruptedException ex) {
      Logger.getLogger(WorldCountryWorker.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
