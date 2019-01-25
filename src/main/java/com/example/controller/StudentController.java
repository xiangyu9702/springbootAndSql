package com.example.controller;

import com.example.web.student.Student;
import com.example.web.student.StudentDatabase;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/学生")
public class StudentController {
    private Map<Long, Student> studentMap = new HashMap<>();
    public StudentController() {
        this.studentMap = StudentDatabase.getStudentMap();
    }
    /*
    student
     */
    @ApiOperation(value = "获取全部学生信息", notes = "根据数据库信息获得信息", position = 1)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Student> importStudent() {
        System.out.println("执行获取全部学生信息成功");
        List<Student> students = new ArrayList<Student>(studentMap.values());
        return students;
    }

    @ApiOperation(value = "创建学生", notes = "根据数据库信息创建学生", position = 2)
    @ApiImplicitParam(name = "student", value = "学生详细实体", required = true, dataType = "Student")
    @ApiResponses({
            @ApiResponse(code = 404, message = "无请求路径或页面跳转路径不对")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postStudent(@RequestBody Student student) {
        studentMap.put(student.getStudentId(), student);
        return StudentDatabase.saveStudent(student);
    }

    @ApiOperation(value = "通过id获取学生详细信息", notes = "根据用户的id来获取用户详细信息")
    @ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    public Student getStudent(@PathVariable Long studentId) {
        System.out.println("执行查询功能");
        return studentMap.get(studentId);
    }

    @ApiOperation(value = "更新学生详细信息", notes = "根据学生的id来指定更新对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "student", value = "学生信息", required = true, dataType = "Student")
    })
    @ApiResponses({
            @ApiResponse(code = 500, message = "错误，可能为不存在数据库的用户id")
    })
    @RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long studentId, @RequestBody Student student) {
        Student t = studentMap.get(studentId);
        t.setStudentId(student.getStudentId());
        t.setStudentName(student.getStudentName());
        t.setStudentSex(student.getStudentSex());
        t.setMajorId(student.getMajorId());
        t.setMajorName(student.getMajorName());
        return StudentDatabase.updateStudent(studentId,student);
    }

    @ApiOperation(value = "删除学生", notes = "根据学生的id来指定删除对象")
    @ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{studentId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long studentId) {
        studentMap.remove(studentId);
        return StudentDatabase.deleteStudent(studentId);
    }
}
