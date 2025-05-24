package servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.*;
import java.sql.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtil;

public class StudentAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject responseJson = new JSONObject();

        int studentId = 1; // Hardcoded for now

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT attendance_date, subject, status FROM subject_attendance_records WHERE student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);

            ResultSet rs = stmt.executeQuery();
            Map<String, List<JSONObject>> attendanceByMonth = new HashMap<>();

            while (rs.next()) {
                String attendanceDate = rs.getString("attendance_date");
                String subject = rs.getString("subject");
                String status = rs.getString("status");

                String month = getMonthFromDate(attendanceDate);

                JSONObject record = new JSONObject();
                record.put("date", attendanceDate);
                record.put("subject", subject);
                record.put("status", status);

                attendanceByMonth.putIfAbsent(month, new ArrayList<>());
                attendanceByMonth.get(month).add(record);
            }

            JSONObject attendance = new JSONObject();
            for (String month : attendanceByMonth.keySet()) {
                JSONArray records = new JSONArray(attendanceByMonth.get(month));
                attendance.put(month, records);
            }

            responseJson.put("name", "Muhiminul");
            responseJson.put("attendance", attendance);
            out.print(responseJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseJson.put("error", e.getMessage());
            out.print(responseJson.toString());
        }
    }

    private String getMonthFromDate(String date) {
        String monthNumber = date.substring(5, 7);
        switch (monthNumber) {
            case "01": return "January";
            case "02": return "February";
            case "03": return "March";
            case "04": return "April";
            case "05": return "May";
            case "06": return "June";
            case "07": return "July";
            case "08": return "August";
            case "09": return "September";
            case "10": return "October";
            case "11": return "November";
            case "12": return "December";
            default: return "Unknown";
        }
    }
}
