package servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtil;

@WebServlet("/teacher-grades")
public class TeacherGradesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();

        String subject = request.getParameter("subject");
        if (subject == null || subject.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            result.put("error", "Missing subject parameter");
            out.print(result.toString());
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            // Corrected SQL with only one parameter
            String sql = """
                SELECT s.student_id, s.name, g.grade
                FROM students s
                JOIN enrollments e ON s.student_id = e.student_id
                JOIN classes c ON e.class_id = c.class_id
                LEFT JOIN grades g ON g.student_id = s.student_id AND g.subject = c.subject_name
                WHERE c.subject_name = ?
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, subject);  // Only one parameter needed now

            ResultSet rs = stmt.executeQuery();
            JSONArray students = new JSONArray();

            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("student_id"));
                obj.put("name", rs.getString("name"));
                obj.put("grade", rs.getString("grade") != null ? rs.getString("grade") : "");
                students.put(obj);
            }

            result.put("students", students);
            out.print(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("error", e.getMessage());
            out.print(result.toString());
        }
    }
}
