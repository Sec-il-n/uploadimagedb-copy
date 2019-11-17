<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Register form</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body id="page-top">
<!-- nav -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
  <div class="container">
  <a class="navbar-brand js-scroll-trigger" href="##page-top">Your Picture Blog*s</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="/" id="home">Home</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Login
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="/LoginServlet">Easy Login<span class="sr-only">(current)</span></a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/RegisterServlet" id="register">Register</a>
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
    </ul>
  </div>
 </div>
</nav>

<div class="container pt-4">
<div class="jumbotron">
<c:if test="${not empty msg}">
<div class="alert alert-danger" role="alert">
<c:out value="${msg}"/></div></c:if><br>
<%-- useId:<c:out value="${checkUserId}"/><br>
pass:<c:out value="${checkPass}"/><br>
name:<c:out value="${checkName}"/><br>
age:<c:out value="${checkAge}"/><br>
age:<c:out value="${checkZero}"/><br> --%>
<form action="/RegisterEdit" method="post" name="input_null_form">
	<div class="form-group">
		<label for="userIdla">User ID</label>
		<c:if test="${not empty checkUserId}">
		<div class="alert alert-danger" role="alert">
		<c:out value="${checkUserId}"/></div></c:if><br>
		<input type="text" class="form-control" name="userId" value="${newAccount.userId}"><br>
	</div>
	<div class="form-group">
	    <label for="passwordla">Password</label>
	    <c:if test="${not empty checkPass}">
		<div class="alert alert-danger" role="alert">
		<c:out value="${checkPass}"/></div></c:if><br>
		<input type="password" class="form-control" name="pass" id="pass" value="${newAccount.pass}"><br>
	</div>
	<div class="form-group">
	    <label for="namela">Name</label>
	    <c:if test="${not empty checkName}">
		<div class="alert alert-danger" role="alert">
		<c:out value="${checkName}"/></div></c:if><br>
		<input type="text" class="form-control" name="name" id="name" value="${newAccount.name}"><br>
	</div>
	<div class="form-group">
	    <label for="agela">Age</label>
	    <c:if test="${not empty checkAge}">
		<div class="alert alert-danger" role="alert">
		<c:out value="${checkAge}"/></div></c:if><br>
	    <c:if test="${not empty checkZero}">
		<div class="alert alert-danger" role="alert">
		<c:out value="${checkZero}"/></div></c:if><br>
<%-- 	    <c:if test="${not empty checkAge}${not empty checkZero}">
		<div class="alert alert-danger" role="alert">
		<c:out value="${checkAge}"/><c:out value="${checkZero}"/></div></c:if><br> --%>
		<input type="text" class="form-control" name="age" id="age" value="${newAccount.age}"><br>
	</div>
	<button type="submit" class="btn btn-secondary btn-sm">Conform</button><br>
</form>
</div></div>
<!-- footer -->
<footer class="py-5 bg-dark">
   <div class="container">
      <p class="m-0 text-center text-white">Copyright &copy; Your Website 2019</p>
    </div>
</footer>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>