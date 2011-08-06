package us.jnsq.handoff

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class ProjectController {
    
    static defaultAction = 'list'
    
    def projectService

    @Secured(['ROLE_USER', 'ROLE_ANONYMOUS'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 16, 100)
        [projects: projectService.list(params)]
    }
    
    def view = { 
        [project: projectService.view(params.int('id'))]
    }
    
    def invite = {
        def project = Project.get(params.project.id)
        def user = User.findByUsername(params.user.username)
        def role = Role.get(params.role.id)
        if (project && role && user) {
            def ppa = projectService.invite(project, user, role, params.notes)
            redirect controller: 'ppa', action: 'view', id: ppa.id
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
            redirect controller: 'ppa', action: 'view', id: ppa.id
        } else {
            response.sendError(404)
        }
    }
    
    def join = {
        def project = Project.get(params.id)
        def role = Role.get(params.role.id)
        if (project && role) {
            projectService.join(project, role)
            redirect action: 'view', id: params.id
        } else {
            response.sendError(404)
        }
    }
    
    def leave = {
        def project = Project.get(params.id)
        if (!project) {
            response.sendError(404)
        }
        projectService.leave(project)
        redirect action: 'list'
    }
    
    def eject = {
        def project = Project.get(params.project.id)
        def user = User.findByUsername(params.user.username)
        if (project && user) {
            def actor = Actor.findByUserAndProject(user, project)
            projectService.eject(project, actor)
            redirect action: 'view', id: project.id
        } else {
            response.sendError(404)
        }
    }
    
    def create = {}
    
    def edit = {
        [project: Project.get(params.id)]
    }
    
    def delete = {
        def project = Project.get(params.id)
        projectService.delete(project)
        redirect action: 'list'
    }

    def save = {
        log.info "saving..."
        if (params.id) {
            projectService.edit(params)
        } else {
            params.id = projectService.create(params).id
        }
        redirect action: 'view', id: params.id
    }
}
