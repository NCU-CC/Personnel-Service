package tw.edu.ncu.cc.student.server.service

import groovy.time.TimeCategory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.oauth.data.v1.management.user.UserRestrictedIdObject
import tw.edu.ncu.cc.oauth.resource.service.BlackListService

@Service
class AccessProtectServiceImpl implements AccessProtectService {

    private Map< String, List< Date > > errorTable = [:]

    @Autowired
    def BlackListService blackListService

    @Override
    void registerError( String key ) {
        if( errorTable.containsKey( key ) ) {
            errorTable.get( key ).add( new Date() )
            trimErrorTable( key )
            if( errorTable.get( key ).size() > 5 ) {
                blackListService.addUser( new UserRestrictedIdObject(
                        user_name: key,
                        reason: 'authentication error too many times'
                ) )
            }
        } else {
            errorTable.put( key, [ new Date() ] )
        }
    }

    private void trimErrorTable( String key ) {
        List< Date > validRecords = []
        for( Date date : errorTable.get( key ) ) {
            use( TimeCategory ) {
                if( date.after( new Date() - 1.minutes ) ) {
                    validRecords.add( date )
                }
            }
        }
        errorTable.put( key, validRecords )
    }

}
