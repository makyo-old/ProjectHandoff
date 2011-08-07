package us.jnsq.handoff

class ProjectTagLib {
    static namespace = "handoff"
    
    def springSecurityService
    
    def joinOrApplyButton = { attrs, body ->
        def project = attrs.project
        def user = springSecurityService.currentUser
        
        if (!(project in user.actors.collect { it.project })) {
            if (project.joinMethod == 'joinAll' || 
                (project.joinMethod == 'joinDesired' && 
                    !project.desiredRoles.collect { it.id }.disjoint(user.roles.collect { it.id })
                )) {
                def roles = project.joinMethod == 'joinAll' ? user.roles : user.roles.intersect(project.desiredRoles)
                out << g.render(template: 'joinButton', model: [roles: roles, projectID: project.id])
            } else if (project.joinMethod == 'applyAll' ||
                (project.joinMethod == 'applyDesired' &&
                    !project.desiredRoles.collect { it.id }.disjoint(user.roles.collect { it.id })
                )) {
                def roles = project.joinMethod == 'applyAll' ? user.roles : user.roles.intersect(project.desiredRoles)
                out << g.render(template: 'applyButton', model: [projectID: project.id])
            }
        } else {
            out << "<em>$attrs.alreadyIn</em>"
        }
    }
}
