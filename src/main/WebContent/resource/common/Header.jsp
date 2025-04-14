<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="resource/css/common/header.css">

<header class="fixed-header">
    <div id="top">
        <!-- 로고 영역 -->
        <section id="logo">
            <a href="Main.jsp"><img src="resource/img/headerImg/logoMain.png" alt="코스잇 메인 로고"></a>
        </section>
        
        <!-- 로그인 상태에 따라 메뉴 표시 -->
        <div id="user-menu">
            <%
            String userId = (String) session.getAttribute("userId");
            String userName = (String) session.getAttribute("userName");
            System.out.println("DEBUG: header.jsp userId from session = " + userId); // 디버깅 출력
            System.out.println("DEBUG: header.jsp userName from session = " + userName); // 디버깅 출력
        	
				if (userId == null) {
                    // 로그아웃 상태
            %>
            <a href="<%= request.getContextPath() %>/Login.jsp">로그인</a> |
            <a href="<%= request.getContextPath() %>/Sign_up.jsp">회원가입</a>
            <%
                } else {
                    // 로그인 상태
            %>
            <a href="<%= request.getContextPath() %>/logout.do">로그아웃</a> |
            <a href="<%= request.getContextPath() %>/Mypagemain.jsp">마이페이지</a>
            <%
                }
            %>
        </div>
        
        <!-- 네비게이션 메뉴 -->
        <nav id="head">
            <ul>
                <%
                    if (userId != null && userName != null) {
                %>
                <li>
                    <a href="<%= request.getContextPath() %>/Mypagemain.jsp">
                        <img alt="" src="resource/img/headerImg/profile.png" style='border-radius: 100%'>
                        <%= userName %> 님
                    </a>
                </li>
                <%
                    } else {
                %>
                <li>
                    <a href="<%= request.getContextPath() %>/Mypagemain.jsp">
                        <img alt="" src="resource/img/headerImg/profile.png" style='border-radius: 100%'>
                        프로필
                    </a>
                </li>
                <%
                    }
                %>
                <li><a href="MyCourse.jsp"><img alt="" src="resource/img/headerImg/mycourse.png">마이코스</a></li>
                <li><a href="Daylog.jsp"><img alt="" src="resource/img/headerImg/daylog.png">여행기록들</a></li>
                <li><a href="RealExhibition.jsp"><img alt="" src="resource/img/headerImg/perform.png">전시/공연</a></li>
                <li><a href="MiddlePlace.jsp"><img alt="" src="resource/img/headerImg/middle.png">중간 지점 찾기</a></li>
            </ul>
        </nav>
    </div>
</header>
