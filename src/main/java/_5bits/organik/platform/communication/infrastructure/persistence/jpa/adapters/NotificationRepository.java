package _5bits.organik.platform.communication.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}