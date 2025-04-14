<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resource/css/common/managerHeader.css">


<header class="admin-header">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/CourseIt/AdminPage/AdminMain.jsp">Admin Dashboard</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#adminNavbar" aria-controls="adminNavbar" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="adminNavbar">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/AdminPage/User_management.jsp">회원 관리</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">게시물 관리</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">코스 관리</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">설정</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
