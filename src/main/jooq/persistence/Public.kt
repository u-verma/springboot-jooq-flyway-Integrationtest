/*
 * This file is generated by jOOQ.
 */
package persistence


import kotlin.collections.List

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl

import persistence.tables.Account
import persistence.tables.UserPrincipal


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Public : SchemaImpl("public", DefaultCatalog.DEFAULT_CATALOG) {
    companion object {

        /**
         * The reference instance of <code>public</code>
         */
        val PUBLIC = Public()
    }

    /**
     * The table <code>public.account</code>.
     */
    val ACCOUNT get() = Account.ACCOUNT

    /**
     * The table <code>public.user_principal</code>.
     */
    val USER_PRINCIPAL get() = UserPrincipal.USER_PRINCIPAL

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        Account.ACCOUNT,
        UserPrincipal.USER_PRINCIPAL
    )
}