package us.jnsq.handoff

import us.jnsq.handoff.security.User

class Project {
    String name
    String description
    String visibility = "all"
    String joinMethod = "Invite only"
    User lead
    boolean completed = false
    Date dateCreated
    Date lastUpdated

    static constraints = {
        visibility blank: false, inList: ["all", "loggedIn", "desiredRoles", "actors"]
        joinMethod blank: false, inList: ["joinAll", "joinDesired", "applyAll", "applyDesired", "invite", "closed"]
    }
    static hasMany = [
        files: File,
        milestones: Milestone,
        actors: Actor,
        desiredRoles: Role
    ]
}
