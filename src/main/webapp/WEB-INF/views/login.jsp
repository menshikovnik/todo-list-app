<%--
  Created by IntelliJ IDEA.
  User: nick
  Date: 5/20/25
  Time: 11:09 PM
  To change this template use File | Settings | File Templates.
--%>
<!-- login.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>

    <!-- ===== BASIC STYLE ===== -->
    <style>
        :root {
            --primary: #2C7BE5;
            --primary-light: #5393ff;
            --success: #28a745;
            --danger: #e55353;
            --font: 'Segoe UI', Tahoma, Helvetica, Arial, sans-serif;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            padding: 1rem;
            background: #f5f7fa;
            font-family: var(--font);
        }

        /* ---------- Card ---------- */
        .card {
            background: #fff;
            width: 100%;
            max-width: 420px;
            padding: 2rem 2.5rem;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, .06);
        }

        h2 {
            margin-bottom: 1.5rem;
            text-align: center;
            color: var(--primary);
        }

        /* ---------- Messages ---------- */
        .message {
            margin-bottom: 1rem;
            text-align: center;
            font-weight: 600;
        }

        .success {
            color: var(--success);
        }

        .error {
            color: var(--danger);
        }

        /* ---------- Form ---------- */
        label {
            display: block;
            margin-bottom: .75rem;
            font-weight: 600;
            color: #333;
        }

        input {
            width: 100%;
            padding: .65rem .8rem;
            border: 1px solid #ccd0d5;
            border-radius: 5px;
            margin-top: .35rem;
            transition: border .2s;
        }

        input:focus {
            outline: none;
            border-color: var(--primary-light);
        }

        button {
            width: 100%;
            margin-top: 1rem;
            padding: .75rem;
            border: none;
            border-radius: 5px;
            background: var(--primary);
            color: #fff;
            font-weight: 600;
            cursor: pointer;
            transition: background .2s;
        }

        button:hover {
            background: var(--primary-light);
        }

        /* ---------- Extra links ---------- */
        .extra {
            margin-top: 1rem;
            text-align: center;
            font-size: .9rem;
        }

        .extra a {
            color: var(--primary);
            text-decoration: none;
        }

        .extra a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="card">
    <h2>Login</h2>

    <c:if test="${not empty param.registered}">
        <p class="message success">Registration successful. Please login.</p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="message error">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/api/auth/login" method="post">
        <label>Email
            <input type="email" name="email" required>
        </label>
        <label>Password
            <input type="password" name="password" required>
        </label>
        <button type="submit">Login</button>
    </form>

    <div class="extra">
        Don't have an account?
        <a href="${pageContext.request.contextPath}/api/auth/register">Register here</a>
    </div>
</div>

</body>
</html>