package exception

// ----------------------------------------------------------------------------
// Custom exception sistem perpustakaan
// ----------------------------------------------------------------------------

/** Item dengan ID tertentu tidak ada di koleksi. */
class ItemNotFoundException(val itemId: String) :
    Exception("Item dengan ID '$itemId' tidak ditemukan")

/** Item tidak bisa dipinjam karena sedang dipinjam orang lain (atau tidak tersedia). */
class ItemAlreadyBorrowedException(val title: String, val borrower: String?) :
    Exception(
        if (borrower != null) "Item '$title' sedang dipinjam oleh $borrower"
        else "Item '$title' tidak tersedia untuk dipinjam"
    )

/** Item dikembalikan padahal tidak sedang dipinjam. */
class ItemNotBorrowedException(val title: String) :
    Exception("Item '$title' tidak sedang dipinjam")

/** Item memang tidak bisa dipinjam (contoh: Majalah, tidak mengimplementasikan Borrowable). */
class ItemNotBorrowableException(val title: String) :
    Exception("Item '$title' tidak dapat dipinjam")
