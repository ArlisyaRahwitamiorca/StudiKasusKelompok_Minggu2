package model.subclasses

import model.Employee
import model.interfaces.Taxable
import model.status.EmployeeStatus

// Pegawai kontrak: hanya gaji pokok, kena pajak, tanpa bonus
class ContractEmployee(
    id: String, name: String, baseSalary: Double,
    val contractDuration: Int   // bulan
) : Employee(id, name, baseSalary), Taxable {

    override var status: EmployeeStatus = EmployeeStatus.Active
    override fun calculateSalary(): Double = baseSalary
}
