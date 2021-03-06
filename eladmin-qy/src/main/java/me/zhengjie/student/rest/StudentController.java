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
package me.zhengjie.student.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.student.domain.Student;
import me.zhengjie.student.service.StudentService;
import me.zhengjie.student.service.dto.StudentQueryCriteria;
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
@Api(tags = "学生管理")
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('student:list')")
    public void download(HttpServletResponse response, StudentQueryCriteria criteria) throws IOException {
        studentService.download(studentService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询学生")
    @ApiOperation("查询学生")
    @PreAuthorize("@el.check('student:list')")
    public ResponseEntity<Object> query(StudentQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(studentService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增学生")
    @ApiOperation("新增学生")
    @PreAuthorize("@el.check('student:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Student resources){
        return new ResponseEntity<>(studentService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改学生")
    @ApiOperation("修改学生")
    @PreAuthorize("@el.check('student:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Student resources){
        studentService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除学生")
    @ApiOperation("删除学生")
    @PreAuthorize("@el.check('student:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        studentService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}