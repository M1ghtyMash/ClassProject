package servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import org.json.JSONObject;
import utils.DBUtil;

@WebServlet("/post-announcement")
public class PostAnnouncementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (BufferedReader reader = request.getReader()) {
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            JSONObject input = new JSONObject(data.toString());
            String title = input.getString("title");
            String message = input.getString("message");
            LocalDate date = LocalDate.now();

            try (Connection conn = DBUtil.getConnection()) {
                String sql = "INSERT INTO announcements (title, message, date_posted, target_group) VALUES (?, ?, ?, 'all')";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, title);
                stmt.setString(2, message);
                stmt.setDate(3, Date.valueOf(date));
                stmt.executeUpdate();

                JSONObject success = new JSONObject();
                success.put("status", "Announcement posted successfully");
                out.print(success.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSONObject error = new JSONObject();
            error.put("error", "Invalid input or DB error");
            out.print(error.toString());
        }
    }
}
