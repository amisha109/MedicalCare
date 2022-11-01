package org.amishakalhan;

public enum DoctorSpeciality {
    ALLERGY_AND_IMMUNOLOGY("Allergy and immunology"),
    ANESTHESIOLOGY("Anesthesiology"),
    DERMATOLOGY("Dermatology"),
    DIAGNOSTIC_RADIOLOGY("Diagnostic radiology"),
    EMERGENCY_MEDICINE("Emergency medicine"),
    FAMILY_MEDICINE("Family medicine"),
    INTERNAL_MEDICINE("Internal medicine"),
    MEDICAL_GENETICS("Medical genetics"),
    NEUROLOGY("Neurology"),
    NUCLEAR_MEDICINE("Nuclear medicine"),
    OBSTETRICS_AND_GYNECOLOGY("Obstetrics and gynecology"),
    OPHTHALMOLOGY("Ophthalmology"),
    PATHOLOGY("Pathology"),
    PEDIATRICS("Pediatrics"),
    PHYSICAL_MEDICINE_AND_REHABILITATION("Physical medicine and rehabilitation"),
    PREVENTIVE_MEDICINE("Preventive medicine"),
    PSYCHIATRY("Psychiatry"),
    RADIATION_ONCOLOGY("Radiation oncology"),
    SURGERY("Surgery"),
    UROLOGY("Urology");

    private final String speciality;

    DoctorSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }
}
