<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Homepage</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
        }
        .header {
            background-color: #007bff;
            color: white;
            padding: 15px 20px;
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            box-sizing: border-box; /* Include padding in width */
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .logout-link a {
            color: white;
            text-decoration: none;
            font-size: 16px;
        }
        .logout-link a:hover {
            text-decoration: underline;
        }
        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 700px;
            margin-top: 30px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
            color: #333;
        }
        textarea {
            width: calc(100% - 22px); /* Account for padding and border */
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            resize: vertical;
            min-height: 100px;
            font-size: 16px;
        }
        button {
            width: 100%;
            padding: 12px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 18px;
            margin-bottom: 20px;
        }
        button:hover {
            background-color: #218838;
        }
        .story-output {
            background-color: #e9ecef;
            padding: 15px;
            border-radius: 4px;
            text-align: left;
            white-space: pre-wrap; /* Preserves whitespace and line breaks */
            max-height: 400px; /* Limit height for scrollability */
            overflow-y: auto; /* Enable scrolling */
        }
        .extra-info-output {
            margin-top: 20px;
            background-color: #e0f7fa;
            padding: 15px;
            border-radius: 4px;
            text-align: left;
            white-space: pre-wrap;
            max-height: 200px;
            overflow-y: auto;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Welcome, <%= session.getAttribute("user") != null ? session.getAttribute("user") : "Guest" %>!</h1>
        <div class="logout-link">
            <a href="AuthServlet?action=logout">Logout</a>
        </div>
    </div>

    <div class="container">
        <h2>Generate Your Story</h2>
        <form action="GenerateServlet" method="post">
            <textarea name="prompt" placeholder="Enter your prompt for the story here... (e.g., A brave knight goes on a quest to rescue a princess from a dragon in a dark forest)"></textarea>
            <button type="submit">Generate Story</button>
        </form>

        <% if (request.getAttribute("story") != null) { %>
            <h3>Generated Story:</h3>
            <div class="story-output">
                <%= request.getAttribute("story") %>
            </div>
        <% } %>

        <% if (request.getAttribute("extraInfo") != null) { %>
            <h3>Additional Information:</h3>
            <div class="extra-info-output">
                <%= request.getAttribute("extraInfo") %>
            </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error" style="color: red; margin-top: 20px;"><%= request.getAttribute("error") %></p>
        <% } %>
    </div>
</body>
</html>