package ru.edu.vsu.servlet;

import ru.edu.vsu.model.*;
import ru.edu.vsu.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

@WebServlet("/tasks/*")
public class TaskServlet extends HttpServlet {

    private TaskService taskService;
    private AttestationService attService;
    private SubjectService subjService;

    @Override
    public void init() throws ServletException {
        taskService = (TaskService) getServletContext().getAttribute("taskService");
        attService = (AttestationService) getServletContext().getAttribute("attService");
        subjService = (SubjectService) getServletContext().getAttribute("subjService");
        if (taskService == null || attService == null || subjService == null) {
            throw new ServletException("Services missing in context");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (getLoggedUser(req, resp) == null) return;

        String path = Optional.ofNullable(req.getPathInfo()).orElse("/");
        try {
            if ("/add".equals(path)) {
                renderAddForm(req, resp);
            } else {
                renderTaskTable(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = getLoggedUser(req, resp);
        if (user == null) return;

        String path = Optional.ofNullable(req.getPathInfo()).orElse("/");
        try {
            switch (path) {
                case "/add" -> handleAdd(req, user);
                case "/toggle" -> taskService.toggleDone(
                        safeInt(req.getParameter("id")).orElseThrow(badRequest("id")));
                case "/delete" -> taskService.delete(
                        safeInt(req.getParameter("id")).orElseThrow(badRequest("id")));
                default -> {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        resp.sendRedirect(buildRedirect(req));
    }

    private void renderAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException, SQLException {

        List<Attestation> attList = attService.getAll();
        if (attList.isEmpty()) throw new ServletException("No attestations yet");

        int attId = safeInt(req.getParameter("attId")).orElse(attList.get(0).getId());
        List<Subject> subjList = subjService.getByAttestation(attId);

        int subjId = subjList.isEmpty()
                ? 0
                : safeInt(req.getParameter("subjectId")).orElse(subjList.get(0).getId());

        req.setAttribute("attList", attList);
        req.setAttribute("subjList", subjList);
        req.setAttribute("selAttId", attId);
        req.setAttribute("selSubjId", subjId);

        req.getRequestDispatcher("/WEB-INF/views/add_task.jsp").forward(req, resp);
    }

    private void renderTaskTable(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException, SQLException {

        List<Attestation> attList = attService.getAll();
        if (attList.isEmpty()) throw new ServletException("No attestations yet");

        int attId = safeInt(req.getParameter("attId")).orElse(attList.get(0).getId());

        List<Subject> subjList = subjService.getByAttestation(attId);
        int subjId = subjList.isEmpty()
                ? 0
                : safeInt(req.getParameter("subjectId")).orElse(subjList.get(0).getId());

        List<Task> tasks = (subjId == 0)
                ? taskService.findByAttestation(attId)
                : taskService.findBySubject(subjId);

        req.setAttribute("attList", attList);
        req.setAttribute("subjList", subjList);
        req.setAttribute("tasks", tasks);
        req.setAttribute("selAttId", attId);
        req.setAttribute("selSubjId", subjId);

        req.getRequestDispatcher("/WEB-INF/views/tasks.jsp").forward(req, resp);
    }

    private void handleAdd(HttpServletRequest req, User user) throws SQLException, ServletException {

        int attId = safeInt(req.getParameter("attId")).orElseThrow(badRequest("attId"));
        int subjId = safeInt(req.getParameter("subjectId"))
                .orElseGet(() -> firstSubjectId(attId).orElseThrow(badRequest("subjectId")));
        String desc = req.getParameter("description");
        Date dueDate = safeDate(req.getParameter("due_date")).orElse(null);

        taskService.addTask(user.getId(), subjId, desc, dueDate);
    }

    private Optional<Integer> safeInt(String raw) {
        if (raw == null || raw.isBlank()) return Optional.empty();
        try {
            return Optional.of(Integer.parseInt(raw.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private Optional<Date> safeDate(String raw) {
        if (raw == null || raw.isBlank()) return Optional.empty();
        try {
            return Optional.of(Date.valueOf(raw));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Optional<Integer> firstSubjectId(int attId) {
        try {
            List<Subject> list = subjService.getByAttestation(attId);
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0).getId());
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    private String buildRedirect(HttpServletRequest req) {
        String attId = req.getParameter("attId");
        String subjId = req.getParameter("subjectId");

        StringBuilder url = new StringBuilder(req.getContextPath()).append("/tasks");
        String sep = "?";
        if (attId != null && !attId.isBlank()) {
            url.append(sep).append("attId=").append(attId);
            sep = "&";
        }
        if (subjId != null && !subjId.isBlank()) {
            url.append(sep).append("subjectId=").append(subjId);
        }
        return url.toString();
    }

    private User getLoggedUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/api/auth/login");
            return null;
        }
        return (User) sess.getAttribute("user");
    }

    private Supplier<IllegalArgumentException> badRequest(String p) {
        return () -> new IllegalArgumentException(p + " missing or malformed");
    }
}