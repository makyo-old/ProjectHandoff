package us.jnsq.handoff

class PermissionService {

    static transactional = true

    /*
     * Checks the given permission on the given file for the given actor
     */
    def hasPermission(Actor actor, String permission, File file = null) {
        def fp = FilePermissions.findByFileAndActor(file, actor) ?: [read: false, write: false, interact: false, administrate: false]
        def rp = actor.role.permissionMask ?: [read: false, write: false, interact: false, administrate: false]
        
        (true && (rp."$permission" || rp.administrate) && (fp."$permission" || fp.administrate))
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
