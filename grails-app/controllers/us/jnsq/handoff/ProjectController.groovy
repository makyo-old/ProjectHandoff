package us.jnsq.handoff

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class ProjectController {
    
    static defaultAction = 'list'
    
    def projectService

    @Secured(['ROLE_ANONYMOUS'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 16, 100)
        [projects: projectService.list(params)]
    }
    
    def view = { 
        [project: projectService.view(params.id)]
    }
    
    def invite = {
        def project = Project.get(params.project.id)
        def user = User.findByUsername(params.user.username)
        def role = Role.get(params.role.id)
        if (project && role && user) {
            def ppa = projectService.invite(project, user, role, params.notes)
            redirect action: 'ppa', id: ppa.id
        } else {
            response.sendError(404)
        }
    }
        
    def apply = {
        def project = Project.get(params.project.id)
        def user = User.findByUsername(params.user.username)
        def role = Role.get(params.role.id)
        if (project && user && role) {
            def ppa = projectService.apply(project, user, role, params.notes)
            redirect action: 'ppa', id: ppa.id
        } else {
            response.sendError(404)
        }
    }
    
    def approveApplication = {
        def ppa = PotentialProjectActor.get(params.id)
        projectService.approveApplication(ppa)
        redirect action: 'view', id: ppa.project.id
    }

    def acceptInvitation = {}

    def ppa = { }
    
    def join = { }
    
    def leave = { }
    
    def eject = { }
    
    def create = { }
    
    def edit = { }
    
    def delete = { }
}
