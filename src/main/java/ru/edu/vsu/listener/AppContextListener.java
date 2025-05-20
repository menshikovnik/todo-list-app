package ru.edu.vsu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import ru.edu.vsu.dao.AttestationDao;
import ru.edu.vsu.dao.SubjectDao;
import ru.edu.vsu.dao.TaskDao;
import ru.edu.vsu.dao.UserDao;
import ru.edu.vsu.service.AttestationService;
import ru.edu.vsu.service.SubjectService;
import ru.edu.vsu.service.TaskService;
import ru.edu.vsu.service.UserService;
import ru.edu.vsu.util.DbUtil;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        String url = ctx.getInitParameter("jdbc.url");
        String user = ctx.getInitParameter("jdbc.user");
        String password = ctx.getInitParameter("jdbc.password");

        DbUtil.init(url, user, password);

        UserDao userDao = new UserDao();
        TaskDao taskDao = new TaskDao();
        AttestationDao attDao = new AttestationDao();
        SubjectDao subjDao = new SubjectDao();
        UserService userService = new UserService(userDao);
        TaskService taskService = new TaskService(taskDao);
        AttestationService attestationService = new AttestationService(attDao);
        SubjectService subjectService = new SubjectService(subjDao);


        ctx.setAttribute("userService", userService);
        ctx.setAttribute("taskService", taskService);
        ctx.setAttribute("attService", attestationService);
        ctx.setAttribute("subjService", subjectService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}