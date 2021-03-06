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
package me.zhengjie.lesson.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.lesson.domain.Lesson;
import me.zhengjie.lesson.service.LessonService;
import me.zhengjie.lesson.service.dto.LessonQueryCriteria;
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
* @date 2021-03-07
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "课程管理")
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonService lessonService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('lesson:list')")
    public void download(HttpServletResponse response, LessonQueryCriteria criteria) throws IOException {
        lessonService.download(lessonService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询课程")
    @ApiOperation("查询课程")
    @PreAuthorize("@el.check('lesson:list')")
    public ResponseEntity<Object> query(LessonQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(lessonService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增课程")
    @ApiOperation("新增课程")
    @PreAuthorize("@el.check('lesson:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Lesson resources){
        return new ResponseEntity<>(lessonService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改课程")
    @ApiOperation("修改课程")
    @PreAuthorize("@el.check('lesson:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Lesson resources){
        lessonService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除课程")
    @ApiOperation("删除课程")
    @PreAuthorize("@el.check('lesson:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        lessonService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}