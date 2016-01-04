package flair.resources
{
	import _appId_.view.EnumScreen;

	/**
	 *
	 */
	public function getXml( id : String , groupID : String = EnumScreen.MAIN ) : XML
	{
		return getAssetManager( groupID ).getXml( id );
	}
}
