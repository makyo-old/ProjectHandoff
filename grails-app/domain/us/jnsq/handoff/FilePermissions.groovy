package us.jnsq.handoff

class FilePermissions {
    Actor actor
    boolean write
    boolean claim
    boolean read
    boolean handoff

    static constraints = {
    }
    static belongsTo = [file: File]
}
