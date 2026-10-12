package model

import model.status.EmployeeStatus

// ----------------------------------------------------------------------------
// Abstract class Employee: induk semua jenis pegawai
// ----------------------------------------------------------------------------
abstract class Employee(
    val id: String,
    val name: String,
    val baseSalary: Double
) {
    abstract var status: EmployeeStatus      // properti abstrak, bisa berubah
    abstract fun calculateSalary(): Double   // rumus gaji beda tiap jenis pegawai

    open fun displayInfo() {
        println("ID: $id, Nama: $name")
        println("Gaji Pokok: Rp ${baseSalary}")
        println("Gaji Total: Rp ${calculateSalary()}")
        println("Status: ${status.display()}")
    }
}
