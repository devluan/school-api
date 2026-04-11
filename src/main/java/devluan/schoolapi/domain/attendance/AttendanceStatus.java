package devluan.schoolapi.domain.attendance;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AttendanceStatus {
    PRESENT("present"),
    ABSENT("absent"),
    LATE("late");

    private final String name;
}