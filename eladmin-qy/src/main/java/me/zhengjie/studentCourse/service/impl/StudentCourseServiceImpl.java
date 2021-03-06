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
package me.zhengjie.studentCourse.service.impl;

import me.zhengjie.studentCourse.domain.StudentCourse;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.studentCourse.repository.StudentCourseRepository;
import me.zhengjie.studentCourse.service.StudentCourseService;
import me.zhengjie.studentCourse.service.dto.StudentCourseDto;
import me.zhengjie.studentCourse.service.dto.StudentCourseQueryCriteria;
import me.zhengjie.studentCourse.service.mapstruct.StudentCourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author hulupiao
* @date 2021-03-06
**/
@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentCourseMapper studentCourseMapper;

    @Override
    public Map<String,Object> queryAll(StudentCourseQueryCriteria criteria, Pageable pageable){
        Page<StudentCourse> page = studentCourseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(studentCourseMapper::toDto));
    }

    @Override
    public List<StudentCourseDto> queryAll(StudentCourseQueryCriteria criteria){
        return studentCourseMapper.toDto(studentCourseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StudentCourseDto findById(Integer id) {
        StudentCourse studentCourse = studentCourseRepository.findById(id).orElseGet(StudentCourse::new);
        ValidationUtil.isNull(studentCourse.getId(),"StudentCourse","id",id);
        return studentCourseMapper.toDto(studentCourse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StudentCourseDto create(StudentCourse resources) {
        return studentCourseMapper.toDto(studentCourseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudentCourse resources) {
        StudentCourse studentCourse = studentCourseRepository.findById(resources.getId()).orElseGet(StudentCourse::new);
        ValidationUtil.isNull( studentCourse.getId(),"StudentCourse","id",resources.getId());
        studentCourse.copy(resources);
        studentCourseRepository.save(studentCourse);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            studentCourseRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StudentCourseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudentCourseDto studentCourse : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" studentId",  studentCourse.getStudentId());
            map.put(" courseId",  studentCourse.getCourseId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}