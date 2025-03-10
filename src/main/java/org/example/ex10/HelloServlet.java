package org.example.ex10;

import java.io.*;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// 이름(name)과 경로(value, path)를 지정
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String nickname;
    public void init() {
        Dotenv dotenv = Dotenv.load();
        nickname = dotenv.get("NICKNAME");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + nickname + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}