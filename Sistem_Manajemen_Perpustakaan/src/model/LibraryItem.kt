package model

import model.status.ItemStatus

// ----------------------------------------------------------------------------
// Abstract class LibraryItem: induk semua koleksi perpustakaan
// ----------------------------------------------------------------------------
abstract class LibraryItem(
    open val id: String,
    open val title: String,
    open val year: Int
) {
    abstract var status: ItemStatus          // properti abstrak, bisa berubah
    abstract val typeName: String            // "Buku", "Majalah", "DVD" (untuk pesan)
    abstract fun getDetails(): String        // detail khusus tiap jenis item

    /** Satu baris ringkas: [ID] Judul (Tahun) - Status */
    fun summary(): String = "[$id] $title ($year) - ${status.display()}"

    /** Tampilan lengkap: ringkasan + detail. */
    open fun display() {
        println(summary())
        println("   Detail: ${getDetails()}")
    }
}
