package com.example.web.Institute;

import com.example.web.Database;
import com.example.web.course.Course;
import com.example.web.major.Major;
import com.example.web.major.MajorDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
学院的数据库方法
getInstituteMap：从数据库获取学院信息
deleteInstitute：从数据库删除学院信息
saveInstitute：保存新的学院信息
updateInstitute:更新学院信息
 */
public class InstituteDatabase {
    public static Map<Long,Institute> getInstituteMap() {
        Map<Long,Institute> instituteMap=new HashMap<>();
        Map<Long,Major> majorMap=new HashMap<>();
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
            sql = "SELECT instituteId,instituteName,numberOfMajor FROM institute ";
            ResultSet instituteRs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (instituteRs.next()) {
                long instituteId = instituteRs.getLong("instituteId");
                String instituteName = instituteRs.getString("instituteName");
                int numberOfMajor = instituteRs.getInt("numberOfMajor");
                Institute i = new Institute();
                i.setInstituteName(instituteName);
                i.setInstituteId((instituteId));
                i.setNumberOfMajor(numberOfMajor);
                i.setMajorArrayList(new ArrayList<>());
                instituteMap.put(instituteId,i);
            }
            sql = "SELECT majorId,instituteId FROM major";
            ResultSet majorRs = stmt.executeQuery(sql);
            while (majorRs.next()) {
                long majorId = majorRs.getLong("majorId");
                long instituteId = majorRs.getLong("instituteId");
                instituteMap.get(instituteId).getMajorArrayList().add(MajorDatabase.getMajorMap().get(majorId));
            }
            majorRs.close();
            instituteRs.close();
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
        return instituteMap;
    }

    public static String saveInstitute(Institute Institute) {
        Map<Long, Institute> userMap=new HashMap<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "insert into institute values (?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Institute.getInstituteId());
            stmt.setString(2, Institute.getInstituteName());
            stmt.setInt(3, Institute.getNumberOfMajor());
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
        return "保持成功";
    }
    public static String deleteInstitute(Long id) {
        Map<Long,Institute> userMap=new HashMap<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "delete from institute where instituteId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,id);
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

    public static String updateInstitute( Long id, Institute institute) {
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
            sql = "update institute set instituteId=?,instituteName=?,numberOfMajor=? where instituteId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,institute.getInstituteId());
            stmt.setString(2,institute.getInstituteName());
            stmt.setLong(3,institute.getNumberOfMajor());
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
