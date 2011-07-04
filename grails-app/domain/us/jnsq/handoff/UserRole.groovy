package us.jnsq.handoff

import us.jnsq.handoff.security.User

class UserRole {
    User user
    Role role
    String notes

    static constraints = {
    }
    static hasMany = [ fields: UserRoleField ]
}
