package model.subclasses

import model.LibraryItem
import model.interfaces.Searchable
import model.status.ItemStatus

// Majalah: hanya bisa dicari, TIDAK bisa dipinjam (tidak mengimplementasikan Borrowable)
class Magazine(
    id: String,
    title: String,
    year: Int,
    val issueNumber: Int,
    val publisher: String
) : LibraryItem(id, title, year), Searchable {

    override var status: ItemStatus = ItemStatus.Available
    override val typeName: String = "Majalah"

    override fun getDetails(): String = "Majalah edisi #$issueNumber, diterbitkan oleh $publisher"

    // --- Searchable ---
    override fun matches(query: String): Boolean =
        title.contains(query, ignoreCase = true) || publisher.contains(query, ignoreCase = true)
}
