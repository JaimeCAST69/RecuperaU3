package controllers.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.user.DaoUser;
import models.user.Incidencia;
import models.user.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name = "users",urlPatterns = {
        "/user/login",
        "/user/auth",
        "/user/main",
        "/user/user",
        "/user/incidencias",
        "/user/save_incidencia",
        "/charge/aceptar",
        "/charge/rechazar",
        "/user/logout"
})
public class ServletUser extends HttpServlet {
    private String action;
    private String redirect = " ";
    private String username, password, role;
    private String titulo, descripcion, estado;
    private Long id;
    User user;
    HttpSession session;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action=req.getServletPath();
        switch (action){
            case "/user/user":
                redirect = "/index.jsp";
                break;
            case "/user/main":
                List<Incidencia> incidencias = new DaoUser().buscarIncidencias();
                req.setAttribute("incidencias",incidencias);
                redirect = "/views/user/index.jsp";
                break;
            case "/user/incidencias":
                redirect = "/views/user/registrar.jsp";
                break;

            case "/user/logout":
                try {
                    HttpSession session = req.getSession(false);  // Obtener la sesión existente (sin crear una nueva)
                    if (session != null) {
                        session.invalidate();  // Invalidar la sesión para cerrarla
                    }
                    redirect = "/user/user?result=true&message=" + URLEncoder
                            .encode("Sesión cerrada correctamente", StandardCharsets.UTF_8);
                } catch (Exception e) {

                }
                break;

            default:
                System.out.println(action);
        }
        req.getRequestDispatcher(redirect).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        action = req.getServletPath();
        switch (action){
            case "/user/save_incidencia":
                titulo = req.getParameter("titulo");
                descripcion = req.getParameter(("descripcion"));
                estado = req.getParameter("estado");

                Incidencia incidencia = new Incidencia(0L,titulo, descripcion, estado);
                boolean result1 = new DaoUser().saveIncidencia(incidencia);
                if (result1){
                    redirect = "/user/main?result= " + result1 + "&message=" + URLEncoder.encode("¡Éxito! Usuario registrado correctamente.",
                            StandardCharsets.UTF_8);

                }else{
                    redirect = "/user/main?result= " + result1 + "&message=" + URLEncoder.encode("¡Error! Acción no realizada correctamente.",
                            StandardCharsets.UTF_8);
                }
                break;
            case "/user/auth":
                username = req.getParameter("username");
                password = req.getParameter("password");
                try {
                    user = new DaoUser()
                            .loadUserByUsernameAndPassword(username, password);
                    if (user != null) {
                        session = req.getSession();
                        session.setAttribute("user", user);
                        switch (user.getRole()) {
                            case "ADMIN_ROLE":
                                redirect = "/admin/main";
                                break;
                            case "CHARGE_ROLE":
                                redirect = "/charge/main";
                                break;
                            case "USER_ROLE":
                                redirect = "/user/main";
                                break;
                        }
                    } else {
                        throw new Exception("Credentials mismatch");
                    }
                } catch (Exception e) {
                    redirect = "/user/user?result=false&message=" + URLEncoder
                            .encode("Usuario y/o contraseña incorrecta",
                                    StandardCharsets.UTF_8);
                }
                break;

            case "/charge/aceptar":
                id = Long.parseLong(req.getParameter("id"));
                System.out.println(id);
                if (new DaoUser().IncidenciaAceptada(id)) {
                    redirect = "/charge/main?result=" + true
                            + "&message=" + URLEncoder.encode("Usuario aceptado correctamente", StandardCharsets.UTF_8);
                } else {
                    redirect = "/charge/main?result=" + false
                            + "&message=" + URLEncoder.encode("No se pudo aceptar el usuario", StandardCharsets.UTF_8);
                }
                break;
            case "/charge/rechazar":
                id = Long.parseLong(req.getParameter("id"));
                System.out.println(id);
                if (new DaoUser().IncidenciaRechazada(id)) {
                    redirect = "/charge/main?result=" + true
                            + "&message=" + URLEncoder.encode("Usuario rechazado correctamente", StandardCharsets.UTF_8);
                } else {
                    redirect = "/charge/main?result=" + false
                            + "&message=" + URLEncoder.encode("No se pudo rechazar el usuario", StandardCharsets.UTF_8);
                }
                break;
            default:
                redirect = "/user/user";
        }
        resp.sendRedirect(req.getContextPath() + redirect);

    }
}
