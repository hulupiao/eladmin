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
package me.zhengjie.studentCourse.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.course.domain.Course;
import me.zhengjie.student.domain.Student;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author hulupiao
* @date 2021-03-06
**/
@Entity
@Getter
@Setter
@Table(name="qy_student_course")
public class StudentCourse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

//    @Column(name = "student_id")
//    @ApiModelProperty(value = "studentId")
//    private Integer studentId;
//
//    @Column(name = "course_id")
//    @ApiModelProperty(value = "courseId")
//    private Integer courseId;

//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @ApiModelProperty(value = "学生")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    @ApiModelProperty(value = "课程")
    private Course course;

    public void copy(StudentCourse source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}