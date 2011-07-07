package us.jnsq.handoff

class File {
    Project project
    String name
    Actor currentOwner

    static constraints = {
    }
    static hasMany = [
        permissions: FilePermissions,
        versions: FileVersion,
        interactions: Interaction
    ]
}
