package us.jnsq.handoff

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.access.prepost.PostFilter
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
    //def projectService

    @PreAuthorize("hasPermission(#id, 'us.jnsq.handoff.PotentialProjectActor', read) or hasPermission(#id, 'us.jnsq.handoff.PotentialProjectActor', admin)")
    def view(long id) {
        PotentialProjectActor.get(id)
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def listForUser(User user) {
        PotentialProjectActor.withCriteria {
            user {
                eq("id", user.id)
            }
        }
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def listForProject(Project project) {
        PotentialProjectActor.withCriteria {
            project {
                eq("id", project.id)
            }
        }
    }
    
    @PreAuthorize("hasPermission(#ppa.project, admin)")
    def approveApplication(PotentialProjectActor ppa, String note) {
        ppa.active = false
        ppa.notes += "<div class=\"approved\">$note</div>"
        ppa.save()
        def actor = new Actor(
            project: ppa.project,
            user: ppa.user,
            role: ppa.role
        ).save(flush: true)
        //projectService.addPermission(ppa.project, ppa.user.username, BasePermission.READ)
        addProjectPermission(ppa.project, ppa.user.username, BasePermission.READ)
        actor
    }
    
    @PreAuthorize("hasPermission(#ppa.project, admin)")
    def rejectApplication(PotentialProjectActor ppa, String reason) {
        ppa.active = false
        ppa.notes += "<div class=\"rejected\">$reason</div>"
        ppa.save(flush: true)
    }
    
    @PreAuthorize("hasPermission(#ppa.project, read)")
    def acceptInvitation(PotentialProjectActor ppa, String note) {
        ppa.active = false
        ppa.notes += "<div class=\"accepted\">$note</div>"
        ppa.save()
        def actor = new Actor(
            project: ppa.project,
            user: ppa.user,
            role: ppa.role
        ).save(flush: true)
        //projectService.addPermission(ppa.project, ppa.user.username, BasePermission.READ)
        addProjectPermission(ppa.project, ppa.user.username, BasePermission.READ)
        actor
    }
    
    @PreAuthorize("hasPermission(#ppa.project, read)")
    def declineInvitation(PotentialProjectActor ppa, String reason) {
        ppa.active = false
        ppa.notes += "<div class=\"declined\">$reason</div>"
        ppa.save(flush: true)
    }
    
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
            notes: "<div class=\"description\">$notes</div>",
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
    
    void addProjectPermission(Project project, String username, int permission) {
        addPermission(project, username, aclPermissionFactory.buildFromMask(permission))
    }
    
    @Transactional
    //@PreAuthorize("hasPermission(#ppa, admin)")
    void addPermission(Project project, String username, Permission permission) {
        aclUtilService.addPermission(project, username, permission)
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
