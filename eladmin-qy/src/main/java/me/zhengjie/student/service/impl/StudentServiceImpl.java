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
package me.zhengjie.student.service.impl;

import me.zhengjie.student.domain.Student;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.student.repository.StudentRepository;
import me.zhengjie.student.service.StudentService;
import me.zhengjie.student.service.dto.StudentDto;
import me.zhengjie.student.service.dto.StudentQueryCriteria;
import me.zhengjie.student.service.mapstruct.StudentMapper;
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
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public Map<String,Object> queryAll(StudentQueryCriteria criteria, Pageable pageable){
        Page<Student> page = studentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(studentMapper::toDto));
    }

    @Override
    public List<StudentDto> queryAll(StudentQueryCriteria criteria){
        return studentMapper.toDto(studentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StudentDto findById(Integer id) {
        Student student = studentRepository.findById(id).orElseGet(Student::new);
        ValidationUtil.isNull(student.getId(),"Student","id",id);
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StudentDto create(Student resources) {
        return studentMapper.toDto(studentRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Student resources) {
        Student student = studentRepository.findById(resources.getId()).orElseGet(Student::new);
        ValidationUtil.isNull( student.getId(),"Student","id",resources.getId());
        student.copy(resources);
        studentRepository.save(student);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            studentRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StudentDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudentDto student : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" name",  student.getName());
            map.put(" createTime",  student.getCreateTime());
            map.put(" updateTime",  student.getUpdateTime());
            map.put(" status",  student.getStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}