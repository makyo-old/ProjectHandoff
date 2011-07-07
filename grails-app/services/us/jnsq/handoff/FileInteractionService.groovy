package us.jnsq.handoff

/*
 * Files may be moved amongst actors or between an actor and the docket by three
 * actors (handoffs), two actors (assignments/retractions), or one actor
 * (claims/relinquishments)
 * 
 */
class FileInteractionService {

    static transactional = true
    
    def handoff(File file, Actor from, Actor to, Actor assigner) {
        // check permissions
            // canHandoff(from) && canHandoff(to) && canHandoff(assigner)
        
        // create handoff with three actors
    }
    
    def assign(File file, Actor assignee, Actor assigner) {
        // check permissions
            // canHandoff(assignee) && canHandoff(assigner)
        
        // create handoff with two actors
    }
    
    def retract(File file, Actor retractee, Actor retractor) {
        // check permissions
            // canHandoff(retractee) && canHandoff(retractor)
        
        // create handoff with two actors
    }
    
    def claim(File file, Actor claimer) {
        // check permissions
        
        // create handoff with one actor
    }
    
    def relinquish(File file, Actor relinquisher) {
        // check permissions
        
        // create handoff with one actor
    }
}
