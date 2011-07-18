package us.jnsq.handoff

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class RoleAjaxController {
   
    @Secured(['ROLE_ROLE_ADMIN'])
    def editRole = {
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
    def deleteRole = {
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
    def editRoleField = {
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
    def deleteRoleField = {
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
    
    @Secured(['ROLE_ROLE_ADMIN'])
    def saveRoleFieldSort = {
        def role = Role.get(params.role)
        if (role) {
            (0..<params."field[]".size()).each {
                def roleField = RoleField.get(params."field[]"[it])
                if (roleField) {
                    roleField.weight = it
                    roleField.save()
                }
            }
            role.save(flush: true)
            render([success: true] as JSON)
        } else {
            render([success: false] as JSON)
        }
    }
    
    @Secured(['ROLE_ROLE_ADMIN'])
    def createRoleField = {
        def role = Role.get(params.role.id)
        if (role) {
            def roleField = new RoleField()
            roleField.properties = params
            roleField.role = role
            roleField.save(flush: true)
            if (roleField.hasErrors()) {
                render([success: false, errors: roleField.errors] as JSON)
            } else {
                render([success: true, id: roleField.id] as JSON)
            }
        } else {
            render([success: false] as JSON)
        }
    }
}
