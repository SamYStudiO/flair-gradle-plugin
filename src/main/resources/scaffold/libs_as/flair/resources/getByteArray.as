package flair.resources
{
	import flash.utils.ByteArray;

	/**
	 *
	 */
	public function getByteArray( id : String , groupID : String = null ) : ByteArray
	{
		return getAssetManager( groupID ).getByteArray( id );
	}
}
