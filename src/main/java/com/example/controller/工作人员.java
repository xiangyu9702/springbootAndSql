package com.example.controller;

import com.example.web.Database;
import com.example.web.User;
import com.example.web.Worker;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;


@RestController
@RequestMapping(value="/工作人员")
public class 工作人员 {
    Properties properties = new Properties();
    static Map<Long,Worker> workers = Collections.synchronizedMap(new HashMap<Long, Worker>());
    @ApiOperation(value="获取工作人员列表", notes="")
    @RequestMapping(value={""}, method=RequestMethod.GET)
    public List<Worker> getWorkerList() {
        update();
        System.out.println("执行获取工作人员列表成功");
        List<Worker> r = new ArrayList<Worker>(workers.values());
        return r;
    }

    @ApiOperation(value="创建工作人员", notes="根据工作人员对象创建用户")
    @ApiImplicitParam(name = "worker", value = "用户详细实体工作人员", required = true, dataType = "Worker")
    @ApiResponses({
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="", method=RequestMethod.POST)
    public String postWorker(@RequestBody Worker worker) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL,Database.USER,Database.PASS);
            // 执行
            System.out.println("执行创建工作人员");
            String sql;
            sql = "insert into worker(workerId, workerName) values (?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,worker.getWorker_id());
            stmt.setString(2,worker.getWorkerName());
            stmt.executeUpdate();
            // 展开结果集数据库
            // 完成后关闭
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
            return "创建工作人员失败";
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
        workers.put(worker.getWorker_id(), worker);
        update();
        return "成功";
    }

    @ApiOperation(value="通过工作人员id获取用户详细信息", notes="根据工作人员的id来获取用户详细信息")
    @ApiImplicitParam(name = "worker_id", value = "工作人员id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value="/{worker_id}", method=RequestMethod.GET)
    public Worker getWorker(@PathVariable Long worker_id) {
        System.out.println("执行查询工作人员功能");
        return workers.get(worker_id);
    }


    @ApiOperation(value="更新工作人员详细信息", notes="根据工作人员的id来指定更新对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "worker_id", value = "工作人员id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "worker", value = "用户信息", required = true, dataType = "Worker")
    })
    @ApiResponses({
            @ApiResponse(code=500,message="错误，可能为不存在于数据库的用户id")
    })
    @RequestMapping(value="/{worker_id}", method=RequestMethod.PUT)
    public String putWorker(@PathVariable Long worker_id, @RequestBody Worker worker) {
        Worker w = workers.get(worker_id);
        w.setWorkerName(worker.getWorkerName());
        workers.put(worker_id, w);
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL,Database.USER,Database.PASS);
            // 执行
            System.out.println("执行更新工作人员功能");
            String sql;
            sql = "update worker set workerName=? where workerId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1,w.getWorkerName());
            stmt.setLong(2,worker.getWorker_id());
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
            }//
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        update();
        return "更新工作人员详细信息成功";
    }

    @ApiOperation(value="删除工作人员", notes="根据工作人员的id来指定删除对象")
    @ApiImplicitParam(name = "worker_Id", value = "工作人员ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value="/{worker_Id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long worker_Id) {
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
            sql = "delete from worker where workerId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,worker_Id);
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
        workers.remove(worker_Id);
        update();
        return "删除工作人员成功";
    }
    public static void update(){
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(Database.JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(Database.DB_URL,Database.USER,Database.PASS);
            // 执行查询
            System.out.println("更新数据库内容");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT workerId,workerName from worker";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                long id  = rs.getLong("workerId");
                String name = rs.getString("workerName");
                Worker worker=new Worker();
                worker.setWorkerName(name);
                worker.setWorker_id(id);
                workers.put(id, worker);
            }
            // 完成后关闭
            rs.close();
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
    }
}
