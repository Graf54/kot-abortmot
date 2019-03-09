package org.abormot.db.repository.psy

import org.abormot.db.entity.business.ProfileVariant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileVariantRepo : JpaRepository<ProfileVariant, Int>
