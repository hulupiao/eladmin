package me.zhengjie;

import me.zhengjie.student.domain.Student;
import me.zhengjie.student.service.dto.StudentDto;
import me.zhengjie.student.service.mapstruct.StudentMapper;
import me.zhengjie.student.service.mapstruct.StudentMapperImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTest {

    @Resource
    private StudentMapperImpl studentSerivce;

    @Test
    public void student2Dto(){
        Student student = new Student();
        student.setName("test");
        String name = student.getName();
        System.out.print( name.equals( "test" ) );
    }
}
