package tw.edu.ncu.cc.student.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.cardservice.entity.Student
import tw.edu.ncu.cc.student.data.person.StudentObject

@Component
class Student_StudentObjectConverter implements Converter< Student, StudentObject > {

    @Override
    StudentObject convert( Student source ) {
        new StudentObject(
                name: source.name,
                type: source.type,
                unit: source.unit,
                group: source.group,
                number: source.no
        )
    }

}
