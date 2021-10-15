package warranty;

import java.util.Date;
import java.util.UUID;

public class SubscriptionRenewed {
    public final UUID   contract_id;
    public final String reason;
    public final Date   occurred_on;

    public SubscriptionRenewed(UUID contract_id, String reason) {
        this.contract_id = contract_id;
        this.reason      = reason;
        this.occurred_on = new Date();
    }
}
