/*
 * This file is generated by jOOQ.
 */
package persistence.tables.pojos


import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class UserPrincipal(
    val id: UUID? = null, 
    val firstName: String? = null, 
    val lastName: String? = null, 
    val emailId: String? = null, 
    val phoneNo: String? = null, 
    val createdBy: LocalDateTime? = null
): Serializable {

    override fun toString(): String {
        val sb = StringBuilder("UserPrincipal (")

        sb.append(id)
        sb.append(", ").append(firstName)
        sb.append(", ").append(lastName)
        sb.append(", ").append(emailId)
        sb.append(", ").append(phoneNo)
        sb.append(", ").append(createdBy)

        sb.append(")")
        return sb.toString()
    }
}
