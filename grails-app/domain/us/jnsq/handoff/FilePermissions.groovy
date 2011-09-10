package us.jnsq.handoff

class FilePermissions {
    Actor actor
    File file
    boolean write = false
    boolean read = false
    boolean interact = false
    boolean administrate = false

    static constraints = {
        // for masks
        actor(nullable: true)
        file(nullable: true)
    }
}
