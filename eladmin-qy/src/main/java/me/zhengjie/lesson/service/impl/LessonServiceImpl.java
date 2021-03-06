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
package me.zhengjie.lesson.service.impl;

import me.zhengjie.lesson.domain.Lesson;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.lesson.repository.LessonRepository;
import me.zhengjie.lesson.service.LessonService;
import me.zhengjie.lesson.service.dto.LessonDto;
import me.zhengjie.lesson.service.dto.LessonQueryCriteria;
import me.zhengjie.lesson.service.mapstruct.LessonMapper;
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
* @date 2021-03-07
**/
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Override
    public Map<String,Object> queryAll(LessonQueryCriteria criteria, Pageable pageable){
        Page<Lesson> page = lessonRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(lessonMapper::toDto));
    }

    @Override
    public List<LessonDto> queryAll(LessonQueryCriteria criteria){
        return lessonMapper.toDto(lessonRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public LessonDto findById(Integer id) {
        Lesson lesson = lessonRepository.findById(id).orElseGet(Lesson::new);
        ValidationUtil.isNull(lesson.getId(),"Lesson","id",id);
        return lessonMapper.toDto(lesson);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LessonDto create(Lesson resources) {
        return lessonMapper.toDto(lessonRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Lesson resources) {
        Lesson lesson = lessonRepository.findById(resources.getId()).orElseGet(Lesson::new);
        ValidationUtil.isNull( lesson.getId(),"Lesson","id",resources.getId());
        lesson.copy(resources);
        lessonRepository.save(lesson);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            lessonRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<LessonDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LessonDto lesson : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" courseId",  lesson.getCourseId());
            map.put(" title",  lesson.getTitle());
            map.put(" content",  lesson.getContent());
            map.put(" createTime",  lesson.getCreateTime());
            map.put(" updateTime",  lesson.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}