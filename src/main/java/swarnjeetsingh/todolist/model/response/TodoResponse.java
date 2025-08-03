package swarnjeetsingh.todolist.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TodoResponse {
    private Integer statusCode;
    private String message;
    private Boolean success;
    private Object data;
}
