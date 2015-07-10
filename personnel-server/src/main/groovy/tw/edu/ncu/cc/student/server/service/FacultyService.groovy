package tw.edu.ncu.cc.student.server.service

import tw.edu.ncu.cc.location.data.person.Person


interface FacultyService {

    Optional< Person > findByPortalId( String portalId )

}