package servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtil;

@WebServlet("/teacher-announcements")
public class TeacherAnnouncementsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT id, title, message, date_posted FROM announcements ORDER BY date_posted DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            JSONArray announcementList = new JSONArray();

            while (rs.next()) {
                JSONObject announcement = new JSONObject();
                announcement.put("id", rs.getInt("id"));
                announcement.put("title", rs.getString("title"));
                announcement.put("message", rs.getString("message"));
                announcement.put("date", rs.getDate("date_posted").toString());

                announcementList.put(announcement);
            }

            JSONObject responseJson = new JSONObject();
            responseJson.put("announcements", announcementList);

            out.print(responseJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            JSONObject error = new JSONObject();
            error.put("error", "Database error: " + e.getMessage());
            out.print(error.toString());
        }
    }
}
