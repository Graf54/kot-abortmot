package org.abormot.db.entity.business

import javax.persistence.*

@Entity
class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    var name: String? = null
    var question: String? = null
    var maxLength: Int = 0
    @OneToMany
    @JoinTable(name = "PROFILE_VARIANT_LIST")
    @OrderColumn(name = "ID")
    var profileVariants: MutableList<ProfileVariant>? = null
    @Column(nullable = false)
    var typeId: Int = 0
    @Column(length = 100)
    var contains: String? = null
}
