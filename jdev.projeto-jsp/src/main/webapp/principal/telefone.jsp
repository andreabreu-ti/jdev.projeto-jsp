<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- Declaração do JSTL -->
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
    
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

  <body>
  	<!-- Pre-loader start -->
  	<jsp:include page="theme-loader.jsp"></jsp:include>
  
  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          
          <jsp:include page="navbar.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
              
                  
                  <jsp:include page="navbarmainmenu.jsp"></jsp:include>
                  
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      
                      <jsp:include page="page-header.jsp"></jsp:include>
                      
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                   
	                                   <div class="row">
                                            <div class="col-sm-12">
                                                <!-- Basic Form Inputs card start -->
                                                <div class="card">
                                                    <div class="card-header">
                                                        <h5>Cadastro de Telefones dos Usuários</h5>
                                                    </div>
                                                    <div class="card-block">
                                                    
                                                    	<form class="form-material" action="<%= request.getContextPath()%>/ServletTelefone" method="post" id="formFone">
															
															<div class="form-group form-default form-static-label">
															    
															    <input type="text" name="id" id="id" class="form-control" placeholder="" readonly="readonly" value="${modelLogin.id}">
															    <span class="form-bar"></span>
															    <label class="float-label">ID User:</label>
															    
															</div>
															
															<div class="form-group form-default form-static-label">
                                                                <input readonly="readonly" type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="numero" id="numero" class="form-control" required="required">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Número Telefone:</label>
                                                            </div>
                                                            
                                                            <button class="btn waves-effect waves-light btn-success"><i class="icofont icofont-check-circled"></i>Salvar</button>
															
                                                    	</form>
                                                    
                                                    </div>
                                                </div>
                                            </div>
	                                     </div>
                                    
                                    	<span id="msg">${msg}</span>
                                    	
                                    	<div style="height: 400px; overflow: scroll;">
											<table class="table" id="tabelaResultadosview">
											  <thead>
											    <tr>
											      <th scope="col">ID</th>
											      <th scope="col">Número</th>
											      <th scope="col">Excluir</th>
											    </tr>
											  </thead>
											  <tbody>
											  
											  	<!-- Utilização do JSTL -->
											  	<c:forEach items="${modelTelefones}" var="f">
											  		<tr>
											  			<td><c:out value="${f.id}"></c:out></td>
											  			<td><c:out value="${f.numero}"></c:out></td>
											  			<td><a class="btn btn-success" href="<%= request.getContextPath() %>/ServletTelefone?acao=excluir&id=${f.id}&userpai=${modelLogin.id}">Excluir</a></td>
											  		</tr>	
											  	</c:forEach> 
											  	 
											  </tbody>
											</table>
										</div>	
                                    
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Required Jquery -->
    <jsp:include page="javascriptfile.jsp"></jsp:include>
    
    <script type="text/javascript">
    
		$("#numero").keypress(function (event) {
    		return /\d/.test(String.fromCharCode(event.keyCode));
		});
    
    </script>
    
</body>

</html>
