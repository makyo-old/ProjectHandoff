package us.jnsq.handoff

class Project {
    String name
    String description
    String visibility = "all"
    String joinMethod = "Invite only"
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
