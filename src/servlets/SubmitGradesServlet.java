package servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtil;

@WebServlet("/submit-grades")
public class SubmitGradesServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try (BufferedReader reader = request.getReader()) {
            StringBuilder requestData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestData.append(line);
            }

            JSONObject body = new JSONObject(requestData.toString());
            JSONArray gradesArray = body.getJSONArray("grades");

            try (Connection conn = DBUtil.getConnection()) {
                String sql = """
                    INSERT INTO grades (student_id, subject, grade)
                    VALUES (?, ?, ?)
                    ON DUPLICATE KEY UPDATE grade = VALUES(grade)
                """;

                PreparedStatement stmt = conn.prepareStatement(sql);

                for (int i = 0; i < gradesArray.length(); i++) {
                    JSONObject gradeObj = gradesArray.getJSONObject(i);
                    stmt.setInt(1, gradeObj.getInt("student_id"));
                    stmt.setString(2, gradeObj.getString("subject"));
                    stmt.setString(3, gradeObj.getString("grade"));
                    stmt.addBatch();
                }

                stmt.executeBatch();
                jsonResponse.put("message", "Grades saved successfully.");
                out.print(jsonResponse.toString());

            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.put("error", "Database error: " + e.getMessage());
                out.print(jsonResponse.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.put("error", "Invalid JSON data.");
            out.print(jsonResponse.toString());
        }
    }
}
