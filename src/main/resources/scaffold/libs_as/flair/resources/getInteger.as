package flair.resources
{
	import _appId_.view.EnumScreen;

	/**
	 *
	 */
	public function getInteger( id : String , groupID : String = EnumScreen.MAIN ) : int
	{
		return int( getAssetManager( groupID ).getXml( "values" )..integer.( @name == id ) );
	}
}
