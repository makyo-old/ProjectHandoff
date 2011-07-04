package us.jnsq.handoff.security

import us.jnsq.handoff.Actor
import us.jnsq.handoff.Role

class User {

    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static constraints = {
        username blank: false, unique: true
        password blank: false
    }

    static mapping = {
        table 'security_user'
        password column: '`password`'
    }
    
    static hasMany = [
        actors: Actor,
        roles: Role
    ]

    Set<Role> getAuthorities() {
        UserAuthority.findAllByUser(this).collect { it.role } as Set
    }
}
