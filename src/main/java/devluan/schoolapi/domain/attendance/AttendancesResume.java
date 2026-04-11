package devluan.schoolapi.domain.attendance;

public record AttendancesResume(
        Long total,
        Long present,
        Long absent,
        Long late
) {
    public AttendancesResume(Long present, Long absent, Long late) {
        this(present + absent + late, present, absent, late);
    }
}