package org.abormot.impl.db.repository.psy

import org.abormot.impl.db.entity.business.ProfileVariant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileVariantRepo : JpaRepository<ProfileVariant, Int>
