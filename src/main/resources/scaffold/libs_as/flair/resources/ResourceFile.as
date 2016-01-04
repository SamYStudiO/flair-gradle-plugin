package flair.resources
{
	import flash.filesystem.File;

	/**
	 *
	 */
	public class ResourceFile extends File
	{
		/**
		 * Create a new ResourceFile instance from a file instance.
		 * @param file File instance ton convert to a ResourceFile instance.
		 * @param resource Type of resource as defined by EnumResourceType constants.
		 * @param drawableScale If resource is a drawable this returns the scale to apply to resource if resource is picked from wrong dpi bucket,
		 * for example if your device bucket is xxhdpi and you provide only hdpi resource scale will return 2 to match your device bucket.
		 * @return A ResourceFile instance.
		 */
		public static function fromFile( file : File , resource : String , drawableScale : Number = 1 ) : ResourceFile
		{
			return new ResourceFile( file.url , resource , drawableScale );
		}

		/**
		 * Type of resource as defined by EnumResourceType constants.
		 */
		public var resource : String;

		/**
		 * If resource is a drawable this returns the scale to apply if resource is picked from wrong dpi bucket,
		 * for example if your device bucket is xxhdpi and you provide only hdpi resource scale will return 2 to match your device bucket.
		 * If your resource file is not a drawable this will always return 1.
		 */
		public var drawableScale : Number = 1;

		/**
		 * Constructor.
		 * @param path The absolute file path.
		 * @param resource Type of resource as defined by EnumResourceType constants.
		 * @param drawableScale scale to apply to match device dpi bucket.
		 */
		public function ResourceFile( path : String , resource : String , drawableScale : Number = 1.0 )
		{
			super( path );

			this.resource = resource;
			this.drawableScale = resource == EnumResourceType.DRAWABLE ? drawableScale : 1.0;
		}

		/**
		 * Get file instance from this ResourceFile instance.
		 * @return A File instance.
		 */
		public function getFile() : File
		{
			return new File( url );
		}
	}
}
