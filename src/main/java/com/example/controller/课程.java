package com.example.controller;

import com.example.web.course.Course;
import com.example.web.course.CourseDatabase;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value="/课程")
public class 课程 {
    private Map<Long, Course> courseMap = new HashMap<>();
    public 课程() {
        this.courseMap = CourseDatabase.getCourseMap();
    }
    /*
    course
     */
    @ApiOperation(value = "获取全部课程信息", notes = "根据数据库信息获得信息", position = 1)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Course> importCourse() {
        System.out.println("执行获取全部课程信息成功");
        List<Course> courses = new ArrayList<Course>(courseMap.values());
        return courses;
    }

    @ApiOperation(value = "创建课程", notes = "根据数据库信息创建课程", position = 2)
    @ApiImplicitParam(name = "course", value = "课程详细实体", required = true, dataType = "Course")
    @ApiResponses({
            @ApiResponse(code = 404, message = "无请求路径或页面跳转路径不对")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postCourse(@RequestBody Course course) {
        courseMap.put(course.getCourseId(), course);
        return CourseDatabase.saveCourse(course);
    }

    @ApiOperation(value = "通过id获取课程详细信息", notes = "根据用户的id来获取用户详细信息")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public Course getCourse(@PathVariable Long courseId) {
        System.out.println("执行查询功能");
        return courseMap.get(courseId);
    }

    @ApiOperation(value = "更新课程详细信息", notes = "根据课程的id来指定更新对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "course", value = "课程信息", required = true, dataType = "Course")
    })
    @ApiResponses({
            @ApiResponse(code = 500, message = "错误，可能为不存在数据库的用户id")
    })
    @RequestMapping(value = "/{courseId}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long courseId, @RequestBody Course course) {
        Course t = courseMap.get(courseId);
        t.setCourseId(course.getCourseId());
        t.setCourseName(course.getCourseName());
        t.setTeacherId(course.getTeacherId());
        t.setMajorId(course.getMajorId());
        t.setMajorName(course.getMajorName());
        t.setTeacherName(course.getTeacherName());
        return CourseDatabase.updateCourse(courseId,course);
    }

    @ApiOperation(value = "删除课程", notes = "根据课程的id来指定删除对象")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{courseId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long courseId) {
        courseMap.remove(courseId);
        return CourseDatabase.deleteCourse(courseId);
    }
}
