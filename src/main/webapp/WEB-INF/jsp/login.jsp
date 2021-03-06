<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Login Form</title>
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
          <a class="dropdown-item" href="#">Easy Login<span class="sr-only">(current)</span></a>
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
<!-- alert -->
<c:if test="${not empty msg}">
	<c:if test="${msg == '登録完了しました。ログインして下さい。'}">
	<div class="alert alert-info" role="alert" id="msg">
	<c:out value="${msg}"/></div></c:if>
<c:if test="${msg != '登録完了しました。ログインして下さい。'}">
<div class="alert alert-danger" role="alert" id="msg">
<c:out value="${msg}"/></div></c:if>
</c:if><br>
<div class="jumbotron">
<form action="/LoginServlet" method="post" name="loginform">
 	<div class="form-group">
	 	<label for="userIdla">User ID</label>
	 	<input type="text" class="form-control" name="userId" aria-describedby="userIdhelp" placeholder="Tester12">
	</div>
	<div class="form-group">
    <label for="passwordla">Password</label>
    <input type="password" name="pass" class="form-control" placeholder="121212">
  </div>
	 <button type="submit" class="btn btn-secondary btn-sm">login</button><br>
</form>
</div></div>
<!-- footer -->
<footer class="py-5 bg-dark fixed-bottom">
   <div class="container">
      <p class="m-0 text-center text-white">Copyright &copy; Your Website 2019</p>
    </div>
</footer>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>