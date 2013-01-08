/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worker;

import com.mycompany.entity.Customer;
import com.mycompany.entity.controller.CustomerJpaController;
import java.util.List;
import java.util.concurrent.Future;
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
public class CustomerWorker {

  @PersistenceContext(unitName = "com.mycompany.demo_ConcurrencyWAR_war_1.0-SNAPSHOTPU")
  EntityManager entityManager;
  @Resource
  UserTransaction userTransaction;

  public List<Customer> getCustomers() {
    CustomerJpaController customerJpaController =
            new CustomerJpaController(userTransaction, entityManager.getEntityManagerFactory());
    return customerJpaController.findCustomerEntities();
  }

  public Future<List<Customer>> getCustomersAsync() {
    return new AsyncResult<List<Customer>>(getCustomers());
  }
}