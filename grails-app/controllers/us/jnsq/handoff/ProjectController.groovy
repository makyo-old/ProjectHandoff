package us.jnsq.handoff

import grails.plugins.springsecurity.Secured

class ProjectController {
    
    static defaultAction = 'list'
    
    def projectService

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 16, 100)
        [projects: projectService.list(params)]
    }
    
    @Secured(['ROLE_USER'])
    def view = { 
        [project: projectService.view(params.id)]
    }
    
    @Secured(['ROLE_USER'])
    def invite = { }
    
    @Secured(['ROLE_USER'])
    def apply = { }
    
    @Secured(['ROLE_USER'])
    def join = { }
    
    @Secured(['ROLE_USER'])
    def leave = { }
    
    @Secured(['ROLE_USER'])
    def eject = { }
    
    @Secured(['ROLE_USER'])
    def create = { }
    
    @Secured(['ROLE_USER'])
    def edit = { }
    
    @Secured(['ROLE_USER'])
    def delete = { }
}
