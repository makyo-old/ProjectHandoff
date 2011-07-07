package us.jnsq.handoff

class PermissionService {

    static transactional = true

    /*
     * Checks the given permission on the given file for the given actors (if provided)
     */
    def hasPermission(Map actors = [to: null, from: null, intermediary: null], String permission, File file = null) {
        // Is this cool, a hack, or both?
        def mask = [read: true, write: true, interact: true]
        def inverseMask = [read: false, write: false, interact: false]
        
        // to = mask && ((to.permissions && to.role.permissions) || to.permissions || false)
        def to = (file == null ? mask : (actors.to == null ? mask : (file.permissions.find { it.actor.id == to.id } ?: inverseMask)))
        to = (actors.to == null 
            ? [read: to.read && mask.read, write: to.write && mask.write, interact: to.interact && mask.interact] 
            : [read: to.read && to.role.permissionsMask.read, write: to.write && to.role.permissionsMask.write, interact: to.interact && to.role.permissionsMask.interact])
        
        def from = (file == null ? mask : (actors.from == null ? mask : (file.permissions.find { it.actor.id == from.id } ?: inverseMask)))
        from = (actors.from == null 
            ? [read: from.read && mask.read, write: from.write && mask.write, interact: from.interact && mask.interact] 
            : [read: from.read && from.role.permissionsMask.read, write: from.write && from.role.permissionsMask.write, interact: from.interact && from.role.permissionsMask.interact])
        
        def interact = (file == null ? mask : (actors.interact == null ? mask : (file.permissions.find { it.actor.id == interact.id } ?: inverseMask)))
        interact = (actors.interact == null 
            ? [read: interact.read && mask.read, write: interact.write && mask.write, interact: interact.interact && mask.interact] 
            : [read: interact.read && interact.role.permissionsMask.read, write: interact.write && interact.role.permissionsMask.write, interact: interact.interact && interact.role.permissionsMask.interact])
        
        to."$permission" && from."$permission" && intermediary."$permission"
    }
}
