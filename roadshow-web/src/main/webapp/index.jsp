<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cl.roadshow.controller.RequestContextListenerTest" %>
hello

<%
RequestContextListenerTest test = new RequestContextListenerTest();
out.println(test.getName());
%>