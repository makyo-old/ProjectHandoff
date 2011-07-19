package us.jnsq.handoff

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.access.AccessDeniedException
import us.jnsq.handoff.security.User

class PpaService {

    static transactional = true
    
    def aclPermissionFactory
    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasPermission(#id, 'us.jnsq.handoff.PotentialProjectActor', read) or hasPermission(#id, 'us.jnsq.handoff.PotentialProjectActor', admin)")
    def view(long id) {
        PotentialProjectActor.get(id)
    }
    
    def listForUser() {}
    
    def listForProject() {}
    
    @PreAuthorize("hasPermission(#ppa.project, admin)")
    def approveApplication(PotentialProjectActor ppa) {
        // create actor
        // set PPA as inactive
    }
    
    @PreAuthorize("hasPermission(#ppa.project, admin)")
    def rejectApplication(PotentialProjectActor ppa, String reason) {}
    
    @PreAuthorize("hasPermission(#ppa.project, read)")
    def acceptInvitation(PotentialProjectActor ppa) {}
    
    @PreAuthorize("hasPermission(#ppa.project, read)")
    def declineInvitation(PotentialProjectActor ppa, String reason) {}
    
    def create(Project project, User user, Role role, String notes, String type) {
        def c = PotentialProjectActor.createCriteria().count {
            and {
                eq("project", project)
                eq("user", user)
            }
        }
        if (c > 0) {
            throw new AccessDeniedException()
        }
        def ppa = new PotentialProjectActor(
            project: project,
            user: user,
            role: role,
            notes: notes,
            type: type
        ).save(flush: true)
        addPermission(ppa, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        addPermission(ppa, project.lead.username, BasePermission.ADMINISTRATION)
        project.actors.each {
            addPermission(ppa, it.user.username, BasePermission.READ)
        }
        ppa
    }
    
    void addPermission(PotentialProjectActor ppa, String username, int permission) {
        addPermission(ppa, username, aclPermissionFactory.buildFromMask(permission))
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#ppa, admin)")
    void addPermission(PotentialProjectActor ppa, String username, Permission permission) {
        aclUtilService.addPermission(ppa, username, permission)
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#ppa, admin)")
    void deletePermission(PotentialProjectActor ppa, String username, Permission permission) {
        def acl = aclUtilService.readAcl(ppa)
        acl.entries.eachWithIndex { entry, i ->
            if (entry.sid.equals(username) && entry.permission.equals(permission)) {
                acl.deleteAce(i)
            }
        }
        aclService.updateAcl(acl)
    }
}
