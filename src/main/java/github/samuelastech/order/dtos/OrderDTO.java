package github.samuelastech.order.dtos;

import github.samuelastech.order.models.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime dateHour;
    private OrderStatus status;
    private List<OrderItemDTO> items = new ArrayList<>();
}
