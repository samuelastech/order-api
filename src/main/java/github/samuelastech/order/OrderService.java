package github.samuelastech.order;

import github.samuelastech.order.dtos.OrderDTO;
import github.samuelastech.order.dtos.OrderStatusDTO;
import github.samuelastech.order.models.Order;
import github.samuelastech.order.models.OrderStatus;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private ModelMapper mapper;

    public List<OrderDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(order -> mapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO getById(Long id) {
        Order order = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.map(order, OrderDTO.class);
    }

    public OrderDTO create(OrderDTO orderDTO) {
        Order order = mapper.map(orderDTO, Order.class);
        order.setDateHour(LocalDateTime.now());
        order.setStatus(OrderStatus.MADE);
        order.getItems().forEach(item -> item.setOrder(order));
        Order createdOrder = repository.save(order);
        return mapper.map(createdOrder, OrderDTO.class);
    }

    public OrderDTO updateStatus(Long id, OrderStatusDTO status) {
        Order order = repository.findByIdWithItems(id);
        if (order == null) throw new EntityNotFoundException();
        order.setStatus(status.getStatus());
        repository.updateStatus(status.getStatus(), order);
        return mapper.map(order, OrderDTO.class);
    }

    public void approvePayment(Long id) {
        Order order = repository.findByIdWithItems(id);
        if (order == null) throw new EntityNotFoundException();
        order.setStatus(OrderStatus.PAID);
        repository.updateStatus(OrderStatus.PAID, order);
    }
}
