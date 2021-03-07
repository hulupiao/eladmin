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
package me.zhengjie.course.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.zhengjie.annotation.Log;
import me.zhengjie.student.domain.Student;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
* @website https://el-admin.vip
* @description /
* @author hulupiao
* @date 2021-03-06
**/
@Entity
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name="qy_course")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Include
    @EqualsAndHashCode.Include
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "name")
    @ToString.Include
    @EqualsAndHashCode.Include
    @ApiModelProperty(value = "name")
    private String name;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "createTime")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "updateTime")
    private Timestamp updateTime;

    @Column(name = "status")
    @ApiModelProperty(value = "status")
    private Integer status;

//    @JSONField(serialize = false)
//    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
//    @ApiModelProperty(value = "student course", hidden = true)
//    private Set<Student> students;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "qy_student_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ApiModelProperty(value = "student course", hidden = true)
    private Set<Student> students;


//    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("course")
//    @JoinTable(name = "qy_lesson")
//    @JoinColumn(name = "course_id")
//    @ApiModelProperty(value = "课时")
//    private Set<Lesson> lessons;

    public void copy(Course source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}