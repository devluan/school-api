package devluan.schoolapi.web.handler;

public record ErrorInfo(
        Integer status,
        String message
) {
}
