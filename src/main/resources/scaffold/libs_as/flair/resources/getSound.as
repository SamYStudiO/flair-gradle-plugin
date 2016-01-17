package flair.resources
{
	import flash.media.Sound;

	/**
	 *
	 */
	public function getSound( id : String , groupID : String = null ) : Sound
	{
		return getAssetManager( groupID ).getSound( id );
	}
}
