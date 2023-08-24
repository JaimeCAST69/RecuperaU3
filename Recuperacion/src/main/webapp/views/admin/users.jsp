<%--
  Created by IntelliJ IDEA.
  User: oskit
  Date: 23/08/2023
  Time: 01:08 a. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registro | Usuario</title>
    <jsp:include page="../../layouts/head.jsp"/>

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col">
            <div class="card mt-5">
                <div class="card-header">Registro de usuario</div>
                <div class="card-body">
                    <form id="user-form" class="needs-validation" novalidate action="/admin/save" method="post">
                        <div class="form-group mb-3">
                            <div class="row">
                                <div class="col">
                                    <label for="usuario" class="fw-bold">Usuario:</label>
                                    <input type="text" name="usuario" id="usuario" class="form-control" required>
                                    <div class="invalid-feedback">Campo obligatorio</div>
                                </div>
                                <div class="col">
                                    <label for="password" class="fw-bold">Password:</label>
                                    <input type="text" name="password" id="password" class="form-control" required>
                                    <div class="invalid-feedback">Campo obligatorio</div>
                                </div>
                                <div class="col">
                                    <label for="role" class="fw-bold">Role:</label>
                                    <select class="form-select" aria-label="Default select example" name="role" id="role" required>
                                        <option selected disabled value="">Seleccionar Role</option>
                                        <option value="ADMIN_ROLE">ADMIN_ROLE</option>
                                        <option value="CHARGE_ROLE">CHARGE_ROLE</option>
                                        <option value="USER_ROLE">USER_ROLE</option>
                                    </select>
                                    <div class="invalid-feedback">Campo obligatorio</div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group mb-3">
                            <div class="row">
                                <div class="col text-end">
                                    <a href="/admin/view_users" class="btn btn-outline-danger btn-sm">
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
