package us.jnsq.handoff

class RoleField {
    Role role
    String name
    String description
    String repeatability
    Integer weight

    static constraints = {
    }
    static mapping = {
        sort "weight"
    }
}
