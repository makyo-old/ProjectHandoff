package us.jnsq.handoff

class RoleTagLib {
    static namespace = "handoff"
    
    def springSecurityService
    
    def addToSelfOrNot = { attrs, body ->
        def roleID = attrs.roleID
        def user = springSecurityService.currentUser
        
        if (user.roles.collect { it.id }.contains(roleID)) {
            out << "<em>$attrs.not</em>"
        } else {
            out << body()
        }
    }

}
