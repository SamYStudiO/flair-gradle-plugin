package _appId_.resources
{
	/**
	 * Constants used to defined resource type.
	 * @see http://developer.android.com/guide/topics/resources/available-resources.html
	 */
	public final class EnumResourceType
	{
		/**
		 * A drawable resource is an image stored under a drawable folder.
		 * @see http://developer.android.com/guide/topics/resources/drawable-resource.html
		 */
		public static const DRAWABLE : String = "drawable";

		/**
		 * Value resources are xml files defining different value types stored under a values folder, actual types are :
		 * - bool
		 * - color
		 * - dimen
		 * - integer
		 * - string
		 * @see http://developer.android.com/guide/topics/resources/more-resources.html
		 */
		public static const VALUES : String = "value";

		/**
		 * A xml resource is any kind of xml file stored under a xml folder.
		 */
		public static const XML : String = "xml";

		/**
		 * A raw resource is any raw data file resource stored under a raw folder.
		 */
		public static const RAW : String = "raw";

		/**
		 * @private
		 */
		public function EnumResourceType()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
