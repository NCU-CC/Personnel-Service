package tw.edu.ncu.cc.student.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.cardservice.entity.Faculty
import tw.edu.ncu.cc.student.data.person.FacultyObject

@Component
class Faculty_FacultyObjectConverter implements Converter< Faculty, FacultyObject >{

    @Override
    FacultyObject convert( Faculty source ) {
        new FacultyObject(
                name: source.name,
                type: source.type,
                unit: source.unit,
                title: source.title,
                number: source.id,
                id: source.id
        )
    }

}
