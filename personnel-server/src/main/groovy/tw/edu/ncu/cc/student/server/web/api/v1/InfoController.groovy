package tw.edu.ncu.cc.student.server.web.api.v1

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.HttpStatusCodeException
import tw.edu.ncu.cc.cardservice.entity.Student
import tw.edu.ncu.cc.cardservice.service.INCUCardService
import tw.edu.ncu.cc.location.data.person.Person
import tw.edu.ncu.cc.student.data.person.FacultyObject
import tw.edu.ncu.cc.student.data.person.StudentObject
import tw.edu.ncu.cc.student.server.service.FacultyService

@RestController
@RequestMapping( value = "v1/info" )
class InfoController {

    @Autowired
    def INCUCardService cardService

    @Autowired
    def FacultyService facultyService

    @Autowired
    def ConversionService conversionService

    @PreAuthorize( "hasAuthority('user.info.basic.read')" )
    @RequestMapping( method = RequestMethod.GET )
    public Object show( Authentication authentication ) {
        Optional< Student > student = cardService.findStudentByStudentNo( authentication.name )
        if( student.present ) {
            conversionService.convert( student.get(), StudentObject.class )
        } else {
            Optional< Person > person = facultyService.findByPortalId( authentication.name )
            if( person.present ) {
                FacultyObject facultyObject = conversionService.convert( person.get(), FacultyObject.class )
                facultyObject.number = authentication.name
                facultyObject.id     = authentication.name
                facultyObject
            } else {
                throw new HttpServerErrorException( HttpStatus.NOT_FOUND )
            }
        }
    }

}
