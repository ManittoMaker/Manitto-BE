package manitto.backend.global.util;

import manitto.backend.global.dto.response.result.SingleResult;

public class ResponseUtil {

    public static <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        return result;
    }
}
