<%--
  Created by IntelliJ IDEA.
  User: oskit
  Date: 23/08/2023
  Time: 04:38 a. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Registro | Incidencia</title>
  <jsp:include page="../../layouts/head.jsp"/>
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <div class="col">
      <div class="card mt-5">
        <div class="card-header">Registro de incidencia</div>
        <div class="card-body">
          <form id="user-form" class="needs-validation" novalidate action="/user/save_incidencia" method="post">
            <div class="form-group mb-3">
              <div class="row">
                <div class="col">
                  <label for="titulo" class="fw-bold">Titulo:</label>
                  <input type="text" name="titulo" id="titulo" class="form-control" required>
                  <div class="invalid-feedback">Campo obligatorio</div>
                </div>
                <div class="col">
                  <label for="descripcion" class="fw-bold">Descripcion:</label>
                  <input type="text" name="descripcion" id="descripcion" class="form-control" required>
                  <div class="invalid-feedback">Campo obligatorio</div>
                </div>
                <div class="col">
                  <label for="estado" class="fw-bold">Estado:</label>
                  <select disabled class="form-select" aria-label="Default select example" name="estado" id="estado" required>
                    <option selected>PENDIENTE</option>
                  </select>
                  <div class="invalid-feedback">Campo obligatorio</div>
                </div>
              </div>
            </div>
            <div class="form-group mb-3">
              <div class="row">
                <div class="col text-end">
                  <a href="/user/main" class="btn btn-outline-danger btn-sm">
                    CANCELAR
                  </a>
                  <button type="submit" class="btn btn-outline-success btn-sm">
                    ACEPTAR
                  </button>
                </div>
              </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="../../layouts/footer.jsp"/>
<script>
  (function (){
    const form = document.getElementById("user-form"); //document hace referencia a la pantalla
    form.addEventListener("submit",function (event){
      if(!form.checkValidity()){ //check revisa que no este vacio el formulario
        event.preventDefault();
        event.stopPropagation();
      }
      form.classList.add("was-validated")
    }, false); //false que no necesita que haga mas cosas que solo la función
  })(); //Se ejecuta mientras este en pantalla
</script>
</body>
</html>
