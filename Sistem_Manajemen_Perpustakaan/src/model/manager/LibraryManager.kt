package model.manager

import exception.ItemAlreadyBorrowedException
import exception.ItemNotBorrowableException
import exception.ItemNotBorrowedException
import exception.ItemNotFoundException
import model.LibraryItem
import model.interfaces.Borrowable
import model.interfaces.Searchable
import model.status.ItemStatus

// ----------------------------------------------------------------------------
// Generic Repository<T>: penyimpan koleksi apa saja
// (ditulis di file ini karena struktur folder hanya menyebut LibraryManager.kt)
// ----------------------------------------------------------------------------
class Repository<T : Any> {
    private val items = mutableListOf<T>()

    fun add(item: T) { items.add(item) }

    fun getAll(): List<T> = items.toList()            // salinan, list asli tetap aman

    fun find(predicate: (T) -> Boolean): T? = items.find(predicate)

    fun findAll(predicate: (T) -> Boolean): List<T> = items.filter(predicate)
}

// ----------------------------------------------------------------------------
// LibraryManager (object declaration / singleton): pusat operasi perpustakaan
// ----------------------------------------------------------------------------
object LibraryManager {
    private val itemRepo = Repository<LibraryItem>()

    fun addItem(item: LibraryItem) = itemRepo.add(item)

    /** Cari item berdasarkan kata kunci (judul, penulis, penerbit, atau genre). */
    fun findByTitle(query: String): List<LibraryItem> =
        itemRepo.findAll { it is Searchable && it.matches(query) }

    /**
     * Pinjam item. Kegagalan (item tidak ada, sudah dipinjam, tidak bisa dipinjam)
     * ditangani dengan runCatching: pesan error dicetak dan hasilnya false.
     */
    fun borrowItem(itemId: String, memberId: String, date: String): Boolean =
        runCatching {
            val item = itemRepo.find { it.id == itemId } ?: throw ItemNotFoundException(itemId)
            if (item !is Borrowable) throw ItemNotBorrowableException(item.title)
            if (!item.isAvailable()) throw ItemAlreadyBorrowedException(item.title, item.getBorrower())

            item.borrow(memberId, date)
            println("✅ ${item.typeName} '${item.title}' dipinjam oleh $memberId pada $date")
        }.onFailure { println("❌ ${it.message}") }
            .isSuccess

    /** Kembalikan item. Gagal jika item tidak ada atau tidak sedang dipinjam. */
    fun returnItem(itemId: String): Boolean =
        runCatching {
            val item = itemRepo.find { it.id == itemId } ?: throw ItemNotFoundException(itemId)
            if (item !is Borrowable) throw ItemNotBorrowableException(item.title)
            if (!item.returnItem()) throw ItemNotBorrowedException(item.title)

            println("✅ ${item.typeName} '${item.title}' dikembalikan")
        }.onFailure { println("❌ ${it.message}") }
            .isSuccess

    fun getAvailableItems(): List<LibraryItem> =
        itemRepo.findAll { it.status is ItemStatus.Available }

    fun getBorrowedItems(): List<LibraryItem> =
        itemRepo.findAll { it.status is ItemStatus.Borrowed }

    fun displayAllItems() = itemRepo.getAll().forEach { it.display() }
}
