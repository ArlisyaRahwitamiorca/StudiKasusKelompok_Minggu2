package model.status

// ----------------------------------------------------------------------------
// Sealed class EmployeeStatus: status pegawai
// ----------------------------------------------------------------------------
sealed class EmployeeStatus {
    object Active : EmployeeStatus()       // pegawai aktif
    object OnLeave : EmployeeStatus()      // pegawai sedang cuti
    object Terminated : EmployeeStatus()   // pegawai berhenti

    // `when` ekshaustif: tanpa `else`, compiler memastikan semua status tertangani
    fun display(): String = when (this) {
        Active -> "Aktif"
        OnLeave -> "Cuti"
        Terminated -> "Berhenti"
    }
}
