package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.CommentDAO;
import com.grotor.termwork1sem.entities.Account;
import com.grotor.termwork1sem.entities.Comment;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class CommentServlet extends AbstractServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String text = req.getParameter("text");
        String type = req.getParameter("type");
        UUID entityId = UUID.fromString(req.getParameter("entityId"));
        Account user = (Account) req.getSession().getAttribute("account");

        Comment comment = new Comment();
        comment.setEntityId(entityId);
        comment.setText(text);
        comment.setAuthor(user);

        CommentDAO commentDAO = new CommentDAO(DevPostgressConnection.getInstance());

        commentDAO.save(comment);

        resp.sendRedirect(type + "?id=" + entityId);
    }
}
