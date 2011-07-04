package us.jnsq.handoff

class Project {
    String name
    String description
    String visibility

    static constraints = {
    }
    static hasMany = [
        files: File,
        milestones: Milestone,
        actors: Actor,
        desiredRoles: Role
    ]
}
