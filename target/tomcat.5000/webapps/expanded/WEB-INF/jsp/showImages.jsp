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
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<title>Upload Comments</title>
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="css/blog-post.css" rel="stylesheet">
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
          <a class="dropdown-item" href="/LoginServlet">EasyLogin</a>
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
	<div class="container-fluid">
    	<div class="row">
      		<div class="col-xl-8">
				<!-- <div class="row"> -->
<c:if test="${not empty account.userId}">
<c:if test="${not empty userId}"><c:out value="Welcome! "/><c:out value="${account.userId}"/><c:out value=" 's now loged in"/></c:if><br>
</c:if><br>
<div class="text-center">
<h2 class="my-4"><c:out value="Photos & Comments"/>
          <!-- <small>Secondary Text</small> -->
</h2>
<c:forEach var="paged" items="${pagedList}">
<!-- wouldn't do ⇩ unless you deploy your application as ROOT -->
<!-- x not working (will be at context =>full URL /context/upload/test.png) -->
	<!-- <div class="card mb-3"> -->
	<div class="card mb-3" style="max-width: 1100px;">

 		<div class="row no-gutters">
		<div class="row">

			<div class="col-md-8 col-lg-8 my-auto mx-auto" style="max-width: 420px;">
		    	<a href="/ShowEachComment?id=${paged.id}">
		    	<img class="card-img" style="max-width: 1100px;" src="https://d19elqcdc7fluw.cloudfront.net/upload/${paged.filename}" alt="Card image cap">
		    	</a>
		    </div>
    	</div>
    	<div class="col-md-8 col-lg-8 my-auto mx-auto">
    	<!-- <div class="col-md-8 col-lg-8 my-auto mx-auto"> -->
<!--         	<div class="card-body" style="max-width: 650px;" > -->
        	<div class="card-body" style="max-width: 100%;" >
	            <h2 class="card-title">
	            <a href="/ShowEachComment?id=${paged.id}"><c:out value="${paged.title}"/></a>
	            </h2>
        	</div>
        	<div class="card-footer text-muted">
	            <p>Posted on <c:out value="${paged.date_time}"/></p>
	            <p>by <c:out value="${paged.userId}"/></p>
	          <!--   <a href="#">Start Bootstrap</a> -->
        	</div>
     	</div>
        </div>
  </div>

</c:forEach>

<c:out value="${in}"/>&nbsp;/&nbsp;<c:out value="${total}"/> &nbsp;total<br>
<a href="#"><i class="fas fa-arrow-alt-circle-left fa-lg"></i>before &nbsp;</a>
<!-- <a href="#">before &nbsp;</a> -->
<c:forEach var="t" begin="1" end="${totalPage}" step="1">
<a class="noline" href="/PageNation?action=middle&page=${t}"><c:out value="${t}"/>&nbsp;</a>
</c:forEach>
<!-- <a href="/PageNation?action=after&now=1" id="after"> &nbsp; next</a> -->
<a class="noline" href="/PageNation?action=after&now=1" id="after">&nbsp; next <i class="fas fa-arrow-alt-circle-right fa-lg"></i></a><br>

<a class="noline" href="/ToLoginResult">TopPage</a>
<a class="noline" href="/ToFileUploadJsp">&nbsp; &nbsp;Post</a>
<a class="noline" href="/ShowImages?action=edit">&nbsp; &nbsp;Edit</a>

</div><!--text-sentre  -->
</div>
</div></div>
<!-- </div> -->
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