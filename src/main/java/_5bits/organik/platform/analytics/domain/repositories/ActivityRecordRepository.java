package _5bits.organik.platform.analytics.domain.repositories;

import _5bits.organik.platform.analytics.domain.model.aggregates.ActivityRecord;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link ActivityRecord} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface ActivityRecordRepository {

    Optional<ActivityRecord> findById(Long id);

    List<ActivityRecord> findAll();

    List<ActivityRecord> findAllByMinimarketId(MinimarketId minimarketId);

    ActivityRecord save(ActivityRecord activityRecord);

    boolean existsById(Long id);
}
