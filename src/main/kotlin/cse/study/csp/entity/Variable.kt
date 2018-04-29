package cse.study.csp.entity

data class Variable(val definition: String) {

    val domains: MutableSet<Domain> = mutableSetOf()

    var assignment: Domain? = null

    fun addDomains(element: Set<Domain>) = domains.addAll(element)

    fun getUnassignedDomain(): Domain? = domains.toList()[domains.indexOf(assignment).inc()]

    fun assignDomain(domain: Domain) {
        assignment = domain
    }

    override fun toString(): String {
        return "VARIABLE{$definition} --> DOMAINS[$domains] ASSIGNMENT[$assignment]"
    }

}