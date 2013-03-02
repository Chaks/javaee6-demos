/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import com.mycompany.entity.City;
import com.mycompany.entity.controller.CityJpaController;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 *
 * @author dchakr
 */
public class WorldCityWorker implements Callable<List<City>> {

  private EntityManager entityManager;
  private UserTransaction userTransaction;

  public WorldCityWorker(EntityManager entityManager, UserTransaction userTransaction) {
    this.entityManager = entityManager;
    this.userTransaction = userTransaction;
  }

  @Override
  public List<City> call() throws Exception {
    CityJpaController cityJpaController =
            new CityJpaController(userTransaction, entityManager.getEntityManagerFactory());
    insertDealy(0);
    return cityJpaController.findCityEntities();
  }

  private void insertDealy(long milliSeconds) {
    try {
      Thread.sleep(milliSeconds);
    } catch (InterruptedException ex) {
      Logger.getLogger(WorldCityWorker.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
