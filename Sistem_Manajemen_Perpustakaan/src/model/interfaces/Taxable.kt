package model.interfaces

import model.Employee

// ----------------------------------------------------------------------------
// Interface Taxable: pegawai yang dikenai pajak (default 5% dari gaji total)
// ----------------------------------------------------------------------------
interface Taxable {
    fun calculateTax(): Double = 0.05 * (this as Employee).calculateSalary()
}
