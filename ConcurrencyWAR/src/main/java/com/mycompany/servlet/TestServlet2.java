/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.mycompany.worker.WeatherWorker;
import com.mycompany.worker.WorldCityWorker;
import com.mycompany.worker.WorldCountryWorker;
import com.mycompany.worker.WorldLanguageWorker;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dchakr
 */
@WebServlet(name = "TestServlet2", urlPatterns = {"/TestServlet2"})
public class TestServlet2 extends HttpServlet {

  @Inject
  WorldCityWorker worldCityWorker;
  @Inject
  WorldCountryWorker worldCountryWorker;
  @Inject
  WorldLanguageWorker worldLanguageWorker;
  @Inject
  WeatherWorker weatherWorker;

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
    try {
      /* TODO output your page here. You may use following sample code. */
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet TestServlet2</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet TestServlet2 at " + request.getContextPath() + "</h1>");

      out.println("Cities: " + worldCityWorker.getCities().size());
      out.println("</br>");
      out.println("Countries: " + worldCountryWorker.getCountries().size());
      out.println("</br>");
      out.println("Languages: " + worldLanguageWorker.getLanguages().size());
      out.println("</br>");
      out.println("Weather: " + weatherWorker.getWeather("India", "Bombay"));

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
