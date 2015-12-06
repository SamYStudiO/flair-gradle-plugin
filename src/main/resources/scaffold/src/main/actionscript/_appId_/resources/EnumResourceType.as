package _appId_.resources
{
	/**
	 * @author SamYStudiO (contact@samystudio.net) on 29/11/2015.
	 */
	public final class EnumResourceType
	{
		/**
		 *
		 */
		public static const DRAWABLE : String = "drawable";

		/**
		 *
		 */
		public static const VALUES : String = "value";

		/**
		 *
		 */
		public static const XML : String = "xml";

		/**
		 * @private
		 */
		public function EnumResourceType()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
