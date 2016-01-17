package flair.resources
{
	import starling.textures.Texture;

	/**
	 *
	 */
	public function getDrawables( prefix : String , groupID : String = null ) : Vector.<Texture>
	{
		return getAssetManager( groupID ).getTextures( prefix );
	}
}
