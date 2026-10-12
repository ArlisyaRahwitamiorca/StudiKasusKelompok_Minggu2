package model.interfaces

// ----------------------------------------------------------------------------
// Interface Borrowable: kemampuan untuk dipinjam
// ----------------------------------------------------------------------------
interface Borrowable {
    fun borrow(memberId: String, date: String): Boolean
    fun returnItem(): Boolean
    fun isAvailable(): Boolean
    fun getBorrower(): String?
}
