package us.jnsq.handoff

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional
import us.jnsq.handoff.security.User

class ProjectService {

    static transactional = true
    
    def aclPermissionFactory
    def aclUtilService
    
    def list(params) {
        
    }
    
    @PreAuthorize("hasPermission(#id, 'us.jnsq.handoff.project', read) or hasPermission(#id, 'us.jnsq.handoff.project', 'admin')")
    def view(long id) {
        Project.get id
    }
    
    @PreAuthorize("hasPermission(#project, admin)")
    def invite(Project project, User user, Role role, String notes) {}
    
    @PreAuthorize("hasRole('ROLE_USER')")
    def apply(Project project, User user, Role role, String notes) {}
    
    @PreAuthorize("hasRole('ROLE_USER')")
    def join(Project project, Role role) {}
    
    @PreAuthorize("hasPermission(#project, admin)")
    def approveApplication(Project project, PotentialProjectActor ppa) {}
    
    @PreAuthorize("hasPermission(#project, read)")
    def acceptInvitation(Project project, PotentialProjectActor ppa) {}

    @PreAuthorize("hasPermission(#ppa.project, read) or hasPermission(#ppa.project, admin)")
    def ppa(PotentialProjectActor ppa) {}
    
    @PreAuthorize("hasRole('ROLE_USER')")
    def leave(Project project) {}
    
    @PreAuthorize("hasPermission(#project, admin)")
    def eject(Project project, Actor actor) {}
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def create(params) {}
    
    @Transactional
    @PreAuthorize("hasPermission(#id, 'us.jnsq.handoff.project', admin)")
    def edit(params) {}
    
    @PreAuthorize("hasPermission(#project, delete) or hasPermission(#project, admin)")
    def delete(Project project) {}
    
    void addPermission(Project project, String username, int permission) {
        addPermission report, username, aclPermissionFactory.buildFromMask(permission)
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#project, admin)")
    void addPermission(Project project, String username, Permission permission) {
        aclUtilService.addPermission report, username, permission
    }
    
    @Transactional
    @PreAuthorize("hasPermission(#project, admin)")
    void deletePermission(Project project, String username, Permission permission) {
        
    }
}
