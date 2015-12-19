package _appId_.resources
{
	import _appId_.view.EnumScreen;

	/**
	 *
	 */
	public function getObject( id : String , groupID : String = EnumScreen.MAIN ) : Object
	{
		return getAssetManager( groupID ).getObject( id );
	}
}
