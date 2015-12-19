package _appId_.resources
{
	import _appId_.view.EnumScreen;

	import flash.media.SoundChannel;
	import flash.media.SoundTransform;

	/**
	 *
	 */
	public function playSound( id : String , startTime : Number = 0 , loops : int = 0 , transform : SoundTransform = null , groupID : String = EnumScreen.MAIN ) : SoundChannel
	{
		return getAssetManager( groupID ).playSound( id , startTime , loops , transform );
	}
}
