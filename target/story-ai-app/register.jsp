<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <style>
        body {
            background: #e6fff5;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
        }

        /* ✅ Navbar Styles */
        .navbar {
            background-color: #28a745;
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .navbar .title {
            font-size: 20px;
            font-weight: bold;
        }

        .navbar .nav-right a {
            background-color: white;
            color: #28a745;
            padding: 6px 12px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .navbar .nav-right a:hover {
            background-color: #e6f9ee;
        }

        .box {
            width: 350px;
            margin: 100px auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #28a745;
            text-align: center;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #28a745;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
            font-size: 15px;
        }

        input[type="submit"]:hover {
            background: #1e7e34;
        }

        .options {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 13px;
        }

        .options a {
            color: #28a745;
            text-decoration: none;
        }

        .options a:hover {
            text-decoration: underline;
        }

        p {
            text-align: center;
            font-size: 14px;
        }

        a {
            color: #28a745;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .error {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="navbar">
    <div class="title">sTory Ai</div>
    <div class="nav-right">
        <a href="login.jsp">Login</a>
    </div>
</div>

<div class="box">
    <h2>Register</h2>
    <form action="AuthServlet" method="post">
        <input type="hidden" name="action" value="register" />
        <input type="text" name="username" placeholder="Choose Username or Email" required />
        <input type="password" name="password" placeholder="Choose Password" required />
        <input type="submit" value="Register" />
    </form>
    <p>Already have an account? <a href="login.jsp">Login</a></p>

    <%-- Display error message if present --%>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
</div>

</body>
</html>