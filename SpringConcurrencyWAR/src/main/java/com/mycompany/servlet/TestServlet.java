/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.mycompany.entity.City;
import com.mycompany.entity.Country;
import com.mycompany.entity.CountryLanguage;
import com.mycompany.worker.WeatherWorker;
import com.mycompany.worker.WorldCityWorker;
import com.mycompany.worker.WorldCountryWorker;
import com.mycompany.worker.WorldLanguageWorker;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.commonj.WorkManagerTaskExecutor;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author dchakr
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {

  @PersistenceContext(unitName = "com.mycompany.demo_SpringConcurrencyWAR_war_1.0-SNAPSHOTPU")
  EntityManager entityManager;
  @Resource
  UserTransaction userTransaction;

  /**
   * Processes requests for both HTTP
   * <code>GET</code> and
   * <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    ServletContext servletContext = this.getServletContext();
    ApplicationContext applicationContext =
            WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

    WorkManagerTaskExecutor wmte =
            (WorkManagerTaskExecutor) applicationContext.getBean("taskExecutor");

    Future<List<City>> asyncCities =
            wmte.submit(new WorldCityWorker(entityManager, userTransaction));
    Future<List<Country>> asyncCountries =
            wmte.submit(new WorldCountryWorker(entityManager, userTransaction));
    Future<List<CountryLanguage>> asyncLanguages =
            wmte.submit(new WorldLanguageWorker(entityManager, userTransaction));

    Future<String> asyncString = wmte.submit(new WeatherWorker("India", "Bombay"));
    try {
      /* TODO output your page here. You may use following sample code. */
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet TestServlet</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet TestServlet at " + request.getContextPath() + "</h1>");
      try {
        out.println("Cities: " + asyncCities.get().size());
        out.println("</br>");
        out.println("Countries: " + asyncCountries.get().size());
        out.println("</br>");
        out.println("Languages: " + asyncLanguages.get().size());
        out.println("</br>");
        out.println("Weather: " + asyncString.get());
      } catch (InterruptedException ex) {
        Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ExecutionException ex) {
        Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
      out.println("</body>");
      out.println("</html>");
    } finally {
      out.close();
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP
   * <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP
   * <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}
