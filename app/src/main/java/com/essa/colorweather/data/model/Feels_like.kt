import android.os.Parcel
import android.os.Parcelable

data class Feels_like (
	val day : Double,
	val night : Double,
	val eve : Double,
	val morn : Double
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readDouble(),
		parcel.readDouble(),
		parcel.readDouble(),
		parcel.readDouble()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeDouble(day)
		parcel.writeDouble(night)
		parcel.writeDouble(eve)
		parcel.writeDouble(morn)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Feels_like> {
		override fun createFromParcel(parcel: Parcel): Feels_like {
			return Feels_like(parcel)
		}

		override fun newArray(size: Int): Array<Feels_like?> {
			return arrayOfNulls(size)
		}
	}
}