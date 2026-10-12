package model.status

// ----------------------------------------------------------------------------
// Sealed class ItemStatus: status sebuah item perpustakaan
// ----------------------------------------------------------------------------
sealed class ItemStatus {
    object Available : ItemStatus()                                   // tersedia
    data class Borrowed(val by: String, val date: String) : ItemStatus() // dipinjam
    object Lost : ItemStatus()                                        // hilang

    // `when` ekshaustif: tanpa `else`, compiler memastikan semua status tertangani
    fun display(): String = when (this) {
        Available -> "Tersedia"
        is Borrowed -> "Dipinjam oleh $by pada $date"
        Lost -> "Hilang"
    }
}
