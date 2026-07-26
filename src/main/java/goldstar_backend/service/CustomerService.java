package goldstar_backend.service;

import goldstar_backend.dto.CustomerRequest;
import goldstar_backend.dto.CustomerResponse;
import goldstar_backend.entity.Customer;
import goldstar_backend.entity.Owner;
import goldstar_backend.entity.Worker;
import goldstar_backend.repository.CustomerRepository;
import goldstar_backend.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final WorkerRepository workerRepository;

    public CustomerResponse createCustomer(CustomerRequest request, Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Owner owner = worker.getOwner();

        Customer customer = Customer.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .owner(owner)
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerResponse.builder()
                .id(savedCustomer.getId())
                .name(savedCustomer.getName())
                .phoneNumber(savedCustomer.getPhoneNumber())
                .build();
    }
}