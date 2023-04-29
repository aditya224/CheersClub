<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<anyxmlelement xmlns:c="jakarta.tags.core" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>User List</title>
	<style>
        /* this is header content */
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            background-color: #3b5998;
            color: #fff;
        }

        .logo img {
            height: 40px;
        }

        .search-box form {
        display: flex;
        align-items: center;
        }

        .search-box input[type="text"] {
            width: 200px;
            height: 30px;
            margin-right: 10px;
            padding: 5px;
            border: none;
            border-radius: 5px;
        }

        .search-box button {
            height: 30px;
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            background-color: #fff;
            color: #3b5998;
            cursor: pointer;
        }

        nav ul {
            display: flex;
        }

        nav li {
            margin-right: 20px;
        }

        nav a {
            color: #fff;
            text-decoration: none;
        }
        /* this is main content */
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 200px;
            height: 100%;
            background-color: #f1f1f1;
            padding: 20px;
        }

        .main-content {
            margin-left: 50px;
            padding: 20px;
        }

        h1 {
            font-size: 24px;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        a {
            color: #333;
            text-decoration: none;
        }

        a:hover {
            color: #000;
            text-decoration: underline;
        }
        .my-button {
            background-color: #4CAF50; /* Green background color */
            color: white; /* Text color */
            padding: 10px 20px; /* Padding */
            border: none; /* Remove border */
            border-radius: 4px; /* Rounded corners */
            cursor: pointer; /* Change cursor on hover */
            transition: background-color 0.3s ease; /* Add transition effect */
            }

            /* Change background color on hover */
        .my-button:hover {
            background-color: #3e8e41;
        }
        /*for displaying moderator details */
        #userDetailsContainer {
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
            background-color: #fff;
            padding: 20px;
            box-sizing: border-box;
            display: none;
            }

        #userDetailsContainer.show {
            display: block;
            }

    </style>
    <script>
        function deleteUser(id) {
            $.ajax({
                type: "POST",
                url: "deleteUser",
                data: { id: id },
                success: function(response) {
                // update the UI to reflect the new number of upvotes
                
                //$("#"+id).find('.status').text(response.status);
                //var row = $('#' + id).closest('tr');
                //row.find('.status').text(response.status);
                location.reload();
                },
                error: function() {
                alert("Error updating upvotes");
                }
            });
        }
        function denyModerator(id) {
            $.ajax({
                type: "POST",
                url: "denyModerator",
                data: { id: id },
                success: function(response) {
                // update the UI to reflect the new number of upvotes
                
                //$("#"+id).find('.status').text(response.status);
                //var row = $('#' + id).closest('tr');
                //row.find('.status').text(response.status);
                location.reload();
                },
                error: function() {
                alert("Error updating upvotes");
                }
            });
        }
    </script>
</head>
<body>
	<div class="header">
        <div class="logo">
            <a href="homepage">
                <img src="${pageContext.request.contextPath}/images/logo.png" height="20px" width="70px" alt=...>
            </a>
        </div>
        <div class="search-box">
          <form>
            <input type="text" placeholder="Search...">
            <button type="submit">Search</button>
          </form>
        </div>
        <nav>
          <ul>
            <li><a href="homepage">Home</a></li>
            <li><a href="#">Your Profile, ${user.firstName}</a></li>
            <li><a href="#">Notifications</a></li>
            <li><a href="logout">Logout</a></li>
          </ul>
        </nav>
    </div>
	<div class="main-content">
		<h1>User List</h1>
		<table>
			<tr>
				<th>First Name</th>
				<th>Last name</th>
				<th>Email</th>
                <th>Status</th>
                <th>State</th>
                <th>Country</th>
                <th>Approve/Deny Moderator</th>
                
			</tr>
			<c:forEach items="${userList}" var="user">
                <c:set var="userId" value="${user.userid}"/>
                <div id="${userId}">
                    <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        
                        <td id="myDiv" class="status">${user.status}</td>
                        <td>${user.address.state}</td>
                        <td>${user.address.country}</td>
                        <!-- <td><button class="my-button" id="moderatorDetailsButton" onclick="displayModeratorDetails('${moderator}')">View Details</button></td> -->
                        <c:if test="${user.status == 'APPROVED'}">
                            <td><button class="my-button" onclick="deleteUser('${user.userid}')">Delete</button></td>
                        </c:if>
                        
                        
                        
                    </tr>
                </div>
			</c:forEach>
		</table>
        <c:if test="${message != null}">
            <p>${message}</p>
        </c:if>
        <div id="userDetailsContainer"></div>
	</div>
</body>
</html>
