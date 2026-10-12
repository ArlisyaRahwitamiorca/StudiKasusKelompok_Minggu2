package model.subclasses

import model.LibraryItem
import model.interfaces.Borrowable
import model.interfaces.Searchable
import model.status.ItemStatus

// Buku: bisa dipinjam dan bisa dicari (judul / penulis)
class Book(
    id: String,
    title: String,
    year: Int,
    val author: String,
    val pages: Int
) : LibraryItem(id, title, year), Borrowable, Searchable {

    override var status: ItemStatus = ItemStatus.Available
    override val typeName: String = "Buku"

    override fun getDetails(): String = "Buku oleh $author, $pages halaman"

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
        title.contains(query, ignoreCase = true) || author.contains(query, ignoreCase = true)
}
