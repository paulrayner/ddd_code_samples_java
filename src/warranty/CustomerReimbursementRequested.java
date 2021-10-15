package warranty;

import java.util.Date;
import java.util.UUID;

public class CustomerReimbursementRequested {
    public final UUID   contract_id;
    public final String rep_name;
    public final String reason;
    public final Date   occurred_on;

    public CustomerReimbursementRequested(UUID contract_id, String rep_name, String reason) {
        this.contract_id = contract_id;
        this.rep_name    = rep_name;
        this.reason      = reason;
        this.occurred_on = new Date();
    }
}
