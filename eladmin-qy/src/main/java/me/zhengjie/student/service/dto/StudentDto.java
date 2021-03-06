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
package me.zhengjie.student.service.dto;

import lombok.Data;
import me.zhengjie.course.domain.Course;
import me.zhengjie.course.service.dto.CourseDto;

import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Set;

/**
* @website https://el-admin.vip
* @description /
* @author hulupiao
* @date 2021-03-06
**/
@Data
public class StudentDto implements Serializable {

    private Integer id;

    private String name;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer status;

    private Set<CourseDto> courses;
}