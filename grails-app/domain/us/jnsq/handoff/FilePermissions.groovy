package us.jnsq.handoff

class FilePermissions {
    Actor actor
    File file
    boolean write
    boolean read
    boolean interact
    boolean administrate

    static constraints = {
    }
}
