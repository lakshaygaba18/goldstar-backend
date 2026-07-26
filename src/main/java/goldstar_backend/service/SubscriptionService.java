package goldstar_backend.service;

import goldstar_backend.entity.Subscription;
import goldstar_backend.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription getByOwner(Long ownerId) {
        return subscriptionRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("No subscription found"));
    }

    public boolean hasTokensAvailable(Long ownerId) {
        Subscription sub = getByOwner(ownerId);
        return sub.isActive() && sub.getTokensRemaining() > 0;
    }

    public void deductToken(Long ownerId) {
        Subscription sub = getByOwner(ownerId);

        if (!sub.isActive() || sub.getTokensRemaining() <= 0) {
            throw new RuntimeException("Tokens exhausted. Please renew subscription.");
        }

        sub.setTokensRemaining(sub.getTokensRemaining() - 1);

        if (sub.getTokensRemaining() == 0) {
            sub.setActive(false);
        }

        subscriptionRepository.save(sub);
    }

    public Subscription renew(Long ownerId, Integer newTokens, Double amount, String planName) {
        Subscription sub = getByOwner(ownerId);

        sub.setTotalTokens(newTokens);
        sub.setTokensRemaining(newTokens);
        sub.setAmountPaid(amount);
        sub.setPlanName(planName);
        sub.setLastRenewedAt(LocalDateTime.now());
        sub.setActive(true);

        return subscriptionRepository.save(sub);
    }
}