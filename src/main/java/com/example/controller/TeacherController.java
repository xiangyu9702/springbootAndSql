package com.example.controller;

import com.example.web.teacher.Teacher;
import com.example.web.teacher.TeacherDatabase;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping(value="/教师")
public class TeacherController {
    private Map<Long, Teacher> teacherMap = new HashMap<>();

    public TeacherController() {
        this.teacherMap = TeacherDatabase.getTeacherMap();
    }

    /*
    Teacher
     */
    @ApiOperation(value = "获取全部教师信息", notes = "根据数据库信息获得信息", position = 1)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Teacher> importTeacher() {
        System.out.println("执行获取全部教师信息成功");
        List<Teacher> teachers = new ArrayList<Teacher>(teacherMap.values());
        return teachers;
    }

    @ApiOperation(value = "创建教师", notes = "根据数据库信息创建教师", position = 2)
    @ApiImplicitParam(name = "teacher", value = "教师详细实体", required = true, dataType = "Teacher")
    @ApiResponses({
            @ApiResponse(code = 404, message = "无请求路径或页面跳转路径不对")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postTeacher(@RequestBody Teacher teacher) {
        teacherMap.put(teacher.getTeacherId(), teacher);
        return TeacherDatabase.saveTeacher(teacher);
    }

    @ApiOperation(value = "通过id获取教师详细信息", notes = "根据用户的id来获取用户详细信息")
    @ApiImplicitParam(name = "teacherId", value = "教师id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{teacherId}", method = RequestMethod.GET)
    public Teacher getTeacher(@PathVariable Long teacherId) {
        System.out.println("执行查询用户功能");
        return teacherMap.get(teacherId);
    }

    @ApiOperation(value = "更新教师详细信息", notes = "根据教师的id来指定更新对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "teacher", value = "教师信息", required = true, dataType = "Teacher")
    })
    @ApiResponses({
            @ApiResponse(code = 500, message = "错误，可能为不存在数据库的用户id")
    })
    @RequestMapping(value = "/{teacherId}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long teacherId, @RequestBody Teacher teacher) {
        Teacher t = teacherMap.get(teacherId);
        t.setInstituteName(teacher.getInstituteName());
        t.setInstituteId(teacher.getInstituteId());
        t.setTeacherSex(teacher.getTeacherSex());
        t.setTeacherId(teacher.getTeacherId());
        t.setTeacherName(teacher.getTeacherName());
        return TeacherDatabase.updateTeacher(teacherId,teacher);
    }

    @ApiOperation(value = "删除教师", notes = "根据教师的id来指定删除对象")
    @ApiImplicitParam(name = "teacherId", value = "教师id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{teacherId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long teacherId) {
        teacherMap.remove(teacherId);
        return TeacherDatabase.deleteTeacher(teacherId);
    }
}