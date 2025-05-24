package servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtil;

@WebServlet("/teacher-profile")
public class TeacherProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject profile = new JSONObject();

        int teacherId = 1; // Hardcoded for now

        try (Connection conn = DBUtil.getConnection()) {
            // Step 1: Get teacher basic info
            PreparedStatement stmt1 = conn.prepareStatement(
                "SELECT name, email FROM teachers WHERE teacher_id = ?"
            );
            stmt1.setInt(1, teacherId);
            ResultSet rs1 = stmt1.executeQuery();

            if (rs1.next()) {
                profile.put("teacher_id", teacherId);
                profile.put("name", rs1.getString("name"));
                profile.put("email", rs1.getString("email"));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                profile.put("error", "Teacher not found.");
                out.print(profile.toString());
                return;
            }

            // Step 2: Get subjects taught
            PreparedStatement stmt2 = conn.prepareStatement(
                "SELECT c.subject_name FROM teacher_classes tc JOIN classes c ON tc.class_id = c.class_id WHERE tc.teacher_id = ?"
            );
            stmt2.setInt(1, teacherId);
            ResultSet rs2 = stmt2.executeQuery();

            JSONArray subjects = new JSONArray();
            while (rs2.next()) {
                subjects.put(rs2.getString("subject_name"));
            }

            profile.put("subjects", subjects);
            out.print(profile.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            profile.put("error", "Database error: " + e.getMessage());
            out.print(profile.toString());
        }
    }
}
