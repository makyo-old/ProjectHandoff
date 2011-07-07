package us.jnsq.handoff

class Interaction {
    FileVersion fileVersion
    Date dateCreated
    String notes
    String visibility
    Actor from
    Actor to
    Actor intermediary

    static constraints = {
    }
    static hasMany = [ fields: InteractionRoleField ]
}
