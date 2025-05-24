package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtil;

@WebServlet("/get-students")
public class GetStudentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONArray studentsArray = new JSONArray();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT student_id, name, class_year, email, contact FROM students";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject student = new JSONObject();
                student.put("student_id", rs.getInt("student_id"));
                student.put("name", rs.getString("name"));
                student.put("class_year", rs.getString("class_year"));
                student.put("email", rs.getString("email"));
                student.put("contact", rs.getString("contact"));
                studentsArray.put(student);
            }

            JSONObject result = new JSONObject();
            result.put("students", studentsArray);
            out.print(result.toString());

        } catch (Exception e) {
            response.setStatus(500);
            e.printStackTrace();
            JSONObject error = new JSONObject();
            error.put("error", "Database error: " + e.getMessage());
            out.print(error.toString());
        }
    }
}
