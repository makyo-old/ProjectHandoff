package us.jnsq.handoff

class PermissionService {

    static transactional = true

    /*
     * Checks the given permission on the given file for the given actors (if provided)
     * 
     * hasPermission(to: t, from: f, intermediary: i, "interact", f)
     */
    def hasPermission(Map actors = [to: null, from: null, intermediary: null], String permission, File file = null) {
        def fp
        
        // to = mask && to.permissions && to.role.permissions
        fp = FilePermissions.findByFileAndActor(file, actor.to) ?: [read: false, write: false, interact: false]
        def to = [
            read: true && (actors.to == null ? true : (actors.to.role.permissionsMask.read && fp.read)),
            write: true && (actors.to == null ? true : (actors.to.role.permissionsMask.write && fp.write)),
            interact: true && (actors.to == null ? true : (actors.to.role.permissionsMask.interact && fp.interact))
        ]
        
        fp = FilePermissions.findByFileAndActor(file, actor.from) ?: [read: false, write: false, interact: false]
        def from = [
            read: true && (actors.from == null ? true : (actors.from.role.permissionsMask.read && fp.read)),
            write: true && (actors.from == null ? true : (actors.from.role.permissionsMask.write && fp.write)),
            interact: true && (actors.from == null ? true : (actors.from.role.permissionsMask.interact && fp.interact))
        ]
        
        fp = FilePermissions.findByFileAndActor(file, actor.intermediary) ?: [read: false, write: false, interact: false]
        def intermediary = [
            read: true && (actors.intermediary == null ? true : (actors.intermediary.role.permissionsMask.read && fp.read)),
            write: true && (actors.intermediary == null ? true : (actors.intermediary.role.permissionsMask.write && fp.write)),
            interact: true && (actors.intermediary == null ? true : (actors.intermediary.role.permissionsMask.interact && fp.interact))
        ]
        
        to[permission] && from[permission] && intermediary[position]
    }
    
    /*
     * Sets permissions on a given file for a given actor
     * 
     * setPermissions(read: true, write: false, interact: true, actor: a, file: f)
     */
    def setPermissions(Map permissionsActorFile) {
        def permissions = file.permissions.findByActor(actor)
        permissions = (permissions ?: new FilePermissions())
        permissions.properties = permissionsActorFile
        permissions.save(flush: true)
        permissions
    }
}
