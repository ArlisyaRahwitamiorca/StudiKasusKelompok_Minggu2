package model.subclasses

import model.LibraryItem
import model.interfaces.Borrowable
import model.interfaces.Searchable
import model.status.ItemStatus

// DVD: bisa dipinjam dan bisa dicari (judul / genre)
class DVD(
    id: String,
    title: String,
    year: Int,
    val duration: Int,      // menit
    val genre: String
) : LibraryItem(id, title, year), Borrowable, Searchable {

    override var status: ItemStatus = ItemStatus.Available
    override val typeName: String = "DVD"

    override fun getDetails(): String = "DVD, $duration menit, genre: $genre"

    // --- Borrowable ---
    override fun borrow(memberId: String, date: String): Boolean {
        if (!isAvailable()) return false
        status = ItemStatus.Borrowed(memberId, date)
        return true
    }

    override fun returnItem(): Boolean {
        if (status !is ItemStatus.Borrowed) return false
        status = ItemStatus.Available
        return true
    }

    override fun isAvailable(): Boolean = status is ItemStatus.Available

    override fun getBorrower(): String? = (status as? ItemStatus.Borrowed)?.by

    // --- Searchable ---
    override fun matches(query: String): Boolean =
        title.contains(query, ignoreCase = true) || genre.contains(query, ignoreCase = true)
}
