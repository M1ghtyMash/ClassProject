package servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtil;

public class TeacherClassesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject responseJson = new JSONObject();

        try (Connection conn = DBUtil.getConnection()) {
            int teacherId = 1; // For now

            // Step 1: Fetch classes the teacher teaches
            String classQuery = "SELECT c.class_id, c.subject_name, c.course_code, c.semester " +
                                "FROM classes c " +
                                "JOIN teacher_classes tc ON c.class_id = tc.class_id " +
                                "WHERE tc.teacher_id = ?";
            PreparedStatement classStmt = conn.prepareStatement(classQuery);
            classStmt.setInt(1, teacherId);
            ResultSet classRs = classStmt.executeQuery();

            JSONArray classList = new JSONArray();

            while (classRs.next()) {
                JSONObject classObj = new JSONObject();
                int classId = classRs.getInt("class_id");
                String subject = classRs.getString("subject_name");

                classObj.put("subject_name", subject);
                classObj.put("course_code", classRs.getString("course_code"));
                classObj.put("semester", classRs.getString("semester"));

                // Step 2: Find this subject's schedule (where it appears in weekly_schedule)
                String scheduleQuery = "SELECT day_of_week, period_number FROM weekly_schedule WHERE subject = ?";
                PreparedStatement schedStmt = conn.prepareStatement(scheduleQuery);
                schedStmt.setString(1, subject);

                ResultSet schedRs = schedStmt.executeQuery();
                JSONArray scheduleArray = new JSONArray();

                while (schedRs.next()) {
                    String entry = schedRs.getString("day_of_week") + " - Period " + schedRs.getInt("period_number");
                    scheduleArray.put(entry);
                }

                classObj.put("weekly_schedule", scheduleArray);
                classList.put(classObj);
            }

            responseJson.put("teacher_id", teacherId);
            responseJson.put("classes", classList);
            out.print(responseJson.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
