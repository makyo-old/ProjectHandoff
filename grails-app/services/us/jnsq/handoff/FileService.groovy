package us.jnsq.handoff

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.access.AccessDeniedException
import us.jnsq.handoff.security.User

class FileService {

    static transactional = false
    
    def permissionService
    
    def can = permissionService.hasPermission
    
    def list(params) {}
    
    @PreAuthorize("hasPermission(#id, 'us.jnsq.handoff.File', read) or hasPermission(#id, 'us.jnsq.handoff.File', admin)")
    def view(long id) {
        def file = File.get(id)
        file
    }

    @Transactional
    @PreAuthorize("hasPermission(#creator.project, admin)")
    def create(String name, Actor creator) {
        def file = new File()
        file.actor = creator
        file.project = creator.project
        file.name = name
        file.save(flush: true)
        permissionService(read: true, write: true, interact: true, administrate: true, actor: creator, file: file)
    }
    
    @Transactional
    def update(Actor creator, file) {}
    
    @Transactional
    def delete(Actor deletor, File file) {}
}
