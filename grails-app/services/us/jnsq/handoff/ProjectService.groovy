package us.jnsq.handoff

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional


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
    
    @PreAuthorize("hasPermission(#id, 'us.jnsq.handoff.project', admin)")
    def invite() {}
    
    @PreAuthorize("hasRole('ROLE_USER')")
    def apply() {}
    
    @PreAuthorize("hasRole('ROLE_USER')")
    def join() {}
    
    @PreAuthorize("hasPermission(#project, admin)")
    def approveApplication(Project project) {}
    
    @PreAuthorize("hasPermission(#project, read)")
    def acceptInvitation(Project project) {}
    
    @PreAuthorize("hasRole('ROLE_USER')")
    def leave() {}
    
    @PreAuthorize("hasPermission(#project, admin)")
    def eject(Project project, Actor actor) {}
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def create(params) {}
    
    @Transactional
    @PreAuthorize("hasPermission(#id, 'us.jnsq.handoff.project', admin)")
    def edit() {}
    
    @PreAuthorize("hasPermission(#project, delete) or hasPermission(#project, admin)")
    def delete() {}
    
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
