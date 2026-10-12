package model.interfaces

// ----------------------------------------------------------------------------
// Interface Searchable: kemampuan untuk dicari dengan kata kunci
// ----------------------------------------------------------------------------
interface Searchable {
    fun matches(query: String): Boolean
}
