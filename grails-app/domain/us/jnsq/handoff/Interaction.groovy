package us.jnsq.handoff

class Interaction {
    File file
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
