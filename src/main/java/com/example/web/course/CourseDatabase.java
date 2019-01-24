package com.example.web.course;

import com.example.web.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
课程的数据库方法
getCourseMap：从数据库获取课程信息
deleteCourse：从数据库删除课程信息
saveCourse：保存新的课程信息
updateCourse:更新课程信息
 */
public class CourseDatabase {
    public static Map<Long, Course> getCourseMap() {
        Map<Long, Course> courseMap = new HashMap<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT courseId,courseName,course.majorId,majorName,course.teacherId,teacher.teacherName from course inner join major,teacher" +
                    " where major.majorId=course.majorId and course.teacherId=teacher.teacherId";
            ResultSet courseRs = stmt.executeQuery(sql);
            while (courseRs.next()) {
                long courseId = courseRs.getLong("courseId");
                String courseName = courseRs.getString("courseName");
                Long majorId = courseRs.getLong("majorId");
                String majorName = courseRs.getString("majorName");
                Long teacherId=courseRs.getLong("teacherId");
                String teacherName=courseRs.getString("teacherName");
                Course c = new Course();
                c.setCourseId(courseId);
                c.setMajorName(majorName);
                c.setCourseName(courseName);
                c.setMajorId(majorId);
                c.setTeacherName(teacherName);
                c.setTeacherId(teacherId);
                courseMap.put(courseId,c);
            }
            courseRs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return courseMap;
    }

    public static String deleteCourse(Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "delete from course where courseId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
            return "失败";
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return "删除成功";
    }
    public static String saveCourse(Course course) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "insert into course values (?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());
            stmt.setLong(3, course.getMajorId());
            stmt.setLong(4,course.getTeacherId());
            try {
                stmt.executeUpdate();
            }
            catch (SQLIntegrityConstraintViolationException e){
                return "保存失败";
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
            return "失败";
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return "保存成功";
    }

    public static String updateCourse(Long id, Course course) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL,Database.USER,Database.PASS);
            // 执行
            System.out.println("执行更新功能");
            String sql;
            sql = "update course set courseId=?,courseName=?,majorId=?,teacherId=? where courseId =?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,course.getCourseId());
            stmt.setString(2,course.getCourseName());
            stmt.setLong(3,course.getCourseId());
            stmt.setLong(4,course.getTeacherId());
            stmt.setLong(5,id);
            stmt.executeUpdate();
            // 完成后关闭
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
            return "失败";
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return "更新信息成功";
    }
}

