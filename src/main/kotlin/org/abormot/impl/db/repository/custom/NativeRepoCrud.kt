package org.abormot.impl.db.repository.custom

import org.abormot.impl.db.entity.standard.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NativeRepoCrud : JpaRepository<User, Long>, NativeRepo
