<%--
  Created by IntelliJ IDEA.
  User: nick
  Date: 5/20/25
  Time: 11:10 PM
  To change this template use File | Settings | File Templates.
--%>
<!-- addTask.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add Task</title>

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

        .card {
            background: #fff;
            width: 100%;
            max-width: 480px;
            padding: 2rem 2.5rem;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, .06);
        }

        h2 {
            margin-bottom: 1.5rem;
            text-align: center;
            color: var(--primary);
        }

        .message {
            margin-bottom: 1rem;
            text-align: center;
            font-weight: 600;
            color: var(--danger);
        }

        label {
            display: block;
            margin-bottom: 1.2rem;
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
            margin-top: .5rem;
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
    <h2>Add New Task</h2>

    <!-- ───── Форма выбора аттестации (GET) ───── -->
    <form id="attForm" method="get" action="${pageContext.request.contextPath}/tasks/add">
        <label>Attestation:
            <select name="attId" onchange="document.getElementById('attForm').submit()">
                <c:forEach var="a" items="${attList}">
                    <option value="${a.id}" <c:if test="${a.id == selAttId}">selected</c:if>>
                            ${a.name}
                    </option>
                </c:forEach>
            </select>
        </label>
    </form>

    <!-- ───── Сообщение об ошибке ───── -->
    <c:if test="${not empty error}">
        <p class="message">${error}</p>
    </c:if>

    <!-- ───── Форма добавления задачи (POST) ───── -->
    <form method="post" action="${pageContext.request.contextPath}/tasks/add">
        <input type="hidden" name="attId" value="${selAttId}"/>

        <label>Subject:
            <select name="subjectId" required>
                <c:forEach var="s" items="${subjList}">
                    <option value="${s.id}" <c:if test="${s.id == selSubjId}">selected</c:if>>
                            ${s.name}
                    </option>
                </c:forEach>
            </select>
        </label>

        <label>Description:
            <input type="text" name="description" required maxlength="255"/>
        </label>

        <label>Due date:
            <input type="date" name="due_date"/>
        </label>

        <button type="submit">Add Task</button>
    </form>

    <div class="extra">
        <a href="${pageContext.request.contextPath}/tasks">← Back to Task List</a>
    </div>
</div>
</body>
</html>