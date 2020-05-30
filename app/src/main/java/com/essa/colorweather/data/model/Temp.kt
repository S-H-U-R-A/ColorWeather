import android.os.Parcel
import android.os.Parcelable

data class Temp (

	val day : Double,
	val min : Double,
	val max : Double,
	val night : Double,
	val eve : Double,
	val morn : Double
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readDouble(),
		parcel.readDouble(),
		parcel.readDouble(),
		parcel.readDouble(),
		parcel.readDouble(),
		parcel.readDouble()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeDouble(day)
		parcel.writeDouble(min)
		parcel.writeDouble(max)
		parcel.writeDouble(night)
		parcel.writeDouble(eve)
		parcel.writeDouble(morn)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Temp> {
		override fun createFromParcel(parcel: Parcel): Temp {
			return Temp(parcel)
		}

		override fun newArray(size: Int): Array<Temp?> {
			return arrayOfNulls(size)
		}
	}
}