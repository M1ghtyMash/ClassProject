package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import org.json.JSONObject;
import utils.DBUtil;

@WebServlet("/create-student")
public class CreateStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String classYear = request.getParameter("class"); // class/year
        String contact = request.getParameter("contact");
        String[] classIds = request.getParameterValues("classes"); // multiple subject assignments

        try (Connection conn = DBUtil.getConnection()) {
            // Insert into students table
            String insertSQL = "INSERT INTO students (name, email, class_year, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, classYear);
            stmt.setString(4, contact);
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int studentId = keys.getInt(1);

                if (classIds != null) {
                    String enrollSQL = "INSERT INTO enrollments (student_id, class_id) VALUES (?, ?)";
                    PreparedStatement enrollStmt = conn.prepareStatement(enrollSQL);
                    for (String classId : classIds) {
                        enrollStmt.setInt(1, studentId);
                        enrollStmt.setInt(2, Integer.parseInt(classId));
                        enrollStmt.addBatch();
                    }
                    enrollStmt.executeBatch();
                }

                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Student created successfully.");
                jsonResponse.put("student_id", studentId);
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Failed to retrieve student ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Database error: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
    }
}
