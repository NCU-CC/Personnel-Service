package tw.edu.ncu.cc.student.server.service

import groovy.time.TimeCategory
import org.springframework.stereotype.Service

@Service
class AccessProtectServiceImpl implements AccessProtectService {

    private Map< String, List< Date > > errorTable = [:]

    @Override
    boolean isValidKey( String key ) {
        if( errorTable.containsKey( key ) ) {

            List< Date > validRecords = []
            for( Date date : errorTable.get( key ) ) {
                use( TimeCategory ) {
                    if( date.after( new Date() - 1.minutes ) ) {
                        validRecords.add( date )
                    }
                }
            }
            errorTable.put( key, validRecords )
            return validRecords.size() < 5
        } else {
            return true
        }
    }

    @Override
    void registerError( String key ) {
        if( errorTable.containsKey( key ) ) {
            errorTable.get( key ).add( new Date() )
        } else {
            errorTable.put( key, [ new Date() ] )
        }
    }

}
