package tw.edu.ncu.cc.student.server.service


interface AccessProtectService {

    public boolean isValidKey( String key )

    public void registerError( String key )

}