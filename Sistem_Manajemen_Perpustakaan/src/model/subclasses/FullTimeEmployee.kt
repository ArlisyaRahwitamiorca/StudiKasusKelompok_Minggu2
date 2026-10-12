package model.subclasses

import model.Employee
import model.interfaces.BonusEligible
import model.interfaces.Taxable
import model.status.EmployeeStatus

// Pegawai tetap: gaji pokok + tunjangan, kena pajak, dapat bonus tahunan
class FullTimeEmployee(
    id: String, name: String, baseSalary: Double,
    val allowance: Double, val annualBonus: Double
) : Employee(id, name, baseSalary), Taxable, BonusEligible {

    override var status: EmployeeStatus = EmployeeStatus.Active
    override val bonusRate: Double = 0.0 // tidak digunakan karena override calculateBonus
    override fun calculateSalary(): Double = baseSalary + allowance
    override fun calculateBonus(): Double = annualBonus
}
