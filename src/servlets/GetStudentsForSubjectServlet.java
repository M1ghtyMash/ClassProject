import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtil;

@WebServlet("/get-students-by-subject")
public class GetStudentsForSubjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        String subject = request.getParameter("subject");
        if (subject == null || subject.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.put("error", "Missing subject parameter");
            out.print(jsonResponse.toString());
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT DISTINCT s.student_id, s.name FROM students s " +
                         "JOIN weekly_schedule w ON s.student_id = w.student_id " +
                         "WHERE w.subject = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, subject);
            ResultSet rs = stmt.executeQuery();

            JSONArray studentArray = new JSONArray();

            while (rs.next()) {
                JSONObject student = new JSONObject();
                student.put("student_id", rs.getInt("student_id"));
                student.put("name", rs.getString("name"));
                studentArray.put(student);
            }

            jsonResponse.put("students", studentArray);
            out.print(jsonResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("error", e.getMessage());
            out.print(jsonResponse.toString());
        }
    }
}