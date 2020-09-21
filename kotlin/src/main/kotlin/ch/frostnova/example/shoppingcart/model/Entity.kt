package ch.frostnova.example.shoppingcart.model

import java.util.*

abstract class Entity<ID> {
    
    var id: ID? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val entity = o as Entity<*>
        return id == entity.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}