import model.Employee
import model.LibraryItem
import model.interfaces.BonusEligible
import model.interfaces.Taxable
import model.manager.Company
import model.manager.LibraryManager
import model.subclasses.Book
import model.subclasses.ContractEmployee
import model.subclasses.DVD
import model.subclasses.FullTimeEmployee
import model.subclasses.Intern
import model.subclasses.Magazine
import java.util.Locale

fun main() {
    runEmployeeDemo()      // Studi Kasus 1 (Individu): Sistem Manajemen Pegawai

    println("\n" + "=".repeat(WIDTH) + "\n")

    runLibraryDemo()       // Studi Kasus 2 (Kelompok): Sistem Manajemen Perpustakaan
}

// ============================================================================
// STUDI KASUS 1 — INDIVIDU: SISTEM MANAJEMEN PEGAWAI
// ============================================================================
fun runEmployeeDemo() {
    banner("STUDI KASUS 1 — INDIVIDU", "Sistem Manajemen Pegawai")

    val company = Company("PT Maju Jaya")
    val pegawai: List<Employee> = listOf(
        FullTimeEmployee("P001", "Budi", 5_000_000.0, 1_500_000.0, 6_000_000.0),
        ContractEmployee("P002", "Siti", 4_500_000.0, 12),
        Intern("P003", "Ahmad", 2_000_000.0)
    )
    pegawai.forEach { company.addEmployee(it) }

    // --- Tabel pegawai --------------------------------------------------------
    section("1. Daftar Pegawai ${company.name}")
    printTable(
        headers = listOf("ID", "Nama", "Jenis", "Gaji Pokok", "Gaji Total", "Pajak", "Bonus", "Status"),
        rows = pegawai.map { e ->
            listOf(
                e.id,
                e.name,
                jenisPegawai(e),
                rupiah(e.baseSalary),
                rupiah(e.calculateSalary()),
                (e as? Taxable)?.let { rupiah(it.calculateTax()) } ?: "-",
                (e as? BonusEligible)?.let { rupiah(it.calculateBonus()) } ?: "-",
                e.status.display()
            )
        },
        aligns = listOf(
            Align.LEFT, Align.LEFT, Align.LEFT,
            Align.RIGHT, Align.RIGHT, Align.RIGHT, Align.RIGHT, Align.CENTER
        )
    )

    // --- Tabel ringkasan (dihitung oleh Company) -------------------------------
    section("2. Ringkasan Perusahaan")
    printTable(
        headers = listOf("Keterangan", "Jumlah"),
        rows = listOf(
            listOf("Jumlah Pegawai", pegawai.size.toString()),
            listOf("Total Gaji", rupiah(company.getTotalSalary())),
            listOf("Total Pajak", rupiah(company.getTotalTax())),
            listOf("Total Bonus", rupiah(company.getTotalBonus()))
        ),
        aligns = listOf(Align.LEFT, Align.RIGHT)
    )
}

private fun jenisPegawai(e: Employee): String = when (e) {
    is FullTimeEmployee -> "Tetap"
    is ContractEmployee -> "Kontrak"
    is Intern -> "Magang"
    else -> "-"
}

// ============================================================================
// STUDI KASUS 2 — KELOMPOK: SISTEM MANAJEMEN PERPUSTAKAAN
// ============================================================================
fun runLibraryDemo() {
    banner("STUDI KASUS 2 — KELOMPOK", "Sistem Manajemen Perpustakaan")

    val koleksi: List<LibraryItem> = listOf(
        Book("P001", "Pemrograman Kotlin", 2023, "Budi Santoso", 350),
        Book("P002", "Dasar-Dasar OOP", 2022, "Siti Rahayu", 280),
        Magazine("M001", "Tech Today", 2024, 42, "Tech Media"),
        DVD("D001", "Film Dokumenter", 2021, 120, "Dokumenter")
    )
    koleksi.forEach { LibraryManager.addItem(it) }

    section("1. Koleksi Perpustakaan")
    printTable(
        headers = listOf("ID", "Judul", "Tahun", "Detail", "Status"),
        rows = koleksi.map { listOf(it.id, it.title, it.year.toString(), it.getDetails(), it.status.display()) },
        aligns = listOf(Align.LEFT, Align.LEFT, Align.CENTER, Align.LEFT, Align.LEFT)
    )

    section("2. Meminjam Buku P001 oleh M001")
    LibraryManager.borrowItem("P001", "M001", "2025-01-15")

    section("3. Mencari 'Kotlin'")
    printItems(LibraryManager.findByTitle("Kotlin"))

    section("4. Mengembalikan Buku P001")
    LibraryManager.returnItem("P001")

    section("5. Item Tersedia")
    printItems(LibraryManager.getAvailableItems())

    section("6. Uji Skenario Error")
    step("Pinjam item yang tidak ada (X999)")
    LibraryManager.borrowItem("X999", "M001", "2025-01-16")

    step("Pinjam item yang sudah dipinjam (P002 oleh M002, lalu M003)")
    LibraryManager.borrowItem("P002", "M002", "2025-01-16")
    LibraryManager.borrowItem("P002", "M003", "2025-01-17")

    step("Kembalikan item yang tidak dipinjam (D001)")
    LibraryManager.returnItem("D001")

    step("Pinjam Majalah M001 (tidak bisa dipinjam)")
    LibraryManager.borrowItem("M001", "M001", "2025-01-18")

    section("7. Item Dipinjam")
    printItems(LibraryManager.getBorrowedItems())

    section("8. Ringkasan Koleksi")
    printTable(
        headers = listOf("Keterangan", "Jumlah"),
        rows = listOf(
            listOf("Total Item", koleksi.size.toString()),
            listOf("Tersedia", LibraryManager.getAvailableItems().size.toString()),
            listOf("Dipinjam", LibraryManager.getBorrowedItems().size.toString())
        ),
        aligns = listOf(Align.LEFT, Align.RIGHT)
    )
}

private fun printItems(items: List<LibraryItem>) =
    printTable(
        headers = listOf("ID", "Judul", "Tahun", "Status"),
        rows = items.map { listOf(it.id, it.title, it.year.toString(), it.status.display()) },
        aligns = listOf(Align.LEFT, Align.LEFT, Align.CENTER, Align.LEFT)
    )

// ============================================================================
// FUNGSI BANTU TAMPILAN (tabel, banner, format rupiah)
// ============================================================================
private const val WIDTH = 97

private enum class Align { LEFT, RIGHT, CENTER }

private fun center(text: String, width: Int): String {
    val total = (width - text.length).coerceAtLeast(0)
    val left = total / 2
    return " ".repeat(left) + text + " ".repeat(total - left)
}

private fun pad(text: String, width: Int, align: Align): String = when (align) {
    Align.LEFT -> text.padEnd(width)
    Align.RIGHT -> text.padStart(width)
    Align.CENTER -> center(text, width)
}

private fun banner(title: String, subtitle: String) {
    println()
    println("=".repeat(WIDTH))
    println(center(title, WIDTH))
    println(center(subtitle, WIDTH))
    println("=".repeat(WIDTH))
}

private fun section(title: String) {
    println()
    println("> $title")
    println("-".repeat(WIDTH))
}

private fun step(title: String) = println("  - $title")

private fun printTable(headers: List<String>, rows: List<List<String>>, aligns: List<Align>) {
    if (rows.isEmpty()) {
        println("  (tidak ada data)")
        return
    }
    val widths = headers.indices.map { c ->
        maxOf(headers[c].length, rows.maxOf { it[c].length })
    }

    fun border(left: String, mid: String, right: String) =
        println(left + widths.joinToString(mid) { "─".repeat(it + 2) } + right)

    fun row(cells: List<String>, header: Boolean = false) =
        println(
            "│" + cells.indices.joinToString("│") { i ->
                " " + pad(cells[i], widths[i], if (header) Align.CENTER else aligns[i]) + " "
            } + "│"
        )

    border("┌", "┬", "┐")
    row(headers, header = true)
    border("├", "┼", "┤")
    rows.forEach { row(it) }
    border("└", "┴", "┘")
}

private fun rupiah(value: Double): String =
    "Rp " + String.format(Locale.forLanguageTag("id-ID"), "%,.0f", value)