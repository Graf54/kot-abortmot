package org.abormot.db.entity.standard

import org.abormot.db.Const
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class Property {
    @Id
    var int = 0
    @Column(length = 500, nullable = false)
    var name = ""
    @Column(length = Const.MESSAGE, nullable = false)
    var value = ""
}
