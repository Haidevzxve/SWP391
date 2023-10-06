/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DressPhotoComboDAO;
import dao.LocationDAO;
import dao.PhotographyStudiosDAO;
import dao.RentalProductDAO;
import dto.DressPhotoCombo;
import dto.Location;
import dto.OrderDetail;
import dto.PhotographyStudio;
import dto.RentalProduct;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Contant;
import util.PaginationHelper;

/**
 *
 * @author ptd
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/CategoryServlet"})
public class CategoryServlet extends HttpServlet {

    public final String HOME_PAGE = "home.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = HOME_PAGE;
        
        HttpSession session = request.getSession();

        try {

            String value = request.getParameter("category");


            LocationDAO locationDAO = new LocationDAO();
            RentalProductDAO rentalDAO = new RentalProductDAO();
            PhotographyStudiosDAO photoDAO = new PhotographyStudiosDAO();
            DressPhotoComboDAO dressPhotoComboDAO = new DressPhotoComboDAO();

            List<Location> listLocation = locationDAO.getAllLocation();
            List<RentalProduct> listProduct = rentalDAO.getAllRentalProduct();
            List<PhotographyStudio> listStudio = photoDAO.getAllPhotographyStudio();
            List<DressPhotoCombo> listCombo = dressPhotoComboDAO.getAllDressPhotoCombo();
            if ("location".equals(value)) {
                // Set the number of entities per page
                int entitiesPerPage = 6;

                // Calculate the total pages for all lists combined
                int totalEntities = listLocation.size();
                int totalPages = (int) Math.ceil((double) totalEntities / entitiesPerPage);

                // Retrieve the current page number from the request parameters
                int currentPage = PaginationHelper.getCurrentPage(request, "page", totalPages);

                List<OrderDetail> listOrder = new ArrayList<>();
                for (Location location : listLocation) {
                    OrderDetail orderDetail = PaginationHelper.mapLoationToOrderDetail(location);

                    listOrder.add(orderDetail);
                }

                List<OrderDetail> listOrderDetailPage = PaginationHelper.getPageEntities(listOrder, currentPage, entitiesPerPage);
                session.setAttribute("LIST_ORDER_PAGING", listOrderDetailPage);

                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", currentPage);
            } else if ("product".equals(value)) {
                // Set the number of entities per page
                int entitiesPerPage = Contant.PAGE_SIZE;

                // Calculate the total pages for all lists combined
                int totalEntities = listProduct.size();
                int totalPages = (int) Math.ceil((double) totalEntities / entitiesPerPage);

                // Retrieve the current page number from the request parameters
                int currentPage = PaginationHelper.getCurrentPage(request, "page", totalPages);

                List<OrderDetail> listOrder = new ArrayList<>();
                for (RentalProduct product : listProduct) {
                    OrderDetail orderDetail = PaginationHelper.mapProductToOrderDetail(product);

                    listOrder.add(orderDetail);
                }

                List<OrderDetail> listOrderDetailPage = PaginationHelper.getPageEntities(listOrder, currentPage, entitiesPerPage);
                session.setAttribute("LIST_ORDER_PAGING", listOrderDetailPage);

                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", currentPage);

            } else if ("studio".equals(value)) {
                // Set the number of entities per page
                int entitiesPerPage = Contant.PAGE_SIZE;

                // Calculate the total pages for all lists combined
                int totalEntities = listStudio.size();
                int totalPages = (int) Math.ceil((double) totalEntities / entitiesPerPage);

                // Retrieve the current page number from the request parameters
                int currentPage = PaginationHelper.getCurrentPage(request, "page", totalPages);

                List<OrderDetail> listOrder = new ArrayList<>();
                for (PhotographyStudio studio : listStudio) {
                    OrderDetail orderDetail = PaginationHelper.mapStudioToOrderDetail(studio);

                    listOrder.add(orderDetail);
                }

                List<OrderDetail> listOrderDetailPage = PaginationHelper.getPageEntities(listOrder, currentPage, entitiesPerPage);
                session.setAttribute("LIST_ORDER_PAGING", listOrderDetailPage);

                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", currentPage);
            } else if ("combo".equals(value)) {
                // Set the number of entities per page
                int entitiesPerPage = Contant.PAGE_SIZE;

                // Calculate the total pages for all lists combined
                int totalEntities = listCombo.size();
                int totalPages = (int) Math.ceil((double) totalEntities / entitiesPerPage);

                // Retrieve the current page number from the request parameters
                int currentPage = PaginationHelper.getCurrentPage(request, "page", totalPages);

                List<OrderDetail> listOrder = new ArrayList<>();
                for (DressPhotoCombo combo : listCombo) {
                    OrderDetail orderDetail = PaginationHelper.mapComboToOrderDetail(combo);

                    listOrder.add(orderDetail);
                }

                List<OrderDetail> listOrderDetailPage = PaginationHelper.getPageEntities(listOrder, currentPage, entitiesPerPage);
                session.setAttribute("LIST_ORDER_PAGING", listOrderDetailPage);

                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", currentPage);
            } else {
                List<OrderDetail> listOrder = PaginationHelper.pagingList(listLocation, listProduct, listStudio, listCombo);

                // Set the number of entities per page
                int entitiesPerPage = Contant.PAGE_SIZE;

                // Calculate the total pages for all lists combined
                int totalEntities = listOrder.size();
                int totalPages = (int) Math.ceil((double) totalEntities / entitiesPerPage);

                // Retrieve the current page number from the request parameters
                int currentPage = PaginationHelper.getCurrentPage(request, "page", totalPages);

                List<OrderDetail> listOrderDetailPage = PaginationHelper.getPageEntities(listOrder, currentPage, entitiesPerPage);
                session.setAttribute("LIST_ORDER_PAGING", listOrderDetailPage);

                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", currentPage);
            }
        } catch (NamingException ex) {
            log("DispatcherServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("DispatcherServlet_SQLException " + ex.getMessage());
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
