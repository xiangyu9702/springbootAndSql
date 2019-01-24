package com.example.web.student;

import com.example.web.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
学生的数据库方法
getStudentMap：从数据库获取学生信息
deleteStudent：从数据库删除学生信息
saveStudent：保存新的学生信息
updateStudent:更新学生信息
 */
public class StudentDatabase {
    public static Map<Long, Student> getStudentMap() {
        Map<Long, Student> studentMap = new HashMap<>();
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
            sql = "SELECT studentId,studentName,studentSex,student.majorId,majorName FROM student inner join major WHERE major.majorId=student.majorId";
            ResultSet studentRs = stmt.executeQuery(sql);
            while (studentRs.next()) {
                long studentId = studentRs.getLong("studentId");
                String studentName = studentRs.getString("studentName");
                String studentSex = studentRs.getString("studentSex");
                long majorId = studentRs.getLong("majorId");
                String majorName =studentRs.getString("majorName");
                Student s = new Student();
                s.setStudentId(studentId);
                s.setStudentName(studentName);
                s.setStudentSex(studentSex);
                s.setMajorId(majorId);
                s.setMajorName(majorName);
                studentMap.put(studentId,s);
            }
            studentRs.close();
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
        return studentMap;
    }



    public static String deleteStudent(Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "delete from student where studentId=?";
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

    public static String saveStudent(Student student) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
            // 执行查询
            String sql;
            sql = "insert into student values (?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, student.getStudentId());
            stmt.setString(2, student.getStudentName());
            stmt.setString(3, student.getStudentSex());
            stmt.setLong(4, student.getMajorId());
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
    public static String updateStudent( Long id, Student student) {
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
            sql = "update student set studentId=?,studentName=?,studentSex=?,majorId=? where studentId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,student.getStudentId());
            stmt.setString(2,student.getStudentName());
            stmt.setString(3,student.getStudentSex());
            stmt.setLong(4,student.getMajorId());
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
            return "更新详细信息失败";
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
