package warranty;

import java.util.Objects;

public class LineItem {
	public final String type;
	public final double amount;
	public final String description;

	public LineItem(String type, double amount, String description) {
		this.type        = type;
		this.amount      = amount;
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LineItem lineItem = (LineItem) o;
		return Double.compare(lineItem.amount, amount) == 0 && Objects.equals(type, lineItem.type) && Objects.equals(description, lineItem.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, amount, description);
	}
}
