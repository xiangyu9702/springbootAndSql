package com.example.controller;

import com.example.web.Database;
import com.example.web.User;
import com.example.web.Worker;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;


@RestController
    @RequestMapping(value="/用户")
    public class 用户 {

    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @ApiOperation(value = "获取全部用户信息", notes = "根据数据库信息创建用户",position = 1)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> importDatabase() {
        System.out.println("执行获取全部用户信息成功");
        List<User> r = new ArrayList<User>(users.values());
        return r;
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户",position = 2)
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @ApiResponses({
            @ApiResponse(code = 404, message = "无请求路径或页面跳转路径不对")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        System.out.println(user.getWorker().getWorkerName());
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL,Database.USER,Database.PASS);
            // 执行
            System.out.println("执行创建用户功能");
            String sql;
            sql ="insert into worker values (?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,user.getWorker().getWorker_id());
            stmt.setString(2,user.getWorker().getWorkerName());
            stmt.setLong(3,user.getId());
            stmt.executeUpdate();
            sql = "insert into user values (?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,user.getId());
            stmt.setString(2,user.getName());
            stmt.setInt(3,user.getAge());
            stmt.executeUpdate();
            // 更新数据库
            // 完成后关闭
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
                return "创建用户失败";
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// nothing
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        update();
        return "创建用户成功";
    }

    @ApiOperation(value = "通过id获取用户详细信息", notes = "根据用户的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        System.out.println("执行查询用户功能");
        return users.get(id);
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据用户的id来指定更新对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User")
    })
    @ApiResponses({
            @ApiResponse(code = 500, message = "错误，可能为不存在数据库的用户id")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @RequestBody User user) {
        User u = users.get(id);
        u.setId(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        u.setWorker(user.getWorker());
        users.put(id, u);
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL,Database.USER,Database.PASS);
            // 执行
            System.out.println("执行更新用户功能");
            String sql;
            sql = "update worker set workerId=?,workerName=? where user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,user.getWorker().getWorker_id());
            stmt.setString(2,user.getWorker().getWorkerName());
            stmt.setLong(3,user.getId());
            stmt.executeUpdate();
            sql = "update user set userId=?,userName=?,userAge=? where userId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,user.getId());
            stmt.setString(2,user.getName());
            stmt.setLong(3,user.getAge());
            stmt.setLong(4,user.getId());
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
        return "更新用户详细信息成功";
    }

    @ApiOperation(value = "删除用户", notes = "根据用户的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL,Database.USER,Database.PASS);
            // 执行
            System.out.println("执行删除用户功能");
            String sql;
            sql = "delete from worker where user_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,id);
            stmt.executeUpdate();//因为外键约束必须先删除worker中的column
            sql = "delete from user where userId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,id);
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
        users.remove(id);
        return "删除用户成功";
    }
    public static void update(){
        Connection conn = null;
        Statement stmt = null;
        try{// 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL,Database.USER,Database.PASS);
            // 执行查询
            System.out.println("更新数据库内容");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT userId,userName,userAge,workerId,workerName FROM user inner join worker on user.userId=worker.user_id";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                long id  = rs.getLong("userId");
                String name = rs.getString("userName");
                int age = rs.getInt("userAge");
                long worker_id=rs.getLong("workerId");
                String workerName=rs.getString("workerName");
                User u = new User();
                u.setName(name);
                u.setId((id));
                u.setAge(age);
                Worker worker=new Worker();
                worker.setWorkerName(workerName);
                worker.setWorker_id(worker_id);
                u.setWorker(worker);
                users.put(u.getId(), u);//给map赋值，导入数据库
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){// 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){// 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){//nothing
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}