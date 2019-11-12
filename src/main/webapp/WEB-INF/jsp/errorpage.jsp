<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error Page</title>
</head>
<body>
<c:out value="問題が発生しました。"/>
<a href="<c:url value="/WEB-INF/jsp/errorpage.jsp"/>">戻る</a>
</body>
</html>