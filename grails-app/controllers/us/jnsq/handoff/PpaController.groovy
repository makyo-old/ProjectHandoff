package us.jnsq.handoff

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class PpaController {

    def ppaService
    
    def view = {
        [ppa: ppaService.view(params.id)]
    }
    
    def user = {
        render view: 'list', model: [list: ppaService.listForUser(User.findByUsername(params.id))]
    }
    
    def project = {
        render view: 'list', model: [list: ppaService.listForProject(Project.get(params.id))]
    }
    
    def approveApplication = {
        def ppa = PotentialProjectActor.get(params.id)
        def actor = ppaService.approveApplication(ppa, params.note)
        redirect controller: 'actor', action: 'view', id: actor.id
    }
    
    def rejectApplication = {
        def ppa = PotentialProjectActor.get(params.id)
        ppaService.rejectApplication(ppa, params.note)
        redirect action: 'view', id: params.id
    }
    
    def acceptInvitation = {
        def ppa = PotentialProjectActor.get(params.id)
        def actor = ppaService.approveApplication(ppa, params.note)
        redirect controller: 'actor', action: 'view', id: actor.id
    }
    
    def declineInvitation = {
        def ppa = PotentialProjectActor.get(params.id)
        ppaService.declineInvitation(ppa, params.note)
        redirect action: 'view', id: params.id
    }
    
    def create = {
        def project = Project.get(params.project.id)
        def user = User.findByUsername(params.user.username)
        def role = Role.get(params.role.id)
        redirect action: 'view', id: ppaService.create(project, user, role, params.notes, params.type)
    }
}
