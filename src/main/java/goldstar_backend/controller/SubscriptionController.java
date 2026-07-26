package goldstar_backend.controller;

import goldstar_backend.entity.Subscription;
import goldstar_backend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{ownerId}/subscribe")
    public ResponseEntity<Subscription> subscribe(
            @PathVariable Long ownerId,
            @RequestParam Integer tokens,
            @RequestParam Double amount,
            @RequestParam String planName) {

        return ResponseEntity.ok(
                subscriptionService.renew(ownerId, tokens, amount, planName)
        );
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<Subscription> getSubscription(
            @PathVariable Long ownerId) {

        return ResponseEntity.ok(
                subscriptionService.getByOwner(ownerId)
        );
    }
}