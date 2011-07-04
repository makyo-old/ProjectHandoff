package us.jnsq.handoff

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
    
    def create = {
        if (request.method == 'POST') {
            def role = new Role(name: params.name, description: params.description).save(flush: true)
            if (params.numFields.toInteger() > 0) {
                (0..<params.numFields.toInteger()).each {
                    def field = new RoleField(
                        role: role,
                        name: params."rolefield-$it-name",
                        description: params."rolefield-$it-description",
                        repeatability: params."rolefield-$it-repeatability"
                    ).save(flush: true)
                }
            }
            redirect action: 'view', params: [id: role.id]
        }
    }
    
    def view = {
        [role: Role.get(params.id)]
    }
    
    def edit = { }
}
