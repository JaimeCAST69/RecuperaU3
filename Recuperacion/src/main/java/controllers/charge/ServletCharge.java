package controllers.charge;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Role.Role;
import models.charge.DaoCharge;
import models.user.DaoUser;
import models.user.Incidencia;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "charges", urlPatterns = {
        "/charge/main"
})
public class ServletCharge extends HttpServlet {
    private String action;
    private String redirect = "/charge/main";
    private String id, titulo, descripcion, estado;
    Role role;
    HttpSession session;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action=req.getServletPath();
        switch (action){
            case "/charge/main":
                List<Incidencia> incidencias = new DaoCharge().buscarIncidenciasPendientes();
                req.setAttribute("incidencias",incidencias);
                redirect = "/views/charge/index.jsp";
                break;

            default:
                System.out.println(action);
        }
        req.getRequestDispatcher(redirect).forward(req,resp);
    }
}
