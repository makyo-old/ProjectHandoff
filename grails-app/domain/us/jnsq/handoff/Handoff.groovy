package us.jnsq.handoff

class Handoff {
    FileVersion fileVersion
    Date dateCreated
    String notes
    String visibility
    Actor from
    Actor to

    static constraints = {
    }
    static hasMany = [ fields: HandoffRoleField ]
}
