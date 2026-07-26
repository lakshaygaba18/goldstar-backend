package goldstar_backend.service;

import goldstar_backend.dto.OrderItemRequest;
import goldstar_backend.dto.OrderItemResponse;
import goldstar_backend.dto.OrderRequest;
import goldstar_backend.dto.OrderResponse;
import goldstar_backend.entity.*;
import goldstar_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final WorkerRepository workerRepository;
    private final GarmentRepository garmentRepository;

    public OrderResponse createOrder(OrderRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Worker worker = null;
        if (request.getWorkerId() != null) {
            worker = workerRepository.findById(request.getWorkerId())
                    .orElseThrow(() -> new RuntimeException("Worker not found"));
        }

        Long ownerId = customer.getOwner().getId();

        Order order = Order.builder()
                .customer(customer)
                .worker(worker)
                .owner(customer.getOwner())
                .orderedAt(LocalDateTime.now())
                .items(new ArrayList<>())
                .build();

        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemReq : request.getItems()) {

            Garment garment = garmentRepository.findById(itemReq.getGarmentId())
                    .orElseThrow(() -> new RuntimeException("Garment not found: " + itemReq.getGarmentId()));

            // check + deduct stock
            Integer stock = garment.getStockQuantity();
            if (stock == null || stock <= 0) {
                throw new RuntimeException("Out of stock: " + garment.getName());
            }
            garment.setStockQuantity(stock - 1);

            if (garment.getStockQuantity() == 0) {
                garment.setActive(false); // auto-hide from inventory when sold out
            }

            garmentRepository.save(garment);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .garment(garment)
                    .price(itemReq.getPrice())
                    .build();

            orderItems.add(orderItem);
            total += itemReq.getPrice();
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        List<OrderItemResponse> itemResponses = saved.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .garmentId(item.getGarment().getId())
                        .garmentName(item.getGarment().getName())
                        .price(item.getPrice())
                        .build())
                .toList();

        return OrderResponse.builder()
                .orderId(saved.getId())
                .customerName(saved.getCustomer().getName())
                .workerName(saved.getWorker() != null ? saved.getWorker().getName() : null)
                .totalAmount(saved.getTotalAmount())
                .orderedAt(saved.getOrderedAt())
                .items(itemResponses)
                .build();
    }
}