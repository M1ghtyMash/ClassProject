package servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtil;

//@WebServlet("/mark-subject-attendance")
public class MarkSubjectAttendanceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try (BufferedReader reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject requestBody = new JSONObject(sb.toString());
            JSONArray attendanceArray = requestBody.getJSONArray("attendance");

            try (Connection conn = DBUtil.getConnection()) {
                String insertSQL = "INSERT INTO subject_attendance_records (student_id, subject, attendance_date, status) " +
                                   "VALUES (?, ?, ?, ?) " +
                                   "ON DUPLICATE KEY UPDATE status = VALUES(status)";

                PreparedStatement stmt = conn.prepareStatement(insertSQL);

                for (int i = 0; i < attendanceArray.length(); i++) {
                    JSONObject record = attendanceArray.getJSONObject(i);

                    stmt.setInt(1, record.getInt("student_id"));
                    stmt.setString(2, record.getString("subject"));
                    stmt.setDate(3, Date.valueOf(record.getString("date")));
                    stmt.setString(4, record.getString("status"));

                    stmt.addBatch();
                }

                stmt.executeBatch();
                jsonResponse.put("message", "Attendance saved successfully");
                out.print(jsonResponse.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("error", e.getMessage());
            out.print(jsonResponse.toString());
        }
    }
}
