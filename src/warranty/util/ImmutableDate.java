package warranty.util;

import java.util.Calendar;
import java.util.Date;

/**
 * IN java 8 Use LocalDateTime instead
 * 
 * @author binair
 *
 */
public final class ImmutableDate {
	public final long dateInMills;

	private ImmutableDate(long dateInMills) {
		this.dateInMills = dateInMills;
	}

	public static ImmutableDate of(Date date) {
		return of(date.getTime());
	}

	public static ImmutableDate of(long time) {
		return new ImmutableDate(time);
	}

	public int compareTo(ImmutableDate effectiveDate) {
		return (dateInMills < effectiveDate.dateInMills ? -1
				: (dateInMills == effectiveDate.dateInMills ? 0 : 1));
	}

	public ImmutableDate add(int field, int amount) {
		return new ImmutableDate(increaseDateByOneYear(field, amount));
	}

	private long increaseDateByOneYear(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateInMills);
		cal.add(field, amount);

		return cal.getTimeInMillis();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (dateInMills ^ (dateInMills >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImmutableDate other = (ImmutableDate) obj;
		if (dateInMills != other.dateInMills)
			return false;
		return true;
	}
	
	
}
