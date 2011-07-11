package us.jnsq.handoff

import org.springframework.security.access.AccessDeniedException

class FileInteractionService {

    static transactional = true
    
    def permissionService
    
    // TODO: switch from this to ACLs
    def can = permissionService.hasPermission
    
    def handoff(File file, Actor to, Actor assigner = null) {
        if (assigner) {
            if (!can(assigner, "administrate", file)) {
                throw new AccessDeniedException()
            }
        } else {
            assigner = file.currentOwner
        }
        if ([file.currentOwner, to, assigner].inject(true) { res, who -> res && can(who, "interact", file) }) {
            new Interaction(file: file, from: from, to: to, intermediary: assigner)
        } else {
            throw new AccessDeniedException()
        }
    }
    
    def assign(File file, Actor assignee, Actor assigner) {
        if (file.currentOwner == null && [assignee, assigner].inject(true) { res, who -> res && can(who, "interact", file) }) {
            new Interaction(file: file, from: file.currentOwner, to: assignee, intermediary: assigner)
        } else {
            throw new AccessDeniedException()
        }
    }
    
    def retract(File file, Actor retractor) {
        if ([retractor, file.currentOwner].inject(true) { res, who -> res && can(who, "interact", file) }) {
            
        } else {
            throw new AccessDeniedException()
        }
    }
    
    def claim(File file, Actor claimer) {
        if (file.currentOwner == null && can(claimer, "interact", file)) {
            
        } else {
            throw new AccessDeniedException()
        }
    }
    
    def relinquish(File file) {
        if (can(file.currentOwner, "interact", file)) {
            
        } else {
            throw new AccessDeniedException()
        }
    }
}
