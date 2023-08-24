<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
  <title>Incidencias | Pendientes</title>
  <jsp:include page="../../layouts/head.jsp"/>
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <nav class="navbar bg-body-tertiary">
      <div style="display: flex; align-items: center;" >
        <a class="navbar-brand" href="#" style="display: flex; align-items: center; margin: 10px; margin-left: 40px;">
          <img src="../../assets/img/optimus.png" alt="Logo" width="60" height="60" class="d-inline-block align-text-top" style="margin-right: 18px;">
          Bienvenido Administrador ${user.username}
        </a>
        <a class="navbar-brand" href="/admin/view_users" style="display: flex; align-items: center; margin: 10px; margin-left: 40px;">
          | Usuarios 
        </a>
        <a  href="" style="display: flex; align-items: center; margin: 10px; margin-left: 40px;"></a>

        <a class="navbar-brand text-end" href="/user/logout" style="display: flex; align-items: center; margin: 10px; margin-left: 1100px;">
          | Cerrar Sesión 
        </a>
      </div>


    </nav>

    <div class="col text-center mt-5">
      <h2>Solicitudes</h2>
      <div class="card">
        <div class="card-header">

        </div>
        <div class="card-body">
          <table class="table table-striped">
            <thead>
            <th>#</th>
            <th>Titulo</th>
            <th>Descripción</th>
            <th>Estado</th>
            </thead>
            <tbody>
            <c:forEach var="incidencia" items="${incidencias}" varStatus="s">
              <tr>
                <td>
                  <c:out value="${s.count}"/>
                </td>
                <td>
                  <c:out value="${incidencia.titulo}"/>
                </td>
                <td>
                  <c:out value="${incidencia.descripcion}"/>
                </td>
                <td>
                  <c:out value="${incidencia.estado}"/>
                </td>
                <td style="width: 10%;">
                  <div class="btn-group">
                    <form method="post" action="/admin/aceptar">
                      <input hidden value="${incidencia.id}" name="id"/>
                      <button type="submit" class="btn btn-outline-success btn-sm" style="margin-right: 10px;">
                        APROBAR
                      </button>
                    </form>
                    <form method="post" action="/admin/rechazar">
                      <input hidden value="${incidencia.id}" name="id"/>
                      <button type="submit" class="btn btn-outline-danger btn-sm" style="margin-right: 50px;">
                        RECHAZAR
                      </button>
                    </form>
                  </div>

                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>


<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
