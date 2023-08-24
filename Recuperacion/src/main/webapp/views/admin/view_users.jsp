<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
  <title>Usuarios</title>
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
        <a class="navbar-brand" href="/admin/main" style="display: flex; align-items: center; margin: 10px; margin-left: 40px;">
          | Regresar
        </a>
        <a class="navbar-brand" href="/user/logout" style="display: flex; align-items: center; margin: 10px; margin-left: 40px;">
          | Cerrar Sesión
        </a>
      </div>
      <input hidden value="${user.id}" name="id"/>

    </nav>

    <div class="col text-center mt-5">
      <h2>Usuarios</h2>
      <div class="card">
        <div class="card-header">

          <div class="col text-end">
            <a class="btn btn-outline-primary btn-sm" href="/admin/users" role="button">Registrar Usuario</a>
          </div>

        </div>
        <div class="card-body">
          <table class="table table-striped">
            <thead>
            <th>#</th>
            <th>Usuario</th>
            <th>Contraseña</th>
            <th>Role</th>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}" varStatus="s">
              <tr>
                <td>
                  <c:out value="${s.count}"/>
                </td>
                <td>
                  <c:out value="${user.username}"/>
                </td>
                <td>
                  <c:out value="${user.password}"/>
                </td>
                <td>
                  <c:out value="${user.role}"/>
                </td>
                <td style="width: 10%;">
                  <div class="btn-group">
                    <form method="post" action="/admin/delete">
                      <input hidden value="${user.id}" name="id">
                      <button type="submit" class="btn btn-outline-danger btn-sm" style="margin-right: 50px; margin-left: -120px;">
                        ELIMINAR
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
