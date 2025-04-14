<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, beans.user.*, java.sql.*" %>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
	<%@ include file="/resource/common/AdminHeader.jsp" %>
    <div class="container">
        <h2 class="mt-4">User Management</h2>

        <!-- Add User Form -->
        <form method="post" action="<%= request.getContextPath() %>/userServlet.do">
            <input type="hidden" name="action" value="add">
            <div class="row">
                <div class="col-md-3">
                    <input type="text" name="userId" class="form-control" placeholder="User ID" required>
                </div>
                <div class="col-md-3">
                    <input type="password" name="userPw" class="form-control" placeholder="User PW" required>
                </div>
                <div class="col-md-3">
                    <input type="text" name="userName" class="form-control" placeholder="Name" required>
                </div>
                <div class="col-md-3">
                    <input type="email" name="userEmail" class="form-control" placeholder="Email" required>
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-primary">Add User</button>
                </div>
            </div>
        </form>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    beans.user.UserDAO userDao = new beans.user.UserDAO();
                    List<beans.user.UserDTO> userList = userDao.getAllUsers();
                    for (beans.user.UserDTO user : userList) {
                %>
                <tr>
                    <td><%= user.getUserId() %></td>
                    <td><%= user.getUserName() %></td>
                    <td><%= user.getUserEmail() %></td>
                    <td>
						<form action="<%= request.getContextPath() %>/userServlet.do" method="post" style="display:inline;">
						    <input type="hidden" name="action" value="edit">
						    <input type="hidden" name="userId" value="<%= user.getUserId() %>">
						    <button type="submit" class="btn btn-sm btn-warning">Edit</button>
						</form>
						
						

						<form action="<%= request.getContextPath() %>/userServlet.do" method="post" style="display:inline;">
						    <input type="hidden" name="action" value="delete">
						    <input type="hidden" name="userId" value="<%= user.getUserId() %>">
						    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
						</form>


                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
