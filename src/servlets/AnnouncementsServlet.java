package servlets;

import utils.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class AnnouncementsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JSONArray announcementsArray = new JSONArray();

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT title, message, date_posted, target_group FROM announcements ORDER BY date_posted DESC"
            );

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject announcement = new JSONObject();
                announcement.put("title", rs.getString("title"));
                announcement.put("message", rs.getString("message"));
                announcement.put("date", rs.getDate("date_posted").toString());
                announcement.put("targetGroup", rs.getString("target_group"));

                announcementsArray.put(announcement);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        out.print(announcementsArray.toString());
    }
}
