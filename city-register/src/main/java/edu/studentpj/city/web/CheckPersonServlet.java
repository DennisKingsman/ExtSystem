package edu.studentpj.city.web;

import edu.studentpj.city.dao.PersonCheckDao;
import edu.studentpj.city.dao.PoolConnectionBuilder;
import edu.studentpj.city.domain.PersonRequest;
import edu.studentpj.city.domain.PersonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "CheckPersonServlet", urlPatterns = {"/checkPerson"})
public class CheckPersonServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CheckPersonServlet.class);

    private PersonCheckDao dao;

    @Override
    public void init() throws ServletException {
        logger.info("Servlet is created ");

        dao = new PersonCheckDao();
        dao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Get checkPerson - Called");

        req.setCharacterEncoding("UTF-8");

        String surname = req.getParameter("surname");

        System.out.println(surname);

        PersonRequest request = new PersonRequest();
        request.setSurName(surname);
        request.setGivenName("Павел");
        request.setPatronymic("Николаевич");
        request.setDateOfBirth(LocalDate.of(1995, 3, 18));
        request.setStreetCode(1);
        request.setBuilding("10");
        request.setExtension("2");
        request.setApartment("121");

        try {
            PersonResponse ps = dao.checkPerson(request);
            if(ps.isRegistered()){
                resp.getWriter().write("Registered");
            }else resp.getWriter().write("Not registered ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
