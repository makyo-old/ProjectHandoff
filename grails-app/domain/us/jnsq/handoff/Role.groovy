package us.jnsq.handoff

class Role {
    String name
    String description
    FilePermissions permissionsMask

    static mapping = {
        table 'handoff_role'
    }
    static constraints = {
    }
    static hasMany = [ actors: Actor, fields: RoleField ]
}
