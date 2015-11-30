package _appId_.utils.displayMetrics
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public final class EnumDensityBucket
	{
		/**
		 * 120 , .75
		 */
		public static const LDPI : String = "ldpi";

		/**
		 * 160 , 1
		 */
		public static const MDPI : String = "mdpi";

		/**
		 * 240 , 1.5
		 */
		public static const HDPI : String = "hdpi";

		/**
		 * 320 , 2
		 */
		public static const XHDPI : String = "xhdpi";

		/**
		 * 480 , 3
		 */
		public static const XXHDPI : String = "xxhdpi";

		/**
		 * 640 , 4
		 */
		public static const XXXHDPI : String = "xxxhdpi";

		/**
		 * @private
		 */
		public function EnumDensityBucket()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
