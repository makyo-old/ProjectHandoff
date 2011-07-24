package us.jnsq.handoff

import us.jnsq.handoff.security.User

class Actor {
    Project project
    User user
    Role role
    boolean active = true

    static constraints = {
    }
    static hasMany = [ 
        interactionsTo: Interaction,
        interactionsFrom: Interaction,
        interactionsIntermediated: Interaction,
        files: File
    ]
    static mappedBy = [
        interactionsTo: "to",
        interactionsFrom: "from",
        interactionsIntermediated: "intermediary"
    ]
}
