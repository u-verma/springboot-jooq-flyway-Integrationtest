/*
 * This file is generated by jOOQ.
 */
package persistence.keys


import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal

import persistence.tables.Account
import persistence.tables.UserPrincipal
import persistence.tables.records.AccountRecord
import persistence.tables.records.UserPrincipalRecord



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val PK_ACCOUNT: UniqueKey<AccountRecord> = Internal.createUniqueKey(Account.ACCOUNT, DSL.name("pk_account"), arrayOf(Account.ACCOUNT.ID), true)
val PK_USER_PRINCIPAL: UniqueKey<UserPrincipalRecord> = Internal.createUniqueKey(UserPrincipal.USER_PRINCIPAL, DSL.name("pk_user_principal"), arrayOf(UserPrincipal.USER_PRINCIPAL.ID), true)