/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.studentCourse.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.studentCourse.domain.StudentCourse;
import me.zhengjie.studentCourse.service.StudentCourseService;
import me.zhengjie.studentCourse.service.dto.StudentCourseQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author hulupiao
* @date 2021-03-06
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "学生课程管理")
@RequestMapping("/api/studentCourse")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('studentCourse:list')")
    public void download(HttpServletResponse response, StudentCourseQueryCriteria criteria) throws IOException {
        studentCourseService.download(studentCourseService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询学生课程")
    @ApiOperation("查询学生课程")
    @PreAuthorize("@el.check('studentCourse:list')")
    public ResponseEntity<Object> query(StudentCourseQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(studentCourseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增学生课程")
    @ApiOperation("新增学生课程")
    @PreAuthorize("@el.check('studentCourse:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StudentCourse resources){
        return new ResponseEntity<>(studentCourseService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改学生课程")
    @ApiOperation("修改学生课程")
    @PreAuthorize("@el.check('studentCourse:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StudentCourse resources){
        studentCourseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除学生课程")
    @ApiOperation("删除学生课程")
    @PreAuthorize("@el.check('studentCourse:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        studentCourseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}