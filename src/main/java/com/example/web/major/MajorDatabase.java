package com.example.web.major;

import com.example.web.Database;
import com.example.web.course.Course;
import com.example.web.course.CourseDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
专业的数据库方法
getMajorMap：从数据库获取专业信息
deleteMajor：从数据库删除专业信息
saveMajor：保存新的专业信息
updateMajor:更新专业信息
 */
public class MajorDatabase {
    public static Map<Long, Major> getMajorMap() {
        Map<Long, Major> majorMap = new HashMap<>();
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
            sql = "SELECT majorId,majorName,major.instituteId,instituteName FROM major inner join institute where major.instituteId=institute.instituteId";
            ResultSet majorRs = stmt.executeQuery(sql);
            while (majorRs.next()) {
                long majorId = majorRs.getLong("majorId");
                String majorName = majorRs.getString("majorName");
                long instituteId = majorRs.getLong("instituteId");
                String instituteName = majorRs.getString("instituteName");
                Major m = new Major();
                m.setInstituteId(instituteId);
                m.setMajorId(majorId);
                m.setMajorName(majorName);
                m.setInstituteName(instituteName);
                m.setCourseArrayList(new ArrayList<>());
                majorMap.put(majorId, m);
            }
            sql = "SELECT courseId,majorId FROM course ";
            ResultSet courserRs = stmt.executeQuery(sql);
            while (courserRs.next()) {
                long courseId = courserRs.getLong("courseId");
                long majorId = courserRs.getLong("majorId");
                majorMap.get(majorId).getCourseArrayList().add(CourseDatabase.getCourseMap().get(courseId));
            }
            majorRs.close();
            courserRs.close();
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
        return majorMap;
    }
    public static String deleteMajor(Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "delete from major where majorId=?";
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
    public static String saveMajor(Major major) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "insert into major values (?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, major.getMajorId());
            stmt.setString(2, major.getMajorName());
            stmt.setLong(3, major.getInstituteId());
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
    public static String updateMajor( Long id, Major major) {
        Connection conn = null;
        PreparedStatement stmt = null;
        for (Course c:major.getCourseArrayList()){
            CourseDatabase.updateCourse(c.getCourseId(),c);
        }
        try{
            // 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL,Database.USER,Database.PASS);
            // 执行
            System.out.println("执行更新功能");
            String sql;
            sql = "update major set majorId=?,majorName=?,instituteId=? where majorId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,major.getMajorId());
            stmt.setString(2,major.getMajorName());
            stmt.setLong(3,major.getInstituteId());
            stmt.setLong(4,id);
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
        return "更新详细信息成功";
    }
}
