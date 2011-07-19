package us.jnsq.handoff

import us.jnsq.handoff.security.User

class PotentialProjectActor {
    User user
    Role role
    Project project
    String notes
    String type
    boolean active = true

    static constraints = {
        type blank: false, inList: ["invitation", "application"]
    }
}
