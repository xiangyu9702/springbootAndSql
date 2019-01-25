package com.example.controller;

import com.example.web.major.Major;
import com.example.web.major.MajorDatabase;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/专业")
public class MajorController {
    private Map<Long, Major> majorMap = new HashMap<>();
    public MajorController() {
        this.majorMap = MajorDatabase.getMajorMap();
    }
    /*
    major
     */
    @ApiOperation(value = "获取全部专业信息", notes = "根据数据库信息获得信息", position = 1)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Major> importMajor() {
        System.out.println("执行获取全部专业信息成功");
        List<Major> majors = new ArrayList<Major>(majorMap.values());
        return majors;
    }

    @ApiOperation(value = "创建专业", notes = "根据数据库信息创建专业", position = 2)
    @ApiImplicitParam(name = "major", value = "专业详细实体", required = true, dataType = "Major")
    @ApiResponses({
            @ApiResponse(code = 404, message = "无请求路径或页面跳转路径不对")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postMajor(@RequestBody Major major) {
        majorMap.put(major.getMajorId(), major);
        return MajorDatabase.saveMajor(major);
    }

    @ApiOperation(value = "通过id获取专业详细信息", notes = "根据用户的id来获取用户详细信息")
    @ApiImplicitParam(name = "majorId", value = "专业id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{majorId}", method = RequestMethod.GET)
    public Major getMajor(@PathVariable Long majorId) {
        System.out.println("执行查询功能");
        return majorMap.get(majorId);
    }

    @ApiOperation(value = "更新专业详细信息", notes = "根据专业的id来指定更新对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "majorId", value = "专业id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "major", value = "专业信息", required = true, dataType = "Major")
    })
    @ApiResponses({
            @ApiResponse(code = 500, message = "错误，可能为不存在数据库的用户id")
    })
    @RequestMapping(value = "/{majorId}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long majorId, @RequestBody Major major) {
        Major t = majorMap.get(majorId);
        t.setMajorId(major.getMajorId());
        t.setMajorName(major.getMajorName());
        t.setInstituteName(major.getInstituteName());
        t.setInstituteId(major.getInstituteId());
        t.setCourseArrayList(major.getCourseArrayList());
        return MajorDatabase.updateMajor(majorId,major);
    }

    @ApiOperation(value = "删除专业", notes = "根据专业的id来指定删除对象")
    @ApiImplicitParam(name = "majorId", value = "专业id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{majorId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long majorId) {
        majorMap.remove(majorId);
        return MajorDatabase.deleteMajor(majorId);
    }
}
