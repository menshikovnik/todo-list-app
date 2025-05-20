<%--
  Created by IntelliJ IDEA.
  User: nick
  Date: 5/20/25
  Time: 11:09 PM
  To change this template use File | Settings | File Templates.
--%>
<!-- tasks.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Task List</title>

    <!-- ===== BASIC STYLE ===== -->
    <style>
        :root {
            --primary: #2C7BE5;
            --primary-light: #5393ff;
            --danger: #e55353;
            --success: #28a745;
            --warning: #ff9f43;
            --font: 'Segoe UI', Tahoma, Helvetica, Arial, sans-serif;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            background: #f5f7fa;
            font-family: var(--font);
        }

        .container {
            max-width: 900px;
            margin: 3rem auto;
            padding: 0 1rem;
        }

        h2 {
            text-align: center;
            color: var(--primary);
            margin-bottom: 1rem;
        }

        /* ---------- Top links ---------- */
        .actions {
            margin-bottom: 1.25rem;
            text-align: center;
            font-weight: 600;
        }

        .actions a {
            color: var(--primary);
            text-decoration: none;
            margin: 0 .5rem;
        }

        .actions a:hover {
            text-decoration: underline;
        }

        /* ---------- Table ---------- */
        table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(0, 0, 0, .06);
        }

        th, td {
            padding: .75rem 1rem;
            text-align: left;
        }

        th {
            background: var(--primary);
            color: #fff;
            font-weight: 600;
            font-size: .95rem;
        }

        tr:nth-child(even) {
            background: #f8f9fb;
        }

        /* ---------- Status ---------- */
        .status-done {
            color: var(--success);
            font-weight: 600;
        }

        .status-pending {
            color: var(--warning);
            font-weight: 600;
        }

        /* ---------- Buttons ---------- */
        .btn {
            border: none;
            border-radius: 4px;
            padding: .4rem .7rem;
            font-size: .85rem;
            font-weight: 600;
            cursor: pointer;
            transition: opacity .15s;
        }

        .btn.toggle {
            background: var(--primary);
            color: #fff;
        }

        .btn.toggle:hover {
            opacity: .85;
        }

        .btn.delete {
            background: var(--danger);
            color: #fff;
            margin-left: .3rem;
        }

        .btn.delete:hover {
            opacity: .85;
        }

        /* ---------- Responsive tweaks ---------- */
        @media (max-width: 640px) {
            th:nth-child(1), td:nth-child(1) {
                display: none;
            }

            /* скрыть ID на маленьких экранах */
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Your Tasks</h2>

    <!-- ───────── ФИЛЬТРЫ ───────── -->
    <form id="filter" method="get" action="${pageContext.request.contextPath}/tasks" class="filters">
        <label>Attestation:
            <select name="attId" onchange="this.form.submit()">
                <c:forEach var="a" items="${attList}">
                    <option value="${a.id}" <c:if test="${a.id == selAttId}">selected</c:if>>
                            ${a.name}
                    </option>
                </c:forEach>
            </select>
        </label>

        <label>Subject:
            <select name="subjectId" onchange="this.form.submit()">
                <option value="0" <c:if test="${selSubjId == 0}">selected</c:if>>-- All subjects --</option>
                <c:forEach var="s" items="${subjList}">
                    <option value="${s.id}" <c:if test="${s.id == selSubjId}">selected</c:if>>
                            ${s.name}
                    </option>
                </c:forEach>
            </select>
        </label>

        <a class="btn"
           href="${pageContext.request.contextPath}/tasks/add?attId=${selAttId}&subjectId=${selSubjId}">
            + Add task
        </a>
    </form>

    <!-- ───────── ТАБЛИЦА ЗАДАЧ ───────── -->
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Due date</th>
            <th>Status</th>
            <c:if test="${selSubjId == 0}">
                <th>Subject</th>
            </c:if>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="t" items="${tasks}">
            <tr>
                <td>${t.id}</td>
                <td>${t.description}</td>
                <td><c:out value="${t.dueDate}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${t.done}"><span class="status-done">Done</span></c:when>
                        <c:otherwise><span class="status-pending">Pending</span></c:otherwise>
                    </c:choose>
                </td>

                <!-- Показываем название предмета, если выбрано «All subjects» -->
                <c:if test="${selSubjId == 0}">
                    <td>${t.subjectName}</td>
                    <!-- предположено, что в Task есть поле subjectName -->
                </c:if>

                <td>
                    <form action="${pageContext.request.contextPath}/tasks/toggle" method="post"
                          style="display:inline;">
                        <input type="hidden" name="id" value="${t.id}"/>
                        <input type="hidden" name="attId" value="${selAttId}"/>
                        <input type="hidden" name="subjectId" value="${selSubjId}"/>
                        <button class="btn toggle" type="submit">Toggle</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/tasks/delete" method="post"
                          style="display:inline;">
                        <input type="hidden" name="id" value="${t.id}"/>
                        <input type="hidden" name="attId" value="${selAttId}"/>
                        <input type="hidden" name="subjectId" value="${selSubjId}"/>
                        <button class="btn delete" type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty tasks}">
            <tr>
                <td colspan="6" style="text-align:center;">No tasks here yet</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
