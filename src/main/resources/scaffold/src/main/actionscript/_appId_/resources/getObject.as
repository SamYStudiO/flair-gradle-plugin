package _appId_.resources
{
	import _appId_.view.EnumScreen;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getObject( id : String , groupID : String = EnumScreen.MAIN ) : Object
	{
		return getAssetManager( groupID ).getObject( id );
	}
}
