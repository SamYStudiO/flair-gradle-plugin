package _appId_.resources
{
	import flash.filesystem.File;

	/**
	 * @author SamYStudiO (contact@samystudio.net) on 06/12/2015.
	 */
	public class ResourceFile extends File
	{
		/**
		 *
		 */
		public static function fromFile( file : File , resource : String , drawableScale : Number = 1 ) : ResourceFile
		{
			return new ResourceFile( file.url , resource , drawableScale );
		}

		/**
		 *
		 */
		public var resource : String;

		/**
		 *
		 */
		public var drawableScale : Number = 1;

		/**
		 *
		 */
		public function ResourceFile( path : String , resource : String , drawableScale : Number = 1.0 )
		{
			super( path );

			this.resource = resource;
			this.drawableScale = resource == EnumResourceType.DRAWABLE ? drawableScale : 1.0;
		}

		/**
		 *
		 */
		public function getFile() : File
		{
			return new File( url );
		}
	}
}
