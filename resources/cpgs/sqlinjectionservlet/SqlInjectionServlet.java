package io.shiftleft.testcode.sp2rt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/sqli")
public class SqlInjectionServlet extends HttpServlet {
  public Connection conn = null; //maybe someone will set this :)

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String input = request.getParameter("input");
    String sql = "INSERT INTO EXAMPLES VALUES " + input;

    try {
      Statement stmt = conn.createStatement();
      ResultSet results  = stmt.executeQuery(sql);
      System.out.println("look what the database said! " + results);
    } catch (SQLException e) {
        e.printStackTrace();
    }

  }
}
