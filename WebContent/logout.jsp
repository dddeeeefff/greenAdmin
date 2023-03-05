<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
session.invalidate();

response.sendRedirect("index");
%>
</body>
</html>