package us.jnsq.handoff

import grails.plugins.springsecurity.Secured

class RoleController extends grails.plugins.springsecurity.ui.RoleController {

    static defaultAction = 'list'
    
    def springSecurityService
    
    def secCreate = super.create
    
    def secEdit = super.edit
    
    def list = {
        [roles: Role.list([
            max: Math.min(params.max ? params.int('max') : 16, 100),
            offset: params.offset?.toInteger() ?: 0
        ])]
    }
    
    @Secured(['ROLE_ROLE_ADMIN'])
    def create = {
        if (request.method == 'POST') {
            def role = new Role(name: params.name, description: params.description).save(flush: true)
            if (params.numFields.toInteger() > 0) {
                (1..params.numFields.toInteger()).each {
                    def field = new RoleField(
                        role: role,
                        name: params."rolefield-$it-name",
                        description: params."rolefield-$it-description",
                        repeatability: params."rolefield-$it-repeatability",
                        weight: it
                    ).save(flush: true)
                }
            }
            redirect action: 'view', params: [id: role.id]
        }
    }
   
    def view = {
        [role: Role.get(params.id)]
    }
    
    @Secured(['ROLE_USER'])
    def addToSelf = {
        def role = Role.get(params.id)
        def user = springSecurityService.currentUser
        user.addToRoles(role)
        user.save(flush: true)
        redirect action: 'view', id: role.id
    }
}
