package model.interfaces

import model.Employee

// ----------------------------------------------------------------------------
// Interface BonusEligible: pegawai yang berhak bonus
// (default: bonusRate * gaji total)
// ----------------------------------------------------------------------------
interface BonusEligible {
    val bonusRate: Double
    fun calculateBonus(): Double = bonusRate * (this as Employee).calculateSalary()
}
