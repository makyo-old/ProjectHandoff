import grails.util.Environment
import us.jnsq.handoff.security.*

class BootStrap {
    
    def springSecurityService

    def init = { servletContext ->
        if (Environment.current != Environment.PRODUCTION) {
            def adminAuthority = new Authority(authority: 'ROLE_ADMIN').save(flush: true)
            def userAuthority = new Authority(authority: 'ROLE_USER').save(flush: true)
            
            String password = springSecurityService.encodePassword('god')
            def testUser = new User(username: 'god', enabled: true, password: password)
            testUser.save(flush: true)
            
            UserAuthority.create testUser, adminAuthority, true
        }
    }
    def destroy = {
    }
}
