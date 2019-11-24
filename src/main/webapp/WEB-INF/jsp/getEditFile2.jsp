<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>get file to delete </title>
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
	    <li class="nav-item">
	      	<a class="nav-link" href="/LogoutServlet" id="logout">Logout</a><br>
	    </li>

    </ul>
  </div>
 </div>

</nav>
<c:out value="click title which you whant to edit."></c:out><br>

<c:forEach var="y" items="${pagedPostedList}">
<c:out value="${y.date_time}"/><br>
<a href="
	<c:url value="/EditImage">
	<c:param name="id" value="${y.id}"/>
	<c:param name="title" value="${y.title}"/>
	<c:param name="text" value="${y.text}"/>
	<c:param name="deleteFileName" value="${y.filename}"/>
	</c:url>
">${y.title}</a><br>
</c:forEach>
<c:out value="${in}"/>/<c:out value="${total}"/>   total<br>

<a class="noline a:hover img {
  border: solid 2px silver;
}" href="/PageNationEdit?action=before&now=${page}" id="before">before  </a>
<a class="noline" href="/PageNationEdit?action=after&now=${page}" id="after">   next</a><br>
<c:forEach var="t" begin="1" end="${totalPage}" step="1">
<a class="noline" href="/PageNationEdit?action=middle&page=${t}"><c:out value="${t}"/></a>
</c:forEach><br>
<a class="noline" href="/MoveToLoginResult">TopPage</a>

<footer class="py-5 bg-dark fixed-bottom">
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
