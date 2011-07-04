package us.jnsq.handoff.security

import org.apache.commons.lang.builder.HashCodeBuilder

class UserAuthority implements Serializable {

    User user
    Authority role

    boolean equals(other) {
        if (!(other instanceof UserAuthority)) {
            return false
        }

        other.user?.id == user?.id &&
        other.role?.id == role?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (user) builder.append(user.id)
        if (role) builder.append(role.id)
        builder.toHashCode()
    }

    static UserAuthority get(long userId, long roleId) {
        find 'from UserAuthority where user.id=:userId and role.id=:roleId',
        [userId: userId, roleId: roleId]
    }

    static UserAuthority create(User user, Authority role, boolean flush = false) {
        new UserAuthority(user: user, role: role).save(flush: flush, insert: true)
    }

    static boolean remove(User user, Authority role, boolean flush = false) {
        UserAuthority instance = UserAuthority.findByUserAndAuthority(user, role)
        instance ? instance.delete(flush: flush) : false
    }

    static void removeAll(User user) {
        executeUpdate 'DELETE FROM UserAuthority WHERE user=:user', [user: user]
    }

    static void removeAll(Authority role) {
        executeUpdate 'DELETE FROM UserAuthority WHERE role=:role', [role: role]
    }

    static mapping = {
        id composite: ['role', 'user']
        version false
    }
}
