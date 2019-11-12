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
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/blog-post.css" rel="stylesheet">
<title>Show comment selected by title</title>
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
          <a class="dropdown-item" href="/LoginServlet">Login</a>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Comments
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="/ToFileUploadJsp" id="post">to Post</a>
          <a class="dropdown-item" href="/EditMutter?action=editShow&userId=${account.userId}">to Edit</a>
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
<div class="container-fluid">
    	<div class="row">
      		<div class="col-lg-8">
<div class="text-center">
 <h1><c:out value="${imgb.title}"/></h1><br>
 <p class="lead">
 by  <c:out value="${imgb.userId}"/></p>
 <hr>
<p> Posted on<c:out value="${imgb.date_time}"/></p><hr>
 <!-- よう編集⤵︎ -->
<!--  <div class="card mb-3" style="max-width: 540px;"> -->
 <div class="card mb-3" style="max-width: 1100px;">
 <!-- <div class="card" style="width: 24rem;"> -->
  <img src="https://d19elqcdc7fluw.cloudfront.net/upload/${imgb.filename}" class="card-img-top" alt="">
  <div class="card-body">
    <p class="card-text"><c:out value="${imgb.text}"/></p>
<!--   </div> -->
</div></div>
 <%-- <a href="/GoodComment?good=${id}&by=${imgb.userId}" id="good">like  </a><c:out value="  ${count}"/><br>
 <a class="fas fa-heart fa-lg" href="/GoodComment?good=${id}&by=${imgb.userId}" id="good">  </a><c:out value="  ${count}"/><br> --%>

<p><a href="/GoodComment?good=${id}&by=${imgb.userId}" id="good">
 <i class="fas fa-heart fa-lg" ></i>
 </a>&nbsp;<c:out value="${count}"/></p><br>
<hr></div>
 </div></div></div>

 id:<c:out value="${id}"/>&nbsp;count:<c:out value="${count}"/>
 &nbsp;userid:<c:out value="${userId}"/>
 <a class="btn btn-link btn-lg noline" href="/ShowImages?action=show">&nbsp;All Comments</a>

<footer class="py-5 bg-dark">
    <div class="container">
      <p class="m-0 text-center text-white">Copyright &copy; Your Website 2019</p>
    </div>
</footer>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/a9e3d6559e.js" crossorigin="anonymous"></script>
</body>
</html>