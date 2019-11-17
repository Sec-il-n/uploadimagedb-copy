<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


<title>menu</title>
</head>
<body id="page-top">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
  <div class="container">
  <a class="navbar-brand js-scroll-trigger" href="##page-top">Your Picture Blog*s</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
   <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="/">Home</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Login
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="/LoginServlet">EasyLogin<span class="sr-only">(current)</span></a>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Comments
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="/ToFileUploadJsp" id="post">to Post</a>
          <a class="dropdown-item" href="/ShowImages?action=edit" id="edit">to Edit</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="/ShowImages?action=show" id="see">See Comments</a>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Configure Register
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="/RegisterEdit" id="regisetration">Registration</a><!-- ↓drop down -->
          <a class="dropdown-item" href="/RegisterEdit?action=delete"id="Register delete">Register delete</a>
        </div>
      </li>

      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          About
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="/Navigate?action=start">How to Start</a>
          <a class="dropdown-item" href="/Navigate?action=comment">How to Coments</a>
        </div>
      </li>
      <li class="nav-item">
      	<a class="nav-link" href="/LogoutServlet" id="logout">Logout</a><br>
      </li>
    </ul>
  </div>
 </div>
</nav>
<div class="container pt-4 mb-4">

	<div class="text-center">
		<h5><c:if test="${not empty userId}"><c:out value="Welcome! "/><c:out value="${account.userId}"/><c:out value=" 's now loged in"/></c:if></h5><br>
	</div>
	<div class="jumbotron">

<c:if test="${not empty msg}">
<div class="alert alert-danger" role="alert">
<c:out value="${msg}"/></div></c:if><br>
<c:if test="${not empty errormsg}">
<div class="alert alert-danger" role="alert">
<c:out value="${errormsg}"/><br>
<c:out value="${path}"/></div></c:if><br>

<h3 class="display-4"></h3>
  <p class="lead">See comment button to list</p><br>
  <a class="btn btn-outline-info ml-8" href="/ShowImages?action=show" role="button">See Comments</a>
  <hr class="my-4">
  <div class="row">
	  <div class="col pr-10">
		  <p>Let's&nbsp;&nbsp;</p>
		  <a class="btn btn-secondary" href="/ShowImages?action=show" role="button">Post</a>
		  <p>&nbsp;&nbsp;post your photos.</p>
	  </div>
	  <div class="col pl-8">
		  <p>Click&nbsp;&nbsp;</p>
		  <a class="btn btn-secondary" href="/ShowImages?action=edit" role="button">Edit</a>
		  <p>If you want to edit your comments.</p>
  	  </div>
  </div>
  </div></div>
<footer class="py-5 bg-dark">
   <div class="container">
      <p class="m-0 text-center text-white">Copyright &copy; Your Website 2019</p>
    </div>
</footer>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	<script src="vendor/jquery/jquery.min.js"></script>
</body>
</html>