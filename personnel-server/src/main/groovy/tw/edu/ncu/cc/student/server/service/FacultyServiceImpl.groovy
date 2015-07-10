package tw.edu.ncu.cc.student.server.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException
import tw.edu.ncu.cc.lib.http.client.HttpClientSpring
import tw.edu.ncu.cc.location.data.person.Person

@Service
class FacultyServiceImpl implements FacultyService {

    @Value( '${custom.connection.location.server}' )
    def String locationServerAddress

    @Override
    Optional< Person > findByPortalId( String portalId ) {
        try {
            Person person = HttpClientSpring.connect( locationServerAddress + "/management/v1/faculties/{portalId}" )
                    .variables( portalId )
                    .get( Person.class )
            Optional.of( person )
        } catch ( HttpServerErrorException e ) {
            if( e.statusCode == HttpStatus.NOT_FOUND ) {
                Optional.empty()
            } else {
                throw e
            }
        }
    }

}
