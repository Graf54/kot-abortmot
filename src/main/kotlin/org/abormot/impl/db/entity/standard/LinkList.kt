package org.abormot.impl.db.entity.standard

import javax.persistence.*

@Entity
class LinkList {
    @Id
    @GeneratedValue
    var id=0
    @OneToMany
    @JoinTable(name = "LINK_LIST_SUPPORT")
    var links: MutableList<Link> = ArrayList(0)
}
