package us.jnsq.handoff

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class RoleController extends grails.plugins.springsecurity.ui.RoleController {

    static defaultAction = 'list'
    
    def secCreate = super.create
    
    def secEdit = super.edit
    
    def list = {
        [roles: Role.list([
            max: Math.min(params.max?.toInteger() ?: 16, 100),
            offset: params.offset?.toInteger() ?: 0
        ])]
    }
    
    @Secured(['ROLE_ROLE_ADMIN'])
    def create = {
        if (request.method == 'POST') {
            def roleField = new Role(name: params.name, description: params.description).save(flush: true)
            if (params.numFields.toInteger() > 0) {
                log.info (0..<params.numFields.toInteger())
                (1..params.numFields.toInteger()).each {
                    def field = new RoleField(
                        role: roleField,
                        name: params."rolefield-$it-name",
                        description: params."rolefield-$it-description",
                        repeatability: params."rolefield-$it-repeatability"
                    ).save(flush: true)
                }
            }
            redirect action: 'view', params: [id: roleField.id]
        }
    }
   
    def view = {
        [role: Role.get(params.id)]
    }
    
   
    @Secured(['ROLE_ROLE_ADMIN'])
    def ajax_editRole = {
        def roleField = Role.get(params.id)
        if (roleField) {
            roleField.properties = params
            roleField.save(flush: true)
            if (roleField.hasErrors()) {
                render([success: false, errors: roleField.errors] as JSON)
            } else {
                render([success: true] as JSON)
            }
        }
    }
    
    @Secured(['ROLE_ROLE_ADMIN'])
    def ajax_deleteRole = {
        def roleField = Role.get(params.id)
        if (roleField) {
            roleField.delete(flush: true)
            roleField = Role.exists(params.id)
            if (roleField) {
                render([success: false] as JSON)
            } else {
                flash.message = "Role successfully deleted."
                render([success: true] as JSON)
            }
        }
    }
    
    @Secured(['ROLE_ROLE_ADMIN'])
    def ajax_editField = {
        def roleField = RoleField.get(params.id)
        if (roleField) {
            roleField.properties = params
            roleField.save(flush: true)
            if (roleField.hasErrors()) {
                render(roleField.errors as JSON)
            } else {
                render([success: true] as JSON)
            }
        }
    }
    
    @Secured(['ROLE_ROLE_ADMIN'])
    def ajax_deleteRoleField = {
        def roleField = RoleField.get(params.id)
        if (roleField) {
            roleField.delete(flush: true)
            roleField = RoleField.exists(params.id)
            if (roleField) {
                render([success: false] as JSON)
            } else {
                render([success: true] as JSON)
            }
        }
    }
}
