package flair.resources
{
	import _appId_.view.EnumScreen;

	import flash.utils.ByteArray;

	/**
	 *
	 */
	public function getByteArray( id : String , groupID : String = EnumScreen.MAIN ) : ByteArray
	{
		return getAssetManager( groupID ).getByteArray( id );
	}
}
