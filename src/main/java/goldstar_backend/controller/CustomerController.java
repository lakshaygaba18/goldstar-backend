package goldstar_backend.controller;

import goldstar_backend.dto.CustomerRequest;
import goldstar_backend.dto.CustomerResponse;
import goldstar_backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import goldstar_backend.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @PostMapping("/{workerId}")
    public ResponseEntity<CustomerResponse> createCustomer(
            @PathVariable Long workerId,
            @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(
                customerService.createCustomer(request, workerId)
        );
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok(customerRepository.findAll());
    }
}