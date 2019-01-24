package com.example.web.teacher;

import com.example.web.Database;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
/*
教师的数据库方法
getTeacherMap：从数据库获取教师信息
deleteTeacher：从数据库删除教师信息
saveTeacher：保存新的教师信息
updateTeacher:更新教师信息
 */
public class TeacherDatabase {
    public static Map getTeacherMap() {
        Map<Long, Teacher> teacherMap = new HashMap<>();
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
            sql = "SELECT teacherId,teacherName,teacherSex,teacher.instituteId,instituteName from teacher inner join institute where teacher.instituteId=institute.instituteId";
            ResultSet teacherRs = stmt.executeQuery(sql);
            while (teacherRs.next()) {
                long teacherId = teacherRs.getLong("teacherId");
                String teacherName = teacherRs.getString("teacherName");
                long instituteId = teacherRs.getLong("instituteId");
                String teacherSex = teacherRs.getString("teacherSex");
                String instituteName = teacherRs.getString("instituteName");
                Teacher teacher = new Teacher();
                teacher.setTeacherId(teacherId);
                teacher.setTeacherName(teacherName);
                teacher.setTeacherSex(teacherSex);
                teacher.setInstituteId(instituteId);
                teacher.setInstituteName(instituteName);
                teacherMap.put(teacherId, teacher);
            }
            teacherRs.close();
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
        return teacherMap;
    }

    public static String deleteTeacher(Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "delete from teacher where teacherId=?";
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

    public static String saveTeacher(Teacher teacher) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "insert into teacher values (?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, teacher.getTeacherId());
            stmt.setString(2, teacher.getTeacherName());
            stmt.setString(3, teacher.getTeacherSex());
            stmt.setLong(4, teacher.getInstituteId());
            try {
                stmt.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
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

    public static String updateTeacher(Long id, Teacher teacher) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行
            System.out.println("执行更新功能");
            String sql;
            sql = "update teacher set teacherId=?,teacherName=?,teacherSex=?,instituteId=? where teacherId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, teacher.getTeacherId());
            stmt.setString(2, teacher.getTeacherName());
            stmt.setString(3, teacher.getTeacherSex());
            stmt.setLong(4, teacher.getInstituteId());
            stmt.setLong(5, id);
            stmt.executeUpdate();
            // 完成后关闭
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
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return "更新用户详细信息成功";
    }
}
