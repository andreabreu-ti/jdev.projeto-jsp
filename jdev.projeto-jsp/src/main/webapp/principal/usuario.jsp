<%@page import="model.ModelLogin"%>
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
                                                        <h5>Cadastro de Usuários</h5>
                                                    </div>
                                                    <div class="card-block">
                                        
														<form class="form-material" action="<%= request.getContextPath()%>/ServletUsuarioController" method="post" id="formUser">
														
															<input type="hidden" name="acao" id="acao" value="">									
														
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" placeholder="" readonly="readonly" value="${modelLogin.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Id:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="email" name="email" id="email" class="form-control" placeholder="" required="required" autocomplete="off" value="${modelLogin.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Email:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
																<select class="form-control" aria-label="Default select example" name="perfil" >
																  <option disabled="disabled" selected="selected">[Selecione o perfil]</option>
																  
																  <option value="ADMIN" <% 
																  
																  ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																  
																  
																  
																  if (modelLogin != null && modelLogin.getPerfil().equals("ADMIN")){
																		out.print(" ");
																			out.print("selected=\"selected\"");
																		out.print(" ");	  
																  }%> >Admin</option>
																  
																  <option value="SECRETARIA" <% 
																  
																  modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																  
																  if (modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")){
																		out.print(" ");
																			out.print("selected=\"selected\"");
																		out.print(" ");	  
																  }%>>Secretária</option>
																  
																  <option value="AUXILIAR"<%
																  
																  modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																  
																  if (modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR")){
																		out.print(" ");
																			out.print("selected=\"selected\"");
																		out.print(" ");	  
																  }%>>Auxiliar</option>
																  
																</select> 
																<span class="form-bar"></span>
                                                                <label class="float-label">Perfil:</label>                                                           
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="login" id="login" class="form-control" placeholder="" required="required" autocomplete="off" value="${modelLogin.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="password" name="senha" id="senha" class="form-control" placeholder="" required="required" autocomplete="off" value="${modelLogin.senha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Password</label>
                                                            </div>
                                                            
                                                            <button type="button" class="btn waves-effect waves-light btn-primary" onclick="limparForm();"><i class="icofont icofont-user-alt-3"></i>Novo</button>
                                                            <button class="btn waves-effect waves-light btn-success"><i class="icofont icofont-check-circled"></i>Salva</button>
                											<button type="button" class="btn waves-effect waves-light btn-danger" onclick="criaDeleteComAjax();"><i class="icofont icofont-eye-alt"></i>Excluir</button>
                                                            
                                                            <!-- Button trigger modal -->
															<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModalUsuario">
															  Pesquisar
															</button>
                                                            
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
											      <th scope="col">Nome</th>
											      <th scope="col">Ver</th>
											    </tr>
											  </thead>
											  <tbody>
											  
											  	<!-- Utilização do JSTL -->
											  	<c:forEach items="${modelLogins}" var="ml">
											  		<tr>
											  			<td><c:out value="${ml.id}"></c:out></td>
											  			<td><c:out value="${ml.nome}"></c:out></td>
											  			<td><a class="btn btn-success" href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
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

	<!-- Modal -->
	<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de Usuários</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        
			<div class="input-group mb-3">
			  <input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
			  <div class="input-group-append">
			    <button class="btn btn-success" type="button" onclick="buscarUsuario();">Buscar</button>
			  </div>
			</div>
			
			<div style="height: 400px; overflow: scroll;">
				<table class="table" id="tabelaResultados">
				  <thead>
				    <tr>
				      <th scope="col">ID</th>
				      <th scope="col">Nome</th>
				      <th scope="col">Ver</th>
				    </tr>
				  </thead>
				  <tbody>
				    
				  </tbody>
				</table>
			</div>
	        <span id="totalResultados"></span>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
	      </div>
	    </div>
	  </div>
	</div>    
    
    
    <script type="text/javascript">
    
    	function verEditar(id) {

    		//alert(id);
    		
    		var urlAction = document.getElementById('formUser').action

    		//Redirecionamento com javascript
    		window.location.href = urlAction + '?acao=buscarEditar&id='+id;    //Executa um get
    		
		}
    
  		//Buscar um lista de usuários mostrando em uma modal
  		function buscarUsuario() {
			
  			var nomeBusca = document.getElementById('nomeBusca').value;
  			
  			//Validando que tem que ter valor para buscar no banco
  			if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') {
				
  				var urlAction = document.getElementById('formUser').action;
  				
				$.ajax({
  					
  					method: "get",
  					url: urlAction,
  					data: "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
  					success: function(response){
  						
  						var json = JSON.parse(response);
  						//console.info(json);  ctrl + shift + j
  						
  						$('#tabelaResultados > tbody > tr').remove();
  						
  						for (var p = 0; p < json.length; p++) {
							$('#tabelaResultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td>'+json[p].nome+'</td> <td><button onclick="verEditar('+json[p].id+')" type="button" class="btn btn-info">Ver</button></td> </tr>');
						}
  						document.getElementById('totalResultados').textContent = 'Resultados: '+json.length;
  					}
  					
  				}).fail(function(xhr, status, errorThrown){
  					
  					alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
  				});
			}
  			
		}
    
    	//Deletar dados com o Ajax com confirmação de exlusão
  		function criaDeleteComAjax() {
  			
  			if (confirm('Deseja realmente excluir os dados?')) {
  				
  				//Capiturar o action do formulário
  				var urlAction = document.getElementById("formUser").action;
  				var idUser = document.getElementById("id").value;
  				
  				$.ajax({
  					
  					method: "get",
  					url: urlAction,
  					data: "id=" + idUser + '&acao=deletarajax',
  					success: function(response){
  						limparForm()
  						document.getElementById('msg').textContent = response;
  					}
  					
  				}).fail(function(xhr, status, errorThrown){
  					
  					alert('Erro ao deletar usuário por id: ' + xhr.responseText);
  				});
  			}
		}
  		
    
    	//Funcao vinculada ao Botão Excluir com confirmação de exlusão
    	function criarDelete() {
    		
    		if (confirm('Deseja realmente excluir os dados?')) {
    			document.getElementById("formUser").method='get';
        		document.getElementById("acao").value = 'deletar';
        		document.getElementById("formUser").submit();	
			}
		}
    
  		//Função vinculada ao botão Novo para limpara a tela e cadastrar um novo usuário
    	function limparForm() {
  			
			var elementos = document.getElementById("formUser").elements;  //Retorna os elementos html dentro do formulário
			
			for (p = 0; p < elementos.length; p ++) {
				elementos[p].value =  '';
			}
		}
    	
    </script>
    
</body>

</html>
