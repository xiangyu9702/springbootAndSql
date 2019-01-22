package com.example.controller;

import com.example.web.Institute.Institute;
import com.example.web.Institute.InstituteDatabase;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value="/学院")
public class 学院 {
    private Map<Long, Institute> instituteMap = new HashMap<>();
    public 学院() {
        this.instituteMap = InstituteDatabase.getInstituteMap();
    }
    /*
    institute
     */
    @ApiOperation(value = "获取全部学院信息", notes = "根据数据库信息获得信息", position = 1)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Institute> importInstitute() {
        System.out.println("执行获取全部学院信息成功");
        List<Institute> institutes = new ArrayList<Institute>(instituteMap.values());
        return institutes;
    }

    @ApiOperation(value = "创建学院", notes = "根据数据库信息创建学院", position = 2)
    @ApiImplicitParam(name = "institute", value = "学院详细实体", required = true, dataType = "Institute")
    @ApiResponses({
            @ApiResponse(code = 404, message = "无请求路径或页面跳转路径不对")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postInstitute(@RequestBody Institute institute) {
        instituteMap.put(institute.getInstituteId(), institute);
        return InstituteDatabase.saveInstitute(institute);
    }

    @ApiOperation(value = "通过id获取学院详细信息", notes = "根据用户的id来获取用户详细信息")
    @ApiImplicitParam(name = "instituteId", value = "学院id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{instituteId}", method = RequestMethod.GET)
    public Institute getInstitute(@PathVariable Long instituteId) {
        System.out.println("执行查询功能");
        return instituteMap.get(instituteId);
    }

    @ApiOperation(value = "更新学院详细信息", notes = "根据学院的id来指定更新对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instituteId", value = "学院id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "institute", value = "学院信息", required = true, dataType = "Institute")
    })
    @ApiResponses({
            @ApiResponse(code = 500, message = "错误，可能为不存在数据库的用户id")
    })
    @RequestMapping(value = "/{instituteId}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long instituteId, @RequestBody Institute institute) {
        Institute t = instituteMap.get(instituteId);
        t.setInstituteId(institute.getInstituteId());
        t.setInstituteName(institute.getInstituteName());
        t.setNumberOfMajor(institute.getNumberOfMajor());
        t.setMajorArrayList(institute.getMajorArrayList());
        return InstituteDatabase.updateInstitute(instituteId,institute);
    }

    @ApiOperation(value = "删除学院", notes = "根据学院的id来指定删除对象")
    @ApiImplicitParam(name = "instituteId", value = "学院id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{instituteId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long instituteId) {
        instituteMap.remove(instituteId);
        return InstituteDatabase.deleteInstitute(instituteId);
    }
}
