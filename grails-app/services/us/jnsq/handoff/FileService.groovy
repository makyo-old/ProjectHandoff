package us.jnsq.handoff

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.access.AccessDeniedException
import us.jnsq.handoff.security.User

class FileService {

    static transactional = true

    def aclPermissionFactory
    def aclUtilService
    def springSecurityService

    def create(Actor creator, file) {
        
    }
    
    def update(Actor creator, file) {}
    
    def delete(Actor deletor, File file) {}
}
