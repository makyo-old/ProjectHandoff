package us.jnsq.handoff

class Project {
    String name
    String description
    String visibility = "Everyone"

    static constraints = {
        visibility blank: false, inList: ["Everyone", "Logged in", "Desired roles", "Actors"]
    }
    static hasMany = [
        files: File,
        milestones: Milestone,
        actors: Actor,
        desiredRoles: Role
    ]
}
