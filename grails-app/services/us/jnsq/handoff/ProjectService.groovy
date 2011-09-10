package us.jnsq.handoff

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.access.AccessDeniedException
import us.jnsq.handoff.security.User

class ProjectService {

    static transactional = true
    
    def aclPermissionFactory
    def aclUtilService
    def springSecurityService
    def ppaService
    
    def list(params) {
        def user = springSecurityService.currentUser
        Project.withCriteria {
            if (user) {
                or {
                    "in"("visibility", ["all", "loggedIn"])
                    if (user.roles.size() > 0) {
                        and {
                            eq("visibility", "desiredRoles")
                            desiredRoles {
                                "in"("id", user.roles)
                            }
                        }
                    }
                    if (user.actors.size() > 0) {
                        and {
                            eq("visibility", "actors")
                            actors {
                                "in"("id", user.actors)
                            }
                        }
                    }
                }
            } else {
                eq("visibility", "all")
            }
        }
    }
    
    @PreAuthorize("hasPermission(#id, 'us.jnsq.handoff.Project', read) or hasPermission(#id, 'us.jnsq.handoff.Project', admin)")
    def view(long id) {
        def project = Project.get(id)
        log.info project
        project
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#project, admin)")
    def invite(Project project, User user, Role role, String notes) {
        ppaService.create(project, user, role, notes, "invitation")
    }
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def apply(Project project, User user, Role role, String notes) {
        if (project.joinMethod == "applyDesired" && role in project.desiredRoles) {
            ppaService.create(project, user, role, notes, "application")
        } else {
            throw new AccessDeniedException()
        }
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    def join(Project project, Role role) {
        if (project.joinMethod == "joinAll" || (project.joinMethod == "joinDesired" && role in project.desiredRoles)) {
            new Actor (
                project: project,
                user: springSecurityService.currentUser,
                role: role
            ).save(flush: true)
            addPermission(project, actor.user.username, BasePermission.READ)
        } else {
            throw new AccessDeniedException()
        }
    }
    
    @PreAuthorize("hasPermission(#project, read) and not hasPermission(#project, admin)")
    def leave(Project project, Actor actor) {
        if (actor.project == project && actor.user.username == springSecurityService.authentication.name) {
            actor.active = false
            actor.save(flush: true)
        } else {
            throw new AccessDeniedException()
        }
    }
    
    @PreAuthorize("hasPermission(#project, admin)")
    def eject(Project project, Actor actor) {
        def acl = aclUtilService.readAcl(project)
        acl.entries.eachWithIndex { entry, i ->
            if (entry.sid.equals(actor.user.username) && entry.permission.equals(BasePermission.ADMINISTRATION)) {
                throw new AccessDeniedException()
            }
        }
        if (actor.project == project) {
            actor.active = false
            actor.save(flush: true)
        } else {
            throw new AccessDeniedException()
        }
    }
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def create(params) {
        def project = new Project()
        project.lead = springSecurityService.currentUser
        project.properties = params
        project.save(flush: true)
        addPermission(project, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        project
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#params.id, 'us.jnsq.handoff.project', admin)")
    def edit(params) {
        def project = Project.get(params.id)
        project.properties = params
        project.save(flush: true)
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#project, delete) or hasPermission(#project, admin)")
    def delete(Project project) {
        project.delete(flush: true)
    }
    
    @PreAuthorize("hasPermission(#project, admin)")
    def makeAdmin(Project project, String username) {
        addPermission(project, actor.user.username, BasePermission.ADMINISTRATION)
    }
    
    @PreAuthorize("hasPermission(#project, admin)")
    def relinquishAdmin(Project project) {
        removePermission(project, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
    }
    
    void addPermission(Project project, String username, int permission) {
        addPermission(project, username, aclPermissionFactory.buildFromMask(permission))
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#project, admin)")
    void addPermission(Project project, String username, Permission permission) {
        aclUtilService.addPermission(project, username, permission)
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#project, admin)")
    void deletePermission(Project project, String username, Permission permission) {
        def acl = aclUtilService.readAcl(project)
        acl.entries.eachWithIndex { entry, i ->
            if (entry.sid.equals(username) && entry.permission.equals(permission)) {
                acl.deleteAce(i)
            }
        }
        aclService.updateAcl(acl)
    }
}