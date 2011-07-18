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
        new PotentialProjectActor(
            project: project,
            user: user,
            role: role,
            notes: notes,
            type: "invitation"
        ).save(flush: true)
    }
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def apply(Project project, User user, Role role, String notes) {
        if (project.joinMethod == "applyDesired" && role in project.desiredRoles) {
            new PotentialProjectActor(
                project: project,
                user: user,
                role: role,
                notes: notes,
                type: "application"
            ).save(flush: true)
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
        } else {
            throw new AccessDeniedException()
        }
    }
    
    @PreAuthorize("hasPermission(#ppa.project, admin)")
    def approveApplication(PotentialProjectActor ppa) {}
    
    @PreAuthorize("hasPermission(#ppa.project, read)")
    def acceptInvitation(PotentialProjectActor ppa) {}

    @PreAuthorize("hasPermission(#ppa, read) or hasPermission(#ppa.project, read) or hasPermission(#ppa.project, admin)")
    def ppa(PotentialProjectActor ppa) {}
    
    @PreAuthorize("hasRole('ROLE_USER')")
    def leave(Project project) {}
    
    @PreAuthorize("hasPermission(#project, admin)")
    def eject(Project project, Actor actor) {}
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def create(params) {
        def project = new Project()
        project.properties = params
        project.save(flush: true)
        addPermission(project, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        project
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#id, 'us.jnsq.handoff.project', admin)")
    def edit(params) {}
    
    @Transactional
    @PreAuthorize("hasPermission(#project, delete) or hasPermission(#project, admin)")
    def delete(Project project) {}
    
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