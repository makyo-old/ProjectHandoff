package us.jnsq.handoff

import us.jnsq.handoff.security.User

class Actor {
    Project project
    User user
    Role role

    static constraints = {
    }
    static hasMany = [ 
        handoffsTo: Handoff,
        handoffsFrom: Handoff,
        files: File
    ]
    static mappedBy = [
        handoffsTo: "to",
        handoffsFrom: "from"
    ]
}
