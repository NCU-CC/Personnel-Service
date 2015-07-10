package tw.edu.ncu.cc.student.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.cardservice.entity.Faculty
import tw.edu.ncu.cc.cardservice.enums.PersonType
import tw.edu.ncu.cc.location.data.person.Person
import tw.edu.ncu.cc.student.data.person.FacultyObject

@Component
class Person_FacultyObjectConverter implements Converter< Person, FacultyObject >{

    @Override
    FacultyObject convert( Person source ) {
        new FacultyObject(
                name: source.chineseName,
                type: PersonType.FACULTY,
                unit: source.primaryUnit.chineseName + " " + source.secondaryUnit.chineseName,
                title: source.title
        )
    }

}
