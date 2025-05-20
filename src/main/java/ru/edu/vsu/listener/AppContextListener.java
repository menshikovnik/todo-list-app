package ru.edu.vsu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        // Подгружаем параметры JDBC из web.xml или контекста
        String url = ctx.getInitParameter("jdbc.url");
        String user = ctx.getInitParameter("jdbc.user");
        String password = ctx.getInitParameter("jdbc.password");

        // Инициализируем утилиту для получения соединений
        DbUtil.init(url, user, password);

        // Создаём DAO и сервисы
        UserDao userDao = new UserDao();
        TaskDao taskDao = new TaskDao();
        UserService userService = new UserService(userDao);
        TaskService taskService = new TaskService(taskDao);

        // Сохраняем сервисы в контексте
        ctx.setAttribute("userService", userService);
        ctx.setAttribute("taskService", taskService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Нечего очищать при использовании DriverManager
    }
}