<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<anyxmlelement xmlns:c="jakarta.tags.core" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Homepage</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        #header {
            background-color: #333;
            color: #fff;
            padding: 10px;
        }

        #sidebar-left {
            float: left;
            width: 20%;
            background-color: #f2f2f2;
            height: 100vh;
        }

        #main-content {
            float: left;
            width: 60%;
            padding: 10px;
        }

        #sidebar-right {
            float: left;
            width: 20%;
            background-color: #f2f2f2;
            height: 100vh;
        }

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        li {
            margin: 10px 0;
        }

        a {
            color: #333;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
        /* this is header design */
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
        /* style for post container */
        .container {
            display: inline-block;
            margin: auto;
            width: 50%;
            border: 3px solid #73AD21;
            padding: 10px;
        }

        label {
            display: inline;
            font-weight: bold;
            margin-top: 10px;
        }

        form:input[type=text], form:textarea {
            width: 50%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        input[type=submit] {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 50%;
        }

        input[type=submit]:hover {
            background-color: #45a049;
        }

        /* style for posts */
        /* CSS code */
        .topic-container {
            border: 1px solid #ddd;
            padding: 10px;
            display: flex;
            flex-direction: column;
        }

        .topic-header {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .topic-header img {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 10px;
        }

        .topic-description {
                margin-bottom: 10px;
        }

        /* .topic-description img {
            width: 100%;
            height: auto;
            margin-top: 10px;
        } */

        .topic-actions {
            display: flex;
            justify-content: space-between;
            border-top: 1px solid #ddd;
            padding-top: 10px;
        }
        /* styling for comments */
        .comment {
            background-color: #f2f2f2;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-size: 14px;
            line-height: 1.5;
        }

        .comment p {
            margin: 0;
        }

        .comment .author {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .comment .date {
            font-style: italic;
            color: #999;
        }
        /* button styling */
        .link {
            background-color: #4CAF50; /* Green background color */
            color: white; /* Text color */
            padding: 10px 20px; /* Padding */
            border: none; /* Remove border */
            border-radius: 4px; /* Rounded corners */
            cursor: pointer; /* Change cursor on hover */
            transition: background-color 0.3s ease; /* Add transition effect */
            }

            /* Change background color on hover */
        .link:hover {
            background-color: #3e8e41;
        }
        




    </style>
    <script>
        document.getElementById("useridinput").innerHTML = document.getElementById("userID").textContent;
        function toggleForm() {
			var postcontainer = document.getElementById("container");
			
			if (postcontainer.style.display === "none") {
                postcontainer.style.display = "block";
            } else {
                postcontainer.style.display = "none";
            }
		}
        function updateUpvotes(button,postid,userid) {
            $.ajax({
                type: "POST",
                url: "updateVote",
                data: { userid: userid,
                        postid: postid,
                        button: button },
                success: function(response) {
                // update the UI to reflect the new number of upvotes
                
                    $("#likesCount").text(response.updateVotes);
                },
                error: function() {
                alert("Error updating upvotes");
                }
            });
        }

    </script>
</head>
<body>
    <div id="header">
        <!-- Header content goes here -->
    </div>
    <div class="header">
        <div class="logo">
            <a href="homepage">
                <img src="${pageContext.request.contextPath}/images/logo.png" height="20px" width="70px" alt=...>
            </a>
        </div>
        
        <nav>
          <ul>
            <li><a href="homepage">Home</a></li>
            <li><a href="#">Your Profile, ${user.firstName}</a></li>
            <!-- <li><a href="#">Notifications</a></li> -->
            <li><a href="logout">Logout</a></li>
          </ul>
        </nav>
    </div>

    <div id="sidebar-left">
        <ul>
            <c:forEach var="page" items="${pageList}">
                <li>
                    <a href="${page.id}">${page.pageTitle}</a>
                </li>
            </c:forEach>
            <c:forEach var="userpage" items="${userpageList}">
                <li>
                    <a href="${userpage.page.id}">${userpage.page.pageTitle}</a>
                </li>
            </c:forEach>
            
            <c:if test="${sessionScope.user.userType == 'MODERATOR'}">
                <li><a href="approvePageRequest">Approve Page requests</a></li>
                <li><a href="addPage">Add a Page</a></li>
            </c:if>
            <c:if test="${sessionScope.user.userType == 'USER'}">
                <li><a href="searchForPages">searchForPages</a></li>
                
            </c:if>
        </ul>
    </div>
     
    <div id="main-content">
       
        <!-- <button onclick="toggleForm()">Post</button> -->
        <div class="container" id="container">
            <form:form modelAttribute="topic" action="${a}/createTopic" method="post">
            <!-- <form action="createPost" method="post"> -->
                <!-- <input type="hidden" name="${pageID}" value="${a}"/> -->
                <!-- <input type="hidden" name="myParagraph" value="<c:out value='${user.userid}'/>"/> -->
                <label for="topicDesciption">Topic:</label>
                <form:textarea path="topicDescription" id="topicDescription" rows="4" cols="50" />
                
                <input type="submit" value="Create Topic"/>
            </form>
        </form:form>
        </div>
        <h2> ${page.pageTitle}</h2>
        <ul>
            
            <!-- Add more posts here -->
            <c:forEach var="t" items="${topics}">
                <li>
                <div class="t">
                    
                    
                </div>
                <!-- JSP code -->
                <div class="topic-container">
                    <div class="topic-header">
                    <!-- <img src="profile-picture.jpg" alt="Profile Picture"> -->
                    <div class="topic-header-text">
                        <h3>${t.user.firstName} ${t.user.lastName}</h3><br/>
                        <h4>${t.topicCreatedOn}</h4>
                    </div>
                    </div>
                    <div class="topic-description">
                        <p>${t.topicDescription}</p>
                    </div>
                    <div class="comments">

                    </div>
                    
                    <div class="topic-actions">
                        
                            <!-- <button onclick="updateUpvotes('${t.topicId}')">View Details</button> -->
                            <a href="${a}/${t.topicId}" class="link">View Details</a>
                    </div>
                </div>
  
                </li>
            </c:forEach>
        </ul>

        <h2>Featured Posts</h2>
        <ul>
            <li><a href="#">Post 1</a></li>
            <li><a href="#">Post 2</a></li>
            <li><a href="#">Post 3</a></li>
            <!-- Add more featured posts here -->
        </ul>
    </div>

    
</body>
</html>
