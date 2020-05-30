import android.os.Parcel
import android.os.Parcelable

data class Daily (

	val dt : Int,
	val sunrise : Int,
	val sunset : Int,
	val temp : Temp?,
	val feels_like : Feels_like?,
	val pressure : Int,
	val humidity : Int,
	val dew_point : Double,
	val wind_speed : Double,
	val wind_deg : Int,
	val weather : List<Weather>?,
	val clouds : Int,
	val rain : Double,
	val uvi : Double
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readInt(),
		parcel.readInt(),
		parcel.readInt(),
		parcel.readParcelable(Temp::class.java.classLoader),
		parcel.readParcelable(Feels_like::class.java.classLoader),
		parcel.readInt(),
		parcel.readInt(),
		parcel.readDouble(),
		parcel.readDouble(),
		parcel.readInt(),
		parcel.createTypedArrayList(Weather),
		parcel.readInt(),
		parcel.readDouble(),
		parcel.readDouble()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(dt)
		parcel.writeInt(sunrise)
		parcel.writeInt(sunset)
		parcel.writeParcelable(temp, flags)
		parcel.writeParcelable(feels_like, flags)
		parcel.writeInt(pressure)
		parcel.writeInt(humidity)
		parcel.writeDouble(dew_point)
		parcel.writeDouble(wind_speed)
		parcel.writeInt(wind_deg)
		parcel.writeTypedList(weather)
		parcel.writeInt(clouds)
		parcel.writeDouble(rain)
		parcel.writeDouble(uvi)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Daily> {
		override fun createFromParcel(parcel: Parcel): Daily {
			return Daily(parcel)
		}

		override fun newArray(size: Int): Array<Daily?> {
			return arrayOfNulls(size)
		}
	}
}