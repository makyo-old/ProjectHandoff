package us.jnsq.handoff

class PotentialProjectActor {
    User user
    Role role
    Project project
    String notes
    String type

    static constraints = {
        type blank: false, inList: ["Invitation", "Application"]
    }
}
