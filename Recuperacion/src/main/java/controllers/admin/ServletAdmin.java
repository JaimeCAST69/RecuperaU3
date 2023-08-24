package controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.admin.DaoAdmin;
import models.user.DaoUser;
import models.user.Incidencia;
import models.user.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name = "admins", urlPatterns = {
        "/admin/main",
        "/admin/users",
        "/admin/save",
        "/admin/view_users",
        "/admin/delete",
        "/admin/aceptar",
        "/admin/rechazar"
})
public class ServletAdmin extends HttpServlet {
    private String action;
    private String redirect = "/admin/main";
    private String id2,usuario, password, role;
    private Long id;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        switch (action){
            case "/admin/main":
                List<Incidencia> incidencias = new DaoAdmin().buscarIncidenciasAceptadas();
                req.setAttribute("incidencias",incidencias);
                redirect = "/views/admin/index.jsp";
                break;
            case "/admin/users":
                redirect = "/views/admin/users.jsp";
                break;
            case "/admin/view_users":
                List<User> users = new DaoUser().findAll();
                req.setAttribute("users",users);
                redirect = "/views/admin/view_users.jsp";
                break;
        }
        req.getRequestDispatcher(redirect).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        action = req.getServletPath();
        switch (action){
            case "/admin/save":
                usuario = req.getParameter("usuario");
                password = req.getParameter("password");
                role = req.getParameter("role");

                User user1 = new User(0L,usuario, password, role);
                boolean result = new DaoUser().save(user1);
                if (result){
                    redirect = "/admin/view_users?result= " + result + "&message=" + URLEncoder.encode("¡Éxito! Usuario registrado correctamente.",
                            StandardCharsets.UTF_8);

                }else{
                    redirect = "/admin/view_users?result= " + result + "&message=" + URLEncoder.encode("¡Error! Acción no realizada correctamente.",
                            StandardCharsets.UTF_8);
                }
                break;
            case "/admin/delete":
                id2 = req.getParameter("id");
                if (new DaoUser().delete(Long.parseLong(id2))){
                    redirect = "/admin/view_users?result= " + true + "&message=" + URLEncoder.encode("¡Éxito! Usuario eliminado correctamente.",
                            StandardCharsets.UTF_8);
                }else{
                    redirect = "/admin/view_users?result= " + false + "&message=" + URLEncoder.encode("¡Error! Acción no eliminado correctamente.",
                            StandardCharsets.UTF_8);
                }
                break;
            case "/admin/aceptar":
                id = Long.parseLong(req.getParameter("id"));
                if (new DaoAdmin().IncidenciaAprobada(id)) {
                    redirect = "/admin/main?result=" + true
                            + "&message=" + URLEncoder.encode("Usuario aprobado correctamente", StandardCharsets.UTF_8);
                } else {
                    redirect = "/admin/main?result=" + false
                            + "&message=" + URLEncoder.encode("No se pudo aceptar el usuario", StandardCharsets.UTF_8);
                }
                break;
            case "/admin/rechazar":
                id = Long.parseLong(req.getParameter("id"));
                System.out.println(id);
                if (new DaoAdmin().IncidenciaRechazada(id)) {
                    redirect = "/admin/main?result=" + true
                            + "&message=" + URLEncoder.encode("Usuario rechazado correctamente", StandardCharsets.UTF_8);
                } else {
                    redirect = "/admin/main?result=" + false
                            + "&message=" + URLEncoder.encode("No se pudo rechazar el usuario", StandardCharsets.UTF_8);
                }
                break;
            default:
                redirect = "/admin/main";
        }
        resp.sendRedirect(req.getContextPath() + redirect);

    }

}
