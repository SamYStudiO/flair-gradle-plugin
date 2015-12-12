package _appId_.resources
{
	import _appId_.view.EnumScreen;

	import flash.media.Sound;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getSound( id : String , groupID : String = EnumScreen.MAIN ) : Sound
	{
		return getAssetManager( groupID ).getSound( id );
	}
}
