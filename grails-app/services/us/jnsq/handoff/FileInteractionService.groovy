package us.jnsq.handoff

import org.springframework.security.access.AccessDeniedException

class FileInteractionService {

    static transactional = true
    
    def permissionService
    
    // TODO: switch from this to ACLs
    def can = permissionService.hasPermission
    
    def handoff(File file, Actor to, Actor assigner = null, String notes, Map interactionRoleFieldData) {
        if (assigner) {
            if (!can(assigner, "administrate", file)) {
                throw new AccessDeniedException()
            }
        } else {
            assigner = file.currentOwner
        }
        if ([file.currentOwner, to, assigner].inject(true) { res, who -> res && can(who, "interact", file) }) {
            def interaction = new Interaction(
                file: file, 
                from: from, 
                to: to, 
                intermediary: assigner,
                notes: notes
            ).save()
            to.role.fields.each { 
                interaction.addToFields(new InteractionRoleField(
                        interaction: interaction,
                        roleField: it,
                        data: interactionRoleFieldData[it.id]
                    ).save()
                )
            }
            interaction.save(flush: true)
            file.currentOwner = to
            file.save(flush: true)
        } else {
            throw new AccessDeniedException()
        }
    }
    
    def assign(File file, Actor assignee, Actor assigner, String notes) {
        if (file.currentOwner == null && [assignee, assigner].inject(true) { res, who -> res && can(who, "interact", file) }) {
            new Interaction(
                file: file,
                to: assignee,
                intermediary: assigner,
                notes: notes
            ).save(flush: true)
            // No role fields needed, file is being handed from the void
            file.currentOwner = assignee
            file.save(flush: true)
        } else {
            throw new AccessDeniedException()
        }
    }
    
    def retract(File file, Actor retractor, String notes, Map interactionRoleFieldData) {
        if (retractor.id != file.currentOwner.id && [retractor, file.currentOwner].inject(true) { res, who -> res && can(who, "interact", file) }) {
            new Interaction(
                file: file,
                from: file.currentOwner,
                intermediary: retractor,
                notes: notes
            ).save(flush: true)
            // No role fields needed, the current owner doesn't have a say in this
            file.currentOwner = null
            file.save(flush: true)
        } else {
            throw new AccessDeniedException()
        }
    }
    
    def claim(File file, Actor claimer, String notes, Map interactionRoleFieldData) {
        if (file.currentOwner == null && can(claimer, "interact", file)) {
            new Interaction(
                file: file,
                to: claimer,
                notes: notes
            ).save(flush: true)
            // No role fields needed, file is being claimed from the void
            file.currentOwner = claimer
            file.save(flush: true)
        } else {
            throw new AccessDeniedException()
        }
    }
    
    def relinquish(File file, String notes, Map interactionRoleFieldData) {
        if (can(file.currentOwner, "interact", file)) {
            new Interaction(
                file: file,
                from: file.currentOwner,
                notes: notes
            ).save()
            file.currentOwner.role.fields.each { 
                interaction.addToFields(new InteractionRoleField(
                        interaction: interaction,
                        roleField: it,
                        data: interactionRoleFieldData[it.id]
                    ).save()
                )
            }
            interaction.save(flush: true)
            file.currentOwner = null
            file.save(flush: true)
        } else {
            throw new AccessDeniedException()
        }
    }
}
